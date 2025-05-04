package kr.hhplus.be.server.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.presentation.order.OrderController;
import kr.hhplus.be.server.presentation.order.OrderRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("상품 주문")
    void createOrder() throws Exception {
        // given
        OrderRequest.Order request = OrderRequest.Order.of(1L, 100L,
                List.of(OrderRequest.OrderItem.of(10L, 10L, 20L, 20000L)
                ));

        // when & then
        mockMvc.perform(post("/api/orders")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
