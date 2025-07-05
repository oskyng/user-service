-- Tabla para la entidad User
CREATE TABLE IF NOT EXISTS user (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created TIMESTAMP NOT NULL,
    modified TIMESTAMP NOT NULL,
    last_login TIMESTAMP NOT NULL,
    token VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL
);

-- Tabla para la entidad Phone
CREATE TABLE IF NOT EXISTS phone (
    id UUID PRIMARY KEY,
    number VARCHAR(20) NOT NULL,
    citycode VARCHAR(10) NOT NULL,
    contrycode VARCHAR(10) NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);