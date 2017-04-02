package com.deppon.uap.framework.cache;

import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.apache.zookeeper.common.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import redis.clients.jedis.JedisPoolConfig;

public class CodisPoolFactoryBean extends AbstractFactoryBean<JedisResourcePool> {

    // 日志信息
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 服务器IP
    private String zkConnectString;

    // 超时时间
    private int zkSessionTimeout;

    // 密码
    private String password;

    // Codis ZK节点
    private String zkPath;

    // Jedis Pool配置参数
    private JedisPoolConfig poolConfig;

    @Override
    protected JedisResourcePool createInstance() throws Exception {
        logger.info("开始创建codis-jodis连接池");
        JedisResourcePool jedisPool = RoundRobinJedisPool.create()
                .curatorClient(zkConnectString, zkSessionTimeout)
                .password(password)
                .zkProxyDir(zkPath)
                .poolConfig(poolConfig)
                .build();
        logger.info("成功创建codis-jodis连接池");
        return jedisPool;
    }

    @Override
    protected void destroyInstance(JedisResourcePool instance) throws Exception {
        logger.info("开始销毁codis-jodis连接池");
        IOUtils.closeStream(instance);
        logger.info("成功销毁codis-jodis连接池");
    }

    @Override
    public Class<JedisResourcePool> getObjectType() {
        return JedisResourcePool.class;
    }

    public void setZkConnectString(String zkConnectString) {
        this.zkConnectString = zkConnectString;
    }

    public void setZkSessionTimeout(int zkSessionTimeout) {
        this.zkSessionTimeout = zkSessionTimeout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setZkPath(String zkPath) {
        this.zkPath = zkPath;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }
}
