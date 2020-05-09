package com.zui.vorite;

import com.zui.vorite.controller.VisitController;
import com.zui.vorite.dao.CaricatureMapper;
import com.zui.vorite.pojo.Caricature;
import com.zui.vorite.service.CaricatureService;
import com.zui.vorite.service.GenreCaricatureService;
import com.zui.vorite.service.OperationLogService;
import com.zui.vorite.tools.ExtractZip;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
// 指定webEnvironment属性，自动初始化Filter、Servlet
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VoriteApplicationTests {

    final static private Logger log = LoggerFactory.getLogger(VoriteApplicationTests.class);

    @Autowired
    GenreCaricatureService genreCaricatureService;

    @Autowired
    CaricatureMapper caricatureMapper;

    @Autowired
    OperationLogService operationLogService;

    private CaricatureService caricatureService;

    @Autowired
    void setCaricatureService(CaricatureService caricatureService) {
        this.caricatureService = caricatureService;
    }

    @Autowired
    private MockMvc mvc;

    @Test
    public void testManageController() throws Exception {
        RequestBuilder request = null;

        List<Caricature> caricatures = caricatureService.selectAll();

        request = get("/visit/group");
        mvc.perform(request)
                .andExpect(status().isOk())
                // .andExpect(content().string(equalTo(caricatures.toString())))
                .andDo(print());
    }

    @Test
    public void testCaricatureService() {
        List<Caricature> c = caricatureService.selectAll();
        Optional<Caricature> caricature = c.stream().filter(caricature1 -> "一拳超人".equals(caricature1.getName())).findFirst();
        Assert.assertTrue(caricature.isPresent());
        Assert.assertEquals(caricature.map(caricature1 -> caricature1.getAuthor()).orElse("未知作者"), "村田雄介 ");
    }

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
        Map<Sstring, String> fileMap = new HashMap<>();
        fileMap.put("E:\\download\\caricature\\upload\\一拳超人\\closing.zip", "E:\\download\\caricature\\upload\\一拳超人");
        fileMap.put("E:\\download\\caricature\\upload\\一拳超人\\特别篇.zip", "E:\\download\\caricature\\upload\\一拳超人");

        // List<String> names = extractZip.unZip("E:\\download\\caricature\\upload\\一拳超人\\closing.zip", "E:\\download\\caricature\\upload\\一拳超人");
        System.out.println(extractZip.unZip(fileMap));
        log.info("-------------------------->");
    }
}
