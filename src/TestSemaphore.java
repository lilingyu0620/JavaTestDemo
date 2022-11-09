import java.util.concurrent.Semaphore;

/**
 * @Description
 * @Author lly
 * @Create 2022-11-09 4:05 PM
 */
public class TestSemaphore {
    private static Integer count = 0;
    private static final Integer FULL = 10;
    final Semaphore notFull = new Semaphore(FULL);
    // 需要生产者先release一个信号量 所以这里初始值为0
    final Semaphore notEmpty = new Semaphore(0);
    final Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) {
        TestSemaphore test = new TestSemaphore();
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

                try {
                    notFull.acquire();

                    mutex.acquire();
                    count++;

                    System.out.println(Thread.currentThread().getName()
                            + "生产者生产，目前总共有" + count);

                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    mutex.release();
                    notEmpty.release();
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

                try {
                    notEmpty.acquire();
                    mutex.acquire();
                    count--;
                    System.out.println(Thread.currentThread().getName()
                            + "消费者消费，目前总共有" + count);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    mutex.release();
                    notFull.release();
                }

            }

        }
    }

}
