package com.custom;

import javax.annotation.Priority;
import javax.interceptor.AroundConstruct;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * Created by olga on 09.04.15.
 */
@Priority(Interceptor.Priority.APPLICATION + 10)
@Interceptor
@MyInterceptorBinding
public class MyInterceptor implements Serializable{
    @AroundConstruct
    public Object interceptorConstruct(InvocationContext context) throws Exception {
        System.out.println("interceptor -- around construct");
        return context.proceed();
    }
}
