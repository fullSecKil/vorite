package com.zui.vorite.dao;

import com.zui.vorite.pojo.Caricature;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * 集成模板
 *
 * @author Dusk
 */

@Mapper
@Transactional(rollbackFor = Exception.class)
public interface CaricatureMapper extends BaseMapper<Caricature> {

    @Select("select * from caricature where genre=#{genre}")
    List<Caricature> selectCaricatureByGenre(int genre);

}
