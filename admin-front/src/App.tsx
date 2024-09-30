import React, { useEffect, useState } from 'react';
import { Route, Routes } from 'react-router-dom';
import Keycloak from 'keycloak-js';
import { KEYCLOAK_URL } from './utils/apiUrlUtil/apiUrlUtil';
import Homepage from './pages/LoginRequiredHomepage';



function App() {

  const [keycloak, setKeycloak] = useState<Keycloak | null>(null);

  useEffect(() => {
    const keycloakInstance = new Keycloak({
      url:KEYCLOAK_URL(),
      realm: 'miniblog-realm',
      clientId: 'admin-client',
    });

    keycloakInstance.init({
      onLoad: "login-required",
      checkLoginIframe: true,
      pkceMethod: 'S256',
      checkLoginIframeInterval: 30,
      silentCheckSsoRedirectUri: undefined,
    })
    .then((authenticated) => {
      setKeycloak(keycloakInstance);
    })
    .catch((error) => {
      console.error("Keycloak 초기화 실패: ", error);
    })

  }, []);

  return (
    <div>
      <header className='bg-gray-800 p-4 text-white'>
        <h1 className='text-2xl font-bold'>관리자 사이트</h1>
      </header>
      <Routes>
        <Route path='/' element={<Homepage keycloak={keycloak}/>}/>
      </Routes>
    </div>
    
  );
}

export default App;
