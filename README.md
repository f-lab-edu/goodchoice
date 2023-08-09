# goodchoice


<p align="middle" >
  <img width="200px;" src="https://cdn-icons-png.flaticon.com/512/4812/4812872.png"/>
<!--   ![image](https://github.com/f-lab-edu/goodchoice/assets/46472768/c1f0a682-7982-466f-860b-84be2691f9f5) -->
</p>
<h1 align="middle">goodchoice</h1>
<p align="middle">실시간 최저가로, 예약을 한 번에! 내주변 숙소 예약 서비스 플랫폼 goodchoice입니다!</p>

## 프로젝트 소개

내주변 숙소 정보 제공, 예약 서비스입니다.


## 팀원 👨‍👨‍👧‍👧👩‍👦‍👦

|                                         Backend                                          |                                         Backend                                          |
| :--------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------: |
| <img src="" width=400px alt="선강"/> | <img src="" width=400px alt="봄봄"/> |
|                       [선강](https://github.com/zzangoobrother)                        |                            [은지](https://github.com/KATEKEITH)                            |       |



## 프로젝트 기술스택


## 프로젝트 아키텍쳐 🏛


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
