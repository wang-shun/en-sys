-- 系统管理V4.2.3标准精简版建库及初始化脚本_postgresql
-- 建库脚本_postgresql -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
drop table tb_application_info;

drop table tb_sm_businessdata;

drop table tb_sm_custres;

drop table tb_sm_decision_entitlement;

drop table tb_sm_menu;

drop table tb_sm_privilege;

drop table tb_sm_privilege_insiderelate;

drop table tb_sm_query;

drop table tb_sm_query_entitlement;

drop table tb_sm_usercategory;

drop table tb_system_info;

drop table tb_system_type;

drop table td_df_data_obj;

drop table td_df_data_obj_column;

drop table td_sm_dictdata;

drop table td_sm_dicttype;

drop table td_sm_group;

drop table td_sm_grouporg;

drop table td_sm_grouporgjob;

drop index td_sm_grouprole_fk2;

drop index td_sm_grouprole_fk;

drop table td_sm_grouprole;

drop table td_sm_instance_job;

drop table td_sm_instance_org;

drop table td_sm_job;

drop table td_sm_log;

drop table td_sm_logconfig;

drop table td_sm_log_his;

drop table td_sm_organization;

drop index td_sm_orgjob_fk2;

drop index td_sm_orgjob_fk;

drop table td_sm_orgjob;

drop table td_sm_role;

drop index td_sm_roleresop_fk3;

drop index td_sm_roleresop_fk2;

drop index td_sm_roleresop_fk;

drop table td_sm_roleresop;

drop table td_sm_roletype;

drop index in_td_sm_user_uname;

drop table td_sm_user;

drop table td_sm_userinstance;

drop table td_sm_userinstancegroup;

drop table td_sm_userinstancerole;

drop table td_sm_userjoborg_history;

drop table td_sm_userpreferences;

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
   constraint pk_tb_application_info primary key (app_id)
);

comment on table tb_application_info is
'应用信息表';

comment on column tb_application_info.app_id is
'应用ID';

comment on column tb_application_info.app_name is
'应用名称';

comment on column tb_application_info.remark is
'描述';

comment on column tb_application_info.dbname is
'应用数据源名称';

comment on column tb_application_info.dbuser is
'数据库用户名';

comment on column tb_application_info.dbpwd is
'数据库用户密码';

comment on column tb_application_info.deftablespace is
'缺省表空间';

comment on column tb_application_info.temptablespace is
'临时表空间';

comment on column tb_application_info.workfolder is
'文件夹名称';

comment on column tb_application_info.accpath is
'访问路径';

comment on column tb_application_info.enablestatus is
'应用使能状态，0为不可用，1为可用';

comment on column tb_application_info.functionlist is
'功能开关:从右往左第一位代表短信，第二位代表内部邮件，第三位代表RTX';

comment on column tb_application_info.ismsgcenter is
'是否显示消息中心:0代表不显示，1代表显示，缺省为显示';

comment on column tb_application_info.appimgpath is
'应用图标路径';

create table tb_sm_businessdata (
   id                   varchar(50)          not null,
   name                 varchar(50)          not null,
   description          varchar(200)         null,
   parent_id            varchar(50)          not null default '0',
   isleaf               char(1)              null default '1',
   rule                 text                 null,
   constraint pk_tb_sm_businessdata primary key (id)
);

comment on table tb_sm_businessdata is
'业务数据分类表';

comment on column tb_sm_businessdata.id is
'序号';

comment on column tb_sm_businessdata.name is
'名称';

comment on column tb_sm_businessdata.description is
'备注';

comment on column tb_sm_businessdata.parent_id is
'父ID';

comment on column tb_sm_businessdata.isleaf is
'是否子节点(0:否1:是)';

comment on column tb_sm_businessdata.rule is
'分类规则';

create table tb_sm_custres (
   privilege_id         varchar(50)          not null,
   restype_id           varchar(50)          not null,
   restype_name         varchar(200)         not null,
   res_id               varchar(50)          not null,
   res_name             varchar(200)         not null,
   grintlevel           char(1)              not null default '1',
   constraint pk_tb_sm_custres primary key (privilege_id)
);

comment on table tb_sm_custres is
'资源信息表';

comment on column tb_sm_custres.privilege_id is
'权限ID';

comment on column tb_sm_custres.restype_id is
'资源类型ID';

comment on column tb_sm_custres.restype_name is
'资源类型名称';

comment on column tb_sm_custres.res_id is
'资源ID';

comment on column tb_sm_custres.res_name is
'资源名称';

comment on column tb_sm_custres.grintlevel is
'授予级别（-1：管理员，1：所有用户）';

create table tb_sm_decision_entitlement (
   id                   varchar(50)          not null,
   privilege_id         varchar(50)          not null,
   usercategory_id      varchar(50)          not null,
   businessdata_id      varchar(50)          not null,
   effect               char(1)              not null default '1',
   denyreason           varchar(2000)        null,
   priority             numeric              not null default 0,
   constraint pk_tb_sm_decision_entitlement primary key (id)
);

comment on table tb_sm_decision_entitlement is
'决策权限策略表';

comment on column tb_sm_decision_entitlement.id is
'序号';

comment on column tb_sm_decision_entitlement.privilege_id is
'权限ID';

comment on column tb_sm_decision_entitlement.usercategory_id is
'用户分类ID';

comment on column tb_sm_decision_entitlement.businessdata_id is
'业务数据分类ID';

comment on column tb_sm_decision_entitlement.effect is
'决策（0：false，1：true）';

comment on column tb_sm_decision_entitlement.denyreason is
'拒绝理由';

comment on column tb_sm_decision_entitlement.priority is
'优先级别';

create table tb_sm_menu (
   menu_id              varchar(50)          not null,
   is_enabled           char(1)              not null default '1',
   href                 varchar(500)         null,
   icon                 varchar(500)         null,
   display_mode         char(1)              not null default '0',
   menu_ext             text                 null,
   constraint pk_tb_sm_menu primary key (menu_id)
);

