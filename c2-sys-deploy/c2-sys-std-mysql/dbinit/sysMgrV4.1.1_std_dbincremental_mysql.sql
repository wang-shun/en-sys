-- 系统管理V4.1.0-4.1.1标准版增量脚本_mysql

-- 修改日志表
alter table td_sm_log drop column op_orgid;
alter table td_sm_log modify log_operuser varchar(200) comment '用户帐号';
alter table td_sm_log modify oper_module varchar(50) comment '日志模块ID';
alter table td_sm_log modify log_opertime timestamp default current_timestamp comment '操作时间';
alter table td_sm_log modify oper_type decimal(1) comment '操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他）';
alter table td_sm_log modify remark1 varchar(100) comment '备注1';
alter table td_sm_log add log_status decimal(1) comment '日志状态（1：成功，0：失败）';
alter table td_sm_log add remark2 varchar(100) comment '备注2';
alter table td_sm_log add remark3 varchar(100) comment '备注3';
alter table td_sm_log add remark4 varchar(100) comment '备注4';
alter table td_sm_log add remark5 varchar(100) comment '备注5';

-- 修改历史日志表
alter table td_sm_log_his drop column op_orgid;
alter table td_sm_log_his modify log_operuser varchar(200) comment '用户帐号';
alter table td_sm_log_his modify oper_module varchar(50) comment '日志模块ID';
alter table td_sm_log_his modify log_opertime timestamp default current_timestamp comment '操作时间';
alter table td_sm_log_his modify oper_type decimal(1) comment '操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他）';
alter table td_sm_log_his modify remark1 varchar(100) comment '备注1';
alter table td_sm_log_his add log_status decimal(1) comment '日志状态（1：成功，0：失败）';
alter table td_sm_log_his add remark2 varchar(100) comment '备注2';
alter table td_sm_log_his add remark3 varchar(100) comment '备注3';
alter table td_sm_log_his add remark4 varchar(100) comment '备注4';
alter table td_sm_log_his add remark5 varchar(100) comment '备注5';

-- 修改日志配置表
alter table td_sm_logconfig drop primary key;
alter table td_sm_logconfig drop column menu_code;
alter table td_sm_logconfig add oper_module varchar(50) not null comment '日志模块ID';
alter table td_sm_logconfig add log_type varchar(50) comment '日志类型（e：实体操作，ws：服务，dao：持久层操作，custom：自定义）';
alter table td_sm_logconfig add log_oper varchar(200) comment '日志操作ID';
alter table td_sm_logconfig add log_operdesc varchar(500) comment '日志操作描述';
alter table td_sm_logconfig add primary key (oper_module);