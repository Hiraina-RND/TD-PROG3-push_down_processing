CREATE DATABASE "invoice_db";

CREATE USER "invoice_db_manager" WITH PASSWORD '123456';

ALTER DATABASE "invoice_db" OWNER TO "invoice_db_manager";