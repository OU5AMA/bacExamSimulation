# BacSimulationExam - Moroccan Baccalaureate Exam Simulator

![Project Banner](https://via.placeholder.com/800x200?text=Bac+Exam+Simulator)

A monolithic Spring Boot + React application for simulating Moroccan baccalaureate exams.

## 🚀 Features
- Question bank management
- Exam simulation with time limits
- Automatic grading system
- Admin dashboard (Future)

## 🛠️ Tech Stack
- **Backend**: Java 17, Spring Boot 3.x, PostgreSQL
- **Frontend**: React 18, TypeScript, Tailwind CSS
- **Tools**: Maven, IntelliJ IDEA

## 📦 Installation (Local Development)

### Prerequisites
- Java 17
- Node.js 18+
- PostgreSQL 15

### Steps
1. **Database Setup**
   ```bash
   sudo -u postgres createdb bac_exam_db
   sudo -u postgres psql -c "ALTER USER postgres WITH PASSWORD 'password';"