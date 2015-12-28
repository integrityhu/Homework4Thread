/**
 * 
 */
package hu.integrity.tasks;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.LongStream.range;

/**
 * @author pzoli
 *
 */
public class PrimesDoParallel {
    public static boolean isPrime(long value) {
        for (int i = 2; i * i <= value; ++i)
            if (value % i == 0)
                return false;
        return true;
    }

    public static long primeCounter(int bound) {
        long cnt = range(2, bound).parallel().filter(PrimesDoParallel::isPrime).count();
        return cnt;
    }

    public static List<Long> primes(int bound) {
        List<Long> result = null;
        result = range(2, bound).parallel().filter(PrimesDoParallel::isPrime).collect(ArrayList::new, PrimesDoParallel::addToList, PrimesDoParallel::combine);
        return result;
    }
    
    private static void addToList(List<Long> list, Long n) {
        log("Adding " + n + " to " + list);
        list.add(n);
    }

    private static void combine(List<Long> list1, List<Long> list2) {
        log("Combining " + list1 + " and " + list2);
        list1.addAll(list2);
    }    
    
    public static void log(String msg) {
        System.out.println(msg);
    }
}
