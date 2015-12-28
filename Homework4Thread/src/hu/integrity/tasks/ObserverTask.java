/**
 * 
 */
package hu.integrity.tasks;

import hu.integrity.test.ObservableProducer;
import hu.integrity.test.ObserverConsumer;

/**
 * @author pzoli
 *
 */
public class ObserverTask {

    public static void run() {
        ObservableProducer op = new ObservableProducer();
        ObserverConsumer oc1 = new ObserverConsumer();
        ObserverConsumer oc2 = new ObserverConsumer();
        
        op.addObserver(oc1);
        op.addObserver(oc2);
        op.change("modify to.");
    }

}
