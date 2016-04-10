contract AuthorityContract {

    Authority authority;

    struct Authority {
        uint created;
        string description;
        string name;
    }

    function newAuthority(uint created, string description, string name) {
        authority = Authority(created, description, name);
    }

    function get() constant returns (string retVal) {
            return authority.name;
    }
}