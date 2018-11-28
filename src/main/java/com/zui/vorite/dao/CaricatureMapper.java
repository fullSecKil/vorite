package com.zui.vorite.dao;

import com.zui.vorite.pojo.Caricature;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * 集成模板
 * @author Dusk
 */

@Mapper
@Transactional(rollbackFor = Exception.class)
public interface CaricatureMapper extends BaseMapper<Caricature> {

}
