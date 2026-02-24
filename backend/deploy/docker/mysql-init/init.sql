CREATE DATABASE IF NOT EXISTS product_db;
CREATE DATABASE IF NOT EXISTS order_db;
CREATE DATABASE IF NOT EXISTS inventory_db;
CREATE DATABASE IF NOT EXISTS notification_db;

CREATE USER IF NOT EXISTS 'ecommerce'@'%' IDENTIFIED BY 'ecommerce';
GRANT ALL PRIVILEGES ON product_db.* TO 'ecommerce'@'%';
GRANT ALL PRIVILEGES ON order_db.* TO 'ecommerce'@'%';
GRANT ALL PRIVILEGES ON inventory_db.* TO 'ecommerce'@'%';
GRANT ALL PRIVILEGES ON notification_db.* TO 'ecommerce'@'%';
FLUSH PRIVILEGES;
