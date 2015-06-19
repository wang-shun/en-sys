-- 系统管理V4.0.2-4.1.0标准版增量脚本_mysql

-- 字典数据表中字段修改说明
alter table td_sm_dictdata modify column dictdata_name varchar(100) comment '字典真实值';
alter table td_sm_dictdata modify column dictdata_value varchar(100) comment '字典显示值';

-- 菜单表中去除字段（菜单创建者、创建时间、排序号）
alter table tb_sm_menu drop column creator;
alter table tb_sm_menu drop column creator_time;
alter table tb_sm_menu drop column sn;

-- 菜单中新增字段（显示方式）
alter table tb_sm_menu add display_mode char(1) default '0' not null comment '显示方式（0：div中，1：iframe中）'; 

-- 权限表中字段（权限编码、权限名称、父ID）长度修改为500
alter table tb_sm_privilege modify code varchar(500) not null comment '权限编码';
alter table tb_sm_privilege modify name varchar(500) comment '权限名称';
alter table tb_sm_privilege modify parent_id varchar(500) not null default '0' comment '父ID';

-- 权限表中字段（类型）修改字段默认值及说明
alter table tb_sm_privilege modify type char(1) not null comment '类型（1:服务(service),2:表单(form),3:表单元素(dom),4:菜单(menu),5:实体(entity),6:实体操作(entity_op),9:自定义(custom)）'; 

-- 权限表中新增字段（创建者、创建时间、排序号、权限来源）
alter table tb_sm_privilege add creator varchar(50) default '1' comment '创建者';
alter table tb_sm_privilege add creator_time timestamp default current_timestamp comment '创建时间';
alter table tb_sm_privilege add sn decimal(10) comment '排序号';
alter table tb_sm_privilege add source char(1) not null default '0' comment '权限来源（0：自定义，1：IDE）';

-- 删除所有权限授予及权限、菜单数据
delete from td_sm_roleresop;
delete from tb_sm_menu;
delete from tb_sm_privilege;

-- 初始化权限及菜单数据
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
commit;
