## 사용자 보유 잔액 조회
```mermaid
sequenceDiagram
    actor User
    participant UserPoint

    User ->>+ UserPoint: 잔액 조회 요청
    UserPoint ->> UserPoint : 현재 잔액 조회
    UserPoint -->>- User: 사용자 보유 잔액
```

## 사용자 잔액 충전
```mermaid
sequenceDiagram
    actor User
    participant UserPoint

    User ->>+ UserPoint: 사용자 잔액 충전 요청
    UserPoint ->>+ UserPoint: 현재 잔액 조회
    alt 충전 가능
        UserPoint ->> UserPoint : 충전 처리
        UserPoint -->>- User: 충전 성공
    else 충전 한도 초과
        UserPoint ->> UserPoint : 충전 거부 처리
        UserPoint -->>- User: 충전 실패 (한도초과)
    end
```

## 상품 목록 조회
```mermaid
sequenceDiagram
    actor User
    participant Product

    User ->>+ Product: 상품 목록 조회 요청
    Product -->>- User: 구매 가능한 상품 목록
```

## 상품 단건 조회
```mermaid
sequenceDiagram
    actor User
    participant Product
    
    User ->>+ Product: 상품 단건 조회 요청
    Product -->>- User: 구매 가능한 상품
```

## 주문-결제
### 재고가 없다고 주문을 받지 않는다면 사용자의 서비스 이용 만족도가 떨어질 수 있음
- 선 주문 생성 후, 주문 상태 변경
```mermaid
sequenceDiagram
    actor User
    participant OrderAPI
    participant ProductAPI
    participant CouponAPI
    participant UserPointAPI
    participant PlatformAPI

%% 주문 생성 및 재고 확인
    User ->>+ OrderAPI: POST /orders
    activate OrderAPI
    OrderAPI ->>+ ProductAPI: GET /products/{id}/stock-check
    activate ProductAPI
    alt 재고 충분
        ProductAPI -->> OrderAPI: 재고 확인 OK
        deactivate ProductAPI
        OrderAPI -->> User: 주문 생성 완료 (결제 대기)
    else 재고 부족
        ProductAPI -->> OrderAPI: 재고 부족
        deactivate ProductAPI
        OrderAPI -->> User: 주문 실패 (재고 부족)
        deactivate OrderAPI
    end
    deactivate OrderAPI

%% 결제 단계
    User ->>+ CouponAPI: GET /coupons/valid?orderId={id}
    activate CouponAPI
    alt 쿠폰 있음
        CouponAPI -->> OrderAPI: 적용 가능한 쿠폰 금액
    else 쿠폰 없음
        CouponAPI -->> OrderAPI: 쿠폰 없음
    end
    deactivate CouponAPI

    OrderAPI ->>+ UserPointAPI: POST /payments
    activate UserPointAPI
    alt 결제 성공
        UserPointAPI -->> OrderAPI: 결제 완료
        deactivate UserPointAPI
    %% 비동기 전송 시작
        OrderAPI -->>+ PlatformAPI: POST /payments (비동기)
        OrderAPI -->> User: 결제 완료
    else 결제 실패
        UserPointAPI -->> OrderAPI: 결제 실패
        deactivate UserPointAPI
        activate CouponAPI
        OrderAPI ->> CouponAPI: POST /coupons/{id}/cancel
        deactivate CouponAPI
        OrderAPI -->> User: 결제 실패
    end


```

## 쿠폰 발급 요청
```mermaid
sequenceDiagram
    actor User
    participant Coupon

    User ->>+ Coupon: 선착순 쿠폰 발급 요청
    Coupon ->>+ Coupon : 사용자 쿠폰 발급 이력 조회
    alt 이미 발급된 사용자
        Coupon -->>- User : 중복 발급 불가
    else 발급 이력 없음
        Coupon ->>+ Coupon: 쿠폰 개수 조회
        alt 쿠폰 발급 성공 (쿠폰 개수 > 0)
            Coupon -->>- User : 발급된 쿠폰 정보
        else 쿠폰 발급 실패
            Coupon -->>- User : 쿠폰 소진
        end
    end
```


## 쿠폰 발급 목록 조회
```mermaid
sequenceDiagram
    actor User
    participant Coupon

    User ->>+ Coupon: 쿠폰 목록 조회
    Coupon ->>+ Coupon : 사용자 쿠폰 발급 이력 조회
    alt 보유 쿠폰 O
        Coupon -->>- User : 쿠폰 목록
    else 보유 쿠폰 X
        Coupon -->>- User : 보유 쿠폰 없음
    end
```

## 인기 상품 조회
### 인기 상품 조회의 목적은 통계
- 상품의 상세 정보를 표출할지 말지는 설계자의 마음인 것 같은데 상세 정보가 있어야지 이후 서비스 제공에 원활하지 않을까..?
```mermaid
sequenceDiagram
    actor User
    participant Order
    participant Product

    User ->>+ Order: 인기 상품 조회 요청
    Order -->>+ Product: 상품 상세 정보 조회
    Product -->>- Order: 상품 정보 목록
    Order -->>- User: 인기 상품 Top5 상세 정보
```