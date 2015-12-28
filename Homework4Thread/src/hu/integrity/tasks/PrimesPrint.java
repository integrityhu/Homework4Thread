/**
 * 
 */
package hu.integrity.tasks;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.sqrt;
import static java.util.stream.LongStream.range;
import static java.util.stream.LongStream.rangeClosed;

/**
 * @author pzoli
 *
 */
public class PrimesPrint {

    public static List<Long> getPrimes(int bound) {
        List<Long> result = range(1, bound).filter(PrimesPrint::isPrime).collect(ArrayList::new, PrimesPrint::addToList, PrimesPrint::combine);
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

    public static boolean isPrime(long n) {
        log("Checking " + n);
        boolean isPrime = n > 1 && rangeClosed(2, (long) sqrt(n)).noneMatch(divisor -> n % divisor == 0);
        if (isPrime)
            log("Prime found " + n);
        return isPrime;
    }

    public static void log(String log) {
        System.out.println(log);
    }

}
