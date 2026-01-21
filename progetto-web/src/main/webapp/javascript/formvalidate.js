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
        // Cerchiamo l'input con l'ID corretto
        const inputPassword = form.querySelector("#resetPassword");

        if (inputPassword) {
            // Attiva la comparsa dei criteri (valid/invalid)
            initPasswordValidation(inputPassword);
        }

        form.addEventListener("submit", function (event) {
            // Prendiamo il valore attuale della password al momento del click
            const passwordValue = inputPassword.value;

            // Controlliamo se la password rispetta i criteri
            const passwordOk = validatePassword(passwordValue);

            if (!passwordOk) {
                event.preventDefault(); // Blocca l'invio del form
                alert("La nuova password non rispetta i criteri di sicurezza.");
                return;
            }

            // Se arriviamo qui, il form viene inviato correttamente alla Servlet Modifica
            console.log("Validazione superata, invio in corso...");
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

    const updatePasswordForm = document.getElementById("form-updatePassword");
    if (updatePasswordForm) {
        const passwordInput= updatePasswordForm.querySelector("#registerPassword");
        if (passwordInput) {
            initPasswordValidation(passwordInput);


        }
    }
});
