-- Drop in dependency order so you can re-run easily
DROP TABLE IF EXISTS employee_project;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS address;

-- Reference/lookup tables first
CREATE TABLE department (
  dept_id      INT PRIMARY KEY,
  name         VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE address (
  address_id   INT PRIMARY KEY,
  city         VARCHAR(100) NOT NULL,
  state        CHAR(2)      NOT NULL,
  zipcode      VARCHAR(10)  NOT NULL
);

CREATE TABLE project (
  project_id     INT PRIMARY KEY,
  project_name   VARCHAR(100) NOT NULL,
  estimated_days INT NOT NULL CHECK (estimated_days > 0),
  location       CHAR(2) NOT NULL       -- looks like a state code in your data
);

-- Core entity
CREATE TABLE employee (
  emp_id     INT PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  salary     DECIMAL(12,2) NOT NULL CHECK (salary > 0),
  address_id INT NOT NULL,
  dept_id    INT NOT NULL,
  CONSTRAINT fk_emp_address
    FOREIGN KEY (address_id) REFERENCES address(address_id),
  CONSTRAINT fk_emp_department
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

-- Many-to-many: employee <-> project
CREATE TABLE employee_project (
  emp_id     INT NOT NULL,
  project_id INT NOT NULL,
  PRIMARY KEY (emp_id, project_id),           -- prevents duplicates
  CONSTRAINT fk_ep_emp     FOREIGN KEY (emp_id)     REFERENCES employee(emp_id),
  CONSTRAINT fk_ep_project FOREIGN KEY (project_id) REFERENCES project(project_id)
);

-- Helpful indexes (especially in MySQL)
CREATE INDEX idx_employee_dept     ON employee(dept_id);
CREATE INDEX idx_employee_address  ON employee(address_id);
CREATE INDEX idx_ep_project        ON employee_project(project_id);
