CREATE TABLE product_raw_material
(
    id                RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    product_id        RAW(16) UNIQUE NOT NULL,
    raw_material_id   RAW(16)        NOT NULL,
    required_quantity NUMBER         NOT NULL,

    CONSTRAINT fk_prm_product
        FOREIGN KEY (product_id)
            REFERENCES product (id),

    CONSTRAINT fk_prm_raw_material
        FOREIGN KEY (raw_material_id)
            REFERENCES raw_material (id),

    CONSTRAINT uk_product_raw_material
        UNIQUE (product_id, raw_material_id)
);
