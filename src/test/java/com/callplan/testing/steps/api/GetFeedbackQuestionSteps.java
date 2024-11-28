package com.callplan.testing.steps.api;

import com.callplan.testing.steps.TestContext;
import com.callplan.testing.utils.AllureUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;

public class GetFeedbackQuestionSteps {
  private final RequestSpecification request;

  public GetFeedbackQuestionSteps() throws IOException {
    this.request = TestContext.getRequest();
  }

  @When("I send GET request to feedback-question with query parameter:")
  public void iSendGETRequestToFeedbackQuestionWithQueryParameter(DataTable dataTable) throws IOException {
    Map<String, Object> queryParams = dataTable.asMap(String.class, Object.class);

    AllureUtils.attachJsonToAllure("Request Query Parameter", queryParams);

    Response response = request
      .queryParams(queryParams)
      .get("/feedback-question");

    TestContext.setResponse(response);
  }

  @And("the response should contain empty array of feedback question data")
  public void theResponseShouldContainEmptyArrayOfFeedbackQuestionData() {
    Response response = TestContext.getResponse();
    response.then().body("data", hasSize(0));
  }

  @And("the response should contain array of feedback question data")
  public void theResponseShouldContainArrayOfFeedbackQuestionData() {
    Response response = TestContext.getResponse();
    response.then().body("data", hasSize(greaterThan(0)));
  }

  @And("each feedback question data should match schema")
  public void eachFeedbackQuestionDataShouldMatchSchema() {
    Response response = TestContext.getResponse();
    ObjectMapper mapper = new ObjectMapper();
    try {
      String jsonArray = mapper.writeValueAsString(response.jsonPath().getList("data"));
      assertThat(jsonArray, matchesJsonSchemaInClasspath("schemas/GetFeedbackQuestion.json"));
    } catch (JsonProcessingException e) {
      fail("Failed to parse response data: " + e.getMessage());
    }
  }
}