package com.ihorshulha.asyncapidatamanager.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Component
public class QueueClient {

    protected BlockingQueue<String> companyQueue = new LinkedBlockingQueue<>();


    AtomicBoolean flag = new AtomicBoolean(true);

    public void putToQueue() {
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 200; i++) {
                try {
                    companyQueue.put(String.valueOf(i));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            flag.set(false);
        }).join();
    }

    public String takeUrl() {
        String url = "";
        while (flag.get() || !companyQueue.isEmpty()) {
            try {
                url = companyQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return url;
    }
}
