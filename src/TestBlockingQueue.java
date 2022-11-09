import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Description
 * @Author lly
 * @Create 2022-11-09 3:53 PM
 */
public class TestBlockingQueue {

    private static Integer count = 0;
    private static final Integer FULL = 10;
    final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(FULL);

    public static void main(String[] args) {

        TestBlockingQueue test = new TestBlockingQueue();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();

    }

    class Producer implements Runnable{

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
                    queue.put(i);
                    count++;
                    System.out.println(Thread.currentThread().getName()
                            + "生产者生产，目前总共有" + count);

                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    class Consumer implements Runnable{

        @Override
        public void run() {

            for(int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    queue.take();
                    count--;
                    System.out.println(Thread.currentThread().getName()
                            + "消费者消费，目前总共有" + count);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
