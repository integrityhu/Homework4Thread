package hu.integrity.test;

import java.io.*;
import java.util.*;

public class Sender extends Thread {
    private Random rand = new Random();
    private PipedWriter out = new PipedWriter();
    private boolean send = true;

    public PipedWriter getPipedWriter() {
        return out;
    }

    public void run() {
        while (send) {
            try {
                for (char c = 'A'; c <= 'z'; c++) {
                    out.write(c);
                    sleep(rand.nextInt(500));
                }
            } catch (Exception e) {
                send = false;
            }
        }
        System.out.println("Sender loop finished.");
    }
}
