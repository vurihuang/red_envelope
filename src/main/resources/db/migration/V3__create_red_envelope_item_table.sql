CREATE TABLE IF NOT EXISTS red_envelope_item (
  `id`              BIGINT(11) NOT NULL AUTO_INCREMENT,
  `red_envelope_id` INT(10)    NOT NULL
  COMMENT '红包ID',
  `belong_to`       VARCHAR(15)         DEFAULT ''
  COMMENT '领取用户',
  `get_time`        DATETIME   NOT NULL,
  `amount`          DECIMAL(9, 2)       DEFAULT 0.0,
  PRIMARY KEY (`id`),
  INDEX `IDX_RED_ENVELOPE_ID` (`red_envelope_id`)
)
  ENGINE = InnoDB
  CHARSET utf8
  COLLATE utf8_bin;