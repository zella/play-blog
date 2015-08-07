# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table linked_accounts (
  id                        bigint not null,
  user_id                   varchar(40) not null,
  provider_user_id          varchar(255),
  provider_key              varchar(255),
  constraint pk_linked_accounts primary key (id))
;

create table posts (
  id                        varchar(40) not null,
  creation_date             timestamp,
  title                     varchar(255),
  content                   varchar(255),
  user_id                   varchar(40),
  constraint pk_posts primary key (id))
;

create table users (
  id                        varchar(40) not null,
  name                      varchar(255),
  email                     varchar(255),
  avatar_url                varchar(255),
  constraint pk_users primary key (id))
;

create sequence linked_accounts_seq;

alter table linked_accounts add constraint fk_linked_accounts_users_1 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_linked_accounts_users_1 on linked_accounts (user_id);
alter table posts add constraint fk_posts_user_2 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_posts_user_2 on posts (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists linked_accounts;

drop table if exists posts;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists linked_accounts_seq;

