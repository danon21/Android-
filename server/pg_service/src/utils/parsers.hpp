#pragma once

#include <userver/formats/json/value_builder.hpp>
#include "models/game.hpp"

namespace pg_service::errors {

class ParseError : public std::runtime_error {
  using std::runtime_error::runtime_error;
};

}  // namespace pg_service::errors

namespace userver::formats::json {

pg_service::models::Game Convert(const json::Value& request_json,
                                 parse::To<pg_service::models::Game>);

}  // namespace userver::formats::json