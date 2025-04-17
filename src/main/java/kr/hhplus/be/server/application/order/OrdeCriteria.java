package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.order.OrderCommand;
import kr.hhplus.be.server.domain.order.OrderItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrdeCriteria {
    @Getter
    public static class Order {
        private final Long userId;
        private final List<OrderItem> items;
        private final Long userCouponId;

        private Order(Long userId, List<OrderItem> items, Long userCouponId) {
            this.userId = userId;
            this.items = items;
            this.userCouponId = userCouponId;
        }

        public static Order of(Long userId, List<OrderItem> items, Long userCouponId) {
            return new Order(userId, items, userCouponId);
        }

        public OrderCommand.OrderItems toOrderItemCommand() {
            return OrderCommand.OrderItems.of(
                    items.stream()
                            .map(item -> OrderCommand.OrderItem.of(item.getProductId(), item.getProductQuantity(), item.getProductPrice()))
                            .toList()
            );
        }

        public OrderCommand.Order toOrderCommand(Long userId, List<OrderCommand.OrderItem> orderItems, Long userCouponId) {
            List<OrderCommand.OrderItem> items = orderItems.stream()
                    .map(o -> OrderCommand.OrderItem.builder()
                            .productId(o.getProductId()).productQuantity(o.getProductQuantity()).productPrice(o.getProductPrice())
                            .build()).toList();

            return OrderCommand.Order.of(userId, items, userCouponId);
        }
    }


}
