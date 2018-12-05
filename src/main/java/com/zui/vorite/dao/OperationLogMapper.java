package com.zui.vorite.dao;

import com.zui.vorite.pojo.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * 操作日志
 * @file: OperationLogMapper.class
 * @author: Dusk
 * @since: 2018/12/5 11:16
 * @desc:
 */
@Mapper
@Transactional(rollbackFor = Exception.class)
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    /**
     * 取出某个请求路径
     * @param url
     * @return
     */
    @Select(value = "select * from operation_log where url=#{url}")
    List<OperationLog> seleteByUrl(String url);
}
