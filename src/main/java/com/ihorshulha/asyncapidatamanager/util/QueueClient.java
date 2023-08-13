package com.ihorshulha.asyncapidatamanager.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
@Slf4j
@Component
public class QueueClient {

    protected BlockingQueue<String> companyQueue = new LinkedBlockingQueue<>();

    public void putToQueue(String task) {
        CompletableFuture.runAsync(() -> {
            try {
                companyQueue.put(String.valueOf(task));
                log.debug("Queue client put task: {}", task);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }).join();
    }

    public String takeUrl() {
        String url = "";
        try {
            url = companyQueue.take();
            log.debug("Queue client took task: {}", url);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return url;
    }
}
