function setToken(newToken) {
    localStorage.setItem('token', newToken);
}

function getToken() {
    return localStorage.getItem('token');
}

function getAuthHeaders() {
    const token = getToken(); 
    return {
        'Authorization': `Bearer ${token}`
    };
}