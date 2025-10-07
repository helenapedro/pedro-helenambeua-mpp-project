-- *** 1. Insert Data into Address Table ***
INSERT INTO address (address_id, city, state, zipcode) VALUES
(1, 'Fairfield', 'IA', '52556'),
(2, 'Iowa City', 'IA', '52440'),
(3, 'Morrison', 'IL', '61270'),
(4, 'Orlando', 'FL', '34565'),
(5, 'Tampa', 'FL', '31765');

-- *** 2. Insert Data into Department Table ***
INSERT INTO department (dept_id, name) VALUES
(1, 'Tech'),
(2, 'HR'),
(3, 'Finance'),
(4, 'Marketing');

-- *** 3. Insert Data into Project Table ***
INSERT INTO project (project_id, project_name, estimated_days, location) VALUES
(1, 'X', 180, 'FL'),
(2, 'Y', 60, 'FL'),
(3, 'Z', 80, 'IA');

-- *** 4. Insert Data into Employee Table ***
INSERT INTO employee (emp_id, name, salary, address_id, dept_id) VALUES
(111, 'Zaineh', 100000, 1, 1),
(112, 'Yasmeen', 160000, 2, 4),
(113, 'Mira', 140000, 3, 3),
(114, 'Shimaa', 200000, 4, 2),
(115, 'Dean', 150000, 5, 1);

-- *** 5. Insert Data into Employee_Project Table ***
INSERT INTO employee_project (emp_id, project_id) VALUES
(115, 1),
(115, 2),
(115, 3),
(114, 1),
(114, 3),
(111, 1),
(111, 2);