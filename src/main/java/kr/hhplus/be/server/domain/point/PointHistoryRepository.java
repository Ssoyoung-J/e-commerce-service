package kr.hhplus.be.server.domain.point;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PointHistoryRepository {

    // 사용자 포인트 내역 조회
    List<PointHistory> findPointHistoryByUserId(Long userId);

    // 사용자 포인트 저장
    PointHistory save(PointHistory history) ;
}
