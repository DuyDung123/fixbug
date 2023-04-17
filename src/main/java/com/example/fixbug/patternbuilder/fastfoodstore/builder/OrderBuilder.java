package com.example.fixbug.patternbuilder.fastfoodstore.builder;

import com.example.fixbug.patternbuilder.fastfoodstore.product.order.Order;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.BreadType;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.OrderType;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.SauceType;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.VegetableType;
public interface OrderBuilder {

    OrderBuilder orderType(OrderType orderType);

    OrderBuilder orderBread(BreadType breadType);

    OrderBuilder orderSauce(SauceType sauceType);

    OrderBuilder orderVegetable(VegetableType vegetableType);

    Order build();
}
