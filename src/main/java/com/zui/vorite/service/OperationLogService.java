package com.zui.vorite.service;

import com.zui.vorite.pojo.OperationLog;

import java.util.List;

/**
 * @file: OperationLogService.class
 * @author: Dusk
 * @since: 2018/12/5 11:15
 * @desc:
 */
public interface OperationLogService {

    /**
     * 取出所有操作日志
     * @return
     */
    List<OperationLog> seleteAll();

    /**
     * 取出指定路径下的日志
     * @param url
     * @return
     */
    List<OperationLog> seleteByUrl(String url);

    /**
     * 插入一条记录
     * @param operationLog
     * @return
     */
    int insert(OperationLog operationLog);
}
