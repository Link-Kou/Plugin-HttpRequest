package com.linkkou.httprequest.file;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 构建文件上传
 *
 * @author lk
 * @date 2018/9/23 11:28
 */
public class MultipartFlieBuild {


    private static MediaTypeList mediaTypeList = new MediaTypeList();

    /**
     * Spring-获取文件流-上传
     *
     * @param multipartFile 文件
     */
    public static MultipartBody.Part get(MultipartFile multipartFile) {
        return get(multipartFile, "file");
    }

    /**
     * Spring-获取文件流-上传
     *
     * @param multipartFile 文件
     */
    public static MultipartBody.Part get(MultipartFile multipartFile, String key) {
        byte[] bytes = new byte[0];
        try {
            bytes = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(multipartFile.getContentType()), bytes);
        MultipartBody.Part part = MultipartBody.Part.createFormData(multipartFile.getName(), multipartFile.getOriginalFilename(), requestBody);
        return part;
    }

    /**
     * Spring-获取文件流-上传-获取上传进度
     *
     * @param multipartFile
     */
    public static MultipartBody.Part get(MultipartFile multipartFile, String key, FileProgressRequestBody.ProgressListener progressListener) {
        byte[] bytes = new byte[0];
        try {
            bytes = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileProgressRequestBody fileProgressRequestBody = new FileProgressRequestBody(
                bytes,
                okhttp3.MediaType.parse(multipartFile.getContentType()),
                progressListener
        );
        MultipartBody.Part part = MultipartBody.Part.createFormData(multipartFile.getName(), multipartFile.getOriginalFilename(), fileProgressRequestBody);
        return part;
    }

    //region 普通文件上传

    /**
     * 普通文件上传
     *
     * @param file 文件
     * @return
     */
    public static RequestBody getRequestBody(File file) {
        return RequestBody.create(
                //说明文件类型
                //MediaType.parse(mediaTypeList.getType(file)),
                MediaType.parse("multipart/form-data"),
                file
        );
    }

    /**
     * 普通文件上传
     *
     * @param file 文件
     * @return
     */
    public static MultipartBody.Part get(File file) {
        return get(file, "file");
    }

    /**
     * 普通文件上传
     *
     * @param file 文件
     * @return
     */
    public static MultipartBody.Part get(File file, FileProgressRequestBody.ProgressListener progressListener) {
        return get(file, "file", progressListener);
    }

    /**
     * 普通文件上传 - 构建Okhttp指定上传对象
     *
     * @param file 文件
     * @return
     */
    public static MultipartBody.Part get(File file, String key) {
        RequestBody requestFile = RequestBody.create(
                //说明文件类型
                //MediaType.parse(mediaTypeList.getType(file)),
                MediaType.parse("multipart/form-data"),
                file
        );
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        return MultipartBody.Part.createFormData(key == null ? "file" : key, file.getName(), requestFile);
    }

    /**
     * 普通文件上传 - 上传进度监听
     *
     * @param file             文件
     * @param key              名称
     * @param progressListener 监听上传
     * @return
     */
    public static MultipartBody.Part get(File file, String key, FileProgressRequestBody.ProgressListener progressListener) {
        FileProgressRequestBody fileProgressRequestBody = new FileProgressRequestBody(file, mediaTypeList.getType(file), progressListener);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        return MultipartBody.Part.createFormData(key == null ? "file" : key, file.getName(), fileProgressRequestBody);
    }

    //endregion

}
