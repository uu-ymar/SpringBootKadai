fetch('/sidebar.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('sidebar-container').innerHTML = data;

        if (window.loginUserName) {
            const nameElem = document.getElementById('login-user-name');
            if (nameElem) {
                nameElem.innerText = window.loginUserName;
            }
        }
    });
