#pragma once

#include <string>

#include <userver/storages/postgres/io/io_fwd.hpp>
#include <userver/storages/postgres/io/pg_types.hpp>

namespace pg_service::models {

struct Game {
  std::string user_name;
  int difficulty;
  std::string game_score;
};

}  // namespace pg_service::models

namespace userver::storages::postgres::io {

template <>
struct CppToUserPg<pg_service::models::Game> {
  static constexpr DBTypeName postgres_name = "pg_service.game_t_v1";
};

}  // namespace userver::storages::postgres::io
