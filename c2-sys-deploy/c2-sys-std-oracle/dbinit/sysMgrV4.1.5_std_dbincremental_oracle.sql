-- 系统管理V4.1.4-4.1.5标准版增量脚本_oracle

-- 菜单表中添加字段
alter table tb_sm_menu add menu_ext clob;
comment on column tb_sm_menu.menu_ext is '菜单扩展字段';