#pragma once

#include <stdexcept>
#include <userver/components/component.hpp>
#include <userver/storages/postgres/cluster.hpp>
#include <userver/storages/postgres/component.hpp>

#include "models/game.hpp"

namespace pg_service::db {

using UserName = models::Game::UserName;

class Repository {
 public:
  Repository(const userver::components::ComponentContext& component_context)
      : pg_cluster_(
            component_context
                .FindComponent<userver::components::Postgres>("postgres-db_1")
                .GetCluster()) {}

  std::vector<models::Game> GetBestGames() const;

  std::vector<models::Game> GetGames(const UserName&) const;

  int GetUserRank(const UserName&) const;

  void InsertGame(const models::Game&);

 private:
  userver::storages::postgres::ClusterPtr pg_cluster_;
};

struct UnknownUserError : public std::runtime_error {
  using std::runtime_error::runtime_error;
};

}  // namespace pg_service::db
