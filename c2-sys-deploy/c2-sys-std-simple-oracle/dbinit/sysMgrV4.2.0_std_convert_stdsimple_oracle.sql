-- 系统管理V4.2.0,标准版(std)转换为标准精简版(stdsimple)数据库脚本_oracle

-- 删除"岗位资源管理、机构用户管理"菜单模块及其授予权限信息
delete from td_sm_roleresop where op_id in('121','122');
delete from tb_sm_menu where menu_id in('121','122');
delete from tb_sm_privilege where id in('121','122');

-- 新增"机构管理、用户管理、岗位权限管理、资源维护"菜单模块
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('124', 'orgMgt', '机构管理', '12', '4', 'orgMgt', '1', sysdate, 1);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('125', 'userMgt', '用户管理', '12', '4', 'userMgt', '1', sysdate, 2);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('126', 'jobMgt', '岗位权限管理', '12', '4', 'jobMgt', '1', sysdate, 3);
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('127', 'resMgt', '资源维护', '12', '4', 'resMgt', '1', sysdate, 4);
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('124', '1', '#/f/orgMain', 'fa-sitemap', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('125', '1', '#/f/userMain', 'fa-users', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('126', '1', '#/f/jobMain', 'fa-anchor', '0');
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('127', '1', '#/f/resMain', 'fa-tree', '0');

-- 修改"字典管理"菜单排序
update tb_sm_privilege set sn = 5 where id = '123';

commit;