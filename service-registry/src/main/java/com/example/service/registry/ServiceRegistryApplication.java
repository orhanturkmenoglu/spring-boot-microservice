package com.example.service.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication

/*
	service registry eureka server active
	eureka servis başlatıldığı anda bütün çalışan mikro hizmetleri kayıt defterine kayıt edecek.
	ayrıca aynı port adresinde birden fazla mikro hizmet var ise loadblancer ile yük dengeleme
	sağlıyacak.
 */
@EnableEurekaServer
public class ServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryApplication.class, args);
	}

}
