CREATE TABLE logs
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    datetime DATETIME NOT NULL,
    ip VARCHAR(39) NOT NULL,
    request VARCHAR(100) NOT NULL,
    code INT NOT NULL,
    useragent VARCHAR(300) NOT NULL
);


CREATE TABLE bad_ips
(
    ip VARCHAR(39) PRIMARY KEY NOT NULL,
    count INT NOT NULL,
    note VARCHAR(300)
);