comment on table tb_sm_menu is
'模块信息表';

comment on column tb_sm_menu.menu_id is
'菜单ID';

comment on column tb_sm_menu.is_enabled is
'是否显示（0：不显示，1：显示）';

comment on column tb_sm_menu.href is
'链接路径';

comment on column tb_sm_menu.icon is
'图标';

comment on column tb_sm_menu.display_mode is
'打开方式（0：div中，1：iframe中，2：新页面中）';

comment on column tb_sm_menu.menu_ext is
'菜单扩展字段';

create table tb_sm_privilege (
   id                   varchar(50)          not null,
   code                 varchar(500)         not null,
   name                 varchar(500)         null,
   parent_id            varchar(500)         not null default '0',
   type                 char(1)              not null,
   perm_expr            varchar(2000)        null,
   creator              varchar(50)          null default '1',
   creator_time         timestamp            null default current_timestamp,
   sn                   numeric              null,
   source               char(1)              not null default '0',
   constraint pk_tb_resource_type primary key (id)
);

comment on table tb_sm_privilege is
'权限信息表';

comment on column tb_sm_privilege.id is
'序号';

comment on column tb_sm_privilege.code is
'权限编码';

comment on column tb_sm_privilege.name is
'权限名称';

comment on column tb_sm_privilege.parent_id is
'父ID';

comment on column tb_sm_privilege.type is
'类型（1:服务(service),2:表单(form),3:表单元素(dom),4:菜单(menu),5:实体(entity),6:实体操作(entity_op),9:自定义(custom)）';

comment on column tb_sm_privilege.perm_expr is
'权限字符串';

comment on column tb_sm_privilege.creator is
'创建者';

comment on column tb_sm_privilege.creator_time is
'创建时间';

comment on column tb_sm_privilege.sn is
'排序号';

comment on column tb_sm_privilege.source is
'权限来源（0：自定义，1：IDE）';

create table tb_sm_privilege_insiderelate (
   id                   varchar(50)          not null,
   relate_id            varchar(50)          not null
);

comment on table tb_sm_privilege_insiderelate is
'权限内部关联表';

comment on column tb_sm_privilege_insiderelate.id is
'权限ID';

comment on column tb_sm_privilege_insiderelate.relate_id is
'关联ID';

create table tb_sm_query (
   id                   varchar(50)          not null,
   name                 varchar(50)          not null,
   description          varchar(200)         null,
   parent_id            varchar(50)          not null default '0',
   isleaf               char(1)              null default '1',
   rule                 text                 null,
   constraint pk_tb_sm_query primary key (id)
);

comment on table tb_sm_query is
'数据查询表';

comment on column tb_sm_query.id is
'序号';

comment on column tb_sm_query.name is
'名称';

comment on column tb_sm_query.description is
'备注';

comment on column tb_sm_query.parent_id is
'父ID';

comment on column tb_sm_query.isleaf is
'是否子节点(0:否1:是)';

comment on column tb_sm_query.rule is
'查询规则';

create table tb_sm_query_entitlement (
   id                   varchar(50)          not null,
   privilege_id         varchar(50)          not null,
   query_id             varchar(50)          not null,
   usercategory_id      varchar(50)          not null,
   priority             numeric              not null default 0,
   constraint pk_tb_sm_query_entitlement primary key (id)
);

comment on table tb_sm_query_entitlement is
'查询权限策略表';

comment on column tb_sm_query_entitlement.id is
'序号';

comment on column tb_sm_query_entitlement.privilege_id is
'权限ID';

comment on column tb_sm_query_entitlement.query_id is
'数据查询ID';

comment on column tb_sm_query_entitlement.usercategory_id is
'用户分类ID';

comment on column tb_sm_query_entitlement.priority is
'优先级别';

create table tb_sm_usercategory (
   id                   varchar(50)          not null,
   name                 varchar(50)          not null,
   description          varchar(200)         null,
   parent_id            varchar(50)          not null default '0',
   isleaf               char(1)              null default '1',
   rule                 text                 null,
   constraint pk_tb_sm_usercategory primary key (id)
);

comment on table tb_sm_usercategory is
'用户类型表';

comment on column tb_sm_usercategory.id is
'序号';

comment on column tb_sm_usercategory.name is
'名称';

comment on column tb_sm_usercategory.description is
'备注';

comment on column tb_sm_usercategory.parent_id is
'父ID';

comment on column tb_sm_usercategory.isleaf is
'是否子节点(0:否1:是)';

comment on column tb_sm_usercategory.rule is
'分类规则';

create table tb_system_info (
   id                   varchar(50)          not null,
   info_name            varchar(500)         not null,
   info_content         varchar(2000)        null,
   info_desc            varchar(500)         null,
   info_type            varchar(50)          not null,
   canremove            varchar(50)          not null default '1',
   remark               varchar(200)         null,
   constraint pk_tb_system_info primary key (id)
);

comment on table tb_system_info is
'系统设置信息表';

comment on column tb_system_info.id is
'系统信息ID';

comment on column tb_system_info.info_name is
'系统信息名称';

comment on column tb_system_info.info_content is
'系统信息内容';

comment on column tb_system_info.info_desc is
'系统信息描述';

comment on column tb_system_info.info_type is
'系统信息类型';

comment on column tb_system_info.canremove is
'是否可以删除字段，0：可删除 1：不可删除';

comment on column tb_system_info.remark is
'备用字段';

create table tb_system_type (
   id                   varchar(50)          not null,
   type_name            varchar(50)          not null,
   type_desc            varchar(100)         null,
   constraint pk_tb_system_type primary key (id)
);

comment on table tb_system_type is
'系统类别表';

comment on column tb_system_type.id is
'系统信息类型ID';

comment on column tb_system_type.type_name is
'系统信息类型名称';

