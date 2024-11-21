package com.callplan.testing.steps.api;

import com.callplan.testing.steps.TestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CreateDoctorLeaveSteps {
  private final RequestSpecification request;

  public CreateDoctorLeaveSteps() throws IOException {
    this.request = TestContext.getRequest();
  }

  @When("I send POST request to \\/create-doctor-leave with payload:")
  public void iSendPOSTRequestToCreateDoctorLeaveWithPayload(String docString) throws JsonProcessingException {
    Map<String, Object> jsonPayload = new ObjectMapper().readValue(docString, Map.class);

    Response response = request
      .body(jsonPayload)
      .post("/create-doctor-leave");

    TestContext.setResponse(response);
  }

  @Given("Leave period is not set yet for the selected date:")
  public void leavePeriodIsNotSetYetForTheSelectedDate(DataTable dataTable) throws JsonProcessingException {
    Map<String, Object> responseMap = checkLeavePeriodOverlap(dataTable);
    
    if ((boolean) responseMap.get("success")) {
      Map<String, Object> payload = dataTable.asMap(String.class, Object.class);
      List<Map<String, String>> leaves = (List<Map<String, String>>) responseMap.get("data");
      
      if (hasOverlappingLeave((String) payload.get("leaveStart"), (String) payload.get("leaveEnd"), leaves)) {
        fail("Leave period overlaps with existing leave");
      }
    }
  }

  @Given("Leave period is already set for the selected date:")
  public void leavePeriodIsAlreadySetForTheSelectedDate(DataTable dataTable) throws JsonProcessingException {
    Map<String, Object> responseMap = checkLeavePeriodOverlap(dataTable);
    
    if ((boolean) responseMap.get("success")) {
      Map<String, Object> payload = dataTable.asMap(String.class, Object.class);
      List<Map<String, String>> leaves = (List<Map<String, String>>) responseMap.get("data");
      
      assertTrue(hasOverlappingLeave((String) payload.get("leaveStart"), (String) payload.get("leaveEnd"), leaves));
    }
  }

  private Map<String, Object> checkLeavePeriodOverlap(DataTable dataTable) throws JsonProcessingException {
    Map<String, Object> dataTablePayload = dataTable.asMap(String.class, Object.class);
    Map<String, Object> jsonPayload = new HashMap<>();
    jsonPayload.put("personId", dataTablePayload.get("personId"));
    jsonPayload.put("rayonCode", dataTablePayload.get("rayonCode"));
    
    Response response = request.body(jsonPayload).post("/get-one-year-leave-history");
    return new ObjectMapper().readValue(response.getBody().asString(), Map.class);
  }

  private boolean hasOverlappingLeave(String requestStart, String requestEnd, List<Map<String, String>> leaves) {
    LocalDate start = LocalDate.parse(requestStart);
    LocalDate end = LocalDate.parse(requestEnd);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    for (Map<String, String> leave : leaves) {
      LocalDate existingStart = LocalDate.parse(leave.get("LEAVE_START"), formatter);
      LocalDate existingEnd = LocalDate.parse(leave.get("LEAVE_END"), formatter);
      
      if (!(end.isBefore(existingStart) || start.isAfter(existingEnd))) {
        return true;
      }
    }
    return false;
  }
}