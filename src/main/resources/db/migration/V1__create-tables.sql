CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE election (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE sub_election (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    position_name VARCHAR(50) NOT NULL,
    election_id UUID NOT NULL,
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
    username VARCHAR(25) NOT NULL,
    password VARCHAR(25) NOT NULL,
    admin BOOLEAN NOT NULL,
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

