package src;

import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {
    private Object leftFork;
    private Object rightFork;
    private int eat = 3;
    private String name;
    private CountDownLatch cdl;

    public Philosopher(Object leftFork, Object rightFork, String name, CountDownLatch cdl) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.name = name;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try {
            goEat();
            cdl.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void goEat() throws InterruptedException {
        while (true) {
            if (eat != 0) {
                doAction(": Размышляет");
                synchronized (leftFork) {
                    doAction(": Поднял левую вилку");
                    synchronized (rightFork) {
                        doAction(": Поднял правую вилку и начал " + (4 - eat) +  " - раз есть");
                        doAction(": Положил правую вилку");
                        eat--;
                    }
                    doAction(": Положил левую вилку");
                }
            }
            else
            {
                doAction(": Закончил есть и начал размышлять");
                break;
            }
        }
    }

    private void doAction(String action) throws InterruptedException {
        System.out.printf("%11s %s\n", name, action);
        Thread.sleep(((int) (Math.random() * 500)));
    }
}
