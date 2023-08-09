-- Database: external_api_db

-- DROP DATABASE IF EXISTS ext_api_db;
--
--CREATE DATABASE ext_api_db
--    WITH
--    OWNER = postgres
--    ENCODING = 'UTF8'
--    LC_COLLATE = 'en_US.UTF-8'
--    LC_CTYPE = 'en_US.UTF-8'
--    TABLESPACE = pg_default
--    CONNECTION LIMIT = -1
--    IS_TEMPLATE = False;

CREATE TABLE IF NOT EXISTS company (id SERIAL PRIMARY KEY, symbol VARCHAR(255) NOT NULL);
CREATE TABLE IF NOT EXISTS stock (id SERIAL PRIMARY KEY, change NUMERIC(38,2) NOT NULL, latest_price NUMERIC(38,2)NOT NULL, previous_volume INT NOT NULL, volume INT, company_id VARCHAR(255) NOT NULL);
