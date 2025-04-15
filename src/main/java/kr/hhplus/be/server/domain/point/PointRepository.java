package kr.hhplus.be.server.domain.point;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository {

    // 사용자 포인트 조회
    Optional<Point> findByUserId(Long userId);

    // 사용자 포인트 저장
    Point save(Point point);
}
