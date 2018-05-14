function UserServiceClient() {
    this.createUser = createUser;
    this.findAllUsers = findAllUsers;
    this.deleteUser = deleteUser;
    this.findUserById = findUserById;
    this.findUserByUsername = findUserByUsername;
    this.registerUser = registerUser;
    this.findUserByEmail = findUserByEmail;
    this.updateUser = updateUser;
    this.login = login;
    this.url =
        '/api/user';
    this.login_url =
        '/api/login';
    var self = this;

    function login(user) {
        return fetch(self.login_url, {
            method: 'post',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        });
    }

    function updateUser(userId, user) {
        return fetch(self.url + '/' + userId, {
            method: 'put',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        })
            .then(function(response){
                console.log(response);
                if(response.ok) {
                    return response.json();
                } else {
                    return null;
                }
            });
    }

    function findUserById(userId) {
        return fetch(self.url + '/' + userId)
            .then(function(response){
                if(response.status != 200){
                    return null;
                }
                else{
                    return response.json();
                }


            })
    }

    function login(user) {
        console.log(user);
        return fetch('/api/login', {
            method: 'post',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response){
            if(response.status != 200){
                return null;
            }
            else{
                return response.text();
            }


        });
    }
    function findUserByUsername(username) {
        return fetch(self.url + '/username/' + username, {
            method: 'get'
        }).then(function(response){
                if(response.status != 200){
                    return null;
                }
                else{
                    return response.json();
                }


            });
    }

    function findUserByEmail(email) {
        return fetch(self.url + '/email/' + email, {
            method: 'get'
        }).then(function(response) {
            if (response.status != 200) {
                return null;
            }
            else {
                return response.json();
            }
        });
    }

    function deleteUser(userId) {
        return fetch(self.url + '/' + userId, {
            method: 'delete'
        })
    }

    function findAllUsers() {
        return fetch(self.url)
            .then(function (response) {
                return response.json();
            });
    }

    function registerUser(user) {
        return fetch('/api/register', {
            method: 'post',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response) {
            responseText = response.text();
            return responseText;
        })


    }
    function createUser(user) {

        return fetch(self.url, {
            method: 'post',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response) {
            responseText = response.text();
            return responseText;
        })


    }
}
