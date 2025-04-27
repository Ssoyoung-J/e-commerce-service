package kr.hhplus.be.server.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo {

    @Getter
    public static class User {

        private final Long userId;
        private final String name;

        @Builder
        private User(Long userId, String name) {
            this.userId = userId;
            this.name = name;
        }
    }
}
