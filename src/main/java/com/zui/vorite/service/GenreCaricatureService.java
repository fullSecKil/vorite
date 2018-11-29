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
     * @return
     */
    List<GenreCaricature> selectAll();
}
