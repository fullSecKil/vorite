package com.zui.vorite.dao;

import com.zui.vorite.pojo.GenreCaricature;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @file: GenreCaricatureMapper.class
 * @author: Dusk
 * @since: 2018/11/29 22:15
 * @desc:
 */

@Mapper
@Transactional(rollbackFor = Exception.class)
public interface GenreCaricatureMapper extends BaseMapper<GenreCaricature> {

    @Select("select * from genre_caricature")
    @Results({@Result(column="genre", property="genre"), @Result(column="genreDescribe",property="genre_describe"), @Result(property="caricatureList",  javaType = List.class, column="id", many=@Many(select = "com.zui.vorite.dao.CaricatureMapper.selectCaricatureByGenre"))})
    List<GenreCaricature> seletAllGenreCaricature();
}
