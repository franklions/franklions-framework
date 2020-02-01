package com.franklions.example.filter;

import java.io.PrintWriter;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/1/22
 * @since Jdk 1.8
 */
public class TeePrintWriter extends PrintWriter {
    PrintWriter branch;

    public TeePrintWriter(PrintWriter main, PrintWriter branch) {
        super(main, true);
        this.branch = branch;
    }

    @Override
    public void write(char[] buf, int off, int len) {
        super.write(buf, off, len);
        super.flush();
        this.branch.write(buf, off, len);
        this.branch.flush();
    }

    @Override
    public void write(String s, int off, int len) {
        super.write(s, off, len);
        super.flush();
        this.branch.write(s, off, len);
        this.branch.flush();
    }

    @Override
    public void write(int c) {
        super.write(c);
        super.flush();
        this.branch.write(c);
        this.branch.flush();
    }

    @Override
    public void flush() {
        super.flush();
        this.branch.flush();
    }
}