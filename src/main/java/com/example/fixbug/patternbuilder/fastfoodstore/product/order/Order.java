package com.example.fixbug.patternbuilder.fastfoodstore.product.order;

import com.example.fixbug.patternbuilder.fastfoodstore.builder.OrderBuilder;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.BreadType;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.OrderType;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.SauceType;
import com.example.fixbug.patternbuilder.fastfoodstore.product.type.VegetableType;

public class Order {

    private OrderType orderType;
    private BreadType breadType;
    private SauceType sauceType;
    private VegetableType vegetableType;

    Order() {
    }

    Order(OrderType orderType, BreadType breadType, SauceType sauceType, VegetableType vegetableType) {
        this.orderType = orderType;
        this.breadType = breadType;
        this.sauceType = sauceType;
        this.vegetableType = vegetableType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public BreadType getBreadType() {
        return breadType;
    }

    public SauceType getSauceType() {
        return sauceType;
    }

    public VegetableType getVegetableType() {
        return vegetableType;
    }

    public static final class FastFoodOrderBuilder implements OrderBuilder {

        private OrderType orderType;
        private BreadType breadType;
        private SauceType sauceType;
        private VegetableType vegetableType;

        @Override
        public OrderBuilder orderType(OrderType orderType) {
            this.orderType = orderType;
            return this;
        }

        @Override
        public OrderBuilder orderBread(BreadType breadType) {
            this.breadType = breadType;
            return this;
        }

        @Override
        public OrderBuilder orderSauce(SauceType sauceType) {
            this.sauceType = sauceType;
            return this;
        }

        @Override
        public OrderBuilder orderVegetable(VegetableType vegetableType) {
            this.vegetableType = vegetableType;
            return this;
        }

        @Override
        public Order build() {
            return new Order(orderType, breadType, sauceType, vegetableType);
        }
    }

    @Override
    public String toString() {
        return "Order [orderType=" + orderType + ", breadType=" + breadType + ", sauceType=" + sauceType
                + ", vegetableType=" + vegetableType + "]";
    }
}
