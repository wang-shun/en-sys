-- 系统管理V4.2.3-5.0.0完整精简版增量升级脚本_mysql

-- 新增权限内部关联表
create table tb_sm_privilege_insiderelate  (
   id			varchar(50)	not null	comment '权限ID',
   relate_id	varchar(50)	not null	comment '关联ID(父ID)',
   sn			decimal(10)				comment '排序号'
);
alter table tb_sm_privilege_insiderelate comment '权限内部关联表';

-- 权限表新增字段
alter table tb_sm_privilege add virtual char(1) not null default '0' comment '是否虚拟节点（0：否，1：是）';

-- 日志表新增字段
alter table td_sm_log add target_pk varchar(200) comment '目标主键';

-- 历史日志表新增字段
alter table td_sm_log_his add target_pk varchar(200) comment '目标主键';