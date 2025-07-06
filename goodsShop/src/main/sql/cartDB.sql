USE goodsDB;

CREATE TABLE IF NOT EXISTS cart ( -- 장바구니 테이블
    cart_id         INT AUTO_INCREMENT PRIMARY KEY,   -- 장바구니 고유 ID
    m_id            VARCHAR(10) NOT NULL,             -- 회원 ID (member.m_id와 동일)
    m_name          VARCHAR(20),                      -- 회원 이름 (member.m_name과 동일)
    i_id            VARCHAR(10) NOT NULL,             -- 상품 ID (items의 i_id와 동일)
    i_name          VARCHAR(20),                      -- 상품 이름
    i_unitPrice     INTEGER NOT NULL,                 -- 단가
    i_category      VARCHAR(10),                      -- 카테고리 (Home, Aways, Acc)
    i_size          VARCHAR(10),                      -- 사이즈 (M, FREE 등)
    i_quantity      INT NOT NULL,                     -- 수량
    c_total         INT                               -- 총합계 (단가 × 수량)
) DEFAULT CHARSET=utf8;

INSERT INTO cart (m_id, m_name, i_id, i_name, i_unitPrice, i_category, i_size, i_quantity, c_total) VALUES
('user01', '전민기', '25HOME', '25홈유니폼', 109000, 'Home', 'M', 1, 109000),
('user01', '전민기', '25AWAYS', '25원정유니폼', 109000, 'Aways', 'M', 1, 109000),
('user01', '전민기', '25Keyring', '25마스코트키링', 15000, 'Acc', 'FREE', 1, 15000),
('user02', '김샛별', '25AWAYS', '25원정유니폼', 109000, 'Aways', 'M', 1, 109000),
('user03', '이하늘', '25Ball', '25싸인볼', 20000, 'Acc', 'FREE', 2, 40000),
('user04', '배혜민', '25Keyring', '25마스코트키링', 15000, 'Acc', 'FREE', 1, 15000);

SELECT * FROM cart;
DELETE FROM cart;
drop table cart;



 -- 주문 요약 테이블 

CREATE TABLE IF NOT EXISTS orders (
    order_id      INT AUTO_INCREMENT PRIMARY KEY,       -- 주문 고유 번호
    m_id          VARCHAR(10) NOT NULL,                 -- 회원 ID
    m_name        VARCHAR(20),                          -- 회원 이름
    order_total   INT NOT NULL,                         -- 전체 결제 금액
    order_date    DATETIME DEFAULT CURRENT_TIMESTAMP,   -- 결제 일시
    phone         VARCHAR(20),                          -- 연락처
    address       VARCHAR(200),                         -- 배송 주소
    memo          TEXT                                  -- 배송 요청사항
) DEFAULT CHARSET=utf8;

ALTER TABLE orders ADD payment_method VARCHAR(50);

INSERT INTO orders (m_id, m_name, order_total, order_date, phone, address, memo) VALUES
('user01', '전민기', 218000, '2025-07-03 10:30:00', '010-1234-5678', '경기 수원시 월드컵로 310', '부재 시 문 앞에 놔주세요'),
('user02', '김샛별', 109000, '2025-07-03 11:15:00', '010-2345-6789', '서울시 마포구 홍익로 25', '직접 전달 부탁드립니다'),
('user03', '이하늘', 40000,  '2025-07-03 12:00:00', '010-3456-7890', '부산시 해운대구 우동 123', ''),
('user04', '배혜민', 15000,  '2025-07-03 12:45:00', '010-4567-8901', '대전시 유성구 궁동 456', '경비실 맡겨주세요');


SELECT * FROM orders;
DELETE FROM oorders;
drop table orders;



-- 주문 상세 테이블

CREATE TABLE IF NOT EXISTS order_items ( 
    order_item_id   INT AUTO_INCREMENT PRIMARY KEY,  -- 주문 상세 고유 번호
    order_id        INT NOT NULL,                    -- 연결된 주문 ID
    i_id            VARCHAR(10) NOT NULL,            -- 상품 ID
    i_name          VARCHAR(20),                     -- 상품 이름
    i_unitPrice     INT NOT NULL,                    -- 단가
    i_size          VARCHAR(10),                     -- 사이즈
    i_quantity      INT NOT NULL,                    -- 수량
    item_total      INT                              -- 소계 (단가 × 수량)
) DEFAULT CHARSET=utf8;


-- 주문 ID 1번 (전민기: 홈유니폼 + 원정유니폼 = 총 218,000원)
INSERT INTO order_items (order_id, i_id, i_name, i_unitPrice, i_size, i_quantity, item_total) VALUES
(1, '25HOME', '25홈유니폼', 109000, 'M', 1, 109000),(1, '25AWAYS', '25원정유니폼', 109000, 'M', 1, 109000);

-- 주문 ID 2번 (김샛별: 원정유니폼 1개 = 109,000원)
INSERT INTO order_items (order_id, i_id, i_name, i_unitPrice, i_size, i_quantity, item_total) VALUES
(1, '25AWAYS', '25원정유니폼', 109000, 'M', 1, 109000);

-- 주문 ID 3번 (이하늘: 싸인볼 2개 = 40,000원)
INSERT INTO order_items (order_id, i_id, i_name, i_unitPrice, i_size, i_quantity, item_total) VALUES
(3, '25Ball', '25싸인볼', 20000, 'FREE', 2, 40000);

-- 주문 ID 4번 (배혜민: 키링 1개 = 15,000원)
INSERT INTO order_items (order_id, i_id, i_name, i_unitPrice, i_size, i_quantity, item_total) VALUES
(4, '25Keyring', '25마스코트키링', 15000, 'FREE', 1, 15000);

SELECT * FROM order_items;
DELETE FROM order_items;
drop table order_items;

DESCRIBE orders;

SELECT * FROM cart WHERE m_id = 'user01';