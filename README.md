# Train Reservation System

## Overview

The **Train Reservation System** is a comprehensive Java application designed for managing train seat reservations. It provides an intuitive interface for users to efficiently handle the entire reservation process, from booking seats to viewing, updating, and canceling reservations. The application is connected to a MySQL database, ensuring reliable data storage and management.

## Features

- **Seat Reservation:** Users can easily reserve seats by entering necessary passenger information, including name, contact number, seat number, and bogie number.
  
- **View Reservations:** The system allows users to view all current reservations, displaying essential details such as reservation ID, passenger name, seat number, bogie number, contact number, and reservation date.
  
- **Seat Number Retrieval:** Users can retrieve seat numbers associated with their reservations by entering their reservation ID and passenger name, enhancing user convenience.
  
- **Update Reservation:** The application provides functionality to update existing reservations, allowing users to modify passenger details, seat assignments, and other related information.
  
- **Reservation Cancellation:** Users can cancel their reservations effortlessly by providing the reservation ID, making the process straightforward and user-friendly.

## Technologies Used

- **Programming Language:** Java
- **Database Management System:** MySQL (accessed via JDBC)
- **Development Environment:** IDE (e.g., IntelliJ IDEA, Eclipse)

## Installation

### Prerequisites

- **Java Development Kit (JDK):** Ensure JDK is installed on your machine. You can download it from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
  
- **MySQL Database:** Install and configure MySQL server. You can download MySQL from [MySQL's official website](https://dev.mysql.com/downloads/mysql/).

### Database Setup

1. **Create a Database:**
   - Launch MySQL and create a database named `Train_db` using the following SQL command:

   ```sql
   CREATE DATABASE Train_db;
   ```

2. **Create a Table:**
   - Execute the following SQL command to create the `reservaion` table, which will store the reservation details:

   ```sql
   CREATE TABLE reservaion (
       reservation_id INT AUTO_INCREMENT PRIMARY KEY,
       passenger_name VARCHAR(100) NOT NULL,
       seat_number INT NOT NULL,
       bogie_number VARCHAR(10) NOT NULL,
       contact_number VARCHAR(15) NOT NULL,
       reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );
   ```

### Application Setup

1. **Clone the Repository:**

   Open your terminal or command prompt and execute the following command:

   ```bash
   git clone <repository-url>
   cd Train-Reservation-System
   ```

2. **Configure Database Connection:**
   - Open the `Train_Reservation_System.java` file and update the database connection details to match your MySQL setup:

   ```java
   private static final String url="jdbc:mysql://localhost:3306/Train_db";
   private static final String userName= "your-username";
   private static final String passWord= "your-password";
   ```

3. **Compile and Run the Application:**

   Compile the Java file and run the application using the following commands:

   ```bash
   javac Train_Reservation_System.java
   java Train_Reservation_System
   ```

## Usage

Upon running the application, users will be presented with a menu-driven interface with the following options:

1. **Reserve a Seat:** Allows users to input passenger details to reserve a seat.
2. **View Reservations:** Displays all current reservations with detailed information.
3. **Get Seat Number:** Enables users to retrieve their seat number using the reservation ID and passenger name.
4. **Update Reservation:** Users can modify existing reservation details.
5. **Cancel Reservation:** Allows users to cancel their reservations by entering the reservation ID.
6. **Exit:** Closes the application gracefully.

### Example Interaction

- When prompted, enter your choice (1-5) and follow the on-screen instructions. For instance, to reserve a seat, input the required passenger details when prompted.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contributing

Contributions to enhance this project are welcome! To contribute, please fork the repository, make your changes, and submit a pull request. Ensure to follow the code style and maintain clarity in your modifications.

## Acknowledgments

- Special thanks to the contributors and users who provide feedback and support, which helps improve the application.
- Inspired by various resources available online for database management and Java programming.

---
