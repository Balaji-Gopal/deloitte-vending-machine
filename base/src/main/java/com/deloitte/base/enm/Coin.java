package com.deloitte.base.enm;

public enum Coin {
    CENT(1), DIME(10), DOLLAR(100), HALF_DOLLAR(50), NICKLE(5), QUARTER(25);
    private int denomination;

    public int getDenomination() {
        return this.denomination;
    }

    Coin(int denomination) {
        this.denomination = denomination;
    }

}
