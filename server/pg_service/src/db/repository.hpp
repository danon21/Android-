#pragma once

#include <userver/components/component.hpp>
#include <userver/storages/postgres/cluster.hpp>
#include <userver/storages/postgres/component.hpp>

#include "models/game.hpp"

namespace pg_service::db {

class Repository {
 public:
  Repository(const userver::components::ComponentContext& component_context)
      : pg_cluster_(
            component_context
                .FindComponent<userver::components::Postgres>("postgres-db_1")
                .GetCluster()) {}

  std::vector<models::Game> GetGames(const std::string& user_name) const;

  void InsertGame(const models::Game&);

 private:
  userver::storages::postgres::ClusterPtr pg_cluster_;
};

}  // namespace pg_service::db
