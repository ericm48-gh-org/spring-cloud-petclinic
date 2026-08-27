package com.eric.common.petclinic.system;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * Globally access the ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * Shortcut to fetch a bean by type
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
    
    /**
     * Shortcut to fetch a bean by name and type
     */
    public static <T> T getBean(String name, Class<T> beanClass) {
        return context.getBean(name, beanClass);
    }
}
