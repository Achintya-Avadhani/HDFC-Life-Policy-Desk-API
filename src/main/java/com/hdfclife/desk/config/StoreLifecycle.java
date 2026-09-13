package com.hdfclife.desk.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class StoreLifecycle {
    @PostConstruct
    public void ready() {
        System.out.println("PolicyStore ready");
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("PolicyStore shutdown");
    }
}
