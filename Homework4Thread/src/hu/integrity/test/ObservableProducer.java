/**
 * 
 */
package hu.integrity.test;

import java.util.Observable;

/**
 * @author pzoli
 *
 */
public class ObservableProducer extends Observable {
    public void change(Object o) {
        setChanged();
        notifyObservers(o);
    }
}
