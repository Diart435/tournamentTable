CREATE TABLE IF NOT EXISTS team (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    team_score INT NOT NULL DEFAULT 0
);

-- Таблица Player
CREATE TABLE IF NOT EXISTS player (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    team_id UUID,
    CONSTRAINT fk_player_team FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE SET NULL
);

-- Таблица Game
CREATE TABLE IF NOT EXISTS game (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    team1_id UUID,
    team2_id UUID,
    score1 INT NOT NULL DEFAULT 0,
    score2 INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_game_team1 FOREIGN KEY (team1_id) REFERENCES team(id) ON DELETE SET NULL,
    CONSTRAINT fk_game_team2 FOREIGN KEY (team2_id) REFERENCES team(id) ON DELETE SET NULL
);

-- Индексы для внешних ключей
CREATE INDEX IF NOT EXISTS idx_player_team_id ON player(team_id);
CREATE INDEX IF NOT EXISTS idx_game_team1_id ON game(team1_id);
CREATE INDEX IF NOT EXISTS idx_game_team2_id ON game(team2_id);