comment on column tb_system_type.type_desc is
'系统信息类型描述';

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
   constraint pk_td_df_data_obj primary key (df_object_id)
);

comment on table td_df_data_obj is
'动态表单数据对象表';

comment on column td_df_data_obj.df_object_id is
'数据对象ID';

comment on column td_df_data_obj.df_ds_id is
'数据源ID';

comment on column td_df_data_obj.df_object_name is
'数据对象名称';

comment on column td_df_data_obj.df_table_name is
'数据对象表名';

comment on column td_df_data_obj.df_provide_type is
'信息提供类型:row按记录提供，col按字段提供';

comment on column td_df_data_obj.df_formcolnum is
'表单列数';

comment on column td_df_data_obj.df_pkauto_type is
'主键增长方式:tableinfo，native，none';

comment on column td_df_data_obj.df_createmenu_flag is
'是否创建菜单,0不创建，1创建';

comment on column td_df_data_obj.df_parentmenu_id is
'父菜单ID';

comment on column td_df_data_obj.df_menu_name is
'菜单名';

comment on column td_df_data_obj.df_status is
'数据对象状态,1可用,0不可用';

comment on column td_df_data_obj.df_regfrompoolman is
'数据对象是否来源Poolman数据源,1是,0否';

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
   constraint pk_td_df_data_obj_column primary key (df_column_id)
);

comment on table td_df_data_obj_column is
'动态表单数据对象字段表';

comment on column td_df_data_obj_column.df_column_id is
'字段ID';

comment on column td_df_data_obj_column.df_column_code is
'字段名';

comment on column td_df_data_obj_column.df_column_name is
'字段中文名';

comment on column td_df_data_obj_column.df_object_id is
'数据对象ID';

comment on column td_df_data_obj_column.df_column_type is
'字段类型';

comment on column td_df_data_obj_column.df_length is
'字段长度';

comment on column td_df_data_obj_column.df_precision is
'字段精度';

comment on column td_df_data_obj_column.df_logic_type is
'逻辑数据类型';

comment on column td_df_data_obj_column.df_sort_sn is
'字段排序号';

comment on column td_df_data_obj_column.df_maxlength is
'界面最大输入长度';

comment on column td_df_data_obj_column.df_orderby is
'默认查询的排序字段，可选值：空白（不排序）,asc,desc';

comment on column td_df_data_obj_column.df_uitype is
'字段对应表单组件类型';

comment on column td_df_data_obj_column.df_optiondata_type is
'下拉组件的选项数据来源类型，可选值：固定，字典';

comment on column td_df_data_obj_column.df_optiondata is
'下拉组件的选项数据。如果是固定则用格式存放内容”key1,value1;key2,value2”；如果是字典则保存sql语句。';

comment on column td_df_data_obj_column.df_optiondata_dsid is
'下拉组件的选项数据源Id';

comment on column td_df_data_obj_column.df_initdata is
'初始值。可用变量：#{currentUserName}当前用户名，#{currentUserName}当前用户，#{now}当前时间';

comment on column td_df_data_obj_column.df_uigroup is
'字段对应表单组件的所在分组';

comment on column td_df_data_obj_column.df_uirownum is
'组件在表单中所占行数，默认为1';

comment on column td_df_data_obj_column.df_uicolnum is
'组件在表单中所占列数，默认为2(标题和组件各一列)。';

comment on column td_df_data_obj_column.df_regex is
'表单字段值验证正则表达式';

comment on column td_df_data_obj_column.df_regex_message is
'表单字段值验证正则表达式错误信息';

comment on column td_df_data_obj_column.df_pk_flag is
'是否为主键';

comment on column td_df_data_obj_column.df_required_flag is
'表单验证是否非空';

comment on column td_df_data_obj_column.df_search_flag is
'是否为列表查询字段';

comment on column td_df_data_obj_column.df_listshow_flag is
'是否可在列表中默认显示';

comment on column td_df_data_obj_column.df_status is
'该字段状态，0不可用,1可用,-1对应表字段不存在';

comment on column td_df_data_obj_column.df_creater_field_flag is
'是否为添加人字段';

comment on column td_df_data_obj_column.df_creater_org_field_flag is
'是否为添加人机构字段';

create table td_sm_dictdata (
   dictdata_id          varchar(50)          not null,
   dicttype_id          varchar(50)          null,
   dictdata_name        varchar(100)         null,
   dictdata_value       varchar(100)         null,
   dictdata_order       numeric              null default 0,
   dictdata_isdefault   char(1)              null default '0',
   constraint pk_td_sm_dictdata primary key (dictdata_id)
);

comment on table td_sm_dictdata is
'字典数据表';

comment on column td_sm_dictdata.dictdata_id is
'字典ID';

comment on column td_sm_dictdata.dicttype_id is
'字典类型ID';

comment on column td_sm_dictdata.dictdata_name is
'字典真实值';

comment on column td_sm_dictdata.dictdata_value is
'字典显示值';

comment on column td_sm_dictdata.dictdata_order is
'字典排序号';

comment on column td_sm_dictdata.dictdata_isdefault is
'是否默认值（0：否，1：是）';

create table td_sm_dicttype (
   dicttype_id          varchar(50)          not null,
   dicttype_name        varchar(100)         not null,
   dicttype_desc        varchar(100)         null,
   constraint pk_td_sm_dicttype primary key (dicttype_id)
);

comment on table td_sm_dicttype is
'字典类型表';

comment on column td_sm_dicttype.dicttype_id is
'字典类型ID';

comment on column td_sm_dicttype.dicttype_name is
'字典类型名称';

comment on column td_sm_dicttype.dicttype_desc is
'字典类型描述';

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
   constraint pk_td_sm_group primary key (group_id)
);

comment on table td_sm_group is
'用户组信息表';

comment on column td_sm_group.group_id is
'用户组ID';

comment on column td_sm_group.group_name is
'用户组名称';

