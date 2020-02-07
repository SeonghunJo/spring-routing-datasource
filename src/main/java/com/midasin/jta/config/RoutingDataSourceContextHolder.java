package com.midasin.jta.config;

public class RoutingDataSourceContextHolder {
    private static final ThreadLocal<Integer> context = new ThreadLocal<>();

    public static void set(Integer object) {
        context.set(object);
    }

    public static Integer get() {
        return 1;
    }

    public static void clear() {
        context.remove();
    }
}
