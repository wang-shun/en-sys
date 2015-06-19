-- 系统管理V4.1.5标准版建库及初始化脚本_mysql
-- 建库脚本_mysql -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
drop table if exists tb_application_info;

drop table if exists tb_sm_businessdata;

drop table if exists tb_sm_custres;

drop table if exists tb_sm_decision_entitlement;

drop table if exists tb_sm_menu;

drop table if exists tb_sm_privilege;

drop table if exists tb_sm_query;

drop table if exists tb_sm_query_entitlement;

drop table if exists tb_sm_usercategory;

drop table if exists tb_system_info;

drop table if exists tb_system_type;

drop table if exists td_df_data_obj;

drop table if exists td_df_data_obj_column;

drop table if exists td_sm_dictdata;

drop table if exists td_sm_dicttype;

drop table if exists td_sm_group;

drop table if exists td_sm_grouporg;

drop table if exists td_sm_grouporgjob;

drop table if exists td_sm_grouprole;

drop table if exists td_sm_instance_job;

drop table if exists td_sm_instance_org;

drop table if exists td_sm_job;

drop table if exists td_sm_log;

drop table if exists td_sm_logconfig;

drop table if exists td_sm_log_his;

drop table if exists td_sm_organization;

drop table if exists td_sm_orgjob;

drop table if exists td_sm_role;

drop index td_sm_roleresop_fk3 on td_sm_roleresop;

drop index td_sm_roleresop_fk2 on td_sm_roleresop;

drop index td_sm_roleresop_fk on td_sm_roleresop;

drop table if exists td_sm_roleresop;

drop table if exists td_sm_roletype;

drop index in_td_sm_user_uname on td_sm_user;

drop table if exists td_sm_user;

drop table if exists td_sm_userinstance;

drop table if exists td_sm_userinstancegroup;

drop table if exists td_sm_userinstancerole;

drop table if exists td_sm_userjoborg_history;

drop table if exists td_sm_userpreferences;

create table tb_application_info
(
   app_id               varchar(20) not null comment '应用ID',
   app_name             varchar(64) not null comment '应用名称',
   remark               varchar(512) comment '描述',
   dbname               varchar(64) comment '应用数据源名称',
   dbuser               varchar(64) comment '数据库用户名',
   dbpwd                varchar(64) comment '数据库用户密码',
   deftablespace        varchar(128) comment '缺省表空间',
   temptablespace       varchar(128) comment '临时表空间',
   workfolder           varchar(256) comment '文件夹名称',
   accpath              varchar(512) comment '访问路径',
   enablestatus         decimal(10) default 1 comment '应用使能状态，0为不可用，1为可用',
   functionlist         varchar(128) comment '功能开关:从右往左第一位代表短信，第二位代表内部邮件，第三位代表RTX',
   ismsgcenter          decimal(10) default 1 comment '是否显示消息中心:0代表不显示，1代表显示，缺省为显示',
   appimgpath           varchar(128) comment '应用图标路径',
   primary key (app_id)
);

alter table tb_application_info comment '应用信息表';

create table tb_sm_businessdata
(
   id                   varchar(50) not null comment '序号',
   name                 varchar(50) not null comment '名称',
   description          varchar(200) comment '备注',
   parent_id            varchar(50) not null default '0' comment '父ID',
   isleaf               char(1) default '1' comment '是否子节点(0:否1:是)',
   rule                 text comment '分类规则',
   primary key (id)
);

alter table tb_sm_businessdata comment '业务数据分类表';

create table tb_sm_custres
(
   privilege_id         varchar(50) not null comment '权限ID',
   restype_id           varchar(50) not null comment '资源类型ID',
   restype_name         varchar(200) not null comment '资源类型名称',
   res_id               varchar(50) not null comment '资源ID',
   res_name             varchar(200) not null comment '资源名称',
   grintlevel           char(1) not null default '1' comment '授予级别（-1：管理员，1：所有用户）',
   primary key (privilege_id)
);

alter table tb_sm_custres comment '资源信息表';

create table tb_sm_decision_entitlement
(
   id                   varchar(50) not null comment '序号',
   privilege_id         varchar(50) not null comment '权限ID',
   usercategory_id      varchar(50) not null comment '用户分类ID',
   businessdata_id      varchar(50) not null comment '业务数据分类ID',
   effect               char(1) not null default '1' comment '决策（0：false，1：true）',
   denyreason           varchar(2000) comment '拒绝理由',
   priority             decimal(10) not null default 0 comment '优先级别',
   primary key (id)
);

alter table tb_sm_decision_entitlement comment '决策权限策略表';

