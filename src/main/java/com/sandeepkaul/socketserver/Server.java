package com.sandeepkaul.socketserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import org.omg.PortableServer.POA;

import com.sandeepkaul.socketserver.http.HttpRequestParser;
import com.sandeepkaul.socketserver.http.handler.RequestHandler;
import com.sandeepkaul.socketserver.http.handler.ResponseGenerator;
import com.sandeepkaul.socketserver.http.request.HttpRequest;

public class Server {

  Logger logger = Logger.getLogger(Server.class.getName());

  private int portNumber = 9111;

  public static void main(String[] args) throws Exception {

    
    Server server = new Server();
    if(args != null && args.length > 0) {
      server.portNumber = new Integer(args[0]);
      System.out.println("Starting server... on port: "+server.portNumber);
    }
    server.runServer();


  }

  private void runServer() throws Exception {

    HttpRequestParser httpRequestParser = new HttpRequestParser();

    ResponseGenerator responseGenerator = new ResponseGenerator();
    RequestHandler requestHandler = new RequestHandler(responseGenerator);
    ServerSocket listener = new ServerSocket(portNumber);
    try {
      while (true) {
        Socket socket = listener.accept();
        try {

          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          HttpRequest request = httpRequestParser.generateHttpRequest(in);
          String responseBody = requestHandler.handle(request);
          DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
          dout.write(responseBody.getBytes());
          dout.flush();
          dout.close();

        } catch (Exception e) {
          logger.fine("Exception Caught. Ignoring this request");
        } finally {
          socket.close();
        }
      }
    } finally {
      listener.close();
    }
  }
}
