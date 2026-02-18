# MissionQA – Test Automation Framework

## Overview

**MissionQA** is a hybrid test automation framework built in **Java** that supports both **UI testing** and **API testing** using modern industry tools and design patterns.

The framework demonstrates:

* UI automation using Selenium WebDriver
* API automation using RestAssured
* Behavior-Driven Development (BDD) with Cucumber
* Page Object Model (POM) architecture
* Reusable verification utilities
* Scalable test structure
* Maven-based build and dependency management

This project is designed to showcase real-world automation practices, maintainability, and clean framework design.

---
## Purpose

This framework was built to demonstrate production-grade test automation design using industry-standard patterns and tooling. The focus is on:

- Maintainable test architecture
- Scalable UI and API automation
- Clean separation of concerns
- Reliable test execution in CI environments
- Reusable and extensible test components

The project reflects real-world QA engineering practices rather than simple test scripting.

---
## Quick Start

Clone the project and run all tests:

```bash
git clone <your-repo-url>
cd MissionQA
mvn clean test
```
This executes all UI and API scenarios using the MavenTestRunner

---


## Tech Stack

* **Java 21**
* **Maven**
* **Selenium WebDriver 4**
* **RestAssured**
* **Cucumber (BDD)**
* **TestNG (Cucumber TestNG runner)**
* **WebDriverManager**
* **Log4j2**
* **Page Object Model (POM)**

---

## What This Framework Tests

### UI Testing (SauceDemo)

Tests the e-commerce workflow on:

https://www.saucedemo.com/

Coverage includes:

* Login
* Adding products to cart
* Removing items
* Cart quantity validation
* Checkout flow
* Item total calculation
* Tax validation

---

### API Testing (ReqRes)

Tests public REST APIs from:

https://reqres.in/

Coverage includes:

* List users (pagination validation)
* Single user retrieval
* Error handling (404 validation)
* User creation
* Login success and failure scenarios
* Delayed response handling
* Unique user ID verification

---

## Framework Architecture

The project follows standard enterprise test automation design patterns with clear separation of concerns and a scalable layered structure.

```
src
├── main
│   └── java/framework
│       ├── api/               # API client classes (ReqRes endpoints)
│       ├── config/            # Configuration and property utilities
│       ├── driver/            # Thread-safe WebDriver management
│       ├── listeners/         # # Test lifecycle listeners (logging, reporting, screenshots, execution hooks)
│       ├── pages/             # Page Object Model classes (UI)
│       ├── driver/            # Thread-safe WebDriver management
│       └── verifications/     # Reusable assertion helpers
│
├── test
│   ├── java/tests
│   │   ├── hooks/             # Test setup and teardown logic
│   │   ├── runners/           # Cucumber runner classes
│   │   │   ├── MavenTestRunner.java   # Primary runner used by Maven/CI
│   │   │   ├── TestUIRunner.java      # IDE-only UI runner
│   │   │   ├── TestAPIRunner.java     # IDE-only API runner
│   │   │   └── TestAllRunner.java     # IDE-only runner for all tests
│   │   │
│   │   └── stepdefs/          # Cucumber step definitions
│   │
│   └── resources
│       ├── features/          # Gherkin feature files
│       ├── testdata/          # Test data and configuration files
│       └── log4j2.xml         # Logging configuration
```

### Architecture Highlights

- **Page Object Model (POM)** — Separates UI interaction from test logic.
- **API Client Layer** — Encapsulates REST endpoint behavior and request configuration.
- **Driver Manager** — Provides thread-safe WebDriver lifecycle management.
- **Reusable Verifications** — Centralized assertion utilities to reduce duplication.
- **Cucumber BDD Structure** — Business-readable feature files mapped to step definitions.
- **Single Maven Runner Strategy** — `MavenTestRunner` ensures tests execute once in CLI and CI environments.


---

## Test Execution Flow

1. Maven triggers `MavenTestRunner`.
2. Cucumber loads feature files and scenario definitions.
3. Test hooks initialize WebDriver for UI tests or API clients for API tests.
4. Step definitions execute UI or API actions.
5. Verification utilities validate expected results.
6. Hooks perform cleanup and teardown.

This layered execution model ensures reliable, maintainable, and scalable test automation.

---


## Design Principles Used

### Page Object Model (POM)

* Separates test logic from UI interaction
* Improves maintainability and readability

### Base Client Pattern (API)

* Centralized API configuration
* Reusable request specifications

### Driver Manager

* Thread-safe WebDriver handling
* Prevents multiple browser instances

### Reusable Verifications

* Central assertion utilities
* Reduces duplicated test code

### BDD Structure

* Tests written in business-readable language
* Gherkin feature files
* Step definition mapping

### Test Execution Strategy
* A dedicated MavenTestRunner ensures a single source of test execution in CI/CD and command-line runs.
* Maven Surefire is configured to execute only this runner.
* Tag filtering (@UI, @API) controls which scenarios execute.
* The single runner strategy mirrors production CI pipelines by ensuring deterministic test execution and preventing duplicate runs.

Additional runners exist only for IDE debugging and development workflows.
This prevents duplicate execution and improves build reliability.

---

## Setup Instructions

### Prerequisites

* Java 21 installed
* Maven installed
* Chrome browser (for UI tests)

Verify:

```bash
java -version
mvn -version
```

---

### Clone Project

```bash
git clone <your-repo-url>
cd MissionQA
```

---

### Install Dependencies

```bash
mvn clean install
```

---

## Running Tests

### Run All Tests

```bash
mvn test
```

---

### Run UI Tests Only

```bash
mvn test -Dcucumber.filter.tags="@UI"
```

---

### Run API Tests Only

```bash
mvn test -Dcucumber.filter.tags="@API"
```

---
### Run from IDE

The project also includes runner classes for IDE convenience:

* TestUIRunner — UI tests
* TestAPIRunner — API tests
* TestAllRunner — all tests

These are not executed by Maven.

---

## Configuration

### API Key Setup

The framework expects an API key for ReqRes:

```
src/test/resources/testdata/TestData.properties
```

Example:

```
REQRES_API_KEY=your_api_key_here
```

---

## Example Test Scenarios

### UI Example

```
Given I am on the home page
And I login with valid credentials
And I add items to the basket
Then Item total will equal the sum of items
```

### API Example

```
Given I create a user
Then response should contain id and createdAt
```

---

## Key Features

* Hybrid UI + API automation
* Clean layered architecture
* Scalable test design
* Thread-safe driver management
* Reusable test utilities
* Data-driven testing support
* Clear separation of concerns

---

## Author

Michael Fisher — Software Test Engineer

20+ years QA experience | Automation | Selenium | API Testing

---

## License

This project is for demonstration and educational purposes.
