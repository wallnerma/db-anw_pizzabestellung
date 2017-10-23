/*	
DB Pizzabestellung
Georg Wresnik & Marcus Wallner
*/

/*	Teil 1	*/
/* Create Tables */

CREATE TABLE "kunde"(
	nickname VARCHAR,
	fk_adresse_id INT,
	nachname VARCHAR,
	vorname VARCHAR,
	telnummer VARCHAR
);

CREATE TABLE "adresse"(
    id INT,
    strasse VARCHAR,
    hausnummer INT,
    plz INT,
    ort VARCHAR
);

CREATE TABLE "bestellung" (
	id INT,
	status VARCHAR,
	fk_kunde_nickname VARCHAR,
	fk_bestellungpizza_id INT,
	fk_bearbeiter_angestellter_id INT,
	fk_zusteller_angestellter_id INT
);

CREATE TABLE "angestellter"(
	id INT,
	fk_adresse_id INT,
	nachname VARCHAR,
	vorname VARCHAR,
	svnummer BIGINT,
	zusteller BOOL,
	führerscheintyp VARCHAR
);

CREATE TABLE "bestellungpizza"(
	id INT,
	fk_pizza_id INT,
	fk_bestellung_id INT
);

CREATE TABLE "pizza"(
	id INT,
	name VARCHAR,
	größe VARCHAR,
	einzelpreis REAL
);

CREATE TABLE "zutaten"(
	id INT,
	name VARCHAR
);

CREATE TABLE "pizzazutaten"(
	id INT,
	fk_pizza_id INT,
	fk_zutaten_id INT
);

/*	Teil 2	*/
/* Add Primary Keys */

Alter Table "kunde" Add Primary Key (nickname);
Alter Table "bestellung" Add Primary Key (id);
Alter Table "angestellter" Add Primary Key (id);
Alter Table "bestellungpizza" Add Primary Key (id);
Alter Table "pizza" Add Primary Key (id);
Alter Table "zutaten" Add Primary Key (id);
Alter Table "pizzazutaten" Add Primary Key (id);
Alter Table "adresse" Add Primary Key (id);


/*	Teil 3	*/
/* Add Foreign Keys */

Alter Table "kunde" Add Constraint fk_adresse_id Foreign Key (fk_adresse_id) References "adresse" (id);
Alter Table "angestellter" Add Constraint fk_adresse_id Foreign Key (fk_adresse_id) References "adresse" (id);

Alter Table "bestellung" Add Constraint fk_kunde_nickname Foreign Key (fk_kunde_nickname) References "kunde" (nickname);
Alter Table "bestellung" Add Constraint fk_bestellungpizza_id Foreign Key (fk_bestellungpizza_id) References "bestellungpizza" (id);
Alter Table "bestellung" Add Constraint fk_bearbeiter_angestellter_id Foreign Key (fk_bearbeiter_angestellter_id) References "angestellter" (id);
Alter Table "bestellung" Add Constraint fk_zusteller_angestellter_id Foreign Key (fk_zusteller_angestellter_id) References "angestellter" (id);

Alter Table "bestellungpizza" Add Constraint fk_pizza_id Foreign Key (fk_pizza_id) References "pizza" (id);
Alter Table "bestellungpizza" Add Constraint fk_bestellung_id Foreign Key (fk_bestellung_id) References "bestellung" (id);

Alter Table "pizzazutaten" Add Constraint fk_pizza_id Foreign Key (fk_pizza_id) References "pizza" (id);
Alter Table "pizzazutaten" Add Constraint fk_zutaten_id Foreign Key (fk_zutaten_id) References "zutaten" (id);

/*	Teil 4	*/
/* Create and Add Sequence */

CREATE SEQUENCE bestellungpizza_sequence
  start 1
  increment 1;

ALTER TABLE bestellungpizza ALTER COLUMN id SET DEFAULT nextval('bestellungpizza_sequence');