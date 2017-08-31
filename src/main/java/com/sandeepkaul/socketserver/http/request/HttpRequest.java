package com.sandeepkaul.socketserver.http.request;

import java.util.Map;

public class HttpRequest {

  private String url;

  private RequestMethod requestMethod;

  private String body;

  private Map<String, String> headers;

  private Map<String, String> queryParams;

  
  
  public HttpRequest(String url, RequestMethod requestMethod, String body,
      Map<String, String> headers, Map<String, String> queryParams) {
    super();
    this.url = url;
    this.requestMethod = requestMethod;
    this.body = body;
    this.headers = headers;
    this.queryParams = queryParams;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public RequestMethod getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(RequestMethod requestMethod) {
    this.requestMethod = requestMethod;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public Map<String, String> getQueryParams() {
    return queryParams;
  }

  public void setQueryParams(Map<String, String> queryParams) {
    this.queryParams = queryParams;
  }


}

