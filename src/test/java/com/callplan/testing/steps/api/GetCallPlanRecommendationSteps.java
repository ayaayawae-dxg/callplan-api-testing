package com.callplan.testing.steps.api;

import com.callplan.testing.steps.TestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class GetCallPlanRecommendationSteps {
  private final RequestSpecification request;

  public GetCallPlanRecommendationSteps() {
    this.request = TestContext.getRequest();
  }

  @When("I send POST request to \\/get-call-plan-recommendation with payload:")
  public void iSendPOSTRequestToGetCallPlanRecommendationWithPayload(String docString) throws JsonProcessingException {
    Map<String, Object> jsonPayload = new ObjectMapper().readValue(docString, Map.class);
    
    Response response = request
      .body(jsonPayload)
      .post("/get-call-plan-recommendation");
        
    TestContext.setResponse(response);
  }

  @And("the response should contain array of call plan recommendation data")
  public void theResponseShouldContainArrayOfCallPlanRecommendationData() {
    Response response = TestContext.getResponse();
    response.then().body("data", is(notNullValue()));
  }

  @And("call plan recommendation data should match schema")
  public void callPlanRecommendationDataShouldMatchSchema() {
    Response response = TestContext.getResponse();
    ObjectMapper mapper = new ObjectMapper();
    try {
      String jsonObject = mapper.writeValueAsString(response.jsonPath().getMap("data"));
      assertThat(jsonObject, matchesJsonSchemaInClasspath("schemas/GetCallPlanRecommendation.json"));
    } catch (JsonProcessingException e) {
      fail("Failed to parse response data: " + e.getMessage());
    }
  }

  @And("the response should contain empty array of call plan recommendation data")
  public void theResponseShouldContainEmptyArrayOfCallPlanRecommendationData() {
    Response response = TestContext.getResponse();
    response.then().body("data", hasSize(0));
  }
}