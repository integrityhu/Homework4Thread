package hu.integrity.test;

import java.io.*;

public class Receiver extends Thread {
    private PipedReader in;
    private boolean receive = true;
    
    public Receiver(Sender sender) throws IOException {
        in = new PipedReader(sender.getPipedWriter());
    }

    public void run() {
        try {
            while (receive) {
                System.out.println("Read: " + (char) in.read());
            }
        } catch (IOException e) {
            receive = false;
        }
        System.out.println("Receiver loop finished.");
    }
}
