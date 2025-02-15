CREATE TABLE IF NOT EXISTS persistent_logins(
  username VARCHAR(64) NOT NULL COMMENT 'ログインID'
  , ip_address VARCHAR(64) NOT NULL COMMENT 'IPアドレス'
  , user_agent VARCHAR(200) NOT NULL COMMENT 'UserAgent'
  , series VARCHAR(64) COMMENT '直列トークン'
  , token VARCHAR(64) NOT NULL COMMENT 'トークン'
  , last_used DATETIME NOT NULL COMMENT '最終利用日'
  , PRIMARY KEY (series)
  , KEY idx_persistent_logins(username, ip_address, user_agent)
  , KEY idx_persistent_logins_01(last_used)
) COMMENT='ログイン記録';

CREATE TABLE IF NOT EXISTS code_categories(
  code_category_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'コード分類ID'
  , category_code VARCHAR(50) NOT NULL COMMENT '分類コード'
  , category_name VARCHAR(50) NOT NULL COMMENT '分類名'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (code_category_id)
  , KEY idx_code_categories (category_code, deleted_at)
) COMMENT='コード分類';

CREATE TABLE IF NOT EXISTS codes(
  code_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'コードID'
  , category_code VARCHAR(50) NOT NULL COMMENT '分類コード'
  , code_value VARCHAR(100) NOT NULL COMMENT 'コード値'
  , code_name VARCHAR(50) NOT NULL COMMENT 'コード名'
  , code_alias VARCHAR(100) DEFAULT NULL COMMENT 'コードエイリアス'
  , display_order INT(11) DEFAULT 0 COMMENT '表示順'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (code_id)
  , KEY idx_codes (category_code, deleted_at)
) COMMENT='コード';

CREATE TABLE IF NOT EXISTS permissions(
  permission_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '権限ID'
  , permission_code VARCHAR(50) NOT NULL COMMENT '権限コード'
  , permission_name VARCHAR(50) NOT NULL COMMENT '権限名'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (permission_id)
  , KEY idx_permissions (permission_code, deleted_at)
) COMMENT='権限';

CREATE TABLE IF NOT EXISTS roles(
  role_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ロールID'
  , role_code VARCHAR(50) NOT NULL COMMENT 'ロールコード'
  , role_name VARCHAR(100) NOT NULL COMMENT 'ロール名'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (role_id)
  , KEY idx_roles (role_code, deleted_at)
) COMMENT='ロール';

CREATE TABLE IF NOT EXISTS role_permissions(
  role_permission_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ロール権限紐付けID'
  , role_code VARCHAR(50) NOT NULL COMMENT 'ロールコード'
  , permission_code VARCHAR(50) NOT NULL COMMENT '権限コード'
  , is_enabled TINYINT(1) NOT NULL DEFAULT 0 COMMENT '有効フラグ'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (role_permission_id)
  , KEY idx_role_permissions (role_code, deleted_at)
) COMMENT='ロール権限紐付け';

CREATE TABLE IF NOT EXISTS staff_roles(
  staff_role_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '担当者ロールID'
  , staff_id INT(11) unsigned NOT NULL COMMENT '担当者ID'
  , role_code VARCHAR(50) NOT NULL COMMENT 'ロールコード'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (staff_role_id)
  , KEY idx_staff_roles (staff_id, role_code, deleted_at)
) COMMENT='担当者ロール';

CREATE TABLE IF NOT EXISTS user_roles(
  user_role_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ユーザーロールID'
  , user_id INT(11) unsigned NOT NULL COMMENT 'ユーザーID'
  , role_code VARCHAR(50) NOT NULL COMMENT 'ロールコード'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (user_role_id)
  , KEY idx_user_roles (user_id, role_code, deleted_at)
) COMMENT='ユーザーロール';

CREATE TABLE IF NOT EXISTS staffs(
  staff_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '担当者ID'
  , first_name VARCHAR(40) NOT NULL COMMENT '名'
  , last_name VARCHAR(40) NOT NULL COMMENT '性'
  , email VARCHAR(100) DEFAULT NULL COMMENT 'メールアドレス'
  , password VARCHAR(100) DEFAULT NULL COMMENT 'パスワード'
  , tel VARCHAR(20) DEFAULT NULL COMMENT '電話番号'
  , password_reset_token VARCHAR(50) DEFAULT NULL COMMENT 'パスワードリセットトークン'
  , token_expires_at DATETIME DEFAULT NULL COMMENT 'トークン失効日'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (staff_id)
  , KEY idx_staffs (email, deleted_at)
) COMMENT='担当者';

