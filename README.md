# Why This Framework Exists

This framework exists to demonstrate **how test automation should be structured, maintained, and reasoned about in a real production environment**.

**JavaSeleniumCucumberAutomationFramework** was built to showcase:

* How I implement and extend an existing automation solution, not just write isolated tests
* Clean separation between **test intent**, **test execution**, and **system interaction**
* Practical decisions around **when to favor API automation over UI automation**
* Patterns that scale as test coverage and teams grow

This project is intentionally opinionated. It reflects the kinds of tradeoffs, guardrails, and structure used in enterprise test automation—not toy examples or tutorial-style demos.

---

# JavaSeleniumCucumberAutomationFramework – Test Automation Framework
**An API-first, hybrid UI + API automation framework built in Java using Selenium, RestAssured, and Cucumber**

## Overview

**JavaSeleniumCucumberAutomationFramework** is a hybrid test automation framework built in **Java** that supports both **UI testing** and **API testing** using modern industry tools and design patterns.

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

* Maintainable test architecture
* Scalable UI and API automation
* Clean separation of concerns
* Reliable test execution in CI environments
* Reusable and extensible test components

The project reflects real-world QA engineering practices rather than simple test scripting.

---

## Quick Start

Clone the project and run all tests:

```bash
git clone https://github.com/michaelFisher-qaEngineer/JavaSeleniumCucumberAutomationFramework.git
cd JavaSeleniumCucumberAutomationFramework
mvn clean test
```

This executes all UI and API scenarios using the MavenTestRunner.

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

[https://www.saucedemo.com/](https://www.saucedemo.com/)

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

[https://reqres.in/](https://reqres.in/)

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
│       ├── listeners/         # Test lifecycle listeners (logging, reporting, screenshots, execution hooks)
│       ├── pages/             # Page Object Model classes (UI)
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

---

## Secure Credential Handling (Important)

This framework intentionally **does not store secrets (passwords, tokens, API keys) in Gherkin feature files or source code**.

### How credentials are handled

* Gherkin feature files use **logical placeholders** (lookup keys) instead of real secrets
* Step definitions resolve those keys at runtime from property files or environment variables
* Real credentials are **never committed to source control**

### Example: Gherkin (no secrets)

```gherkin
Given I login successfully with the following data
  | Email              | Password          |
  | eve.holt@reqres.in | validApiPassword  |
```

Here, `validApiPassword` is a **lookup key**, not the real password.

### Properties file (gitignored)

```properties
validApiPassword=passwordGoesHere
```

A template file is provided instead:

```properties
# TestData.properties.template
validApiPassword=
```

### Supported scenarios

* Lookup-based passwords (recommended)
* Explicit empty passwords for negative testing
* Clear validation errors if a lookup key is missing

This approach mirrors **enterprise CI/CD practices**, keeps feature files business-readable, and prevents accidental credential leaks.

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

* A dedicated MavenTestRunner ensures a single source of test execution in CI/CD and command-line runs
* Maven Surefire is configured to execute only this runner
* Tag filtering (`@UI`, `@API`) controls which scenarios execute

Additional runners exist only for IDE debugging and development workflows.

---

## Configuration

### API Key Setup

This framework requires a ReqRes API key for API tests.

For security reasons, the actual `TestData.properties` file is **not committed** to the repository.

#### Setup Instructions

1. Navigate to: `src/test/resources/testdata/`
2. Copy the template file: `TestData.properties.template`
3. Rename the copy to: `TestData.properties`
4. Add your ReqRes API key:

```properties
reqresApiKey=your_api_key_here
```

> ⚠️ The real `TestData.properties` file is gitignored to prevent committing secrets.

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
* Secure credential handling

---

## Author

Michael Fisher — Software Test Engineer

20+ years QA experience | Automation | Selenium | API Testing

---

## License

This project is for demonstration and educational purposes.