create table tb_sm_menu
(
   menu_id              varchar(50) not null comment '菜单ID',
   is_enabled           char(1) not null default '1' comment '是否显示（0：不显示，1：显示）',
   href                 varchar(500) comment '链接路径',
   icon                 varchar(500) comment '图标',
   display_mode         char(1) not null default '0' comment '打开方式（0：div中，1：iframe中，2：新页面中）',
   menu_ext             text comment '菜单扩展字段',
   primary key (menu_id)
);

alter table tb_sm_menu comment '模块信息表';

create table tb_sm_privilege
(
   id                   varchar(50) not null comment '序号',
   code                 varchar(500) not null comment '权限编码',
   name                 varchar(500) comment '权限名称',
   parent_id            varchar(500) not null default '0' comment '父ID',
   type                 char(1) not null comment '类型（1:服务(service),2:表单(form),3:表单元素(dom),4:菜单(menu),5:实体(entity),6:实体操作(entity_op),9:自定义(custom)）',
   perm_expr            varchar(2000) comment '权限字符串',
   creator              varchar(50) default '1' comment '创建者',
   creator_time         timestamp default current_timestamp comment '创建时间',
   sn                   decimal(10) comment '排序号',
   source               char(1) not null default '0' comment '权限来源（0：自定义，1：IDE）',
   primary key (id)
);

alter table tb_sm_privilege comment '权限信息表';

create table tb_sm_query
(
   id                   varchar(50) not null comment '序号',
   name                 varchar(50) not null comment '名称',
   description          varchar(200) comment '备注',
   parent_id            varchar(50) not null default '0' comment '父ID',
   isleaf               char(1) default '1' comment '是否子节点(0:否1:是)',
   rule                 text comment '查询规则',
   primary key (id)
);

alter table tb_sm_query comment '数据查询表';

create table tb_sm_query_entitlement
(
   id                   varchar(50) not null comment '序号',
   privilege_id         varchar(50) not null comment '权限ID',
   query_id             varchar(50) not null comment '数据查询ID',
   usercategory_id      varchar(50) not null comment '用户分类ID',
   priority             decimal(10) not null default 0 comment '优先级别',
   primary key (id)
);

alter table tb_sm_query_entitlement comment '查询权限策略表';

create table tb_sm_usercategory
(
   id                   varchar(50) not null comment '序号',
   name                 varchar(50) not null comment '名称',
   description          varchar(200) comment '备注',
   parent_id            varchar(50) not null default '0' comment '父ID',
   isleaf               char(1) default '1' comment '是否子节点(0:否1:是)',
   rule                 text comment '分类规则',
   primary key (id)
);

alter table tb_sm_usercategory comment '用户类型表';

create table tb_system_info
(
   id                   varchar(50) not null comment '系统信息ID',
   info_name            varchar(500) not null comment '系统信息名称',
   info_content         varchar(2000) comment '系统信息内容',
   info_desc            varchar(500) comment '系统信息描述',
   info_type            varchar(50) not null comment '系统信息类型',
   canremove            varchar(50) not null default '1' comment '是否可以删除字段，0：可删除 1：不可删除',
   remark               varchar(200) comment '备用字段',
   primary key (id)
);

alter table tb_system_info comment '系统设置信息表';

create table tb_system_type
(
   id                   varchar(50) not null comment '系统信息类型ID',
   type_name            varchar(50) not null comment '系统信息类型名称',
   type_desc            varchar(100) comment '系统信息类型描述',
   primary key (id)
);

alter table tb_system_type comment '系统类别表';

create table td_df_data_obj
(
   df_object_id         varchar(50) not null comment '数据对象ID',
   df_ds_id             varchar(100) comment '数据源ID',
   df_object_name       varchar(255) comment '数据对象名称',
   df_table_name        varchar(255) comment '数据对象表名',
   df_provide_type      varchar(20) comment '信息提供类型:row按记录提供，col按字段提供',
   df_formcolnum        decimal(10) default 4 comment '表单列数',
   df_pkauto_type       varchar(50) comment '主键增长方式:tableinfo，native，none',
   df_createmenu_flag   char(1) comment '是否创建菜单,0不创建，1创建',
   df_parentmenu_id     varchar(100) comment '父菜单ID',
   df_menu_name         varchar(100) comment '菜单名',
   df_status            char(1) default '1' comment '数据对象状态,1可用,0不可用',
   df_regfrompoolman    char(1) comment '数据对象是否来源Poolman数据源,1是,0否',
   ui_groupname         varchar(2000),
   primary key (df_object_id)
);

alter table td_df_data_obj comment '动态表单数据对象表';

