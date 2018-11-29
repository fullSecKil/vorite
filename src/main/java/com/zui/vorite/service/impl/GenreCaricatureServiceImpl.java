package com.zui.vorite.service.impl;

import com.zui.vorite.dao.GenreCaricatureMapper;
import com.zui.vorite.pojo.GenreCaricature;
import com.zui.vorite.service.GenreCaricatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @file: GenreCaricatureServiceImpl.class
 * @author: Dusk
 * @since: 2018/11/29 22:49
 * @desc:
 */

@Service
public class GenreCaricatureServiceImpl implements GenreCaricatureService {

    private GenreCaricatureMapper genreCaricatureMapper;

    @Autowired
    public void getMapper(GenreCaricatureMapper genreCaricatureMapper){
        this.genreCaricatureMapper = genreCaricatureMapper;
    }

    @Override
    public List<GenreCaricature> selectAll() {
        return genreCaricatureMapper.seletAllGenreCaricature();
    }
}
