create table t_base(
	id bigint auto_increment,
    create_time datetime,
    modified_time datetime,
    deleted boolean default 0,
    primary key(id)
);