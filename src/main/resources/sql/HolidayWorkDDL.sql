drop table holiday_work;

create table holiday_work (
    holiday_work_id int auto_increment primary key ,
    employee_no varchar(5),
    work_date date,
    memo varchar(200),
    delete_dt datetime
);