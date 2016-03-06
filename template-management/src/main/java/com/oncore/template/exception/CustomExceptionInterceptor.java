/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.template.exception;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * Created by steve on 2/19/16.
 */
public class CustomExceptionInterceptor implements ThrowsAdvice {
    public void afterThrowing(Method me, Object[] args, Object target,
                              RuntimeException  throwable) {
        System.out.println("产生异常的方法名称：  " + me.getName());

        for(Object o:args){
            System.out.println("方法的参数：   " + o.toString());
        }

        System.out.println("代理对象：   " + target.getClass().getName());
        System.out.println("抛出的异常:    " + throwable.getMessage()+">>>>>>>"
                + throwable.getCause());
        System.out.println("异常详细信息：　　　"+throwable.fillInStackTrace());
    }



}
