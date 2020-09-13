package com.deloitte.base.enm;

public enum Product {
    CHOCOLATE("Chocolate",10),CANDY("Candy",5),COOL_DRINK("Cool Drink",25);
    public String name;
    public int price;
    public int getPrice(){
        return this.price;
    }
    Product(String name, int price){
        this.name  = name;
        this.price = price;
    }
}
