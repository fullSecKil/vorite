package com.zui.vorite.service;

import com.zui.vorite.pojo.Caricature;

import java.util.List;

/**
 * @author Dusk
 */

public interface CaricatureService {

    /**
     * 某一条
     * @param id
     * @return
     */
    Caricature getCaricature(Long id);

    /**
     * list
     * @return
     */
    List<Caricature> selectAll();

    /**
     * 追加
     * @param caricature
     * @return
     */
    int insertOrUpdate(Caricature caricature);

    /**
     * 删
     * @param id
     * @return
     */
    int caricatureDel(Long id);
}
