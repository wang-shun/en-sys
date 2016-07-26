package com.chinacreator.asp.comp.sys.sdk.log.service;

import com.chinacreator.asp.comp.sys.sdk.log.dto.LogDTO;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

import java.util.Map;


/**
 * 历史日志服务接口
 * @author 彭盛
 */
public interface LogHistoryService {
    /**
    * 删除所有历史日志
    */
    public void deleteAll();

    /**
    * 删除历史日志
    * @param logIds历史日志ID数组
    */
    public void deleteByPKs(String... logIds);

    /**
    * 分页查询所有历史日志
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的历史日志数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<LogDTO> queryAll(Pageable pageable, Sortable sortable);

    /**
    * 分页查询历史日志
    * @param logDTO历史日志数据传输对象
    * @param paramMap其他查询条件<br>
    * <table border="1">
    * <tr>
    * <td>key值</td>
    * <td>对象类型</td>
    * <td>说明</td>
    * </tr>
    * <tr>
    * <td>startDate</td>
    * <td>java.util.Date</td>
    * <td>操作开始时间</td>
    * </tr>
    * <tr>
    * <td>endDate</td>
    * <td>java.util.Date</td>
    * <td>操作结束时间</td>
    * </tr>
    * </table>
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的历史日志数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<LogDTO> queryByLog(LogDTO logDTO, Map<String, Object> paramMap,
        Pageable pageable, Sortable sortable);

    /**
    * 查询历史日志
    * @param logId历史日志ID
    * @return 历史日志数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public LogDTO queryByPK(String logId);
}
