-- Recreate FKs with ON DELETE CASCADE
ALTER TABLE employee_project
  DROP FOREIGN KEY fk_ep_emp,
  DROP FOREIGN KEY fk_ep_project;

ALTER TABLE employee_project
  ADD CONSTRAINT fk_ep_emp
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id)
    ON DELETE CASCADE,
  ADD CONSTRAINT fk_ep_project
    FOREIGN KEY (project_id) REFERENCES project(project_id)
    ON DELETE CASCADE;

CREATE INDEX idx_ep_emp ON employee_project(emp_id);