create table td_df_data_obj_column
(
   df_column_id         varchar(50) not null comment '字段ID',
   df_column_code       varchar(100) not null comment '字段名',
   df_column_name       varchar(100) not null comment '字段中文名',
   df_object_id         varchar(50) not null comment '数据对象ID',
   df_column_type       varchar(63) not null comment '字段类型',
   df_length            decimal(10) comment '字段长度',
   df_precision         decimal(10) comment '字段精度',
   df_logic_type        varchar(63) comment '逻辑数据类型',
   df_sort_sn           decimal(10) comment '字段排序号',
   df_maxlength         decimal(10) comment '界面最大输入长度',
   df_orderby           varchar(10) comment '默认查询的排序字段，可选值：空白（不排序）,asc,desc',
   df_uitype            varchar(20) comment '字段对应表单组件类型',
   df_optiondata_type   varchar(10) comment '下拉组件的选项数据来源类型，可选值：固定，字典',
   df_optiondata        varchar(1000) comment '下拉组件的选项数据。如果是固定则用格式存放内容”key1,value1;key2,value2”；如果是字典则保存sql语句。',
   df_optiondata_dsid   varchar(50) comment '下拉组件的选项数据源Id',
   df_initdata          varchar(100) comment '初始值。可用变量：#{currentUserName}当前用户名，#{currentUserName}当前用户，#{now}当前时间',
   df_uigroup           varchar(100) comment '字段对应表单组件的所在分组',
   df_uirownum          decimal(10) default 1 comment '组件在表单中所占行数，默认为1',
   df_uicolnum          decimal(10) default 2 comment '组件在表单中所占列数，默认为2(标题和组件各一列)。',
   df_regex             varchar(100) comment '表单字段值验证正则表达式',
   df_regex_message     varchar(200) comment '表单字段值验证正则表达式错误信息',
   df_pk_flag           char(1) comment '是否为主键',
   df_required_flag     char(1) comment '表单验证是否非空',
   df_search_flag       char(1) comment '是否为列表查询字段',
   df_listshow_flag     char(1) comment '是否可在列表中默认显示',
   df_status            decimal(10) default 1 comment '该字段状态，0不可用,1可用,-1对应表字段不存在',
   df_creater_field_flag char(1) comment '是否为添加人字段',
   df_creater_org_field_flag char(1) comment '是否为添加人机构字段',
   primary key (df_column_id)
);

alter table td_df_data_obj_column comment '动态表单数据对象字段表';

create table td_sm_dictdata
(
   dictdata_id          varchar(50) not null comment '字典ID',
   dicttype_id          varchar(50) comment '字典类型ID',
   dictdata_name        varchar(100) comment '字典真实值',
   dictdata_value       varchar(100) comment '字典显示值',
   dictdata_order       decimal(10) default 0 comment '字典排序号',
   dictdata_isdefault   char(1) default '0' comment '是否默认值（0：否，1：是）',
   primary key (dictdata_id)
);

alter table td_sm_dictdata comment '字典数据表';

create table td_sm_dicttype
(
   dicttype_id          varchar(50) not null comment '字典类型ID',
   dicttype_name        varchar(100) not null comment '字典类型名称',
   dicttype_desc        varchar(100) comment '字典类型描述',
   primary key (dicttype_id)
);

alter table td_sm_dicttype comment '字典类型表';

create table td_sm_group
(
   group_id             varchar(50) not null comment '用户组ID',
   group_name           varchar(100) not null comment '用户组名称',
   group_desc           varchar(100) comment '用户组描述',
   parent_id            varchar(50) not null default '0' comment '父ID',
   owner_id             varchar(50) default '1' comment '用户组创建人ID',
   remark1              varchar(100) comment '备用字段1',
   remark2              varchar(100) comment '备用字段2',
   remark3              varchar(100) comment '备用字段3',
   remark4              varchar(100) comment '备用字段4',
   remark5              varchar(100) comment '备用字段5',
   type                 char(1) default '0' comment '类型（0：自定义用户组，1：机构，2：机构岗位）',
   primary key (group_id)
);

alter table td_sm_group comment '用户组信息表';

create table td_sm_grouporg
(
   group_id             varchar(50) comment '用户组ID',
   org_id               varchar(50) comment '机构ID'
);

alter table td_sm_grouporg comment '用户组与机构关系表';

create table td_sm_grouporgjob
(
   group_id             varchar(50) comment '用户组ID',
   job_id               varchar(50) comment '岗位ID'
);

alter table td_sm_grouporgjob comment '用户组与机构岗位关系';

