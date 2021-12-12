package com.lcx.spring.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author: lichunxia
 * @create: 2021-04-16 12:51
 */
// @Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // BeanDefinition beanDefinition = beanFactory.getBeanDefinition("myBean");
        // beanDefinition.setDescription("hahaha");
        System.out.println("设置BeanDefinition");
    }

}
