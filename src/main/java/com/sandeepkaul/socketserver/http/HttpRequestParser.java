package com.sandeepkaul.socketserver.http;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.sandeepkaul.socketserver.http.request.HttpRequest;
import com.sandeepkaul.socketserver.http.request.RequestMethod;

public class HttpRequestParser {

  Logger logger = Logger.getLogger(HttpRequestParser.class.getName());
  
  private static final String CONTENT_LENGTH = "Content-Length";

  public HttpRequest generateHttpRequest(BufferedReader in) throws Exception {

    boolean firstLine = true;
    String requestLine = null;
    String inputLine;
    Map<String, String> headers = new HashMap<String, String>();
    System.out.println("Processing Request");
    while ((inputLine = in.readLine()) != null) {
      System.out.println(inputLine);
      if (firstLine) {
        requestLine = inputLine;
        firstLine = false;
        continue;
      }
      if (inputLine.length() == 0) {
        break;
      }
      handleHeader(headers, inputLine);
    }
    String body = readBody(in, headers);
    System.out.println(body);
    HttpRequest request = generateHttpRequest(requestLine, headers, body);
    return request;
  }

  private void handleHeader(Map<String, String> headers, String inputLine) {
    String[] split = inputLine.split(":");
    headers.put(split[0].trim(), split[1].trim());
  }

  private  HttpRequest generateHttpRequest(String requestLine, Map<String, String> headers,
      String body) {
    
    if(requestLine  == null || requestLine.isEmpty()) {
      return null;
    }
    String[] split = requestLine.split(" ");
    if(split.length < 3) {
      return null;
    }
    RequestMethod requestMethod  = RequestMethod.valueOf(split[0]);
    
    Map<String, String> queryParams = new HashMap<String, String>();
    String url = split[1];
    split = url.split("\\?");
    if(split.length == 1) {
      url = split[0];
    } else if(split.length == 2) {
      url  = split[0];
      queryParams = getQueryParams(split[1]);
    }
    
    HttpRequest httpRequest = new  HttpRequest(url, requestMethod, body, headers, queryParams);
    return httpRequest;
  }

  private  Map<String, String> getQueryParams(String string) {
    
    if(string == null || string.isEmpty()) {
      return null;
    }
    Map<String, String> queryParams = new HashMap<String, String>();
    String[] split = string.split("&");
    for (String qp : split) {
      String[] qpSplit = qp.split("=");
      if(qpSplit.length == 2) {
        queryParams.put(qpSplit[0], qpSplit[1]);
      }
    }
    return queryParams;
  }

  private static String readBody(BufferedReader in, Map<String, String> headers) throws Exception {

    String body = "";
    if (headers == null || headers.get(CONTENT_LENGTH) == null) {
      return body;
    }
    

    int contentLength = new Integer(headers.get(CONTENT_LENGTH));
    StringBuilder data = new StringBuilder(contentLength);
    char[] buffer = new char[contentLength];
    int charsIn = in.read(buffer, 0, contentLength);

    data.append(buffer, 0, charsIn);

    return data.toString();

  }


}
