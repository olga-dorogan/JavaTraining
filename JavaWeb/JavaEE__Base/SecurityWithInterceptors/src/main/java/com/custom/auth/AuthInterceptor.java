package com.custom.auth;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by olga on 05.06.15.
 */
@Auth(roles = {})
@Interceptor
public class AuthInterceptor implements Serializable {
    private static final String HEADER_TOKEN = "token";
    private static final String HEADER_ID = "id";
    @Inject
    private HttpServletRequest request;

    @AroundInvoke
    public Object invoke(final InvocationContext context) throws Exception {
        String[] allowedRoles = getRoles(context.getMethod());
        System.out.println(String.format("Method '%s' was annotated with roles '%s'", context.getMethod().getName(),
                Arrays.toString(allowedRoles)));
        if (allowedRoles == null || allowedRoles.length == 0) {
            return context.proceed();
        }
//        1. verify token
//        2. verify id
//        3. get role
        String role = getRole(getToken(), getId());
        if (isUserInRoles(role, allowedRoles)) {
            return context.proceed();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    private boolean isUserInRoles(String role, String[] allowedRoles) {
        for (int i = 0; i < allowedRoles.length; i++) {
            if (role.equals(allowedRoles[i])) {
                return true;
            }
        }
        return false;
    }

    private String getRole(String token, String id) {
        if (token.equals("1") && id.equals("1")) {
            return "teacher";
        } else if (token.equals("2") && id.equals("2")) {
            return "student";
        }
        return null;
    }

    private String[] getRoles(Method method) {
        if (method.isAnnotationPresent(Auth.class)) {
            return method.getAnnotation(Auth.class).roles();
        }

        if (method.getDeclaringClass().isAnnotationPresent(Auth.class)) {
            return method.getDeclaringClass().getAnnotation(Auth.class).roles();
        }
        return null;
    }

    private String getToken() {
        return request.getHeader(HEADER_TOKEN);
    }

    private String getId() {
        return request.getHeader(HEADER_ID);
    }
}
