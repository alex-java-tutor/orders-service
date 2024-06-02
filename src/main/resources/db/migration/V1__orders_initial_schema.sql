create table orders(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    city TEXT NOT NULL,
    street TEXT NOT NULL,
    house INTEGER NOT NULL,
    block INTEGER,
    apartment INTEGER NOT NULL,
    username TEXT NOT NULL,
    total_price NUMERIC(6, 2),
    status TEXT NOT NULL,
    product_line_items JSONB NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);