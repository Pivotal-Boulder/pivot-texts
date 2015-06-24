CREATE TABLE pivot_texts (
  id               SERIAL PRIMARY KEY,
  received_at      TIMESTAMP,
  pivot_id         INTEGER,
  pivot_first_name VARCHAR(255),
  pivot_last_name  VARCHAR(255),
  pivot_location   VARCHAR(255),
  message          VARCHAR(255)
);
