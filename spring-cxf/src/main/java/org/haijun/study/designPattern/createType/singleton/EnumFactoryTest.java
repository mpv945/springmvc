package org.haijun.study.designPattern.createType.singleton;

public class EnumFactoryTest  extends Thread {

    @Override
    public void run() {
        System.out.println(EnumFactory.getInstance().hashCode());
    }

    public static void main(String[] args) {

        EnumFactoryTest[] mts = new EnumFactoryTest[10];
        for(int i = 0 ; i < mts.length ; i++){
            mts[i] = new EnumFactoryTest();
        }

        for (int j = 0; j < mts.length; j++) {
            mts[j].start();
        }
    }

}
