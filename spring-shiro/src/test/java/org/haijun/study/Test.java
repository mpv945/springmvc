package org.haijun.study;

import org.apache.shiro.crypto.hash.SimpleHash;

public class Test {

    public static void main(String[] args) {
        String simpleHash = new SimpleHash("MD5", "12345", "eteokues",1).toString();
        System.out.println(simpleHash);
    }
}
