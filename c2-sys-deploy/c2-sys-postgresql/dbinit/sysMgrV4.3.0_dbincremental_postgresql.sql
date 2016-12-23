-- 系统管理V4.2.3-4.3.0完整精简版增量升级脚本_postgresql

-- 新增权限内部关联表
create table tb_sm_privilege_insiderelate  (
   id			varchar(50)		not null,
   relate_id	varchar(50)		not null,
   sn			numeric			null
);
comment on table tb_sm_privilege_insiderelate is
'权限内部关联表';
comment on column tb_sm_privilege_insiderelate.id is
'权限ID';
comment on column tb_sm_privilege_insiderelate.relate_id is
'关联ID(父ID)';
comment on column tb_sm_privilege_insiderelate.sn is
'排序号';

-- 权限表新增字段
alter table tb_sm_privilege add virtual char(1) default '0' not null;
comment on column tb_sm_privilege.virtual is '是否虚拟节点（0：否，1：是）';

-- 日志表新增字段
alter table td_sm_log add target_pk varchar(200);
comment on column td_sm_log.target_pk is '目标主键';

-- 历史日志表新增字段
alter table td_sm_log_his add target_pk varchar(200);
comment on column td_sm_log_his.target_pk is '目标主键';