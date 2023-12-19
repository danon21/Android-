import pytest


@pytest.mark.pgsql('db_1', files=['test_data.sql'])
async def test_handler(service_client):
    response = await service_client.get(
        '/user_rank',
        params={'user_name': 'user1'},
    )
    assert response.status == 200
    assert response.json()['user_rank'] == 2


@pytest.mark.pgsql('db_1', files=['test_data.sql'])
async def test_empty_query(service_client):
    response = await service_client.get(
        '/user_rank',
    )
    assert response.status == 400
    assert response.json()[
        'message'
    ] == "Missing required parameter: user_name"


@pytest.mark.pgsql('db_1', files=['test_data.sql'])
async def test_unknown_user(service_client):
    response = await service_client.get(
        '/user_rank',
        params={'user_name': 'user0'},
    )
    assert response.status == 404
    assert response.json()['message'] == "Unknown user: user0"
