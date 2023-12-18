#include "queries.hpp"

namespace pg_service::queries {

const userver::storages::postgres::Query kGetGames{
    R"~(
        SELECT
            user_name,
            difficulty,
            to_char(game_score, 'HH24:MI:SS:MS')
        FROM
            db_service.users u,
            db_service.games g
        WHERE
            u.user_name = $1
            AND
            u.user_id = g.user_id;
    )~",
    userver::storages::postgres::Query::Name{"get_games"},
};

}  // namespace pg_service::queries
