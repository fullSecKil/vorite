package com.zui.vorite;

import com.zui.vorite.dao.CaricatureMapper;
import com.zui.vorite.pojo.OperationLog;
import com.zui.vorite.service.GenreCaricatureService;
import com.zui.vorite.service.OperationLogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VoriteApplicationTests {
    @Autowired
    GenreCaricatureService genreCaricatureService;

    @Autowired
    CaricatureMapper caricatureMapper;

    @Autowired
    OperationLogService operationLogService;

    @Test
    public void contextLoads() {
        System.out.println(genreCaricatureService.selectAll());
        // System.out.println(caricatureMapper.selectCaricatureByGenre(1));
    }

    @Test
    public void logServiceTest(){
        System.out.println(operationLogService.seleteAll());
    }
}
