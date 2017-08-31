package com.sandeepkaul.socketserver.http.handler;

import com.sandeepkaul.socketserver.app.JobRequestHandler;
import com.sandeepkaul.socketserver.http.request.HttpRequest;
import com.sandeepkaul.socketserver.http.response.HttpResponse;

public class RequestHandler {

  private ResponseGenerator responseGenerator;
  private JobRequestHandler jobRequestHandler;
  
  public RequestHandler(ResponseGenerator responseGenerator) {
   this.responseGenerator = responseGenerator;
   this.jobRequestHandler = new  JobRequestHandler();
  }
  
  
  public String handle(HttpRequest request) throws Exception {
    
    
    HttpResponse httpResponse = jobRequestHandler.handleRequest(request);
    
    String responseBody = responseGenerator.getResponseString( httpResponse.getStatusCode(), httpResponse.getBody());
    System.out.println("\n\nSending Response:");
    System.out.println(responseBody);
    return responseBody;
  }

  
}
