# ⚽️ 축구 동호회 경기 매칭 서비스(SoccerFriend) ⚽️ 
축구 클럽 간 경기 매칭 서비스입니다. 누구나 클럽을 개설하고 클럽에 참여할 수 있습니다. 클럽에 참여하여 다른 클럽과 경기를 잡고 경기장도 예약해보세요!

## 📌 서버 흐름도
<img width="1934" alt="image" src="https://user-images.githubusercontent.com/35839248/215392334-8731b506-c752-4516-b046-55d917329ae1.png">


## 📌 테이블 설계
<img width="1281" alt="image" src="https://user-images.githubusercontent.com/35839248/215393100-adecf3ac-e2d6-4eb1-aeed-5245fe0500c4.png">

- 정규화를 유념한 테이블 설계를 통한 데이터의 중복을 최소화하고 데이터 일관성을 확보함
- 외래키를 사용하지 않고 비즈니스 로직에서 처리하여 db 탐색 성능을 향상

## 📌 기술 관련 이야기
- Ngrinder를 이용한 부하테스트 
- Nginx를 이용한 로드밸런싱 구현 
- Prometheus와 Grafana를 이용한 실시간 모니터링 환경 구축 
- 대규모 트래픽을 감당하기 위한 다중서버 구현 및 세션보호 구현
- DataBase 부하 분산을 위한 Master/Slave 구조 구현
- 인증이 필요한 서비스들을 AOP를 통해 구분 
- GitHub Actions를 이용한 CI/CD 구축
- 효율적인 서비스 배포를 위한 도커 컨테이너 구축
- 분산 서버 환경에서 세션 정합성을 위한 Redis 사용
- Redis 트래픽 분산을 위한 세션과 캐시 저장소 분리

## 📌 Use Case
https://glib-pawpaw-9b5.notion.site/ce3898d1bbe8429a8e0419198b1823b7

## 📌 사용 기술 및 환경
- Java 11
- Gradle 7.5
- SpringBoot 2.7.3
- MySQL 8.0.30
- Redis 7.0.5
- MyBatis
