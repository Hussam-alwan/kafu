-- Create Kafu database and user (if not created by Docker)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'kafu') THEN
        CREATE DATABASE kafu;
    END IF;
END
$$;

-- Switch to kafu database
\c kafu;

-- 1. Address Table
CREATE TABLE Address (
    id bigint PRIMARY KEY,
    latitude DECIMAL(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL,
    description varchar(255) NOT NULL,
    city VARCHAR(50) NOT NULL
);

-- 2. Gov Table (Government Entity)
CREATE TABLE Gov (
    id bigint PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(100) NOT NULL,
    logo_url VARCHAR(1024),
    address_id bigint,
	phone varchar(20),
    parent_gov_id bigint,
    FOREIGN KEY (address_id) REFERENCES Address(id)
);

-- 3. User Table
CREATE TABLE Users (
    id bigint PRIMARY KEY,
    keycloak_id VARCHAR(50),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
	date_of_birth DATE,
    college_degree VARCHAR(100),
    job VARCHAR(50),
    cv_url VARCHAR(1024),
    photo_url VARCHAR(1024),
    description TEXT,
    address_id bigint,
	gov_id bigint,
	deleted BOOLEAN,
    FOREIGN KEY (address_id) REFERENCES Address(id),
	FOREIGN KEY (gov_id) REFERENCES Gov(id)
);



-- 4. ProblemCategory Table
CREATE TABLE Problem_Category (
    id bigint PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    gov_id bigint NOT NULL,
    FOREIGN KEY (gov_id) REFERENCES Gov(id)
);

-- 5. Problem Table
CREATE TABLE Problem (
    id bigint PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    is_real BOOLEAN NOT NULL DEFAULT FALSE,
    for_contribution BOOLEAN NOT NULL DEFAULT FALSE,
    for_donation BOOLEAN NOT NULL DEFAULT FALSE,
    submission_date TIMESTAMP  NOT NULL,
    submitted_by_user_id bigint NOT NULL,
    approved_by_user_id bigint,
	rejection_reason VARCHAR(255),
    address_id bigint NOT NULL,
	category_id bigint NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING_APPROVAL', 'APPROVED', 'REJECTED','PENDING_CONRIBUTIONS','PENDING_FUNDING','WORK_IN_PROGRESS','RESOLVED')),
    FOREIGN KEY (submitted_by_user_id) REFERENCES Users(id),
    FOREIGN KEY (approved_by_user_id) REFERENCES Users(id),
    FOREIGN KEY (address_id) REFERENCES Address(id),
	FOREIGN KEY (category_id) REFERENCES Problem_Category(id)
);

-- 6. ProblemPhoto Table
CREATE TABLE Problem_Photo (
    id bigint PRIMARY KEY,
    problem_id bigint NOT NULL,
    s3_Key VARCHAR(1024) NOT NULL,
    photo_date TIMESTAMP NOT NULL,
    progress_id bigint,
    FOREIGN KEY (problem_id) REFERENCES Problem(id)
);

-- 7. Solution Table
CREATE TABLE Solution (
    id bigint PRIMARY KEY,
    description TEXT NOT NULL,
    estimated_cost DECIMAL(12, 2) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING_APPROVAL', 'APPROVED', 'REJECTED','PENDING_FUNDING','WORK_IN_PROGRESS','RESOLVED')),
    accepted_reason TEXT,
    creation_date DATE,
    start_date DATE,
    end_date DATE,
    feedback TEXT,
    rating INT CHECK (rating BETWEEN 1 AND 10),
    problem_id bigint NOT NULL,
    proposed_by_user_id bigint NOT NULL,
    accepted_by_user_id bigint,
    FOREIGN KEY (problem_id) REFERENCES Problem(id),
    FOREIGN KEY (proposed_by_user_id) REFERENCES Users(id),
    FOREIGN KEY (accepted_by_user_id) REFERENCES Users(id)
);

-- 8. Donation Table
CREATE TABLE Donation (
    id bigint PRIMARY KEY,
    problem_id bigint NOT NULL,
    donor_id bigint NOT NULL,
    amount DECIMAL(12, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'USD',
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('STRIPE')),
    payment_transaction_id VARCHAR(255),
    status VARCHAR(20) NOT NULL CHECK (status IN ('CREATED', 'SUCCESS','FAILED')),
    is_anonymous BOOLEAN NOT NULL DEFAULT FALSE,
    donation_date TIMESTAMP NOT NULL,
    idempotency_key VARCHAR(255),
    FOREIGN KEY (problem_id) REFERENCES Problem(id),
    FOREIGN KEY (donor_id) REFERENCES Users(id)
);

-- 9. ProblemProgress Table
CREATE TABLE Problem_Progress (
    id bigint PRIMARY KEY,
    percentage INT NOT NULL CHECK (percentage BETWEEN 0 AND 100),
    comment TEXT NOT NULL,
    progress_date TIMESTAMP NOT NULL,
    problem_id bigint NOT NULL,
    solution_id bigint NOT NULL,
    FOREIGN KEY (problem_id) REFERENCES Problem(id),
    FOREIGN KEY (solution_id) REFERENCES Solution(id)
);

ALTER TABLE problem_photo ADD CONSTRAINT fk_progress_id FOREIGN KEY (progress_id) REFERENCES Problem_Progress(id);

create sequence address_seq start with 1 increment by 50;
create sequence donation_seq start with 1 increment by 50;
create sequence gov_seq start with 1 increment by 50;
create sequence problem_category_seq start with 1 increment by 50;
create sequence problem_photo_seq start with 1 increment by 50;
create sequence problem_seq start with 1 increment by 50;
create sequence solution_seq start with 1 increment by 50;
create sequence users_seq start with 1 increment by 50;
CREATE sequence problem_progress_seq start with 1 increment by 50;