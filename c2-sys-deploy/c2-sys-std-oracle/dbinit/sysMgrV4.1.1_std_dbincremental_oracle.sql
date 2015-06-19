-- 系统管理V4.1.0-4.1.1标准版增量脚本_oracle

-- 修改日志表
alter table td_sm_log drop column op_orgid;
alter table td_sm_log modify log_operuser varchar2(200);
alter table td_sm_log modify oper_module varchar2(50);
alter table td_sm_log modify log_opertime timestamp;
alter table td_sm_log add log_status number(1);
alter table td_sm_log add remark2 varchar2(100);
alter table td_sm_log add remark3 varchar2(100);
alter table td_sm_log add remark4 varchar2(100);
alter table td_sm_log add remark5 varchar2(100);
comment on column td_sm_log.log_operuser is '用户帐号';
comment on column td_sm_log.oper_module is '日志模块ID';
comment on column td_sm_log.oper_type is '操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他）';
comment on column td_sm_log.log_status is '日志状态（1：成功，0：失败）';
comment on column td_sm_log.remark1 is '备注1';
comment on column td_sm_log.remark2 is '备注2';
comment on column td_sm_log.remark3 is '备注3';
comment on column td_sm_log.remark4 is '备注4';
comment on column td_sm_log.remark5 is '备注5';

-- 修改历史日志表
alter table td_sm_log_his drop column op_orgid;
alter table td_sm_log_his modify log_operuser varchar2(200);
alter table td_sm_log_his modify oper_module varchar2(50);
alter table td_sm_log_his modify log_opertime timestamp;
alter table td_sm_log_his add log_status number(1);
alter table td_sm_log_his add remark2 varchar2(100);
alter table td_sm_log_his add remark3 varchar2(100);
alter table td_sm_log_his add remark4 varchar2(100);
alter table td_sm_log_his add remark5 varchar2(100);
comment on column td_sm_log_his.log_operuser is '用户帐号';
comment on column td_sm_log_his.oper_module is '日志模块ID';
comment on column td_sm_log_his.oper_type is '操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他）';
comment on column td_sm_log_his.log_status is '日志状态（1：成功，0：失败）';
comment on column td_sm_log_his.remark1 is '备注1';
comment on column td_sm_log_his.remark2 is '备注2';
comment on column td_sm_log_his.remark3 is '备注3';
comment on column td_sm_log_his.remark4 is '备注4';
comment on column td_sm_log_his.remark5 is '备注5';


-- 修改日志配置表
alter table td_sm_logconfig drop constraint pk_td_sm_logconfig cascade;
alter table td_sm_logconfig drop column menu_code;
alter table td_sm_logconfig add oper_module varchar2(50) not null;
alter table td_sm_logconfig add log_type varchar2(50);
alter table td_sm_logconfig add log_oper varchar2(200);
alter table td_sm_logconfig add log_operdesc varchar2(500);
comment on column td_sm_logconfig.oper_module is '日志模块ID';
comment on column td_sm_logconfig.log_type is '日志类型（e：实体操作，ws：服务，dao：持久层操作，custom：自定义）';
comment on column td_sm_logconfig.log_oper is '日志操作ID';
comment on column td_sm_logconfig.log_operdesc is '日志操作描述';
alter table td_sm_logconfig add constraint pk_td_sm_logconfig primary key (oper_module);