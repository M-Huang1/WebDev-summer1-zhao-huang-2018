
(function () {

    jQuery(main);
    var userService = new UserServiceClient();
    function main() {
        $('#registerBTN').on('click',registerUser);


    }

    function registerUser() {

        var toCreate = true;
        var username = $('#usernameFld').val();
        var password = $('#passwordFld').val();
        var vPassword = $('#verifyPasswordFld').val();
        var firstName = $('#firstNameFld').val();
        var lastName = $('#lastNameFld').val();
        var role = $('#roleFld').val();
        var email = $('#emailFld').val();
        var dob = $('#datepicker').val();

        var user = {
            username: username,
            password: password,
            firstName: firstName,
            lastName: lastName,
            role: role,
            email:email,
            dob:dob
        };
        userService.findUserByUsername(username).then(
            function(response){
                if (response != null){
                    alert("Username Taken");
                    toCreate = false;
                }
            }
        ).then(
            function(response) {
                userService.findUserByEmail(email).then(
                    function (response) {
                        if (response != null) {
                            alert("Email Taken");
                            toCreate = false;
                        }
                    }
                );
            }).then(
                function(response){
                    if(vPassword != password) {
                        alert("Your verification password doesn't match your password!")
                        toCreate = false;
                    }
                    console.log(toCreate);
                    if (toCreate) {
                        console.log('Registering User');
                        console.log(user);

                        userService.registerUser(user);
                    }

                }
        )




    }
})();