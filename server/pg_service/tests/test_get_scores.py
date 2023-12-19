import pytest


@pytest.mark.pgsql('db_1', files=['initial_data.sql'])
async def test_handler(service_client):
    response = await service_client.get(
        '/scores',
        params={'user_name': 'user1'},
    )
    assert response.status == 200
    assert sorted(response.json()['games'], key=lambda x: x['difficulty']) == [
        {
            "difficulty": 1,
            "game_score": "00:00:35:457",
            "user_name": "user1"
        },
        {
            "difficulty": 2,
            "game_score": "00:02:33:000",
            "user_name": "user1"
        }
    ]


@pytest.mark.pgsql('db_1', files=['initial_data.sql'])
async def test_empty_query(service_client):
    response = await service_client.get(
        '/scores',
    )
    assert response.status == 200
    assert response.json() == {'games': []}


@pytest.mark.pgsql('db_1', files=['initial_data.sql'])
async def test_unknown_user(service_client):
    response = await service_client.get(
        '/scores',
        params={'user_name': 'user3'},
    )
    assert response.status == 200
    assert response.json() == {'games': []}
