-- 系统管理V4.0.0-4.0.2标准版增量脚本_mysql
-- 机构信息表字段行政区码字段长度由原10修改为12
alter table td_sm_organization modify org_xzqm varchar(12);