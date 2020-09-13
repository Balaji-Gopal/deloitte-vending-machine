package com.deloitte.service;

import com.deloitte.base.*;
import com.deloitte.base.enm.Coin;
import com.deloitte.base.enm.Product;
import com.deloitte.base.exception.IncompletePaymentException;
import com.deloitte.base.exception.NoChangeException;
import com.deloitte.base.exception.OutOfStockException;
import com.deloitte.base.intrfc.Machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MachineImpl implements Machine {
    private Product selectedProduct;
    private long turnOver;
    private long balance;
    private Stock<Coin> coinStock = new Stock<Coin>();
    private Stock<Product> productStock = new Stock<Product>();

    public MachineImpl() {
        Arrays.stream(Coin.values()).forEach(c -> coinStock.put(c, 10));
        Arrays.stream(Product.values()).forEach(i -> productStock.put(i, 10));
    }

    @Override
    public long getPrice(Product product) {
        if (productStock.hasProduct(product)) {
            selectedProduct = product;
            return selectedProduct.getPrice();
        }
        throw new OutOfStockException("Out of stock, Please buy any another product");
    }

    @Override
    public void putCoin(Coin coin) {
        balance = balance + coin.getDenomination();
        coinStock.add(coin);
    }

    @Override
    public DropBox<Product, List<Coin>> getProductAndRemainingPrice() {
        Product product = collectItem();
        turnOver = turnOver + selectedProduct.getPrice();
        List<Coin> change = collectChange();
        return new DropBox<Product, List<Coin>>(product, change);
    }

    private Product collectItem() throws NoChangeException, IncompletePaymentException {
        if (isFullPaid()) {
            if (hasSufficientChange()) {
                productStock.reduce(selectedProduct);
                return selectedProduct;
            }
            throw new NoChangeException("No change in the machine");
        }
        long remainingBalance = selectedProduct.getPrice() - balance;
        throw new IncompletePaymentException("Please pay complete amount : ", remainingBalance);
    }

    private List<Coin> collectChange() {
        long changeAmount = balance - selectedProduct.getPrice();
        List<Coin> change = getChange(changeAmount);
        updateCashStock(change);
        balance = 0;
        selectedProduct = null;
        return change;
    }

    @Override
    public List<Coin> cancel() {
        List<Coin> refund = getChange(balance);
        updateCashStock(refund);
        balance = 0;
        selectedProduct = null;
        return refund;
    }


    private boolean isFullPaid() {
        return balance >= selectedProduct.getPrice();
    }


    private List<Coin> getChange(long amount) throws NoChangeException {
        List<Coin> changeList = Collections.EMPTY_LIST;
        if (amount > 0) {
            changeList = new ArrayList<Coin>();
            long balance = amount;
            while (balance > 0) {
                if (balance >= Coin.DOLLAR.getDenomination()
                        && coinStock.hasProduct(Coin.DOLLAR)) {
                    changeList.add(Coin.DOLLAR);
                    balance = balance - Coin.DOLLAR.getDenomination();
                    continue;

                } else if (balance >= Coin.HALF_DOLLAR.getDenomination()
                        && coinStock.hasProduct(Coin.HALF_DOLLAR)) {
                    changeList.add(Coin.HALF_DOLLAR);
                    balance = balance - Coin.HALF_DOLLAR.getDenomination();
                    continue;

                }else if (balance >= Coin.QUARTER.getDenomination()
                        && coinStock.hasProduct(Coin.QUARTER)) {
                    changeList.add(Coin.QUARTER);
                    balance = balance - Coin.QUARTER.getDenomination();
                    continue;

                } else if (balance >= Coin.DIME.getDenomination()
                        && coinStock.hasProduct(Coin.DIME)) {
                    changeList.add(Coin.DIME);
                    balance = balance - Coin.DIME.getDenomination();
                    continue;

                } else if (balance >= Coin.NICKLE.getDenomination()
                        && coinStock.hasProduct(Coin.NICKLE)) {
                    changeList.add(Coin.NICKLE);
                    balance = balance - Coin.NICKLE.getDenomination();
                    continue;

                } else if (balance >= Coin.CENT.getDenomination()
                        && coinStock.hasProduct(Coin.CENT)) {
                    changeList.add(Coin.CENT);
                    balance = balance - Coin.CENT.getDenomination();
                    continue;

                } else {
                    throw new NoChangeException("NotSufficientChange, Please try another product");
                }
            }
        }
        return changeList;
    }

    @Override
    public void reset() {
        coinStock.clear();
        productStock.clear();
        turnOver = 0;
        selectedProduct = null;
        balance = 0;
    }

    private boolean hasSufficientChange() {
        return hasSufficientChangeForAmount(balance - selectedProduct.getPrice());
    }

    private boolean hasSufficientChangeForAmount(long amount) {
        try {
            getChange(amount);
        } catch (NoChangeException nce) {
            return false;
        }
        return true;
    }

    private void updateCashStock(List<Coin> change) {
        for (Coin c : change) {
            coinStock.reduce(c);
        }
    }
}