create table td_sm_grouprole
(
   group_id             varchar(50) not null comment '用户组ID',
   role_id              varchar(50) not null comment '角色ID',
   resop_origin_userid  varchar(50) default '1' comment '授予角色的用户',
   primary key (group_id, role_id)
);

alter table td_sm_grouprole comment '用户组与角色关系表';

create table td_sm_instance_job
(
   userinstance_id      varchar(50) not null comment '用户实例ID',
   is_main              char(1) not null default '0' comment '是否主岗位（0：否，1：是）',
   sn                   decimal(10) default 9999 comment '用户排序号'
);

alter table td_sm_instance_job comment '岗位实例表';

create table td_sm_instance_org
(
   userinstance_id      varchar(50) not null comment '用户实例ID',
   is_main              char(1) not null default '0' comment '是否主机构（0：否，1：是）',
   sn                   decimal(10) default 9999 comment '用户排序号'
);

alter table td_sm_instance_org comment '机构实例表';

create table td_sm_job
(
   job_id               varchar(50) not null comment '岗位ID',
   job_name             varchar(100) comment '岗位名称',
   job_desc             varchar(200) comment '岗位描述',
   job_scope            char(1) not null default '1' comment '岗位范围(0:通用岗位,1:机构特有)',
   job_function         varchar(200) comment '岗位职责',
   job_amount           varchar(100) comment '岗位编制人数',
   job_number           varchar(100) comment '岗位编号',
   job_condition        varchar(200) comment '任职条件',
   job_rank             varchar(100) comment '岗位级别',
   owner_id             varchar(50) default '1' comment '岗位授予人ID',
   primary key (job_id)
);

alter table td_sm_job comment '岗位信息表';

create table td_sm_log
(
   log_id               varchar(50) not null comment '日志ID',
   log_operuser         varchar(200) comment '用户帐号',
   oper_module          varchar(50) comment '日志模块ID',
   log_visitorial       varchar(200) comment '操作来源（一般为操作员机器ip）',
   log_opertime         timestamp default current_timestamp comment '操作时间',
   log_content          text comment '操作内容',
   oper_type            decimal(1) comment '操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他）',
   log_status           decimal(1) comment '日志状态（1：成功，0：失败）',
   remark1              varchar(100) comment '备注1',
   remark2              varchar(100) comment '备注2',
   remark3              varchar(100) comment '备注3',
   remark4              varchar(100) comment '备注4',
   remark5              varchar(100) comment '备注5',
   primary key (log_id)
);

alter table td_sm_log comment '日志表';

create table td_sm_logconfig
(
   oper_module          varchar(50) not null comment '日志模块ID',
   log_type             varchar(50) comment '日志类型（e：实体操作，ws：服务，dao：持久层操作，custom：自定义）',
   log_oper             varchar(200) comment '日志操作ID',
   log_operdesc         varchar(500) comment '日志操作描述',
   log_enabled          char(1) not null default '1' comment '记录日志状态，0:不记录日志，1:记录日志',
   primary key (oper_module)
);

alter table td_sm_logconfig comment '日志配置表';

create table td_sm_log_his
(
   log_id               varchar(50) not null comment '日志ID',
   log_operuser         varchar(200) comment '用户账号',
   oper_module          varchar(200) comment '日志模块ID',
   log_visitorial       varchar(200) comment '操作来源（一般为操作员机器ip）',
   log_opertime         timestamp default current_timestamp comment '操作时间',
   log_content          text comment '操作内容',
   oper_type            decimal(1) comment '操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他）',
   log_status           decimal(1) comment '日志状态（1：成功，0：失败）',
   remark1              varchar(100) comment '备注1',
   remark2              varchar(100) comment '备注2',
   remark3              varchar(100) comment '备注3',
   remark4              varchar(100) comment '备注4',
   remark5              varchar(100) comment '备注5',
   primary key (log_id)
);

alter table td_sm_log_his comment '历史日志表';

