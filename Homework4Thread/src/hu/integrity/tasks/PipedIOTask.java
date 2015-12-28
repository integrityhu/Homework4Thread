package hu.integrity.tasks;

import hu.integrity.test.Receiver;
import hu.integrity.test.Sender;

public class PipedIOTask {
    public void run() throws Exception {
        System.out.println("PipeIO thread task started.");
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        sender.start();
        receiver.start();
        synchronized (this) {
            System.out.println("Waiting...");
            wait(4000);
            System.out.println("Terminating.");
            sender.interrupt();
            sender.join();
            receiver.interrupt();
            receiver.join();
            System.out.println("Terminated.");
        }
        System.out.println("PipeIO thread task finished.");
    }
}