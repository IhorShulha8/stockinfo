-- Database: stocks-db
--CREATE DATABASE IF NOT EXISTS stocks-db;

--company
create TABLE IF NOT EXISTS company (
    id serial PRIMARY KEY,
    symbol VARCHAR(255)
    );

--stock
create TABLE IF NOT EXISTS stock (
    id serial PRIMARY KEY,
    symbol VARCHAR(255) not null,
    change NUMERIC(38,2),
    latest_price NUMERIC(38,2)NOT NULL,
    previous_volume INT NOT NULL,
    volume INT,
    company_name VARCHAR(255) NOT NULL
    );