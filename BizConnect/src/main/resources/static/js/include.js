// sidebar.html ファイルを非同期で取得し、サイドバー領域に表示する(AI作成)
fetch('/sidebar.html')
    .then(response => response.text()) // レスポンスをテキスト（HTML）として取得
    .then(data => {
        // サイドバー表示用の要素（ID: sidebar-container）にHTMLを挿入
        document.getElementById('sidebar-container').innerHTML = data;

        // window.loginUserName が定義されている場合は、ログインユーザー名をサイドバーに表示
        if (window.loginUserName) {
            const nameElem = document.getElementById('login-user-name');
            if (nameElem) {
                // ログインユーザー名を表示用エレメントにセット
                nameElem.innerText = window.loginUserName;
            }
        }
    });
