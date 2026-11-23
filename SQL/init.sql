-- ==========================================================
-- Tlias 社員・組織管理システム データベース初期化スクリプト
-- ==========================================================
CREATE DATABASE IF NOT EXISTS tlias DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tlias;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------
-- 1. 部署テーブル (dept)
-- ----------------------------------------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '部署名',
  `create_time` datetime DEFAULT NULL COMMENT '作成日時',
  `update_time` datetime DEFAULT NULL COMMENT '更新日時',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dept_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部署マスタ';

INSERT INTO `dept` (`id`, `name`, `create_time`, `update_time`) VALUES
(1, '労務部', '2024-09-25 09:47:40', '2026-10-02 12:41:56'),
(2, '人事部', '2026-09-05 17:43:46', '2026-09-05 17:43:46'),
(3, '財務部', '2024-09-25 09:47:40', '2024-09-30 21:26:24'),
(4, '総務部', '2024-09-25 09:47:40', '2024-09-25 09:47:40'),
(5, '事業部', '2024-09-25 09:47:40', '2024-09-25 09:47:40'),
(8, '営業部', '2026-09-05 17:48:35', '2026-09-05 19:19:58');

-- ----------------------------------------------------------
-- 2. 従業員テーブル (emp)
--    gender: 1=男性, 2=女性
--    job: 1=システムエンジニア, 2=バックエンドエンジニア, 3=QAエンジニア, 4=プロジェクトマネージャー, 5=営業担当
-- ----------------------------------------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(30) NOT NULL COMMENT 'ユーザー名',
  `password` varchar(64) DEFAULT '123456' COMMENT 'パスワード',
  `name` varchar(30) NOT NULL COMMENT '氏名',
  `gender` tinyint unsigned NOT NULL COMMENT '性別 (1:男, 2:女)',
  `phone` varchar(20) NOT NULL COMMENT '電話番号',
  `job` tinyint unsigned DEFAULT NULL COMMENT '職種',
  `salary` int unsigned DEFAULT NULL COMMENT '給与',
  `image` varchar(255) DEFAULT NULL COMMENT 'プロフィール画像URL',
  `entry_date` date DEFAULT NULL COMMENT '入社日',
  `dept_id` int unsigned DEFAULT NULL COMMENT '所属部署ID',
  `create_time` datetime DEFAULT NULL COMMENT '作成日時',
  `update_time` datetime DEFAULT NULL COMMENT '更新日時',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_emp_username` (`username`),
  UNIQUE KEY `uk_emp_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='従業員マスタ';

