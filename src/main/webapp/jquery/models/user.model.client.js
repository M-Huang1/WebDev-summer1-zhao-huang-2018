
function User(username, password, firstName, lastName, role, email, dob) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.dob = dob;
    this.email = email;
    this.setUsername = setUsername;
    this.getUsername = getUsername;
    this.setPassword = setPassword;
    this.getPassword= getPassword;
    this.setFirstName= setFirstName;
    this.getFirstName = getFirstName;
    this.setLastName = setLastName;
    this.getLastName = getLastName;
    this.setRole = setRole;
    this.getRole = getRole;
    this.setEmail = setEmail;
    this.getEmail = getEmail;
    this.setDob = setDob;
    this.getDob = getDob;

    function setUsername(username){
        this.username = username;
    }

    function getUsername(){
        return this.username;
    }

    function setPassword(password){
        this.password = password;
    }

    function getPassword(){
        return this.password;
    }

    function setFirstName(firstName){
        this.firstName = firstName;
    }

    function getFirstName(){
        return this.firstName
    }
    function setLastName(lastName){
        this.lastName = lastName;
    }

    function getLastName(){
        return this.lastName;
    }
    function setRole(role){
        this.role = role;
    }
    function getRole(){
        return this.role;
    }

    function getEmail(){
        return this.email;
    }

    function setEmail(email){
        this.email = email;
    }

    function getDob(){
        return this.dob;
    }
    function setDob(dob){
        this.dob = dob;
    }


}