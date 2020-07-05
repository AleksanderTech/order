CREATE DATABASE "order";

CREATE USER "order" WITH PASSWORD 'order';
ALTER DATABASE "order" OWNER TO "order";
ALTER USER "order" WITH createdb;