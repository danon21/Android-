#include "repository.hpp"

#include "queries.hpp"

namespace pg_service::db {

std::vector<pg_service::models::Game> Repository::GetGames(
    const std::string& user_name) const {
  return pg_cluster_
      ->Execute(userver::storages::postgres::ClusterHostType::kMaster,
                queries::kGetGames, user_name)
      .AsContainer<std::vector<models::Game>>(
          userver::storages::postgres::kRowTag);
}

}  // namespace pg_service::db
