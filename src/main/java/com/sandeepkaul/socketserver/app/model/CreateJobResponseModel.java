/**
 * 
 */
package com.sandeepkaul.socketserver.app.model;

/**
 * @author Sandeep Kaul
 *
 */
public class CreateJobResponseModel {

  String id;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public CreateJobResponseModel(String id) {
    super();
    this.id = id;
  }
  
  
}
