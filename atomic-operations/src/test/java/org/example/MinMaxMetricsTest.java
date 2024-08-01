package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinMaxMetricsTest {

    private MinMaxMetrics metrics;

    @BeforeEach
    void setUp() {
        metrics = new MinMaxMetrics();
    }

    @Test
    void testAddSampleSingleValue() {
        // when
        metrics.addSample(10);

        // then
        assertEquals(10, metrics.getMin());
        assertEquals(10, metrics.getMax());
    }

    @Test
    void testAddSampleMultipleValues() {
        // when
        metrics.addSample(10);
        metrics.addSample(5);
        metrics.addSample(20);

        // then
        assertEquals(5, metrics.getMin());
        assertEquals(20, metrics.getMax());
    }

    @Test
    void testAddSampleAllSameValues() {
        // when
        metrics.addSample(10);
        metrics.addSample(10);
        metrics.addSample(10);

        // then
        assertEquals(10, metrics.getMin());
        assertEquals(10, metrics.getMax());
    }

    @Test
    void testGetMinInitialValue() {
        // then
        assertEquals(Long.MAX_VALUE, metrics.getMin());
    }

    @Test
    void testGetMaxInitialValue() {
        // then
        assertEquals(Long.MIN_VALUE, metrics.getMax());
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        // given
        Runnable task1 = () -> {
            for (int i = 0; i < 1000; i++) {
                metrics.addSample(i);
            }
        };

        // and
        Runnable task2 = () -> {
            for (int i = 1000; i > 0; i--) {
                metrics.addSample(i);
            }
        };

        // when
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        // and
        thread1.start();
        thread2.start();

        // and
        thread1.join();
        thread2.join();

        // then
        assertEquals(0, metrics.getMin());
        assertEquals(1000, metrics.getMax());
    }
}
