package org.sw.nb.demos.gradle.java;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    public Integer id;
    // ...
    public Date createdAt;

    public Order(Integer id, Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createdAt=" + new SimpleDateFormat("dd/MM/yyyy").format(createdAt) +
                '}';
    }
}
