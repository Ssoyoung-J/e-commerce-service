# API 명세 문서

## 목차
- [잔액 조회 API](#잔액-조회-api)
- [잔액 충전 API](#잔액-충전-api)
- [상품 목록 조회 API](#상품-목록-조회-api)
- [상품 단건 조회 API](#상품-단건-조회-api)
- [주문 API](#주문-api)
- [결제 API](#결제-api)
- [쿠폰 발급 요청 API](#쿠폰-발급-요청-api)
- [쿠폰 발급 목록 조회 API](#쿠폰-목록-조회-api)
- [인기 상품 조회 API](#인기-상품-조회-api)

# 잔액 조회 API

## 개요
- **Method** : `GET`
- **Endpoint** : `/users/{user_id}/point`
- **Description** : 특정 사용자의 현재 잔액을 조회하는 API

---

## Request
- Request Parameter : `userId` - 사용자 고유 ID

---

## Response

###  Response Headers
| Code  | Message                 | Description |
|------|-------------------------|-------------|
| `200` | `OK` | 요청 성공 |
| `404` | `NOT_FOUND` | 사용자를 찾을 수 없음 |
| `500` | `INTERNAL_SERVER_ERROR` | 서버 오류 발생 |

---

### Response Body
```json
{
  "message": "OK",
  "data": {
    "user_id": 1,
    "point": 5000
  }
}
```


# 잔액 충전 API

## 개요
- **Method** : `POST`
- **Endpoint** : `/users/{user_id}/point`
- **Description** : 특정 사용자의 잔액을 충전하는 API

---

## Request
- Request Parameter : `userId` - 사용자 고유 ID

---

## Response

###  Response Headers
| Code  | Message                 | Description |
|------|-------------------------|-------------|
| `200` | `OK` | 요청 성공 |
| `404` | `NOT_FOUND` | 사용자를 찾을 수 없음 |
| `500` | `INTERNAL_SERVER_ERROR` | 서버 오류 발생 |

---

### Request Body
```json
{
  "point" : 10000
}
```

### Response Body
```json
{
  "message": "OK",
  "data": {
    "user_id": 1,
    "point": 15000
  }
}
```


# 상품 목록 조회 API

## 개요
- **Method** : `GET`
- **Endpoint** : `/products`
- **Description** : 상품 목록을 조회하는 API

---



## Response

###  Response Headers
| Code  | Message                 | Description |
|------|-------------------------|-------------|
| `200` | `OK` | 요청 성공       |
| `500` | `INTERNAL_SERVER_ERROR` | 서버 오류 발생    |

---


### Response Body
  ```json
  {
    "code": 200,
    "message": "OK",
    "data": [
      {
        "product_id": 1,
        "brand": "나이키",
        "product_name": "운동화",
        "options": [
          {
            "product_detail_id": 1,
            "option": "에어맥스/245",
            "product_price": 100000,
            "stock_quantity": 150
          },
          {
            "product_detail_id": 2,
            "option": "슈퍼스타/270",
            "product_price": 99000,
            "stock_quantity": 88
          }
        ]
      },
      {
        "product_id": 2,
        "brand": "반스",
        "name": "운동화",
        "options": [
          {
            "product_detail_id": 1,
            "option": "메리제인/270",
            "product_price": 79000,
            "stock_quantity": 67
          }
        ]
      }
    ]
  }
  ```


# 상품 단건 조회 API

## 개요
- **Method** : `GET`
- **Endpoint** : `/products/{product_id}`
- **Description** : 상품을 단건 조회하는 API

---

## Request
- Request Parameter : `productId` - 상품 고유 ID

---



## Response

###  Response Headers
| Code  | Message                 | Description |
|------|-------------------------|-------------|
| `200` | `OK` | 요청 성공       |
| `404` | `NOT_FOUND` | 상품을 찾을 수 없음 |
| `500` | `INTERNAL_SERVER_ERROR` | 서버 오류 발생    |

---


### Response Body
  ```json
  {
    "code": 200,
    "message": "OK",
    "data": {
        "product_id": 1,
        "brand": "나이키",
        "product_name": "운동화",
        "options": [
          {
            "product_detail_id": 1,
            "option": "에어맥스/245",
            "product_price": 100000,
            "stock_quantity": 150
          }
        ]
      }
    }
  ```

# 주문 API

## 개요
- **Method** : `POST`
- **Endpoint** : `/order`
- **Description** : 상품을 주문하는 API

---

### Request Body
```json
{
  "user_id" : 1,
  "items": [
    {
      "product_detail_id": 1,
      "product_quantity": 5
    }
  ],
  "coupon_id": 1
}
```



## Response

###  Response Headers
| Code  | Message                 | Description |
|-------|-------------------------|-------------|
| `200` | `OK`                    | 요청 성공       |
| `400` | `BAD_REQUEST`           | 잘못된 요청      |
| `500` | `INTERNAL_SERVER_ERROR` | 서버 오류 발생    |

---


### Response Body
  ```json
  {
    "code": 200,
    "message": "OK",
    "data": {
      "order_id": 1,
      "user_id": 1,
      "status": "PENDING",
      "total_amount": "10000",
      "discount_amount": "1000",
      "payment_amount": "9000"
      }
  }
  ```

# 결제 API

## 개요
- **Method** : `POST`
- **Endpoint** : `/order/payment`
- **Description** : 상품을 결제하는 API

---

### Request Body
```json
{
  "order_id" : 1
}
```



## Response

###  Response Headers
| Code  | Message                 | Description |
|-------|-------------------------|-------------|
| `200` | `OK`                    | 요청 성공       |
| `400` | `BAD_REQUEST`           | 잘못된 요청      |
| `404` | `NOT FOUND`             | 주문을 찾을 수 없음 |
| `500` | `INTERNAL_SERVER_ERROR` | 서버 오류 발생    |

---


### Response Body
  ```json
  {
    "code": 200,
    "message": "OK"
  }
  ```

# 쿠폰 발급 요청 API

## 개요
- **Method** : `POST`
- **Endpoint** : `/coupons/{user_id}`
- **Description** : 쿠폰 발급 요청 API

---

### Request Body
```json
{
  "coupon_id" : 1
}
```



## Response

###  Response Headers
| Code  | Message                 | Description |
|-------|-------------------------|-------------|
| `200` | `OK`                    | 요청 성공       |
| `400` | `BAD_REQUEST`           | 잘못된 요청      |
| `500` | `INTERNAL_SERVER_ERROR` | 서버 오류 발생    |

---


### Response Body
  ```json
  {
    "code": 200,
    "message": "OK"
  }
  ```

# 쿠폰 목록 조회 API

## 개요
- **Method** : `GET`
- **Endpoint** : `/coupons/{user_id}`
- **Description** : 쿠폰 목록 조회 API

---



## Response

###  Response Headers
| Code  | Message                 | Description |
|-------|-------------------------|-------------|
| `200` | `OK`                    | 요청 성공       |
| `400` | `BAD_REQUEST`           | 잘못된 요청      |
| `404` | `NOT FOUND`             | 쿠폰이 존재하지 않음 |
| `500` | `INTERNAL_SERVER_ERROR` | 서버 오류 발생    |

---


### Response Body
  ```json
  {
    "code": 200,
    "message": "OK",
    "data": {
      "coupons": [
        {
          "coupon_id": 1,
          "coupon_name": "할인쿠폰1",
          "discount_amount": 3000,
          "coupon_status": "미사용",
          "expriation_at": "2026-01-01"
        }
      ]
    }
  }
  ```


# 인기 상품 조회 API

## 개요
- **Method** : `GET`
- **Endpoint** : `/products/popular`
- **Description** : 인기 상품 조회 API

---



## Response

###  Response Headers
| Code  | Message                 | Description |
|-------|-------------------------|-------------|
| `200` | `OK`                    | 요청 성공       |
| `500` | `INTERNAL_SERVER_ERROR` | 서버 오류 발생    |

---


### Response Body
  ```json
  {
    "code": 200,
    "message": "OK",
    "data": [
        {
          "product_id": 1,
          "brand": "나이키",
          "product_name": "운동화",
          "option":
          {
            "product_detail_id": 1,
            "option": "에어맥스/245",
            "product_price": 100000,
            "stock_quantity": 150
          }
        },
        {
          "product_id": 1,
          "brand": "나이키",
          "product_name": "운동화",
          "option":
          {
            "detail_id": 2,
            "option": "슈퍼스타/270",
            "product_price": 99000,
            "stock_quantity": 88
          }
        }
      ]
  }
  ```
