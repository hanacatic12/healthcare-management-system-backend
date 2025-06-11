# Healthcare Management System

A comprehensive healthcare management system built with Spring Boot that provides a robust solution for managing patient records, appointments, medical documentation, and healthcare operations.

## Overview

This system is designed to streamline healthcare operations by providing a centralized platform for managing patient information, appointments, medical records, and documentation. It follows modern software development practices and implements industry-standard security measures.

## Core Features

### Patient Management
- Comprehensive patient profiles with medical history
- Appointment tracking and scheduling
- Medical diagnosis tracking
- Document management for patient records
- Last visit tracking and follow-up management
- Medical history tracking
- Patient statistics and analytics

### Doctor Management
- Doctor registration and profiles
- Department management
- Doctor availability tracking
- Doctor scheduling system
- Performance analytics

### Appointment System
- Appointment scheduling and management
- Appointment types and statuses
- Appointment search and filtering
- Appointment statistics
- Appointment history tracking
- Calendar integration
- Appointment reminders

### Document Management
- Medical document storage
- Secure document transfer system
- Document version control
- Document search and categorization
- Document access analytics
- Document versioning
- Access control

### Diagnosis Management
- Diagnosis recording and tracking
- Diagnosis statistics
- Diagnosis history
- Diagnosis notes and comments
- Medical condition tracking

### Additional Features
- Role-based access control
- JWT-based authentication
- Secure password management
- Audit logging
- Data export capabilities
- Integration with external systems
- Customizable reports and analytics
- Mobile-responsive API endpoints

## Technical Architecture

### Backend Stack
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Security
- JWT Authentication
- Lombok
- Maven
- PostgreSQL

### Database Structure
- Entity-Relationship Model:
  - Patient (One-to-Many with Appointments, Diagnoses)
  - Doctor (One-to-Many with Appointments)
  - Appointment (Many-to-One with Patient, Doctor)
  - Diagnosis (Many-to-One with Patient)
  - Department (One-to-Many with Doctor)
  - Document (Many-to-One with Patient, Doctor)
  - User (One-to-One with Patient/Doctor)

### Security Architecture
- JWT-based authentication
- Role-based access control
- Secure password hashing
- CORS configuration
- CSRF protection
- XSS prevention
- Rate limiting
- Security headers

### API Design
- RESTful architecture
- Comprehensive error handling
- Consistent response format
- Versioning support
- Rate limiting
- Input validation
- Caching support

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8.x or higher
- PostgreSQL 14.x or higher
- Git

### Installation
1. Clone the repository:
```bash
git clone [repository-url]
cd healthcare-management-system-backend
```

2. Build the project:
```bash
./mvnw clean install
```

3. Configure the application:
- Copy `application.properties.example` to `application.properties`
- Update database connection settings
- Configure JWT secret
- Set up security settings

4. Run the application:
```bash
./mvnw spring-boot:run
```

### Development Setup
For development purposes:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## API Documentation

The API documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

### Authentication Endpoints
- POST /api/auth/login - User login
- POST /api/auth/register - User registration
- POST /api/auth/refresh - Token refresh
- POST /api/auth/reset-password - Password reset

### Patient Management Endpoints
- GET /api/patients - List all patients
- POST /api/patients - Create new patient
- GET /api/patients/{id} - Get patient details
- PUT /api/patients/{id} - Update patient
- DELETE /api/patients/{id} - Delete patient

### Doctor Management Endpoints
- GET /api/doctors - List all doctors
- POST /api/doctors - Create new doctor
- GET /api/doctors/{id} - Get doctor details
- PUT /api/doctors/{id} - Update doctor
- DELETE /api/doctors/{id} - Delete doctor

### Appointment Management Endpoints
- GET /api/appointments - List appointments
- POST /api/appointments - Create appointment
- GET /api/appointments/{id} - Get appointment details
- PUT /api/appointments/{id} - Update appointment
- DELETE /api/appointments/{id} - Cancel appointment

### Document Management Endpoints
- GET /api/documents - List documents
- POST /api/documents - Upload document
- GET /api/documents/{id} - Get document
- PUT /api/documents/{id} - Update document
- DELETE /api/documents/{id} - Delete document

