package com.oncore.auth.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 26/04/2013
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        return null;
    }

//    private static Logger LOG = LoggerFactory.getLogger(GenericExceptionMapper.class);
//
//    public Response toResponse(Exception exception) {
//        if (exception instanceof WebApplicationException) {
//            LOG.info("Web Application Exception: " + exception);
//            return ((WebApplicationException) exception).getResponse();
//        }
//        exception.printStackTrace();
//        LOG.error("Internal Server Error: " + exception);
//        LOG.error("Internal Server Error: " + exception.getCause());
//        return new ApplicationRuntimeException("Server error").getResponse();
//    }
}
