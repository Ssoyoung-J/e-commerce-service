package kr.hhplus.be.server.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.application.point.PointCriteria;
import kr.hhplus.be.server.application.point.PointFacade;
import kr.hhplus.be.server.application.point.PointResult;
import kr.hhplus.be.server.domain.point.PointHistory;
import kr.hhplus.be.server.presentation.point.PointController;
import kr.hhplus.be.server.presentation.point.PointRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static kr.hhplus.be.server.domain.point.PointHistory.PointTransactionType.CHARGE;
import static kr.hhplus.be.server.domain.point.PointHistory.PointTransactionType.USE;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PointController.class)
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PointFacade pointFacade;

    @DisplayName("포인트 조회")
    @Test
    void getUserBalance() throws Exception {
        // given
        Long userId = 10L;
        Long balance = 35000L;

        PointResult.UserPoint point = PointResult.UserPoint.builder()
                .userId(userId)
                .balance(balance)
                .build();

        when(pointFacade.getUserBalance(any(PointCriteria.Balance.class))).thenReturn(point);

        // when & then
        mockMvc.perform(get("/api/points/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.balance").value(35000L));


    }

    @DisplayName("포인트 충전")
    @Test
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

        when(pointFacade.charge(any(PointCriteria.UserPoint.class)))
                .thenReturn(charge);

        // when & then
        mockMvc.perform(post("/api/points/charge")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.pointId").value(10L))
                .andExpect(jsonPath("$.data.userId").value(10L))
                .andExpect(jsonPath("$.data.balance").value(25000L));


    }

    @DisplayName("포인트 내역 조회")
    @Test
    void getUserPointHistories() throws Exception{
        // given
        Long userId = 10L;

        PointResult.History chargeHistory = PointResult.History.builder()
                .pointHistoryId(10L)
                .userId(userId)
                .pointAmount(10_000L)
                .type(CHARGE)
                .createdAt(LocalDateTime.now())
                .build();

        PointResult.History useHistory = PointResult.History.builder()
                .pointHistoryId(20L)
                .userId(userId)
                .pointAmount(20_000L)
                .type(USE)
                .createdAt(LocalDateTime.now())
                .build();

        List<PointResult.History> histories = List.of(chargeHistory, useHistory);

        when(pointFacade.getUserPointHistories(any(PointCriteria.Balance.class))).thenReturn(histories);


        // when & then
        mockMvc.perform(get("/api/points/history/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].pointHistoryId").value(10L))
                .andExpect(jsonPath("$.data[0].userId").value(userId))
                .andExpect(jsonPath("$.data[0].pointAmount").value(10_000L))
                .andExpect(jsonPath("$.data[0].type").value("CHARGE"))
                .andExpect(jsonPath("$.data[1].pointHistoryId").value(20L))
                .andExpect(jsonPath("$.data[1].userId").value(userId))
                .andExpect(jsonPath("$.data[1].pointAmount").value(20_000L))
                .andExpect(jsonPath("$.data[1].type").value("USE"));


    }


}
