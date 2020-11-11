create table t_base(
	  id bigint auto_increment,
    create_time time,
    modified_time time,
    deleted boolean default 0,
    primary key(id)
);