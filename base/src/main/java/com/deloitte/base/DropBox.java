package com.deloitte.base;

public class DropBox<I,C> {
    private I item;
    private C coins;
    public I getItem(){
        return  item;
    }
    public C getCoins(){
        return coins;
    }
    public DropBox(I item, C coins) {
        this.item = item;
        this.coins = coins;
    }
}
