package com.viorelia.p2p;

public class DeadlockDemo {

    static final Object LOCK_A = new Object();
    static final Object LOCK_B = new Object();

    public static void run() {
        Thread t1 = new Thread(() -> {
            System.out.println("[T1] trying to lock A");
            synchronized (LOCK_A) {
                System.out.println("[T1] locked A");
                sleep();
                System.out.println("[T1] trying to lock B");
                synchronized (LOCK_B) {
                    System.out.println("[T1] locked B");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            System.out.println("[T2] trying to lock B");
            synchronized (LOCK_B) {
                System.out.println("[T2] locked B");
                sleep();
                System.out.println("[T2] trying to lock A");
                synchronized (LOCK_A) {
                    System.out.println("[T2] locked A");
                }
            }
        });

        t1.start();
        t2.start();

        System.out.println("[deadlock-demo] Threads started -> deadlock expected");
    }

    static void sleep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException ignored) {}
    }
}
