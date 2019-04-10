CREATE TABLE IF NOT EXISTS `red_envelope` (
  `id`         INT(11)       NOT NULL AUTO_INCREMENT,
  `user_id`    VARCHAR(15)   NOT NULL
  COMMENT '用户ID',
  `sign`       VARCHAR(10)   NOT NULL
  COMMENT '口令',
  number       INT(9)        NOT NULL
  COMMENT '红包总个数',
  money        DECIMAL(9, 2) NOT NULL
  COMMENT '红包总金额',
  `created_at` DATETIME      NOT NULL
  COMMENT '创建时间',
  `expired_at` DATETIME      NOT NULL
  COMMENT '过期时间',
  PRIMARY KEY (`id`),
  INDEX `IDX_USER_ID` (`user_id`),
  INDEX `IDX_EXPIRED_TIME` (`expired_at`)
)
  ENGINE = InnoDB
  CHARSET = utf8
  COLLATE = utf8_bin;

