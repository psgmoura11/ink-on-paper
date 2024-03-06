package com.inkonpaper.notification.service.services;

import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockJobService {

  @Job(name = "Simple job")
  public void execute() {
    execute("Hello Simple job");
  }

  @Job(name = "The parametrized job")
  public void execute(String input) {
    log.info("The parameterized job with input {}", input);
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      log.error("Error while executing job", e);
    } finally {
      log.info("job has finished...");
    }
  }
}
