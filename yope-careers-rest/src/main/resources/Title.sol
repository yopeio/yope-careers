contract Title {

    Title title;

    struct Title {
        uint birthdate;
        string surname;
        string name;
    }

    function set(string name, string surname, uint birthdate) {
        title = Title(name, surname, birthdate);
    }

    function get() constant returns (string retVal) {
            return candidate.surname;
    }
}