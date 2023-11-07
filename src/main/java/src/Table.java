package src;

import java.util.concurrent.CountDownLatch;

public class Table extends Thread {

    private static final int QUANTITY = 5;
    private static final Philosopher[] PHILOSOPHERS = new Philosopher[QUANTITY];
    private static final String[] NAMES = new String[]{
            "Иван",
            "Максим",
            "Михаил",
            "Дмитрий",
            "Кирилл"};
    private Object[] forks;
    private CountDownLatch cdl;

    Table(){
        cdl = new CountDownLatch(QUANTITY);
        forks = new Object[QUANTITY];

        for (int i = 0; i < QUANTITY; i++) {
            forks[i] = new Object();
        }
    }

    @Override
    public void run() {
        try {
            goAll();
            cdl.await();
            System.out.println("На столе не осталось еды");;
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void goAll(){
        for (int i = 0; i < QUANTITY; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];

            if (i == QUANTITY - 1) {
                PHILOSOPHERS[i] = new Philosopher(rightFork, leftFork, NAMES[i] + " (" + (i + 1) + ")", cdl);
            } else {
                PHILOSOPHERS[i] = new Philosopher(leftFork, rightFork, NAMES[i] + " (" + (i + 1) + ")", cdl);
            }

            PHILOSOPHERS[i].start();
        }
    }

}
