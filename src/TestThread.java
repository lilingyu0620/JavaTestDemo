/**
 * @Description
 * @Author lly
 * @Create 2022-11-09 10:53 AM
 */
public class TestThread {

    private static Integer count = 0;
    private static final Integer FULL = 10;
    private static String LOCK = "lock";

    public static void main(String[] args) {
        System.out.println("lly test");
        TestThread test = new TestThread();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
    }

    class Producer implements Runnable {

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (LOCK) {

                    while (count == FULL) {
                        try {
                            LOCK.wait();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    LOCK.notifyAll();
                    System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + count);

                }

            }

        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (LOCK) {

                    while (count == 0) {
                        try {
                            LOCK.wait();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    LOCK.notifyAll();
                    System.out.println(Thread.currentThread().getName() + "消费者消费，目前总共有" + count);

                }

            }

        }
    }

}