comment on column td_sm_group.group_desc is
'用户组描述';

comment on column td_sm_group.parent_id is
'父ID';

comment on column td_sm_group.owner_id is
'用户组创建人ID';

comment on column td_sm_group.remark1 is
'备用字段1';

comment on column td_sm_group.remark2 is
'备用字段2';

comment on column td_sm_group.remark3 is
'备用字段3';

comment on column td_sm_group.remark4 is
'备用字段4';

comment on column td_sm_group.remark5 is
'备用字段5';

comment on column td_sm_group.type is
'类型（0：自定义用户组，1：机构，2：机构岗位）';

create table td_sm_grouporg (
   group_id             varchar(50)          null,
   org_id               varchar(50)          null
);

comment on table td_sm_grouporg is
'用户组与机构关系表';

comment on column td_sm_grouporg.group_id is
'用户组ID';

comment on column td_sm_grouporg.org_id is
'机构ID';

create table td_sm_grouporgjob (
   group_id             varchar(50)          null,
   job_id               varchar(50)          null
);

comment on table td_sm_grouporgjob is
'用户组与机构岗位关系';

comment on column td_sm_grouporgjob.group_id is
'用户组ID';

comment on column td_sm_grouporgjob.job_id is
'岗位ID';

create table td_sm_grouprole (
   group_id             varchar(50)          not null,
   role_id              varchar(50)          not null,
   resop_origin_userid  varchar(50)          null default '1',
   constraint pk_td_sm_grouprole primary key (group_id, role_id)
);

comment on table td_sm_grouprole is
'用户组与角色关系表';

comment on column td_sm_grouprole.group_id is
'用户组ID';

comment on column td_sm_grouprole.role_id is
'角色ID';

comment on column td_sm_grouprole.resop_origin_userid is
'授予角色的用户';

create  index td_sm_grouprole_fk on td_sm_grouprole (
group_id
);

create  index td_sm_grouprole_fk2 on td_sm_grouprole (
role_id
);

create table td_sm_instance_job (
   userinstance_id      varchar(50)          not null,
   is_main              char(1)              not null default '0',
   sn                   numeric              null default 9999
);

comment on table td_sm_instance_job is
'岗位实例表';

comment on column td_sm_instance_job.userinstance_id is
'用户实例ID';

comment on column td_sm_instance_job.is_main is
'是否主岗位（0：否，1：是）';

comment on column td_sm_instance_job.sn is
'用户排序号';

create table td_sm_instance_org (
   userinstance_id      varchar(50)          not null,
   is_main              char(1)              not null default '0',
   sn                   numeric              null default 9999
);

comment on table td_sm_instance_org is
'机构实例表';

comment on column td_sm_instance_org.userinstance_id is
'用户实例ID';

comment on column td_sm_instance_org.is_main is
'是否主机构（0：否，1：是）';

comment on column td_sm_instance_org.sn is
'用户排序号';

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
   constraint pk_td_sm_job primary key (job_id)
);

comment on table td_sm_job is
'岗位信息表';

comment on column td_sm_job.job_id is
'岗位ID';

comment on column td_sm_job.job_name is
'岗位名称';

comment on column td_sm_job.job_desc is
'岗位描述';

comment on column td_sm_job.job_scope is
'岗位范围(0:通用岗位,1:机构特有)';

comment on column td_sm_job.job_function is
'岗位职责';

comment on column td_sm_job.job_amount is
'岗位编制人数';

comment on column td_sm_job.job_number is
'岗位编号';

comment on column td_sm_job.job_condition is
'任职条件';

comment on column td_sm_job.job_rank is
'岗位级别';

comment on column td_sm_job.owner_id is
'岗位授予人ID';

create table td_sm_log (
   log_id               varchar(50)          not null,
   log_operuser         varchar(200)         null,
   oper_module          varchar(50)          null,
   log_visitorial       varchar(200)         null,
   log_opertime         timestamp            null default current_timestamp,
   log_content          text                 null,
   oper_type            numeric(1)           null,
   log_status           numeric(1)           null,
   remark1              varchar(100)         null,
   remark2              varchar(100)         null,
   remark3              varchar(100)         null,
   remark4              varchar(100)         null,
   remark5              varchar(100)         null,
   constraint pk_td_sm_log primary key (log_id)
);

comment on table td_sm_log is
'日志表';

comment on column td_sm_log.log_id is
'日志ID';

comment on column td_sm_log.log_operuser is
'用户帐号';

comment on column td_sm_log.oper_module is
'日志模块ID';

comment on column td_sm_log.log_visitorial is
'操作来源（一般为操作员机器ip）';

comment on column td_sm_log.log_opertime is
'操作时间';

comment on column td_sm_log.log_content is
'操作内容';

comment on column td_sm_log.oper_type is
'操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他）';

comment on column td_sm_log.log_status is
'日志状态（1：成功，0：失败）';

comment on column td_sm_log.remark1 is
'备注1';

comment on column td_sm_log.remark2 is
'备注2';

comment on column td_sm_log.remark3 is
'备注3';

comment on column td_sm_log.remark4 is
'备注4';

comment on column td_sm_log.remark5 is
'备注5';

create table td_sm_logconfig (
   oper_module          varchar(50)          not null,
   log_type             varchar(50)          null,
   log_oper             varchar(200)         null,
   log_operdesc         varchar(500)         null,
   log_enabled          char(1)              not null default '1',
   constraint pk_td_sm_logconfig primary key (oper_module)
);

comment on table td_sm_logconfig is
'日志配置表';

comment on column td_sm_logconfig.oper_module is
'日志模块ID';

comment on column td_sm_logconfig.log_type is
'日志类型（e：实体操作，ws：服务，dao：持久层操作，custom：自定义）';

comment on column td_sm_logconfig.log_oper is
'日志操作ID';

comment on column td_sm_logconfig.log_operdesc is
'日志操作描述';

