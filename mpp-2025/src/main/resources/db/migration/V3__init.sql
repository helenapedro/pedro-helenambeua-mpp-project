-- 2. TABLE CREATION (DDL)

CREATE TABLE address (
    address_id INT PRIMARY KEY,
    city       VARCHAR(50) NOT NULL,
    state      CHAR(2) NOT NULL,
    zipcode    VARCHAR(10)
);

CREATE TABLE department (
    dept_id INT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL
);

CREATE TABLE project (
    project_id     INT PRIMARY KEY,
    project_name   VARCHAR(100) NOT NULL,
    estimated_days INT NOT NULL CHECK (estimated_days > 0),
    location       CHAR(2) NOT NULL
);

CREATE TABLE employee (
    emp_id     INT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    salary     INT NOT NULL CHECK (salary > 0),
    address_id INT NOT NULL,
    dept_id    INT NOT NULL,
    CONSTRAINT fk_emp_address
        FOREIGN KEY (address_id) REFERENCES address(address_id),
    CONSTRAINT fk_emp_dept
        FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE employee_project (
    emp_id     INT NOT NULL,
    project_id INT NOT NULL,
    PRIMARY KEY (emp_id, project_id),
    CONSTRAINT fk_ep_emp
        FOREIGN KEY (emp_id) REFERENCES employee(emp_id),
    CONSTRAINT fk_ep_project
        FOREIGN KEY (project_id) REFERENCES project(project_id)
);

----------------------------------------------------------------------
-- 3. DATA INSERTION (DML)
----------------------------------------------------------------------

INSERT INTO address (address_id, city, state, zipcode) VALUES
(1, 'Fairfield', 'IA', '52556'),
(2, 'Iowa City', 'IA', '52440'),
(3, 'Morrison', 'IL', '61270'),
(4, 'Orlando', 'FL', '34565'),
(5, 'Tampa', 'FL', '31765');

INSERT INTO department (dept_id, name) VALUES
(1, 'Tech'),
(2, 'HR'),
(3, 'Finance'),
(4, 'Marketing');

INSERT INTO project (project_id, project_name, estimated_days, location) VALUES
(1, 'X', 180, 'FL'),
(2, 'Y', 60,  'FL'),
(3, 'Z', 80,  'IA');

INSERT INTO employee (emp_id, name, salary, address_id, dept_id) VALUES
(111, 'Zaineh', 100000, 1, 1),
(112, 'Yasmeen', 160000, 2, 4),
(113, 'Mira',   140000, 3, 3),
(114, 'Shimaa', 200000, 4, 2),
(115, 'Dean',   150000, 5, 1);

INSERT INTO employee_project (emp_id, project_id) VALUES
(115, 1),
(115, 2),
(115, 3),
(114, 1),
(114, 3),
(111, 1),
(111, 2);

--------------------------------------------------
-- 4. QUERY EXERCISES

-- A. SELECTION QUERIES
---------------------------------------------------

-- 1. List the name and salary of all employees
SELECT name, salary
FROM employee;

-- 2. Find the names of all projects located in Florida (FL)
SELECT project_name
FROM project
WHERE location = 'FL';

-- 3. Retrieve the emp_id and project_id for employees working on Project 1
SELECT emp_id, project_id
FROM employee_project
WHERE project_id = 1;

-- 4. Find all unique (distinct) states where employee addresses are located
SELECT DISTINCT a.state
FROM employee e
JOIN address a ON e.address_id = a.address_id;

-- 5. List the names and salaries of all employees who earn a salary less than $150,000
SELECT name, salary
FROM employee
WHERE salary < 150000;

-- 6. List the project names and their estimated days, ordered from longest to shortest
SELECT project_name, estimated_days
FROM project
ORDER BY estimated_days DESC;

-- 7. Find the emp_ids of employees who are assigned to a project (unique only)
SELECT DISTINCT emp_id
FROM employee_project;

-- B. AGGREGATES AND GROUPING
---------------------------------------------------

-- 1. Calculate the average salary of all employees
SELECT AVG(salary) AS avg_salary
FROM employee;

-- 2. Find the maximum estimated_days for any single project
SELECT MAX(estimated_days) AS max_estimated_days
FROM project;

-- 3. For each department, report the dept_id and total salary expenditure
SELECT dept_id, SUM(salary) AS total_salary
FROM employee
GROUP BY dept_id
ORDER BY dept_id;

-- 4. Find dept_id of departments with average employee salary > 150,000
SELECT dept_id
FROM employee
GROUP BY dept_id
HAVING AVG(salary) > 150000
ORDER BY dept_id;

-- C. JOINS
---------------------------------------------------

-- 1. List employee name and the city where they live
SELECT e.name AS employee_name, a.city
FROM employee e
JOIN address a ON e.address_id = a.address_id;

-- 2. List all departments and employee names (include departments with no employees)
SELECT d.dept_id, d.name AS dept_name, e.name AS employee_name
FROM department d
LEFT JOIN employee e ON d.dept_id = e.dept_id
ORDER BY d.dept_id;

-- 3. Find employee names and the projects they are working on
SELECT e.name AS employee_name, p.project_name
FROM employee e
JOIN employee_project ep ON e.emp_id = ep.emp_id
JOIN project p ON ep.project_id = p.project_id
ORDER BY e.name, p.project_name;

-- D. SUBQUERIES
---------------------------------------------------

-- 1. Find the name of the employee who has the highest salary
SELECT name
FROM employee
WHERE salary = (
    SELECT MAX(salary)
    FROM employee
);

-- 2. List the names of employees who work on a project that has estimated_days = 180
SELECT DISTINCT e.name
FROM employee e
WHERE e.emp_id IN (
    SELECT ep.emp_id
    FROM employee_project ep
    JOIN project p ON ep.project_id = p.project_id
    WHERE p.estimated_days = 180
);

-- 3. Find the project_id of all projects with estimated_days > average estimated_days
SELECT project_id
FROM project
WHERE estimated_days > (
    SELECT AVG(estimated_days)
    FROM project
);