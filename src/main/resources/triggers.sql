--drop TRIGGER IF EXISTS "updateDeltaOnInserOrUpdateTrigger" ON txs;

CREATE OR REPLACE FUNCTION deltaCalc() RETURNS trigger
   LANGUAGE plpgsql AS
$$BEGIN
   NEW.delta_price := NEW.latest_price - OLD.latest_price;
   RETURN NEW;
END;
$$;

CREATE TRIGGER updateDeltaOnInserOrUpdateTrigger
   BEFORE INSERT OR UPDATE OF latest_price ON stock
   FOR EACH ROW
   EXECUTE PROCEDURE deltaCalc();



-- triger stock_audit_trigger
CREATE TRIGGER stock_audit_trigger
AFTER INSERT OR UPDATE ON stock
FOR EACH ROW EXECUTE FUNCTION stock_audit_trigger_func()

--stock_audit_trigger_func
CREATE OR REPLACE FUNCTION stock_audit_trigger_func()
RETURNS trigger AS $body$
BEGIN
  if (TG_OP = 'INSERT') then
      INSERT INTO stock_audit_log (
           id,
           symbol,
           old_row_data,
           new_row_data,
           dml_type,
           dml_timestamp
           )
      VALUES(
          NEW.id,
          NEW.symbol,
          null,
          to_jsonb(NEW),
          TG_OP::dml_type,
          CURRENT_TIMESTAMP
      );
      RETURN NEW;

  elsif (TG_OP = 'UPDATE') then
      INSERT INTO stock_audit_log (
           id,
           symbol,
           old_row_data,
           new_row_data,
           dml_type,
           dml_timestamp
      )
      VALUES(
          NEW.id,
          NEW.symbol,
          to_jsonb(OLD),
          to_jsonb(NEW),
          'UPDATE',
          CURRENT_TIMESTAMP
      );
      RETURN NEW;

  end if;
RETURN NULL;
END;
$body$
LANGUAGE plpgsql