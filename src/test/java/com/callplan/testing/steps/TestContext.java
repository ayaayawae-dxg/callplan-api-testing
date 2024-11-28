package com.callplan.testing.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TestContext {
  private static Response response;

  public static RequestSpecification getRequest() throws IOException {
    Properties props = getEnv();
    return given()
      .baseUri(props.getProperty("BASE_URL"))
      .contentType("application/json")
      .accept("application/json")
      .header("api-key", props.getProperty("API_KEY"));
  }

  private static Properties getEnv() throws IOException {
    var props = new Properties();
    var envFile = Paths.get("src/test/resources/.env");
    try (var inputStream = Files.newInputStream(envFile)) {
      props.load(inputStream);
    }
    return props;
  }

  public static Response getResponse() {
    return response;
  }

  public static void setResponse(Response response) {
    TestContext.response = response;
  }
}