comment on column td_sm_logconfig.log_enabled is
'记录日志状态，0:不记录日志，1:记录日志';

create table td_sm_log_his (
   log_id               varchar(50)          not null,
   log_operuser         varchar(200)         null,
   oper_module          varchar(50)          null,
   log_visitorial       varchar(200)         null,
   log_opertime         timestamp            null default current_timestamp,
   log_content          text                 null,
   oper_type            numeric(1)           null,
   log_status           numeric(1)           null,
   remark1              varchar(100)         null,
   remark2              varchar(100)         null,
   remark3              varchar(100)         null,
   remark4              varchar(100)         null,
   remark5              varchar(100)         null,
   constraint pk_td_sm_log_his primary key (log_id)
);

comment on table td_sm_log_his is
'历史日志表';

comment on column td_sm_log_his.log_id is
'日志ID';

comment on column td_sm_log_his.log_operuser is
'用户帐号';

comment on column td_sm_log_his.oper_module is
'日志模块ID';

comment on column td_sm_log_his.log_visitorial is
'操作来源（一般为操作员机器ip）';

comment on column td_sm_log_his.log_opertime is
'操作时间';

comment on column td_sm_log_his.log_content is
'操作内容';

comment on column td_sm_log_his.oper_type is
'操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他）';

comment on column td_sm_log_his.log_status is
'日志状态（1：成功，0：失败）';

comment on column td_sm_log_his.remark1 is
'备注1';

comment on column td_sm_log_his.remark2 is
'备注2';

comment on column td_sm_log_his.remark3 is
'备注3';

comment on column td_sm_log_his.remark4 is
'备注4';

comment on column td_sm_log_his.remark5 is
'备注5';

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
   creatingtime         timestamp            null default current_timestamp,
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
   constraint pk_td_sm_organization primary key (org_id)
);

comment on table td_sm_organization is
'机构信息表';

comment on column td_sm_organization.org_id is
'机构ID';

comment on column td_sm_organization.org_sn is
'机构排序ID';

comment on column td_sm_organization.org_name is
'机构名称';

comment on column td_sm_organization.org_showname is
'机构显示名称';

comment on column td_sm_organization.parent_id is
'父机构ID';

comment on column td_sm_organization.path is
'路径';

comment on column td_sm_organization.layer is
'层（阶次）';

comment on column td_sm_organization.children is
'子机构';

comment on column td_sm_organization.code is
'机构代号';

comment on column td_sm_organization.jp is
'简拼';

comment on column td_sm_organization.qp is
'全拼';

comment on column td_sm_organization.creatingtime is
'创建时间';

comment on column td_sm_organization.creator is
'创建人';

comment on column td_sm_organization.orgnumber is
'机构编号';

comment on column td_sm_organization.orgdesc is
'机构描述';

comment on column td_sm_organization.chargeorgid is
'主管机构';

comment on column td_sm_organization.org_level is
'机构行政级别，1：省级，2：市州级，3：县区级，4：科所级';

comment on column td_sm_organization.org_xzqm is
'行政区码';

comment on column td_sm_organization.isdirectlyparty is
'是否直属局 0-不是，缺省值 1-是';

comment on column td_sm_organization.isforeignparty is
'是否涉外局 0-是，缺省值 1-不是';

comment on column td_sm_organization.isjichaparty is
'是否稽查局 0-不是，缺省值 1-是';

comment on column td_sm_organization.isdirectguanhu is
'是否直接管户单位 0-不是，缺省值 1-是';

comment on column td_sm_organization.remark1 is
'备用字段1';

comment on column td_sm_organization.remark2 is
'备用字段2';

comment on column td_sm_organization.remark3 is
'备用字段3';

comment on column td_sm_organization.remark4 is
'备用字段4';

comment on column td_sm_organization.remark5 is
'备用字段5';

comment on column td_sm_organization.remark6 is
'备用字段6';

comment on column td_sm_organization.remark7 is
'备用字段7';

comment on column td_sm_organization.remark8 is
'备用字段8';

comment on column td_sm_organization.remark9 is
'备用字段9';

comment on column td_sm_organization.remark10 is
'备用字段10';

create table td_sm_orgjob (
   org_id               varchar(50)          not null,
   job_id               varchar(50)          not null,
   job_sn               numeric              null,
   constraint pk_td_sm_orgjob primary key (org_id, job_id)
);

comment on table td_sm_orgjob is
'机构岗位关系表';

comment on column td_sm_orgjob.org_id is
'机构ID';

comment on column td_sm_orgjob.job_id is
'岗位ID';

comment on column td_sm_orgjob.job_sn is
'机构岗位序号';

create  index td_sm_orgjob_fk on td_sm_orgjob (
org_id
);

create  index td_sm_orgjob_fk2 on td_sm_orgjob (
job_id
);

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
   constraint pk_td_sm_role primary key (role_id)
);

comment on table td_sm_role is
'角色信息表';

comment on column td_sm_role.role_id is
'角色ID';

comment on column td_sm_role.role_name is
'角色名称';

comment on column td_sm_role.role_type is
'角色类型';

comment on column td_sm_role.role_desc is
'角色描述';

comment on column td_sm_role.role_usage is
'是否使用 1:使用,0:不使用';

comment on column td_sm_role.owner_id is
'创建人id，默认为系统管理员';

comment on column td_sm_role.remark1 is
'备用字段1';

comment on column td_sm_role.remark2 is
'备用字段2';

comment on column td_sm_role.remark3 is
'备用字段3';

comment on column td_sm_role.remark4 is
'备用字段4';

comment on column td_sm_role.remark5 is
'备用字段5';

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
   authorization_stime  timestamp            null default current_timestamp,
   authorization_etime  timestamp            null,
   use_counted          numeric              null,
   org_id               varchar(50)          null default '-1',
   constraint pk_td_sm_roleresop primary key (op_id, role_id)
);

