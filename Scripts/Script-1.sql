select user(), database ();

select * from employee;

select * from department;


select *
	from department, employee
	where dno = deptno;
	
desc employee;

desc department;