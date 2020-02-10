package com.midasin.sample.Interceptor;

import com.midasin.sample.config.RoutingDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class DomainInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("===== DOMAIN INTERCEPTOR PREHANDLE =====");
        RoutingDataSourceContextHolder.set(request.getServerName());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RoutingDataSourceContextHolder.clear();
        log.info("===== DOMAIN INTERCEPTOR POSTHANDLE =====");
    }
}