## Contributing

1. Fork the repository
2. Create a feature branch
3. Implement your changes
4. Add tests
5. Update documentation
6. Submit a pull request

### Code Style
- Follow Spring Boot conventions
- Use Lombok annotations
- Implement proper error handling
- Add comprehensive Javadoc
- Follow RESTful API design principles
- Write unit tests
- Follow security best practices

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please:
1. Check the existing issues
2. Open a new issue if your problem is not listed
3. Provide detailed information about the problem
4. Include error logs if available

## Version History

- 1.0.0 - Initial Release
  - Basic patient management
  - Appointment system
  - Document management
  - Security implementation

- 1.1.0 - Enhanced Features
  - Improved patient tracking
  - Enhanced appointment management
  - Document versioning
  - Security enhancements

## Contact

For questions or support, please contact:
- Email: support@healthcare-system.com
- GitHub: @healthcare-system-backend

## Acknowledgments

- Spring Boot Framework
- Spring Security Team
- Lombok Project
- All contributors and users

## Code of Conduct

Please note that this project is released with a Contributor Code of Conduct. By participating in this project you agree to abide by its terms.

## 🛠️ Technical Architecture

### Backend Architecture
-  Spring Boot 3.4.5
-  RESTful API design
-  Spring Security with JWT
-  Spring Data JPA
-  Lombok for reduced boilerplate

### Database Structure
-  PostgreSQL database
-  Entity-Relationship Model:
  - Patient (One-to-Many with Appointments, Diagnoses)
  - Doctor (One-to-Many with Appointments)
  - Appointment (Many-to-One with Patient, Doctor)
  - Diagnosis (Many-to-One with Patient)
  - Department (One-to-Many with Doctor)
  - Document (Many-to-One with Patient, Doctor)

### Security Architecture
-  JWT-based authentication
-  Spring Security configuration
-  Custom authentication manager
-  CORS configuration
-  Custom authentication entry point

### Service Layer
-  Business logic implementation
-  Service interfaces and implementations
-  Data validation
-  Business rules enforcement
-  Security checks

### Repository Layer
-  JPA Repository implementation
-  Custom query methods
-  Entity relationships management
-  Transaction management
-  Data access optimization

##  Project Structure

```
src/main/java/
├── com.healthcare.system
│   ├── config/         # Application configurations
│   │   ├── SecurityConfig.java      # Security configuration
│   │   ├── WebSecurityConfig.java   # Web security configuration
│   │   └── CorsConfig.java          # CORS configuration
│   │
│   ├── controllers/     # REST controllers
│   │   ├── AuthController.java      # Authentication endpoints
│   │   ├── PatientAppointmentsController.java  # Patient appointment management
│   │   ├── DocAppointmentsController.java     # Doctor appointment management
│   │   ├── DocumentTransferController.java    # Document management
│   │   └── ProfileController.java            # User profile management
│   │
│   ├── models/          # Data models
│   │   ├── entities/    # JPA entities
│   │   │   ├── Patient.java
│   │   │   ├── Doctor.java
│   │   │   ├── Appointment.java
│   │   │   └── Document.java
│   │   │
│   │   └── dtos/       # Data Transfer Objects
│   │       ├── PatientResponse.java
│   │       ├── DoctorDto.java
│   │       └── AppointmentDto.java
│   │
│   ├── repositories/   # Database repositories
│   │   ├── PatientRepository.java
│   │   ├── DoctorRepository.java
│   │   └── AppointmentsRepository.java
│   │
│   └── services/       # Business services
│       ├── PatientAppointmentService.java
│       ├── DocumentTransferService.java
│       └── CustomUserDetailsService.java
```

##  Security Features

### Authentication & Authorization
-  JWT-based token authentication
-  Role-based access control (RBAC)
-  Refresh token mechanism
-  Token expiration and renewal
-  Custom authentication manager
-  Secure password hashing with BCrypt

### Security Configuration
-  Custom security configuration
-  CORS policy implementation
-  CSRF protection
-  XSS prevention
-  Custom authentication entry point
-  Rate limiting

