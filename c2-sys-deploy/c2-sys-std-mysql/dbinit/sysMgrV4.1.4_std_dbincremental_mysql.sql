-- 系统管理V4.1.3-4.1.4标准版增量脚本_mysql

-- 修改系统信息类别表
alter table tb_system_type modify column type_name varchar(50) not null comment '系统信息类型名称';

-- 修改系统信息表
alter table tb_system_info modify column info_name varchar(500) not null comment '系统信息名称';
alter table tb_system_info modify column info_content varchar(2000) comment '系统信息内容';
alter table tb_system_info modify column info_desc varchar(500) comment '系统信息描述';
alter table tb_system_info add constraint fk_tb_system_info foreign key (info_type)
      references tb_system_type (id) on delete restrict on update restrict;

-- 新增用户偏好设置信息表
create table td_sm_userpreferences
(
   user_id              varchar(50) not null comment '用户ID',
   info_name            varchar(500) not null comment '信息名称',
   info_content         varchar(2000) comment '信息内容',
   info_desc            varchar(500) comment '信息描述',
   info_lastupdate      timestamp default current_timestamp comment '最后修改时间'   
);
alter table td_sm_userpreferences comment '用户偏好设置信息表';

-- 菜单表中字段修改说明
alter table tb_sm_menu modify column display_mode char(1) not null default '0' comment '打开方式（0：div中，1：iframe中，2：新页面中）';

-- 用户表中用户密码字段长度由原128修改为512
alter table td_sm_user modify column user_password varchar(512) not null comment '用户密码';