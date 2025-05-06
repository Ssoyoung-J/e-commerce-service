package kr.hhplus.be.server.domain.point;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PointService {

    private final PointRepository pointRepository;

    private final PointHistoryRepository pointHistoryRepository;

    // 포인트 기본값 지정
    private static final long BALANCE = 0L;

    // 포인트 조회
    public PointInfo.Balance getUserBalance(PointCommand.Balance command) {
        Point userBalance = pointRepository.findByUserId(command.getUserId());
        PointInfo.Balance result = PointInfo.Balance.from(userBalance);
        return result;
    }

    // 포인트 충전
    public PointInfo.Transaction chargePoint(PointCommand.Transaction command) {
        try {
            Point point = pointRepository.findByUserId(command.getUserId());
            point.charge(command.getPointAmount());

            PointHistory pointHistory = PointHistory.saveHistory(command.getUserId(), PointHistory.PointTransactionType.CHARGE, command.getPointAmount());
            pointHistoryRepository.save(pointHistory);

            return PointInfo.Transaction.from(point);

        } catch (BusinessException e) {
            throw e;
        }
    }

    // 포인트 사용
    public PointInfo.Transaction usePoint(PointCommand.Point command) {
        try {
            Point point =  pointRepository.findByUserId(command.getUserId());
            point.use(command.getPointAmount());

            PointHistory pointHistory = PointHistory.saveHistory(command.getUserId(), PointHistory.PointTransactionType.USE, command.getPointAmount());
            pointHistoryRepository.save(pointHistory);

            return PointInfo.Transaction.from(point);
        } catch (BusinessException e) {
            throw e;
        }
    }

    // 포인트 내역 조회
    public List<PointInfo.History> getUserPointHistories(PointCommand.Balance command) {
        try {
            return pointHistoryRepository.findPointHistoryByUserId(command.getUserId()).stream()
                    .map(PointInfo.History::from)
                    .toList();
        }
        catch(BusinessException e) {
            throw new BusinessException(400, "유효한 사용자 ID가 아닙니다.");
        }
    }
   
}



