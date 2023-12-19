#pragma once

#include <userver/storages/postgres/query.hpp>

namespace pg_service::queries {

extern const userver::storages::postgres::Query kGetBestGames;

extern const userver::storages::postgres::Query kGetGames;

extern const userver::storages::postgres::Query kInsertGame;

extern const userver::storages::postgres::Query kInsertUser;

}  // namespace pg_service::queries
