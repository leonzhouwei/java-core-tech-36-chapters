package cn.edu.xidian.sc.leonzhou.mine.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

public class GuavaRateLimiterSample {

    public static void main(String[] args) throws InterruptedException {
        final double permitsPerSecond = 5;
        //RateLimiter limiter = RateLimiter.create(permitsPerSecond);
        RateLimiter limiter = RateLimiter.create(permitsPerSecond, 1, TimeUnit.MILLISECONDS);
        for (int i = 0; i < 2 * permitsPerSecond; i++) {
            System.out.println(limiter.acquire());
        }
        Thread.sleep(1000L);
        for (int i = 0; i < 2 * permitsPerSecond; i++) {
            System.out.println(limiter.acquire());
        }
    }

}
