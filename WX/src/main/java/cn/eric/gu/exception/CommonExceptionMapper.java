package cn.eric.gu.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
public class CommonExceptionMapper implements ExceptionMapper<Throwable> {

	
	public Response toResponse(Throwable exception) {
		// TODO Auto-generated method stub
		if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        } else {
        	return Response.status(200).entity("runtime error!!!").build();
        }
	}

}
