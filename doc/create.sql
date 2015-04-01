drop database if exists rdms;
create database rdms;
use rdms;

drop table if exists t_sys_user;
create table t_sys_user (
	id varchar(32) primary key,
	account varchar(45),
	pwd varchar(64),
	name varchar(45)
) engine=InnoDB default charset = utf8;

drop table if exists t_sys_menu;
create table t_sys_menu (
	id varchar(32) primary key,
	code varchar(100),
	name varchar(45),
	parent_id varchar(32),
	page_id varchar(32),
	sort_index int,
	create_time datetime,
	create_user varchar(100)
) engine=InnoDB default charset = utf8;

drop table if exists t_sys_page;
create table t_sys_page (
	id varchar(32) primary key,
	code varchar(100),
	url varchar(200),
	name varchar(45),
	create_user varchar(100),
	create_time datetime
) engine=InnoDB default charset = utf8;

drop table if exists t_comm_position;
create table t_comm_position (
	id varchar(32) primary key,
	name varchar(45),
	detail text
) engine=InnoDB default charset = utf8;

drop table if exists t_comm_dept;	
create table t_comm_dept (
	id varchar(32) primary key,
	name varchar(45),
	dept_code varchar(3),
	mem_total int
) engine=InnoDB default charset = utf8;

drop table if exists t_comm_emp;
create table t_comm_emp (
	id varchar(32) primary key,
	account varchar(45),
	pwd varchar(64),
	name varchar(45),
	gender varchar(4),
	birth_date date,
	entry_date date,
	email varchar(100),
	phone varchar(20),
	dept varchar(45),
	position varchar(45),
	stats varchar(10)
) engine=InnoDB default charset = utf8;

drop table if exists t_comm_pj_gr;
create table t_comm_pj_gr (
	id varchar(32) primary key,
	name varchar(100),
	detail text,
	create_user varchar(32),
	create_time datetime
) engine=InnoDB default charset = utf8;

drop table if exists t_comm_pj_gr_mem;
create table t_comm_pj_gr_mem (
	id varchar(32) primary key,
	pj_gr_id varchar(32),
	eid varchar(32),
	role varchar(45)
) engine=InnoDB default charset = utf8;

drop table if exists t_comm_pj;
create table t_comm_pj (
	id varchar(32) primary key,
	name varchar(200),
	detail text,
	status varchar(20),
	create_user varchar(32),
	create_time datetime,
	start_time date,
	end_time date,
	pre_complete_day int,
	real_complete_day int,
	pj_gr_id varchar(32),
	progress double
)engine=InnoDB default charset = utf8;

drop table if exists t_comm_pj_stage;
create table t_comm_pj_stage (
	id varchar(32) primary key,
	pid varchar(32),
	name varchar(45),
	create_time datetime,
	start_time date,
	end_time date,
	pre_complete_day int,
	real_complete_day int,
	status varchar(20),
	progress double
) engine=InnoDB default charset = utf8;

drop table if exists t_comm_pj_mark;
create table t_comm_pj_mark (
	id varchar(32) primary key,
	create_time datetime,
	pid varchar(32),
	content text,
	attachment varchar(200)
) engine=InnoDB default charset = utf8;

drop table if exists t_comm_work_log;
create table t_comm_work_log (
	id varchar(32) primary key,
	eid varchar(32),
	create_time date,
	work_times int,
	content text
) engine=InnoDB default charset = utf8;

drop table if exists t_svn_pj;
create table t_svn_pj (
	id varchar(32) primary key,
	pid varchar(32),
	path varchar(200),
	url varchar(200),
	detail text
) engine=InnoDB default charset = utf8;

drop table if exists t_svn_pj_usr;
create table t_svn_pj_usr (
	id varchar(32) primary key,
	svn_pj_id varchar(32),
	account varchar(45),
	pwd varchar(64)
) engine=InnoDB default charset = utf8;

drop table if exists t_svn_pj_gr;
create table t_svn_pj_gr (
	id varchar(32) primary key,
	svn_pj_id varchar(32),
	name varchar(45),
	detail text
) engine=InnoDB default charset = utf8;

drop table if exists t_svn_pj_gr_usr;
create table t_svn_pj_gr_usr (
	id varchar(32) primary key,
	svn_gr_id varchar(32),
	account varchar(45)
) engine=InnoDB default charset = utf8;

drop table if exists t_svn_pj_gr_auth;
create table t_svn_pj_gr_auth (
	id varchar(32) primary key,
	svn_pj_id varchar(32),
	svn_gr_id varchar(32),
	resource varchar(200),
	auth varchar(20)
) engine=InnoDB default charset = utf8;

drop table if exists t_bug_info;
create table t_bug_info (
	id varchar(32) primary key,
	name varchar(45),
	pj_id varchar(32),
	title varchar(200),
	detail text,
	qa_id varchar(32),
	dev_id varchar(32),
	level varchar(20),
	status varchar(20),
	create_time datetime,
	pre_resolve_day int,
	real_resolve_day int,
	resolve_time datetime,
	attachment varchar(200)
) engine=InnoDB default charset = utf8;

drop table if exists t_bug_trace;
create table t_bug_trace (
	id varchar(32) primary key,
	title varchar(100),
	bid varchar(32),
	from_who_id varchar(32),
	to_who_id varchar(32),
	type varchar(45),
	create_time datetime,
	detail text,
	attachment varchar(200)
) engine=InnoDB default charset = utf8;

-- 新增加 角色表
drop table if exists t_auth_role;
create table t_auth_role (
	id varchar(32) primary key,
	name varchar(100),
	detail text
) engine=InnoDB default charset = utf8;

-- 新增加 角色-用户表
drop table if exists t_auth_emp_role;
create table t_auth_emp_role (
	eid varchar(32),
	rid varchar(32),
	primary key(eid, rid)
) engine=InnoDB default charset = utf8;

-- 新增加 动作表
drop table if exists t_auth_action;
create table t_auth_action (
	id varchar(32),
	name varchar(100),
	url varchar(200),
	detail text
) engine = InnoDB default charset = utf8;

-- 新增加 角色-动作禁止表
drop table if exists t_auth_action_forbid;
create table t_auth_action_forbid (
	rid varchar(32),
	aid varchar(32),
	primary key(rid, aid)
) engine = InnoDB default charset = utf8;


-- 新增加
drop table if exists t_comm_pj_mark_res;
create table t_comm_pj_mark_res (
	id varchar(32) primary key,
	pj_mark_id varchar(32),
	name varchar(100),
	path varchar(200)
) engine=InnoDB default charset = utf8;

-- 新增加
drop table if exists t_bug_info_res;
create table t_bug_info_res (
	id varchar(32) primary key,
	bid varchar(32),
	name varchar(100),
	path varchar(200)
) engine=InnoDB default charset = utf8;

-- 新增加
drop table if exists t_bug_trace_res;
create table t_bug_trace_res (
	id varchar(32) primary key,
	bt_id varchar(32),
	name varchar(100),
	path varchar(200)
) engine=InnoDB default charset = utf8;

