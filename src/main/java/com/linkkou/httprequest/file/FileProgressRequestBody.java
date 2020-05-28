package com.linkkou.httprequest.file;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传进度监听
 *
 * @author lk
 * @version 1.0
 * @date 2019/4/9 14:09
 */
public class FileProgressRequestBody extends RequestBody {

    public interface ProgressListener {
        /**
         * 进度返回
         *
         * @param progressSize 已上传大小
         * @param fileSize     文件大小
         */
        void transferred(long progressSize, long fileSize);
    }

    /**
     * okio.Segment.SIZE
     * 以2KB为单位上传
     */
    private static final int SEGMENT_SIZE = 2 * 1024;

    /**
     * 文件
     */
    private File file;
    /**
     * 流
     */
    private byte[] content;
    /**
     * 流 长度
     */
    private long contentLength;
    /**
     * 监听结果
     */
    private ProgressListener listener;
    /**
     * 文件类型
     */
    private MediaType contentType;

    public FileProgressRequestBody(final File file, final String contentType, final ProgressListener listener) {
        this.file = file;
        this.contentType = MediaType.parse(contentType);
        this.listener = listener;
    }

    public FileProgressRequestBody(final byte[] content, final MediaType contentType, final ProgressListener listener) {
        this.content = content;
        this.contentLength = content.length;
        this.contentType = contentType;
        this.listener = listener;
    }

    protected FileProgressRequestBody() {
    }

    @Override
    public long contentLength() {
        return file.length();
    }

    @Override
    public MediaType contentType() {
        return contentType;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (this.file != null) {
            Source source = null;
            final long filelength = file.length();
            try {
                source = Okio.source(file);
                long total = 0;
                long read;
                while ((read = source.read(sink.buffer(), SEGMENT_SIZE)) != -1) {
                    total += read;
                    sink.flush();
                    this.listener.transferred(total, filelength);
                }
            } finally {
                Util.closeQuietly(source);
            }
        } else if (this.content != null) {
            Util.checkOffsetAndCount(content.length, 0, this.contentLength);
            sink.write(content, 0, content.length);
        } else {
            throw new NullPointerException("content == null");
        }
    }

}
