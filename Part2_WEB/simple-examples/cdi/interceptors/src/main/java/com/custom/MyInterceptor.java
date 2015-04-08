package com.custom;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Created by olga on 07.04.15.
 */
@Interceptor
@MyInterceptorBinding
public class MyInterceptor {
    @AroundInvoke
    public Object changeParam(InvocationContext context) throws Exception {
        Object[] params = context.getParameters();
        if (params.length > 0 && params[0] instanceof String) {
            params[0] = (String) params[0] + " (form interceptor)";
        }
        return context.proceed();
    }
}
