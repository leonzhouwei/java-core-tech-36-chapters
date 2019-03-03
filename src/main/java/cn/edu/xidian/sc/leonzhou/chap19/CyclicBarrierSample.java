package cn.edu.xidian.sc.leonzhou.chap19;

import cn.edu.xidian.sc.leonzhou.DateTimeUtil;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierSample {

    public static void main(String[] args) throws InterruptedException {
        final int count = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, new MyBarrierAction());
        List<Thread> threads = Lists.newArrayList();

        for (int i = 0; i < count; ++i) {
            Thread t = new Thread(new FirstBatchWorker(cyclicBarrier));
            t.start();
            threads.add(t);
        }

        for (int i = 0; i < count; ++i) {
            Thread t = new Thread(new SecondBatchWorker(cyclicBarrier));
            t.start();
            threads.add(t);
        }

        for (Thread e : threads) {
            e.join();
        }
        System.out.println("done");
    }

    private static class MyBarrierAction implements Runnable {

        @Override
        public void run() {
            System.out.println(this.getClass().getSimpleName());
        }
    }

    private static class FirstBatchWorker implements Runnable {

        private final CyclicBarrier cyclicBarrier;

        FirstBatchWorker(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }


        @Override
        public void run() {
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(DateTimeUtil.toString(new Date()) + this.getClass().getSimpleName());
        }
    }

    private static class SecondBatchWorker implements Runnable {

        private final CyclicBarrier cyclicBarrier;

        SecondBatchWorker(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }


        @Override
        public void run() {
            try {
                cyclicBarrier.await();
                Thread.sleep(5000L);
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(DateTimeUtil.toString(new Date()) + this.getClass().getSimpleName());
        }
    }
}
