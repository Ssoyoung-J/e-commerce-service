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

## 주문
### 재고가 없다고 주문을 받지 않는다면 사용자의 서비스 이용 만족도가 떨어질 수 있음
- 선 주문 생성 후, 주문 상태 변경
```mermaid
sequenceDiagram
    actor User
    participant Order
    participant Product

    User ->>+ Order : 상품 주문 요청
    Order ->>+ Order : 주문 정보 생성
    Order ->>+ Product : 재고 확인 요청
    alt 재고 충분
        Product -->>- Order : 재고 확보 성공
        Order -->>- User : 결제 대기
    else 재고 부족
        activate Product
        Product -->>- Order : 재고 부족
        Order ->>+ Order : 주문 상태 변경 → 실패
        Order -->>- User : 주문 실패 (재고 부족)
    end
```

## 결제
```mermaid
sequenceDiagram
    actor User
    participant Order
    participant Coupon
    participant UserPoint
    participant Platform

    User ->>+ Order : 주문 정보 조회
    Order -->>- User : 결제 대기
    User ->>+ Coupon : 유효한 쿠폰 조회
    alt 결제 성공
        activate Coupon
        Coupon -->>- Order: 쿠폰이 적용된 포인트
        Order ->> UserPoint: 결제 요청
        activate UserPoint
        UserPoint -->>- Order: 결제 성공
        Order --)+ Platform: 결제 정보
    else 결제 성공 (유효한 쿠폰X)
        activate Coupon
        Coupon -->>- Order: 쿠폰 적용 X
        Order ->>+ UserPoint: 결제 요청
        UserPoint -->>- Order : 결제 성공
        Order --)+ Platform: 결제 정보
    else 결제 실패 (결제 기한 만료)
    %% ex : 무통장 입금
        activate Coupon
        Coupon -->>- Order : 쿠폰 적용
        Order ->>+ UserPoint : 결제 요청
        UserPoint -->>- Order : 결제 실패
        Order ->>+ Coupon: 쿠폰 적용 취소
        deactivate Coupon
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