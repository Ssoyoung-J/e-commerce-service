CREATE TABLE coupon (
                        coupon_id BIGINT NOT NULL AUTO_INCREMENT,
                        coupon_name VARCHAR(255) NOT NULL,
                        discount BIGINT NOT NULL,
                        quantity BIGINT,
                        created_at DATETIME NOT NULL,
                        updated_at DATETIME NOT NULL,
                        PRIMARY KEY (coupon_id)
);


CREATE TABLE user_coupon (
                             user_coupon_id BIGINT NOT NULL AUTO_INCREMENT,
                             user_id BIGINT NOT NULL,
                             coupon_id BIGINT NOT NULL,
                             coupon_name VARCHAR(255) NOT NULL,
                             user_coupon_status VARCHAR(20) NOT NULL,
                             created_at DATETIME NOT NULL,
                             updated_at DATETIME NOT NULL,
                             PRIMARY KEY (user_coupon_id)
);

CREATE TABLE order_item (
                            item_id BIGINT NOT NULL AUTO_INCREMENT,
                            order_id BIGINT NOT NULL,
                            product_detail_id BIGINT NOT NULL,
                            product_price BIGINT NOT NULL,
                            product_quantity INT NOT NULL,
                            user_coupon_id BIGINT NOT NULL,
                            created_at DATETIME NOT NULL,
                            updated_at DATETIME NOT NULL,
                            PRIMARY KEY (item_id)
);


CREATE TABLE `order` (
                         order_id BIGINT NOT NULL AUTO_INCREMENT,
                         user_id BIGINT NOT NULL,
                         user_coupon_id BIGINT NOT NULL,
                         orderStatus VARCHAR(20) NOT NULL,
                         orderedAt DATETIME NOT NULL,
                         total_amount BIGINT NOT NULL,
                         discount_amount BIGINT NOT NULL DEFAULT 0,
                         final_price BIGINT NOT NULL,
                         created_at DATETIME NOT NULL,
                         updated_at DATETIME NOT NULL,
                         PRIMARY KEY (order_id)
);


CREATE TABLE payment (
                         payment_id BIGINT NOT NULL AUTO_INCREMENT,
                         payment_status VARCHAR(20) NOT NULL,
                         payment_price BIGINT NOT NULL,
                         paid_at DATETIME NOT NULL,
                         order_id BIGINT NOT NULL,
                         created_at DATETIME NOT NULL,
                         updated_at DATETIME NOT NULL,
                         PRIMARY KEY (payment_id)
);


CREATE TABLE point (
                       point_id BIGINT NOT NULL AUTO_INCREMENT,
                       user_id BIGINT NOT NULL,
                       balance BIGINT NOT NULL DEFAULT 0,
                       pointAmount BIGINT NOT NULL,
                       created_at DATETIME NOT NULL,
                       updated_at DATETIME NOT NULL,
                       PRIMARY KEY (point_id)
);


CREATE TABLE point_history (
                               point_history_id BIGINT NOT NULL AUTO_INCREMENT,
                               user_id BIGINT NOT NULL,
                               transaction_type VARCHAR(10) NOT NULL,
                               point_amount BIGINT NOT NULL,
                               created_at DATETIME NOT NULL,
                               updated_at DATETIME NOT NULL,
                               PRIMARY KEY (point_history_id)
);


CREATE TABLE product (
                         product_id BIGINT NOT NULL AUTO_INCREMENT,
                         brand VARCHAR(255) NOT NULL,
                         product_name VARCHAR(255) NOT NULL,
                         created_at DATETIME NOT NULL,
                         updated_at DATETIME NOT NULL,
                         PRIMARY KEY (product_id)
);


CREATE TABLE product_detail (
                                product_detail_id BIGINT NOT NULL AUTO_INCREMENT,
                                product_id BIGINT NOT NULL,
                                option_name VARCHAR(255) NOT NULL,
                                product_price BIGINT NOT NULL,
                                created_at DATETIME NOT NULL,
                                updated_at DATETIME NOT NULL,
                                PRIMARY KEY (product_detail_id)
);


CREATE TABLE stock (
                       stock_id BIGINT NOT NULL AUTO_INCREMENT,
                       product_option_id BIGINT NOT NULL,
                       quantity INT NOT NULL,
                       created_at DATETIME NOT NULL,
                       updated_at DATETIME NOT NULL,
                       PRIMARY KEY (stock_id)
);


CREATE TABLE user (
                      user_id BIGINT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      phone_num VARCHAR(20) NOT NULL,
                      created_at DATETIME NOT NULL,
                      updated_at DATETIME NOT NULL,
                      PRIMARY KEY (user_id)
);
