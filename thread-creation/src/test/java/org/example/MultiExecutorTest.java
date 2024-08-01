package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MultiExecutorTest {

    private MultiExecutor multiExecutor;
    private Runnable task1;
    private Runnable task2;
    private Runnable task3;

    @BeforeEach
    void setUp() {
        task1 = mock(Runnable.class);
        task2 = mock(Runnable.class);
        task3 = mock(Runnable.class);

        List<Runnable> tasks = Arrays.asList(task1, task2, task3);
        multiExecutor = new MultiExecutor(tasks);
    }

    @Test
    void testExecuteAll() throws InterruptedException {
        // given
        CountDownLatch latch = new CountDownLatch(3);

        // when
        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(task1).run();

        // and
        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(task2).run();

        // and
        doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(task3).run();

        // when
        multiExecutor.executeAll();

        // and
        boolean allTasksStarted = latch.await(2, TimeUnit.SECONDS);

        // then
        assertTrue(allTasksStarted, "Not all tasks started in time");

        // and
        verify(task1, times(1)).run();
        verify(task2, times(1)).run();
        verify(task3, times(1)).run();
    }
}
