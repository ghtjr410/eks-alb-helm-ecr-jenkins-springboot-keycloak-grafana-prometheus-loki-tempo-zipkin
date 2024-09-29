import React, { useEffect, useState } from 'react';
import Keycloak from 'keycloak-js';
import { KEYCLOAK_URL } from './utils/apiUrlUtil/apiUrlUtil';
import { Route, Routes } from 'react-router-dom';
import CheckSsoHomepage from './pages/CheckSsoMyPage';

function App() {
  const [keycloak, setKeycloak] = useState<Keycloak | null>(null);

  useEffect(() => {
    const keycloakInstance = new Keycloak({
      url: KEYCLOAK_URL(),
      realm: 'miniblog-realm',
      clientId: 'service-client',
    });

    keycloakInstance.init({
      onLoad: "check-sso",
      checkLoginIframe: true,
      pkceMethod: 'S256',
      checkLoginIframeInterval: 30,
      silentCheckSsoRedirectUri: undefined,
    })
    .then((authenticated) => {
      setKeycloak(keycloakInstance);
    })
    .catch((error) => {
      console.error("keycloak 초기화 실패: ", error);
    });        
}, []);

  return (
    <Routes>
      <Route path='' element={<CheckSsoHomepage keycloak={keycloak} />}/>
    </Routes>
  );
}

export default App;
