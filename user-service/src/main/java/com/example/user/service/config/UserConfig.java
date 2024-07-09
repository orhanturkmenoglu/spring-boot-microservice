package com.example.user.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserConfig {

    @Bean
    @LoadBalanced
    /*@LoadBalanced
     Bu anotasyon, uygulamanızın belirli bir hizmete olan isteklerini
      dinamik olarak dağıtmak ve hizmetler arası yük dengelemesini sağlamak için kullanılır.
     */
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
