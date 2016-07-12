contract Title {
    /*
    * owner of the title.
    */
    address owner;

    /*
    * account addresses of candidate and authority.
    */
    address[2] userIds;

    /*
    * the constructor registers the signature of the contract creator.
    * The authority still need to sign the title to authenticate it.
    */
    function Title() {
        owner = msg.sender;
        userIds[0] = owner;
    }

    /*
    * authenticates a title submitted by a candidate.
    */
    function authenticate(address authority) {
        userIds[1] = authority;
    }

    /*
    * verifies if a title is authenticated by both signature.
    */
    function isVerified() constant returns (bool retVal) {
        if (userIds.length == 2) {
            return true;
        }
        throw;
    }
}

contract User {

    /*
    * user profile.
    */
    Profile profile;

    /*
    * account address.
    */
    address username;

    string userType = "{0}";
    string password = "{1}";
    string dateOfBirth = "{2}";
    string firstName = "{3}";
    string lastName = "{4}";
    address[] titles;

    /*
    * User constructor set username and profile.
    */
    function User() {
        username = msg.sender;
        profile = Profile(firstName, lastName);
    }

    /*
    * minimal profile information.
    */
    struct Profile {
        string firstName;
        string lastName;
    }

    /*
    * updates profile data checking the passprhase.
    */
    function updateProfile(string firstName, string lastName, string pwd) {
        if (authenticate(pwd)) {
            profile = Profile(firstName, lastName);
        }
    }

    /*
    * updates profile data.
    */
    function updatePassword(string oldPwd, string newPwd) {
        if (authenticate(oldPwd)) {
            password = newPwd;
        }
    }

    /*
    * updates titles list given an already stored title contract.
    */
    function updateTitlesList(address title, string pwd) {
        if (authenticate(pwd)) {
            titles[titles.length -1] = title;
        }
    }

    /*
    * authenticate a user, given a password and the sender address (username).
    */
    function authenticate(string pwd) constant returns (bool retVal) {
        if (msg.sender == username && stringsEqual(pwd, password)) {
            return true;
        }
        throw;
    }

    /*
    * check if two strings are equals.
    */
    function stringsEqual(string _a, string _b) constant returns (bool) {
        bytes memory a = bytes(_a);
        bytes memory b = bytes(_b);
        if (a.length != b.length)
            return false;
        for (uint i = 0; i < a.length; i ++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }


}