package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class SimpleCountDownLatchTest {

    private SimpleCountDownLatch latch;

    @BeforeEach
    void setUp() {
        latch = new SimpleCountDownLatch(3);
    }

    @Test
    void testInitialCount() {
        // then
        assertEquals(3, latch.getCount());
    }

    @Test
    void testCountDown() {
        // then
        latch.countDown();
        assertEquals(2, latch.getCount());

        // and
        latch.countDown();
        assertEquals(1, latch.getCount());

        // and
        latch.countDown();
        assertEquals(0, latch.getCount());

        // and
        // count should not go below 0
        latch.countDown();
        assertEquals(0, latch.getCount());
    }

    @Test
    void testAwait() throws InterruptedException {
        // given
        Thread thread = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                fail("Thread was interrupted");
            }
        });

        // when
        thread.start();
        Thread.sleep(100); // Ensure the thread has started and is waiting

        // and
        latch.countDown();
        latch.countDown();
        latch.countDown();

        // and
        thread.join(100); // Wait for the thread to finish

        // then
        assertFalse(thread.isAlive(), "Thread should have finished waiting");
    }

    @Test
    void testAwaitWhenCountIsZero() throws InterruptedException {
        // given
        SimpleCountDownLatch zeroLatch = new SimpleCountDownLatch(0);

        // then
        zeroLatch.await(); // Should return immediately without blocking
    }

    @Test
    void testNegativeInitialCount() {
        // then
        assertThrows(IllegalArgumentException.class, () -> new SimpleCountDownLatch(-1));
    }

    @Test
    void testConcurrentCountDown() throws InterruptedException {
        // given
        Thread thread1 = new Thread(() -> latch.countDown());
        Thread thread2 = new Thread(() -> latch.countDown());
        Thread thread3 = new Thread(() -> latch.countDown());

        // when
        thread1.start();
        thread2.start();
        thread3.start();

        // and
        thread1.join();
        thread2.join();
        thread3.join();

        // then
        assertEquals(0, latch.getCount());
    }
}
