package com.zui.vorite.controller;

import com.zui.vorite.pojo.Caricature;
import com.zui.vorite.service.CaricatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @file: VisitController.class
 * @author: Dusk
 * @since: 2019/2/23 17:07
 * @desc: 浏览管理
 */

@Controller
@RequestMapping("/visit")
public class VisitController {

    private CaricatureService caricatureService;

    @Autowired
    void setCaricatureService(CaricatureService caricatureService) {
        this.caricatureService = caricatureService;
    }

    @GetMapping("/group")
    public Object group(Model model){
        if(!model.containsAttribute("caricatures")){
            List<Caricature> caricatures = caricatureService.selectAll();
            model.addAttribute("caricatures", caricatures);
        }
        List<Caricature> hottest = caricatureService.selectAll().stream().sorted(Comparator.comparing(Caricature::getStar)).limit(5).collect(Collectors.toList());
        model.addAttribute("hottestCaricature", hottest);
        return "cp/share";
    }

    @GetMapping("/global/caricature/upload/{name}")
    public String visitCaricature(@RequestParam("caricatureId") Long id, @PathVariable("name") String name, Model model) throws FileNotFoundException {
        Caricature caricature = caricatureService.getCaricature(id);
        File path = new File(ResourceUtils.getURL("classpath:static").getPath());
        File caricatureFile = new File(path.getAbsolutePath(), caricature.getPath());
        List<String> caricatureFileNames =  Arrays.stream(caricatureFile.listFiles(File::isDirectory)).map(File::getName).collect(Collectors.toList());
        // Arrays.stream(caricatureFile.listFiles()).filter(File::isDirectory).map(File::getName).forEach(System.out::print);
        List<Caricature> hottest = caricatureService.selectAll().stream().sorted(Comparator.comparing(Caricature::getStar)).limit(5).collect(Collectors.toList());
        model.addAttribute(caricature);
        model.addAttribute("caricatureFileNames", caricatureFileNames);
        model.addAttribute("hottestCaricature", hottest);
        return "cp/info";
        // return name+id;
    }
    @GetMapping("/global/caricature/List/{level}")
    public String visitCaricatureByLevel(@RequestParam("caricatureId") Long id, @PathVariable("level") String level, Model model) throws FileNotFoundException {
        Caricature caricature = caricatureService.getCaricature(id);
        String currentLevel = level;
        if ("first".equals(currentLevel) || "last".equals(currentLevel)){
            // model.addAttribute("name", caricature.getName());
            // model.addAttribute("caricatureId", caricature.getId());
            try {
                return "redirect:/visit/global/caricature/upload/" + java.net.URLEncoder.encode(caricature.getName(), "UTF-8") + "?caricatureId=" + caricature.getId();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        File path = new File(ResourceUtils.getURL("classpath:static").getPath());

        Map<String, String> context = new HashMap<>(5);
        context.put("level", currentLevel);
        File caricatureList = new File(path, caricature.getPath());
        File[] arrays = caricatureList.listFiles();
        for(int i=0;i<arrays.length;i++){
            if(arrays[i].isDirectory() && arrays[i].getName().equals(currentLevel)){
                String prevChapter = Optional.ofNullable(i-1<0?null: arrays[i-1]).filter(File::isDirectory).map(File::getName).orElse("first");
                String nextChapter = Optional.ofNullable(i+1>=arrays.length?null: arrays[i+1]).filter(File::isDirectory).map(File::getName).orElse("last");
                context.put("prevChapter", prevChapter);
                context.put("nextChapter", nextChapter);
            }
        }
        File caricatureFile = new File(path, MessageFormat.format("{0}/{1}", caricature.getPath(), currentLevel));
        List<String> carcatureLevelList = Arrays.stream(caricatureFile.listFiles(File::isFile)).map(c ->MessageFormat.format("{0}/{1}/{2}", caricature.getPath(), currentLevel, c.getName())).collect(Collectors.toList());
        model.addAttribute(caricature);
        model.addAttribute("carcatureLevelList", carcatureLevelList);
        model.addAttribute("context", context);
        return "cp/gbook";
    }

    @GetMapping("/global/caricature/search")
    public String searchCaricature(@RequestParam("keyword") String keyword, RedirectAttributes model){
        System.out.println(keyword);
        Set<Caricature> caricatures = caricatureService.selectAll().stream().filter(caricature -> {if(caricature.getName().contains(keyword)){
            return true;
        } else {
            return Optional.ofNullable(caricature.getAuthor()).map(author->author.contains(keyword)).orElse(false);
        }
        }).collect(Collectors.toSet());
        model.addFlashAttribute("caricatures", caricatures);
        return "redirect:/visit/group";
    }
}
