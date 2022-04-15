DROP TABLE IF EXISTS product_repay_flow;

CREATE TABLE product_repay_flow (
    repay_apply_id     bigint AUTO_INCREMENT COMMENT '主键（自增）' PRIMARY KEY,
    internal_order_num varchar(64)                 NULL COMMENT '业务流水号',
    create_time date COMMENT '创建时间'
);

INSERT INTO product_repay_flow VALUES (1, '568745896', now());