-- Crear tabla para las Comunidades Autónomas de España
CREATE TABLE IF NOT EXISTS regions (
   id INT AUTO_INCREMENT PRIMARY KEY,
   code VARCHAR(10) NOT NULL UNIQUE,
   name VARCHAR(100) NOT NULL
);


-- Crear tabla para las provincias españolas
CREATE TABLE IF NOT EXISTS provinces (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    region_id INT NOT NULL,
    FOREIGN KEY (region_id) REFERENCES regions(id)
);



-- Crear la tabla 'categories'
CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(500) NULL,
    parent_id INT DEFAULT NULL,
    CONSTRAINT fk_parent_category
        FOREIGN KEY (parent_id) REFERENCES categories(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- Crear la tabla 'users'
CREATE TABLE IF NOT EXISTS users (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   username VARCHAR(50) UNIQUE NOT NULL,
   password VARCHAR(100) NOT NULL,
   enabled BOOLEAN NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   image VARCHAR(255),
   created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   last_password_change_date TIMESTAMP
);

-- Crear la tabla 'roles'
CREATE TABLE IF NOT EXISTS roles (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(50) UNIQUE NOT NULL
);

-- Crear la tabla 'user_roles'
CREATE TABLE IF NOT EXISTS user_roles (
   user_id BIGINT NOT NULL,
   role_id BIGINT NOT NULL,
   PRIMARY KEY (user_id, role_id),
   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);



