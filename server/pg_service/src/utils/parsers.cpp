#include "parsers.hpp"

namespace userver::formats::json {

pg_service::models::Game Convert(
    const ::userver::formats::json::Value& request_json,
    ::userver::formats::parse::To<pg_service::models::Game>) {
  using namespace pg_service::errors;

  if (!request_json.HasMember("user_name"))
    throw ParseError{"Missing user_name"};
  if (!request_json.HasMember("difficulty"))
    throw ParseError{"Missing difficulty"};
  if (!request_json.HasMember("game_score"))
    throw ParseError{"Missing game_score"};

  if (!request_json["user_name"].IsString())
    throw ParseError{"user_name should be string"};
  if (!request_json["difficulty"].IsInt())
    throw ParseError{"difficulty should be integer"};
  if (!request_json["game_score"].IsString())
    throw ParseError{"game_score should be string"};

  if (request_json["user_name"].As<std::string>().empty())
    throw ParseError{"user_name can't be empty"};
  if (request_json["game_score"].As<std::string>().empty())
    throw ParseError{"game_score can't be empty"};

  return pg_service::models::Game{request_json["user_name"].As<std::string>(),
                                  request_json["difficulty"].As<int>(),
                                  request_json["game_score"].As<std::string>()};
}

}  // namespace userver::formats::json