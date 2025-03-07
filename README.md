
# Role Master Project

## Overview
This project implements **Role-Based Access Control (RBAC)** where a **superadmin** has full control and **child admins** (such as salesadmin, supportadmin) have specific permissions. The system dynamically **controls UI elements and API access** based on assigned privileges.

## Tech Stack
- **Backend:** Java, Spring Boot, MySQL
- **Frontend:** React.js
- **Database:** MySQL

## Features
- **User Authentication:** Secure login system
- **Role Management:** Superadmin can create/manage roles
- **Privilege Assignment:** Define what actions each role can perform
- **Dynamic UI Rendering:** React frontend adapts based on user role
- **Secure API:** Spring Boot backend ensures role-based access control

## Installation & Setup

### Backend (Spring Boot)
1. Navigate to the backend directory:
   \`\`\`bash
   cd backend
   \`\`\`
2. Configure **application.properties** with your database credentials:
   \`\`\`properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=root
   spring.datasource.password=your_password
   \`\`\`
3. Run the application:
   \`\`\`bash
   mvn spring-boot:run
   \`\`\`

### Frontend (React.js)
1. Navigate to the frontend directory:
   \`\`\`bash
   cd frontend
   \`\`\`
2. Install dependencies:
   \`\`\`bash
   npm install
   \`\`\`
3. Start the React app:
   \`\`\`bash
   npm start
   \`\`\`

## Database Schema
### Tables:
- \`users\` (user_id, username, password, role_id)
- \`roles\` (role_id, role_name)
- \`privileges\` (privilege_id, privilege_name)
- \`role_privileges\` (role_id, privilege_id)

## API Endpoints
| Method | Endpoint                 | Description              |
|--------|--------------------------|--------------------------|
| GET    | \`/api/users\`             | Get all users           |
| POST   | \`/api/users\`             | Create a new user       |
| GET    | \`/api/roles\`             | Get all roles           |
| POST   | \`/api/roles\`             | Create a new role       |
| GET    | \`/api/privileges\`        | Get all privileges      |
| POST   | \`/api/role-privileges\`   | Assign privileges to role |

## Uploading to GitHub
1. **Initialize a Git Repository**  
   \`\`\`bash
   git init
   \`\`\`
2. **Add Remote Repository**  
   \`\`\`bash
   git remote add origin https://github.com/your-username/your-repo.git
   \`\`\`
3. **Add Files & Commit**  
   \`\`\`bash
   git add .
   git commit -m "Initial commit"
   \`\`\`
4. **Push to GitHub**  
   \`\`\`bash
   git branch -M main
   git push -u origin main
   \`\`\`

## Contributors
- [Nikhil Barapatre] (https://github.com/nikhilcyb)
