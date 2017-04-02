package com.deppon.uap.framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.common.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Properties;

import static org.springframework.context.support.PropertySourcesPlaceholderConfigurer.ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME;
import static org.springframework.context.support.PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME;

public class MixturePlaceholderConfigurer extends PlaceholderConfigurerSupport implements EnvironmentAware, InitializingBean, DisposableBean {

    public static final String REMOTE_PROPERTIES_PROPERTY_SOURCE_NAME = "remoteProperties";

    private MutablePropertySources propertySources = new MutablePropertySources();

    private ConfigurablePropertyResolver propertyResolver;

    private CuratorFramework curatorClient;

    private String nodePath;

    private ObjectMapper objectMapper;

    private Environment environment;

    private PropertySources appliedPropertySources;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 添加环境配置
        if (this.environment != null) {
            this.propertySources.addLast(
                    new PropertySource<Environment>(ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME, this.environment) {
                        @Override
                        public String getProperty(String key) {
                            return this.source.getProperty(key);
                        }
                    }
            );
        }

        // 添加本地配置
        try {
            PropertySource<?> localPropertySource =
                    new PropertiesPropertySource(LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME, mergeProperties());
            if (this.localOverride) {
                this.propertySources.addFirst(localPropertySource);
            } else {
                this.propertySources.addLast(localPropertySource);
            }
        } catch (IOException ex) {
            throw new BeanInitializationException("Could not load properties", ex);
        }

        if (!"dev".equals(propertyResolver.getProperty("project.environment.active"))) {
            this.initCuratorClientAndNodePath(); // 初始化远程客户端和配置节点
            // 添加远程配置
            try {
                PropertiesPropertySource remotePropertySource =
                        new PropertiesPropertySource(REMOTE_PROPERTIES_PROPERTY_SOURCE_NAME, this.getRemoteProperties());
                this.propertySources.addLast(remotePropertySource);
            } catch (Exception e) {
                ExceptionUtils.wrapAndThrow(e);
            }
        }

        this.processProperties(beanFactory, this.propertyResolver);
        this.appliedPropertySources = this.propertySources;
    }

    protected void initCuratorClientAndNodePath() {
        String connectString = this.propertyResolver.getProperty("config.zk.connectString");
        int connectionTimeout = this.propertyResolver.getProperty("config.zk.connectionTimeout", int.class, 1000);
        int sessionTimeout = this.propertyResolver.getProperty("config.zk.sessionTimeout", int.class, 5000);
        this.curatorClient = CuratorFrameworkFactory.newClient(connectString, sessionTimeout,
                connectionTimeout, new ExponentialBackoffRetry(1000, 3));
        this.curatorClient.start();
        this.nodePath = this.propertyResolver.getProperty("config.zk.nodePath");
    }


    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
            final PropertyResolver propertyResolver) throws BeansException {
        StringValueResolver valueResolver = new StringValueResolver() {
            @Override
            public String resolveStringValue(String strVal) {
                String resolved = ignoreUnresolvablePlaceholders ?
                        propertyResolver.resolvePlaceholders(strVal) :
                        propertyResolver.resolveRequiredPlaceholders(strVal);
                return (resolved.equals(nullValue) ? null : resolved);
            }
        };
        super.doProcessProperties(beanFactoryToProcess, valueResolver);
    }

    @Override @Deprecated
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        throw new UnsupportedOperationException(
                "Call processProperties(ConfigurableListableBeanFactory, ConfigurablePropertyResolver) instead");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.propertyResolver = new PropertySourcesPropertyResolver(this.propertySources);
        this.propertyResolver.setPlaceholderPrefix(super.placeholderPrefix);
        this.propertyResolver.setPlaceholderSuffix(super.placeholderSuffix);
        this.propertyResolver.setValueSeparator(super.valueSeparator);
    }

    @Override
    public void destroy() throws Exception {
        IOUtils.closeStream(this.curatorClient);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * @return 当前使用的PropertySources
     */
    public PropertySources getAppliedPropertySources() throws IllegalStateException {
        Assert.state(this.appliedPropertySources != null, "PropertySources have not get been applied");
        return this.appliedPropertySources;
    }

    public PropertyResolver getPropertyResolver() {
        return this.propertyResolver;
    }

    /**
     * 从UCMC拉取最新配置
     *
     * @return 配置信息
     */
    public Properties getRemoteProperties() throws Exception {
        // 获取所有配置子节点
        ListIterator<String> cNodePathIterator = this.curatorClient.getChildren()
                .forPath(this.nodePath)
                .listIterator();
        // 构建配置信息的JSON
        StringBuilder configBuilder = new StringBuilder("{");
        while (cNodePathIterator.hasNext()) {
            if (cNodePathIterator.hasPrevious()) {
                configBuilder.append(",");
            }
            String cNodePath = this.nodePath + "/" + cNodePathIterator.next();
            String cNodeData = new String(this.curatorClient.getData()
                    .forPath(cNodePath));
            cNodeData = cNodeData.substring(cNodeData.indexOf("{") + 1,
                    cNodeData.lastIndexOf("}"));
            configBuilder.append(cNodeData);
        }
        configBuilder.append("}");
        return this.objectMapper.readValue(configBuilder.toString(),
                Properties.class);
    }


}
