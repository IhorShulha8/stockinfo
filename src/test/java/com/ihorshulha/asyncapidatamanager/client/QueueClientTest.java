package com.ihorshulha.asyncapidatamanager.client;

import com.ihorshulha.asyncapidatamanager.BaseAbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class QueueClientTest extends BaseAbstractTest {

    private final String task = "task";

    @BeforeTestExecution
    public void setUp() {
        queueClient.companyQueue.clear();
    }

    @Test
    void whenPutToQueueSuccessful() {
        queueClient.putToQueue(task);
        assertTrue(queueClient.getCompanyQueue().contains(task));
    }

    @Test
    void whenPutNullTaskFailed() throws RuntimeException {
        assertThatThrownBy(() -> queueClient.putToQueue(null))
                .hasCauseExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    void whenTakeUrlSuccessful() {
        queueClient.putToQueue(task);
        String actual = queueClient.takeUrl();
        assertEquals(actual, task);
    }
}