import pytest


async def test_handler(service_client):
    response = await service_client.post(
        '/scores/upload',
        {
            'user_name': 'user1',
            'difficulty': 1,
            'game_score': '00:00:35:457',
        }
    )

    assert response.status == 200
    assert response.json() == {}

    response = await service_client.get(
        '/scores',
        params={'user_name': 'user1'},
    )

    assert response.json()['games'] == [
        {
            'difficulty': 1,
            'game_score': '00:00:35:457',
            'user_name': 'user1',
        },
    ]


@pytest.mark.parametrize(
    'body,expected_error',
    [
        pytest.param(
            {
                'game_score': '00:00:33',
            },
            'Missing user_name',
        ),
        pytest.param(
            {
                'user_name': 'user1',
                'game_score': '00:00:33',
            },
            'Missing difficulty',
        ),
        pytest.param(
            {
                'user_name': 'user1',
                'difficulty': 1,
            },
            'Missing game_score',
        ),
        pytest.param(
            {
                'user_name': 0,
                'difficulty': 1,
                'game_score': '00:00:33',
            },
            'user_name should be string',
        ),
        pytest.param(
            {
                'user_name': 'user1',
                'difficulty': '1',
                'game_score': '00:00:33',
            },
            'difficulty should be integer',
        ),
        pytest.param(
            {
                'user_name': 'user1',
                'difficulty': 1,
                'game_score': 0,
            },
            'game_score should be string',
        ),
        pytest.param(
            {
                'user_name': '',
                'difficulty': 1,
                'game_score': '00:00:33',
            },
            'user_name can\'t be empty',
        ),
        pytest.param(
            {
                'user_name': 'user1',
                'difficulty': 1,
                'game_score': '',
            },
            'game_score can\'t be empty',
        ),
        pytest.param(
            {
                'user_name': 'user1',
                'difficulty': 1,
                'game_score': 'sdfasdfsf',
            },
            'Bad game_score format',
        ),
    ]
)
async def test_errors(service_client, body, expected_error):
    response = await service_client.post(
        '/scores/upload',
        body
    )

    assert response.status == 400
    assert response.json()['message'] == expected_error
