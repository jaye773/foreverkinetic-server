CREATE TABLE IF NOT EXISTS muscle_groups (
	muscle_group_id	SERIAL PRIMARY KEY,
	title		VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS exercise_types (
	exercise_type_id	SERIAL PRIMARY KEY,
	name				VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS exercises (
	exercise_id	SERIAL PRIMARY KEY,
	title		VARCHAR(40) NOT NULL,
	description VARCHAR(250),
	tip			VARCHAR(250),
	positive_votes	INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS steps (
	step_id		SERIAL PRIMARY KEY,
	exercise_id INTEGER NOT NULL REFERENCES exercises ON DELETE CASCADE,
	step_order	INTEGER NOT NULL,
	step_text	TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS equipment (
	equipment_id	SERIAL PRIMARY KEY,
	name			VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS exercise_muscle_groups (
	muscle_group_id	INTEGER NOT NULL REFERENCES muscle_groups ON DELETE CASCADE,
	exercise_id 	INTEGER NOT NULL REFERENCES exercises ON DELETE CASCADE,
	primary_muscle	BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS media (
	media_id	SERIAL PRIMARY KEY,
	exercise_id INTEGER NOT NULL REFERENCES exercises ON DELETE RESTRICT,
	media_path	VARCHAR(40) NOT NULL
);