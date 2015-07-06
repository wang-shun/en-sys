-- 系统管理V4.1.4-4.2.0标准版增量脚本_oracle

-- 菜单表中添加字段
alter table tb_sm_menu add menu_ext clob;
comment on column tb_sm_menu.menu_ext is '菜单扩展字段';

-- 删除"会话管理"菜单模块及其授予权限信息
delete from td_sm_roleresop where op_id = '142';
delete from tb_sm_menu where menu_id = '142';
delete from tb_sm_privilege where id = '142';
commit;