package com.inkonpaper.notification.service;

import com.inkonpaper.notification.service.services.StockJobService;
import jakarta.annotation.PostConstruct;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication {

  @Autowired
  private JobScheduler jobScheduler;

  public static void main(String[] args) {
    SpringApplication.run(NotificationApplication.class, args);
  }

  @PostConstruct
  public void scheduleRecurrently() {
    jobScheduler.<StockJobService>scheduleRecurrently(Cron.minutely(), x -> x.execute());
  }
}
