package jp.co.monocrea;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

  @Override
  public Response toResponse(WebApplicationException exception) {
    return Response.status(exception.getResponse().getStatus())
        .entity(exception.getMessage()) // エラーメッセージをレスポンスに設定
        .build();
  }
}
