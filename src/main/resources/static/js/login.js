function handleLoginResponse(response) {
    if (response.ok) {
        // If the response status code is 2xx (success), continue with the login process
        return response.json();
    } else {
        // If the response status code is not 2xx (error), handle the error
        response.json().then(exceptionDto => {
            // Extract the message from the ExceptionDto and display it in the alert
            alert(exceptionDto.message);
        });
        throw new Error("Login failed.");
    }
}