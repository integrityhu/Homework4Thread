/**
 * 
 */
package hu.integrity.test;

import java.util.Observable;
import java.util.Observer;

/**
 * @author pzoli
 *
 */
public class ObserverConsumer implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(o+" changed. New value: "+arg);
    }

}
