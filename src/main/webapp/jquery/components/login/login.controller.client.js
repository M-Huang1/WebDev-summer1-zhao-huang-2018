(function () {

    jQuery(main);
    var userService = new UserServiceClient();

    function main() {
        $('#registerBTN').on('click',login);
    }

    function login(){
        var username = $('#usernameFld').val();
        var password = $('#passwordFld').val();
        var user = {
            username:username,
            password:password
        };
        var toLogin = true;
        var userId;
        userService.login(user).then(
            function (response) {
                if (response == null) {
                    alert("There are no existing accounts with that username and password combination");
                }
                else {
                    userId = response.valueOf();
                    
                    window.location='/jquery/components/profile/profile.template.client.html?userId=' + userId;
                }
            });

    }
})();