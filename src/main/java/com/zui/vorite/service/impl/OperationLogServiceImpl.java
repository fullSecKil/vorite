package com.zui.vorite.service.impl;

import com.zui.vorite.dao.OperationLogMapper;
import com.zui.vorite.pojo.OperationLog;
import com.zui.vorite.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志的业务操作
 *
 * @file: OperationLogServiceImpl.class
 * @author: Dusk
 * @since: 2018/12/5 11:39
 * @desc:
 */

@Service
public class OperationLogServiceImpl implements OperationLogService {

    private OperationLogMapper operationLogMapper;

    @Autowired
    public void setOperationLogMapper(OperationLogMapper operationLogMapper) {
        this.operationLogMapper = operationLogMapper;
    }

    @Override
    public List<OperationLog> seleteAll() {
        return this.operationLogMapper.selectAll();
    }

    @Override
    public List<OperationLog> seleteByUrl(String url) {
        return operationLogMapper.seleteByUrl(url);
    }

    @Override
    public int insert(OperationLog operationLog) {
        return operationLogMapper.insertSelective(operationLog);
    }
}
