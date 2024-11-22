package com.callplan.testing.steps.api;

import com.callplan.testing.steps.TestContext;
import com.callplan.testing.utils.AllureUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Map;

public class SaveBestTimeVisitSteps {
  private final RequestSpecification request;

  public SaveBestTimeVisitSteps() throws IOException {
    this.request = TestContext.getRequest();
  }

  @When("I send POST request to save-best-time-visit with payload:")
  public void iSendPOSTRequestToSaveBestTimeVisitWithPayload(String docString) throws JsonProcessingException {
    Map<String, Object> jsonPayload = new ObjectMapper().readValue(docString, Map.class);

    AllureUtils.attachJsonToAllure("Request Payload", jsonPayload);
    
    Response response = request
      .body(jsonPayload)
      .post("/save-best-time-visit");
        
    TestContext.setResponse(response);
  }
}