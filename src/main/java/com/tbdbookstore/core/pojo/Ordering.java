package com.tbdbookstore.core.pojo;

import com.tbdbookstore.core.shared.Attribute;
import com.tbdbookstore.core.shared.OrderMode;

public class Ordering {

    private Attribute attribute;
    private OrderMode mode;

    public Ordering(Attribute attribute) {
        this.attribute = attribute;
        this.mode = OrderMode.ASC;
    }

    public Ordering(Attribute attribute, OrderMode mode) {
        this.attribute = attribute;
        this.mode = mode;
    }

    public Attribute getAttribute() { return attribute; }

    public void setAttribute(Attribute attribute) { this.attribute = attribute; }

    public OrderMode getMode() { return mode; }

    public void setMode(OrderMode mode) { this.mode = mode; }

}
