package hu.integrity.test;

public class Order {
    private int idx = 0;
    private int count;
    
    public Order(int count) {
        this.count = count;
    }
    
    public void incCount(int count) {
        this.idx += count;
        
    }
    
    public int getCount() {
        return count;
    }
    
    public boolean getReady() {
        return count == idx;
    }
}
