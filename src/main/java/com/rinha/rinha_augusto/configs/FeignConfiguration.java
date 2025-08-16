package com.rinha.rinha_augusto.configs;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.rinha.rinha_augusto.clients")
public class FeignConfiguration {

}
