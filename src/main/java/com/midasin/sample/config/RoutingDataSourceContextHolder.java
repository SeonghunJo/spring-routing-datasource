package com.midasin.sample.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoutingDataSourceContextHolder {
    private static final ThreadLocal<String> context = new ThreadLocal<>();

    public static void set(String object) {
        log.info("RoutingDataSource select key : {}", RoutingDataSourceContextHolder.get());
        context.set(object);
    }

    public static String get() {
        return context.get();
    }

    public static void clear() {
        log.info("RoutingDataSource clear key : {}", RoutingDataSourceContextHolder.get());
        context.remove();
    }
}
