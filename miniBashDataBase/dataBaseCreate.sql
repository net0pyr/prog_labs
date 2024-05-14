CREATE TYPE melle_weapon AS ENUM ('Chain sword',
    'Power sword',
    'Chain axe',
    'Manreaper',
    'Power blade');

CREATE TYPE astrates_category AS ENUM ('Scout',
    'Chaplain',
    'Helix');


CREATE TABLE chapter
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    parent_legion VARCHAR(255),
    marines_count INTEGER,
    world VARCHAR(255),
    creator INT REFERENCES account(id) ON DELETE CASCADE
);

CREATE TABLE coordinates
(
    id SERIAL PRIMARY KEY,
    x FLOAT,
    y FLOAT,
    creator INT REFERENCES account(id) ON DELETE CASCADE
);

CREATE TABLE space_marine
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    coordinates INT REFERENCES coordinates(id) ON DELETE CASCADE,
    health FLOAT,
    height INTEGER,
    category astrates_category,
    meleeWeapon melle_weapon,
    chapter INT REFERENCES chapter(id) ON DELETE CASCADE,
    creator INT REFERENCES account(id) ON DELETE CASCADE
);

CREATE TABLE account
(
    id SERIAL PRIMARY KEY,
    login VARCHAR(255),
    PASSWORD VARCHAR(255)
);
