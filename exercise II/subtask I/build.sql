CREATE DATABASE IF NOT EXISTS dbProject2;
USE dbProject2;

CREATE TABLE IF NOT EXISTS employees (
	id integer NOT NULL AUTO_INCREMENT, 
    name varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	PRIMARY KEY(id)
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
	id integer NOT NULL AUTO_INCREMENT,
    project_id integer NOT NULL,
    employee_id integer NOT NULL,
    PRIMARY KEY (id),
	FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

# insert <employees>
INSERT INTO employees (name, email)
VALUES ("Livio Bieri","livio.bieri@email.com");

INSERT INTO employees (name, email)
VALUES ("Yves Buschor","yves.buschor@email.com");

INSERT INTO employees (name, email)
VALUES ("Marius Küngs","yves.buschor@email.com");


# insert <projects>
INSERT INTO projects (titel, startdate, enddate, revenueInCHF) 
VALUES ("Monkey 2k Xs", '2006-05-00', '2014-05-00', 100000);

INSERT INTO projects (titel, startdate, enddate, revenueInCHF) 
VALUES ("Donkey 3000 Xs", '2010-05-00', '2015-05-00', 1400000);


# insert project assignments <employees, projects>
# INSERT INTO projectsAssignment (project_id, employee_id)
# VALUES ( 	(SELECT id from employees WHERE name = 'Yves Buschor'), 
#			(SELECT id from projects WHERE titel = 'Monkey 2k Xs'));
            
INSERT INTO projectsAssignment (project_id, employee_id)
VALUES 	(1,1),
		(1,2),
        (1,3),
        (2,2);
        

# give all projects where Yves was part of the team
SELECT projects.titel FROM projectsAssignment
INNER JOIN projects
ON projectsAssignment.project_id = projects.id
WHERE employee_id = (SELECT id from employees where name = "Yves Buschor");

# give all projects where Yves was part of the team
#SELECT employees.email FROM projectsAssignment
#INNER JOIN employees
#ON projectsAssignment.project_id = projects.id
#WHERE employee_id = (SELECT id from employees where name = "Yves Buschor") 

        
        
        

            
            

