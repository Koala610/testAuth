CREATE SCHEMA security;

CREATE TYPE security.status AS ENUM ('DEACTIVATED', 'ACTIVE', 'DRAFT', 'DELETED');

CREATE TABLE security.users(
    id bigserial primary key,
    username varchar(255),
    email varchar(255),
    password varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    creation_ts timestamp DEFAULT CURRENT_TIMESTAMP,
    modification_ts timestamp DEFAULT CURRENT_TIMESTAMP,
    account_expiration_date date not null,
    credentials_expiration_date date not null,
    status security.status
);

CREATE TABLE security.roles(
    id bigserial primary key,
    name varchar(255),
    creation_ts timestamp DEFAULT CURRENT_TIMESTAMP,
    modification_ts timestamp DEFAULT CURRENT_TIMESTAMP,
    status security.status
);

CREATE TABLE security.user_roles(
    id bigserial primary key,
    user_id bigserial not null,
    role_id bigserial not null,
    creation_ts timestamp DEFAULT CURRENT_TIMESTAMP,
    modification_ts timestamp DEFAULT CURRENT_TIMESTAMP,
    status security.status,
    CONSTRAINT fk_user
          FOREIGN KEY(user_id)
    	  REFERENCES security.users(id),
    CONSTRAINT fk_role
          FOREIGN KEY(role_id)
    	  REFERENCES security.roles(id)
);

CREATE SEQUENCE security.users_seq_id;
CREATE SEQUENCE security.roles_seq_id;
CREATE SEQUENCE security.user_roles_seq_id;

INSERT INTO security.roles(id, name, status)
VALUES(nextval('security.roles_seq_id'), 'USER_ROLE', 'ACTIVE');

INSERT INTO security.roles(id, name, status)
VALUES(nextval('security.roles_seq_id'), 'ADMIN_ROLE', 'ACTIVE');
