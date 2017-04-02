package com.deppon.uap.framework.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

public final class ApplicationContextHolder extends ApplicationObjectSupport implements InitializingBean {

    private static volatile ApplicationContextHolder applicationContextHolder;

    public static final ApplicationContext get() {
        if (applicationContextHolder == null) {
            applicationContextHolder = new ApplicationContextHolder();
        }
        return applicationContextHolder.getApplicationContext();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContextHolder = this;
    }

    @Override
    protected boolean isContextRequired() {
        return super.isContextRequired();
    }

    @Override
    protected Class<?> requiredContextClass() {
        return super.requiredContextClass();
    }

    @Override
    protected void initApplicationContext(ApplicationContext context) throws BeansException {
        super.initApplicationContext(context);
    }

    @Override
    protected void initApplicationContext() throws BeansException {
        super.initApplicationContext();
    }
}
