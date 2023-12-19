#pragma once

#include <string_view>

#include <userver/server/handlers/http_handler_json_base.hpp>
#include "db/repository.hpp"

namespace pg_service::views::user_rank::get {

class View final : public userver::server::handlers::HttpHandlerJsonBase {
 public:
  static constexpr std::string_view kName = "handler-user-rank";

  View(const userver::components::ComponentConfig& config,
       const userver::components::ComponentContext& component_context)
      : HttpHandlerJsonBase(config, component_context),
        repository_(component_context) {}

  userver::formats::json::Value HandleRequestJsonThrow(
      const userver::server::http::HttpRequest&,
      const userver::formats::json::Value&,
      userver::server::request::RequestContext&) const override;

 private:
  db::Repository repository_;
};

}  // namespace pg_service::views::user_rank::get
