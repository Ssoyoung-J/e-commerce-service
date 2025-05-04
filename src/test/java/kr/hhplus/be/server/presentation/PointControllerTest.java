package kr.hhplus.be.server.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.application.point.PointResult;
import kr.hhplus.be.server.presentation.point.PointController;
import kr.hhplus.be.server.presentation.point.PointRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@WebMvcTest(PointControllerTest.class)
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @MockitoBean

    @Test
    @DisplayName("포인트 충전")
    void chargePoint() throws Exception {
        // given
        PointRequest.Charge request = new PointRequest.Charge(10L, 10000L);

        PointResult.Transaction charge = PointResult.Transaction.builder()
                .pointId(10L)
                .userId(10L)
                .balance(25000L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

//        when()

    }


}
