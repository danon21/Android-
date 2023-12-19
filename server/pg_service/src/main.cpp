#include <userver/clients/dns/component.hpp>
#include <userver/clients/http/component.hpp>
#include <userver/components/minimal_server_component_list.hpp>
#include <userver/server/handlers/ping.hpp>
#include <userver/server/handlers/tests_control.hpp>
#include <userver/storages/postgres/component.hpp>
#include <userver/testsuite/testsuite_support.hpp>
#include <userver/utils/daemon_run.hpp>

#include "views/scores/get/view.hpp"
#include "views/scores/upload/post/view.hpp"
#include "views/user_rank/get/view.hpp"

int main(int argc, char* argv[]) {
  auto component_list =
      userver::components::MinimalServerComponentList()
          .Append<userver::server::handlers::Ping>()
          .Append<userver::components::TestsuiteSupport>()
          .Append<userver::components::HttpClient>()
          .Append<userver::server::handlers::TestsControl>()
          .Append<userver::components::Postgres>("postgres-db_1")
          .Append<pg_service::views::scores::get::View>()
          .Append<pg_service::views::scores::upload::post::View>()
          .Append<pg_service::views::user_rank::get::View>()
          .Append<userver::clients::dns::Component>();

  return userver::utils::DaemonMain(argc, argv, component_list);
}
