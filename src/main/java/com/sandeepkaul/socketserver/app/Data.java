package com.sandeepkaul.socketserver.app;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Data {

  public static Map<String, String> jobMap; 
  
  static {
    
    jobMap = new ConcurrentHashMap<>();
  }
}
