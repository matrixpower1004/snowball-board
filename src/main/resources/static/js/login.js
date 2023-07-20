function handleLoginResponse(response) {
    if (response.ok) {
        // If the response status code is 2xx (success), continue with the login process
        return response.json();
    } else {
        // If the response status code is not 2xx (error), handle the error
        if (response.status === 401) {
            alert("로그인 정보를 확인 해 주세요");
        } else if (response.status === 403) {
            alert("로그인 정보를 확인 해 주세요")
        } else {
            alert("뭔가... 뭔가 일어남 Try Again!");
        }
        throw new Error("Login failed.");
    }
}