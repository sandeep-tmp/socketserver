package com.sandeepkaul.socketserver.http.response;

public class HttpResponse {

  private int statusCode;
  private String body;
  public int getStatusCode() {
    return statusCode;
  }
  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }
  public String getBody() {
    return body;
  }
  public void setBody(String body) {
    this.body = body;
  }
  public HttpResponse(int statusCode, String body) {
    super();
    this.statusCode = statusCode;
    this.body = body;
  }
  
  
}
