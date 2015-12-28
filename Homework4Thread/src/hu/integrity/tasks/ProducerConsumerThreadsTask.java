/**
 * 
 */
package hu.integrity.tasks;

import java.util.concurrent.ConcurrentLinkedQueue;

import hu.integrity.test.Consumer;
import hu.integrity.test.Producer;

/**
 * @author pzoli
 *
 */
public class ProducerConsumerThreadsTask {

    private static ConcurrentLinkedQueue<Integer> requestQueue = new ConcurrentLinkedQueue<Integer>();
    private static int RUN_TIME = 1000 * 60 * 1;
    public void run() {
        Producer producer = new Producer(requestQueue);
        producer.start();
        Consumer consumer1 = new Consumer(producer);
        consumer1.setName("consumer#1");
        consumer1.start();
        Consumer consumer2 = new Consumer(producer);
        consumer2.setName("consumer#2");
        consumer2.start();
        try {
            synchronized (this) {
                System.out.println("Main task wait "+RUN_TIME+" ms...");
                wait(RUN_TIME);
                System.out.println("Terminating.");
                producer.interrupt();
                producer.join();
                System.out.println("Producer terminated.");
                consumer1.interrupt();
                consumer1.join();
                System.out.println("Consumer1 terminated.");
                consumer2.interrupt();
                consumer2.join();
                System.out.println("Consumer2 terminated.");
            }
        } catch (InterruptedException e) {

        }        
    }

}
