CREATE DATABASE IF NOT EXISTS dbProject2;
USE dbProject2;


CREATE TABLE IF NOT EXISTS employees (
	id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS projects (
	id integer NOT NULL AUTO_INCREMENT,
	titel varchar(100) NOT NULL,
	startdate date NOT NULL,
	enddate date NOT NULL,
	revenueInCHF integer NOT NULL,
	PRIMARY KEY(id)
);


CREATE TABLE IF NOT EXISTS projectsAssignment (
	id INTEGER NOT NULL AUTO_INCREMENT,
	project_id INTEGER NOT NULL,
	employee_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (project_id)
	REFERENCES projects (id),
	FOREIGN KEY (employee_id)
	REFERENCES employees (id)
);


# insert <employees>
INSERT INTO employees (name, email)
VALUES ("Guy Elliott","guy.elliott@email.com");

INSERT INTO employees (name, email)
VALUES ("Franklin Mills","franklin.mills@email.com");

INSERT INTO employees (name, email)
VALUES ("Bernard Robertson","bernard.robertson@email.com");

INSERT INTO employees (name, email)
VALUES ("Amber Santos","amber.santos@email.com");

INSERT INTO employees (name, email)
VALUES ("Kristina Meyer","kristina.meyer@email.com");

INSERT INTO employees (name, email)
VALUES ("John Deprecated","john.deprecated@oldemail.com");


# insert <projects>
INSERT INTO projects (titel, startdate, enddate, revenueInCHF) 
VALUES ("Monkey 2000", '2000-05-12', '2007-04-15', 2309000);

INSERT INTO projects (titel, startdate, enddate, revenueInCHF) 
VALUES ("Monkey 2000 XS", '2008-01-01', '2018-01-01', 5001000);

INSERT INTO projects (titel, startdate, enddate, revenueInCHF) 
VALUES ("Donkey 3000 NG", '2010-05-01', '2020-05-01', 9890000);


# insert project assignments <employees, projects>
# INSERT INTO projectsAssignment (project_id, employee_id)
# VALUES ( 	(SELECT id from employees WHERE name = 'Kristina Meyer'), 
#			(SELECT id from projects WHERE titel = 'Donkey 3000 NG'));
INSERT INTO projectsAssignment (project_id, employee_id)
VALUES 	(1, 1),
        (1, 2),
        (1, 3),
        (1, 4),
        (1, 6),
        (2, 2),
        (2, 3),
        (2, 5),
        (3, 2),
        (3, 3),
        (3, 4),
        (3, 5);
        

# task I d) a)
# give all projects where Kristina was part of the team
SELECT projects.titel FROM projectsAssignment
INNER JOIN projects ON projectsAssignment.project_id = projects.id
WHERE employee_id = (SELECT id from employees where name = "Kristina Meyer");


# task I d) b)
SELECT employees.email FROM projectsAssignment
INNER JOIN projects ON projectsAssignment.project_id = projects.id
INNER JOIN employees ON projectsAssignment.employee_id = employees.id
WHERE CURDATE() BETWEEN projects.startdate AND projects.enddate
GROUP BY employees.email;


# task I d) c)
# number of projects per person
SELECT employees.name, COUNT(employees.id) as numberOfProjects FROM projectsAssignment
INNER JOIN projects ON projectsAssignment.project_id = projects.id
INNER JOIN employees ON projectsAssignment.employee_id = employees.id
GROUP BY employees.name
ORDER BY numberOfProjects DESC;


# task II a)
# who works with who in which project?
SELECT employeesname.name AS thisGuy, coworkersname.name AS worksWith, projects.titel AS inProject
FROM projectsAssignment empa
INNER JOIN projectsAssignment cwpa ON cwpa.project_id = empa.project_id
INNER JOIN projects ON projects.id = empa.project_id
INNER JOIN employees AS employeesname ON employeesname.id = empa.employee_id
INNER JOIN employees AS coworkersname ON coworkersname.id = cwpa.employee_id
WHERE empa.employee_id != cwpa.employee_id;


# task II b)
SELECT thisGuy, COUNT(*) as withCount FROM (
	SELECT DISTINCT employeesname.name AS thisGuy, coworkersname.name AS worksWith
	FROM projectsAssignment empa
	INNER JOIN projectsAssignment cwpa ON cwpa.project_id = empa.project_id
	INNER JOIN projects ON projects.id = empa.project_id
	INNER JOIN employees AS employeesname ON employeesname.id = empa.employee_id
	INNER JOIN employees AS coworkersname ON coworkersname.id = cwpa.employee_id
	WHERE empa.employee_id != cwpa.employee_id) mp
GROUP BY thisGuy;
