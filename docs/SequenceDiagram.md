## 사용자 보유 잔액 조회
```mermaid
sequenceDiagram
actor User
participant UserPoint

    User ->>+ UserPoint: 잔액 조회 요청
    UserPoint -->>- User: 사용자 보유 잔액
```

## 사용자 잔액 충전
```mermaid
sequenceDiagram
    actor User
    participant UserPoint

    User ->>+ UserPoint: 사용자 잔액 충전 요청
    UserPoint -->>- User: 현재 잔액

    User ->>+ UserPoint: 사용자 잔액 충전 요청
    activate UserPoint
    alt 충전 가능
        UserPoint -->>- User: 충전 성공 
    else 충전 한도 초과
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
```mermaid
sequenceDiagram
    actor User
    participant Product
    participant Order
    
    User ->>+ Order: 주문 요청
    alt 주문 생성
        User ->>+ Product : 상품 재고 조회 (상품 수량 >= 구매 요청 수량)
        Product ->>+ Order : 주문 생성
        Order -->>- User : 결제 대기
    else 주문 실패
        User ->>+ Product : 상품 재고 조회
        Product -->>- User : 주문 실패 (상품 수량 부족)
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
    
    User ->>+ UserPoint: 결제 요청
    alt 결제 성공 
        User ->>+ Order : 주문 상태 조회(결제 대기)
        Order ->>+ Coupon : 유효한 쿠폰 조회
        Coupon ->>+ Order: 쿠폰 적용
        Order ->>+ UserPoint: 포인트 차감
        UserPoint ->>+ Platform: 결제 정보
    else 결제 성공 (유효한 쿠폰X)
        User ->>+ Order : 주문 정보 조회(결제 대기)
        Order ->>+ Coupon : 유효한 쿠폰 X
        Coupon -->>- Order: 쿠폰 적용 X
        Order -->>+ UserPoint: 포인트 차감
        UserPoint ->>+ Platform: 결제 정보
    else 결제 실패 (결제 기한 만료) 
%%    ex : 무통장 입금
        User ->>+ Order : 주문 정보 조회(결제 대기)
        Order ->>+ Coupon : 유효한 쿠폰 조회
        Coupon -->>- Order : 쿠폰 적용
        Order -->>- User : 결제 실패(결제 기한 만료)
        
    end
```

## 쿠폰 발급 목록 조회
```mermaid
sequenceDiagram
    actor User
    participant Coupon

    User ->>+ Coupon: 쿠폰 발급 조회 요청
    Coupon ->>- User : 유효한 쿠폰 목록
```

## 인기 판매 상품 조회
```mermaid
sequenceDiagram
    actor User
    participant Product
    
    User ->>+ Product: 인기 상품 조회 요청
    Product ->>- User: 인기 상품 목록(최근 3일간 판매량이 높은 Top5)
```