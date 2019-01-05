package com.zui.vorite.tools;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

/**
 * @file: ExtractZip.class
 * @author: Dusk
 * @since: 2018/12/16 19:41
 * @desc: 解压zip文件
 */

@Component
public class ExtractZip {

    private List<String> unZip(File zipFile, String destDir){

        List<String> fileNames = new ArrayList<>();

        try(ZipArchiveInputStream is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE));){
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null){
                fileNames.add(entry.getName());

                if(entry.isDirectory()){
                    File directory = new File(destDir, entry.getName());
                    directory.mkdirs();
                }else {
                    try(OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(destDir, entry.getName())), BUFFER_SIZE);){
                        IOUtils.copy(is, os);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileNames;
    }

    /**
     * 压的文件，解压后路径
     * @param files
     * @return
     */
    public List<List<String>> unZip(Map<String, String> files){
        // String zipfile, String destDir
        Map<String, String> fileMap = files;
        List<List<String>> list = fileMap.entrySet().parallelStream().map(f->unZip(new File(f.getKey()), f.getValue())).collect(Collectors.toList());
        return list;
    }
}
