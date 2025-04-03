package kr.hhplus.be.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "잔액 충전 요청")
public class UserPointChargeRequest {

    @Schema(description = "포인트")
    private int point;
}
