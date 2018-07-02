package org.haijun.study.designPattern.createType.singleton;

/**
 * 使用枚举数据类型实现单例模式
 * 枚举enum和静态代码块的特性相似，在使用枚举时，构造方法会被自动调用，利用这一特性也可以实现单例
 */
public class EnumFactory{

    private enum MyEnumSingleton{
        singletonFactory;

        private MySingleton instance;

        private MyEnumSingleton(){//枚举类的构造方法在类加载是被实例化
            instance = new MySingleton();
        }

        public MySingleton getInstance(){
            return instance;
        }
    }

    public static MySingleton getInstance(){
        return MyEnumSingleton.singletonFactory.getInstance();
    }
}

class MySingleton{//需要获实现单例的类，比如数据库连接Connection
    public MySingleton(){}
}
