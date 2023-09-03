package com.ihorshulha.stockinfo.client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
@Slf4j
@Component
public class QueueClient {

    private final BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>();

    public void putToQueue(String task) {
        try {
            taskQueue.put(task);
            log.debug("Queue client put task: {}", task);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String takeUrl() {
        String url;
        try {
            url = taskQueue.take();
            log.debug("Queue client took task: {}", url);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return url;
    }
}
