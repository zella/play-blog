# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table posts (
  id                        varchar(40) not null,
  creation_date             timestamp,
  title                     varchar(255),
  content                   clob,
  user_email                varchar(255),
  constraint pk_posts primary key (id))
;

create table users (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  avatar_url                varchar(255),
  constraint pk_users primary key (email))
;

create sequence users_seq;

alter table posts add constraint fk_posts_user_1 foreign key (user_email) references users (email) on delete restrict on update restrict;
create index ix_posts_user_1 on posts (user_email);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists posts;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists users_seq;

