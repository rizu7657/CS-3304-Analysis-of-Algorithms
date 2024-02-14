package dBay;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SearchFacade {

    public static Order findById(List<Order> orders, Long id) {
        Order foundOrder = null;

        orders.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                if (o1 == null || o2 == null) {
                    return o1 == null ? -1 : 1;
                } else if (Objects.equals(o1.getId(), o2.getId())) {
                    return 0;
                } else if (o1.getId() > o2.getId()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return binarySearch(orders, id);
    }

    private static Order binarySearch(List<Order> orders, Long id) {
        if (orders.size() == 1) {
            Order order = orders.get(0);
            return order.getId().equals(id) ? order : null;
        }

        int i = (int) Math.ceil(orders.size() / 2.0) - 1;
        Order middleOrder = orders.get(i);

        if (id > middleOrder.getId()) {
            return binarySearch(orders.subList(i, orders.size() - 1), id);
        } else if (id < middleOrder.getId()) {
            return binarySearch(orders.subList(0, i - 1), id);
        } else {
            return middleOrder;
        }
    }
}
