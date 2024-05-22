# Regional Inventory Management System(simple UI)

## Description

The Regional Inventory Management System is a Java EE-based web application developed using the JSF framework, designed to facilitate efficient inventory handling across multiple regions. The application runs on the Payara Server and utilizes MySQL for data persistence. 
It implements the DAO pattern and adheres to the MVC design principles 

## Features

- **Data Manipulation**: Features include editing, updating, and inserting inventory data.
- **Responsive Web Design**: Ensures the web application is accessible on both desktop and mobile devices.
![image](https://github.com/freda1874/Java-Enterprise-App-JSF/assets/85437054/8c1f5b9c-7cc5-4998-8946-74318b465c90)

![image](https://github.com/freda1874/Java-Enterprise-App-JSF/assets/85437054/54b320bb-fdaf-45a7-a5d5-ac4d39162a9d)
![image](https://github.com/freda1874/Java-Enterprise-App-JSF/assets/85437054/4bc3a100-3b6c-418c-bd08-d958f0dabd8e)


## Prerequisites

Before you begin, ensure you have the following installed:
- Java JDK 11 or newer
- Eclipse IDE for Java EE Developers
- Payara Server
- MySQL Server

## Setup and Installation

### Database Setup

1. **Install MySQL**:
   Ensure that MySQL server is installed and running on your system.

2. **Create Database**:
   ```sql
   CREATE DATABASE RegionalInventory;
   ```

3. **Import Schema**:
   Use the provided SQL script to set up your database schema:
   ```bash
   mysql -u username -p RegionalInventory < path/to/sql_file.sql
   ```

### Application Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/regional-inventory.git
   cd regional-inventory
   ```

2. **Set Up Eclipse Project**:
   - Open Eclipse and import the project: `File` -> `Import` -> `Existing Projects into Workspace`.
   - Navigate to the project directory and select the project.

3. **Configure Payara Server in Eclipse**:
   - Go to `Servers` tab, right click and `New` -> `Server`.
   - Select `Payara` and set up the server pointing to your Payara installation directory.

4. **Configure Database Connection**:
   - Update the `src/main/resources/database.properties` file with your MySQL username, password, and URL.

## Running the Application

1. **Start Payara Server**:
   - Right-click on the Payara Server in Eclipse and select `Start`.

2. **Deploy the Application**:
   - Right-click on the project in Eclipse, select `Run As` -> `Run on Server`.
   - Choose the configured Payara Server and hit `Finish`.

3. **Access the Application**:
   - Open a web browser and navigate to `http://localhost:8080/regional-inventory` to start using the application.