create table td_sm_organization
(
   org_id               varchar(50) not null comment '机构ID',
   org_sn               decimal(10) default 999 comment '机构排序ID',
   org_name             varchar(40) not null comment '机构名称',
   org_showname         varchar(100) not null comment '机构显示名称',
   parent_id            varchar(100) not null comment '父机构ID',
   path                 varchar(1000) comment '路径',
   layer                varchar(200) comment '层（阶次）',
   children             varchar(1000) comment '子机构',
   code                 varchar(100) comment '机构代号',
   jp                   varchar(40) comment '简拼',
   qp                   varchar(40) comment '全拼',
   creatingtime         timestamp default current_timestamp comment '创建时间',
   creator              varchar(100) default '1' comment '创建人',
   orgnumber            varchar(100) comment '机构编号',
   orgdesc              varchar(300) comment '机构描述',
   chargeorgid          varchar(50) comment '主管机构',
   org_level            varchar(1) default '1' comment '机构行政级别，1：省级，2：市州级，3：县区级，4：科所级',
   org_xzqm             varchar(12) comment '行政区码',
   isdirectlyparty      decimal(1) default 0 comment '是否直属局 0-不是，缺省值 1-是',
   isforeignparty       decimal(1) default 0 comment '是否涉外局 0-是，缺省值 1-不是',
   isjichaparty         decimal(1) default 0 comment '是否稽查局 0-不是，缺省值 1-是',
   isdirectguanhu       decimal(1) default 0 comment '是否直接管户单位 0-不是，缺省值 1-是',
   remark1              varchar(100) comment '备用字段1',
   remark2              varchar(100) comment '备用字段2',
   remark3              varchar(100) comment '备用字段3',
   remark4              varchar(100) comment '备用字段4',
   remark5              varchar(100) comment '备用字段5',
   remark6              varchar(100) comment '备用字段6',
   remark7              varchar(100) comment '备用字段7',
   remark8              varchar(100) comment '备用字段8',
   remark9              varchar(100) comment '备用字段9',
   remark10             varchar(100) comment '备用字段10',
   primary key (org_id)
);

alter table td_sm_organization comment '机构信息表';

create table td_sm_orgjob
(
   org_id               varchar(50) not null comment '机构ID',
   job_id               varchar(50) not null comment '岗位ID',
   job_sn               decimal(10) comment '机构岗位序号',
   primary key (org_id, job_id)
);

alter table td_sm_orgjob comment '机构岗位关系表';

create table td_sm_role
(
   role_id              varchar(50) not null comment '角色ID',
   role_name            varchar(100) not null comment '角色名称',
   role_type            varchar(50) comment '角色类型',
   role_desc            varchar(1024) comment '角色描述',
   role_usage           varchar(1) default '1' comment '是否使用 1:使用,0:不使用',
   owner_id             varchar(50) default '1' comment '创建人id，默认为系统管理员',
   remark1              varchar(100) comment '备用字段1',
   remark2              varchar(100) comment '备用字段2',
   remark3              varchar(100) comment '备用字段3',
   remark4              varchar(100) comment '备用字段4',
   remark5              varchar(100) comment '备用字段5',
   primary key (role_id)
);

alter table td_sm_role comment '角色信息表';

create table td_sm_roleresop
(
   op_id                varchar(50) not null comment '操作ID',
   res_id               varchar(100) comment '资源ID',
   role_id              varchar(50) not null comment '角色ID',
   restype_id           varchar(50) comment '资源类型ID',
   auto                 varchar(50) comment '标识资源维护方式，0自动维护，1手动维护',
   res_name             varchar(500) comment '资源名称',
   types                varchar(50) comment '资源分类（是角色资源还是用户资源）',
   authorization_type   decimal(1) default 0 comment '授权的方式:0:永久授权,1:计数授权,2:时效授权',
   use_count            decimal(10) comment '授权可使用次数',
   authorization_stime  timestamp default current_timestamp comment '授权使用开始时间',
   authorization_etime  timestamp comment '授权使用结束时间',
   use_counted          decimal(10) comment '权限已使用次数',
   org_id               varchar(50) default '-1' comment '机构ID',
   primary key (op_id, role_id)
);

alter table td_sm_roleresop comment '权限授予表';

create index td_sm_roleresop_fk on td_sm_roleresop
(
   op_id
);

create index td_sm_roleresop_fk2 on td_sm_roleresop
(
   res_id
);

create index td_sm_roleresop_fk3 on td_sm_roleresop
(
   role_id
);

create table td_sm_roletype
(
   type_id              varchar(50) not null comment '角色类型ID',
   type_name            varchar(100) comment '角色类型名称',
   type_desc            varchar(500) comment '角色类型描述',
   creator_user_id      varchar(50) default '1' comment '创建人ID',
   creator_org_id       varchar(50) default '-1' comment '创建人机构ID',
   primary key (type_id)
);

alter table td_sm_roletype comment '角色类型信息表';

