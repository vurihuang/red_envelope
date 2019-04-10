CREATE TABLE IF NOT EXISTS user (
  `id`       INT(11)       NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(15)   NOT NULL
  COMMENT '用户名称',
  `amount`   DECIMAL(9, 2) NOT NULL DEFAULT 0
  COMMENT '余额',
  PRIMARY KEY (`id`),
  INDEX `IDX_USER_ID` (`username`)
)
  ENGINE = InnoDB
  CHARSET utf8
  COLLATE utf8_bin;