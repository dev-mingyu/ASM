# ASM API-Service


## 기능
1. Seed 스캔 결과 저장
2. 서브도메인 목록 조회
3. 소프트웨어 목록 조회
4. CVE 목록 조회


## 설치 방법
- 요구 사항
  - Java 11
  - Spring Boot 2.7.17
  - Spring Data JPA
  - H2 DataBase
  - Gradle

### Git 저장소 복제
    https://github.com/dev-mingyu/asm.git

### 의존성 설치
    ./gradlew build

### 데이터베이스 설정
- H2 DataBase 사용
- ID/PW: application.yml 확인
- DataBase Url: <http://localhost:8080/api/h2-console>


## API 문서
1. 의존성 설치 및 빌드 완료
2. 서버 실행 후 접속
   - RestDocs Url: <http://localhost:8080/api/docs/api-docs.html>


## 기타
- CVE 목록 저장: src/main/resource/import.sql 적용