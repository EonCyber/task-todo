-- Create the profiles table
CREATE TABLE profiles (
    profile_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(255),
    role_enum VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create the tasks table
CREATE TABLE tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    task_type VARCHAR(1) NOT NULL,
    task_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP,
    to_complete_at TIMESTAMP,
    completed_at TIMESTAMP,
    profile_id BIGINT,
    CONSTRAINT fk_profile FOREIGN KEY (profile_id) REFERENCES profiles(profile_id)
);

-- Insert Profile
INSERT INTO profiles (full_name, email, password, avatar_url, role_enum, created_at, updated_at)
VALUES ('John Doe', 'john@example.com', '$2a$10$bxo88GBMALq8EQ2Tf374HOecBKWO0cG8sq1cR6NQyFBAm.IPzTmpC', 'https://primefaces.org/cdn/primeng/images/demo/avatar/amyelsner.png', 'USER', NOW(), NULL);

-- Insert Tasks for that Profile
INSERT INTO tasks (title, task_type, task_status, created_at, to_complete_at, completed_at, profile_id)
VALUES
  ('Finish the report', 'W', 'PENDING', NOW(), NOW(), NULL, 1),
  ('Go for a run', 'E', 'PENDING', NOW(), NOW(), NULL, 1),
  ('Clean the kitchen', 'C', 'PENDING', NOW(), NOW(), NULL, 1);