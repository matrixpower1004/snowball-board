function isValidAccessToken(accessToken) {
    if (!accessToken) {
        alert("생성된 토큰이 없어용")
        return false; // Access token not exist
    }

    const tokenParts = accessToken.split('.');
    if (tokenParts.length !== 3) {
        alert("토큰 규격이 엉망이에요")
        return false; // Invalid token format
    }

    // Split the access token into its three parts: header, payload, and signature
    const header = tokenParts[0];
    const payload = tokenParts[1];
    const signature = tokenParts[2];

    const decodedPayload = JSON.parse(atob(payload));
    const expirationDate = new Date(decodedPayload.exp * 1000);
    const now = new Date();
    if (now >= expirationDate) {
        alert("토큰이 만료됬어요. 재발급 해볼게용")
        return refreshAccessToken();
    }

    // token is valid
    return true;
}

function refreshAccessToken() {
    // request to server /refresh-token
    fetch('/api/auth/refresh-token', {
        method: 'POST'
    })
        .then(response => {
                if (response.ok) {
                    alert("재발급 성공")
                    console.log("Refresh Access Token Success!")
                    return true;
                } else {
                    alert("재발급 실패")
                    console.log("Refresh Access Token Failed!")
                    return false;
                }
            }
        );
}
