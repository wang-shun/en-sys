-- 系统管理V4.1.3-4.1.4标准版增量脚本_oracle

-- 修改系统信息类别表
alter table tb_system_type modify type_name not null;

-- 修改系统信息表
alter table tb_system_info modify info_name varchar2(500) not null;
alter table tb_system_info modify info_content varchar2(2000);
alter table tb_system_info modify info_desc varchar2(500);
alter table tb_system_info add constraint fk_tb_system_info foreign key (info_type) references tb_system_type (id);

-- 新增用户偏好设置信息表
create table td_sm_userpreferences  (
   user_id              varchar2(50)                    not null,
   info_name            varchar2(500)                   not null,
   info_content         varchar2(2000),
   info_desc            varchar2(500),
   info_lastupdate      timestamp                      default sysdate   
);
comment on table td_sm_userpreferences is '用户偏好设置信息表';
comment on column td_sm_userpreferences.user_id is '用户ID';
comment on column td_sm_userpreferences.info_name is '信息名称';
comment on column td_sm_userpreferences.info_content is '信息内容';
comment on column td_sm_userpreferences.info_desc is '信息描述'; 
comment on column td_sm_userpreferences.info_lastupdate is '最后修改时间';

-- 菜单表中字段修改说明
comment on column tb_sm_menu.display_mode is '打开方式（0：div中，1：iframe中，2：新页面中）';

-- 用户表中用户密码字段长度由原128修改为512
alter table td_sm_user modify user_password varchar2(512);