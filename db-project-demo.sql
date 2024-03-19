-- ----------------------------
-- Table structure for Hotel Chain
-- ----------------------------
DROP TABLE IF EXISTS hotelChain;
CREATE TABLE IF NOT EXISTS hotelChain (
  name varchar(45) PRIMARY KEY,
  city varchar(45) NOT NULL,
  province varchar(45) NOT NULL,
  streetName varchar(45) NOT NULL,
  streetNum int NOT NULL,
  number_of_hotels int NOT NULL CHECK (number_of_hotels >= 0)
);

-- ----------------------------
-- Table structure for Hotels
-- ----------------------------
DROP TABLE IF EXISTS hotel;
CREATE TABLE IF NOT EXISTS hotel (
  name varchar(45),
  chain_name varchar(45),
  city varchar(45) NOT NULL,
  province varchar(45) NOT NULL,
  streetName varchar(45) NOT NULL,
  streetNum int NOT NULL,
  number_of_rooms int NOT NULL CHECK (number_of_rooms >= 0),
  rating int NOT NULL CHECK (rating >= 0 AND rating <= 100),
  email varchar(100) NOT NULL CHECK (email LIKE '_%@_%._%'),
  phone_num CHAR(10) NOT NULL CHECK (phone_num not like '%[^0-9]%'),
  PRIMARY KEY (name, streetName, streetNum),
  FOREIGN KEY (chain_name) REFERENCES hotelChain(name)
);

-- ----------------------------
-- Table structure for Rooms
-- ----------------------------
DROP TABLE IF EXISTS room;
CREATE TABLE IF NOT EXISTS room (
    room_num int NOT NULL CHECK (room_num >= 0),
    hotel_name varchar(45),
    hotel_street varchar(45),
    hotel_num int,
    capacity int NOT NULL CHECK (capacity > 0),
    price int NOT NULL CHECK (price > 0),
    amenities varchar(100) NOT NULL,
    expandable boolean NOT NULL,
    problems varchar(100),
    FOREIGN KEY (hotel_name, hotel_street, hotel_num) REFERENCES hotel (name, streetName, streetNum),
    PRIMARY KEY (room_num, hotel_name, hotel_street, hotel_num)
);

-- ----------------------------
-- Table structure for Customer
-- ----------------------------
DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer (
    id int CHECK (id > 0) PRIMARY KEY,
    firstName varchar(45) NOT NULL,
    surName varchar(45) NOT NULL,
    city varchar(45) NOT NULL,
    province varchar(45) NOT NULL,
    streetName varchar(45) NOT NULL,
    streetNum int NOT NULL,
    registration_date date NOT NULL
);

-- ----------------------------
-- Table structure for Employee
-- ----------------------------
DROP TABLE IF EXISTS employee;
CREATE TABLE IF NOT EXISTS employee (
    id int CHECK (id > 0) PRIMARY KEY,
    firstName varchar(45) NOT NULL,
    surName varchar(45) NOT NULL,
    city varchar(45) NOT NULL,
    province varchar(45) NOT NULL,
    streetName varchar(45) NOT NULL,
    streetNum int NOT NULL,
    position varchar(45) NOT NULL,
    hotel_name varchar(45),
    hotel_street varchar(45),
    hotel_num int,
    FOREIGN KEY (hotel_name, hotel_street, hotel_num) REFERENCES hotel (name, streetName, streetNum)
);

-- ----------------------------
-- Table structure for Renting
-- ----------------------------
DROP TABLE IF EXISTS renting;
CREATE TABLE IF NOT EXISTS renting (
    startDate date NOT NULL,
    endDate date NOT NULL,
    customerID int,
    status_of_payment boolean NOT NULL,
    room_num int,
    room_price int NOT NULL CHECK (room_price >= 0),
    hotel_name varchar(45),
    hotel_street varchar(45),
    hotel_num int,
    FOREIGN KEY (room_num, hotel_name, hotel_street, hotel_num) REFERENCES room (room_num, hotel_name, hotel_street, hotel_num),
    FOREIGN KEY (customerID) REFERENCES customer (id),
    PRIMARY KEY (startDate, endDate, customerID)
);

