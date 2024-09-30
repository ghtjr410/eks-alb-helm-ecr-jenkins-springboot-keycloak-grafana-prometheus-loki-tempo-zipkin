import React, { useEffect, useState } from "react";
import Keycloak from "keycloak-js";
import { useNavigate } from "react-router-dom";

interface Props {
    keycloak: Keycloak | null;
}

const Homepage:React.FC<Props> = ({keycloak}) => {
    const [userInfo, setUserInfo] = useState<null | Record<string, any>>(null);
    const [roles, setRoles] = useState<string[] | null>(null);
    const navigate = useNavigate();
    
    const handleSignout = () => {
        keycloak?.logout();
    }
    const handleOtherpage = () => {
        navigate("/other");
    };

    console.log("홈페이지 : 인증여부 - " + keycloak?.authenticated);
    console.log("홈페이지 : 객체 - " + keycloak);

    useEffect(()=> {
        if(keycloak?.authenticated) {
            keycloak.updateToken(30).then((refreshed) => {
                if (refreshed) {
                    console.log("토큰이 갱신되었습니다.");
                }

                const tokenParsed = keycloak.tokenParsed;
                if (tokenParsed) {
                    setRoles(tokenParsed.realm_access?.roles || []);
                    console.log("사용자 역할: ", tokenParsed.realm_access?.roles);
                    // Role에 admin이 없으면 로그아웃 처리
                    const hasAdminRole = tokenParsed.realm_access?.roles.includes("admin");
                    if (!hasAdminRole) {
                    keycloak.logout();  // 자동 로그아웃 처리
                    }
                }

                keycloak.loadUserInfo().then(info => {
                    setUserInfo(info);
                }).catch(err => {
                    console.error("사용자 정보 로드 실패:", err);
                })
            }).catch(err => {
                
                console.error("사용자 정보 로드 실패:", err);
                keycloak.logout();
            });
        } else {
            console.log("하이");
        }
    }, [keycloak?.authenticated]);

    const adminRole = roles?.filter((role) => role === "admin");


    return(
        <div className="flex flex-col">
            <h1>관리자 홈페이지</h1>
            <h1>1 : {userInfo ? JSON.stringify(userInfo) : "Loading user info..."}</h1>
            <h2>2 : 사용자 역할: {adminRole?.length ? adminRole.join(", ") : "No admin role"}</h2>
            <h1>-----------------------------------------------------------------</h1>
            <h1>0 : {keycloak?.realm}</h1>
            <h1>1 : {keycloak?.authServerUrl}</h1>
            <h1>2 : {keycloak?.authenticated ? "Yes" : "No"}</h1>
            <h1>3 : {keycloak?.clientId}</h1>
            <h1>4 : {keycloak?.flow}</h1>
            <h1>5 : {keycloak?.idToken ? keycloak.idToken : "No ID token"}</h1>
            <h1>6 : {keycloak?.loginRequired ? "Yes" : "No"}</h1>
            <h1>7 : {keycloak?.token ? keycloak.token : "No Access Token"}</h1>
            <h1>8 : {keycloak?.refreshToken ? keycloak.refreshToken : "No Refresh Token"}</h1>
            <h1>9 : {keycloak?.realm}</h1>
            <button className="bg-blue-400 w-24" onClick={handleSignout}>로그아웃</button>
            <button className="bg-green-400 w-24" onClick={handleOtherpage}>Other page</button>
        </div>
    )
}

export default Homepage;