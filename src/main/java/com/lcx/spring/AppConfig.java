package com.lcx.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @Configuration AppConfig会被CGLIB代理 com.lcx.spring.AppConfig$$EnhancerBySpringCGLIB$$2c8e631b
 * @author: lichunxia
 * @create: 2021-04-14 10:38
 */
@Configuration
public class AppConfig {

    // private final MyBean someBean;
    //
    // public AppConfig(MyBean someBean) {
    //     this.someBean = someBean;
    // }

    /**
     * mark BFPP-returning @Bean methods as static
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer pspc() {
        // instantiate, configure and return pspc ...
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MyBean myBean() {
        // instantiate and configure MyBean obj
        MyBean obj = new MyBean();
        return obj;
    }

}
