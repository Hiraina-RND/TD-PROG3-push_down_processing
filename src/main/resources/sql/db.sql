CREATE DATABASE "invoice_db";

CREATE USER "invoice_db_manager" WITH PASSWORD '123456';

GRANT ALL PRIVILEGES ON DATABASE "invoice_db" TO "invoice_db_manager";