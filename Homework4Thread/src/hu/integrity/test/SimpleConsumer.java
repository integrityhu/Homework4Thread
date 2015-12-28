/**
 * 
 */
package hu.integrity.test;

/**
 * @author pzoli
 * 
 */
public class SimpleConsumer extends Thread {

    private boolean consume = true;
    
    private void buyProducts() {
        synchronized (this) {
            try {
                wait();
                System.out.println("Someone notifyed me.");
            } catch (InterruptedException e) {
                consume = false;
            }
        }
    }

    @Override
    public void run() {
        while (consume) {
            buyProducts();
        }
        System.out.println("Simple customer finished.");
    }

}
