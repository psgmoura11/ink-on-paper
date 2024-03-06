package com.inkonpaper.notification.service.jobs;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.jobrunr.jobs.context.JobContext;
import org.springframework.stereotype.Component;

@Component
public class BookStockJob {

  public void run(JobContext jobContext) throws Exception {

    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://your-microservice-url/book-stock"))
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    String responseBody = response.body();
    System.out.println("Response from microservice: " /*+ responseBody*/);
  }
}
