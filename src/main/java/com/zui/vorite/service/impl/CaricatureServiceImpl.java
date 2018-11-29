package com.zui.vorite.service.impl;

import com.zui.vorite.dao.CaricatureMapper;
import com.zui.vorite.pojo.Caricature;
import com.zui.vorite.service.CaricatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Dusk
 */
@Service
public class CaricatureServiceImpl implements CaricatureService {

    private CaricatureMapper caricatureMapper;

    @Autowired
    public void getMapper(CaricatureMapper caricatureMapper) {
        this.caricatureMapper = caricatureMapper;
    }

    @Override
    public Caricature getCaricature(Long id) {
        Caricature c = caricatureMapper.selectByPrimaryKey(id);
        return caricatureMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Caricature> selectAll() {
        return caricatureMapper.selectAll();
    }

    @Override
    public int insertOrUpdate(Caricature caricature) {
        int flag = Optional.ofNullable(caricature.getId()).filter(caricatureMapper::existsWithPrimaryKey).map(i -> caricatureMapper.updateByPrimaryKeySelective(caricature)).orElse(0);
        return flag == 0 ? caricatureMapper.insertSelective(caricature) : flag;
    }

    @Override
    public int caricatureDel(Long id) {
        return caricatureMapper.deleteByPrimaryKey(id);
    }
}
