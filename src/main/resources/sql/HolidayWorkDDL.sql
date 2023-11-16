drop table holiday_work;

create table holiday_work
(
    holiday_work_id int auto_increment primary key,
    employee_no     varchar(5)   null,
    work_date       date         null,
    memo            varchar(200) null,
    delete_dt       datetime     null,
    create_dt       datetime     default now(),
    constraint holiday_work_ibfk_1
        foreign key (employee_no) references employee (employee_no)
);