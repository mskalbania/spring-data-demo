CREATE TABLE ADDRESS
(
    id     INT PRIMARY KEY NOT NULL,
    street VARCHAR(20)     NOT NULL,
    city   VARCHAR(20)     NOT NULL
);

CREATE TABLE EMPLOYEE
(
    id         INT PRIMARY KEY NOT NULL,
    first_name VARCHAR(20)     NOT NULL,
    last_name  VARCHAR(20)     NOT NULL,
    address_id INT             NOT NULL,
    FOREIGN KEY (address_id) references ADDRESS (id)
);

CREATE TABLE CERTIFICATE
(
    id          INT PRIMARY KEY    NOT NULL,
    cert_id     VARCHAR(20) UNIQUE NOT NULL,
    description VARCHAR(50)        NOT NULL,
    employee_id INT                NOT NULL,
    FOREIGN KEY (employee_id) references EMPLOYEE (id)
);

CREATE TABLE POSITION
(
    id   INT PRIMARY KEY NOT NULL,
    name VARCHAR(20)     NOT NULL
);

CREATE TABLE EMPLOYEE_POSITION
(
    employee_id INT NOT NULL,
    position_id INT NOT NULL,
    FOREIGN KEY (employee_id) references EMPLOYEE (id),
    FOREIGN KEY (position_id) references POSITION (id)
);

INSERT INTO ADDRESS(id, street, city)
VALUES (1, 'Ogrodowa', 'Siewierz');

INSERT INTO EMPLOYEE (id, first_name, last_name, address_id)
VALUES (1, 'Mateusz', 'Skalbania', 1),
       (2, 'John', 'Doe', 1);

INSERT INTO CERTIFICATE(id, cert_id, description, employee_id)
VALUES (1, 'aaa-bbb-ccc', 'JAVA OCA', 1),
       (2, 'ddd-eee-fff', 'JAVA OCP', 1),
       (3, 'ggg-hhh-iii', 'JAVA OCA', 2);

INSERT INTO POSITION(id, name)
VALUES (1, 'DEVELOPERS'),
       (2, 'DEV_OPS'),
       (3, 'QA');

INSERT INTO EMPLOYEE_POSITION (employee_id, position_id)
VALUES (1, 1),
       (1, 3),
       (2, 1);