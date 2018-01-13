
-- Narrow rights for ALL USERS (PUBLIC)  ------------------------------

REVOKE CREATE ON DATABASE db_wallner16  FROM PUBLIC;
REVOKE CREATE ON SCHEMA   public        FROM PUBLIC;

-- Reader   -----------------------------------------------------------

CREATE USER PIZZABESTELLUNG_READER WITH PASSWORD  'reader';
GRANT PIZZABESTELLUNG_READER_ROLE TO PIZZABESTELLUNG_READER;

-- WRITER   -------------------------------------------------------

CREATE USER PIZZABESTELLUNG_WRITER WITH PASSWORD  'writer';
GRANT PIZZABESTELLUNG_WRITER_ROLE TO PIZZABESTELLUNG_WRITER;

-- Admin    -----------------------------------------------------------

CREATE USER PIZZABESTELLUNG_ADMIN WITH PASSWORD  'admin';
GRANT PIZZABESTELLUNG_ADMIN_ROLE TO PIZZABESTELLUNG_ADMIN;
