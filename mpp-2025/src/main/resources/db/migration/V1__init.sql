-- V1__init.sql
-- Customers in the OO model have an int `id` already.
-- We'll keep a DB surrogate key `id` (BIGINT AUTO_INCREMENT) for FK performance,
-- and store the app's `id` in `ext_id` (UNIQUE). That keeps DB joins fast and
-- preserves the model's existing field.

CREATE TABLE IF NOT EXISTS customer (
  id        BIGINT PRIMARY KEY AUTO_INCREMENT,
  ext_id    INT NOT NULL UNIQUE,            -- your Customer.id
  name      VARCHAR(120) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Single table for all service types
CREATE TABLE IF NOT EXISTS service (
  id            BIGINT PRIMARY KEY AUTO_INCREMENT,
  customer_id   BIGINT NOT NULL,            -- FK to customer.id
  kind          ENUM('DAILY','MONTHLY') NOT NULL,
  daily_price   DECIMAL(10,2) NOT NULL,
  subscribed_on DATE NOT NULL,
  created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_service_customer
    FOREIGN KEY (customer_id) REFERENCES customer(id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Helpful indexes
CREATE INDEX idx_customer_name ON customer(name);
CREATE INDEX idx_service_customer ON service(customer_id);
CREATE INDEX idx_service_kind ON service(kind);
