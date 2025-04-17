package kr.hhplus.be.server.domain.user;

public interface UserRepository {

    // 사용자 조회
    User findById(Long userId);
}
