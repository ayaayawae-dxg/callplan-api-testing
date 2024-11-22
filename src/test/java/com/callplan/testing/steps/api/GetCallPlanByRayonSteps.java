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

public class GetCallPlanByRayonSteps {
  private final RequestSpecification request;

  public GetCallPlanByRayonSteps() {
    this.request = TestContext.getRequest();
  }

  @When("I send POST request to \\/get-call-plan-by-rayon with payload:")
  public void iSendPOSTRequestToGetCallPlanByRayonWithPayload(String docString) throws JsonProcessingException {
    Map<String, Object> jsonPayload = new ObjectMapper().readValue(docString, Map.class);

    Response response = request
      .body(jsonPayload)
      .post("/get-call-plan-by-rayon");
        
    TestContext.setResponse(response);
  }

  @And("the response should contain array of call plan by rayon data")
  public void theResponseShouldContainArrayOfCallPlanByRayonData() {
    Response response = TestContext.getResponse();
    response.then().body("data", is(notNullValue()));
  }

  @And("call plan by rayon data should match schema {string}")
  public void callPlanByRayonDataShouldMatchSchema(String schemaFile) {
    Response response = TestContext.getResponse();
    ObjectMapper mapper = new ObjectMapper();
    try {
      String jsonObject = mapper.writeValueAsString(response.jsonPath().getList("data"));
      assertThat(jsonObject, matchesJsonSchemaInClasspath("schemas/" + schemaFile));
    } catch (JsonProcessingException e) {
      fail("Failed to parse response data: " + e.getMessage());
    }
  }

  @And("the response should contain empty array of call plan by rayon data")
  public void theResponseShouldContainEmptyArrayOfCallPlanByRayonData() {
    Response response = TestContext.getResponse();
    response.then().body("data", hasSize(0));
  }
}