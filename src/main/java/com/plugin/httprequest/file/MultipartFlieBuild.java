package com.plugin.httprequest.file;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author lk
 * @date 2018/9/23 11:28
 */
public class MultipartFlieBuild {


    private static MediaTypeList mediaTypeList = new MediaTypeList();

    /**
     * Spring
     * @param multipartFile
     */
    public static MultipartBody.Part get(MultipartFile multipartFile){
        return get(multipartFile,"file");
    }

    /**
     * Spring
     * @param multipartFile
     */
    public static MultipartBody.Part get(MultipartFile multipartFile,String key){
        return springMultipartFileToMultipartBody(multipartFile,key == null ? "file" : key );
    }

    /**
     * 普通文件上传
     * @param file
     * @return
     */
    public static MultipartBody.Part get(File file) {
       return get(file,"file");
    }

    /**
     * 普通文件上传
     * @param file
     * @return
     */
    public static MultipartBody.Part get(File file,String key) {
        RequestBody requestFile = RequestBody.create(
                //说明文件类型
                MediaType.parse(mediaTypeList.getType(file)),
                file
        );
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        return MultipartBody.Part.createFormData(key == null ? "file" : key, file.getName(), requestFile);
    }


    private static MultipartBody.Part springMultipartFileToMultipartBody(MultipartFile file,String key){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(file.getContentType()),bytes);
        builder.addFormDataPart(key, file.getOriginalFilename(), requestBody);
        builder.setType(MultipartBody.FORM);
        MultipartBody.Part part = MultipartBody.Part.createFormData(file.getName(), file.getOriginalFilename(), requestBody);
        return part;
    }

}
