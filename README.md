# 📚 Book Shop Management API  

## 📖 Description  

**Book Shop Management API** is a **Spring Boot** application designed to manage an online bookshop. It provides functionalities for **user authentication, book and category management, order processing, and shopping cart operations**.  

The application utilizes **Spring Security** for authentication and authorization, **Spring Data JPA** for database operations, and follows **RESTful API** principles.  

---

## 🛠️ Technologies Used  

- ☕ **Java 17+**  
- 🌱 **Spring Boot 3+**  
- 🔐 **Spring Security** (JWT-based authentication)  
- 🗄️ **Spring Data JPA** (Hibernate)  
- ✅ **Spring Validation**  
- 🏷️ **Lombok**  
- 📜 **Swagger (Springdoc OpenAPI)**  
- 🗃️ **PostgreSQL** (or any other relational database)  

---

## ✨ Features  

### 🔑 **Authentication & User Management**  
- ✅ **User registration** (`POST /authentication/registration`)  
- ✅ **User login** (`POST /authentication/login`)  

### 📚 **Book Management** (`/books`)  
- 🔍 **Retrieve all books** (paginated)  
- 📖 **Find books by ID**  
- 🎯 **Search books by parameters**  
- ✍️ **Add, update, and delete books** (**admin only**)  

### 🏷️ **Category Management** (`/categories`)  
- 📂 **Retrieve all categories**  
- 🆔 **Find category by ID**  
- 📚 **Retrieve books by category ID**  
- ✍️ **Add, update, and delete categories** (**admin only**)  

### 📦 **Order Management** (`/orders`)  
- 🛒 **Place a new order**  
- 📜 **Retrieve order history**  
- 📑 **Get order details**  
- 🔄 **Update order status** (**admin only**)  

### 🛍️ **Shopping Cart Management** (`/shoppingcart`)  
- 🛒 **Retrieve shopping cart**  
- ➕ **Add a book to the cart**  
- ❌ **Remove a book from the cart**  

---

## 🔒 API Security  

- 👤 **ROLE_USER**: Can browse books, manage shopping cart, and place orders.  
- 🛠️ **ROLE_ADMIN**: Can manage books, categories, and orders.  
- Uses **JWT (JSON Web Token)** authentication for secure access.  

---
