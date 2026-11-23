# Tlias 社員・組織管理バックエンドAPI (Tlias Backend Management System)

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![MyBatis](https://img.shields.io/badge/MyBatis-3.x-red?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![JWT](https://img.shields.io/badge/Auth-JWT-black?style=flat-square)

## 1. プロジェクト概要 (Overview)
企業の部署編成・従業員情報・職歴データおよび組織統計を一元管理するための **RESTful バックエンドAPIシステム** です。  
前職での **QAエンジニア（3年4ヶ月）としての品質保証・API検証の実務経験** と **Oracle Certified Java SE 17 Programmer (Silver)** の知識を活かし、「単なるCRUD実装」にとどまらず、**認証セキュリティ・AOPによる操作監査・トランザクション整合性・防御的例外ハンドリング** を重視して設計・実装しました。

---

## 2. システムアーキテクチャ・リクエスト処理フロー (Architecture)

```text
[Client / Postman]
       │
       ▼  HTTP Request + JWT Header
┌──────────────────────────────────────────────┐
│  TokenFilter (JWT認証フィルター)              │
│   ├─ トークン検証NG ──▶ 401 Unauthorized      │
│   └─ 検証OK ──▶ 社員IDをThreadLocalへ格納     │
└──────────────────────────────────────────────┘
       │
       ▼
┌──────────────────────────────────────────────┐
│  REST Controllers (/login, /depts, /emps...) │
│   └─ @Log アノテーション検知 ──▶ [Spring AOP] │
│       (操作履歴・実行時間をoperate_logへ記録)  │
└──────────────────────────────────────────────┘
       │
       ▼
┌──────────────────────────────────────────────┐
│  Service Layer (@Transactional トランザクション)│
└──────────────────────────────────────────────┘
       │
       ▼
┌──────────────────────────────────────────────┐
│  MyBatis Mapper ──▶ MySQL 8.0 (tlias DB)     │
└──────────────────────────────────────────────┘
```

---

## 3. 技術スタック (Tech Stack)

| カテゴリ | 使用技術・バージョン | 選定理由・実装ポイント |
| :--- | :--- | :--- |
| **言語・基盤** | Java 17 / Spring Boot 3.x | LTSバージョンの採用、AutoConfigurationによる迅速なAPI構築 |
| **データアクセス** | MyBatis / PageHelper | 動的SQLによる柔軟な複合条件検索・ページネーション実装 |
| **データベース** | MySQL 8.0 | 正規化されたリレーショナル設計（部署・従業員・職歴・監査ログ） |
| **認証・認可** | JWT (JSON Web Token) + Filter | ステートレス認証と `ThreadLocal` によるログイン操作者IDの透過的伝播 |
| **共通処理・品質** | Spring AOP / `@RestControllerAdvice` | カスタムアノテーション `@Log` による操作監査、グローバル例外集約処理 |
| **ビルド・検証** | Maven / Postman / DataGrip | 依存関係管理、全エンドポイントの正常系・異常系APIテスト検証 |

---

## 4. 工夫した設計・QA視点の品質ポイント (Key Highlights)

1. **Spring AOP による非侵入型の操作監査ログ (`@Log`)**
    * 部署・従業員の登録・更新・削除メソッドにカスタムアノテーション `@Log` を付与。ビジネスロジックを汚すことなく、操作社員ID・クラス名・メソッド名・引数・戻り値・処理時間（ms）を `operate_log` テーブルへ自動記録します。
2. **JWT 認証フィルターと `ThreadLocal` の連携**
    * リクエストヘッダーの JWT トークンを検証し、ペイロードから抽出したログイン社員IDを `ThreadLocal` に保持。処理完了後に確実にリソースを解放し、スレッドプール環境でのメモリリークとデータ汚染を防止しています。
3. **トランザクション制御によるデータ整合性の担保**
    * 従業員の新規登録・更新・一括削除時に、親テーブル（`emp`）と職歴子テーブル（`emp_expr`）の更新処理を `@Transactional(rollbackFor = Exception.class)` で単一トランザクション化し、部分的なデータ不整合を防ぎます。
4. **QA視点を反映したグローバル例外ハンドリング (`GlobalExceptionHandler`)**
    * データベースの一意制約違反（`DuplicateKeyException`）や不正なリクエストパラメータを専用ハンドラーで捕捉し、スタックトレースを外部へ露出させずに統一レスポンス形式（`Result`）で適切なエラーメッセージを返却します。

---

## 5. データベース設計 (Database Schema)

初期化スクリプトは [`SQL/init.sql`](SQL/init.sql) に格納しています。

* **`dept`（部署マスタ）**: 部署ID、部署名（一意制約）、作成日時、更新日時
* **`emp`（従業員マスタ）**: 社員ID、ユーザー名、パスワード、氏名、性別、電話番号、職種コード、給与、入社日、所属部署ID
* **`emp_expr`（従業員職歴）**: 職歴ID、従業員ID（`emp.id` と連携）、開始日、終了日、企業名、職種・役割
* **`emp_log`（従業員登録履歴ログ）**: 従業員登録時のスナップショット履歴
* **`operate_log`（AOP操作監査ログ）**: 操作者ID、操作日時、対象クラス・メソッド、引数、戻り値、実行時間（ms）

---

## 6. 主要APIエンドポイント一覧 (API Endpoints)

| メソッド | エンドポイント | 概要 | 認証 | AOP監査 |
| :--- | :--- | :--- | :---: | :---: |
| `POST` | `/login` | ログイン認証・JWTトークン発行 | 不要 | - |
| `GET` | `/depts` | 全部署一覧の取得 | 必要 | - |
| `GET` | `/depts/{id}` | ID指定による部署詳細の取得 | 必要 | - |
| `POST` | `/depts` | 部署の新規登録 | 必要 | `@Log` |
| `PUT` | `/depts` | 部署情報の更新 | 必要 | `@Log` |
| `DELETE` | `/depts` | ID指定による部署の削除 | 必要 | `@Log` |
| `GET` | `/emps` | 従業員一覧のページネーション・複合条件検索 | 必要 | - |
| `GET` | `/emps/{id}` | ID指定による従業員詳細・職歴情報の取得 | 必要 | - |
| `POST` | `/emps` | 従業員および職歴情報の新規登録 | 必要 | `@Log` |
| `PUT` | `/emps` | 従業員および職歴情報の更新 | 必要 | `@Log` |
| `DELETE` | `/emps` | ID指定による従業員の一括削除 | 必要 | `@Log` |
| `GET` | `/report/empJobData` | 職種別従業員数の集計データ取得 | 必要 | - |
| `GET` | `/report/empGenderData` | 性別従業員数の集計データ取得 | 必要 | - |

---

## 7. ローカル環境での起動手順 (Getting Started)

### 前提条件
* JDK 17 以上
* Maven 3.8 以上
* MySQL 8.0 以上

### セットアップ手順
1. **リポジトリのクローン**
   ```bash
   git clone https://github.com/Yowaki-sketch/tlias-web-leiduoqi.git
   cd tlias-web-leiduoqi
   ```
2. **データベースの初期化**
   MySQL に接続し、`SQL/init.sql` を実行して `tlias` データベースおよび初期データを投入します。
   ```bash
   mysql -u root -p < SQL/init.sql
   ```
3. **接続設定の更新**
   `src/main/resources/application.properties` のデータベース接続パスワードをご自身の環境に合わせて設定します。
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tlias?serverTimezone=Asia/Tokyo&useUnicode=true&characterEncoding=utf-8
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
4. **アプリケーションのビルドと起動**
   ```bash
   mvn clean package -DskipTests
   java -jar target/tlias-web-management-0.0.1-SNAPSHOT.jar
   ```
   起動後、`http://localhost:8080` にてAPIリクエストを受け付けます（初期検証用アカウント: `leiduoqi` / `1234567`）。
