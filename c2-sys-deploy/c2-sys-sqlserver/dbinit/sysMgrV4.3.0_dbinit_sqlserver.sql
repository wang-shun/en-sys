-- 系统管理V4.3.0完整精简版建库及初始化脚本_sqlsqlserver
-- 建库脚本_sqlsqlserver -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('tb_sm_custres') and o.name = 'fk_tb_sm_staticoper1')
alter table tb_sm_custres
   drop constraint fk_tb_sm_staticoper1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('tb_sm_decision_entitlement') and o.name = 'fk_tb_sm_decision_entitlement1')
alter table tb_sm_decision_entitlement
   drop constraint fk_tb_sm_decision_entitlement1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('tb_sm_decision_entitlement') and o.name = 'fk_tb_sm_decision_entitlement2')
alter table tb_sm_decision_entitlement
   drop constraint fk_tb_sm_decision_entitlement2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('tb_sm_decision_entitlement') and o.name = 'fk_tb_sm_decision_entitlement3')
alter table tb_sm_decision_entitlement
   drop constraint fk_tb_sm_decision_entitlement3
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('tb_sm_menu') and o.name = 'fk_tb_sm_moudle1')
alter table tb_sm_menu
   drop constraint fk_tb_sm_moudle1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('tb_sm_query_entitlement') and o.name = 'fk_tb_sm_query_entitlement2')
alter table tb_sm_query_entitlement
   drop constraint fk_tb_sm_query_entitlement2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('tb_sm_query_entitlement') and o.name = 'fk_tb_sm_query_entitlement3')
alter table tb_sm_query_entitlement
   drop constraint fk_tb_sm_query_entitlement3
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('tb_sm_query_entitlement') and o.name = 'fk_tb_sm_query_entitlement1')
alter table tb_sm_query_entitlement
   drop constraint fk_tb_sm_query_entitlement1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('tb_system_info') and o.name = 'fk_tb_system_info')
alter table tb_system_info
   drop constraint fk_tb_system_info
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_df_data_obj_column') and o.name = 'td_df_data_obj_column_fk')
alter table td_df_data_obj_column
   drop constraint td_df_data_obj_column_fk
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_dictdata') and o.name = 'fk_td_sm_dictdata')
alter table td_sm_dictdata
   drop constraint fk_td_sm_dictdata
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_grouporg') and o.name = 'fk_td_sm_grouporg1')
alter table td_sm_grouporg
   drop constraint fk_td_sm_grouporg1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_grouporg') and o.name = 'fk_td_sm_grouporg2')
alter table td_sm_grouporg
   drop constraint fk_td_sm_grouporg2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_grouporgjob') and o.name = 'fk_td_sm_grouporgjob1')
alter table td_sm_grouporgjob
   drop constraint fk_td_sm_grouporgjob1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_grouporgjob') and o.name = 'fk_td_sm_grouporgjob2')
alter table td_sm_grouporgjob
   drop constraint fk_td_sm_grouporgjob2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_grouprole') and o.name = 'fk_td_sm_grouprole1')
alter table td_sm_grouprole
   drop constraint fk_td_sm_grouprole1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_grouprole') and o.name = 'fk_td_sm_grouprole2')
alter table td_sm_grouprole
   drop constraint fk_td_sm_grouprole2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_instance_job') and o.name = 'fk_td_sm_instance_job')
alter table td_sm_instance_job
   drop constraint fk_td_sm_instance_job
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_instance_org') and o.name = 'fk_td_sm_instance_org1')
alter table td_sm_instance_org
   drop constraint fk_td_sm_instance_org1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_orgjob') and o.name = 'fk_td_sm_orgjob1')
alter table td_sm_orgjob
   drop constraint fk_td_sm_orgjob1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_orgjob') and o.name = 'fk_td_sm_orgjob2')
alter table td_sm_orgjob
   drop constraint fk_td_sm_orgjob2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_role') and o.name = 'fk_td_sm_role')
alter table td_sm_role
   drop constraint fk_td_sm_role
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_userinstance') and o.name = 'fk_td_sm_userinstance')
alter table td_sm_userinstance
   drop constraint fk_td_sm_userinstance
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_userinstancegroup') and o.name = 'fk_td_sm_userinstancegroup1')
alter table td_sm_userinstancegroup
   drop constraint fk_td_sm_userinstancegroup1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_userinstancegroup') and o.name = 'fk_td_sm_userinstancegroup2')
alter table td_sm_userinstancegroup
   drop constraint fk_td_sm_userinstancegroup2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_userinstancerole') and o.name = 'fk_td_sm_userinstancerole1')
alter table td_sm_userinstancerole
   drop constraint fk_td_sm_userinstancerole1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('td_sm_userinstancerole') and o.name = 'fk_td_sm_userinstancerole2')
alter table td_sm_userinstancerole
   drop constraint fk_td_sm_userinstancerole2
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_application_info')
            and   type = 'U')
   drop table tb_application_info
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_sm_businessdata')
            and   type = 'U')
   drop table tb_sm_businessdata
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_sm_custres')
            and   type = 'U')
   drop table tb_sm_custres
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_sm_decision_entitlement')
            and   type = 'U')
   drop table tb_sm_decision_entitlement
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_sm_menu')
            and   type = 'U')
   drop table tb_sm_menu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_sm_privilege')
            and   type = 'U')
   drop table tb_sm_privilege
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_sm_privilege_insiderelate')
            and   type = 'U')
   drop table tb_sm_privilege_insiderelate
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_sm_query')
            and   type = 'U')
   drop table tb_sm_query
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_sm_query_entitlement')
            and   type = 'U')
   drop table tb_sm_query_entitlement
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_sm_usercategory')
            and   type = 'U')
   drop table tb_sm_usercategory
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_system_info')
            and   type = 'U')
   drop table tb_system_info
go

if exists (select 1
            from  sysobjects
           where  id = object_id('tb_system_type')
            and   type = 'U')
   drop table tb_system_type
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_df_data_obj')
            and   type = 'U')
   drop table td_df_data_obj
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_df_data_obj_column')
            and   type = 'U')
   drop table td_df_data_obj_column
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_dictdata')
            and   type = 'U')
   drop table td_sm_dictdata
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_dicttype')
            and   type = 'U')
   drop table td_sm_dicttype
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_group')
            and   type = 'U')
   drop table td_sm_group
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_grouporg')
            and   type = 'U')
   drop table td_sm_grouporg
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_grouporgjob')
            and   type = 'U')
   drop table td_sm_grouporgjob
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('td_sm_grouprole')
            and   name  = 'td_sm_grouprole_fk2'
            and   indid > 0
            and   indid < 255)
   drop index td_sm_grouprole.td_sm_grouprole_fk2
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('td_sm_grouprole')
            and   name  = 'td_sm_grouprole_fk'
            and   indid > 0
            and   indid < 255)
   drop index td_sm_grouprole.td_sm_grouprole_fk
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_grouprole')
            and   type = 'U')
   drop table td_sm_grouprole
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_instance_job')
            and   type = 'U')
   drop table td_sm_instance_job
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_instance_org')
            and   type = 'U')
   drop table td_sm_instance_org
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_job')
            and   type = 'U')
   drop table td_sm_job
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_log')
            and   type = 'U')
   drop table td_sm_log
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_logconfig')
            and   type = 'U')
   drop table td_sm_logconfig
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_log_his')
            and   type = 'U')
   drop table td_sm_log_his
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_organization')
            and   type = 'U')
   drop table td_sm_organization
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('td_sm_orgjob')
            and   name  = 'td_sm_orgjob_fk2'
            and   indid > 0
            and   indid < 255)
   drop index td_sm_orgjob.td_sm_orgjob_fk2
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('td_sm_orgjob')
            and   name  = 'td_sm_orgjob_fk'
            and   indid > 0
            and   indid < 255)
   drop index td_sm_orgjob.td_sm_orgjob_fk
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_orgjob')
            and   type = 'U')
   drop table td_sm_orgjob
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_role')
            and   type = 'U')
   drop table td_sm_role
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('td_sm_roleresop')
            and   name  = 'td_sm_roleresop_fk3'
            and   indid > 0
            and   indid < 255)
   drop index td_sm_roleresop.td_sm_roleresop_fk3
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('td_sm_roleresop')
            and   name  = 'td_sm_roleresop_fk2'
            and   indid > 0
            and   indid < 255)
   drop index td_sm_roleresop.td_sm_roleresop_fk2
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('td_sm_roleresop')
            and   name  = 'td_sm_roleresop_fk'
            and   indid > 0
            and   indid < 255)
   drop index td_sm_roleresop.td_sm_roleresop_fk
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_roleresop')
            and   type = 'U')
   drop table td_sm_roleresop
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_roletype')
            and   type = 'U')
   drop table td_sm_roletype
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('td_sm_user')
            and   name  = 'in_td_sm_user_uname'
            and   indid > 0
            and   indid < 255)
   drop index td_sm_user.in_td_sm_user_uname
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_user')
            and   type = 'U')
   drop table td_sm_user
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_userinstance')
            and   type = 'U')
   drop table td_sm_userinstance
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_userinstancegroup')
            and   type = 'U')
   drop table td_sm_userinstancegroup
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_userinstancerole')
            and   type = 'U')
   drop table td_sm_userinstancerole
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_userjoborg_history')
            and   type = 'U')
   drop table td_sm_userjoborg_history
