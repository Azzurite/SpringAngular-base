CREATE TABLE customer (
	unique_name TEXT PRIMARY KEY
);

CREATE TABLE property (
	id                   BIGSERIAL PRIMARY KEY,
	customer_unique_name TEXT REFERENCES customer,
	name                 TEXT,
	value                TEXT
);
