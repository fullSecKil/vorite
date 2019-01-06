package com.zui.vorite.tools;

/**
 * @file: FileUpload.class
 * @author: Dusk
 * @since: 2018/12/16 12:44
 * @desc:
 */


import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件上传的接口
 * @author Dusk
 */
public interface FileUpload {
    /**
     * 上传
     */
    void upload(MultipartFile file, File fileDir);
}
