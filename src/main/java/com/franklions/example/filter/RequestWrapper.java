package com.franklions.example.filter;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/1/22
 * @since Jdk 1.8
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;
    private BufferedReader reader;
    private ServletInputStream inputStream;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.loadBody(request);
    }

    private void loadBody(HttpServletRequest request) throws IOException {
        this.body = IOUtils.toByteArray(request.getInputStream());
        this.inputStream = new RequestWrapper.RequestCachingInputStream(this.body);
    }

    public byte[] getBody() {
        return this.body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.inputStream != null ? this.inputStream : super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (this.reader == null) {
            this.reader = new BufferedReader(new InputStreamReader(this.inputStream, this.getCharacterEncoding()));
        }

        return this.reader;
    }

    private static class RequestCachingInputStream extends ServletInputStream {
        private final ByteArrayInputStream inputStream;

        RequestCachingInputStream(byte[] bytes) {
            this.inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() {
            return this.inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return this.inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {
        }
    }
}