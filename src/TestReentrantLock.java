import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author lly
 * @Create 2022-11-09 2:09 PM
 */
public class TestReentrantLock {

    private static Integer count = 0;
    private static final Integer FULL = 10;

    ReentrantLock lock = new ReentrantLock();

    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();

    public static void main(String[] args) {
        TestReentrantLock text1 = new TestReentrantLock();
        new Thread(text1.new Producer()).start();
        new Thread(text1.new Consumer()).start();
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

                lock.lock();

                try {

                    while (count == FULL) {

                        try {
                            notFull.await();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    count++;
                    System.out.println(Thread.currentThread().getName()
                            + "生产者生产，目前总共有" + count);
                    notEmpty.signal();

                }
                finally {
                    lock.unlock();
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

                lock.lock();

                try {
                    while (count == 0) {

                        try {
                            notEmpty.await();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    count--;

                    System.out.println(Thread.currentThread().getName()
                            + "生产者生产，目前总共有" + count);

                    notFull.signal();

                }
                finally {
                    lock.unlock();
                }

            }
        }
    }

}
