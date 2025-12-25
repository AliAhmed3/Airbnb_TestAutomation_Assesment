# 🏠 Airbnb Automation Testing Assessment

## 📖 Overview
This repository contains automated UI tests for selected features of the Airbnb web application.  
The tests are implemented using **Java**, **Selenium WebDriver**, **TestNG**, and **Maven**, and focus on validating search results behavior and filter functionality.

---

## 🛠 Tech Stack
- **Java** – Programming language
- **IntelliJ IDEA** – IDE
- **Selenium WebDriver** – Web automation
- **TestNG** – Test execution framework
- **Maven** – Build & dependency management
- **Page Object Model (POM)** – Test design pattern
- **TestNG Listeners** – Execution tracking & reporting hooks

---

## ⚙️ Setup & Installation

### 1️⃣ Prerequisites
Ensure the following are installed on your machine:
- **Java JDK 21** → [Download](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- **Maven** → [Download](https://maven.apache.org/)

## 2️⃣ Clone the Repository
- ```git clone https://github.com/AliAhmed3/Airbnb_TestAutomation_Assesment.git```
- ```cd Airbnb_TestAutomation_Assesment.git```

## 3️⃣ Install Dependencies
```mvn clean install```

# 🚀 Running the Tests
- Option 1: run testNG.xml file

- Option 2: Run all tests using Maven
```mvn clean test```

- Option 3: Run a specific test class
```mvn test -Dtest=SearchResultTest```
```mvn test -Dtest=ExtraFilterTest```


# 📂 Project Structure
```
Airbnb_TestAutomation_Assesment/
│
├── 📜 pom.xml                          # Maven configuration & dependencies
├── 📜 testNG.xml                       # TestNG suite configuration
├── 📜 .gitignore                       # Git ignore rules
│
├── 📂 src
│   ├── 📂 main
│   │   ├── 📂 java
│   │   │   ├── 📂 drivers
│   │   │   │   └── DriverFactory.java  # WebDriver initialization & management
│   │   │   │
│   │   │   ├── 📂 pages                # Page Object Model (POM) classes
│   │   │   │   ├── HomePage.java       # Airbnb home page actions & locators
│   │   │   │   ├── SearchResultsPage.java # Search results page interactions
│   │   │   │   └── PropertyDetailsPage.java # Property details page actions
│   │   │   │
│   │   │   └── 📂 utils
│   │   │       ├── DateUtils.java      # Date handling utilities
│   │   │       ├── JSActions.java      # JavaScript-based actions
│   │   │       ├── JsonUtils.java      # JSON test data reader
│   │   │       ├── PropertiesUtils.java # Reads environment.properties
│   │   │       └── WaitUtility.java    # Explicit wait helpers
│   │   │
│   │   └── 📂 resources
│   │       └── META-INF/services
│   │           └── org.testng.ITestNGListener # Registers TestNG listeners
│   │
│   └── 📂 test
│       ├── 📂 java
│       │   ├── 📂 listeners
│       │   │   └── TestNGListeners.java # TestNG execution listeners
│       │   │
│       │   └── 📂 tests
│       │       ├── BaseTest.java       # Common test setup & teardown
│       │       ├── SearchResultTest.java # Search results test scenarios
│       │       └── ExtraFilterTest.java # Extra filters test scenarios
│       │
│       └── 📂 resources
│               └── test-data.json          # Test input data
│
│           └── 📂 TestData
│               ├── environment.properties # Environment configuration
├── 📂 target                           # Maven output folder (auto-generated)
│   ├── 📂 surefire-reports              # TestNG execution reports
│   
│
```
# 🔄 Test Data
- **Test input data is managed via ```test-data.json```**

