package kr.hhplus.be.server.presentation.order;

import kr.hhplus.be.server.application.order.OrderCriteria;
import kr.hhplus.be.server.application.order.OrderFacade;
import kr.hhplus.be.server.application.order.OrderResult;
import kr.hhplus.be.server.presentation.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/order")
public class OrderController {

   private final OrderFacade orderFacade;

    @PostMapping("/api/orders")
    public OrderResponse.Order order(@RequestBody OrderRequest.Order request) {
        OrderCriteria.Order orderRequest = OrderCriteria.Order.fromRequest(request);
        OrderResult.Order result = orderFacade.order(orderRequest);
        return OrderResponse.Order.from(result);
    }
}
