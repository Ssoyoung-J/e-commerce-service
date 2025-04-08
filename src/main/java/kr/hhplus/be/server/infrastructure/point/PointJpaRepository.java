package kr.hhplus.be.server.infrastructure.point;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointHistory;
import kr.hhplus.be.server.domain.point.PointTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PointJpaRepository extends JpaRepository<Point, Long> {

    Optional<Point> findByUserId(Long userId);

    Point save(Long userId, Long balance);

    // PointHistory 와 Point 테이블은 분리가 되어있는데 Repository도 분리해서 작성하는 것이 좋을까?
    PointHistory saveHistory(Long userId, PointTransactionType transactionType, Long pointAmount, LocalDateTime createdAt) ;
}
