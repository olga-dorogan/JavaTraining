package com.custom.service.rs;

import com.custom.model.exception.DAOBusinessException;

import javax.ejb.EJBException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by olga on 27.04.15.
 */
@Provider
public class ExceptionHttpStatusResolver implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof DAOBusinessException || exception instanceof EJBException) {
            status = Response.Status.BAD_REQUEST;
        }
        return Response.status(status).entity(exception.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
