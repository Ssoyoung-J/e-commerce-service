package kr.hhplus.be.server.application.point;

import kr.hhplus.be.server.domain.point.PointCommand;
import kr.hhplus.be.server.domain.point.PointInfo;
import kr.hhplus.be.server.domain.point.PointService;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserInfo;
import kr.hhplus.be.server.domain.user.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointFacade {

    private final PointService pointService;
    private final UserService userService;

    /**
     *  포인트 충전
     * */
    public PointResult.Transaction charge(PointCriteria.UserPoint criteria) {
        // 사용자 조회
        UserInfo.User user = userService.getUser(criteria.getUserId());

        // 사용자 포인트 조회
        PointInfo.Balance userBalance = pointService.getUserBalance(criteria.toPointCommand(criteria.getUserId(), c))
        // 사용자 포인트 충전
        PointCommand.Point command = PointCommand.Point.of(criteria.getUserId(), criteria.getPointAmount());
        PointInfo.Balance charge = pointService.chargePoint(command);

        PointResult.Transaction result = new PointResult.Transaction(charge.getPointHistoryId(), charge.getUserId(), charge.getBalance(), charge.getCreatedAt(), charge.getUpdatedAt());
        return result;
    }
}
