package com.zui.vorite.tools;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

/**
 * @file: ExtractZip.class
 * @author: Dusk
 * @since: 2018/12/16 19:41
 * @desc: 解压zip文件
 */

@Component
public class ExtractZip {

    public static List<String> unZip(File zipFile, String destDir){

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

    public List<String> unZip(String zipfile, String destDir){
        File zipFile = new File(zipfile);
        return unZip(zipFile, destDir);
    }
}