create table td_sm_user
(
   user_id              varchar(50) not null comment '用户ID',
   user_name            varchar(200) not null comment '用户帐号',
   user_password        varchar(512) not null comment '用户密码',
   user_realname        varchar(100) not null comment '用户实名',
   user_pinyin          varchar(100) comment '拼音',
   user_sex             varchar(100) comment '性别',
   user_hometel         varchar(100) comment '家庭电话',
   user_worktel         varchar(100) comment '公司电话',
   user_workaddress     varchar(100) comment '公司地址',
   user_mobiletel1      varchar(100) comment '手机1',
   user_mobiletel2      varchar(100) comment '手机2',
   user_fax             varchar(100) comment '传真',
   user_oicq            varchar(100) comment 'OICQ',
   user_regdate         timestamp default current_timestamp comment '注册日期',
   user_birthday        timestamp comment '生日',
   user_email           varchar(100) comment '邮箱',
   user_address         varchar(200) comment '住址',
   user_postalcode      varchar(10) comment '邮编',
   user_idcard          varchar(50) comment '身份证',
   user_isvalid         decimal(10) comment '是否使用',
   user_logincount      decimal(10) comment '登陆次数',
   user_type            varchar(100) comment '用户类型',
   past_time            timestamp comment '过期时间',
   dredge_time          varchar(50) comment '开通时间',
   lastlogin_date       timestamp comment '用户最后登陆时间',
   worklength           varchar(50) comment '工作年限',
   politics             varchar(100) comment '政治面貌',
   login_ip             varchar(15) comment '登录IP',
   cert_sn              varchar(50) comment '证书key的唯一标识',
   user_sn              decimal(10) default 999 comment '用户排序号',
   remark1              varchar(100) comment '备用字段1',
   remark2              varchar(100) comment '备用字段2',
   remark3              varchar(100) comment '备用字段3',
   remark4              varchar(100) comment '备用字段4',
   remark5              varchar(100) comment '备用字段5',
   remark6              varchar(100) comment '备用字段6',
   remark7              varchar(100) comment '备用字段7',
   remark8              varchar(100) comment '备用字段8',
   remark9              varchar(100) comment '备用字段9',
   remark10             varchar(100) comment '备用字段10',
   primary key (user_id)
);

alter table td_sm_user comment '用户信息表';

create unique index in_td_sm_user_uname on td_sm_user
(
   user_name
);

create table td_sm_userinstance
(
   id                   varchar(50) not null comment '用户实例ID',
   user_id              varchar(50) not null comment '用户ID',
   scope_id             varchar(50) comment '活动范围ID',
   scope_type           char(1) not null default '0' comment '活动范围类型（0：全局，1：机构，2：岗位）',
   is_enabled           char(1) not null default '1' comment '是否有效（0：否，1：是）',
   primary key (id)
);

alter table td_sm_userinstance comment '用户实例表';

create table td_sm_userinstancegroup
(
   userinstance_id      varchar(50) not null comment '用户实例ID',
   group_id             varchar(50) not null comment '用户组ID',
   sn                   decimal(10) default 9999 comment '用户排序号'
);

alter table td_sm_userinstancegroup comment '用户实例与用户组关系表';

create table td_sm_userinstancerole
(
   userinstance_id      varchar(50) not null comment '用户实例ID',
   role_id              varchar(50) not null comment '角色ID'
);

alter table td_sm_userinstancerole comment '用户实例与角色关系表';

create table td_sm_userjoborg_history
(
   user_id              varchar(50) not null comment '用户ID',
   job_id               varchar(50) not null comment '岗位ID',
   job_name             varchar(100) not null comment '岗位名称',
   org_id               varchar(50) not null comment '机构ID',
   org_name             varchar(100) not null comment '机构名称',
   job_starttime        timestamp comment '开始授予岗位时间',
   job_quashtime        timestamp comment '结束授予岗位时间',
   job_fettle           varchar(50) comment '授予者用户ID'
);

alter table td_sm_userjoborg_history comment '用户历史任职岗位表';

create table td_sm_userpreferences
(
   user_id              varchar(50) not null comment '用户ID',
   info_name            varchar(500) not null comment '信息名称',
   info_content         varchar(2000) comment '信息内容',
   info_desc            varchar(500) comment '信息描述',
   info_lastupdate      timestamp default current_timestamp comment '最后修改时间'
);

alter table td_sm_userpreferences comment '用户偏好设置信息表';

alter table tb_sm_custres add constraint fk_tb_sm_staticoper1 foreign key (privilege_id)
      references tb_sm_privilege (id) on delete restrict on update restrict;

alter table tb_sm_decision_entitlement add constraint fk_tb_sm_decision_entitlement1 foreign key (privilege_id)
      references tb_sm_privilege (id) on delete restrict on update restrict;

alter table tb_sm_decision_entitlement add constraint fk_tb_sm_decision_entitlement2 foreign key (usercategory_id)
      references tb_sm_usercategory (id) on delete restrict on update restrict;

