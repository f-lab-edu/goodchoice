# goodchoice

DDD와 멀티모듈 구조를 사용했습니다. 각 도메인별 연관관계를 최대한 끊어내고 도메인 이벤트를 활용해 도메인간의 의존성을 줄였습니다.

# 구성

- 확장성을 고려한 역할에 맞는 멀티모듈
- service layer를 고립시켜 pojo로 단위테스트 작성
- Jenkins를 사용하여 CI/CD 구축
- Docker 를 이용하여 CD 구현
- Vault 서버를 띄워 암호, 설정값 관리
- Mysql Replication, Master/Slave로 데이터베이스 이중화


# issue
- 선착순 쿠폰 발급에서 재고 계산 동기화 문제 Redis 분산락 이용하여 해결
- 선착순 쿠폰 발급 실패 처리된 쿠폰들 배치로 쿠폰 발급 처리
