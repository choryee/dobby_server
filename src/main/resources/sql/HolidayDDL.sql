drop table holiday_info;
drop table dateKind;


create table dateKind (
    kind_num varchar(2) primary key ,
    kind_name varchar(10)
);

insert into dateKind (kind_num, kind_name) value
    ('01', '국경일'),
    ('02', '기념일'),
    ('03', '24절기'),
    ('04', '잡절');

create table holiday_info(
    holiday_name varchar(30),
    holiday date,
    is_rest_institutions BOOLEAN,
    date_kind_num varchar(2),
    primary key (holiday_name, holiday),
    foreign key date (date_kind_num) references dateKind (kind_num) on delete set null on update set null
);
