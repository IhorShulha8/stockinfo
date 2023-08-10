-- Database: external_api_db

--company
create TABLE IF NOT EXISTS company
(
symbol VARCHAR(255) PRIMARY KEY,
is_enabled BOOLEAN
);

--stock
create TABLE IF NOT EXISTS stock
(
symbol VARCHAR(255) PRIMARY KEY,
change NUMERIC(38,2) NOT NULL,
latest_price NUMERIC(38,2)NOT NULL,
previous_volume INT NOT NULL,
volume INT,
company_name VARCHAR(255) NOT NULL
);
