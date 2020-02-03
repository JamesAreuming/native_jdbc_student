select user(), database ();

select * from employee;

select * from employee where dno = 1;

select e.empno, e.empname, e.title, m.empname as manager_name , m.empno as manager_no, e.salary, e.dno, d.deptname
				from employee e join employee m
				on e.empno = m.manager
				join department d
				on d.deptno = e.dno;
				
select e.empno, e.empname, e.title, m.empname as manager_name , m.empno as manager_no, e.salary, e.dno, d.deptname
				from employee e join employee m
				on e.empno = m.empno
				join department d				on d.deptno = e.dno where e.dno = 1
				
select e.empno, e.empname, e.title, m.empname as manager_name , m.empno as manager_no, e.salary, e.dno, d.deptname
from employee e join employee m on e.manager = m.empno
				join department d on e.dno = d.deptno where e.dno = 1
				
select * from employee;
select * from department;

select e.empno, e.empname, e.title, m.empname as manager_name, m.empno as manager_no, e.salary, e.dno, d.deptname
	from employee e left join employee m
	on e.empno = m.empno 
	left join department d
	on e.dno = d.deptno
	where e.dno=1;


select * from department;
insert into department values(12,"개발3",10);
update department set deptname= "개발1",floor=7 where deptno = 5;
delete from department where deptno = 16;
delete from department where deptno = 4;
