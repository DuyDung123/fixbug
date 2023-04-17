package com.example.fixbug.patternbuilder.fastfoodstore.director;

import com.example.fixbug.patternbuilder.fastfoodstore.product.order.Order;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.BreadType;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.OrderType;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.SauceType;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.VegetableType;

public class Client {
    public static void createOrder() {
        Order order = new Order.FastFoodOrderBuilder()
                .orderType(OrderType.ON_SITE).orderBread(BreadType.OMELETTE)
                .orderSauce(SauceType.SOY_SAUCE).orderVegetable(VegetableType.CUCUMBER).build();
        System.out.println(order);

        Order order1 = new Order.FastFoodOrderBuilder().orderType(OrderType.ON_SITE).orderBread(BreadType.OMELETTE)
                .orderSauce(SauceType.SOY_SAUCE).orderVegetable(VegetableType.CUCUMBER).build();
        System.out.println(order1);

        Order order2 = new Order.FastFoodOrderBuilder().build();
        System.out.println(order2);
    }
}
