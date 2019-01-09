package cn.edu.xidian.sc.leonzhou.chap17;

/**
 * @author Wei Zhou
 */
public class HelloWorld {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < Integer.MAX_VALUE; ++i) {
            System.out.println("Hello, world!");
            Thread.sleep(3000L);
        }
    }

}
