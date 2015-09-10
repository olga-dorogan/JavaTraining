package com.custom;

import com.custom.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by olga on 05.06.15.
 */
@Path("/")
public class ResourceService {
    @GET
    @Path("/teacher")
    @Auth(roles = {"teacher"})
    public Response getTeacher() {
        return Response.ok().build();
    }
    @GET
    @Path("/student")
    @Auth(roles = {"student"})
    public Response getStudent() {
        return Response.ok().build();
    }

    @GET
    @Path("/anonymous")
    @Auth(roles = {})
    public Response getAnonymous() {
        return Response.ok().build();
    }
}
