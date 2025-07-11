 🔍 GitHub Repository Search API (Spring Boot + PostgreSQL)

This Spring Boot project allows you to search for GitHub repositories using the GitHub REST API, store them in a PostgreSQL database, and retrieve the stored repositories using filter criteria.

---

 🚀 Features

- ✅ Search GitHub repositories using keywords, programming language, and sort order
- ✅ Filter stored repositories by language, minimum stars, and sort
- ✅ RESTful API design with proper error handling
- ✅ Testable using Postman 


---

 🧱 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- GitHub REST API v3

---

 🛠️ Setup Instructions

 📦 Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL (running with a database created)

---

 ⚙️ 1. Clone the Repository

```bash
git clone https://github.com/your-username/github-repo-search-api.git
cd github-repo-search-api

---

Configure PostgreSQL
Update application.properties or application.yml:


spring.datasource.url=jdbc:postgresql://localhost:5432/githubdb
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update

---
📬 API Usage
1️⃣ POST /api/github/search
Description: Searches GitHub repositories and stores them in the database.

Request URL:

bash


POST http://localhost:8080/api/github/search
Request Body:

json


{
  "query": "spring boot",
  "language": "Java",
  "sort": "stars"
}
Response:

json


{
  "message": "Repositories fetched and saved successfully",
  "repositories": [
    {
      "id": 123456,
      "name": "spring-boot-example",
      "description": "An example Spring Boot repo",
      "owner": "user123",
      "language": "Java",
      "stars": 456,
      "forks": 99,
      "lastUpdated": "2024-01-01T12:00:00Z"
    }
  ]
}
2️⃣ GET /api/github/repositories
Description: Retrieves stored repositories with optional filters.

Request URL:

bash


GET http://localhost:8080/api/github/repositories?language=Java&minStars=100&sort=stars
Query Parameters:

language (optional): Filter by programming language

minStars (optional): Minimum number of stars

sort (optional): Sort by stars, forks, or updated

Response:

json


{
  "repositories": [
    {
      "id": 123456,
      "name": "spring-boot-example",
      "description": "An example Spring Boot repo",
      "owner": "user123",
      "language": "Java",
      "stars": 456,
      "forks": 99,
      "lastUpdated": "2024-01-01T12:00:00Z"
    }
  ]
}
