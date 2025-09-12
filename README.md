# 🛍️ Store API

A full-featured RESTful backend for an online store, built with Spring Boot. This project includes user authentication (JWT), product management, shopping cart functionality, order processing, and Stripe integration for secure payments.

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/hojiakbar9/spring-boot-store.git
cd spring-boot-store
```
### 2. Configure Environment Variables
Rename the `.env.example` file to `.env` and fill in required values:

#### 🔐`JWT_SECRET`
Generate a secure key using
```bash
openssl rand -base64 32
```
Or go to [Generate Random](generate-random.org), click Strings > Generate Random, and generate a 256-bit token.

#### 💳 `STRIPE_API_SECRET`

1. Create an account at [stripe.com](https://stripe.com/en-de)
2. Go to **Developers > API Keys**
3. Copy your **Secret Key** and add it to `.env`

#### 🔁 `WEBHOOK_STRIPE_SECRET_KEY`
Install Stripe CLI from [Stripe CLI Docs](https://docs.stripe.com/stripe-cli), then run:

```bash
stripe login
stripe listen --forward-to http://localhost:8080/checkout/webhook
```
Copy the signing secret from the terminal and paste it into `.env`.

### ▶️ Running the Project
This is a Maven project. Run the app using:
```bash
./mvnw spring-boot:run
```
On Windows:
```bash
mvnw.cmd spring-boot:run
```
Access the app at:
````bash
http://localhost:8080
````

### 📚 API Documentation
Swagger UI is available at:

````bash
http://localhost:8080/swagger-ui.html
````

### 🛠️ Tech Stack
- Java 17

- Spring Boot 3.4.1

- Spring Security & JWT

- MySQL

- Stripe API

- Maven

- Flyway (DB migrations)

- Swagger (API Docs)

### 📂 Key Endpoints

| Endpoint              | Description                        |
|-----------------------|------------------------------------|
| `/products`           | View all products                  |
| `/auth`               | User login                         |
| `/users`              | User registration                  |
| `/carts`              | Create and update shopping carts   |
| `/checkout`           | Start Stripe checkout              |
| `/checkout/webhook`   | Stripe webhook for payments        |
| `/orders`             | Order history & management         |

### 📃 License
This project is for educational and demo purposes.