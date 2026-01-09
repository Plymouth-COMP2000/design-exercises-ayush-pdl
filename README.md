Restaurant Management Mobile Application

COMP2000 – Software Engineering 2 (Assessment 2)

Overview

This project is an Android-based Restaurant Management Mobile Application developed as part of the COMP2000 Software Engineering 2 module at the University of Plymouth.

The application supports two user roles—Staff and Guest—and aims to improve restaurant operations and customer experience through role-based functionality, local data management, and notification support.

Features
Staff

Secure login via RESTful API

Menu management (Create, Read, Update, Delete)

Reservation management (view, confirm, cancel)

Receive notifications for new reservations

Guest

Secure login via RESTful API

Browse menu items

Create and manage reservations

View reservation status

Receive booking-related notifications

Technical Details

Platform: Android

Language: Java

IDE: Android Studio

Authentication: RESTful API (centralised)

Local Storage: SQLite

Architecture:

DAO pattern for database access

Role-based navigation and control

Event-driven local notifications

Notifications

The application uses Android notification channels to provide alerts for important events such as reservation creation and confirmation.
Notification permissions are requested at runtime (Android 13+).

Usability & Design

Designed using User-Centred Design (UCD) principles

Interfaces aligned with Material Design guidelines

Role-specific dashboards to reduce cognitive load

Tested with representative users for usability

GitHub Repository

This repository contains:

Full Android Studio project

Incremental commits showing development progress

All source code required to build and run the application


Academic Integrity & AI Declaration

This project was completed in line with the module’s acceptable level of AI use (Assisted Work).
Generative AI tools were used for:

Planning and structuring

Technical guidance and debugging support

Language refinement

All AI usage is declared in the submitted report.

