

//IIFE
(function () {

    jQuery(main);

    var tbody;
    var template;
    var userService = new UserServiceClient();

    function main() {
        tbody = $('tbody');
        template = $('.template');
        $('#createUser').on('click',createUser);

        findAllUsers();
    }

    function findAllUsers() {
        userService
            .findAllUsers()
            .then(renderUsers);
    }

    function createUser() {


        var username = $('#usernameFld').val();
        var password = $('#passwordFld').val();
        var firstName = $('#firstNameFld').val();
        var lastName = $('#lastNameFld').val();
        var role = $('#roleFld').val();
        var email = $('#emailFld').val();
        var dob = $('#dobFld').val();

        var user = {
            username: username,
            password: password,
            firstName: firstName,
            lastName: lastName,
            role: role,
            email:email,
            dob:dob
        };

        console.log('Creating User');
        console.log(user);

        userService.createUser(user).then(
            function (response) {
                console.log(response);
                findAllUsers();
            });
    }

    function renderUsers(users) {
        tbody.empty();
        for(var i=0; i<users.length; i++) {
            var user = users[i];
            var clone = template.clone();

            clone.attr('id', user.id);

            clone.find('.delete').click(deleteUser);
            clone.find('.edit').click(updateUser);
            clone.find('.username').val(user.username);
            clone.find('.password').val(user.password);
            clone.find('.firstName').val(user.firstName);
            clone.find('.lastName').val(user.lastName);
            clone.find('.role').val(user.role);
            clone.find('.email').val(user.email);
            clone.find('.dob').val(user.dob);


            tbody.append(clone);
        }
    }

    function deleteUser(event) {
        var deleteBtn = $(event.currentTarget);
        var userId = deleteBtn
            .parent()
            .parent()
            .attr('id');

        userService
            .deleteUser(userId)
            .then(findAllUsers);
    }

    function updateUser(event) {
        var updateBtn = $(event.currentTarget);
        var userId = updateBtn
            .parent()
            .parent()
            .attr('id');
        var parentRow = updateBtn.parent().parent();
        var user = {
            username: parentRow.find('.username').val(),
            password: parentRow.find('.password').val(),
            firstName: parentRow.find('.firstName').val(),
            lastName: parentRow.find('.lastName').val(),
            role: parentRow.find('.role').val(),
            email: parentRow.find('.email').val(),
            DOB: parentRow.find('.dob').val()
        };

        userService.updateUser(userId, user).then(findAllUsers);



    }

})();