package com.tarkesh.dockerdemo.controllers;

import java.awt.AWTException;
import java.awt.Robot;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

  @GetMapping("/message")
  public String showMessage() throws Exception {
    Class.forName("org.mariadb.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://mysql:3306/test?autoReconnect=true", "root", "root");
    Statement statement = conn.createStatement();
    ResultSet result = statement.executeQuery("select * from POCData");
    while (result.next()) {
      return result.getString("vendor");
    }
    return "No Data";
  }

  @GetMapping("/ping")
  public String ping() {
    return "Service Working !\n";
  }

  @GetMapping("/welcome")
  public String welcome(@RequestParam String url) throws InterruptedException, AWTException {

    System.out.println("API Called At :- " + new Date());
    System.out.println("URL :- " + url);

    System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
    WebDriver driver = new ChromeDriver();

    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    driver.manage().window().maximize();

    driver.get(url);

    driver.wait(1000);

    driver.switchTo().alert().accept();

    Robot robot = new Robot();

    return "Welcome Buddy !";
  }

}
