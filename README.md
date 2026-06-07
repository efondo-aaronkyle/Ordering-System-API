# 🛒 Ordering System API

A simple ordering system developed using **Spring Boot**, **MyBatis**, and **MySQL**.

This project is created as part of the **Application Development** course activity to demonstrate the implementation of a RESTful API using a layered architecture. The system manages customers, products, orders, and reports while applying industry-standard development practices such as DTOs, repositories, services, and database integration.

---

## 📖 Project Overview

The Ordering System API allows clients to:

* Register and manage customers
* Create and view products
* Place and manage orders
* Generate sales and product reports
* Perform database operations through MyBatis
* Expose RESTful endpoints using Spring Boot

---

## 🎯 Project Objectives

This project aims to:

* Demonstrate REST API development using Spring Boot
* Implement database integration using MySQL and MyBatis
* Apply layered architecture principles
* Practice collaborative software development using Git and GitHub
* Build a maintainable and scalable backend application

---

## 🛠️ Technology Stack

| Technology   | Purpose                 |
| ------------ | ----------------------- |
| Java 21      | Programming Language    |
| Spring Boot  | Backend Framework       |
| MyBatis      | ORM / Database Mapping  |
| MySQL        | Database                |
| Maven        | Dependency Management   |
| Git & GitHub | Version Control         |
| Eclipse IDE  | Development Environment |

---

## 👥 Development Team

| Name                   | Role           |
| ---------------------- | -------------- |
| Aaron Kyle D. Efondo   | Lead Developer |
| Ernesto P. Bernas      | Developer      |
| Abrianne V. Buenacifra | Developer      |
| Altheno Mari L. Tero   | Developer      |

---

# 🚀 Getting Started

## Prerequisites

Before running the project, make sure you have:

* Java JDK 21
* Eclipse IDE
* MySQL Server
* Git
* Maven (optional, wrapper included)

---

# 📥 Clone the Repository

Open Command Prompt and navigate to your Eclipse workspace:

```bash
cd C:\Users\<Name>\eclipse-workspace
```

Clone the repository:

```bash
git clone <repository-url>
```

Example:

```bash
git clone https://github.com/your-organization/ordering-system-api.git
```

After cloning, the project folder should be located inside your Eclipse workspace:

```text
C:\Users\<Name>\eclipse-workspace\Ordering-System-API
```

---

# ⚙️ Eclipse Setup

### 1. Open the Project in Eclipse

Open Eclipse IDE.

Navigate to:

```text
File
 └── Open Projects from File System...
```

Click:

```text
Directory...
```

Select:

```text
C:\Users\<Name>\eclipse-workspace\Ordering-System-API
```

Eclipse will automatically detect the Maven project.

Click:

```text
Finish
```

Wait for Eclipse to load and build the project.

---

### 2. Update Maven Dependencies

Right-click the project:

```text
Maven
 └── Update Project...
```

Check:

```text
Force Update of Snapshots/Releases
```

Click:

```text
OK
```

Allow Maven to download all required dependencies.

---

### 3. Configure Database Connection

Open:

```text
src/main/resources/application.properties
```

Update the following values based on your local MySQL configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ordering_system_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Save the file after making the necessary changes.

---

### 4. Run the Application

Locate:

```text
OrderingSystemApiApplication.java
```

Right-click the file and select:

```text
Run As
 └── Spring Boot App
```

The application should start successfully and connect to the local MySQL database.

---

# 📌 Planned Features

### Epic 1 – Customer API

* Register Customer
* Get Customer Details

### Epic 2 – Product API

* Create Product
* List Products
* Get Product by ID

### Epic 3 – Order API

* Create Order
* Get Order by ID
* Get Customer Orders
* Cancel Order

### Epic 4 – Reporting API

* Total Sales Report
* Top Selling Products

---

# 📄 License

This project is developed solely for educational purposes as a requirement for the Application Development course.
