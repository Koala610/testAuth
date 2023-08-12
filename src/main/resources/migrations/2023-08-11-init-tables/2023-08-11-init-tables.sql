CREATE SCHEMA IF NOT EXISTS security;

CREATE TYPE security.status AS ENUM ('DEACTIVATED', 'ACTIVE', 'DRAFT', 'DELETED');

CREATE TABLE IF NOT EXISTS security.users(
    id bigserial primary key,
    username varchar(255),
    email varchar(255),
    password varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    creation_ts timestamp DEFAULT CURRENT_TIMESTAMP,
    modification_ts timestamp,
    account_expiration_date date not null,
    credentials_expiration_date date not null,
    status security.status,
    unique(username),
    unique(password)
);

CREATE TABLE IF NOT EXISTS security.roles(
    id bigserial primary key,
    name varchar(255),
    creation_ts timestamp DEFAULT CURRENT_TIMESTAMP,
    modification_ts timestamp,
    status security.status,
    unique(name)
);

CREATE TABLE IF NOT EXISTS security.user_roles(
    id bigserial primary key,
    user_id bigserial not null,
    role_id bigserial not null,
    creation_ts timestamp DEFAULT CURRENT_TIMESTAMP,
    modification_ts timestamp,
    status security.status DEFAULT 'ACTIVE',
    CONSTRAINT fk_user
          FOREIGN KEY(user_id)
    	  REFERENCES security.users(id),
    CONSTRAINT fk_role
          FOREIGN KEY(role_id)
    	  REFERENCES security.roles(id)
);

CREATE SEQUENCE IF NOT EXISTS security.users_seq_id;
CREATE SEQUENCE IF NOT EXISTS security.roles_seq_id;
CREATE SEQUENCE IF NOT EXISTS security.user_roles_seq_id;

INSERT INTO security.roles(id, name, status)
VALUES(nextval('security.roles_seq_id'), 'ROLE_USER', 'ACTIVE');

INSERT INTO security.roles(id, name, status)
VALUES(nextval('security.roles_seq_id'), 'ROLE_ADMIN', 'ACTIVE');
