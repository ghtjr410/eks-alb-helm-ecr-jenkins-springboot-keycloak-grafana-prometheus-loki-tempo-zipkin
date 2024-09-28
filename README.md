# EKS-ALB-HELM-ECR-JENKINS-SPRINGBOOT-KEYCLOAK-GRAFANA-PROMETHEUS-LOKI-TEMPO-ZIPKIN

## 프로젝트 목적
eks,alb,helm,ecr,jenkins,springboot,keycloak,grafana,prometheus,loki,tempo,zipkin의 통합을 구현

## 개발 순서
1. keycloak과 통신할 blog-front 코드 작성
2. local환경에서 keycloak - RDS 연결 방법 생각하기
    ㄴ Dockerfile JDBC 설치
3. keycloak custom-theme을 어떻게 마운트할지
    ㄴ Dockerfile에서 마운트하고 Custom Keycloak이미지 생성
4. Keycloak yaml 파일 작성 - Kubectl
5. Keycloak EKS에 배포 - 무한로딩 문제발생
6. 예상 문제  : 프록시 서버문제, CORS문제, 리소스 파일 경로 또는 권한문제
7. Keycloak 자체가 문제인지 kubectl port-forward 로 다이렉트 연결 - keycloak 문제없음
8. ingress-controller(ALB)와 Keycloak간의 문제
9. ingress-controller와 keycloak 문제해결
    1. ingress Controller에서 backend-chanel = http설정
    2. start-dev를 start로 변경
        1. start-dev 모드는 개발 환경에서 빠른 테스트를 위해 사용되며, 보안 및 프록시 설정이 제대로 적용되지 않을 수 있습니다.
    3. --proxy=edge추가 - Keycloak이 Reverse Proxy뒤에서 올바르게 동작하도록함
==============================================================================================================
10. 민감한 정보는 Kubernetes Secret을 사용하여 관리

10. React- Keycloak 내부통신 





3. Keycloak yaml파일 작성 - Helm
4. React - Keycloak : 통합 어떻게할것인지
5. React - Keycloak : 객체생성
6. React - Keycloak : kc-action으로 AIA 방식 리디렉션 가능한지
7. React - Keycloak : 인증인가 확인

8. DynamoDB 생성
9. local Springboot - DynamoDB 연결
10. React - SpringBoot : 1 띄우기
11. React - SpringBoot - Keycloak : Public Key 검증 후 1띄우기
12. React - SpringBoot - Keycloak - DynamoDB : 1띄우기
13. Grafana : 실행 후 UI확인
14. Grafana : 설정 파일 적용 후 UI확인
14. SpringBoot - ( Prometheus - Grafana ) : 내부 네트워크 연결 후 데이터받기
15. Grafana - Keycloak : 리디렉션 확인하기
16. Grafana - Keycloak : 로그인
17. Grafana - Keycloak : 로그아웃