alter table tb_sm_decision_entitlement add constraint fk_tb_sm_decision_entitlement3 foreign key (businessdata_id)
      references tb_sm_businessdata (id) on delete restrict on update restrict;

alter table tb_sm_menu add constraint fk_tb_sm_moudle1 foreign key (menu_id)
      references tb_sm_privilege (id) on delete restrict on update restrict;

alter table tb_sm_query_entitlement add constraint fk_tb_sm_query_entitlement2 foreign key (query_id)
      references tb_sm_query (id) on delete restrict on update restrict;

alter table tb_sm_query_entitlement add constraint fk_tb_sm_query_entitlement3 foreign key (usercategory_id)
      references tb_sm_usercategory (id) on delete restrict on update restrict;

alter table tb_sm_query_entitlement add constraint fk_tb_sm_query_entitlement1 foreign key (privilege_id)
      references tb_sm_privilege (id) on delete restrict on update restrict;

alter table tb_system_info add constraint fk_tb_system_info foreign key (info_type)
      references tb_system_type (id) on delete restrict on update restrict;

alter table td_df_data_obj_column add constraint td_df_data_obj_column_fk foreign key (df_object_id)
      references td_df_data_obj (df_object_id) on delete restrict on update restrict;

alter table td_sm_dictdata add constraint fk_td_sm_dictdata foreign key (dicttype_id)
      references td_sm_dicttype (dicttype_id) on delete restrict on update restrict;

alter table td_sm_grouporg add constraint fk_td_sm_grouporg1 foreign key (group_id)
      references td_sm_group (group_id) on delete restrict on update restrict;

alter table td_sm_grouporg add constraint fk_td_sm_grouporg2 foreign key (org_id)
      references td_sm_organization (org_id) on delete restrict on update restrict;

alter table td_sm_grouporgjob add constraint fk_td_sm_grouporgjob1 foreign key (group_id)
      references td_sm_group (group_id) on delete restrict on update restrict;

alter table td_sm_grouporgjob add constraint fk_td_sm_grouporgjob2 foreign key (job_id)
      references td_sm_job (job_id) on delete restrict on update restrict;

alter table td_sm_grouprole add constraint fk_td_sm_grouprole1 foreign key (group_id)
      references td_sm_group (group_id) on delete restrict on update restrict;

alter table td_sm_grouprole add constraint fk_td_sm_grouprole2 foreign key (role_id)
      references td_sm_role (role_id) on delete restrict on update restrict;

alter table td_sm_instance_job add constraint fk_td_sm_instance_job foreign key (userinstance_id)
      references td_sm_userinstance (id) on delete restrict on update restrict;

alter table td_sm_instance_org add constraint fk_td_sm_instance_org1 foreign key (userinstance_id)
      references td_sm_userinstance (id) on delete restrict on update restrict;

alter table td_sm_orgjob add constraint fk_td_sm_orgjob1 foreign key (org_id)
      references td_sm_organization (org_id) on delete restrict on update restrict;

alter table td_sm_orgjob add constraint fk_td_sm_orgjob2 foreign key (job_id)
      references td_sm_job (job_id) on delete restrict on update restrict;

alter table td_sm_role add constraint fk_td_sm_role foreign key (role_type)
      references td_sm_roletype (type_id) on delete restrict on update restrict;

alter table td_sm_userinstance add constraint fk_td_sm_userinstance foreign key (user_id)
      references td_sm_user (user_id) on delete restrict on update restrict;

alter table td_sm_userinstancegroup add constraint fk_td_sm_userinstancegroup1 foreign key (userinstance_id)
      references td_sm_userinstance (id) on delete restrict on update restrict;

alter table td_sm_userinstancegroup add constraint fk_td_sm_userinstancegroup2 foreign key (group_id)
      references td_sm_group (group_id) on delete restrict on update restrict;

alter table td_sm_userinstancerole add constraint fk_td_sm_userinstancerole1 foreign key (userinstance_id)
      references td_sm_userinstance (id) on delete restrict on update restrict;

alter table td_sm_userinstancerole add constraint fk_td_sm_userinstancerole2 foreign key (role_id)
      references td_sm_role (role_id) on delete restrict on update restrict;

-- 存储过程脚本_mysql -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
-- 日志备份存储过程
-- start
create procedure log_backup (in backup_date integer)
begin
	-- 日志备份，backup_date 表示当前系统时间多少天已前的数据需要备份
	-- 备份日志主表
  	insert into td_sm_log_his
		select * from td_sm_log t where date_format(t.log_opertime,'%Y%m%d')<=date_format(date_sub(current_timestamp,interval backup_date day),'%Y%m%d');	
	-- 删除日志主表
	delete from td_sm_log  where date_format(log_opertime,'%Y%m%d')<=date_format(date_sub(current_timestamp,interval backup_date day),'%Y%m%d');
	commit;
