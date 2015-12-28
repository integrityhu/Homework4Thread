package hu.integrity.test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Consumer extends Thread {

    private Producer producer;
    private Random randomGenerator = new Random();
    private int sumMSec = 0;
    private int required = 0;
    private int packageSize;

    public Consumer(Producer producer) {
        super();
        this.producer = producer;
    }

    private void setPackageSize() {
        packageSize = required / 2;
        if (packageSize == 0) {
            packageSize = 1;
        }
    }

    private void checkPriority() {
        if (((sumMSec > 5000) && (sumMSec % 5000) > 3000)) {
            sumMSec = 0;
            if (getPriority() < MAX_PRIORITY) {
                setPriority(getPriority() + 1);
            }
            if (packageSize > 0) {
                --packageSize;
            }
            System.out.println("[" + getName() + "," + new Date() + "] set priority to " + getPriority() + ", set package size to " + packageSize);
        }
    }

    private int buyProducts(int buyCount) {
        int needed = required < packageSize ? required : packageSize;
        System.out.println(" =================================== >>>>>>>>>>>>>>>>> timeout, let me see productions... [" + getName() + " / priority: " + getPriority() + "] I need " + needed + "/" + required);
        buyCount = producer.buyProducts(this, needed);
        if (buyCount > 0) {
            required -= buyCount;
            System.out.println("[" + getName() + "," + new Date() + "] " + required + " more product required.");
            sumMSec = 0;
        } else {
            System.out.println("[" + getName() + "] Waiting for production...");
        }
        return buyCount;
    }

    private void waitForProducts() throws InterruptedException {
        ConcurrentLinkedQueue<Integer> requestQueue = producer.getRequestQueue();
        synchronized (requestQueue) {
            int mwaitsecs = getPriority() == MAX_PRIORITY ? 0 : randomGenerator.nextInt(1000);
            System.out.println("[" + getName() + "," + new Date() + "] Wait " + mwaitsecs + " ms");
            requestQueue.wait(mwaitsecs);
            sumMSec += mwaitsecs;
            System.out.println("[" + getName() + "," + new Date() + "] sum wait time " + sumMSec + " ms");
        }
    }

    private void waitUntilReady() throws InterruptedException {
        int buyCount = 0;
        while (required > 0) {
            waitForProducts();
            buyCount = buyProducts(buyCount);
            checkPriority();
        }
        setPriority(NORM_PRIORITY);
    }

    public void run() {
        boolean require = true;
        while (require) {
            try {
                do {
                    required = randomGenerator.nextInt(15);
                } while (required == 0);
                setPackageSize();
                producer.addRequest(required);
                System.out.println("[" + getName() + "] new request count = " + required);
                waitUntilReady();
                int waitMSecsBeforeRequest = randomGenerator.nextInt(5000);
                System.out.println("[" + getName() + "] wait " + waitMSecsBeforeRequest + " msecs before new request.");
                sleep(waitMSecsBeforeRequest);
            } catch (InterruptedException e) {
                System.out.println(getName()+" interrupted");
                require = false;
            }
        }
    }

}
