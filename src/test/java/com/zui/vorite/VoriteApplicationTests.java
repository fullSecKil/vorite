package com.zui.vorite;

import com.zui.vorite.dao.CaricatureMapper;
import com.zui.vorite.pojo.OperationLog;
import com.zui.vorite.service.GenreCaricatureService;
import com.zui.vorite.service.OperationLogService;
import com.zui.vorite.tools.ExtractZip;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VoriteApplicationTests {

    final static private Logger log = LoggerFactory.getLogger(VoriteApplicationTests.class);

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

    @Autowired
    ExtractZip extractZip;

    @Test
    public void unZIpTest(){
        Map<String, String> fileMap = new HashMap<>();
        fileMap.put("E:\\download\\caricature\\upload\\一拳超人\\closing.zip", "E:\\download\\caricature\\upload\\一拳超人");
        fileMap.put("E:\\download\\caricature\\upload\\一拳超人\\特别篇.zip", "E:\\download\\caricature\\upload\\一拳超人");

        // List<String> names = extractZip.unZip("E:\\download\\caricature\\upload\\一拳超人\\closing.zip", "E:\\download\\caricature\\upload\\一拳超人");
        System.out.println(extractZip.unZip(fileMap));
        log.info("-------------------------->");
    }
}
