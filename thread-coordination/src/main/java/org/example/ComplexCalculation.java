package org.example;

import java.math.BigInteger;

public class ComplexCalculation {

    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        PowerCalculatingThread thread1 = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread thread2 = new PowerCalculatingThread(base2, power2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return thread1.getResult().add(thread2.getResult());
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private final BigInteger base;
        private final BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            result = base.pow(power.intValue());
        }

        public BigInteger getResult() {
            return result;
        }
    }

    public static void main(String[] args) {
        ComplexCalculation calculation = new ComplexCalculation();
        BigInteger base1 = new BigInteger("10");
        BigInteger power1 = new BigInteger("2");
        BigInteger base2 = new BigInteger("5");
        BigInteger power2 = new BigInteger("3");

        BigInteger result = calculation.calculateResult(base1, power1, base2, power2);
        System.out.println("Result: " + result); // Expected output: 100 + 125 = 225
    }
}
