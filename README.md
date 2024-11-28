# Callplan API Testing

This project contains automated API tests for the Callplan application using Cucumber, JUnit, and Allure for reporting.

## 🔍 Overview

The test suite covers various API endpoints related to callplan functionality including:
- Doctor leave management
- Best time visit operations
- Feedback questions
- Master list data
- Call plan recommendations
- Public holiday information

## 🛠️ Tech Stack

- Java 17
- Maven
- Cucumber
- JUnit
- Allure Report
- GitHub Actions

## 📋 Prerequisites

- JDK 17
- Maven
- Git

## ⚙️ Configuration

1. Create a `.env` file in `src/test/resources/` with:

```properties
BASE_URL=your_api_base_url
API_KEY=your_api_key
```

## 🚀 Running Tests

Execute tests using Maven:

```bash
./mvnw test
```

## 📊 Test Report

The Allure test report is automatically generated and published to GitHub Pages after each push to the main branch. 

View the latest test report here: [Callplan API Testing Report](https://ayaayawae-dxg.github.io/callplan-api-testing)

## 🔄 CI/CD

This project uses GitHub Actions for continuous integration. The workflow:
1. Runs on push to main branch
2. Sets up JDK 17
3. Creates environment file
4. Executes tests
5. Generates Allure report
6. Publishes report to GitHub Pages

## 📝 Test Scenarios

The test suite includes both positive and negative scenarios for:

- Doctor Leave Management
  - Create doctor leave
  - Get one year leave history
  
- Visit Management
  - Get best time visit data
  - Save best time visit preferences
  
- Master Data
  - Get master list
  - Get outlet information
  - Get public holidays