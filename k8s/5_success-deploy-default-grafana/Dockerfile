FROM quay.io/keycloak/keycloak:latest

# 커스텀 테마를 추가합니다.
COPY themes /opt/keycloak/themes/

# MySQL 지원을 포함하여 Keycloak 서버 빌드
RUN /opt/keycloak/bin/kc.sh build --db=mysql