INSERT INTO `emp` (`id`, `username`, `password`, `name`, `gender`, `phone`, `job`, `salary`, `image`, `entry_date`, `dept_id`, `create_time`, `update_time`) VALUES
(1, 'sato_kenji', '123456', '佐藤 健二', 1, '09011110001', 4, 650000, 'https://example.com/avatar/01.png', '2018-04-01', 5, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(2, 'suzuki_ichiro', '123456', '鈴木 一郎', 1, '09011110002', 2, 480000, 'https://example.com/avatar/02.png', '2020-04-01', 5, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(3, 'takahashi_misaki', '123456', '高橋 美咲', 2, '09011110003', 3, 420000, 'https://example.com/avatar/03.png', '2021-04-01', 5, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(4, 'tanaka_hiroshi', '123456', '田中 浩', 1, '09011110004', 2, 500000, 'https://example.com/avatar/04.png', '2019-10-01', 5, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(5, 'ito_yumi', '123456', '伊藤 由美', 2, '09011110005', 1, 390000, 'https://example.com/avatar/05.png', '2022-04-01', 2, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(6, 'watanabe_daiki', '123456', '渡辺 大輝', 1, '09011110006', 2, 460000, 'https://example.com/avatar/06.png', '2021-07-01', 5, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(7, 'yamamoto_kenta', '123456', '山本 健太', 1, '09011110007', 1, 410000, 'https://example.com/avatar/07.png', '2022-10-01', 1, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(8, 'nakamura_aya', '123456', '中村 彩', 2, '09011110008', 5, 430000, 'https://example.com/avatar/08.png', '2020-04-01', 8, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(9, 'kobayashi_ryota', '123456', '小林 亮太', 1, '09011110009', 2, 470000, 'https://example.com/avatar/09.png', '2021-01-15', 5, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(10, 'kato_naoko', '123456', '加藤 直子', 2, '09011110010', 1, 400000, 'https://example.com/avatar/10.png', '2023-04-01', 3, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(11, 'yoshida_takuya', '123456', '吉田 拓哉', 1, '09011110011', 5, 450000, 'https://example.com/avatar/11.png', '2019-04-01', 8, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(12, 'yamada_shota', '123456', '山田 翔太', 1, '09011110012', 3, 415000, 'https://example.com/avatar/12.png', '2022-04-01', 5, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(13, 'sasaki_haruka', '123456', '佐々木 遥', 2, '09011110013', 1, 385000, 'https://example.com/avatar/13.png', '2024-04-01', 4, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(14, 'matsumoto_yuji', '123456', '松本 雄二', 1, '09011110014', 2, 520000, 'https://example.com/avatar/14.png', '2017-04-01', 5, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(15, 'inoue_mai', '123456', '井上 舞', 2, '09011110015', 5, 440000, 'https://example.com/avatar/15.png', '2021-04-01', 8, '2025-10-20 10:00:00', '2026-10-20 10:00:00'),
(101, 'leiduoqi', '1234567', '雷 多奇', 1, '07033007248', 2, 500000, 'https://example.com/avatar/admin.png', '2026-11-12', 5, '2026-11-12 09:00:00', '2026-11-22 10:00:00');

-- ----------------------------------------------------------
-- 3. 従業員職歴テーブル (emp_expr)
-- ----------------------------------------------------------
DROP TABLE IF EXISTS `emp_expr`;
CREATE TABLE `emp_expr` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `emp_id` int unsigned NOT NULL COMMENT '従業員ID',
  `begin` date DEFAULT NULL COMMENT '開始日',
  `end` date DEFAULT NULL COMMENT '終了日',
  `company` varchar(50) DEFAULT NULL COMMENT '企業名',
  `job` varchar(50) DEFAULT NULL COMMENT '職種・役割',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='従業員職歴';

INSERT INTO `emp_expr` (`id`, `emp_id`, `begin`, `end`, `company`, `job`) VALUES
(1, 2, '2017-04-01', '2020-03-31', '株式会社東京システムズ', 'Javaバックエンド開発'),
(2, 3, '2018-04-01', '2021-03-31', '横浜ソフトウェア株式会社', 'QAエンジニア・API検証'),
(3, 4, '2016-04-01', '2019-09-30', '株式会社関東クラウドソリューションズ', 'Webアプリケーション開発'),
(4, 14, '2014-04-01', '2017-03-31', '株式会社ネクストテック', 'システムアーキテクト'),
(5, 101, '2023-04-01', '2026-07-31', '株式会社テクノロジーパートナーズ', 'QAエンジニア・品質保証');

-- ----------------------------------------------------------
-- 4. 従業員登録ログテーブル (emp_log)
-- ----------------------------------------------------------
DROP TABLE IF EXISTS `emp_log`;
CREATE TABLE `emp_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operate_time` datetime DEFAULT NULL COMMENT '操作日時',
  `info` varchar(2000) DEFAULT NULL COMMENT 'ログ詳細情報',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='従業員登録履歴ログ';

INSERT INTO `emp_log` (`id`, `operate_time`, `info`) VALUES
(1, '2026-11-12 09:05:12', '新規従業員登録: Emp(id=101, username=leiduoqi, name=雷 多奇, gender=1, deptId=5)'),
(2, '2026-11-15 14:20:30', '新規従業員登録: Emp(id=15, username=inoue_mai, name=井上 舞, gender=2, deptId=8)');

-- ----------------------------------------------------------
-- 5. システム操作監査ログテーブル (operate_log) - Spring AOP記録用
-- ----------------------------------------------------------
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operate_emp_id` int unsigned DEFAULT NULL COMMENT '操作者従業員ID',
  `operate_time` datetime DEFAULT NULL COMMENT '操作日時',
  `class_name` varchar(100) DEFAULT NULL COMMENT '対象クラス名',
  `method_name` varchar(100) DEFAULT NULL COMMENT '対象メソッド名',
  `method_params` varchar(2000) DEFAULT NULL COMMENT 'メソッド引数',
  `return_value` varchar(2000) DEFAULT NULL COMMENT '戻り値',
  `cost_time` bigint unsigned DEFAULT NULL COMMENT '実行時間(ms)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AOP操作監査ログ';

INSERT INTO `operate_log` (`id`, `operate_emp_id`, `operate_time`, `class_name`, `method_name`, `method_params`, `return_value`, `cost_time`) VALUES
(1, 101, '2026-11-22 19:30:10', 'com.atlei.controller.DeptController', 'addDept', '[Dept(id=8, name=営業部)]', 'Result(code=1, msg=success, data=null)', 18),
(2, 101, '2026-11-22 19:35:42', 'com.atlei.controller.EmpController', 'update', '[Emp(id=14, name=松本 雄二)]', 'Result(code=1, msg=success, data=null)', 24);

SET FOREIGN_KEY_CHECKS = 1;