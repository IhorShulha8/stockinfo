-- Database: external_api_db

--company
--DROP TABLE IF EXISTS company;
create TABLE IF NOT EXISTS company (
    id serial PRIMARY KEY,
    symbol VARCHAR(255) not null
    );

--stock
create TABLE IF NOT EXISTS stock (
    id serial PRIMARY KEY,
    symbol VARCHAR(255) null null,
    change NUMERIC(38,2),
    latest_price NUMERIC(38,2)NOT NULL,
    previous_volume INT NOT NULL,
    volume INT,
    company_name VARCHAR(255) NOT NULL
    );

--stock_audit_log
--CREATE TABLE IF NOT EXISTS stock_audit_log (
--    id SERIAL NOT NULL,
--    symbol VARCHAR(255) NOT NULL,
--    old_row_data jsonb,
--    new_row_data jsonb,
--    dml_type dml_type NOT NULL,
--    dml_timestamp timestamp NOT NULL,
--    PRIMARY KEY (id, symbol, dml_type, dml_timestamp)
--);

--dml-type
--CREATE TYPE dml_type AS ENUM ('insert', 'update');
--
----triger
--CREATE TRIGGER stock_audit_trigger
--AFTER INSERT OR UPDATE OR DELETE ON stock
--FOR EACH ROW EXECUTE FUNCTION stock_audit_trigger_func()
--
----stock_audit_trigger_func
--CREATE OR REPLACE FUNCTION stock_audit_trigger_func()
--RETURNS trigger AS $body$
--BEGIN
--   if (TG_OP = 'INSERT') then
--       INSERT INTO stock_audit_log (
--            id,
--            symbol,
--            old_row_data,
--            new_row_data,
--            dml_type,
--            dml_timestamp
--            )
--       VALUES(
--           NEW.id,
--           symbol,
--           null,
--           to_jsonb(NEW),
--           'INSERT',
--           CURRENT_TIMESTAMP
--       );
--
--       RETURN NEW;
--   elsif (TG_OP = 'UPDATE') then
--       INSERT INTO stock_audit_log (
--            id,
--            symbol,
--            old_row_data,
--            new_row_data,
--            dml_type,
--            dml_timestamp
--       )
--       VALUES(
--           NEW.id,
--           symbol,
--           to_jsonb(OLD),
--           to_jsonb(NEW),
--           'UPDATE',
--           CURRENT_TIMESTAMP
--       );
--
--       RETURN NEW;
--   end if;
--
--END;
--$body$
--LANGUAGE plpgsql