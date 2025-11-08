
# Company Wex Project

## Overview
This project is a backend application designed to handle purchase transactions and currency exchange conversions. It provides a robust implementation with test coverage for critical services.

## Features
- Handle purchase transactions with CRUD operations.
- Convert transaction amounts using currency exchange rates.
- Integrated with external APIs for live currency rates.
- Comprehensive unit tests using JUnit and WireMock.

## Prerequisites
- **Java 17** or higher
- **Maven 3.8** or higher

## Setup Instructions
1. Clone the repository or extract the ZIP file.
2. Navigate to the project directory:
   ```bash
   cd company-wex
   ```
3. Install dependencies and build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Running Tests
To execute unit tests:
```bash
mvn test
```

## Key Components
### Service Implementation
- **`PurchaseTransactionServiceImpl`**: Handles transaction management and currency conversions.

### Test Frameworks
- **JUnit 5**: Used for unit testing.
- **WireMock**: Simulates external API calls for currency exchange.

### API Endpoints
- **Base URL**: `http://localhost:8080`
- **Endpoints**:
  - `/transactions` (GET, POST)
  - `/transactions/convert` (POST)

## Dependencies
- **Spring Boot**: Application framework.
- **Mockito**: Mocking framework for unit tests.
- **WireMock**: Mock server for simulating external APIs.

## Folder Structure
- `src/main/java`: Application source code.
- `src/test/java`: Unit tests.

## Contribution Guidelines
1. Fork the repository.
2. Create a feature branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m "Add feature"`.
4. Push the branch: `git push origin feature-name`.
5. Open a pull request.

## License
This project is licensed under the MIT License.
