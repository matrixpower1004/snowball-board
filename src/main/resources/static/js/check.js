function isValidAccessToken(accessToken) {
    if (!accessToken) {
        alert("로그인 실패. 생성된 토큰이 없어용")
        return false; // Access token not exist
    }

    const tokenParts = accessToken.split('.');
    if (tokenParts.length !== 3) {
        alert("로그인 실패. 토큰이 규격이 엉망이에요")
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
        alert("로그인 실패. 토큰이 만료됬어요")
        return false; // Token has expired
    }

    // token is valid
    return true;
}
