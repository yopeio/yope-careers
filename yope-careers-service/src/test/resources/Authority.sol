contract Authority {

    Authority authority;

    struct Authority {
        string name;    }

    function set(string name) {
        authority = Authority(name);
    }

    function get() constant returns (string retVal) {
            return authority.name;
    }
}