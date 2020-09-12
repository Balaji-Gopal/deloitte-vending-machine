package com.deloitte.base.enm;

public enum Coin {
    DIME(10), NICKLE(5), PENNY(1), QUARTER(25);
    private int denomination;

    public int getDenomination() {
        return this.denomination;
    }

    Coin(int denomination) {
        this.denomination = denomination;
    }

}
