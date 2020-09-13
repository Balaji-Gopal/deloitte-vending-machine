package com.deloitte.base;

import java.util.HashMap;
import java.util.Map;

public class Stock<T> {
    private Map<T, Integer> stock = new HashMap<T, Integer>();

    public int getQuantity(T item) {
        Integer value = stock.get(item);
        return value == null ? 0 : value;
    }

    public void add(T item){
        int count = stock.get(item);
        stock.put(item, count+1);
    }

    public void reduce(T item) {
        if (hasProduct(item)) {
            int count = stock.get(item);
            stock.put(item, count - 1);
        }
    }

    public boolean hasProduct(T item){
        return getQuantity(item) > 0;
    }

    public void clear(){
        stock.clear();
    }

    public void put(T item, int quantity) {
        stock.put(item, quantity);
    }
}