-- ----------------------------
-- Table structure for Reserves
-- ----------------------------
DROP TABLE IF EXISTS reserves;
CREATE TABLE IF NOT EXISTS reserves (
    startDate date NOT NULL,
    endDate date NOT NULL,
    room_num int,
    hotel_name varchar(45),
    hotel_street varchar(45),
    hotel_num int,
    FOREIGN KEY (room_num, hotel_name, hotel_street, hotel_num) REFERENCES room (room_num, hotel_name, hotel_street, hotel_num),
    PRIMARY KEY (startDate, endDate, room_num, hotel_name, hotel_street, hotel_num)
);

-- ----------------------------
-- Table structure for booking
-- ----------------------------
DROP TABLE IF EXISTS booking;
CREATE TABLE IF NOT EXISTS booking (
    startDate date,
    endDate date,
    customerID int,
    room_price int NOT NULL CHECK (room_price >= 0),
    room_num int,
    hotel_name varchar(45),
    hotel_street varchar(45),
    hotel_num int,
    FOREIGN KEY (startDate, endDate, room_num, hotel_name, hotel_street, hotel_num) REFERENCES reserves (startDate, endDate, room_num, hotel_name, hotel_street, hotel_num),
    FOREIGN KEY (customerID) REFERENCES customer (id),
    PRIMARY KEY (startDate, endDate, customerID)
);

-- ----------------------------
-- Table structure for checks-in
-- ----------------------------
DROP TABLE IF EXISTS checks_in;
CREATE TABLE IF NOT EXISTS checks_in(
    renting_startDate date,
    renting_endDate date,
    customerID int,
    employeeID int,
    FOREIGN KEY (employeeID) REFERENCES employee (id),
    FOREIGN KEY (renting_startDate, renting_endDate, customerID) REFERENCES renting (startDate, endDate, customerID),
    PRIMARY KEY (renting_startDate, renting_endDate, customerID)
);

-- ----------------------------
-- Table structure for renting archive
-- ----------------------------
DROP TABLE IF EXISTS renting_archive;
CREATE TABLE IF NOT EXISTS renting_archive(
    startDate date,
    endDate date,
    customerID int,
    FOREIGN KEY (startDate, endDate, customerID) REFERENCES renting (startDate, endDate, customerID),
    PRIMARY KEY (startDate, endDate, customerID)
);

-- ----------------------------
-- Table structure for booking archive
-- ----------------------------
DROP TABLE IF EXISTS booking_archive;
CREATE TABLE IF NOT EXISTS booking_archive(
    startDate date,
    endDate date,
    customerID int,
    FOREIGN KEY (startDate, endDate, customerID) REFERENCES booking (startDate, endDate, customerID),
    PRIMARY KEY (startDate, endDate, customerID)
);



-- ----------------------------
-- Records of grades
-- ----------------------------
INSERT INTO grades VALUES ('1', 'PAC', '87', '1');
INSERT INTO grades VALUES ('2', 'SHO', '93', '1');
INSERT INTO grades VALUES ('3', 'PAS', '82', '1');
INSERT INTO grades VALUES ('4', 'DRI', '88', '1');
INSERT INTO grades VALUES ('5', 'DEF', '34', '1');
INSERT INTO grades VALUES ('6', 'PHY', '75', '1');
INSERT INTO grades VALUES ('7', 'PAC', '85', '2');
INSERT INTO grades VALUES ('8', 'SHO', '92', '2');
INSERT INTO grades VALUES ('9', 'PAS', '91', '2');
INSERT INTO grades VALUES ('10', 'DRI', '95', '2');
INSERT INTO grades VALUES ('11', 'DEF', '34', '2');
INSERT INTO grades VALUES ('12', 'PHY', '65', '2');
