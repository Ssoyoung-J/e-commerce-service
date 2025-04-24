package kr.hhplus.be.server.domain.point;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

//    PointHistory save(Long userId, PointTransactionType transactionType, Long pointAmount, LocalDateTime createdAt) ;
}
