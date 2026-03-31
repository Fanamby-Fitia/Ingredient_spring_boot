CREATE DATABASE product_management_db;

CREATE USER product_manager_user WITH PASSWORD '123456';

GRANT ALL ON SCHEMA public TO product_manager_user;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO product_manager_user;

GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO product_manager_user;

-- Donner accès au schéma
GRANT ALL ON SCHEMA public TO product_manager_user;

-- Tables existantes
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO product_manager_user;

-- Séquences (auto increment)
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO product_manager_user;

-- Futures tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT ALL ON TABLES TO product_manager_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT ALL ON SEQUENCES TO product_manager_user;