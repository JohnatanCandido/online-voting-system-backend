CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE election (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    confirmed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE sub_election (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    position_name VARCHAR(50) NOT NULL,
    election_id UUID NOT NULL,
    chairs SMALLINT NOT NULL,
    type VARCHAR(25) NOT NULL,
    FOREIGN KEY (election_id) REFERENCES election(id) ON DELETE CASCADE
);

CREATE TABLE party (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    acronym VARCHAR(25) NOT NULL,
    number INTEGER NOT NULL
);

CREATE TABLE person (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    profile_pic_url VARCHAR(100),
    current_party_id UUID,
    FOREIGN KEY (current_party_id) REFERENCES party(id)
);

CREATE TABLE login (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    username VARCHAR(25) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(25) NOT NULL,
    person_id UUID NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

CREATE TABLE candidate (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    person_id UUID NOT NULL,
    party_id UUID NOT NULL,
    number INTEGER NOT NULL,
    sub_election_id UUID NOT NULL,
    votes INTEGER,
    FOREIGN KEY (person_id) REFERENCES person(id),
    FOREIGN KEY (party_id) REFERENCES party(id),
    FOREIGN KEY (sub_election_id) REFERENCES sub_election(id)
);

CREATE TABLE voter (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    person_id UUID NOT NULL,
    sub_election_id UUID NOT NULL,
    vote_computed TIMESTAMP,
    FOREIGN KEY (person_id) REFERENCES person(id),
    FOREIGN KEY (sub_election_id) REFERENCES sub_election(id)
);

CREATE TABLE encrypted_vote (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    sub_election_id UUID NOT NULL,
    encrypted_data TEXT NOT NULL,
    FOREIGN KEY (sub_election_id) REFERENCES sub_election(id)
);

CREATE TABLE vote (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    encrypted_vote_id UUID NOT NULL UNIQUE,
    sub_election_id UUID NOT NULL,
    candidate_id UUID,
    FOREIGN KEY (encrypted_vote_id) REFERENCES encrypted_vote(id),
    FOREIGN KEY (sub_election_id) REFERENCES sub_election(id),
    FOREIGN KEY (candidate_id) REFERENCES candidate(id)
);