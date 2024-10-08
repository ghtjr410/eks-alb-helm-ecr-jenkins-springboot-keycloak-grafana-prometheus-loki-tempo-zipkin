# Forward Proxy Server와 Revers Proxy Server란 무엇일까?
## 1. Proxy Server
1. 프록시 서버는 무언가를 대신해주는 서버로, 클라이언트와 실제 서버 사이에서 중개 역할을 합니다.
2. 프록시 서버의 위치에 따라 포워드 프록시와 리버스 프록시로 구분되며, 각각의 명칭과 기능, 목적이 다릅니다.

### A(클라이언트), B(서버), C(프록시 서버), D(WEB) 구조를 가정하여 흐름을 정리하겠습니다.

## 2. Forward Proxy Server
### 포워드 프록시는 클라이언트 측에서 작동하는 프록시 서버입니다.
### 1. A(클라이언트) -> C(프록시 서버) -> D(WEB) -> B(서버)
1. A가 B에 요청을 보낼 때, A는 직접 B에 요청하지 않고 C(프록시 서버)를 거쳐 요청을 보냅니다.
2. C는 A로부터 요청을 받아 D(WEB)를 통해 B로 전달하고, B가 응답을 보내면 D를 통해 다시 C가 그 응답을 A에게 반환합니다.
### 2. 역할 및 목적
1. 클라이언트의 IP 주소를 숨김
2. 콘텐츠 필터링
3. 캐싱을 통해 성능 향상
4. 클라이언트의 요청을 대리하여 서버에 전달
### 3. 간단한 흐름 예씨
1. A(클라이언트) -> C(포워드 프록시) -> D(WEB) -> B(서버)
2. B(서버) -> D(WEB) -> C(포워드 프록시) -> A(클라이언트)

## 2. Reverse Proxy Server
### 리버스 프록시는 서버측에서 작동하는 프록시 서버입니다.
### 참고
1. D(WEB)는 네트워크 인프라스트럭쳐를 나타내며, 실제 환경에서는 프록시서버가 웹 서버 역할을 겸할 수도 있습니다.
2. 따라서 D(WEB)를 포함한 흐름은 네트워크 구조에 따라 다르게 표현될 수 있습니다.
3. 더 간단하게 표현하면, 리버스 프록시가 웹 서버 앞에 위치하여 클라이언트의 요청을 받아 내부 ㅅ ㅓ버로 전달하는 방식입니다.
### 1. A(클라이언트) -> D(WEB) -> C(프록시 서버) -> B(서버)
1. A가 B에 요청을 보낼 때, A는 직접 B에 요청하지않고 D(WEB)을 통해 C(리버스 프록시)로 요청을 보냅니다.
2. C는 A의 요청을 받아 D(WEB)를 통해 B(실제 서버)에 전달합니다.
3. B가 응답을 보내면, 응답은 D(WEB)를 거쳐 C를 통해 다시 A에게 반환됩니다.
### 2. 역할 및 목적
1. 서버의 IP 주소와 구조 숨기기
2. 로드 밸런싱 (여러 서버로 트래픽 분산)
3. SSL 종료 (SSL/TLS 암호화 해제)
4. 캐싱을 통한 응답 속도 향상
5. 보안 강화 (방화벽 역할 등)
### 3. 간단한 흐름 예시
1. A(클라이언트) -> D(WEB) -> C(리버스 프록시) -> B(서버)
2. B(서버) -> C(리버스 프록시) -> D(WEB) -> A(클라이언트)
### 단순화된 리버스 프록시 흐름
1. A(클라이언트) -> C(리버스 프록시) -> B(서버)
2. B(서버) -> C(리버스 프록시) -> A(클라이언트)

# Ingress Controller(ALB)와 LoadBalancer(NLB)의 차이점

## 1. Ingress Controller(ALB)를 이용하는 경우 (리버스 프록시 구조)

1. ALB(Application Load Balancer)는 클라이언트와 실제 서비스 사이에서 리버스 프록시 역할을 합니다.
2. 클라이언트는 ALB로 요청을 보내고, ALB는 해당 요청을 내부 Kubernetes 서비스에 전달합니다.
그 후 서비스의 응답을 다시 클라이언트에게 반환합니다.
3. 이때 ALB는 HTTP/HTTPS 트래픽을 처리하고, 여러 서비스로 요청을 라우팅할 수 있습니다.
    Ingress 리소스를 사용하여 어떤 호스트 이름이나 경로로 들어온 요청이 어떤 서비스로 라우팅 될지를 정의할 수 있습니다.
4. 리버스 프록시 역할을 하기 때문에 Ingress 설정에서 --proxy=edge 같은 프록시 설정이 필요할 수 있습니다.
    이는 ALB가 클라이언트의 요청을 대신 처리하고, 내부 서비스로 전달하는 역할을 하기 때문입니다.

