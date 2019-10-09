# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table customers_mst (
  cid                       bigint auto_increment not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  account                   varchar(255),
  meterno                   varchar(255),
  mobile_no                 varchar(255),
  idnumber                  varchar(255),
  plot_no                   varchar(255),
  reg_fee                   varchar(255),
  is_active                 varchar(255),
  created_on                datetime,
  constraint pk_customers_mst primary key (cid))
;

create table menus (
  id                        bigint auto_increment not null,
  menu_code                 varchar(255),
  menu_name                 varchar(255),
  menu_type                 varchar(255),
  menuparent_id             varchar(255),
  menu_right                varchar(255),
  menu_section              varchar(255),
  is_active                 varchar(255),
  constraint pk_menus primary key (id))
;

create table payments_mst (
  id                        bigint auto_increment not null,
  previousreading           varchar(255),
  currentreading            varchar(255),
  consumption               varchar(255),
  arrears                   varchar(255),
  outstanding               varchar(255),
  fixedcharge               varchar(255),
  customers_mst_cid         bigint,
  created_on                datetime,
  constraint pk_payments_mst primary key (id))
;

create table roles (
  id                        bigint auto_increment not null,
  rolename                  varchar(255),
  rolecode                  varchar(255),
  status                    varchar(255),
  date                      varchar(255),
  is_active                 varchar(255),
  constraint pk_roles primary key (id))
;

create table user_mst (
  id                        bigint auto_increment not null,
  user_name                 varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  mobile_no                 varchar(255),
  password                  varchar(255),
  is_active                 varchar(255),
  rolecode                  varchar(255),
  roles_id                  bigint,
  created_on                datetime,
  constraint pk_user_mst primary key (id))
;


create table roles_menus (
  roles_id                       bigint not null,
  menus_id                       bigint not null,
  constraint pk_roles_menus primary key (roles_id, menus_id))
;
alter table payments_mst add constraint fk_payments_mst_customersMst_1 foreign key (customers_mst_cid) references customers_mst (cid) on delete restrict on update restrict;
create index ix_payments_mst_customersMst_1 on payments_mst (customers_mst_cid);
alter table user_mst add constraint fk_user_mst_roles_2 foreign key (roles_id) references roles (id) on delete restrict on update restrict;
create index ix_user_mst_roles_2 on user_mst (roles_id);



alter table roles_menus add constraint fk_roles_menus_roles_01 foreign key (roles_id) references roles (id) on delete restrict on update restrict;

alter table roles_menus add constraint fk_roles_menus_menus_02 foreign key (menus_id) references menus (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table customers_mst;

drop table menus;

drop table payments_mst;

drop table roles;

drop table roles_menus;

drop table user_mst;

SET FOREIGN_KEY_CHECKS=1;

