CREATE TYPE melle_weapon AS ENUM ('CHAIN_SWORD',
    'POWER_SWORD',
    'CHAIN_AXE',
    'MANREAPER',
    'POWER_BLADE');

CREATE TYPE astrates_category AS ENUM ('SCOUT',
    'CHAPLAIN',
    'HELIX');

CREATE TABLE account
(
    id SERIAL PRIMARY KEY,
    login VARCHAR(255),
    PASSWORD VARCHAR(255)
);


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

CREATE TABLE history
(
    id SERIAL PRIMARY KEY,
    command VARCHAR(255),
    creator INT REFERENCES account(id) ON DELETE CASCADE
);

DROP TABLE space_marine;
DROP TABLE chapter;
DROP TABLE coordinates;
DROP TABLE history;
DROP TABLE account;
DROP TYPE astrates_category;
DROP TYPE melle_weapon;

DELETE FROM space_marine where id > 1;