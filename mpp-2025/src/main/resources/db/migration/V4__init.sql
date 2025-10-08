-- A. Creating a Database View
CREATE VIEW EmployeeDetails AS SELECT
e.emp_id,
e.employee_name AS Name,
e.salary,
d.dept_name,
p.project_name
FROM employee e
JOIN employee_project ep
ON e.emp_id = ep.emp_id

-- B. Adding an Index
CREATE INDEX idx_employee_name ON employee(name);
