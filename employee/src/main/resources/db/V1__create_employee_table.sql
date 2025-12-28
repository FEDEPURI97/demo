CREATE TABLE employee (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    date_of_birth DATE,
    employee_code VARCHAR(50) UNIQUE NOT NULL,
    hire_date DATE NOT NULL,
    end_date DATE,
    status VARCHAR(30) NOT NULL,
    salary NUMERIC(12,2)
);