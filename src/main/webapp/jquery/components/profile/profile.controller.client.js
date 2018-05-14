(function() {
    jQuery(main);
    var userService = new UserServiceClient();
    var userId;



    function main(){

        userId = getUrlVars()['userId'];
        findUserById(userId);
        $('#updateBtn').on('click',updateUser);

    }



    function getUrlVars()
    {
        var vars = [], hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');

        for(var i = 0; i < hashes.length; i++)
        {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }

        return vars;
    }


    function findUserById(userId) {
        userService
            .findUserById(userId).then(
            function (response) {
                if (response == null) {
                    alert('No Users exist with this ID');
                }
                else {
                    renderUser(response);
                }
            });
    }

    function renderUser(user) {
        console.log(user);
        $('#staticUsername').val(user.username);
        $('#password').val(user.password);
        $('#firstName').val(user.firstName);
        $('#lastName').val(user.lastName);
        $('#role').val(user.role);
        $('#email').val(user.email);
        $('#datepicker').val(user.dob);
    }

    function updateUser(){
        var toUpdate = true;
        var email = $('#email').val();
        userService.findUserByEmail(email).then(
            function (response) {
                if (response != null)  {
                    if(response.id != userId)
                        alert("Email Taken");
                        toUpdate = false;
                }
            }
        );
        var user = {
            username: $('#staticUsername').val(),
            password: $('#password').val(),
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            role: $('#role').val(),
            email: email,
            dob: $('#datepicker').val()
        };
        if(toUpdate) {
            userService.updateUser(userId, user).then(renderUser);
        }
    }
})();