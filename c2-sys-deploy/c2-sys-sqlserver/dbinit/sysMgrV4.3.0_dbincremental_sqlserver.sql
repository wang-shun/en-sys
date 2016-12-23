-- 系统管理V4.2.3-4.3.0完整精简版增量升级脚本_sqlserver

-- 新增权限内部关联表
create table tb_sm_privilege_insiderelate  (
   id			varchar(50)		not null,
   relate_id	varchar(50)		not null,
   sn			numeric			null
)
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限内部关联表',
   'user', @currentuser, 'table', 'tb_sm_privilege_insiderelate'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '权限ID',
   'user', @currentuser, 'table', 'tb_sm_privilege_insiderelate', 'column', 'id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '关联ID(父ID)',
   'user', @currentuser, 'table', 'tb_sm_privilege_insiderelate', 'column', 'relate_id'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '排序号',
   'user', @currentuser, 'table', 'tb_sm_privilege_insiderelate', 'column', 'sn'
go

-- 权限表新增字段
alter table tb_sm_privilege add virtual char(1) not null default '0'
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否虚拟节点（0：否，1：是）',
   'user', @currentuser, 'table', 'tb_sm_privilege', 'column', 'virtual'
go

-- 日志表新增字段
alter table td_sm_log add target_pk varchar(200) null
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '目标主键',
   'user', @currentuser, 'table', 'td_sm_log', 'column', 'target_pk'
go


-- 历史日志表新增字段
alter table td_sm_log_his add target_pk varchar(200) null
go

declare @currentuser sysname
select @currentuser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '目标主键',
   'user', @currentuser, 'table', 'td_sm_log_his', 'column', 'target_pk'
go