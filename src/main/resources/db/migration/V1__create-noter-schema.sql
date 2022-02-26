CREATE TABLE groups(
    id bigint not null primary key generated always as identity,
    chat_id bigint not null,
    description text
);

CREATE TABLE notes(
    id uuid not null primary key default gen_random_uuid(),
    group_id bigint not null references groups(id),
    name text,
    content text not null,
    link boolean
);

-- INSERT INTO groups(chat_id, description) VALUES ()