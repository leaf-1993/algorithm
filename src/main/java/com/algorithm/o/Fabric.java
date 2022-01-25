package com.algorithm.o;

/**
 * 斐波拉契数据
 * 0 1 1 2 3 5
 */
public class Fabric {

    /**
     * 时间复杂度 O(2^n)
     *
     * @param n 第几个数
     * @return 位置n对应的数
     * 时间复杂度很高
     */
    public static int fab1(int n) {
        if (n <= 2) {
            return n - 1;
        }
        return fab1(n - 2) + fab1(n - 1);
    }

    /**
     * 时间复杂度 O(n)
     *
     * @param n 位置
     * @return 位置的值
     */
    public static long fab2(long n) {
        if (n < 2) {
            return n - 1;
        }
        long first = 0;
        long second = 1;
        for (int i = 3; i <= n; i++) {
            second = first + second;
            first = second - first;
        }
        return second;
    }

    public static void main(String[] args) {
//        System.out.println(fab1(50));
        System.out.println(Long.MAX_VALUE);
        System.out.println(fab2(56));
    }
}

