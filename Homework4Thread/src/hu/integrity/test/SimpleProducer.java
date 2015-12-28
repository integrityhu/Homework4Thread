/**
 * 
 */
package hu.integrity.test;

/**
 * @author pzoli
 * 
 */
public class SimpleProducer extends Thread {

    private boolean working = true;
    private SimpleConsumer consumer;

    public SimpleProducer(SimpleConsumer consumer) {
       this.consumer = consumer;
    }
    
    public void setWorking(boolean working) {
        this.working = working;
    }
    
    private void produce() {
        try {
            sleep(500);
            synchronized (consumer) {
                consumer.notify();
            }
            System.out.println("customer notified.");
        } catch (InterruptedException e) {
            working = false;
        }
    }

    @Override
    public void run() {
        while (working) {
            produce();
            System.out.println("Simple producer production ready.");
        }
        System.out.println("Simple producer finished.");
    }

}
