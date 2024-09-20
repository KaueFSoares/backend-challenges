CREATE TABLE planets
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    details JSONB,
    UNIQUE (name)
);

CREATE INDEX idx_planets_name ON planets (name);
