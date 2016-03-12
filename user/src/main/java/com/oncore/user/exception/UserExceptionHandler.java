package com.oncore.user.exception;

import org.aspectj.lang.annotation.*;

import java.sql.SQLException;

/**
 * Created by steve on 3/8/16.
 */
@Aspect
public class UserExceptionHandler {

    @Pointcut("execution(* com.oncore.user.controller.*.*(..))")
    private void pointCut(){}

    @Before("pointCut()")
    public void before(){

    }
    @After("pointCut()")
    public void after(){}

    @AfterThrowing(value = "pointCut()",throwing = "e")
    public void doAfterThrowing(SQLException e){
        e.printStackTrace();
        throw new UserSQLException(e.getMessage());
    }
}
