INSERT INTO
    db_service.users(user_id, user_name)
VALUES
    ('id1', 'user1'),
    ('id2', 'user2')
ON CONFLICT(user_name) DO NOTHING;

INSERT INTO
    db_service.games(user_id, difficulty, game_score)
VALUES 
    ('id1', 1, interval '35s 457ms'),
    ('id2', 1, interval '1m 6s 024ms'),
    ('id1', 2, interval '2m 33s');
