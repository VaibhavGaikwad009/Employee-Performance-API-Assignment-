Add This IN Mysql DB 
-- ---------------------------
-- DEPARTMENTS
-- ---------------------------
INSERT INTO departments (id, name, budget) VALUES
(1, 'HR', 500000),
(2, 'Technology', 1200000),
(3, 'Finance', 800000);

-- ---------------------------
-- EMPLOYEES
-- ---------------------------
INSERT INTO employees (id, name, email, department_id, date_of_joining, salary, manager_id) VALUES
(1, 'Rahul Sharma', 'rahul@company.com', 2, '2021-01-01', 90000, NULL),
(2, 'Anita Verma', 'anita@company.com', 2, '2022-03-15', 70000, 1),
(3, 'Suresh Mehta', 'suresh@company.com', 1, '2021-06-20', 60000, 1),
(4, 'Neha Singh', 'neha@company.com', 3, '2023-02-01', 65000, 1);

-- ---------------------------
-- PROJECTS
-- ---------------------------
INSERT INTO projects (id, name, start_date, end_date, department_id) VALUES
(1, 'CRM System', '2023-01-01', '2024-01-01', 2),
(2, 'HR Portal', '2023-03-01', '2023-12-01', 1),
(3, 'Finance Automation', '2023-04-01', '2024-03-01', 3);

-- ---------------------------
-- EMPLOYEE_PROJECTS (Many-to-Many)
-- ---------------------------
INSERT INTO employee_projects (id, employee_id, project_id, assigned_date, role) VALUES
(1, 1, 1, '2023-01-10', 'Tech Lead'),
(2, 2, 1, '2023-02-01', 'Developer'),
(3, 2, 2, '2023-03-01', 'Backend Developer'),
(4, 3, 2, '2023-03-05', 'HR Analyst'),
(5, 4, 3, '2023-04-10', 'Finance Executive');

-- ---------------------------
-- PERFORMANCE_REVIEWS
-- ---------------------------
INSERT INTO performance_reviews (id, employee_id, review_date, score, review_comments) VALUES
(1, 1, '2024-01-01', 4.8, 'Excellent leadership'),
(2, 1, '2023-07-01', 4.6, 'Strong technical skills'),
(3, 1, '2023-01-01', 4.5, 'Consistent performer'),
(4, 2, '2024-01-01', 4.2, 'Good progress'),
(5, 2, '2023-06-01', 4.0, 'Meets expectations'),
(6, 3, '2024-01-01', 3.8, 'Needs improvement'),
(7, 4, '2024-01-01', 4.1, 'Reliable contributor');
