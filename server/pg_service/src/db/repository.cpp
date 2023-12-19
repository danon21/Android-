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

void Repository::InsertGame(const models::Game& game) {
  auto trx = pg_cluster_->Begin(
      "inserting_game", userver::storages::postgres::ClusterHostType::kMaster,
      {});

  auto user_id = trx.Execute(queries::kInsertUser, game.user_name)
                     .AsSingleRow<std::string>();

  trx.Execute(queries::kInsertGame, user_id, game.difficulty, game.game_score);
  trx.Commit();
}

}  // namespace pg_service::db
