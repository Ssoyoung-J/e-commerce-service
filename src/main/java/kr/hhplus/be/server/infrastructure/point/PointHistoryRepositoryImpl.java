package kr.hhplus.be.server.infrastructure.point;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.point.PointHistory;
import kr.hhplus.be.server.domain.point.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    // 사용자 포인트 내역 조회
    @Override
    public List<PointHistory> findPointHistoryByUserId(Long userId) {
        return pointHistoryJpaRepository.findPointHistoryByUserId(userId);
    }

    // 사용자 포인트 내역 저장
    @Override
    public PointHistory save(PointHistory history) {
        return pointHistoryJpaRepository.save(history);
    }
}
