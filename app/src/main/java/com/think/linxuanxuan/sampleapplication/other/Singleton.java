package com.think.linxuanxuan.sampleapplication.other;


public class Singleton {

    private volatile static Singleton singleton;

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    /**
     * synchronized在应用程序频繁的访问下会造成性能的下降
     */
//    public synchronized static Singleton getInstance() {
//        if (singleton == null) {
//            singleton = new Singleton();
//        }
//        return singleton;
//    }

    /**
     * 多线程环境下会出错
     * @return
     */
//    public static Singleton getInstance() {
//        if (singleton == null) {
//            singleton = new Singleton();
//        }
//        return singleton;
//    }
}
