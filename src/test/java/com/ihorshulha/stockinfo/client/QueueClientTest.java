package com.ihorshulha.stockinfo.client;

import com.ihorshulha.stockinfo.BaseAbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import static org.junit.jupiter.api.Assertions.*;

class QueueClientTest extends BaseAbstractTest {

    private final String task = "task";

    @BeforeTestExecution
    public void setUp() {
        queueClient.getTaskQueue().clear();
    }

    @Test
    void whenPutToQueueSuccessful() {
        queueClient.putToQueue(task);
        assertTrue(queueClient.getTaskQueue().contains(task));
    }

    @Test
    void whenTakeUrlSuccessful() {
        queueClient.putToQueue(task);
        String actual = queueClient.takeUrl();
        assertEquals(actual, task);
    }
}