package cn.edu.xidian.sc.leonzhou.chap01;

public class StringDemo {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        String str = null;
        sb.append(str);
        System.out.println(sb.toString());

        String result = sb.toString();
        boolean isNull = (result == null);
        System.out.println(isNull);
    }

}
