CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    active BOOLEAN DEFAULT true
);

CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    active BOOLEAN DEFAULT true,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS expense (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE,
    date DATE,
    description VARCHAR(255),
    active BOOLEAN DEFAULT true,
    user_id BIGINT,
    category_id BIGINT,
    FOREIGN KEY (users_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);
