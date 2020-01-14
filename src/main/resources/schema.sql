DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS attachments;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS tags_task;

CREATE TABLE tasks
(
id identity,
title VARCHAR(100),
description VARCHAR(1024),
created_at TIMESTAMP
);

CREATE TABLE attachments
(
filename VARCHAR(100) UNIQUE,
comment VARCHAR(1024),
task NUMERIC,
FOREIGN KEY (task) REFERENCES tasks (id)
);

CREATE TABLE tags (
id IDENTITY,
nazwa VARCHAR(100)
);

CREATE table tags_task (
tags NUMERIC NOT NULL,
tasks NUMERIC NOT NULL,
FOREIGN KEY (tags) REFERENCES tags(id),
FOREIGN KEY (tasks) REFERENCES tasks(id)
)