/**
 * 
 */
package hu.integrity.tasks;

import hu.integrity.test.SimpleConsumer;
import hu.integrity.test.SimpleProducer;

/**
 * @author pzoli
 * http://www.vogella.com/tutorials/JavaConcurrency/article.html
 * 
 */
public class SimpleThreadsTask {

    public void run() {
        SimpleConsumer consumer = new SimpleConsumer(); 
        SimpleProducer producer = new SimpleProducer(consumer);
        producer.start();
        consumer.start();
        System.out.println("Simple thread task started.");
        try {
            synchronized (this) {
                System.out.println("Waiting...");
                wait(4000);
                System.out.println("Terminating.");
                producer.interrupt();
                producer.join();
                consumer.interrupt();
                consumer.join();
                System.out.println("Terminated.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Simple thread task finished.");
    }

}
