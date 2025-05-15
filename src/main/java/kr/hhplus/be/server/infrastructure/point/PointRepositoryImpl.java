package kr.hhplus.be.server.infrastructure.point;

import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.usertype.BaseUserTypeSupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;
    
    
    // 사용자 포인트 조회
    @Override
    public Point findByUserId(Long userId) {
        return pointJpaRepository.findById(userId).orElseThrow(
                () -> new BusinessException(400, "일치하는 포인트 정보가 없습니다.")
        );
    }

    // 사용자 포인트 저장
    @Override
    public Point save(Point balance) {
        return pointJpaRepository.save(balance);
    }

}