end;
-- end;

-- 函数脚本_mysql -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
-- 获取机构所有子节点
-- start
create function org_getChilds(rootId varchar(50)) returns varchar(10000)
begin  
	-- 获取机构所有子节点
	declare sTemp varchar(10000);
	declare sTempChd varchar(10000);
	set sTemp = null;
	set sTempChd = rootId;
	
	while sTempChd is not null do 
		select group_concat(org_id) into sTempChd from td_sm_organization where find_in_set(parent_id,sTempChd) > 0;   
			if sTempChd is not null then 
				if sTemp is null then
					set sTemp = sTempChd;
				else
					set sTemp = concat(sTemp,',',sTempChd);
				end if;
			end if;
	end while;
	return sTemp;  
end;
-- end;

-- 获取机构所有父节点
-- start
create function org_getParents(childId varchar(50)) returns varchar(10000)
begin
	-- 获取机构所有父节点
	declare sTemp varchar(10000);
	declare sTempParentId varchar(10000);
	set sTemp = null;
	set sTempParentId = childId;     
    
	while sTempParentId!='0' do 
		select  parent_id  into sTempParentId  from td_sm_organization where org_id=sTempParentId;
			if sTempParentId  is not null then
				if sTemp is null then
					set sTemp = sTempParentId;
				else
					set sTemp = concat(sTemp,',',sTempParentId);   
				end if;
			end if;    
	end while; 
	return sTemp;   
end;
-- end;

-- 获取权限所有子节点
-- start
create function privilege_getChilds(rootId varchar(50)) returns varchar(10000)
begin  
	-- 获取权限所有子节点
	declare sTemp varchar(10000);
	declare sTempChd varchar(10000);
	set sTemp = null;
	set sTempChd = rootId;
	
	while sTempChd is not null do 
		select group_concat(id) into sTempChd from tb_sm_privilege where find_in_set(parent_id,sTempChd) > 0;   
			if sTempChd is not null then 
				if sTemp is null then
					set sTemp = sTempChd;
				else
					set sTemp = concat(sTemp,',',sTempChd);
				end if;
			end if;
	end while;
	return sTemp;  
end;
-- end;

-- 获取权限所有父节点
-- start
create function privilege_getParents(childId varchar(50)) returns varchar(10000)
begin
	-- 获取权限所有父节点
	declare sTemp varchar(10000);
	declare sTempParentId varchar(10000);
	set sTemp = null;
	set sTempParentId = childId;     
    
	while sTempParentId!='0' do 
		select  parent_id  into sTempParentId  from tb_sm_privilege where id=sTempParentId;
			if sTempParentId  is not null then
				if sTemp is null then
					set sTemp = sTempParentId;
				else
					set sTemp = concat(sTemp,',',sTempParentId);   
				end if;
			end if;    
	end while; 
	return sTemp;   
end;
-- end;

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
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('121', 'jobPermMgt', '岗位资源管理', '12', '4', 'jobPermMgt', '1', current_timestamp, 1);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('122', 'orgUserMgt', '机构用户管理', '12', '4', 'orgUserMgt', '1', current_timestamp, 2);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('123', 'dictMgt', '字典管理', '12', '4', 'dictMgt', '1', current_timestamp, 3);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('131', 'logQuery', '日志查询', '13', '4', 'logQuery', '1', current_timestamp, 1);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('141', 'sysInfo', '全局参数配置', '14', '4', 'sysInfo', '1', current_timestamp, 1);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('142', 'sessionMgt', '会话管理', '14', '4', 'sessionMgt', '1', current_timestamp, 2);
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('1', '1', null, 'fa-briefcase', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('11', '1', null, 'fa-dashboard', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('12', '1', null, 'fa-wrench', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('13', '1', null, 'fa-book', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('14', '1', null, 'fa-cogs', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('111', '1', '#/f/personInfo', 'fa-user', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('112', '1', '#/f/modifyPwd', 'fa-key', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('121', '1', '#/f/jobResMain', 'fa-sitemap', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('122', '1', '#/f/orgUserMain', 'fa-users', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('123', '1', '#/f/dictMain', 'fa-list', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('131', '1', '#/f/logQueryMain', 'fa-bookmark', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('141', '1', '#/f/sysInfoMain', 'fa-flask', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('142', '1', '#/f/sessionMain', 'fa-headphones', '0');

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

commit;