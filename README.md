# 🤖 AI Sales Intelligence Copilot

An AI-powered CRM pipeline management tool inspired by Salesforce Einstein. 
Built with Java Spring Boot, React, PostgreSQL, and Claude AI.

## 🎯 What It Does

- **Kanban Pipeline Board** — visualize deals across all sales stages
- **AI Copilot** — ask Claude AI questions about your pipeline and get instant analysis
- **Risk Detection** — AI identifies at-risk deals and recommends actions
- **Live Metrics** — real-time open pipeline value, closed won, and deal count
- **Full CRM Schema** — accounts, opportunities, and activities linked together

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21 + Spring Boot 3.5 |
| Database | PostgreSQL 16 |
| ORM | Spring Data JPA + Hibernate |
| Frontend | React + Vite |
| Styling | Tailwind CSS |
| AI | Anthropic Claude API |
| Version Control | Git + GitHub |

## 🤖 AI Features

The AI copilot uses Claude to analyze real pipeline data from PostgreSQL and provide:
- Deal risk assessment with specific recommendations
- Pipeline summaries and forecasting insights
- Personalized next-step guidance for each deal
- Weekly focus recommendations based on close dates and probability

Built with an **AI-first mindset** — Claude Code and Cursor were used throughout 
development to accelerate coding, with careful human review of all AI-generated code.

## 🏗️ Architecture
React Frontend (Vite) → Spring Boot REST API → PostgreSQL
↓
Claude AI API
(contextual pipeline analysis)
## 🚀 Getting Started

### Prerequisites
- Java 21
- Node.js 20+
- PostgreSQL 16
- Anthropic API key

### Backend Setup
```bash
cd backend
# Add your credentials to application.properties
./mvnw spring-boot:run
```

### Frontend Setup
```bash
cd frontend
npm install
npm run dev
```

### Environment Variables
Create `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/salesforce_copilot?sslmode=disable
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD
anthropic.api.key=YOUR_ANTHROPIC_API_KEY
```

## 📊 Sample AI Analysis

When asked *"Which deals are at risk?"*, the AI responds with:

> **High Risk:** Initech - Starter Package ($15K) — 30% probability, 
> needs immediate qualification activities...

## 🎓 Built For

This project was built as a portfolio piece demonstrating:
- Full-stack Java/React development
- AI/LLM API integration
- Relational database design with SQL
- Agentic AI thinking applied to real business problems
- Skills aligned with Salesforce Einstein AI platform