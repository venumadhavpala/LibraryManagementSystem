# Library Management System

A Java console-based Library Management System built using Object-Oriented Programming (OOP), Collections (`ArrayList`), and File Handling.

## Features

- Add, View, Search, and Delete Books
- Book copy system (`102.1`, `102.2`, `102.3`)
- Add, View, and Delete Members
- Issue and Return Books
- Search all copies using the main Book ID (e.g., `102`)
- Alphabetical book organization
- Rack-A | Row-01 | Slot-04 location tracking
- Persistent storage using `books.txt` and `members.txt`
- Export Library Report

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- ArrayList
- File Handling
- Git & GitHub
- VS Code

## Project Structure

```text
LibraryManagementSystem/
├── src/
│   └── library/
│       ├── Book.java
│       ├── Member.java
│       ├── Library.java
│       ├── FileManager.java
│       └── Main.java
├── data/
│   ├── books.txt
│   └── members.txt
├── README.md
└── .gitignore
```

## How to Run

Compile the project:

```bash
javac -d out src/library/*.java
```

Run the application:

```bash
java -cp out library.Main
```

## Sample Features

- Add multiple copies of the same book (`102.1`, `102.2`).
- Issue books to members.
- Return books.
- Delete books and members with validation.
- Search books by Main Book ID.
- View Rack, Row, and Slot location.
- Save and load data automatically after restarting.

## Future Improvements

- MySQL database integration using JDBC
- GUI using JavaFX or Swing
- Admin Login
- Due date and fine calculation
- PDF report generation

## Author

**Venu Madhav**

Java | OOP | File Handling | DevOps Learner
