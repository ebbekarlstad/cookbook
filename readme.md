# Cookbook App

### Developed by: 
Ebbe Karlstad, Ahmad Snobar, Anas Adiel, Rawan Diwan, Kinan Almasri, Firas Moussa

---

## Table of Contents

1. [Overview](#overview)
2. [App Features](#app-features)
3. [Requirements](#requirements)
4. [Setup Instructions](#setup-instructions)
5. [Database Configuration](#database-configuration)
6. [Running the Application](#running-the-application)
7. [Code Snippets](#code-snippets)
8. [Troubleshooting](#troubleshooting)

---

## Overview

The **Cookbook App** is a recipe management system that allows users to store, search, and manage their favorite recipes. Built with a modern design, it features a responsive user interface and a robust backend.

---

## App Features

- **Recipe Storage**: Add and organize recipes with their ingredients and steps.
- **Search Functionality**: Find recipes by name, ingredient, or category.
- **Database-Driven**: Persistent data storage using a relational database.

---

## Requirements

Before running the application, ensure you have the following:

1. **Java Development Kit (JDK)** installed (version 11 or later).
2. A relational database server, **MySQL**.
3. Required fonts installed: **SFNSDisplay (San Francisco)**.
4. IDE or command-line environment for running Java applications.

---

## Setup Instructions

Follow these steps to set up the Cookbook App:

### Step 1: Install Required Fonts
1. Navigate to the `/resources/fonts/` directory.
2. Locate the `.otf` files for the **SFNSDisplay** font.
3. Install these fonts on your system:
   - On **Windows**: Right-click the `.otf` file and select "Install."
   - On **Mac**: Double-click the file and click "Install Font."
   - On **Linux**: Copy the font file to the `~/.fonts` directory.

### Step 2: Configure the Database
1. Open your database management tool (e.g., MySQL Workbench).
2. Create a new schema:
   ```sql
   CREATE DATABASE cookbookdb;
   ```
### Step 3: Seed the Database
1. Locate the file ```DatabaseSeeder.java``` in the project.
2. Run the file using your IDE or terminal to populate the database with sample data.

### Step 4: Run the Application
1. Locate the App.java file in the project.
2. Execute the file:
Using IDE: Right-click and select "Run."
Using Command Line:
```
javac App.java
java App
```

### Greetings, the developers! 🙂

   
