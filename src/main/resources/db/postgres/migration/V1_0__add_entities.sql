create table airport (
  location  varchar(3) not null,
  name  varchar(255)  not null,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "version" bigint NOT NULL default 0,
  constraint pk_airport primary key (location)
);


create table flight (
  id  bigserial,
  price  numeric(10, 2)  not null,
  location  varchar(3)  not null,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "version" bigint NOT NULL default 0,
  constraint pk_flight primary key (id),
  constraint fk_flight_airport
        foreign key(location)
  	  references airport(location)
  );
);

CREATE INDEX idx_flight_location ON flight USING btree (location);