##  API Documentation & Endpoints

### Authentication Endpoints
- POST /api/auth/login - User login
- POST /api/auth/register - User registration
- POST /api/auth/refresh - Token refresh
- POST /api/auth/reset-password - Password reset

### Patient Management Endpoints
- GET /api/patients - List all patients
- POST /api/patients - Create new patient
- GET /api/patients/{id} - Get patient details
- PUT /api/patients/{id} - Update patient
- DELETE /api/patients/{id} - Delete patient

### Doctor Management Endpoints
- GET /api/doctors - List all doctors
- POST /api/doctors - Create new doctor
- GET /api/doctors/{id} - Get doctor details
- PUT /api/doctors/{id} - Update doctor
- DELETE /api/doctors/{id} - Delete doctor

### Appointment Management Endpoints
- GET /api/appointments - List appointments
- POST /api/appointments - Create appointment
- GET /api/appointments/{id} - Get appointment details
- PUT /api/appointments/{id} - Update appointment
- DELETE /api/appointments/{id} - Cancel appointment

### Document Management Endpoints
- GET /api/documents - List documents
- POST /api/documents - Upload document
- GET /api/documents/{id} - Get document
- PUT /api/documents/{id} - Update document
- DELETE /api/documents/{id} - Delete document

##  Contributing Guidelines

### Development Process
1. Create an issue for new features or bug fixes
2. Fork the repository
3. Create a feature branch
4. Add tests for new features
5. Update documentation
6. Submit a pull request

### Code Style
- Follow Spring Boot conventions
- Use Lombok annotations
- Implement proper error handling
- Add comprehensive Javadoc
- Follow RESTful API design principles

## 📄 Project Dependencies

### Core Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Test
- PostgreSQL Driver
- Lombok
- JWT Libraries
- Jackson JSON Processing
- MS Auth Client Integration

### Testing Dependencies
- JUnit
- Spring Security Test
- Mockito
- H2 Database (for testing)

##  Support & Maintenance

### Getting Help
- Open an issue in the repository
- Check the documentation
- Review the code examples
- Join our community forums

### Maintenance
- Regular security updates
- Performance monitoring
- Bug fixes
- Feature enhancements
- Documentation updates

##  Future Development

### Planned Features
- Real-time appointment notifications
- Patient portal integration
- Mobile app integration
- Advanced reporting system
- AI-powered diagnosis assistance
- Integration with medical devices

### Technical Improvements
- Implement caching layer
- Add database indexing
- Improve API response times
- Add more comprehensive testing
- Implement API versioning
- Add more security features

### Community Goals
- Build a strong developer community
- Create comprehensive documentation
- Develop integration guides
- Provide sample implementations
- Create API client libraries
- Develop mobile app SDK

## 🛠 Tech Stack

- Spring Boot 3.4.5
- Java 17
- PostgreSQL
- Spring Security
- JWT Authentication
- Spring Data JPA
- Lombok for Boilerplate Reduction

##  Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- PostgreSQL Driver
- JWT Libraries
- Jackson JSON Processing
- MS Auth Client Integration

##  Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- PostgreSQL 12 or higher

##  Setup Instructions

1. Clone the repository
```bash
git clone <repository-url>
cd healthcare-management-system-backend
```

2. Configure Database
   - Create a PostgreSQL database
   - Update `application.properties` with database credentials

3. Build the Project
```bash
./mvnw clean install
```

4. Run the Application
```bash
./mvnw spring-boot:run
```

## 🛠 Project Structure

```
src/main/java/
├── com.healthcare.system
│   ├── config/         # Application configurations
│   ├── controller/     # REST controllers
│   ├── model/          # Data models
│   ├── repository/     # Database repositories
│   ├── service/        # Business logic
│   └── security/       # Security configurations
```

##  Security

- JWT-based authentication
- Role-based access control
- Secure password hashing
- CSRF protection
- XSS prevention

##  API Documentation

The API documentation is available at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/v3/api-docs`

##  Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request


## 📈 Future Improvements

- Implement more comprehensive error handling
- Add more comprehensive testing
- Improve API documentation
- Add more security features
- Implement caching mechanisms
