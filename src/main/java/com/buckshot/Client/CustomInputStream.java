package com.buckshot.Client;

import java.io.*;

public class CustomInputStream extends InputStream {
    private final InputStream original;
    private volatile boolean enabled;

    public CustomInputStream(InputStream original) {
        this.original = original;
        this.enabled = false;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int read() throws IOException {
        while (!enabled) {
            // 입력이 비활성화된 동안에는 무한 대기
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted while waiting for input to be enabled", e);
            }
        }
        return original.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        while (!enabled) {
            // 입력이 비활성화된 동안에는 무한 대기
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted while waiting for input to be enabled", e);
            }
        }
        return original.read(b, off, len);
    }
}

