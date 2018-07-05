package org.haijun.study.designPattern.createType.singleton.staticBlock;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 使用静态内置类实现单例模式（推介）
 */
public class MySingleton implements Serializable {//实现Serializable接口，解决反序列化后得到的实例和序列化前的是同一个实例

    private static final long serialVersionUID = 1L;

    //内部类
    private static class MySingletonHandler {
        private static MySingleton instance = new MySingleton();
    }

    private MySingleton() {
    }

    public static MySingleton getInstance() {
        return MySingletonHandler.instance;
    }

    //该方法在反序列化时会被调用，该方法不是接口定义的方法，有点儿约定俗成的感觉
    protected Object readResolve() throws ObjectStreamException {
        System.out.println("调用了readResolve方法！");
        return MySingletonHandler.instance;
    }
}

