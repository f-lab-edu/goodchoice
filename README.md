# goodchoice


<p align="middle" >
  <img width="200px;" src="https://cdn-icons-png.flaticon.com/512/4812/4812872.png"/>
<!--   ![image](https://github.com/f-lab-edu/goodchoice/assets/46472768/c1f0a682-7982-466f-860b-84be2691f9f5) -->
</p>
<h1 align="middle">goodchoice</h1>
<p align="middle">실시간 최저가로, 예약을 한 번에! 내주변 숙소 예약 서비스 플랫폼 goodchoice입니다!</p>

## 프로젝트 소개

내주변 숙소 정보 제공, 예약 서비스입니다.

수정 테스트

## 팀원 👨‍👨‍👧‍👧👩‍👦‍👦

|                                         Backend                                          |                                         Backend                                          |
| :--------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------: |
| <img src="" width=400px alt="선강"/> | <img src="" width=400px alt="봄봄"/> |
|                       [선강](https://github.com/zzangoobrother)                        |                            [은지](https://github.com/KATEKEITH)                            |       |

## 기술 스택
![Java](https://img.shields.io/badge/Java-17-007396?logo=java&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-6DB33F?logo=spring%20boot&logoColor=6DB33F) ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?logo=swagger&logoColor=85EA2D) ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?logo=&logoColor=6DB33F) ![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=4479A1) ![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=02303A) ![IntelliJ IDEA](https://img.shields.io/badge/-IntelliJ%20IDEA-FF0000?logo=intellij%20idea&logoColor=white)

## 프로젝트 중점사항

- 확장성을 고려한 역할에 맞는 멀티모듈
- service layer를 고립시켜 pojo로 단위테스트 작성
- Jenkins를 사용하여 CI/CD 구축
- Docker 를 이용하여 CD 구현
- Vault 서버를 띄워 암호, 설정값 관리
- Mysql Replication, Master/Slave로 데이터베이스 이중화


## 기술적 issue 해결 과정

- 선착순 쿠폰 발급에서 재고 계산 동기화 문제 Redis 분산락 이용하여 해결
- 선착순 쿠폰 발급 실패 처리된 쿠폰들 배치로 쿠폰 발급 처리
