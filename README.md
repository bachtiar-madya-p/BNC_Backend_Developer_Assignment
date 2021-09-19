# BNC_Backend_Developer_Assignment

# Backend Developer Assignment

Develop REST API services using Java programming language using Spring Boot and MySQL database for Regular Installment Saving (simpanan berjangka) modules that covers these function:
Calculate a Saving Account
Request:
Saving tenor (total months)
First deposit amount
Monthly deposit amount
Purpose of saving (purposes like: Education, Emergency, or Other)
Response: estimated final balance = (((saving tenor/12) * 6%) * grand total deposit amount) + grand total deposit amount
Create Saving Account
Request:
Saving tenor (total months)
First deposit amount
Monthly deposit amount
Purpose of saving (purposes like: Education, Emergency, or Other)
Response: status, data (new Saving Account/empty), success/error message
Get all savings
Request: none
Response: status, data (Saving Accounts), success/error message
Get a saving details
Request: Saving Account ID
Response: status, data (Saving Account), success/error message

SQL file = src/main/resource/SCHEMA.sql
Postman collection file = src/main/resource/COLLECTION - BNC Assassement.json
