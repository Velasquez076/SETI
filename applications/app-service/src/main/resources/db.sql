-- POSTGRES DATABASE
CREATE DATABASE IF NOT EXISTS my_company_db;

CREATE TABLE franchises (
    id_franchise SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE branches (
    id_branch SERIAL PRIMARY KEY,
    id_franchise INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_franchise) REFERENCES franchises(id_franchise)
);

CREATE TABLE products (
    id_product SERIAL PRIMARY KEY,
    id_branch INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INTEGER NOT NULL,
    FOREIGN KEY (id_branch) REFERENCES branches(id_branch)
);