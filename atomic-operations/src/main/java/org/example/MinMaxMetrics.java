package org.example;

public class MinMaxMetrics {

    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;
    private final Object minLock = new Object();
    private final Object maxLock = new Object();

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        // Initial values already set for min and max
    }

    /**
     * Adds a new sample to our metrics.
     */
    public void addSample(long newSample) {
        synchronized (minLock) {
            if (newSample < min) {
                min = newSample;
            }
        }

        synchronized (maxLock) {
            if (newSample > max) {
                max = newSample;
            }
        }
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        synchronized (minLock) {
            return min;
        }
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        synchronized (maxLock) {
            return max;
        }
    }

    public static void main(String[] args) {
        MinMaxMetrics metrics = new MinMaxMetrics();
        metrics.addSample(10);
        metrics.addSample(5);
        metrics.addSample(20);

        System.out.println("Min: " + metrics.getMin()); // Should print 5
        System.out.println("Max: " + metrics.getMax()); // Should print 20
    }
}
