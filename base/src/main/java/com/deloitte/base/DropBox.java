package com.deloitte.base;

public class DropBox<P,C> {
    private P product;
    private C coins;
    public P getProduct(){
        return product;
    }
    public C getCoins(){
        return coins;
    }
    public DropBox(P product, C coins) {
        this.product = product;
        this.coins = coins;
    }
}
