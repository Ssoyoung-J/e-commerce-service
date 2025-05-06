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

import java.util.List;

@Component
@RequiredArgsConstructor
public class PointFacade {

    private final PointService pointService;

    /**
     *  포인트 조회
     * */
    public PointResult.UserPoint getUserBalance(PointCriteria.Balance criteria) {
        // 사용자 포인트 조회
        PointInfo.Balance balance = pointService.getUserBalance(criteria.toBalanceCommand(criteria.getUserId()));

        PointResult.UserPoint result = PointResult.UserPoint.from(balance);

        return result;
    }

    /**
     *  포인트 충전
     * */
    public PointResult.Transaction charge(PointCriteria.UserPoint criteria) {
        // 사용자 포인트 충전
        PointInfo.Transaction charge = pointService.chargePoint(criteria.toTransactionCommand(criteria.getUserId(), criteria.getPointAmount()));

        PointResult.Transaction result = PointResult.Transaction.from(charge);
        return result;
    }

    /**
     *  포인트 내역 조회
     * */
    public List<PointResult.History> getUserPointHistories(PointCriteria.Balance criteria) {
        // 사용자 포인트 내역 조회
        List<PointInfo.History> histories = pointService.getUserPointHistories(criteria.toBalanceCommand(criteria.getUserId()));
        return histories.stream()
                .map(PointResult.History::from)
                .toList();
    }
}
