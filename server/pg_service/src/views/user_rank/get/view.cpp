#include "view.hpp"

#include <userver/formats/json/serialize_container.hpp>
#include <userver/formats/json/value_builder.hpp>

namespace pg_service::views::user_rank::get {

userver::formats::json::Value View::HandleRequestJsonThrow(
    const userver::server::http::HttpRequest& request,
    const userver::formats::json::Value& /* request_json */,
    userver::server::request::RequestContext& /* context */) const {
  const auto& name = request.GetArg("user_name");

  userver::formats::json::ValueBuilder result;
  if (name.empty()) {
    request.GetHttpResponse().SetStatus(
        userver::server::http::HttpStatus::kBadRequest);
    result["message"] = "Missing required parameter: user_name";
    return result.ExtractValue();
  }

  try {
    result["user_rank"] = repository_.GetUserRank(name);
  } catch (const db::UnknownUserError& e) {
    request.GetHttpResponse().SetStatus(
        userver::server::http::HttpStatus::kNotFound);
    result["message"] = e.what();
  }

  return result.ExtractValue();
}

}  // namespace pg_service::views::user_rank::get
