# 상품 API MSA 프로젝트

## 개요
`MSA Product API`는 마이크로서비스 아키텍처(MSA)를 기반으로 한 전자상거래 플랫폼의 제품 관리 서비스입니다. 이 프로젝트는 제품의 생성, 조회, 재고 관리를 핵심 기능으로 제공하며, Kafka를 통해 `msa-order-api`에서 발생하는 주문 이벤트와 통합되어 실시간 재고 업데이트를 처리합니다. Spring Boot와 JPA를 활용하여 RESTful API를 구현하며, Docker를 통해 MySQL 데이터베이스와 함께 컨테이너화된 환경에서 배포됩니다.


이 프로젝트는 전체 MSA 시스템의 일부로, 아래와 같은 관련 레포지토리와 함께 동작합니다:
- **[`msa-order-api`](https://github.com/3210439/msa-order-api)**: 주문 관리 핵심 서비스 (Kafka를 통해 이벤트 발행)
- **[`elk-stack-for-msa-server`](https://github.com/3210439/elk-stack-for-msa-server)**: ELK 스택을 사용한 로깅 시스템
- **[`order-api-monitoring`](https://github.com/3210439/order-api-monitoring)**: Prometheus와 Grafana를 사용한 모니터링 스택


---

## 아키텍처
이 서비스는 다음과 같은 흐름으로 동작합니다:
1. **제품 관리**: REST API를 통해 제품 데이터를 생성하고 조회.
2. **이벤트 통합**: Kafka를 통해 `msa-order-api`에서 발행된 주문 이벤트를 소비하여 재고를 업데이트.
3. **로깅**: `elk-stack-for-msa-server`와 연계하여 이벤트 및 API 호출 로그를 수집.
4. **모니터링**: `order-api-monitoring`에서 제공하는 Prometheus 메트릭 수집을 지원 (예: `/actuator/prometheus` 엔드포인트).

---

## 주요 기능
- **제품 생성 (`POST /products`)**:
  - 새로운 제품을 등록하며, 이름, 가격, 초기 재고 수량 등의 정보를 입력받습니다.
  - Swagger를 통해 API 요청 예시 제공.
- **제품 조회 (`GET /products/{id}`)**:
  - 제품 ID를 기반으로 제품 상세 정보(이름, 가격, 재고 등)를 JSON 형식으로 반환합니다.
- **재고 관리**:
  - Kafka의 `order-event` 토픽에서 주문 이벤트를 수신하여 실시간으로 재고를 감소시킵니다.

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

## 사전 요구 사항
- Docker 및 Docker Compose 설치
- Kafka 브로커(`kafka:29092`)가 실행 중이어야 함.
- `msa-order-api`가 동일한 네트워크(`msa-order-api_order-network`)에서 동작 중이어야 함.
