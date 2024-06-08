if(document.getElementById('registerForm')){
    document.getElementById('registerForm').addEventListener('submit', (e)=>{
        e.preventDefault();
        registerUser();
    });
}

if(document.getElementById('loginForm')){
    document.getElementById('loginForm').addEventListener('submit', (e)=>{
        e.preventDefault();
        loginUser();
    });
}


function registerUser() {

    const data = {
        user_name: document.getElementById("user_name").value,
        user_password: document.getElementById("user_password").value,
        user_password_confirm: document.getElementById("user_password_confirm").value,
        user_email: document.getElementById("user_email").value,
        action: document.getElementById("action").value
    };

    if(data.user_name === ""){
        document.getElementsByClassName("responseLabel")[0].innerHTML = "Invalid username";
        return;
    }else if(data.user_password !== data.user_password_confirm){
        document.getElementsByClassName("responseLabel")[0].innerHTML = "Passwords do not match.";
        return;
    }

    const dataJSON = JSON.stringify(data);
    const XMLHttp = new XMLHttpRequest();
    const url = 'register';

    XMLHttp.open("POST", url, true);
    XMLHttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
    XMLHttp.responseType = "json";

    XMLHttp.onreadystatechange = function () {
        if (XMLHttp.readyState === 4 && XMLHttp.status === 200) {
            const response = XMLHttp.response;
            console.log(response);

            if (response["status"] === "register") {
                console.log("Successful register");
                const url = window.location.href.split('/').slice(0,-1).join('/')+'/login'
                window.location.replace(url);

            } else document.getElementsByClassName("responseLabel")[0].innerHTML = response["status"];
        }
    }

    XMLHttp.send(dataJSON);
}

function loginUser() {
    const data = {
        user_email: document.getElementById("user_email").value,
        user_password: document.getElementById("user_password").value,
        action: document.getElementById("action").value
    };
    
    const dataJSON = JSON.stringify(data);
    const XMLHttp = new XMLHttpRequest();
    const url = 'login';

    XMLHttp.open("POST", url, true);
    XMLHttp.setRequestHeader("Content-Type", "application/json; charset=utf-8");
    XMLHttp.responseType = "json";

    XMLHttp.onreadystatechange = function () {
        if (XMLHttp.readyState === 4 && XMLHttp.status === 200) {
            const response = XMLHttp.response;

            if (response["status"] === "login") {
                const url = window.location.href.split('/').slice(0,-1).join('/')+'/topics'
                window.location.replace(url);

            } else document.getElementsByClassName("responseLabel")[0].innerHTML = response["status"];
        }
    }

    XMLHttp.send(dataJSON);
}

function clickedRegisterLink() {
    const url = window.location.href.split('/').slice(0,-1).join('/')+'/register'
    window.location.replace(url);
}