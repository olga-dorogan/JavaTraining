package com.custom.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;

/**
 * Created by olga on 20.04.15.
 */
@Interceptor
@Loggable
public class LoggingInterceptor {
    private static final Logger LOGGER = LogManager.getLogger(LoggingInterceptor.class);

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        if (LOGGER.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("\t")
                    .append(context.getMethod().getDeclaringClass().getCanonicalName())
                    .append("\t")
                    .append(context.getMethod().getName())
                    .append("\t");
            if (context.getParameters().length > 0) {
                sb.append(" with params: ").append(Arrays.toString(context.getParameters()));
            }
            LOGGER.trace(sb.toString());
        }
        return context.proceed();
    }
}
