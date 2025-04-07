# 상품 API MSA 프로젝트

## 개요
MSA Product API는 마이크로서비스 아키텍처(MSA)를 기반으로 한 전자상거래 플랫폼의 제품 관리 서비스입니다. 이 프로젝트는 제품의 생성, 조회, 재고 관리를 제공하며, Kafka를 통해 주문 이벤트와 통합되어 실시간 재고 업데이트를 처리합니다. Spring Boot와 JPA를 활용하여 RESTful API를 구현하고, Docker를 통해 MySQL 데이터베이스와 함께 배포됩니다.

---

## 주요 기능
- **제품 생성**: 새로운 제품을 등록합니다. (`POST /products`)
- **제품 조회**: 제품 ID를 통해 제품 정보를 조회합니다. (`GET /products/{id}`)
- **재고 관리**: Kafka를 통해 주문 이벤트를 수신하여 제품 재고를 실시간으로 업데이트합니다.
- **자동 감사**: 생성 및 수정 시간을 추적합니다. (`BaseEntity`)

---

## 기술 스택
- **언어**: Java 11
- **프레임워크**: Spring Boot 2.7.0
- **데이터베이스**: MySQL 8.0 (via Docker)
- **ORM**: Spring Data JPA
- **메시징**: Spring Kafka
- **의존성 관리**: Gradle
- **API 문서화**: Swagger
- **유효성 검사**: Spring Validation
- **테스트**: JUnit 5 & Mockito (단위 테스트)
- **컨테이너**: Docker & Docker Compose

---
