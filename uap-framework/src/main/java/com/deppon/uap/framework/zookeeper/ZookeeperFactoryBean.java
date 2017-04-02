package com.deppon.uap.framework.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.concurrent.CountDownLatch;

@Deprecated
public class ZookeeperFactoryBean extends AbstractFactoryBean<ZooKeeper> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 同步阻塞对象
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    // Zookeeper 连接地址
    private String connectString;

    // Session 超时时间
    private int sessionTimeout = 5000;


    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }


    @Override
    public Class<ZooKeeper> getObjectType() {
        return ZooKeeper.class;
    }

    @Override
    protected ZooKeeper createInstance() throws Exception {
        // 创建Zookeeper连接
        ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();  //连接上之后，释放计数器
                } else if (event.getState() == Event.KeeperState.Expired) {
                    try {
                        ZookeeperFactoryBean.this.destroy();
                        if (isSingleton()) {
                            afterPropertiesSet();
                        }
                    } catch (Exception e) {
                        logger.error("Zookeeper Reconnect Exception", e);
                    }

                }
            }
        });

        //处理抛出连接异常问题
        if (zooKeeper.getState() == States.CONNECTING) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }

        return zooKeeper;
    }

    protected void destroyInstance(ZooKeeper zooKeeper) throws Exception {
        try {
            if (zooKeeper != null) {
                zooKeeper.close();
            }
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
