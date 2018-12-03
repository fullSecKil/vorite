package com.zui.vorite.service;

import com.zui.vorite.pojo.GenreCaricature;

import java.util.List;

/**
 * @file: GenreCaricatureService.class
 * @author: Dusk
 * @since: 2018/11/29 22:48
 * @desc:
 */
public interface GenreCaricatureService {
    /**
     * 种类中的漫画都select出来
     * @return
     */
    List<GenreCaricature> selectAll();

    List<GenreCaricature> selectAllGenreAndCaricature();

    GenreCaricature selectById(Long id);

}
