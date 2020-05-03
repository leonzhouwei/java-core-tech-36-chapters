package cn.edu.xidian.sc.leonzhou.chap10;

import java.util.concurrent.ConcurrentHashMap;

public class ConcHashMapDemo {

    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        map.put(1, 100);
    }

}
