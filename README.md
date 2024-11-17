# product-backend

## 개요
이 프로젝트는 상품, 브랜드, 카테고리를 관리하기 위한 백엔드 서비스입니다. Java와 Spring Boot를 활용하여 구현되었습니다.

---

## 1. 프로젝트 의존성
- **Language**: Java (21)
- **Testing Framework**: Junit
- **Build Tool**: Gradle (8.10.2)
- **Framework**:
  - Spring Boot (3.3.5)
  - Spring Data JPA (3.3.5)
  - Springdoc OpenAPI: Swagger 기반 API 문서 제공 (2.4.0)
- **Database**: H2 (개발 및 테스트 환경에서 사용)
 ---
 
## 2. 프로젝트 구조
프로젝트는 두 개의 모듈로 구성되어 있습니다:
- **`product-data`**: Exception, Util, DTO와 같은 데이터 관리 기능을 포함합니다.
- **`product-api`**: 서비스와 컨트롤러 로직을 포함하여 REST API를 제공합니다.
---

## 3. Design Pattern
이 프로젝트는 다음과 같은 설계 패턴을 사용합니다:
- **계층형 아키텍처 (Layered Architecture)**: 컨트롤러, 서비스, 리포지토리 계층으로 역할을 분리.
- **DTO (Data Transfer Object)**: 계층 간 통신 및 API 응답 구조화를 위해 사용.

---
## 4. Database Design
### Tables
###  1NF~3NF 기반의 테이블 분리
테이블을 1NF(First Normal Form)부터 3NF(Third Normal Form)까지 정규화하는 이유는 데이터 중복 제거와 데이터 무결성을 유지하기 위해서입니다.

#### ``Category``
| Name     | Type    | Comment |
| -------- | ------- |---------|
| id       | Long   | PK      |
| name     | String  |         |


#### ``Brand``
| Name | Type    | Comment |
|------|---------|---------|
| id   | Long    | PK      |
| name | String  |         |


#### ``Product``
| Name          | Type    | Comment       |
|---------------|---------|---------------|
| id            | String  | PK            |
| category_id   | Long    | FK(ManyToOne) |
| brand_id      | Long    | FK(ManyToOne) |
| price         | Integer |               |

`Product` 테이블은 `Category`와 `Brand` 테이블과 연결되어 있으며, 각 상품의 가격 정보를 저장합니다.

---
# 5. 테스트


### **1. 유닛 테스트 (Unit Test)**

- **서비스 계층 테스트**:
  - 서비스 로직 테스트 (예: 카테고리별 최저가 상품 조회, 유효성 검사).

### **2. 통합 테스트 (Integration Test)**

- **컨트롤러 계층 테스트**:
  - REST 엔드포인트의 동작과 컨트롤러-서비스 계층 간 통합 테스트.

---

# 6. 실행방법

## 실행경로
1. 프로젝트의 root 경로에서 `./gradlew build` 빌드 파일 생성
2. `product-api` 모듈 디렉토리로 이동한 후:

3. 도커 이미지 빌드
```bash
docker build -t product-api .
```
4. 도커 이미지 실행
```bash
docker run -p 8080:8080 product-api
```

5. Swagger URL로 접속해 UI로 실행  
```
http://localhost:8080/swagger-ui/index.html#/ 
``` 
---

## 7. 기능

### API 엔드포인트

#### **1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회**
- **엔드포인트**: `/api/products/cheapest`
- **HTTP 메서드**: `GET`
- **설명**: 각 카테고리에서 가격이 가장 저렴한 상품의 브랜드와 가격을 조회하고, 총액을 계산합니다.

#### **2. 단일 브랜드로 모든 카테고리 상품 구매 시 최저가격 조회**
- **엔드포인트**: `/api/products/brand`
- **HTTP 메서드**: `GET`
- **설명**: 단일 브랜드로 모든 카테고리 상품 구매 시 최저가격 조회

#### **3. 카테고리 이름으로 최저, 최고 가격 조회**
- **엔드포인트**: `/api/products/category/{categoryName}`
- **HTTP 메서드**: `GET`
- **설명**: 특정 카테고리에서 최저가 상품과 최고가 상품을 조회합니다.

#### **4. 상품 추가**
- **엔드포인트**: `/api/products`
- **HTTP 메서드**: `POST`
- **설명**: 새 상품을 추가합니다. 동일한 카테고리-브랜드 조합의 중복 상품은 허용되지 않습니다.

#### **5. 상품 업데이트**
- **엔드포인트**: `/api/products`
- **HTTP 메서드**: `PUT`
- **설명**: 기존 상품 정보를 업데이트합니다.

#### **6. 상품 삭제**
- **엔드포인트**: `/api/products`
- **HTTP 메서드**: `DELETE`
- **설명**: 상품 ID를 기준으로 데이터를 삭제합니다.

---

## 8. 개발 환경

### 프로파일

- **local**: H2 데이터베이스를 사용하는 로컬 개발 환경. DataInitializer 클래스를 통해 어플리케이션 시작 시에 Dummy 데이터가 추가됩니다.


### 설정 파일

- `src/main/resources/application.yml`: 기본 설정 파일.

---
