CREATE TABLE employee (
    id                            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name                          VARCHAR(128) NOT NULL,
    email                         VARCHAR(64) NOT NULL,
    phone_number                  VARCHAR(32) NOT NULL,
    address                       VARCHAR(255) NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE equipment (
    id                            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    description                   VARCHAR(255) NULL,
    serial_number                 VARCHAR(64) NOT NULL,
    model                         VARCHAR(32) NOT NULL,
    type                          VARCHAR(32) NOT NULL,
    latitude                      DECIMAL(7,5) NULL,
    longitude                     DECIMAL(7,5) NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE manufacture_order (
    id                            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    order_number                  VARCHAR(64) NOT NULL,
    description                   VARCHAR(255) NULL,
    shift                         VARCHAR(64) NOT NULL DEFAULT 'REGULAR',
    batch_number                  INTEGER NOT NULL,
    createdAt                     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt                     DATETIME NULL,
    finishedAt                    DATETIME NULL,
    equipment_id                  BIGINT UNSIGNED NOT NULL,
    employee_id                   BIGINT UNSIGNED NOT NULL,
    orderStatus                   VARCHAR(16) NOT NULL DEFAULT 'CREATED',
    PRIMARY KEY ( id ),
    CONSTRAINT fk_manufacture_order_equipment_id FOREIGN KEY ( equipment_id ) REFERENCES equipment ( id ),
    CONSTRAINT fk_manufacture_order_employee_id FOREIGN KEY ( employee_id ) REFERENCES employee ( id )
);

CREATE TABLE manufacture_order_item (
    id                            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    description                   VARCHAR(255) NULL,
    quantity                      INTEGER NOT NULL,
    non_conforming_quantity       INTEGER NOT NULL,
    unit                          VARCHAR(16) NOT NULL,
    manufacture_order_id          BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY ( id ),
    CONSTRAINT fk_manufacture_order_item_order_id FOREIGN KEY ( manufacture_order_id ) REFERENCES manufacture_order ( id )
);