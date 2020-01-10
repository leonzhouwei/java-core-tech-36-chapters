package cn.edu.xidian.sc.leonzhou.chap19;

import cn.edu.xidian.sc.leonzhou.DateTimeUtil;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class LatchSample {

    public static void main(String[] args) throws InterruptedException {
        final int count = 5;
        CountDownLatch latch = new CountDownLatch(count + 1);

        for (int i = 0; i < count; ++i) {
            Thread t = new Thread(new FirstBatchWorker(latch));
            t.start();
        }

        for (int i = 0; i < count; ++i) {
            Thread t = new Thread(new SecondBatchWorker(latch));
            t.start();
        }

        while (latch.getCount() != 1) {
            Thread.sleep(100L);
        }
        System.out.println("waiting for first batch finish");
        latch.countDown();
    }

    private static class FirstBatchWorker implements Runnable {

        private final CountDownLatch latch;

        FirstBatchWorker(CountDownLatch latch) {
            this.latch = latch;
        }


        @Override
        public void run() {
            System.out.println(DateTimeUtil.toString(new Date()) + this.getClass().getSimpleName());
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
    }

    private static class SecondBatchWorker implements Runnable {

        private final CountDownLatch latch;

        SecondBatchWorker(CountDownLatch latch) {
            this.latch = latch;
        }


        @Override
        public void run() {
            try {
                latch.await();
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(DateTimeUtil.toString(new Date()) + this.getClass().getSimpleName());
        }
    }

}
