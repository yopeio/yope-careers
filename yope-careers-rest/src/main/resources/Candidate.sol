contract Candidate {

    Candidate candidate;

    struct Candidate {
        string name;
        string lastname;
        uint birthdate;
    }

    function set(string name, string lastname, uint birthdate) {
        candidate = Candidate(name, lastname, birthdate);
    }

    function get() constant returns (string retVal) {
            return candidate.name;
    }
}