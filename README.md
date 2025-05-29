# Newsfeed

뉴스피드 개발 프로젝트

## 프로젝트 소개

Spring Boot에서 JPA와 JWT에 대한 이해를 바탕으로, 실제로 사용될 수 있는 뉴스피드 기능을 구현합니다.

## 사용하는 기술

`Java` `Spring Boot` `Github` `MySQL` `JPA` `JWT`

## 사용법

<details>
<summary> 환경변수 설정하기 </summary>

---

### 0. 현재 설정

현재 설정에 따라 환경 변수 `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` 을 설정해야하는 상황

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

### 1. 환경변수 설정하기

![envGuide1.png.png](readme/envGuide1.png)

### 2. 옵션 추가하기

`빌드 및 실행` 항목의   <u>옵션 수정(M)</u> 선택

아래 내용 체크 표시

    환경변수
    VM 옵션 추가

1. VM 옵션에 `-Dspring.profiles.active=local` 추가
2. <u>환경 변수(E)</u> 의 파일모양 📄 선택

![envGuide2.png](readme/envGuide2.png)

### 3. 환경변수 설정하기

필요한 환경변수 추가 후 적용

![envGuide3.png](readme/envGuide3.png)
![envGuide4.png](readme/envGuide4.png)
---

</details>