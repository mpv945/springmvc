package org.haijun.study;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class Test {

    public static void main(String[] args) {
        String simpleHash = new SimpleHash("SHA-256", "12345", "eteokues",1).toString();
        //String simpleHash = new Sha256Hash( "12345", "eteokues",1).toString();
        System.out.println(simpleHash);
    }
}
