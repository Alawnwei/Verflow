# VeriFlow - AI-Driven Test Automation Platform

VeriFlow is an AI-powered test automation platform that transforms requirements into executable test scripts through intelligent analysis and automation.

## Features

- **Multi-source Requirements Parsing**: Support BSD, JIRA, Lark, Markdown, screenshots, Excel, Axure, Figma, and Modao
- **AI-Powered Test Case Generation**: Automatically generate test cases from requirements
- **Automated Script Generation**: Convert test cases into Playwright/Selenium scripts
- **MCP Integration**: Enhanced UI automation with Model Context Protocol
- **Self-healing Mechanism**: AI-powered script repair for failed tests
- **Test Orchestration**: Smoke testing, E2E testing, and regression testing
- **GitLab CI/CD Integration**: Seamless pipeline integration

## Tech Stack

### Backend
- **Framework**: Spring Boot 3.2.x
- **Language**: Java 21
- **Database**: PostgreSQL 15
- **Security**: JWT
- **Build**: Maven

### Frontend
- **Framework**: React 18.x
- **UI**: Ant Design 5.x
- **Build**: Vite 5.x
- **Styling**: Tailwind CSS 3.x

### DevOps
- **Container**: Docker
- **CI/CD**: GitLab CI

## Getting Started

### Prerequisites
- Java 21+
- Node.js 20+
- PostgreSQL 15+
- Docker (optional)

### Running with Docker

```bash
cd VeriFlow
docker-compose up -d
```

### Running Locally

#### Backend
```bash
cd backend
mvn spring-boot:run
```

#### Frontend
```bash
cd frontend
npm install
npm run dev
```

## Project Structure

```
VeriFlow/
├── backend/                    # Backend application
│   ├── src/main/java/com/example/veriflow/
│   │   ├── controller/        # REST API controllers
│   │   ├── service/           # Business logic
│   │   ├── repository/        # Data access layer
│   │   ├── entity/            # JPA entities
│   │   ├── dto/               # Data transfer objects
│   │   ├── config/            # Configuration classes
│   │   ├── exception/         # Custom exceptions
│   │   └── VeriFlowApplication.java
│   ├── src/main/resources/
│   │   ├── application.yml    # Application configuration
│   │   └── schema/            # Database schema
│   └── pom.xml
├── frontend/                   # Frontend application
│   ├── src/
│   │   ├── pages/             # Page components
│   │   ├── App.jsx            # Main app component
│   │   ├── main.jsx           # Entry point
│   │   └── index.css          # Global styles
│   ├── public/                # Static assets
│   ├── vite.config.js         # Vite configuration
│   ├── tailwind.config.js     # Tailwind configuration
│   ├── postcss.config.js      # PostCSS configuration
│   └── package.json
├── docker/                    # Docker configurations
├── docs/                      # Documentation
└── docker-compose.yml         # Docker Compose configuration
```

## API Endpoints

### Authentication
- `POST /api/auth/login` - Login
- `POST /api/auth/logout` - Logout

### Requirements
- `GET /api/requirements` - List requirements
- `POST /api/requirements` - Create requirement
- `GET /api/requirements/{id}` - Get requirement
- `PUT /api/requirements/{id}` - Update requirement
- `DELETE /api/requirements/{id}` - Delete requirement
- `POST /api/requirements/{id}/parse` - Parse requirement

### Test Cases
- `GET /api/test-cases` - List test cases
- `POST /api/test-cases` - Create test case
- `GET /api/test-cases/{id}` - Get test case
- `PUT /api/test-cases/{id}` - Update test case
- `DELETE /api/test-cases/{id}` - Delete test case
- `POST /api/test-cases/{id}/review` - Review test case
- `POST /api/test-cases/generate/{requirementId}` - Generate from requirement

### Test Scripts
- `GET /api/test-scripts` - List scripts
- `GET /api/test-scripts/{id}` - Get script
- `PUT /api/test-scripts/{id}` - Update script
- `DELETE /api/test-scripts/{id}` - Delete script
- `POST /api/test-scripts/generate/{caseId}` - Generate script
- `POST /api/test-scripts/{id}/repair` - Repair script

### Test Executions
- `GET /api/test-executions` - List executions
- `POST /api/test-executions/execute/{scriptId}` - Execute script
- `POST /api/test-executions/orchestrate/{type}` - Orchestrate tests
- `POST /api/test-executions/{id}/repair-retry` - Repair and retry

## Test Credentials

- **Email**: test@example.com
- **Password**: 123456

## License

This project is for internal use only.
