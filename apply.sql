/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022/12/12 16:57:41                          */
/*==============================================================*/


drop trigger users_makeapply_time;

drop 
table if exists user_pass_query;

drop index activity_name on activity_info;

/*==============================================================*/
/* Table: activity_info                                         */
/*==============================================================*/
create table activity_info
(
   activity_id          int not null,
   manager_id           int not null,
   activity_time        varchar(1) not null,
   activity_location    varchar(1) not null,
   activity_need        int not null,
   public_time          varchar(1) not null,
   activity_name        varchar(1) not null,
   primary key (activity_id)
);

/*==============================================================*/
/* Index: activity_name                                         */
/*==============================================================*/
create unique index activity_name on activity_info
(
   activity_name
);

/*==============================================================*/
/* Table: apply_category                                        */
/*==============================================================*/
create table apply_category
(
   apply_id             int not null,
   id                   int not null,
   activity_id          int not null,
   apply_time           varchar(1) not null,
   primary key (apply_id)
);

/*==============================================================*/
/* Table: manager                                               */
/*==============================================================*/
create table manager
(
   manager_id           int not null,
   manager_code         varchar(1) not null,
   manager_name         varchar(1),
   primary key (manager_id)
);

/*==============================================================*/
/* Table: notpass_category                                      */
/*==============================================================*/
create table notpass_category
(
   notpass_id           int not null,
   apply_id             int,
   primary key (notpass_id)
);

/*==============================================================*/
/* Table: pass_category                                         */
/*==============================================================*/
create table pass_category
(
   pass_id              int not null,
   apply_id             int,
   primary key (pass_id)
);

/*==============================================================*/
/* Table: students                                              */
/*==============================================================*/
create table students
(
   student_number       int not null,
   id                   int,
   student_code         varchar(1) not null,
   student_name         varchar(1) not null,
   primary key (student_number)
);

/*==============================================================*/
/* Table: teachers                                              */
/*==============================================================*/
create table teachers
(
   teacher_id           int not null,
   id                   int,
   teacher_code         varchar(1) not null,
   teacher_name         varchar(1) not null,
   primary key (teacher_id)
);

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users
(
   id                   int not null,
   primary key (id)
);

/*==============================================================*/
/* View: user_pass_query                                        */
/*==============================================================*/
create VIEW  user_pass_query
 as
select users.id,apply_category.activity_id,activity_info.activity_name,apply_category.apply_time
from users,pass_category,apply_category,activity_info
where users.id=apply_category.id and apply_category.apply_id=pass_category.apply_id
and apply_category.activity_id=activity_info.activity_id;

alter table activity_info add constraint "FK_管理员-活动" foreign key (manager_id)
      references manager (manager_id) on delete restrict on update restrict;

alter table apply_category add constraint "FK_申请人清单-活动信息" foreign key (activity_id)
      references activity_info (activity_id) on delete restrict on update restrict;

alter table apply_category add constraint "FK_申请人清单-用户" foreign key (id)
      references users (id) on delete restrict on update restrict;

alter table notpass_category add constraint FK_申请未通过清单 foreign key (apply_id)
      references apply_category (apply_id) on delete restrict on update restrict;

alter table pass_category add constraint FK_申请通过清单 foreign key (apply_id)
      references apply_category (apply_id) on delete restrict on update restrict;

alter table students add constraint FK_学生用户 foreign key (id)
      references users (id) on delete restrict on update restrict;

alter table teachers add constraint FK_老师用户 foreign key (id)
      references users (id) on delete restrict on update restrict;


CREATE TRIGGER users_makeapply_time
    BEFORE INSERT ON apply_category
    for each row
    SET NEW.apply_time = NOW();

