import java.util.List;

import hu.integrity.tasks.PrimesDoParallel;
import hu.integrity.tasks.PipedIOTask;
import hu.integrity.tasks.PrimesPrint;
import hu.integrity.tasks.ProducerConsumerThreadsTask;
import hu.integrity.tasks.SimpleThreadsTask;


public class Homework4Thread {
    
    private final static int bound = 256;
    
    public static void main(String[] args) {
        try {
            new SimpleThreadsTask().run();
            new PipedIOTask().run();
            new ProducerConsumerThreadsTask().run();
            log("------------------- P R I M E S -------------------");
            List<Long> primes = PrimesDoParallel.primes(bound);
            long cnt = PrimesDoParallel.primeCounter(bound);
            log("Count parallel: "+Long.toString(cnt));
            log("Primes parallel: "+ primes.toString());
            primes = PrimesPrint.getPrimes(bound);
            log("Primes count: "+ primes.size());
            log("Primes: "+ primes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(String msg) {
        System.out.println(msg);
    }
}
