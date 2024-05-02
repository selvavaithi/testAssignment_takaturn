# Project Setup Guide

## Prerequisites
Before you begin, ensure you have the following installed:
- **Java SDK**: Version 8 or above. You can download it from [Oracle's official site](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
- **IntelliJ IDEA**: If you don't have IntelliJ installed, follow the [official guide](https://www.jetbrains.com/idea/download/) to install it.
- **TestNG**: This will be automatically set up when you import the Maven project.

## Installation and Setup

### 1. Install IntelliJ IDEA
For detailed installation instructions, visit the [IntelliJ IDEA installation guide](https://www.jetbrains.com/idea/download/).

### 2. Import the Maven Project
Open IntelliJ IDEA and select `File > Open` and choose the project directory. IntelliJ will automatically detect the `pom.xml` file and set up the project for you.

## How to Run Tests

Execute tests using the following command in IntelliJ's terminal:

```bash
clean test -Denv=test -Dbrowser=chrome -Duser=<Username or Email> -Dpass=<Password> -DsuiteXmlFile=src\test\resources\testng.xml
```
Replace `<Username or Email>` and `<Password>` with your actual credentials.

## Viewing Reports

After running the tests, you can view the ExtentReport and HTML report at:
```
Takaturn.test\target\test-output
```
Simply navigate to this directory in your project folder to access the reports.
