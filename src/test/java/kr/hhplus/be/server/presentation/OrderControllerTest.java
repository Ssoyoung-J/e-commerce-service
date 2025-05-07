package kr.hhplus.be.server.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.application.order.OrderFacade;
import kr.hhplus.be.server.presentation.order.OrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderFacade orderFacade;


//    @Test
//    @DisplayName("상품 주문")
//    void createOrder() throws Exception {
//        // given
//        OrderRequest.Order request = OrderRequest.Order.of(1L,
//                List.of(OrderRequest.OrderItem.of(10L, 10L, 20L, 20000L)
//                ), 200L);
//
//        OrderResult.Order order = OrderResult.Order.builder()
//                        .orderId(10L)
//                        .userId(1L)
//                        .status(OrderStatus.WAIT)
//                        .
//
//
////        when(orderFacade.order(any(OrderCriteria.Order.class))).thenReturn();
//
//        // when & then
//        mockMvc.perform(post("/api/orders")
//                .content(objectMapper.writeValueAsString(request))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

}
