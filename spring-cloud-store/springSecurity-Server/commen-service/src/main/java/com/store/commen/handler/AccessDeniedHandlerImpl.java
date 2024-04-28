//package com.store.commen.handler;
//
//import com.alibaba.fastjson.JSON;
//
//import com.store.commen.utils.ResponseResult;
//import com.store.commen.utils.WebUtils;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.DispatcherServlet;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.nio.file.AccessDeniedException;
//
//@Component
//@ConditionalOnClass(DispatcherServlet.class)
//public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "权限不足");
//        String json = JSON.toJSONString(result);
//        WebUtils.renderString(response,json);
//
//    }
//}
