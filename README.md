# Learnify LMS

Learnify is a Learning Management System (LMS) that allows users to join groups, participate in discussions, and access various features related to group-based learning. The system is designed as a microservice architecture, with backend services built using Spring Boot and PostgreSQL.

## Features

- **Group Management**: Create and join groups for specific subjects or topics.
- **Channel Discussions**: Post and engage in discussions within group-specific channels.
- **User-Friendly Interface**: Dark-themed, modern UI for seamless interaction.
- **Content Sharing**: Share and manage content within groups.
- **Event and Member Management**: Manage events and view group members.

## Screenshots

### Posting in a Channel

![Post in Channel](./assets/Screenshot_from_2024-12-29_12-45-31.png)

### Group View

![Group View](./assets/Screenshot_from_2024-12-29_12-43-27.png)

### Group Discussions

![Group Discussions](./assets/Screenshot_from_2024-12-29_12-43-17.png)

## Tech Stack

### Backend

- **Framework**: Spring Boot
- **Database**: PostgreSQL

### Frontend

- Modern UI with responsive design (framework/library not specified).

## Getting Started

### Prerequisites

- **Java 17+**
- **PostgreSQL 14+**
- **Node.js and npm** (if frontend is included as a separate service)

### Installation

#### Backend Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/learnify-lms.git
   cd learnify-lms
   ```

2. Configure the database:

   - Update the `application.properties` or `application.yml` file in the `src/main/resources` folder with your PostgreSQL credentials:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/learnify
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

#### Frontend Setup

1. Navigate to the frontend directory (if applicable):

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

### Running the Application

- Access the application at `http://localhost:3000`.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request for review.

## License

This project is licensed under the [MIT License](./LICENSE).
