package com.callplan.testing.steps.api;

import com.callplan.testing.steps.TestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class GetOneYearLeaveHistorySteps {
  private final RequestSpecification request;

  public GetOneYearLeaveHistorySteps() {
    this.request = TestContext.getRequest();
  }

  @When("I send POST request to get-one-year-leave-history with payload:")
  public void iSendPOSTRequestToGetOneYearLeaveHistoryWithPayload(String docString) throws JsonProcessingException {
    Map<String, Object> jsonPayload = new ObjectMapper().readValue(docString, Map.class);
    Allure.addAttachment("Request Payload", "application/json", new ObjectMapper().writeValueAsString(jsonPayload));

    Response response = request
      .body(jsonPayload)
      .post("/get-one-year-leave-history");
        
    TestContext.setResponse(response);
  }

  @And("the response should contain array of one year leave history data")
  public void theResponseShouldContainArrayOfOneYearLeaveHistoryData() {
    Response response = TestContext.getResponse();
    response.then().body("data", hasSize(greaterThan(0)));
  }

  @And("each one year leave history data should match schema")
  public void eachOneYearLeaveHistoryDataShouldMatchSchema() {
    Response response = TestContext.getResponse();
    ObjectMapper mapper = new ObjectMapper();
    try {
      String jsonArray = mapper.writeValueAsString(response.jsonPath().getList("data"));
      assertThat(jsonArray, matchesJsonSchemaInClasspath("schemas/GetOneYearLeaveHistory.json"));
    } catch (JsonProcessingException e) {
      fail("Failed to parse response data: " + e.getMessage());
    }
  }

  @And("the response should contain empty array of one year leave history data")
  public void theResponseShouldContainEmptyArrayOfOneYearLeaveHistoryData() {
    Response response = TestContext.getResponse();
    response.then().body("data", hasSize(0));
  }
}