CREATE TABLE IF NOT EXISTS users(
  user_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ユーザID'
  , first_name VARCHAR(40) NOT NULL COMMENT '名'
  , last_name VARCHAR(40) NOT NULL COMMENT '性'
  , email VARCHAR(100) DEFAULT NULL COMMENT 'メールアドレス'
  , password VARCHAR(100) DEFAULT NULL COMMENT 'パスワード'
  , tel VARCHAR(20) DEFAULT NULL COMMENT '電話番号'
  , zip VARCHAR(20) DEFAULT NULL COMMENT '郵便番号'
  , address VARCHAR(100) DEFAULT NULL COMMENT '住所'
  , upload_file_id INT(11) unsigned DEFAULT NULL COMMENT '添付ファイル'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (user_id)
  , KEY idx_users (email, deleted_at)
) COMMENT='ユーザー';

CREATE TABLE IF NOT EXISTS upload_files(
  upload_file_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ファイルID'
  , file_name VARCHAR(100) NOT NULL COMMENT 'ファイル名'
  , original_file_name VARCHAR(200) NOT NULL COMMENT 'オリジナルファイル名'
  , content_type VARCHAR(50) NOT NULL COMMENT 'コンテンツタイプ'
  , content LONGBLOB NOT NULL COMMENT 'コンテンツ'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (upload_file_id)
  , KEY idx_upload_files (file_name, deleted_at)
) COMMENT='アップロードファイル';

CREATE TABLE IF NOT EXISTS mail_templates(
  mail_template_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'メールテンプレートID'
  , category_code VARCHAR(50) DEFAULT NULL COMMENT 'テンプレート分類コード'
  , template_code VARCHAR(100) NOT NULL COMMENT 'テンプレートコード'
  , subject VARCHAR(100) NOT NULL COMMENT 'メールタイトル'
  , template_body TEXT NOT NULL COMMENT 'メール本文'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (mail_template_id)
  , KEY idx_mail_templates (template_code, deleted_at)
) COMMENT='メールテンプレート';

CREATE TABLE IF NOT EXISTS send_mail_queue(
  send_mail_queue_id BIGINT(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'メール送信キューID'
  , from_address varchar(255) NOT NULL COMMENT 'fromアドレス'
  , to_address varchar(255) DEFAULT NULL COMMENT 'toアドレス'
  , cc_address varchar(255) DEFAULT NULL COMMENT 'ccアドレス'
  , bcc_address varchar(255) DEFAULT NULL COMMENT 'bccアドレス'
  , subject varchar(255) DEFAULT NULL COMMENT '件名'
  , body TEXT
  , sent_at DATETIME DEFAULT NULL COMMENT 'メール送信日時'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (send_mail_queue_id, created_at)
  , KEY idx_send_mail_queue (sent_at, deleted_at)
) COMMENT='メール送信キュー' PARTITION BY RANGE (YEAR(created_at))(
  PARTITION p2017 VALUES LESS THAN (2017),
  PARTITION p2018 VALUES LESS THAN (2018),
  PARTITION p2019 VALUES LESS THAN (2019),
  PARTITION p2020 VALUES LESS THAN (2020),
  PARTITION p2021 VALUES LESS THAN (2021),
  PARTITION p2022 VALUES LESS THAN (2022),
  PARTITION p2023 VALUES LESS THAN (2023),
  PARTITION p2024 VALUES LESS THAN (2024),
  PARTITION p2025 VALUES LESS THAN (2025),
  PARTITION p2026 VALUES LESS THAN (2026),
  PARTITION p2027 VALUES LESS THAN (2027),
  PARTITION p2028 VALUES LESS THAN (2028),
  PARTITION p2029 VALUES LESS THAN (2029),
  PARTITION p2030 VALUES LESS THAN (2030)
);

CREATE TABLE IF NOT EXISTS holidays(
  holiday_id INT(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '祝日ID'
  , holiday_name VARCHAR(100) NOT NULL COMMENT '祝日名'
  , holiday_date DATE NOT NULL COMMENT '日付'
  , created_by VARCHAR(50) NOT NULL COMMENT '登録者'
  , created_at DATETIME NOT NULL COMMENT '登録日時'
  , updated_by VARCHAR(50) DEFAULT NULL COMMENT '更新者'
  , updated_at DATETIME DEFAULT NULL COMMENT '更新日時'
  , deleted_by VARCHAR(50) DEFAULT NULL COMMENT '削除者'
  , deleted_at DATETIME DEFAULT NULL COMMENT '削除日時'
  , version INT(11) unsigned NOT NULL DEFAULT 1 COMMENT '改訂番号'
  , PRIMARY KEY (holiday_id)
  , KEY idx_holidays (holiday_name, deleted_at)
) COMMENT='祝日';
