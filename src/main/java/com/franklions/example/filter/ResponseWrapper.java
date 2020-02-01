package com.franklions.example.filter;

import org.apache.commons.io.output.TeeOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/1/22
 * @since Jdk 1.8
 */
public class ResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private PrintWriter writer;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        this.writer = new PrintWriter(new OutputStreamWriter(this.bos, StandardCharsets.UTF_8), true);
    }

    @Override
    public ServletResponse getResponse() {
        return this;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            private TeeOutputStream tee;

            {
                this.tee = new TeeOutputStream(ResponseWrapper.super.getOutputStream(), ResponseWrapper.this.bos);
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }

            @Override
            public void write(int b) throws IOException {
                this.tee.write(b);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new TeePrintWriter(super.getWriter(), this.writer);
    }

    public byte[] toByteArray() {
        return this.bos.toByteArray();
    }
}
