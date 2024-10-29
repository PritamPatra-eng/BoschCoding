CREATE DATABASE inventory_management;

\c inventory_management;

CREATE TABLE inventory (
    item VARCHAR(255) PRIMARY KEY,
    quantity INT NOT NULL
);