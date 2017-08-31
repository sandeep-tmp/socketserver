package com.sandeepkaul.socketserver.app;

import java.util.UUID;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandeepkaul.socketserver.Server;
import com.sandeepkaul.socketserver.app.model.CreateJobModel;
import com.sandeepkaul.socketserver.app.model.CreateJobResponseModel;
import com.sandeepkaul.socketserver.app.model.ErrorModel;
import com.sandeepkaul.socketserver.http.request.HttpRequest;
import com.sandeepkaul.socketserver.http.request.RequestMethod;
import com.sandeepkaul.socketserver.http.response.HttpResponse;

public class JobRequestHandler {

  Logger logger = Logger.getLogger(Server.class.getName());

  ObjectMapper objectMapper = new ObjectMapper();

  public HttpResponse handleRequest(HttpRequest request) throws Exception {

    HttpResponse httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Invalid endpoint")));
    try {
      switch (request.getRequestMethod()) {
        case PUT:
          if ("/job".equals(request.getUrl())) {
            httpResponse = handleCreateJob(request.getBody());
          }
          break;

        case GET:
          if(request.getUrl().startsWith("/job/")) {
            httpResponse = handleGetJob(request.getUrl().substring("/job/".length()));
          }
          break;
        case DELETE:
          if(request.getUrl().startsWith("/job/")) {
            httpResponse = handleDeleteJob(request.getUrl().substring("/job/".length()));
          }
          break;
        case POST:
          if(request.getUrl().startsWith("/job/")) {
            httpResponse = handleUpdateJob(request.getUrl().substring("/job/".length()), request.getBody());
          }
          break;
        default:
          break;
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }

    return httpResponse;
  }

  private HttpResponse handleUpdateJob(String id, String body) throws Exception{
    HttpResponse httpResponse = new HttpResponse(0, null);
    logger.info("Querying for ID: "+id);
    if(id == null || id.isEmpty() || body == null || body.isEmpty()) {
      httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Id/Body cannot be empty in this request")));
      return httpResponse;
    }
    String name = Data.jobMap.get(id);
    if(name == null) {
      httpResponse = new HttpResponse(404, objectMapper.writeValueAsString(new ErrorModel("Unknown Job Id")));
      return httpResponse;
    }
    CreateJobModel createJobModel = objectMapper.readValue(body, CreateJobModel.class);
    if(createJobModel == null || createJobModel.getName() ==null || createJobModel.getName().isEmpty()) {
      httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Name cannot be empty in this request")));
      return httpResponse;
    }
    Data.jobMap.put(id, createJobModel.getName());
    return new HttpResponse(200, "{\"response\":true}");
  }

  private HttpResponse handleDeleteJob(String id) throws Exception{
    
    HttpResponse httpResponse = new HttpResponse(0, null);
    logger.info("Querying for ID: "+id);
    if(id == null || id.isEmpty()) {
      httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Id cannot be empty in this request")));
      return httpResponse;
    }
    String name = Data.jobMap.get(id);
    if(name == null) {
      httpResponse = new HttpResponse(404, objectMapper.writeValueAsString(new ErrorModel("Unknown Job Id")));
      return httpResponse;
    }
    Data.jobMap.remove(id);
    return new HttpResponse(200, "{\"response\":true}");
  }

  private HttpResponse handleGetJob(String id) throws Exception {
    
    HttpResponse httpResponse = new HttpResponse(0, null);
    logger.info("Querying for ID: "+id);
    if(id == null || id.isEmpty()) {
      httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Id cannot be empty in this request")));
      return httpResponse;
    }
    String name = Data.jobMap.get(id);
    if(name == null) {
      httpResponse = new HttpResponse(404, objectMapper.writeValueAsString(new ErrorModel("Unknown Job Id")));
      return httpResponse;
    }
    CreateJobModel createJobModel = new CreateJobModel();
    createJobModel.setName(name);
    return new HttpResponse(200, objectMapper.writeValueAsString(createJobModel));
  }

  private HttpResponse handleCreateJob(String body) throws Exception {

    HttpResponse httpResponse = new HttpResponse(0, null);
    CreateJobModel createJobModel = objectMapper.readValue(body, CreateJobModel.class);
    System.out.println("Running for"+createJobModel.getName());
    if (createJobModel == null || createJobModel.getName() == null
        || createJobModel.getName().isEmpty()) {
      httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Name cannot be empty in this request")));
      return httpResponse;
    }
    String uuid = UUID.randomUUID().toString();
    Data.jobMap.put(uuid, createJobModel.getName());
    logger.info("Data till now:" + Data.jobMap);
    CreateJobResponseModel responseModel = new CreateJobResponseModel(uuid);
    return new HttpResponse(200, objectMapper.writeValueAsString(responseModel));
  }

}
