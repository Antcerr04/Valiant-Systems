//This file contains js functions that check the clients' input

//function to check if password meets the requirements
function validatePassword(password) {
    const lengthOk = password.length >= 8;
    const hasUppercase = /[A-Z]/.test(password);
    const hasNumber = /[0-9]/.test(password);
    const hasSpecialChar = /[!@#\$%\^\&*\)\(+=._-]/.test(password);

    return lengthOk && hasUppercase && hasNumber && hasSpecialChar;
}

//function to check if username already exists
async function validateUsername(input) {
    const username = input.value.trim();
    const divFeedback = document.querySelector(".feedback-username");

    if (username.length > 3) {
        try {
            const response = await fetch("Validate?action=checkUsername&username=" + encodeURIComponent(username));
            const data = await response.text();
            const exists = data === "0"; // 0 = già esistente

            divFeedback.style.display = exists ? "block" : "none";
            return !exists; // true se disponibile
        } catch (error) {
            console.error("Errore nella validazione username", error);
            return false;
        }
    } else {
        divFeedback.style.display = "none";
        return false;
    }
}

//function to check if email already exists
async function validateEmail(input) {
    const email = input.value.trim();
    const divFeedback = document.querySelector(".feedback-email");

    if (email.length > 10) {
        try {
            const response = await fetch("Validate?action=checkEmail&email=" + encodeURIComponent(email));
            const data = await response.text();
            const exists = data === "0"; // 0 = già registrata

            divFeedback.style.display = exists ? "block" : "none";
            return !exists; // true se disponibile
        } catch (error) {
            console.error("Errore nella validazione email", error);
            return false;
        }
    } else {
        divFeedback.style.display = "none";
        return false;
    }
}

//check if email exists
async function checkEmailExists(input) {
    const email = input.value.trim();
    const feedback = document.querySelector(".feedback-login-email");

    if (email.length > 10) {
        try {
            const response = await fetch("Validate?action=checkEmail&email=" + encodeURIComponent(email));
            const data = await response.text();
            const exists = data === "0"; // 0 = esiste

            feedback.style.display = exists ? "none" : "block";
            return exists;
        } catch (error) {
            console.error("Errore nella validazione email reset", error);
            return false;
        }
    } else {
        feedback.style.display = "none";
        return false;
    }
}

//dynamically updates password criteria
function initPasswordValidation(inputPassword) {
    const divFeedback = document.getElementById("feedback");

    inputPassword.addEventListener("focus", () => {
        divFeedback.style.display = "block";
    });

    inputPassword.addEventListener("blur", () => {
        divFeedback.style.display = "none";
    });

    inputPassword.addEventListener("input", () => {
        const passwordValue = inputPassword.value;

        document.getElementById("lenght").className = passwordValue.length >= 8 ? "valid" : "invalid";
        document.getElementById("uppercase").className = /[A-Z]/.test(passwordValue) ? "valid" : "invalid";
        document.getElementById("number").className = /[0-9]/.test(passwordValue) ? "valid" : "invalid";
        document.getElementById("special").className = /[!@#\$%\^\&*\)\(+=._-]/.test(passwordValue) ? "valid" : "invalid";
    });
}

//manages the validation of the registration form
async function initFormValidation(form) {
    const username = form.querySelector("#username");
    const email = form.querySelector("#register-email")
    const password = form.querySelector("#registerPassword");

    initPasswordValidation(password);

    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        const usernameOk = await validateUsername(username);
        const emailOk = await validateEmail(email);
        const passwordOk = validatePassword(password.value);

        if (!usernameOk) {
            alert("Username già in uso");
            return;
        }

        if (!emailOk) {
            alert("Email già registrata");
            return;
        }

        if (!passwordOk) {
            alert("Password non valida");
            return;
        }

        form.submit();
    });
}

//manage the reset password form
async function initPasswordResetValidation(form) {
    const email = form.querySelector("#reset-email");
    const inputPassword = form.querySelector("#resetPassword");

    initPasswordValidation(inputPassword);

    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        const emailExists = await checkEmailExists(email);
        const passwordOk = validatePassword(inputPassword.value);

        if (!emailExists) {
            alert("Email non trovata");
            return;
        }

        if (!passwordOk) {
            alert("Formato password non corretto, riprova");
            return;
        }

        form.submit();
    });
}


//Initialization
document.addEventListener("DOMContentLoaded", function () {
    const resetLink = document.getElementById("reset-password");
    const loginForm = document.getElementById("form-login");
    const resetForm = document.getElementById("form-reset");

    if (resetLink && loginForm && resetForm) {
        resetLink.addEventListener("click", function (e) {
            e.preventDefault();
            loginForm.style.display = "none";
            resetForm.style.display = "block";
        });

        initPasswordResetValidation(resetForm);

    }

    const registerForm = document.getElementById("form-register");
    if (registerForm) {
        initFormValidation(registerForm);
    }
});