go

if exists (select 1
            from  sysobjects
           where  id = object_id('td_sm_userpreferences')
            and   type = 'U')
   drop table td_sm_userpreferences
go

create table tb_application_info (
   app_id               varchar(20)          not null,
   app_name             varchar(64)          not null,
   remark               varchar(512)         null,
   dbname               varchar(64)          null,
   dbuser               varchar(64)          null,
   dbpwd                varchar(64)          null,
   deftablespace        varchar(128)         null,
   temptablespace       varchar(128)         null,
   workfolder           varchar(256)         null,
   accpath              varchar(512)         null,
   enablestatus         numeric              null default 1,
   functionlist         varchar(128)         null,
   ismsgcenter          numeric              null default 1,
   appimgpath           varchar(128)         null,
   constraint pk_tb_application_info primary key nonclustered (app_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '应用信息表',
   'user', @currentuser, 'table', 'tb_application_info'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '应用ID',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'app_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '应用名称',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'app_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '描述',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'remark'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '应用数据源名称',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'dbname'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据库用户名',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'dbuser'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据库用户密码',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'dbpwd'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '缺省表空间',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'deftablespace'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '临时表空间',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'temptablespace'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '文件夹名称',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'workfolder'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '访问路径',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'accpath'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '应用使能状态，0为不可用，1为可用',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'enablestatus'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能开关:从右往左第一位代表短信，第二位代表内部邮件，第三位代表RTX',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'functionlist'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否显示消息中心:0代表不显示，1代表显示，缺省为显示',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'ismsgcenter'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '应用图标路径',
   'user', @currentuser, 'table', 'tb_application_info', 'column', 'appimgpath'
go

create table tb_sm_businessdata (
   id                   varchar(50)          not null,
   name                 varchar(50)          not null,
   description          varchar(200)         null,
   parent_id            varchar(50)          not null default '0',
   isleaf               char(1)              null default '1',
   "rule"               text                 null,
   constraint pk_tb_sm_businessdata primary key nonclustered (id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '业务数据分类表',
   'user', @currentuser, 'table', 'tb_sm_businessdata'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '序号',
   'user', @currentuser, 'table', 'tb_sm_businessdata', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '名称',
   'user', @currentuser, 'table', 'tb_sm_businessdata', 'column', 'name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @currentuser, 'table', 'tb_sm_businessdata', 'column', 'description'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父ID',
   'user', @currentuser, 'table', 'tb_sm_businessdata', 'column', 'parent_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否子节点(0:否1:是)',
   'user', @currentuser, 'table', 'tb_sm_businessdata', 'column', 'isleaf'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分类规则',
   'user', @currentuser, 'table', 'tb_sm_businessdata', 'column', 'rule'
go

create table tb_sm_custres (
   privilege_id         varchar(50)          not null,
   restype_id           varchar(50)          not null,
   restype_name         varchar(200)         not null,
   res_id               varchar(50)          not null,
   res_name             varchar(200)         not null,
   grintlevel           char(1)              not null default '1',
   constraint pk_tb_sm_custres primary key nonclustered (privilege_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '资源信息表',
   'user', @currentuser, 'table', 'tb_sm_custres'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限ID',
   'user', @currentuser, 'table', 'tb_sm_custres', 'column', 'privilege_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '资源类型ID',
   'user', @currentuser, 'table', 'tb_sm_custres', 'column', 'restype_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '资源类型名称',
   'user', @currentuser, 'table', 'tb_sm_custres', 'column', 'restype_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '资源ID',
   'user', @currentuser, 'table', 'tb_sm_custres', 'column', 'res_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '资源名称',
   'user', @currentuser, 'table', 'tb_sm_custres', 'column', 'res_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授予级别（-1：管理员，1：所有用户）',
   'user', @currentuser, 'table', 'tb_sm_custres', 'column', 'grintlevel'
go

create table tb_sm_decision_entitlement (
   id                   varchar(50)          not null,
   privilege_id         varchar(50)          not null,
   usercategory_id      varchar(50)          not null,
   businessdata_id      varchar(50)          not null,
   effect               char(1)              not null default '1',
   denyreason           varchar(2000)        null,
   priority             numeric              not null default 0,
   constraint pk_tb_sm_decision_entitlement primary key nonclustered (id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '决策权限策略表',
   'user', @currentuser, 'table', 'tb_sm_decision_entitlement'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '序号',
   'user', @currentuser, 'table', 'tb_sm_decision_entitlement', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限ID',
   'user', @currentuser, 'table', 'tb_sm_decision_entitlement', 'column', 'privilege_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户分类ID',
   'user', @currentuser, 'table', 'tb_sm_decision_entitlement', 'column', 'usercategory_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '业务数据分类ID',
   'user', @currentuser, 'table', 'tb_sm_decision_entitlement', 'column', 'businessdata_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '决策（0：false，1：true）',
   'user', @currentuser, 'table', 'tb_sm_decision_entitlement', 'column', 'effect'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '拒绝理由',
   'user', @currentuser, 'table', 'tb_sm_decision_entitlement', 'column', 'denyreason'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '优先级别',
   'user', @currentuser, 'table', 'tb_sm_decision_entitlement', 'column', 'priority'
go

create table tb_sm_menu (
   menu_id              varchar(50)          not null,
   is_enabled           char(1)              not null default '1',
   href                 varchar(500)         null,
   icon                 varchar(500)         null,
   display_mode         char(1)              not null default '0',
   menu_ext             text                 null,
   constraint pk_tb_sm_menu primary key nonclustered (menu_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '模块信息表',
   'user', @currentuser, 'table', 'tb_sm_menu'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单ID',
   'user', @currentuser, 'table', 'tb_sm_menu', 'column', 'menu_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否显示（0：不显示，1：显示）',
   'user', @currentuser, 'table', 'tb_sm_menu', 'column', 'is_enabled'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '链接路径',
   'user', @currentuser, 'table', 'tb_sm_menu', 'column', 'href'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '图标',
   'user', @currentuser, 'table', 'tb_sm_menu', 'column', 'icon'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '打开方式（0：div中，1：iframe中，2：新页面中）',
   'user', @currentuser, 'table', 'tb_sm_menu', 'column', 'display_mode'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单扩展字段',
   'user', @currentuser, 'table', 'tb_sm_menu', 'column', 'menu_ext'
go

create table tb_sm_privilege (
   id                   varchar(50)          not null,
   code                 varchar(500)         not null,
   name                 varchar(500)         null,
   parent_id            varchar(500)         not null default '0',
   type                 char(1)              not null,
   perm_expr            varchar(2000)        null,
   creator              varchar(50)          null default '1',
   creator_time         datetime             null default current_timestamp,
   sn                   numeric              null,
   source               char(1)              not null default '0',
   virtual              char(1)              not null default '0',
   constraint pk_tb_resource_type primary key nonclustered (id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限信息表',
   'user', @currentuser, 'table', 'tb_sm_privilege'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '序号',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限编码',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'code'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限名称',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父ID',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'parent_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '类型（1:服务(service),2:表单(form),3:表单元素(dom),4:菜单(menu),5:实体(entity),6:实体操作(entity_op),9:自定义(custom)）',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限字符串',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'perm_expr'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建者',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'creator'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'creator_time'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序号',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'sn'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限来源（0：自定义，1：IDE）',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'source'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否虚拟节点（0：否，1：是）',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'virtual'
go

create table tb_sm_privilege_insiderelate  (
   id			varchar(50)		not null,
   relate_id	varchar(50)		not null,
   sn			numeric			null
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限内部关联表',
   'user', @currentuser, 'table', 'tb_sm_privilege_insiderelate'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限ID',
   'user', @currentuser, 'table', 'tb_sm_privilege_insiderelate', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '关联ID(父ID)',
   'user', @currentuser, 'table', 'tb_sm_privilege_insiderelate', 'column', 'relate_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序号',
   'user', @currentuser, 'table', 'tb_sm_privilege_insiderelate', 'column', 'sn'
go

create table tb_sm_query (
   id                   varchar(50)          not null,
   name                 varchar(50)          not null,
   description          varchar(200)         null,
   parent_id            varchar(50)          not null default '0',
   isleaf               char(1)              null default '1',
   "rule"               text                 null,
   constraint pk_tb_sm_query primary key nonclustered (id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据查询表',
   'user', @currentuser, 'table', 'tb_sm_query'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '序号',
   'user', @currentuser, 'table', 'tb_sm_query', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '名称',
   'user', @currentuser, 'table', 'tb_sm_query', 'column', 'name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @currentuser, 'table', 'tb_sm_query', 'column', 'description'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父ID',
   'user', @currentuser, 'table', 'tb_sm_query', 'column', 'parent_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否子节点(0:否1:是)',
   'user', @currentuser, 'table', 'tb_sm_query', 'column', 'isleaf'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '查询规则',
   'user', @currentuser, 'table', 'tb_sm_query', 'column', 'rule'
go

create table tb_sm_query_entitlement (
   id                   varchar(50)          not null,
   privilege_id         varchar(50)          not null,
   query_id             varchar(50)          not null,
   usercategory_id      varchar(50)          not null,
   priority             numeric              not null default 0,
   constraint pk_tb_sm_query_entitlement primary key nonclustered (id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '查询权限策略表',
   'user', @currentuser, 'table', 'tb_sm_query_entitlement'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '序号',
   'user', @currentuser, 'table', 'tb_sm_query_entitlement', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限ID',
   'user', @currentuser, 'table', 'tb_sm_query_entitlement', 'column', 'privilege_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据查询ID',
   'user', @currentuser, 'table', 'tb_sm_query_entitlement', 'column', 'query_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户分类ID',
   'user', @currentuser, 'table', 'tb_sm_query_entitlement', 'column', 'usercategory_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '优先级别',
   'user', @currentuser, 'table', 'tb_sm_query_entitlement', 'column', 'priority'
go

create table tb_sm_usercategory (
   id                   varchar(50)          not null,
   name                 varchar(50)          not null,
   description          varchar(200)         null,
   parent_id            varchar(50)          not null default '0',
   isleaf               char(1)              null default '1',
   "rule"               text                 null,
   constraint pk_tb_sm_usercategory primary key nonclustered (id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户类型表',
   'user', @currentuser, 'table', 'tb_sm_usercategory'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '序号',
   'user', @currentuser, 'table', 'tb_sm_usercategory', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '名称',
   'user', @currentuser, 'table', 'tb_sm_usercategory', 'column', 'name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @currentuser, 'table', 'tb_sm_usercategory', 'column', 'description'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父ID',
   'user', @currentuser, 'table', 'tb_sm_usercategory', 'column', 'parent_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否子节点(0:否1:是)',
   'user', @currentuser, 'table', 'tb_sm_usercategory', 'column', 'isleaf'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分类规则',
   'user', @currentuser, 'table', 'tb_sm_usercategory', 'column', 'rule'
go

create table tb_system_info (
   id                   varchar(50)          not null,
   info_name            varchar(500)         not null,
   info_content         varchar(2000)        null,
   info_desc            varchar(500)         null,
   info_type            varchar(50)          not null,
   canremove            varchar(50)          not null default '1',
   remark               varchar(200)         null,
   constraint pk_tb_system_info primary key nonclustered (id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统设置信息表',
   'user', @currentuser, 'table', 'tb_system_info'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统信息ID',
   'user', @currentuser, 'table', 'tb_system_info', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统信息名称',
   'user', @currentuser, 'table', 'tb_system_info', 'column', 'info_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统信息内容',
   'user', @currentuser, 'table', 'tb_system_info', 'column', 'info_content'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统信息描述',
   'user', @currentuser, 'table', 'tb_system_info', 'column', 'info_desc'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统信息类型',
   'user', @currentuser, 'table', 'tb_system_info', 'column', 'info_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否可以删除字段，0：可删除 1：不可删除',
   'user', @currentuser, 'table', 'tb_system_info', 'column', 'canremove'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段',
   'user', @currentuser, 'table', 'tb_system_info', 'column', 'remark'
go

create table tb_system_type (
   id                   varchar(50)          not null,
   type_name            varchar(50)          not null,
   type_desc            varchar(100)         null,
   constraint pk_tb_system_type primary key nonclustered (id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统类别表',
   'user', @currentuser, 'table', 'tb_system_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统信息类型ID',
   'user', @currentuser, 'table', 'tb_system_type', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统信息类型名称',
   'user', @currentuser, 'table', 'tb_system_type', 'column', 'type_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统信息类型描述',
   'user', @currentuser, 'table', 'tb_system_type', 'column', 'type_desc'
go

create table td_df_data_obj (
   df_object_id         varchar(50)          not null,
   df_ds_id             varchar(100)         null,
   df_object_name       varchar(255)         null,
   df_table_name        varchar(255)         null,
   df_provide_type      varchar(20)          null,
   df_formcolnum        numeric(5)           null default 4,
   df_pkauto_type       varchar(50)          null,
   df_createmenu_flag   char(1)              null,
   df_parentmenu_id     varchar(100)         null,
   df_menu_name         varchar(100)         null,
   df_status            char(1)              null default '1',
   df_regfrompoolman    char(1)              null,
   ui_groupname         varchar(2000)        null,
   constraint pk_td_df_data_obj primary key nonclustered (df_object_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '动态表单数据对象表',
   'user', @currentuser, 'table', 'td_df_data_obj'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据对象ID',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_object_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据源ID',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_ds_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据对象名称',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_object_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据对象表名',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_table_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '信息提供类型:row按记录提供，col按字段提供',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_provide_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '表单列数',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_formcolnum'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主键增长方式:tableinfo，native，none',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_pkauto_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否创建菜单,0不创建，1创建',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_createmenu_flag'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父菜单ID',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_parentmenu_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单名',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_menu_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据对象状态,1可用,0不可用',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_status'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据对象是否来源Poolman数据源,1是,0否',
   'user', @currentuser, 'table', 'td_df_data_obj', 'column', 'df_regfrompoolman'
go

create table td_df_data_obj_column (
   df_column_id         varchar(50)          not null,
   df_column_code       varchar(100)         not null,
   df_column_name       varchar(100)         not null,
   df_object_id         varchar(50)          not null,
   df_column_type       varchar(63)          not null,
   df_length            numeric(5)           null,
   df_precision         numeric(5)           null,
   df_logic_type        varchar(63)          null,
   df_sort_sn           numeric(5)           null,
   df_maxlength         numeric(5)           null,
   df_orderby           varchar(10)          null,
   df_uitype            varchar(20)          null,
   df_optiondata_type   varchar(10)          null,
   df_optiondata        varchar(1000)        null,
   df_optiondata_dsid   varchar(50)          null,
   df_initdata          varchar(100)         null,
   df_uigroup           varchar(100)         null,
   df_uirownum          numeric(5)           null default 1,
   df_uicolnum          numeric(5)           null default 2,
   df_regex             varchar(100)         null,
   df_regex_message     varchar(200)         null,
   df_pk_flag           char(1)              null,
   df_required_flag     char(1)              null,
   df_search_flag       char(1)              null,
   df_listshow_flag     char(1)              null,
   df_status            numeric(5)           null default 1,
   df_creater_field_flag char(1)              null,
   df_creater_org_field_flag char(1)              null,
   constraint pk_td_df_data_obj_column primary key nonclustered (df_column_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '动态表单数据对象字段表',
   'user', @currentuser, 'table', 'td_df_data_obj_column'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字段ID',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_column_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字段名',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_column_code'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字段中文名',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_column_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据对象ID',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_object_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字段类型',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_column_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字段长度',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_length'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字段精度',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_precision'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逻辑数据类型',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_logic_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字段排序号',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_sort_sn'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '界面最大输入长度',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_maxlength'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '默认查询的排序字段，可选值：空白（不排序）,asc,desc',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_orderby'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字段对应表单组件类型',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_uitype'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '下拉组件的选项数据来源类型，可选值：固定，字典',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_optiondata_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '下拉组件的选项数据。如果是固定则用格式存放内容”key1,value1;key2,value2”；如果是字典则保存sql语句。',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_optiondata'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '下拉组件的选项数据源Id',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_optiondata_dsid'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '初始值。可用变量：#{currentUserName}当前用户名，#{currentUserName}当前用户，#{now}当前时间',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_initdata'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字段对应表单组件的所在分组',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_uigroup'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '组件在表单中所占行数，默认为1',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_uirownum'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '组件在表单中所占列数，默认为2(标题和组件各一列)。',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_uicolnum'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '表单字段值验证正则表达式',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_regex'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '表单字段值验证正则表达式错误信息',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_regex_message'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否为主键',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_pk_flag'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '表单验证是否非空',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_required_flag'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否为列表查询字段',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_search_flag'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否可在列表中默认显示',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_listshow_flag'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '该字段状态，0不可用,1可用,-1对应表字段不存在',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_status'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否为添加人字段',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_creater_field_flag'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否为添加人机构字段',
   'user', @currentuser, 'table', 'td_df_data_obj_column', 'column', 'df_creater_org_field_flag'
go

create table td_sm_dictdata (
   dictdata_id          varchar(50)          not null,
   dicttype_id          varchar(50)          null,
   dictdata_name        varchar(100)         null,
   dictdata_value       varchar(100)         null,
   dictdata_order       numeric              null default 0,
   dictdata_isdefault   char(1)              null default '0',
   constraint pk_td_sm_dictdata primary key nonclustered (dictdata_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典数据表',
   'user', @currentuser, 'table', 'td_sm_dictdata'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典ID',
   'user', @currentuser, 'table', 'td_sm_dictdata', 'column', 'dictdata_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典类型ID',
   'user', @currentuser, 'table', 'td_sm_dictdata', 'column', 'dicttype_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典真实值',
   'user', @currentuser, 'table', 'td_sm_dictdata', 'column', 'dictdata_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典显示值',
   'user', @currentuser, 'table', 'td_sm_dictdata', 'column', 'dictdata_value'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典排序号',
   'user', @currentuser, 'table', 'td_sm_dictdata', 'column', 'dictdata_order'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否默认值（0：否，1：是）',
   'user', @currentuser, 'table', 'td_sm_dictdata', 'column', 'dictdata_isdefault'
go

create table td_sm_dicttype (
   dicttype_id          varchar(50)          not null,
   dicttype_name        varchar(100)         not null,
   dicttype_desc        varchar(100)         null,
   constraint pk_td_sm_dicttype primary key nonclustered (dicttype_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典类型表',
   'user', @currentuser, 'table', 'td_sm_dicttype'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典类型ID',
   'user', @currentuser, 'table', 'td_sm_dicttype', 'column', 'dicttype_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典类型名称',
   'user', @currentuser, 'table', 'td_sm_dicttype', 'column', 'dicttype_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典类型描述',
   'user', @currentuser, 'table', 'td_sm_dicttype', 'column', 'dicttype_desc'
go

create table td_sm_group (
   group_id             varchar(50)          not null,
   group_name           varchar(100)         not null,
   group_desc           varchar(100)         null,
   parent_id            varchar(50)          not null default '0',
   owner_id             varchar(50)          null default '1',
   remark1              varchar(100)         null,
   remark2              varchar(100)         null,
   remark3              varchar(100)         null,
   remark4              varchar(100)         null,
   remark5              varchar(100)         null,
   type                 char(1)              null default '0',
   constraint pk_td_sm_group primary key nonclustered (group_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组信息表',
   'user', @currentuser, 'table', 'td_sm_group'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组ID',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'group_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组名称',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'group_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组描述',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'group_desc'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父ID',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'parent_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组创建人ID',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'owner_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段1',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'remark1'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段2',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'remark2'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段3',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'remark3'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段4',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'remark4'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段5',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'remark5'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '类型（0：自定义用户组，1：机构，2：机构岗位）',
   'user', @currentuser, 'table', 'td_sm_group', 'column', 'type'
go

create table td_sm_grouporg (
   group_id             varchar(50)          null,
   org_id               varchar(50)          null
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组与机构关系表',
   'user', @currentuser, 'table', 'td_sm_grouporg'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组ID',
   'user', @currentuser, 'table', 'td_sm_grouporg', 'column', 'group_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构ID',
   'user', @currentuser, 'table', 'td_sm_grouporg', 'column', 'org_id'
go

create table td_sm_grouporgjob (
   group_id             varchar(50)          null,
   job_id               varchar(50)          null
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组与机构岗位关系',
   'user', @currentuser, 'table', 'td_sm_grouporgjob'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组ID',
   'user', @currentuser, 'table', 'td_sm_grouporgjob', 'column', 'group_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位ID',
   'user', @currentuser, 'table', 'td_sm_grouporgjob', 'column', 'job_id'
go

create table td_sm_grouprole (
   group_id             varchar(50)          not null,
   role_id              varchar(50)          not null,
   resop_origin_userid  varchar(50)          null default '1',
   constraint pk_td_sm_grouprole primary key nonclustered (group_id, role_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组与角色关系表',
   'user', @currentuser, 'table', 'td_sm_grouprole'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组ID',
   'user', @currentuser, 'table', 'td_sm_grouprole', 'column', 'group_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色ID',
   'user', @currentuser, 'table', 'td_sm_grouprole', 'column', 'role_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授予角色的用户',
   'user', @currentuser, 'table', 'td_sm_grouprole', 'column', 'resop_origin_userid'
go

create index td_sm_grouprole_fk on td_sm_grouprole (
group_id asc
)
go

create index td_sm_grouprole_fk2 on td_sm_grouprole (
role_id asc
)
go

create table td_sm_instance_job (
   userinstance_id      varchar(50)          not null,
   is_main              char(1)              not null default '0',
   sn                   numeric              null default 9999
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位实例表',
   'user', @currentuser, 'table', 'td_sm_instance_job'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户实例ID',
   'user', @currentuser, 'table', 'td_sm_instance_job', 'column', 'userinstance_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否主岗位（0：否，1：是）',
   'user', @currentuser, 'table', 'td_sm_instance_job', 'column', 'is_main'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户排序号',
   'user', @currentuser, 'table', 'td_sm_instance_job', 'column', 'sn'
go

create table td_sm_instance_org (
   userinstance_id      varchar(50)          not null,
   is_main              char(1)              not null default '0',
   sn                   numeric              null default 9999
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构实例表',
   'user', @currentuser, 'table', 'td_sm_instance_org'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户实例ID',
   'user', @currentuser, 'table', 'td_sm_instance_org', 'column', 'userinstance_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否主机构（0：否，1：是）',
   'user', @currentuser, 'table', 'td_sm_instance_org', 'column', 'is_main'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户排序号',
   'user', @currentuser, 'table', 'td_sm_instance_org', 'column', 'sn'
go

create table td_sm_job (
   job_id               varchar(50)          not null,
   job_name             varchar(100)         null,
   job_desc             varchar(200)         null,
   job_scope            char(1)              not null default '1',
   job_function         varchar(200)         null,
   job_amount           varchar(100)         null,
   job_number           varchar(100)         null,
   job_condition        varchar(200)         null,
   job_rank             varchar(100)         null,
   owner_id             varchar(50)          null default '1',
   constraint pk_td_sm_job primary key nonclustered (job_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位信息表',
   'user', @currentuser, 'table', 'td_sm_job'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位ID',
   'user', @currentuser, 'table', 'td_sm_job', 'column', 'job_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位名称',
   'user', @currentuser, 'table', 'td_sm_job', 'column', 'job_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位描述',
   'user', @currentuser, 'table', 'td_sm_job', 'column', 'job_desc'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位范围(0:通用岗位,1:机构特有)',
   'user', @currentuser, 'table', 'td_sm_job', 'column', 'job_scope'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位职责',
   'user', @currentuser, 'table', 'td_sm_job', 'column', 'job_function'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位编制人数',
   'user', @currentuser, 'table', 'td_sm_job', 'column', 'job_amount'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位编号',
   'user', @currentuser, 'table', 'td_sm_job', 'column', 'job_number'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '任职条件',
   'user', @currentuser, 'table', 'td_sm_job', 'column', 'job_condition'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位级别',
   'user', @currentuser, 'table', 'td_sm_job', 'column', 'job_rank'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位授予人ID',
   'user', @currentuser, 'table', 'td_sm_job', 'column', 'owner_id'
go

create table td_sm_log (
   log_id               varchar(50)          not null,
   log_operuser         varchar(200)         null,
   oper_module          varchar(50)          null,
   log_visitorial       varchar(200)         null,
   log_opertime         datetime             null default current_timestamp,
   log_content          text                 null,
   oper_type            numeric(1)           null,
   log_status           numeric(1)           null,
   target_pk            varchar(200)         null,
   remark1              varchar(100)         null,
   remark2              varchar(100)         null,
   remark3              varchar(100)         null,
   remark4              varchar(100)         null,
   remark5              varchar(100)         null,
   constraint pk_td_sm_log primary key nonclustered (log_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志表',
   'user', @currentuser, 'table', 'td_sm_log'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志ID',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'log_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户帐号',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'log_operuser'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志模块ID',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'oper_module'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作来源（一般为操作员机器ip）',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'log_visitorial'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作时间',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'log_opertime'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作内容',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'log_content'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他）',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'oper_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志状态（1：成功，0：失败）',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'log_status'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '目标主键',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'target_pk'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注1',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'remark1'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注2',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'remark2'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注3',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'remark3'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注4',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'remark4'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注5',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'remark5'
go

create table td_sm_logconfig (
   oper_module          varchar(50)          not null,
   log_type             varchar(50)          null,
   log_oper             varchar(200)         null,
   log_operdesc         varchar(500)         null,
   log_enabled          char(1)              not null default '1',
   constraint pk_td_sm_logconfig primary key nonclustered (oper_module)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志配置表',
   'user', @currentuser, 'table', 'td_sm_logconfig'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志模块ID',
   'user', @currentuser, 'table', 'td_sm_logconfig', 'column', 'oper_module'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志类型（e：实体操作，ws：服务，dao：持久层操作，custom：自定义）',
   'user', @currentuser, 'table', 'td_sm_logconfig', 'column', 'log_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志操作ID',
   'user', @currentuser, 'table', 'td_sm_logconfig', 'column', 'log_oper'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志操作描述',
   'user', @currentuser, 'table', 'td_sm_logconfig', 'column', 'log_operdesc'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '记录日志状态，0:不记录日志，1:记录日志',
   'user', @currentuser, 'table', 'td_sm_logconfig', 'column', 'log_enabled'
go

create table td_sm_log_his (
   log_id               varchar(50)          not null,
   log_operuser         varchar(200)         null,
   oper_module          varchar(50)          null,
   log_visitorial       varchar(200)         null,
   log_opertime         datetime             null default current_timestamp,
   log_content          text                 null,
   oper_type            numeric(1)           null,
   log_status           numeric(1)           null,
   target_pk            varchar(200)         null,
   remark1              varchar(100)         null,
   remark2              varchar(100)         null,
   remark3              varchar(100)         null,
   remark4              varchar(100)         null,
   remark5              varchar(100)         null,
   constraint pk_td_sm_log_his primary key nonclustered (log_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '历史日志表',
   'user', @currentuser, 'table', 'td_sm_log_his'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志ID',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'log_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户帐号',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'log_operuser'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志模块ID',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'oper_module'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作来源（一般为操作员机器ip）',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'log_visitorial'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作时间',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'log_opertime'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作内容',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'log_content'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他）',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'oper_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志状态（1：成功，0：失败）',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'log_status'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '目标主键',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'target_pk'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注1',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'remark1'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注2',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'remark2'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注3',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'remark3'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注4',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'remark4'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注5',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'remark5'
go

create table td_sm_organization (
   org_id               varchar(50)          not null,
   org_sn               numeric              null default 999,
   org_name             varchar(40)          not null,
   org_showname         varchar(100)         not null,
   parent_id            varchar(100)         not null,
   path                 varchar(1000)        null,
   layer                varchar(200)         null,
   children             varchar(1000)        null,
   code                 varchar(100)         null,
   jp                   varchar(40)          null,
   qp                   varchar(40)          null,
   creatingtime         datetime             null default current_timestamp,
   creator              varchar(100)         null default '1',
   orgnumber            varchar(100)         null,
   orgdesc              varchar(300)         null,
   chargeorgid          varchar(50)          null,
   org_level            varchar(1)           null default '1',
   org_xzqm             varchar(12)          null,
   isdirectlyparty      numeric(1)           null default 0,
   isforeignparty       numeric(1)           null default 0,
   isjichaparty         numeric(1)           null default 0,
   isdirectguanhu       numeric(1)           null default 0,
   remark1              varchar(100)         null,
   remark2              varchar(100)         null,
   remark3              varchar(100)         null,
   remark4              varchar(100)         null,
   remark5              varchar(100)         null,
   remark6              varchar(100)         null,
   remark7              varchar(100)         null,
   remark8              varchar(100)         null,
   remark9              varchar(100)         null,
   remark10             varchar(100)         null,
   constraint pk_td_sm_organization primary key nonclustered (org_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构信息表',
   'user', @currentuser, 'table', 'td_sm_organization'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构ID',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'org_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构排序ID',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'org_sn'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构名称',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'org_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构显示名称',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'org_showname'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父机构ID',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'parent_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '路径',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'path'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '层（阶次）',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'layer'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '子机构',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'children'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构代号',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'code'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '简拼',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'jp'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '全拼',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'qp'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'creatingtime'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'creator'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构编号',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'orgnumber'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构描述',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'orgdesc'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主管机构',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'chargeorgid'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构行政级别，1：省级，2：市州级，3：县区级，4：科所级',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'org_level'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '行政区码',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'org_xzqm'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否直属局 0-不是，缺省值 1-是',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'isdirectlyparty'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否涉外局 0-是，缺省值 1-不是',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'isforeignparty'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否稽查局 0-不是，缺省值 1-是',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'isjichaparty'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否直接管户单位 0-不是，缺省值 1-是',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'isdirectguanhu'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段1',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'remark1'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段2',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'remark2'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段3',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'remark3'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段4',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'remark4'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段5',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'remark5'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段6',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'remark6'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段7',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'remark7'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段8',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'remark8'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段9',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'remark9'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段10',
   'user', @currentuser, 'table', 'td_sm_organization', 'column', 'remark10'
go

create table td_sm_orgjob (
   org_id               varchar(50)          not null,
   job_id               varchar(50)          not null,
   job_sn               int                  null,
   constraint pk_td_sm_orgjob primary key nonclustered (org_id, job_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构岗位关系表',
   'user', @currentuser, 'table', 'td_sm_orgjob'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构ID',
   'user', @currentuser, 'table', 'td_sm_orgjob', 'column', 'org_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位ID',
   'user', @currentuser, 'table', 'td_sm_orgjob', 'column', 'job_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构岗位序号',
   'user', @currentuser, 'table', 'td_sm_orgjob', 'column', 'job_sn'
go

create index td_sm_orgjob_fk on td_sm_orgjob (
org_id asc
)
go

create index td_sm_orgjob_fk2 on td_sm_orgjob (
job_id asc
)
go

create table td_sm_role (
   role_id              varchar(50)          not null,
   role_name            varchar(100)         not null,
   role_type            varchar(50)          null,
   role_desc            varchar(1024)        null,
   role_usage           varchar(1)           null default '1',
   owner_id             varchar(50)          null default '1',
   remark1              varchar(100)         null,
   remark2              varchar(100)         null,
   remark3              varchar(100)         null,
   remark4              varchar(100)         null,
   remark5              varchar(100)         null,
   constraint pk_td_sm_role primary key nonclustered (role_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色信息表',
   'user', @currentuser, 'table', 'td_sm_role'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色ID',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'role_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色名称',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'role_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色类型',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'role_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色描述',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'role_desc'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否使用 1:使用,0:不使用',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'role_usage'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人id，默认为系统管理员',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'owner_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段1',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'remark1'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段2',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'remark2'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段3',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'remark3'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段4',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'remark4'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段5',
   'user', @currentuser, 'table', 'td_sm_role', 'column', 'remark5'
go

create table td_sm_roleresop (
   op_id                varchar(50)          not null,
   res_id               varchar(100)         null,
   role_id              varchar(50)          not null,
   restype_id           varchar(50)          null,
   auto                 varchar(50)          null,
   res_name             varchar(500)         null,
   types                varchar(50)          null,
   authorization_type   numeric              null default 0,
   use_count            numeric              null,
   authorization_stime  datetime             null default current_timestamp,
   authorization_etime  datetime             null,
   use_counted          numeric              null,
   org_id               varchar(50)          null default '-1',
   constraint pk_td_sm_roleresop primary key nonclustered (op_id, role_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限授予表',
   'user', @currentuser, 'table', 'td_sm_roleresop'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作ID',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'op_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '资源ID',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'res_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色ID',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'role_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '资源类型ID',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'restype_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '标识资源维护方式，0自动维护，1手动维护',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'auto'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '资源名称',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'res_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '资源分类（是角色资源还是用户资源）',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'types'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权的方式:0:永久授权,1:计数授权,2:时效授权',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'authorization_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权可使用次数',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'use_count'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权使用开始时间',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'authorization_stime'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授权使用结束时间',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'authorization_etime'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限已使用次数',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'use_counted'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构ID',
   'user', @currentuser, 'table', 'td_sm_roleresop', 'column', 'org_id'
go

create index td_sm_roleresop_fk on td_sm_roleresop (
op_id asc
)
go

create index td_sm_roleresop_fk2 on td_sm_roleresop (
res_id asc
)
go

create index td_sm_roleresop_fk3 on td_sm_roleresop (
role_id asc
)
go

create table td_sm_roletype (
   type_id              varchar(50)          not null,
   type_name            varchar(100)         null,
   type_desc            varchar(500)         null,
   creator_user_id      varchar(50)          null default '1',
   creator_org_id       varchar(50)          null default '-1',
   constraint role_type_pk primary key nonclustered (type_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色类型信息表',
   'user', @currentuser, 'table', 'td_sm_roletype'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色类型ID',
   'user', @currentuser, 'table', 'td_sm_roletype', 'column', 'type_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色类型名称',
   'user', @currentuser, 'table', 'td_sm_roletype', 'column', 'type_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色类型描述',
   'user', @currentuser, 'table', 'td_sm_roletype', 'column', 'type_desc'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人ID',
   'user', @currentuser, 'table', 'td_sm_roletype', 'column', 'creator_user_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人机构ID',
   'user', @currentuser, 'table', 'td_sm_roletype', 'column', 'creator_org_id'
go

create table td_sm_user (
   user_id              varchar(50)          not null,
   user_name            varchar(200)         not null,
   user_password        varchar(512)         not null,
   user_realname        varchar(100)         not null,
   user_pinyin          varchar(100)         null,
   user_sex             varchar(100)         null,
   user_hometel         varchar(100)         null,
   user_worktel         varchar(100)         null,
   user_workaddress     varchar(100)         null,
   user_mobiletel1      varchar(100)         null,
   user_mobiletel2      varchar(100)         null,
   user_fax             varchar(100)         null,
   user_oicq            varchar(100)         null,
   user_birthday        datetime             null,
   user_email           varchar(100)         null,
   user_address         varchar(200)         null,
   user_postalcode      varchar(10)          null,
   user_idcard          varchar(50)          null,
   user_isvalid         numeric              null,
   user_regdate         datetime             null default current_timestamp,
   user_logincount      numeric              null,
   user_type            varchar(100)         null,
   past_time            datetime             null,
   dredge_time          varchar(50)          null,
   lastlogin_date       datetime             null,
   worklength           varchar(50)          null,
   politics             varchar(100)         null,
   login_ip             varchar(15)          null,
   cert_sn              varchar(50)          null,
   user_sn              numeric              null default 999,
   remark1              varchar(100)         null,
   remark2              varchar(100)         null,
   remark3              varchar(100)         null,
   remark4              varchar(100)         null,
   remark5              varchar(100)         null,
   remark6              varchar(100)         null,
   remark7              varchar(100)         null,
   remark8              varchar(100)         null,
   remark9              varchar(100)         null,
   remark10             varchar(100)         null,
   constraint pk_td_sm_user primary key nonclustered (user_id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户信息表',
   'user', @currentuser, 'table', 'td_sm_user'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户ID',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户帐号',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户密码',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_password'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户实名',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_realname'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '拼音',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_pinyin'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '性别',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_sex'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '家庭电话',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_hometel'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '公司电话',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_worktel'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '公司地址',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_workaddress'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '手机1',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_mobiletel1'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '手机2',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_mobiletel2'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '传真',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_fax'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'OICQ',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_oicq'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '生日',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_birthday'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮箱',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_email'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '住址',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_address'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮编',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_postalcode'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '身份证',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_idcard'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否使用',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_isvalid'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '注册日期',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_regdate'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '登陆次数',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_logincount'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户类型',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '过期时间',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'past_time'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开通时间',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'dredge_time'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户最后登陆时间',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'lastlogin_date'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '工作年限',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'worklength'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '政治面貌',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'politics'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '登录IP',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'login_ip'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '证书key的唯一标识',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'cert_sn'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户排序号',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'user_sn'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段1',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'remark1'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段2',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'remark2'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段3',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'remark3'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段4',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'remark4'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段5',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'remark5'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段6',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'remark6'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段7',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'remark7'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段8',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'remark8'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段9',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'remark9'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备用字段10',
   'user', @currentuser, 'table', 'td_sm_user', 'column', 'remark10'
go

create unique index in_td_sm_user_uname on td_sm_user (
user_name asc
)
go

create table td_sm_userinstance (
   id                   varchar(50)          not null,
   user_id              varchar(50)          not null,
   scope_id             varchar(50)          null,
   scope_type           char(1)              not null default '0',
   is_enabled           char(1)              not null default '1',
   constraint pk_td_sm_userinstance primary key nonclustered (id)
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户实例表',
   'user', @currentuser, 'table', 'td_sm_userinstance'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户实例ID',
   'user', @currentuser, 'table', 'td_sm_userinstance', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户ID',
   'user', @currentuser, 'table', 'td_sm_userinstance', 'column', 'user_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '活动范围ID',
   'user', @currentuser, 'table', 'td_sm_userinstance', 'column', 'scope_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '活动范围类型（0：全局，1：机构，2：岗位）',
   'user', @currentuser, 'table', 'td_sm_userinstance', 'column', 'scope_type'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否有效（0：否，1：是）',
   'user', @currentuser, 'table', 'td_sm_userinstance', 'column', 'is_enabled'
go

create table td_sm_userinstancegroup (
   userinstance_id      varchar(50)          not null,
   group_id             varchar(50)          not null,
   sn                   numeric              null default 9999
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户实例与用户组关系表',
   'user', @currentuser, 'table', 'td_sm_userinstancegroup'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户实例ID',
   'user', @currentuser, 'table', 'td_sm_userinstancegroup', 'column', 'userinstance_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户组ID',
   'user', @currentuser, 'table', 'td_sm_userinstancegroup', 'column', 'group_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户排序号',
   'user', @currentuser, 'table', 'td_sm_userinstancegroup', 'column', 'sn'
go

create table td_sm_userinstancerole (
   userinstance_id      varchar(50)          not null,
   role_id              varchar(50)          not null
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户实例与角色关系表',
   'user', @currentuser, 'table', 'td_sm_userinstancerole'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户实例ID',
   'user', @currentuser, 'table', 'td_sm_userinstancerole', 'column', 'userinstance_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色ID',
   'user', @currentuser, 'table', 'td_sm_userinstancerole', 'column', 'role_id'
go

create table td_sm_userjoborg_history (
   user_id              varchar(50)          not null,
   job_id               varchar(50)          not null,
   job_name             varchar(100)         not null,
   org_id               varchar(50)          not null,
   org_name             varchar(100)         not null,
   job_starttime        datetime             null,
   job_quashtime        datetime             null,
   job_fettle           varchar(50)          null
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户历史任职岗位表',
   'user', @currentuser, 'table', 'td_sm_userjoborg_history'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户ID',
   'user', @currentuser, 'table', 'td_sm_userjoborg_history', 'column', 'user_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位ID',
   'user', @currentuser, 'table', 'td_sm_userjoborg_history', 'column', 'job_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '岗位名称',
   'user', @currentuser, 'table', 'td_sm_userjoborg_history', 'column', 'job_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构ID',
   'user', @currentuser, 'table', 'td_sm_userjoborg_history', 'column', 'org_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构名称',
   'user', @currentuser, 'table', 'td_sm_userjoborg_history', 'column', 'org_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开始授予岗位时间',
   'user', @currentuser, 'table', 'td_sm_userjoborg_history', 'column', 'job_starttime'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '结束授予岗位时间',
   'user', @currentuser, 'table', 'td_sm_userjoborg_history', 'column', 'job_quashtime'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授予者用户ID',
   'user', @currentuser, 'table', 'td_sm_userjoborg_history', 'column', 'job_fettle'
go

create table td_sm_userpreferences (
   user_id              varchar(50)          not null,
   info_name            varchar(500)         not null,
   info_content         varchar(2000)        null,
   info_desc            varchar(500)         null,
   info_lastupdate      datetime             null default current_timestamp
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户偏好设置信息表',
   'user', @currentuser, 'table', 'td_sm_userpreferences'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户ID',
   'user', @currentuser, 'table', 'td_sm_userpreferences', 'column', 'user_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '信息名称',
   'user', @currentuser, 'table', 'td_sm_userpreferences', 'column', 'info_name'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '信息内容',
   'user', @currentuser, 'table', 'td_sm_userpreferences', 'column', 'info_content'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '信息描述',
   'user', @currentuser, 'table', 'td_sm_userpreferences', 'column', 'info_desc'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @currentuser, 'table', 'td_sm_userpreferences', 'column', 'info_lastupdate'
go

alter table tb_sm_custres
   add constraint fk_tb_sm_staticoper1 foreign key (privilege_id)
      references tb_sm_privilege (id)
go

alter table tb_sm_decision_entitlement
   add constraint fk_tb_sm_decision_entitlement1 foreign key (privilege_id)
      references tb_sm_privilege (id)
go

alter table tb_sm_decision_entitlement
   add constraint fk_tb_sm_decision_entitlement2 foreign key (usercategory_id)
      references tb_sm_usercategory (id)
go

alter table tb_sm_decision_entitlement
   add constraint fk_tb_sm_decision_entitlement3 foreign key (businessdata_id)
      references tb_sm_businessdata (id)
go

alter table tb_sm_menu
   add constraint fk_tb_sm_moudle1 foreign key (menu_id)
      references tb_sm_privilege (id)
go

alter table tb_sm_query_entitlement
   add constraint fk_tb_sm_query_entitlement2 foreign key (query_id)
      references tb_sm_query (id)
go

alter table tb_sm_query_entitlement
   add constraint fk_tb_sm_query_entitlement3 foreign key (usercategory_id)
      references tb_sm_usercategory (id)
go

alter table tb_sm_query_entitlement
   add constraint fk_tb_sm_query_entitlement1 foreign key (privilege_id)
      references tb_sm_privilege (id)
go

alter table tb_system_info
   add constraint fk_tb_system_info foreign key (info_type)
      references tb_system_type (id)
go

alter table td_df_data_obj_column
   add constraint td_df_data_obj_column_fk foreign key (df_object_id)
      references td_df_data_obj (df_object_id)
go

alter table td_sm_dictdata
   add constraint fk_td_sm_dictdata foreign key (dicttype_id)
      references td_sm_dicttype (dicttype_id)
go

alter table td_sm_grouporg
   add constraint fk_td_sm_grouporg1 foreign key (group_id)
      references td_sm_group (group_id)
go

alter table td_sm_grouporg
   add constraint fk_td_sm_grouporg2 foreign key (org_id)
      references td_sm_organization (org_id)
go

alter table td_sm_grouporgjob
   add constraint fk_td_sm_grouporgjob1 foreign key (group_id)
      references td_sm_group (group_id)
go

alter table td_sm_grouporgjob
   add constraint fk_td_sm_grouporgjob2 foreign key (job_id)
      references td_sm_job (job_id)
go

alter table td_sm_grouprole
   add constraint fk_td_sm_grouprole1 foreign key (group_id)
      references td_sm_group (group_id)
go

alter table td_sm_grouprole
   add constraint fk_td_sm_grouprole2 foreign key (role_id)
      references td_sm_role (role_id)
go

alter table td_sm_instance_job
   add constraint fk_td_sm_instance_job foreign key (userinstance_id)
      references td_sm_userinstance (id)
go

alter table td_sm_instance_org
   add constraint fk_td_sm_instance_org1 foreign key (userinstance_id)
      references td_sm_userinstance (id)
go

alter table td_sm_orgjob
   add constraint fk_td_sm_orgjob1 foreign key (org_id)
      references td_sm_organization (org_id)
go

alter table td_sm_orgjob
   add constraint fk_td_sm_orgjob2 foreign key (job_id)
      references td_sm_job (job_id)
go

alter table td_sm_role
   add constraint fk_td_sm_role foreign key (role_type)
      references td_sm_roletype (type_id)
go

alter table td_sm_userinstance
   add constraint fk_td_sm_userinstance foreign key (user_id)
      references td_sm_user (user_id)
go

alter table td_sm_userinstancegroup
   add constraint fk_td_sm_userinstancegroup1 foreign key (userinstance_id)
      references td_sm_userinstance (id)
go

alter table td_sm_userinstancegroup
   add constraint fk_td_sm_userinstancegroup2 foreign key (group_id)
      references td_sm_group (group_id)
go

alter table td_sm_userinstancerole
   add constraint fk_td_sm_userinstancerole1 foreign key (userinstance_id)
      references td_sm_userinstance (id)
go

alter table td_sm_userinstancerole
   add constraint fk_td_sm_userinstancerole2 foreign key (role_id)
      references td_sm_role (role_id)
go

-- 存储过程脚本_sqlsqlserver -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
---日志备份存储过程
--start
create procedure log_backup
--日志备份，backup_date 表示当前系统时间多少天已前的数据需要备份
@backup_date numeric
as
begin 
-- 备份日志主表
	insert into td_sm_log_his
		select * from td_sm_log t where t.log_opertime<=getdate()-@backup_date;
-- 删除日志主表
	delete from td_sm_log  where log_opertime<=getdate()-@backup_date;
end;
go
--end;

-- 函数脚本_sqlsqlserver -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
-- 获取机构所有子节点，包括本身
--start
create function org_getChilds
(
	@rootid  varchar(50)
)
returns varchar(8000)
as
begin
	declare  @temp varchar(8000)   
  
	select @temp=','+org_id+',' from td_sm_organization where org_id=@rootid
    while  @@rowCount>0 
      select   @temp=@temp+org_id+',' from td_sm_organization       
         where charindex(','+org_id+',',@temp)=0    
               and   charindex(','+parent_id+',',@temp)>0
	return @temp
end;
go
--end;

-- 获取机构所有父节点，包括本身
--start
create function org_getParents
(
	@rootid  varchar(50)
)
returns varchar(8000)
as
begin
   
	declare  @temp varchar(8000)   
    declare  @tempid varchar(8000)
    
	 set @tempid=@rootid;
     set @temp=','+@rootid+',';
     while @tempid!='0'
          begin
          select @tempid=parent_id FROM td_sm_organization where org_id= @tempid;
           IF  @tempid!='0'
          SET @temp =@temp+@tempid+',';
          end
	return @temp
end;
go
--end;

-- 获取权限所有子节点，包括本身
--start
create function privilege_getChilds
(
	@rootid  varchar(50)
)
returns varchar(8000)
as
begin
	declare  @temp varchar(8000)   
  
	select @temp=','+id+',' from tb_sm_privilege where id=@rootid
    while  @@rowCount>0 
      select   @temp=@temp+id+',' from tb_sm_privilege       
         where charindex(','+id+',',@temp)=0    
               and   charindex(','+parent_id+',',@temp)>0
	return @temp
end;
go
--end;

-- 获取权限所有父节点，包括本身
--start
create function privilege_getParents
(
	@rootid  varchar(50)
)
returns varchar(8000)
as
begin
   
	declare  @temp varchar(8000)   
    declare  @tempid varchar(8000)
    
	 set @tempid=@rootid;
     set @temp=','+@rootid+',';
     while @tempid!='0'
          begin
          select @tempid=parent_id FROM tb_sm_privilege where id= @tempid;
           IF  @tempid!='0'
          SET @temp =@temp+@tempid+',';
          end
	return @temp
end;
go
--end;

-- 初始化脚本_mysql -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
-- 清除数据
delete from td_sm_instance_org;
delete from td_sm_instance_job;
delete from td_sm_userinstancegroup;
delete from td_sm_userinstancerole;
delete from td_sm_userinstance;
delete from td_sm_grouporg;
delete from td_sm_grouporgjob;
delete from td_sm_grouprole;
delete from td_sm_orgjob;
delete from td_sm_user;
delete from td_sm_role;
delete from td_sm_roletype;
delete from td_sm_group;
delete from td_sm_organization;
delete from td_sm_job;
delete from td_sm_userjoborg_history;
delete from td_sm_roleresop;
delete from tb_sm_query;
delete from tb_sm_usercategory;
delete from tb_sm_businessdata;
delete from tb_sm_query_entitlement;
delete from tb_sm_decision_entitlement;
delete from tb_sm_custres;
delete from tb_sm_menu;
delete from tb_sm_privilege;
delete from td_df_data_obj_column;
delete from td_df_data_obj;
delete from td_sm_log_his;
delete from td_sm_log;
delete from td_sm_logconfig;
delete from td_sm_dictdata;
delete from td_sm_dicttype;
delete from tb_system_info;
delete from tb_system_type;
delete from tb_application_info;

-- 初始化数据
-- 字典
insert into td_sm_dicttype (dicttype_id, dicttype_name, dicttype_desc) values ('1', '性别', '性别');
insert into td_sm_dicttype (dicttype_id, dicttype_name, dicttype_desc) values ('2', '用户类型', '用户类型');
insert into td_sm_dicttype (dicttype_id, dicttype_name, dicttype_desc) values ('3', '当前状态', '用户当前状态');
insert into td_sm_dicttype (dicttype_id, dicttype_name, dicttype_desc) values ('4', '政治面貌', '政治面貌');
insert into td_sm_dicttype (dicttype_id, dicttype_name, dicttype_desc) values ('5', '行政级别', '行政级别');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('10', '1', '-1', '未知', 0, '1');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('11', '1', 'M', '男', 1, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('12', '1', 'F', '女', 2, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('20', '2', '0', '系统用户', 0, '1');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('21', '2', '1', '外部用户', 1, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('30', '3', '0', '删除', 0, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('31', '3', '1', '申请', 1, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('32', '3', '2', '开通', 2, '1');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('33', '3', '3', '停用', 3, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('40', '4', '群众', '群众', 0, '1');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('41', '4', '共青团员', '共青团员', 1, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('42', '4', '中共党员', '中共党员', 2, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('43', '4', '民主党派', '民主党派', 3, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('50', '5', '1', '省级', 0, '1');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('51', '5', '2', '市州级', 1, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('52', '5', '3', '县区级', 2, '0');
insert into td_sm_dictdata (dictdata_id, dicttype_id, dictdata_name, dictdata_value, dictdata_order, dictdata_isdefault) values ('53', '5', '4', '科所级', 3, '0');

-- 权限及菜单
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('1', 'sysMgt', '系统管理', '0', '4', 'sysMgt', '1', current_timestamp, 1);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('11', 'myPanel', '我的面板', '1', '4', 'myPanel', '1', current_timestamp, 1);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('12', 'sysSet', '系统设置', '1', '4', 'sysSet', '1', current_timestamp, 2);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('13', 'logMgt', '日志管理', '1', '4', 'logMgt', '1', current_timestamp, 3);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('14', 'aspMgt', '平台管理', '1', '4', 'aspMgt', '1', current_timestamp, 4);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('111', 'personInfo', '个人信息', '11', '4', 'personInfo', '1', current_timestamp, 1);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('112', 'modifyPwd', '密码修改', '11', '4', 'modifyPwd', '1', current_timestamp, 2);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('123', 'dictMgt', '字典管理', '12', '4', 'dictMgt', '1', current_timestamp, 5);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('124', 'orgMgt', '机构管理', '12', '4', 'orgMgt', '1', current_timestamp, 1);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('125', 'userMgt', '用户管理', '12', '4', 'userMgt', '1', current_timestamp, 2);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('127', 'resMgt', '资源维护', '12', '4', 'resMgt', '1', current_timestamp, 4);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('128', 'roleMgt', '角色管理', '12', '4', 'roleMgt', '1', current_timestamp, 3);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('131', 'logQuery', '日志查询', '13', '4', 'logQuery', '1', current_timestamp, 1);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('141', 'sysInfo', '全局参数配置', '14', '4', 'sysInfo', '1', current_timestamp, 1);
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('1', '1', null, 'fa-briefcase', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('11', '1', null, 'fa-dashboard', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('12', '1', null, 'fa-wrench', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('13', '1', null, 'fa-book', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('14', '1', null, 'fa-cogs', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('111', '1', '#/f/personInfo', 'fa-user', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('112', '1', '#/f/modifyPwd', 'fa-key', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('123', '1', '#/f/dictMain', 'fa-list', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('124', '1', '#/f/orgMain', 'fa-sitemap', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('125', '1', '#/f/userMain', 'fa-users', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('127', '1', '#/f/resMain', 'fa-tree', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('128', '1', '#/f/roleMain', 'fa-male', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('131', '1', '#/f/logQueryMain', 'fa-bookmark', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('141', '1', '#/f/sysInfoMain', 'fa-flask', '0');

-- 用户
insert into td_sm_user (user_id, user_name, user_password,user_realname, user_isvalid)
values ('1', 'admin', '123456', '系统管理员', 2);
-- 角色
insert into td_sm_roletype (type_id, type_name, type_desc, creator_user_id, creator_org_id) values ('-1', '匿名角色类型', '匿名角色类型', '1', '-1');
insert into td_sm_roletype (type_id, type_name, type_desc, creator_user_id, creator_org_id) values ('1', '系统类型', '系统类型', '1', '-1');
insert into td_sm_roletype (type_id, type_name, type_desc, creator_user_id, creator_org_id) values ('2', '一般类型', '一般类型', '1', '-1');
insert into td_sm_roletype (type_id, type_name, type_desc, creator_user_id, creator_org_id) values ('3', '业务类型', '业务类型', '1', '-1');
insert into td_sm_role (role_id, role_name, role_type, role_desc, role_usage, owner_id, remark1, remark2, remark3, remark4, remark5) values ('1', '超级管理员', '1', '超级管理员角色，此角色不要修改，超级管理员admin自动拥有本角色，本角色也不需要授予权限，默认拥有系统中的所有权限', '1', '1', null, null, null, null, null);
insert into td_sm_role (role_id, role_name, role_type, role_desc, role_usage, owner_id, remark1, remark2, remark3, remark4, remark5) values ('2', '普通用户', '1', '每个用户都缺省拥有的角色，此角色不能被修改，也不能被删除', '1', '1', null, null, null, null, null);

-- 授予用户admin系统管理员角色
insert into td_sm_userinstance (id, user_id, scope_id, scope_type, is_enabled) values ('1', '1', null, '0', '1');
insert into td_sm_userinstancerole (userinstance_id, role_id) values ('1', '1');

go