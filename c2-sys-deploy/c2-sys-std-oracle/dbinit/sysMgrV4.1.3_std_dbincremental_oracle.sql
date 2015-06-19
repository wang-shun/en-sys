-- 系统管理V4.1.1-4.1.3标准版增量脚本_oracle

-- 新增权限及菜单
insert into tb_sm_privilege (id, code, name, parent_id, type, perm_expr, creator, creator_time, sn) values ('142', 'sessionMgt', '会话管理', '14', '4', 'sessionMgt', '1', sysdate, 2);
insert into tb_sm_menu (menu_id, is_enabled, href, icon, display_mode) values ('142', '1', '#/f/sessionMain', 'fa-headphones', '0');

commit;