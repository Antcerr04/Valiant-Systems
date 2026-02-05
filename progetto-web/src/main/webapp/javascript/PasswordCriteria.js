//Function used to show password criteria
function attivaValidazioneRequisiti() {
    //Get input of the new Password
    const inputPassword = document.getElementById("registerPassword");
    //Get id ot the div feedback
    const divFeedback = document.getElementById("feedback");

    //Add event on input of the new password, If focus is on inputPassword, show feedback
    inputPassword.addEventListener("focus", () => {
        divFeedback.style.display = "block";
    });

    //If focus isn't on input of the new password, the div feedback is none
    inputPassword.addEventListener("blur", () => {
        divFeedback.style.display = "none";
    });

    inputPassword.addEventListener("input", () => {
        const val = inputPassword.value;
        // Verify requirements of the new password
        document.getElementById("lenght").className = val.length >= 8 ? "valid" : "invalid";
        document.getElementById("uppercase").className = /[A-Z]/.test(val) ? "valid" : "invalid";
        document.getElementById("number").className = /[0-9]/.test(val) ? "valid" : "invalid";
        document.getElementById("special").className = /[!@#\$%\^\&*\)\(+=._-]/.test(val) ? "valid" : "invalid";
    });


}


document.addEventListener("DOMContentLoaded", function () {
    const registerForm = document.getElementById("form-register");
    const recoveryPasswordForm= document.getElementById("formRecoveryPassword");
    const passwordUpdate=document.getElementById("form-updatePassword");
    if (registerForm){
        attivaValidazioneRequisiti();
    }
    if(recoveryPasswordForm){
        attivaValidazioneRequisiti();
    }
    if (passwordUpdate) {
        attivaValidazioneRequisiti();
    }
});
