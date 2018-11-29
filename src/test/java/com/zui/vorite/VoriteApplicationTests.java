package com.zui.vorite;

import com.zui.vorite.dao.CaricatureMapper;
import com.zui.vorite.service.GenreCaricatureService;
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

    @Test
    public void contextLoads() {
        System.out.println(genreCaricatureService.selectAll());
        // System.out.println(caricatureMapper.selectCaricatureByGenre(1));
    }
}
