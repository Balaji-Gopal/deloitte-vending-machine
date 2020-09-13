package com.deloitte.base.intrfc;

import com.deloitte.base.CollectionBox;
import com.deloitte.base.enm.Coin;
import com.deloitte.base.enm.Product;

import java.util.List;

public interface Machine {
    long getPrice(Product product);
    void putCoin(Coin coin);
    List<Coin> cancel();
    void reset();
    CollectionBox<Product,List<Coin>> getProductAndRemainingPrice();
}
