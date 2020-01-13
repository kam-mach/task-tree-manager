DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS attachments;

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
task NUMERIC,
FOREIGN KEY (task) REFERENCES tasks (id)
);