comment on table td_sm_roleresop is
'权限授予表';

comment on column td_sm_roleresop.op_id is
'操作ID';

comment on column td_sm_roleresop.res_id is
'资源ID';

comment on column td_sm_roleresop.role_id is
'角色ID';

comment on column td_sm_roleresop.restype_id is
'资源类型ID';

comment on column td_sm_roleresop.auto is
'标识资源维护方式，0自动维护，1手动维护';

comment on column td_sm_roleresop.res_name is
'资源名称';

comment on column td_sm_roleresop.types is
'资源分类（是角色资源还是用户资源）';

comment on column td_sm_roleresop.authorization_type is
'授权的方式:0:永久授权,1:计数授权,2:时效授权';

comment on column td_sm_roleresop.use_count is
'授权可使用次数';

comment on column td_sm_roleresop.authorization_stime is
'授权使用开始时间';

comment on column td_sm_roleresop.authorization_etime is
'授权使用结束时间';

comment on column td_sm_roleresop.use_counted is
'权限已使用次数';

comment on column td_sm_roleresop.org_id is
'机构ID';

create  index td_sm_roleresop_fk on td_sm_roleresop (
op_id
);

create  index td_sm_roleresop_fk2 on td_sm_roleresop (
res_id
);

create  index td_sm_roleresop_fk3 on td_sm_roleresop (
role_id
);

create table td_sm_roletype (
   type_id              varchar(50)          not null,
   type_name            varchar(100)         null,
   type_desc            varchar(500)         null,
   creator_user_id      varchar(50)          null default '1',
   creator_org_id       varchar(50)          null default '-1',
   constraint role_type_pk primary key (type_id)
);

comment on table td_sm_roletype is
'角色类型信息表';

comment on column td_sm_roletype.type_id is
'角色类型ID';

comment on column td_sm_roletype.type_name is
'角色类型名称';

comment on column td_sm_roletype.type_desc is
'角色类型描述';

comment on column td_sm_roletype.creator_user_id is
'创建人ID';

comment on column td_sm_roletype.creator_org_id is
'创建人机构ID';

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
   user_birthday        timestamp            null,
   user_email           varchar(100)         null,
   user_address         varchar(200)         null,
   user_postalcode      varchar(10)          null,
   user_idcard          varchar(50)          null,
   user_isvalid         numeric              null,
   user_regdate         timestamp            null default current_timestamp,
   user_logincount      numeric              null,
   user_type            varchar(100)         null,
   past_time            timestamp            null,
   dredge_time          varchar(50)          null,
   lastlogin_date       timestamp            null,
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
   constraint pk_td_sm_user primary key (user_id)
);

comment on table td_sm_user is
'用户信息表';

comment on column td_sm_user.user_id is
'用户ID';

comment on column td_sm_user.user_name is
'用户帐号';

comment on column td_sm_user.user_password is
'用户密码';

comment on column td_sm_user.user_realname is
'用户实名';

comment on column td_sm_user.user_pinyin is
'拼音';

comment on column td_sm_user.user_sex is
'性别';

comment on column td_sm_user.user_hometel is
'家庭电话';

comment on column td_sm_user.user_worktel is
'公司电话';

comment on column td_sm_user.user_workaddress is
'公司地址';

comment on column td_sm_user.user_mobiletel1 is
'手机1';

comment on column td_sm_user.user_mobiletel2 is
'手机2';

comment on column td_sm_user.user_fax is
'传真';

comment on column td_sm_user.user_oicq is
'OICQ';

comment on column td_sm_user.user_birthday is
'生日';

comment on column td_sm_user.user_email is
'邮箱';

comment on column td_sm_user.user_address is
'住址';

comment on column td_sm_user.user_postalcode is
'邮编';

comment on column td_sm_user.user_idcard is
'身份证';

comment on column td_sm_user.user_isvalid is
'是否使用';

comment on column td_sm_user.user_regdate is
'注册日期';

comment on column td_sm_user.user_logincount is
'登陆次数';

comment on column td_sm_user.user_type is
'用户类型';

comment on column td_sm_user.past_time is
'过期时间';

comment on column td_sm_user.dredge_time is
'开通时间';

comment on column td_sm_user.lastlogin_date is
'用户最后登陆时间';

comment on column td_sm_user.worklength is
'工作年限';

comment on column td_sm_user.politics is
'政治面貌';

comment on column td_sm_user.login_ip is
'登录IP';

comment on column td_sm_user.cert_sn is
'证书key的唯一标识';

comment on column td_sm_user.user_sn is
'用户排序号';

comment on column td_sm_user.remark1 is
'备用字段1';

comment on column td_sm_user.remark2 is
'备用字段2';

comment on column td_sm_user.remark3 is
'备用字段3';

comment on column td_sm_user.remark4 is
'备用字段4';

comment on column td_sm_user.remark5 is
'备用字段5';

comment on column td_sm_user.remark6 is
'备用字段6';

comment on column td_sm_user.remark7 is
'备用字段7';

comment on column td_sm_user.remark8 is
'备用字段8';

comment on column td_sm_user.remark9 is
'备用字段9';

comment on column td_sm_user.remark10 is
'备用字段10';

create unique index in_td_sm_user_uname on td_sm_user (
user_name
);

create table td_sm_userinstance (
   id                   varchar(50)          not null,
   user_id              varchar(50)          not null,
   scope_id             varchar(50)          null,
   scope_type           char(1)              not null default '0',
   is_enabled           char(1)              not null default '1',
   constraint pk_td_sm_userinstance primary key (id)
);

comment on table td_sm_userinstance is
'用户实例表';

comment on column td_sm_userinstance.id is
'用户实例ID';

comment on column td_sm_userinstance.user_id is
'用户ID';

comment on column td_sm_userinstance.scope_id is
'活动范围ID';

comment on column td_sm_userinstance.scope_type is
'活动范围类型（0：全局，1：机构，2：岗位）';

