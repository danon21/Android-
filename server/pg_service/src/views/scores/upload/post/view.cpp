#include "view.hpp"

#include <userver/formats/json/serialize_container.hpp>
#include <userver/formats/json/value_builder.hpp>

#include "models/game.hpp"
#include "utils/parsers.hpp"

namespace pg_service::views::scores::upload::post {

userver::formats::json::Value View::HandleRequestJsonThrow(
    const userver::server::http::HttpRequest& request,
    const userver::formats::json::Value& request_json,
    userver::server::request::RequestContext& /* context */) const {
  userver::formats::json::ValueBuilder result;
  models::Game game;

  try {
    game = request_json.ConvertTo<models::Game>();
  } catch (const pg_service::errors::ParseError& e) {
    request.GetHttpResponse().SetStatus(
        userver::server::http::HttpStatus::kBadRequest);
    result["message"] = e.what();
    return result.ExtractValue();
  }

  try {
    repository_.InsertGame(game);
  } catch (const userver::storages::postgres::DataException& e) {
    if (e.GetSqlState() !=
        userver::storages::postgres::SqlState::kInvalidDatetimeFormat) {
      throw;
    }
    request.GetHttpResponse().SetStatus(
        userver::server::http::HttpStatus::kBadRequest);
    result["message"] = "Bad game_score format";
    return result.ExtractValue();
  }

  using namespace userver::formats::literals;
  return "{}"_json;
}

}  // namespace pg_service::views::scores::upload::post
