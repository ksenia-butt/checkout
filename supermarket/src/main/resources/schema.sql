create table product
(
    sku varchar(255) not null unique,
    product_name varchar(255) not null,
    price integer not null,
    primary key(sku)
);
create table promotion
(
    id integer not null,
    sku varchar(255) not null unique,
    quantity integer not null,
    price_per_quantity integer not null,
    primary key(id),
    foreign key (sku) REFERENCES product(sku)
);