## 2. LoadBalancer(NLB)를 이용하는 경우 (다이렉트 연결 구조)
1. LoadBalancer Type으로 Service를 설정하면, AWS와 같은 Cloud 환경에서는 직접 NLB(NeteWork Load Balancer)를 생성합니다.
2. 이 경우 클라이언트는 Load Balancer로 직접 요청을 보내고, Load Balancer는 해당 요청을 바로 Kubernetes 서비스로 전달합니다.
    Ingress Controller를 거치지 않으므로 별도의 프록시 설정이 필요없습니다.
3. NLB는 4계층에서 트래픽을 처리하는데, 이때 트래픽은 주로 TCP로 다이렉트하게 전달됩니다.
    따라서 HTTP/HTTPS와 같은 고급 라우팅 기능이 필요하지 않고, 프록시 설정 없이도 사용이 가능합니다.
4. 즉, NLB는 단순히 트랙픽을 특정 포트로 전달하는 역할을 하며, 추가적인 트래픽 조정(경로 기반 라우팅 등)이 필요 없는 경우 사용됩니다.

## 3. 차이점 요약
1. Ingress Controller(ALB)는 클라이언트와 서비스 사이에서 리버스 프록시 역할을 하며, 여러 서비스로 트래픽을 분배하거나 SSL 종료, 경로 기반 라우팅 등을 제공합니다. 그래서 프록시 관련 설정이 필요합니다.
2. LoadBalancer 서비스(NLB)는 리버스 프록시 없이 클라이언트의 요청을 직접 Kubernetes 서비스로 전달합니다.
    이 경우 프록시 설정 없이 다이렉트로 연결되며, 네트워크 레벨에서 트래픽을 처리하기 때문에 프록시 설정이 필요 없습니다.


# Ingress.yaml - backend-protocol: HTTP
## 1. ALB 종료 처리 
ALB는 클라이언트와의 HTTPS 트래픽을 처리하며, SSL 종료를 수행합니다.
즉, ALB가 클라이언트로부터 오는 HTTPS 트래픽을 암호화 해제하고, 백엔드 서비스로는 HTTP로 전달합니다.
이는 ALB와 서비스 간의 통신이 내부 네트워크에서 이루어지므로 암호화가 필요하지 않은 경우에 자주 사용됩니다.

## 2. 백엔드 설정 단순화
HTTP 프로토콜을 백엔드 서비스에 사용함으로써 Keycloak과 같은 백엔드 서비스에서 SSL 인증서 설정 및 관리를 피할 수 있습니다.
ALB가 클라이언트와의 통신에서 암호화를 처리하고, 백엔드는 암호화되지 않은 HTTP 트래픽만 처리하면 됩니다.

## 3. 내부 통신
ALB와 Keycloak 서비스가 동일한 VPC(가상 사설 클라우드) 내에 있으므로, 
내부의 신뢰할 수 있는 환경에서는 HTTP 프로토콜이 충분합니다.
내부 네트워크가 적절히 보안 설정이 되어있다면 HTTP 통신으로도 문제가 발생하지 않습니다.

### 요약
ALB가 SSL 종료를 처리하므로 백엔드 서비스로는 HTTPS 대신 HTTP를 사용하는것입니다.
내부적으로 ALB와 백엔드 간의 통신이 안전하고 내부 네트워크에서만 이루어진다면 백엔드가 HTTPS를 처리할 필요는 없습니다.

# keycloak-custom.yaml - Reverse Proxy와 Service 타입 기본 설정 적용
## 1. Deployment에서 args에 start와 --proxy=edge를 사용한 이유
1. start : 
    Keycloak 서버를 실행할때 사용하는 기본 명령입니다.
    start 명령어는 Keycloak 서버를 실행하는 데 필수적입니다.
2. --proxy=edge : 
    Keycloak이 리버스 프록시 뒤에서 동작할 때 필요한 설정입니다.
    ALB와 같은 리버스 프록시를 사용하는 경우, 
    프록시 서버에서 전달받은 헤더 정보를 기반으로 Keycloak이 올바르게 작동할 수 있도록 --proxy=edge를 사용합니다. 
    이를 통해 외부 클라이언트에서 접속할 때, 프록시 서버의 정보를 사용하여 Keycloak이 올바른 URL을 반환할 수 있습니다.

## 2. Service에서 type을 지정하지 않은 이유
1. type을 명시하지 않으면 기본적으로 ClusterIP가 설정됩니다. 
    ClusterIP는 클러스터 내부에서만 접근할 수 있는 서비스 타입입니다.
    Keycloak은 ALB를 통해 외부에서 접근하므로,
    굳이 NodePort나 LoadBalancer를 사용하지 않고 기본 ClusterIP로 내부에서만
    접근 가능하도록 설정한 것입니다.
    ALB가 외부에서 트래픽을 받아 CLusterIP 서비스로 전달하므로 추가적인 외부 서비스 노출이 필요하지 않습니다.

## 3. 프로토콜을 TCP로 지정한 이유:
1. Keycloak은 HTTP(S) 기반으로 통신을 합니다.
    HTTP는 기본적으로 TCP 프로토콜을 사용하기 때문에 서비스 포트를 TCP로 설정한것입니다.
    이는 Keycloak이 사용하는 기본 통신 프로토콜을 반영한 설정입니다.
