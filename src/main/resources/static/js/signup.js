"use strict";

function validateInputs(form) {
            // validate the uniqueness for email and username
    		let userEmail = $("#email").val();
    		let userName = $("#username").val();
            let params = {email: userEmail, name: userName};

    		$.ajax({
                type: "POST",
                url: "/signup/check_email",
                data: params,
                success: function (response) {
//                alert("Response from server: " + response);
                    if (response == "valid"){
                        form.submit();
                    } else if (response == "username and email exist"){
                        alert("Both username and email are already associated with an account. Try login.");
                    }else if (response == "email exists"){
                        alert("This email is already associated with an account. Try login.");
                    } else if (response == "username exists"){
                        alert("This username is already taken. Please choose another name.");
                    }
                },
                error: function(error) {
                    console.log(error);
                }
            });
    		return false;
};
