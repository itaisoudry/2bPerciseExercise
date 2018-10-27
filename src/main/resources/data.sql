-- Itai is the head manager of the organization
INSERT INTO employees VALUES (303011308, 'Itai', 'Soudry', 0, 1);

-- Dani and Hen are managers under Itai's management
INSERT INTO employees VALUES (303011309, 'Dani', 'Cohen', 303011308, 1);
INSERT INTO employees VALUES (111111111, 'Hen', 'Bar-Levi', 303011308, 1);

-- Sharon and Daniel are employees under Hen's management
INSERT INTO employees VALUES (111111112, 'Sharon', 'Mendelevitz', 111111111, 0);
INSERT INTO employees VALUES (111111113, 'Daniel', 'Golub', 111111111, 0);

-- Erez and Adi are employees under Dani's management
INSERT INTO employees VALUES (111111114, 'Erez', 'Ben Kimon', 303011309, 0);
INSERT INTO employees VALUES (111111115, 'Adi', 'Regev', 303011309, 0);

-- Task for Sharon given by Hen
INSERT INTO tasks (assign_date, due_date, employee_id, text)
VALUES ('2018-10-22 15:30:14.332', '2018-10-23 15:00:00.000', 111111112, 'First task for Sharon!');

-- Task for Hen by Itai
INSERT INTO tasks (assign_date, due_date, employee_id, text)
VALUES ('2018-10-22 15:30:14.332', '2018-10-23 15:00:00.000', 111111111, 'First task for Hen from Itai!');

-- Task for Erez given by Dani
INSERT INTO tasks (assign_date, due_date, employee_id, text)
VALUES ('2018-10-22 15:30:14.332', '2018-10-23 15:00:00.000', 111111114, 'First task for Erez from Dani!');
-- Report to Hen by Sharon
INSERT INTO reports (report_date, employee_id, manager_id, text)
VALUES ('2018-10-22 15:30:14.332', 111111112, 111111111, 'First report from Sharon to Hen!');