comment on column td_sm_userinstance.is_enabled is
'是否有效（0：否，1：是）';

create table td_sm_userinstancegroup (
   userinstance_id      varchar(50)          not null,
   group_id             varchar(50)          not null,
   sn                   numeric              null default 9999
);

comment on table td_sm_userinstancegroup is
'用户实例与用户组关系表';

comment on column td_sm_userinstancegroup.userinstance_id is
'用户实例ID';

comment on column td_sm_userinstancegroup.group_id is
'用户组ID';

comment on column td_sm_userinstancegroup.sn is
'用户排序号';

create table td_sm_userinstancerole (
   userinstance_id      varchar(50)          not null,
   role_id              varchar(50)          not null
);

comment on table td_sm_userinstancerole is
'用户实例与角色关系表';

comment on column td_sm_userinstancerole.userinstance_id is
'用户实例ID';

comment on column td_sm_userinstancerole.role_id is
'角色ID';

create table td_sm_userjoborg_history (
   user_id              varchar(50)          not null,
   job_id               varchar(50)          not null,
   job_name             varchar(100)         not null,
   org_id               varchar(50)          not null,
   org_name             varchar(100)         not null,
   job_starttime        timestamp            null,
   job_quashtime        timestamp            null,
   job_fettle           varchar(50)          null
);

comment on table td_sm_userjoborg_history is
'用户历史任职岗位表';

comment on column td_sm_userjoborg_history.user_id is
'用户ID';

comment on column td_sm_userjoborg_history.job_id is
'岗位ID';

comment on column td_sm_userjoborg_history.job_name is
'岗位名称';

comment on column td_sm_userjoborg_history.org_id is
'机构ID';

comment on column td_sm_userjoborg_history.org_name is
'机构名称';

comment on column td_sm_userjoborg_history.job_starttime is
'开始授予岗位时间';

comment on column td_sm_userjoborg_history.job_quashtime is
'结束授予岗位时间';

comment on column td_sm_userjoborg_history.job_fettle is
'授予者用户ID';

create table td_sm_userpreferences (
   user_id              varchar(50)          not null,
   info_name            varchar(500)         not null,
   info_content         varchar(2000)        null,
   info_desc            varchar(500)         null,
   info_lastupdate      timestamp            null default current_timestamp
);

comment on table td_sm_userpreferences is
'用户偏好设置信息表';

comment on column td_sm_userpreferences.user_id is
'用户ID';

comment on column td_sm_userpreferences.info_name is
'信息名称';

comment on column td_sm_userpreferences.info_content is
'信息内容';

comment on column td_sm_userpreferences.info_desc is
'信息描述';

comment on column td_sm_userpreferences.info_lastupdate is
'最后修改时间';

alter table tb_sm_custres
   add constraint fk_tb_sm_staticoper1 foreign key (privilege_id)
      references tb_sm_privilege (id)
      on delete restrict on update restrict;

alter table tb_sm_decision_entitlement
   add constraint fk_tb_sm_decision_entitlement1 foreign key (privilege_id)
      references tb_sm_privilege (id)
      on delete restrict on update restrict;

alter table tb_sm_decision_entitlement
   add constraint fk_tb_sm_decision_entitlement2 foreign key (usercategory_id)
      references tb_sm_usercategory (id)
      on delete restrict on update restrict;

alter table tb_sm_decision_entitlement
   add constraint fk_tb_sm_decision_entitlement3 foreign key (businessdata_id)
      references tb_sm_businessdata (id)
      on delete restrict on update restrict;

alter table tb_sm_menu
   add constraint fk_tb_sm_moudle1 foreign key (menu_id)
      references tb_sm_privilege (id)
      on delete restrict on update restrict;

alter table tb_sm_query_entitlement
   add constraint fk_tb_sm_query_entitlement2 foreign key (query_id)
      references tb_sm_query (id)
      on delete restrict on update restrict;

alter table tb_sm_query_entitlement
   add constraint fk_tb_sm_query_entitlement3 foreign key (usercategory_id)
      references tb_sm_usercategory (id)
      on delete restrict on update restrict;

alter table tb_sm_query_entitlement
   add constraint fk_tb_sm_query_entitlement1 foreign key (privilege_id)
      references tb_sm_privilege (id)
      on delete restrict on update restrict;

alter table tb_system_info
   add constraint fk_tb_system_info foreign key (info_type)
      references tb_system_type (id)
      on delete restrict on update restrict;

alter table td_df_data_obj_column
   add constraint td_df_data_obj_column_fk foreign key (df_object_id)
      references td_df_data_obj (df_object_id)
      on delete restrict on update restrict;

alter table td_sm_dictdata
   add constraint fk_td_sm_dictdata foreign key (dicttype_id)
      references td_sm_dicttype (dicttype_id)
      on delete restrict on update restrict;

alter table td_sm_grouporg
   add constraint fk_td_sm_grouporg1 foreign key (group_id)
      references td_sm_group (group_id)
      on delete restrict on update restrict;

alter table td_sm_grouporg
   add constraint fk_td_sm_grouporg2 foreign key (org_id)
      references td_sm_organization (org_id)
      on delete restrict on update restrict;

alter table td_sm_grouporgjob
   add constraint fk_td_sm_grouporgjob1 foreign key (group_id)
      references td_sm_group (group_id)
      on delete restrict on update restrict;

alter table td_sm_grouporgjob
   add constraint fk_td_sm_grouporgjob2 foreign key (job_id)
      references td_sm_job (job_id)
      on delete restrict on update restrict;

alter table td_sm_grouprole
   add constraint fk_td_sm_grouprole1 foreign key (group_id)
      references td_sm_group (group_id)
      on delete restrict on update restrict;

alter table td_sm_grouprole
   add constraint fk_td_sm_grouprole2 foreign key (role_id)
      references td_sm_role (role_id)
      on delete restrict on update restrict;

