#include "queries.hpp"

namespace pg_service::queries {

const userver::storages::postgres::Query kGetBestGames{
    R"~(
        SELECT
            user_name,
            difficulty,
            to_char(MIN(game_score), 'HH24:MI:SS:MS')
        FROM
            db_service.users u,
            db_service.games g
        WHERE
            u.user_id = g.user_id
        GROUP BY
            user_name, difficulty
        ORDER BY
            MIN(game_score) ASC;
    )~",
    userver::storages::postgres::Query::Name{"get_best_games"},
};

const userver::storages::postgres::Query kGetGames{
    R"~(
        -- $1 - user_name

        SELECT
            user_name,
            difficulty,
            to_char(MIN(game_score), 'HH24:MI:SS:MS')
        FROM
            db_service.users u,
            db_service.games g
        WHERE
            u.user_name = $1
            AND
            u.user_id = g.user_id
        GROUP BY
            user_name, difficulty
        ORDER BY
            MIN(game_score) ASC;
    )~",
    userver::storages::postgres::Query::Name{"get_games"},
};

const userver::storages::postgres::Query kInsertGame{
    R"~(
        -- $1 - user_id
        -- $2 - difficulty
        -- $3 - game_score

        INSERT INTO
            db_service.games(user_id, difficulty, game_score)
        SELECT
            $1,
            $2,
            to_timestamp($3, 'HH24:MI:SS:MS') - to_timestamp('00:00:00:000', 'HH24:MI:SS:MS');
    )~",
    userver::storages::postgres::Query::Name{"insert_game"},
};

const userver::storages::postgres::Query kInsertUser{
    R"~(
        -- $1 - user_name

        INSERT INTO
            db_service.users(user_name)
        SELECT
            $1
        ON CONFLICT (user_name) DO UPDATE
        SET
            user_name = $1
        RETURNING user_id;
    )~",
    userver::storages::postgres::Query::Name{"insert_user"},
};

}  // namespace pg_service::queries
