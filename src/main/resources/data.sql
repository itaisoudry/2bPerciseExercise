-- Itai is the head manager of the organization
insert into employees values (303011308, 'Itai','Soudry',0,1);

-- Dani and Hen are managers under Itai's management
insert into employees values (303011309, 'Dani','Cohen',303011308,1);
insert into employees values (111111111,'Hen','Bar-Levi',303011308,1);

-- Sharon and Daniel are employees under Hen's management
insert into employees values (111111112,'Sharon','Mendelevitz',111111111,0);
insert into employees values (111111113,'Daniel','Golub',111111111,0);

-- Erez and Adi are employees under Dani's management
insert into employees values (111111114,'Erez','Ben Kimon',303011309,0);
insert into employees values (111111115,'Adi','Regev',303011309,0);


-- Task for Sharon given by Hen
insert into tasks (assign_date,due_date,employee_id,text)
  values('2018-10-22 15:30:14.332','2018-10-23 15:00:00.000',111111112,'First task for Sharon!');

-- Task for Hen by Itai
insert into tasks (assign_date,due_date,employee_id,text)
values('2018-10-22 15:30:14.332','2018-10-23 15:00:00.000',111111111,'First task for Hen from Itai!');

-- Task for Erez given by Dani
insert into tasks (assign_date,due_date,employee_id,text)
  values('2018-10-22 15:30:14.332','2018-10-23 15:00:00.000',303011309,'First task for Erez!');
-- Report to Hen by Sharon
insert into reports(report_date,employee_id,manager_id,text)
  values('2018-10-22 15:30:14.332',111111112,111111111,'First report from Sharon to Hen!');
