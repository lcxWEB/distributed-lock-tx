package com.lcx.spring;

import com.lcx.spring.postprocessor.MyBeanFactoryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: lichunxia
 * @create: 2021-04-14 10:41
 */
public class SpringMain {

    // Spring源码
    //
    //   关注源码设计上的扩展性
    //
    //   IOC -- 控制反转，是一种思想
    //        DI -- 依赖注入，是一种实现
    //    Spring是一个IOC容器，存放bean对象

    //     加载xml -> 解析xml -> 封装BeanDefinition -> 实例化 -> 放到容器中 -> 从容器中获取

    // 填充属性 -- populate？？


    //
    //   AOP --

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ctx.register(AppConfig.class, MyBeanFactoryPostProcessor.class);
        ctx.refresh();
        AppConfig appConfig = ctx.getBean(AppConfig.class);
        System.out.println(appConfig);
        MyBean myBean = ctx.getBean(MyBean.class);
        MyBeanFactoryPostProcessor factoryPostProcessor = ctx.getBean(MyBeanFactoryPostProcessor.class);
        System.out.println(myBean);
        System.out.println(factoryPostProcessor);

        // ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext();
        // xmlApplicationContext.setConfigLocation("applicationContext.xml");

        System.out.println();
    }
}
