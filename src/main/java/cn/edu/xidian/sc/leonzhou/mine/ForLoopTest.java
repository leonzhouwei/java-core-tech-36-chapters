package cn.edu.xidian.sc.leonzhou.mine;

import org.apache.commons.collections4.list.TreeList;

/**
 * @author Wei Zhou
 */
public class ForLoopTest {

    public static void main(String[] args) {
        for (int i = 0; i < 10; ++i) {
            System.out.println("loop#1, i = " + i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("loop#2, i = " + i);
        }

        TreeList<Integer> treeList = new TreeList<Integer>();
    }

}
