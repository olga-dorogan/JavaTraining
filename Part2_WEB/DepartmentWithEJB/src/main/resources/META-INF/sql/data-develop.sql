INSERT INTO department(id, description) VALUES (-1,'Department 1');
INSERT INTO department(id, description) VALUES (-2,'Department 2');

INSERT INTO department_group(id, name, course, DEPARTMENT_id) VALUES (-1, 'Group 1 (dep_1)', 1, -1);
INSERT INTO department_group(id, name, course, DEPARTMENT_id) VALUES (-2, 'Group 2 (dep_1)', 1, -1);
INSERT INTO department_group(id, name, course, DEPARTMENT_id) VALUES (-3, 'Group 1 (dep_1)', 2, -1);
INSERT INTO department_group(id, name, course, DEPARTMENT_id) VALUES (-4, 'Group 2 (dep_1)', 2, -1);

INSERT INTO department_group(id, name, course, DEPARTMENT_id) VALUES (-5, 'Group 1 (dep_2)', 1, -2);
INSERT INTO department_group(id, name, course, DEPARTMENT_id) VALUES (-6, 'Group 2 (dep_2)', 1, -2);
INSERT INTO department_group(id, name, course, DEPARTMENT_id) VALUES (-7, 'Group 1 (dep_2)', 2, -2);
INSERT INTO department_group(id, name, course, DEPARTMENT_id) VALUES (-8, 'Group 2 (dep_2)', 2, -2);

INSERT INTO student(id, first_name, last_name, age, DEPARTMENTGROUP_id) VALUES (-1, 'Name 1', 'Surname 1', 18, -1);
INSERT INTO student(id, first_name, last_name, age, DEPARTMENTGROUP_id) VALUES (-2, 'Name 2', 'Surname 2', 18, -1);