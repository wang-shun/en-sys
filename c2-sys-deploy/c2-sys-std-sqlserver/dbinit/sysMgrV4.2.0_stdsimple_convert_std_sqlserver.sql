-- 系统管理V4.2.0,标准精简版(stdsimple)转换为标准版(std)数据库脚本_sqlserver

-- 删除"机构管理、用户管理、岗位权限管理、资源维护"菜单模块及其授予权限信息
delete from td_sm_roleresop where op_id in('124','125','126','127');
delete from tb_sm_menu where menu_id in('124','125','126','127');
delete from tb_sm_privilege where id in('124','125','126','127');

-- 新增"机构管理、用户管理、岗位权限管理、资源维护"菜单模块

insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('121', 'jobPermMgt', '岗位资源管理', '12', '4', 'jobPermMgt', '1', current_timestamp, 1);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('122', 'orgUserMgt', '机构用户管理', '12', '4', 'orgUserMgt', '1', current_timestamp, 2);
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('121', '1', '#/f/jobResMain', 'fa-sitemap', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('122', '1', '#/f/orgUserMain', 'fa-users', '0');

-- 修改"字典管理"菜单排序
update tb_sm_privilege set sn = 3 where id = '123';

go