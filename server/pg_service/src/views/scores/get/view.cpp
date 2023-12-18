#include "view.hpp"

#include <userver/formats/json/serialize_container.hpp>
#include <userver/formats/json/value_builder.hpp>

namespace userver::formats::serialize {

::userver::formats::json::Value Serialize(
    const pg_service::models::Game& data,
    ::userver::formats::serialize::To<::userver::formats::json::Value>) {
  userver::formats::json::ValueBuilder builder;
  builder["user_name"] = data.user_name;
  builder["difficulty"] = data.difficulty;
  builder["game_score"] = data.game_score;
  return builder.ExtractValue();
}

}  // namespace userver::formats::serialize

namespace pg_service::views::scores::get {

userver::formats::json::Value View::HandleRequestJsonThrow(
    const userver::server::http::HttpRequest& request,
    const userver::formats::json::Value& /* request_json */,
    userver::server::request::RequestContext& /* context */) const {
  const auto& name = request.GetArg("user_name");

  userver::formats::json::ValueBuilder result;
  result["games"] = repository_.GetGames(name);

  return result.ExtractValue();
}

}  // namespace pg_service::views::scores::get
