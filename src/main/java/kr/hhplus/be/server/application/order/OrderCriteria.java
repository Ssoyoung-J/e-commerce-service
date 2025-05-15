//package kr.hhplus.be.server.application.order;
//
//import kr.hhplus.be.server.domain.order.OrderCommand;
//import kr.hhplus.be.server.domain.product.ProductCommand;
//import kr.hhplus.be.server.presentation.order.OrderRequest;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//public class OrderCriteria {
//    @Getter
//    public static class Create {
//        private final Long userId;
//        private final List<OrderItem> items;
//        private final Long userCouponId;
//
//        @Builder
//        private Create(Long userId, List<OrderItem> items, Long userCouponId) {
//            this.userId = userId;
//            this.items = items;
//            this.userCouponId = userCouponId;
//        }
//
//        public static Create of(Long userId, List<OrderItem> items, Long userCouponId) {
//            return new Create(userId, items, userCouponId);
//        }
//
//        public static Create fromRequest(OrderRequest.Order orderRequest) {
//            List<OrderItem> items = orderRequest.getOrderItemList().stream().map(OrderRequest.OrderItem::toCriteria)
//                    .toList();
//            return new Create(orderRequest.getUserId(), items, orderRequest.getUserCouponId());
//        }
//
//        public OrderCommand.OrderItems toOrderItemCommand() {
//            return OrderCommand.OrderItems.of(
//                    items.stream()
//                            .map(item -> OrderCommand.OrderItem.of(item.getProductId(), item.getProductDetailId()
//                                    ,item.getProductQuantity(), item.getProductPrice()))
//                            .toList()
//            );
//        }
//
//        public OrderCommand.Create toOrderCommand(Long userId, List<OrderCommand.OrderItem> orderItems) {
//            List<OrderCommand.OrderItem> items = orderItems.stream()
//                    .map(o -> OrderCommand.OrderItem.builder()
//                            .productId(o.getProductId()).productQuantity(o.getProductQuantity()).productPrice(o.getProductPrice())
//                            .build()).toList();
//
//            return OrderCommand.Create.of(userId, items);
//        }
//
//        public UserCouponCommand.UsableCoupon toUsableCouponCommand(Long userId, Long userCouponId) {
//            return UserCouponCommand.UsableCoupon.of(userId, userCouponId);
//        }
//
//        public ProductCommand.ProductDetailIds toProductDetailIdsCommand(List<Long> productDetailIds) {
//            return ProductCommand.ProductDetailIds.of(productDetailIds);
//        }
//
//    }
//
//    @Getter
//    public static class OrderItem {
//        private final Long productId;
//        private final Long productDetailId;
//        private final int productQuantity;
//        private final Long productPrice;
//
//        @Builder
//        private OrderItem(Long productId, Long productDetailId, int productQuantity, Long productPrice) {
//            this.productId = productId;
//            this.productDetailId = productDetailId;
//            this.productQuantity = productQuantity;
//            this.productPrice = productPrice;
//        }
//
//        public static OrderItem of(Long productId, Long productDetailId, int productQuantity, Long productPrice) {
//            return new OrderItem(productId, productDetailId, productQuantity, productPrice);
//        }
//    }
//
//
//}
