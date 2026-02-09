# Backend API Server

Spring Boot 기반의 RESTful API 서버입니다. JPA, MyBatis, SSE를 활용한 다양한 데이터 처리 방식을 데모하는 프로젝트입니다.

## 📋 목차

- [기술 스택](#-기술-스택)
- [주요 기능](#-주요-기능)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)
- [API 문서](#-api-문서)
- [데이터베이스](#-데이터베이스)

## 🛠 기술 스택

- **Java**: 21
- **Spring Boot**: 3.4.1
- **빌드 도구**: Gradle (Kotlin DSL)
- **데이터베이스**: H2 (In-memory)
- **ORM/Persistence**:
  - Spring Data JPA (Member 도메인)
  - MyBatis 3.0.3 (Post 도메인)
- **API 문서**: SpringDoc OpenAPI 3 (Swagger)
- **기타**: Lombok, Validation

## ✨ 주요 기능

### 1. 회원 관리 (Member) - JPA 방식
- Spring Data JPA를 사용한 CRUD 구현
- 엔티티 기반 ORM 패턴
- REST API 엔드포인트:
  - `GET /api/members` - 전체 회원 조회
  - `GET /api/members/{id}` - 회원 단건 조회
  - `POST /api/members` - 회원 생성
  - `PUT /api/members/{id}` - 회원 수정
  - `DELETE /api/members/{id}` - 회원 삭제

### 2. 게시글 관리 (Post) - MyBatis 방식
- MyBatis Mapper XML을 사용한 CRUD 구현
- SQL 중심의 매퍼 패턴
- REST API 엔드포인트:
  - `GET /api/posts` - 전체 게시글 조회
  - `GET /api/posts/{id}` - 게시글 단건 조회
  - `POST /api/posts` - 게시글 생성
  - `PUT /api/posts/{id}` - 게시글 수정
  - `DELETE /api/posts/{id}` - 게시글 삭제

### 3. 알림 시스템 (Notification) - SSE + Polling
- **SSE (Server-Sent Events)**: 실시간 알림 푸시
  - `GET /api/notifications/subscribe` - SSE 구독
- **Polling**: 주기적 알림 조회
  - `GET /api/notifications/poll` - 폴링 방식 조회
- **알림 발행**:
  - `POST /api/notifications/publish` - 알림 발행

## 📁 프로젝트 구조

```
src/main/java/com/example/backend/
├── BackendApplication.java
├── config/
│   ├── SwaggerConfig.java      # Swagger 설정
│   └── WebConfig.java           # CORS 등 웹 설정
├── member/                      # 회원 도메인 (JPA)
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
├── post/                        # 게시글 도메인 (MyBatis)
│   ├── controller/
│   ├── dto/
│   ├── mapper/
│   ├── model/
│   └── service/
└── notification/                # 알림 도메인 (SSE/Polling)
    ├── controller/
    ├── dto/
    └── service/

src/main/resources/
├── application.yml              # 애플리케이션 설정
├── mapper/
│   └── PostMapper.xml          # MyBatis SQL 매퍼
└── schema.sql                   # 테이블 초기화 스크립트
```

## 🚀 시작하기

### 필수 요구사항
- Java 21 이상
- Gradle 8.x

### 실행 방법

1. **프로젝트 클론**
```bash
git clone https://github.com/kht2199/backend.git
cd backend
```

2. **빌드**
```bash
./gradlew build
```

3. **실행**
```bash
./gradlew bootRun
```

서버는 기본적으로 `http://localhost:8080`에서 실행됩니다.

## 📖 API 문서

애플리케이션 실행 후 다음 주소에서 API 문서를 확인할 수 있습니다:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

Swagger UI에서 모든 API를 테스트할 수 있습니다.

## 🗄 데이터베이스

### H2 Console
개발 환경에서는 H2 인메모리 데이터베이스를 사용합니다.

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (비어있음)

### 테이블 구조

애플리케이션 시작 시 `schema.sql`을 통해 자동으로 테이블이 생성됩니다.

**Member 테이블** (JPA 관리):
```sql
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);
```

**Post 테이블** (MyBatis 관리):
```sql
CREATE TABLE post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🧪 테스트

```bash
./gradlew test
```

## 📝 라이센스

이 프로젝트는 학습 및 데모 목적으로 제작되었습니다.

## 👤 작성자

- GitHub: [@kht2199](https://github.com/kht2199)