alter table td_sm_instance_job
   add constraint fk_td_sm_instance_job foreign key (userinstance_id)
      references td_sm_userinstance (id)
      on delete restrict on update restrict;

alter table td_sm_instance_org
   add constraint fk_td_sm_instance_org1 foreign key (userinstance_id)
      references td_sm_userinstance (id)
      on delete restrict on update restrict;

alter table td_sm_orgjob
   add constraint fk_td_sm_orgjob1 foreign key (org_id)
      references td_sm_organization (org_id)
      on delete restrict on update restrict;

alter table td_sm_orgjob
   add constraint fk_td_sm_orgjob2 foreign key (job_id)
      references td_sm_job (job_id)
      on delete restrict on update restrict;

alter table td_sm_role
   add constraint fk_td_sm_role foreign key (role_type)
      references td_sm_roletype (type_id)
      on delete restrict on update restrict;

alter table td_sm_userinstance
   add constraint fk_td_sm_userinstance foreign key (user_id)
      references td_sm_user (user_id)
      on delete restrict on update restrict;

alter table td_sm_userinstancegroup
   add constraint fk_td_sm_userinstancegroup1 foreign key (userinstance_id)
      references td_sm_userinstance (id)
      on delete restrict on update restrict;

alter table td_sm_userinstancegroup
   add constraint fk_td_sm_userinstancegroup2 foreign key (group_id)
      references td_sm_group (group_id)
      on delete restrict on update restrict;

alter table td_sm_userinstancerole
   add constraint fk_td_sm_userinstancerole1 foreign key (userinstance_id)
      references td_sm_userinstance (id)
      on delete restrict on update restrict;

alter table td_sm_userinstancerole
   add constraint fk_td_sm_userinstancerole2 foreign key (role_id)
      references td_sm_role (role_id)
      on delete restrict on update restrict;

-- 存储过程脚本_postgresql -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
-- 日志备份存储过程
--start
create or replace function log_backup(in backup_date numeric)
returns numeric
--日志备份，backup_date 表示当前系统时间多少天已前的数据需要备份
 as
 $body$
begin
  --备份日志主表  
	insert into td_sm_log_his
    select * from td_sm_log where to_date(to_char(now(), 'yyyy-mm-dd'), 'yyyy-mm-dd')-to_date(to_char(log_opertime, 'yyyy-mm-dd'), 'yyyy-mm-dd')>=backup_date;
	--删除日志主表
	delete from td_sm_log where to_date(to_char(now(), 'yyyy-mm-dd'), 'yyyy-mm-dd')-to_date(to_char(log_opertime, 'yyyy-mm-dd'), 'yyyy-mm-dd')>=backup_date;
	return backup_date;
end;
 $body$
 language 'plpgsql' volatile;
--end;


-- 初始化脚本_postgresql -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
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
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('126', 'jobMgt', '岗位权限管理', '12', '4', 'jobMgt', '1', current_timestamp, 3);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('127', 'resMgt', '资源维护', '12', '4', 'resMgt', '1', current_timestamp, 4);
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
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('126', '1', '#/f/jobMain', 'fa-anchor', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('127', '1', '#/f/resMain', 'fa-tree', '0');
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
insert into td_sm_role (role_id, role_name, role_type, role_desc, role_usage, owner_id, remark1, remark2, remark3, remark4, remark5) values ('1', 'administrator', '1', '超级管理员角色，此角色不要修改，超级管理员admin自动拥有本角色，只有admin可以将本角色授予其他用户，本角色也不需要授予权限，默认拥有系统中的所有权限', '1', '1', null, null, null, null, null);
insert into td_sm_role (role_id, role_name, role_type, role_desc, role_usage, owner_id, remark1, remark2, remark3, remark4, remark5) values ('2', 'roleofeveryone', '1', '每个用户都缺省拥有的角色，此角色不能被修改，也不能被删除', '1', '1', null, null, null, null, null);
insert into td_sm_role (role_id, role_name, role_type, role_desc, role_usage, owner_id, remark1, remark2, remark3, remark4, remark5) values ('3', 'orgmanager', '1', '部门管理员角色,此角色不能被修改，也不能被删除，所有部门管理员自动拥有本角色', '1', '1', null, null, null, null, null);

-- 授予用户admin系统管理员角色
insert into td_sm_userinstance (id, user_id, scope_id, scope_type, is_enabled) values ('1', '1', null, '0', '1');
insert into td_sm_userinstancerole (userinstance_id, role_id) values ('1', '1');

-- 岗位
insert into td_sm_job (job_id, job_name, job_desc, job_function, job_amount, job_number, job_condition, job_rank, owner_id, job_scope) values ('1', '管理员', '管理员', null, null, null, null, null, '1', '0');
insert into td_sm_job (job_id, job_name, job_desc, job_function, job_amount, job_number, job_condition, job_rank, owner_id, job_scope) values ('2', '普通用户', '普通用户', null, null, null, null, null, '1', '0');
-- 用户组
insert into td_sm_group (group_id, group_name, group_desc, parent_id, owner_id, remark1, remark2, remark3, remark4, remark5, type) values ('1', '1', '通用岗位【管理员】对应的匿名用户组', '0', '1', null, null, null, null, null, '2');
insert into td_sm_group (group_id, group_name, group_desc, parent_id, owner_id, remark1, remark2, remark3, remark4, remark5, type) values ('2', '2', '通用岗位【普通用户】对应的匿名用户组', '0', '1', null, null, null, null, null, '2');
-- 岗位用户组关系
insert into td_sm_grouporgjob (group_id, job_id) values ('1', '1');
insert into td_sm_grouporgjob (group_id, job_id) values ('2', '2');
-- 用户组角色关系
insert into td_sm_grouprole (group_id, role_id, resop_origin_userid) values ('1', '1', '1');
insert into td_sm_grouprole (group_id, role_id, resop_origin_userid) values ('2', '2', '1');
