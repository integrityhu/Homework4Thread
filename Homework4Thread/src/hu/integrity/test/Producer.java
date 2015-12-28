package hu.integrity.test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {

    private boolean produce = true;
    private ConcurrentLinkedQueue<Integer> requestQueue;

    private AtomicInteger readyCount = new AtomicInteger(0);
    private Integer currentRequiredCount;
    private int producedCount = 0;

    public Producer(ConcurrentLinkedQueue<Integer> requestQueue) {
        this.requestQueue = requestQueue;
    }

    public ConcurrentLinkedQueue<Integer> getRequestQueue() {
        return requestQueue;
    }

    public void setProduce(boolean produce) {
        this.produce = produce;
    }

    public void addRequest(int count) {
        System.out.println("[Producer1 ] " + count + " required.");
        requestQueue.add(count);
    }

    public synchronized Integer buyProducts(Consumer consumer, int required) {
        int ready = readyCount.get();
        if (ready >= required) {
            System.out.println("[Producer1 ] " + required + " sold to " + consumer.getName() + ".");
            int stored = readyCount.addAndGet(-1 * required);
            System.out.println("[Producer1 ] " + stored + " stored.");
            return required;
        } else {
            System.out.println("[Producer1 ] " + consumer.getName() + " (required: " + required + ", stored " + ready + ") please wait more.");
            return 0;
        }
    }

    public void incReadyCount(int inc) {
        readyCount.getAndAdd(inc);
        producedCount++;
    }

    public void produce() throws InterruptedException {
        currentRequiredCount = requestQueue.poll();
        if (currentRequiredCount != null) {
            producedCount = 0;
            System.out.println("[Producer1] Take a deap breath...");
            System.out.println("[Producer1] Factoring " + currentRequiredCount + " products.");
            while ((producedCount < currentRequiredCount) && produce) {
                sleep(800);
                incReadyCount(1);
                System.out.println("[Producer1] One more ready (produced:" + producedCount + ", stored: " + readyCount.get() + ").");
                if ((readyCount.get() % 5) == 0) {
                    System.out.println("[Producer1] Notify five.");
                    synchronized (requestQueue) {
                        requestQueue.notifyAll();
                    }
                }
            }
            System.out.println("[Producer1] Factoring ready (count:" + currentRequiredCount + ").");
        } else {
            System.out.println("[Producer1] poll is empty, let me rest please.");
        }
        synchronized (requestQueue) {
            requestQueue.notifyAll();
        }
    }

    public void run() {
        while (produce) {
            try {
                sleep(500);
                produce();
            } catch (InterruptedException e) {
                System.out.println("[Producer1] Factoring interrupted!");
                produce = false;
            }
        }
    }

}
