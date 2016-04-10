contract CandidateContract {

    Candidate candidate;

    struct Candidate {
        string name;
    }

    function newCandidate(string name) {
        candidate = Candidate(name);
    }

    function get() constant returns (string retVal) {
            return candidate.name;
    }
}