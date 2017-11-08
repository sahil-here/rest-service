package vendi.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class VendiExceptionMapper implements ExceptionMapper<VendiException> {
    @Override
    public Response toResponse(VendiException e) {
        return Response.status(e.getCode()).entity(new VendiErrorMessage(e.getCode().toString(), e.getMessage())).build();
    }
}
