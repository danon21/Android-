#pragma once

#include <userver/formats/json/value_builder.hpp>
#include "models/game.hpp"

namespace pg_service::errors {

struct ParseError : public std::runtime_error {
  ParseError(const std::string& message) : std::runtime_error(message) {}
};

}  // namespace pg_service::errors

namespace userver::formats::json {

pg_service::models::Game Convert(const json::Value& request_json,
                                 parse::To<pg_service::models::Game>);

}  // namespace userver::formats::json