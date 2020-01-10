package cn.edu.xidian.sc.leonzhou.mine.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class GuavaCacheSample {

    private static final long EXPIRE_SECONDS = 1L;

    private static final long EXPIRE_MILLIS = EXPIRE_SECONDS * 1000L;

    private static Cache<String, Integer> cache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(EXPIRE_SECONDS, TimeUnit.SECONDS).build();

    public static void main(String[] args) throws InterruptedException {
        String key = "foo";
        cache.put(key, 1);
        System.out.println(cache.getIfPresent(key));

        //
        Thread.sleep(EXPIRE_MILLIS);
        System.out.println(cache.getIfPresent(key));
    }

}
