contract CandidateContract {

    Candidate candidate;

    struct Candidate {
        uint birthdate;
        string surname;
        string name;
    }

    function newCandidate(string name, string surname, uint birthdate) {
        candidate = Candidate(name, surname, birthdate);
    }

    function get() constant returns (string retVal) {
            return candidate.surname;
    }
}