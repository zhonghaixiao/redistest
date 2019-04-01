create table activity(
  id varchar(50) primary key,
  name varchar(100) not null default '',
  state enum('未开始','正在进行','已结束') default '未开始',
  start_time datetime default now(),
  end_time datetime default  now(),
  create_time datetime default now(),
  update_time datetime default now()
);

insert into activity(id, name, start_time, end_time)
values
       ('123', 'testActivityName1', now(), date_add(now(), interval 1 day ));


select * from activity;
