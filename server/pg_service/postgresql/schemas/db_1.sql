DROP SCHEMA IF EXISTS db_service CASCADE;

CREATE SCHEMA db_service;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS db_service.users (
    user_id TEXT PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_name TEXT
);

CREATE UNIQUE INDEX IF NOT EXISTS idx__users__user_name
    ON db_service.users(user_name);

CREATE TABLE IF NOT EXISTS db_service.games (
    game_id TEXT PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id TEXT,
    difficulty INTEGER,
    game_score interval
);

CREATE INDEX IF NOT EXISTS idx__games__user_id ON db_service.games(user_id);

CREATE INDEX IF NOT EXISTS idx__games__difficulty
    ON db_service.games(difficulty);

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'game_t_v1') THEN 
        CREATE TYPE game_t_v1 AS (
            user_name TEXT,
            difficulty INTEGER,
            game_score TEXT
        );
    END IF;
END
$$;
