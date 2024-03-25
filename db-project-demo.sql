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
  phone_num varchar(15) NOT NULL CHECK (phone_num not like '%[^0-9]%'),
  PRIMARY KEY (name, streetNum),
  FOREIGN KEY (chain_name) REFERENCES hotelChain(name) ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for Rooms
-- ----------------------------
DROP TABLE IF EXISTS room;
CREATE TABLE IF NOT EXISTS room (
    room_num int NOT NULL CHECK (room_num >= 0),
    hotel_name varchar(45),
    hotel_num int,
    capacity int NOT NULL CHECK (capacity > 0),
    price int NOT NULL CHECK (price > 0),
    amenities varchar(100) NOT NULL,
    expandable boolean NOT NULL,
    problems varchar(100),
    isAvailable boolean,
    FOREIGN KEY (hotel_name, hotel_num) REFERENCES hotel (name, streetNum) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (room_num, hotel_name, hotel_num)
);

CREATE INDEX rooms_id_index ON room (room_num, hotelNum);

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
    hotel_num int,
    FOREIGN KEY (hotel_name, hotel_num) REFERENCES hotel (name, streetNum) ON DELETE CASCADE ON UPDATE CASCADE
);

-- ----------------------------
-- Table structure for Renting
-- ----------------------------
DROP TABLE IF EXISTS renting;
CREATE TABLE IF NOT EXISTS renting (
    startDate date NOT NULL,
    endDate date NOT NULL,
    customerID int,
    employeeID int,
    status_of_payment boolean NOT NULL,
    room_num int,
    room_price int NOT NULL CHECK (room_price >= 0),
    hotel_name varchar(45),
    hotel_num int,
    FOREIGN KEY (room_num, hotel_name, hotel_num) REFERENCES room (room_num, hotel_name, hotel_num) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (customerID) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (startDate, endDate, customerID)
);
CREATE INDEX renting_date_index ON renting (startDate, endDate, customerID);


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
    hotel_num int,
    FOREIGN KEY (room_num, hotel_name, hotel_num) REFERENCES room (room_num, hotel_name, hotel_num) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (customerID) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (startDate, endDate, customerID)
);

CREATE INDEX booking_date_index ON booking (startDate, endDate, customerID);


-- ----------------------------
-- Table structure for renting archive
-- ----------------------------
DROP TABLE IF EXISTS renting_archive;
CREATE TABLE IF NOT EXISTS renting_archive(
    startDate date,
    endDate date,
    customerID int,
    employeeID int,
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
    PRIMARY KEY (startDate, endDate, customerID)
);


-- ----------------------------
-- Triggers To update the number of hotels owned by a chain when new hotel added
-- ----------------------------
CREATE OR REPLACE FUNCTION update_number_of_hotels()
RETURNS TRIGGER AS $$
BEGIN
    -- Increment the number_of_hotels for the corresponding hotel chain
    UPDATE hotelChain
    SET number_of_hotels = number_of_hotels + 1
    WHERE name = NEW.chain_name;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_hotel_insert
AFTER INSERT ON hotel
FOR EACH ROW
EXECUTE FUNCTION update_number_of_hotels();


-- ----------------------------
-- Triggers To update the number of hotels owned by a chain when hotel deleted
-- ----------------------------
CREATE OR REPLACE FUNCTION decrement_number_of_hotels()
RETURNS TRIGGER AS $$
BEGIN
    -- decrement the number_of_hotels for the corresponding hotel chain
    UPDATE hotelChain
    SET number_of_hotels = number_of_hotels - 1
    WHERE name = OLD.chain_name;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_hotel_delete
AFTER DELETE ON hotel
FOR EACH ROW
EXECUTE FUNCTION decrement_number_of_hotels();


-- ----------------------------
-- Triggers To archive/update booking
-- ----------------------------
CREATE OR REPLACE FUNCTION archive_deleted_booking()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO booking_archive (startDate, endDate, customerID)
        VALUES (OLD.startDate, OLD.endDate, OLD.customerID);
    INSERT INTO renting(startDate, endDate, customerID, employeeID, status_of_payment, room_num, room_price, hotel_name, hotel_num)
        VALUES (OLD.startDate, OLD.endDate, OLD.customerID, NULL, FALSE, OLD.room_num, OLD.room_price, OLD.hotel_name, OLD.hotel_num);
    UPDATE room
        SET isAvailable = FALSE
        WHERE  room_num = OLD.room_num AND hotel_name = OLD.hotel_name AND hotel_num = OLD.hotel_num;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_delete_booking
AFTER DELETE ON booking
FOR EACH ROW
EXECUTE FUNCTION archive_deleted_booking();


-- ----------------------------
-- Triggers To Make a new renting
-- ----------------------------
CREATE OR REPLACE FUNCTION new_renting()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE room
        SET isAvailable = FALSE
        WHERE  room_num = NEW.room_num AND hotel_name = NEW.hotel_name AND hotel_num = NEW.hotel_num;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_new_renting
AFTER INSERT ON renting
FOR EACH ROW
EXECUTE FUNCTION new_renting();



-- ----------------------------
-- Triggers To archive Renting
-- ----------------------------
CREATE OR REPLACE FUNCTION archive_deleted_renting()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO renting_archive (startDate, endDate, customerID, employeeID)
        VALUES (OLD.startDate, OLD.endDate, OLD.customerID, OLD.employeeID);
    UPDATE room
        SET isAvailable = TRUE
        WHERE  room_num = OLD.room_num AND hotel_name = OLD.hotel_name AND hotel_num = OLD.hotel_num;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_delete_renting
AFTER DELETE ON renting
FOR EACH ROW
EXECUTE FUNCTION archive_deleted_renting();


-- ----------------------------
-- View for the number of number of rooms in a given area
-- ----------------------------
CREATE VIEW rooms_in_area AS
SELECT h.city, h.province, COUNT(room_num)
FROM hotel H JOIN room R
ON H.name = R.hotel_name AND H.streetNum = R.hotel_num
WHERE r.isAvailable = true
GROUP BY
    H.city,
    H.province;


-- ----------------------------
-- View for the number of available rooms in a hotel
-- ----------------------------
CREATE VIEW rooms_in_hotel AS
SELECT hotel_name, hotel_num, COUNT(room_num)
FROM room
WHERE isAvailable = true;

-- ----------------------------
-- Records of Hotel Chains
-- ----------------------------
INSERT INTO hotelChain VALUES ('Marriott', 'Bethesda', 'Maryland', 'Wisconsin Ave', 7750, 8700);
INSERT INTO hotelChain VALUES ('Westin', 'San Dimas', 'California', 'Covina Blvd', 320, 235);
INSERT INTO hotelChain VALUES ('Fairmont', 'Toronto', 'Ontario', 'Wellington St W', 3300, 70);
INSERT INTO hotelChain VALUES ('Four Seasons', 'Toronto', 'Ontario', 'Leslie St', 1165, 129);
INSERT INTO hotelChain VALUES ('Hilton', 'Mclean', 'Virginia', 'Jones Branch Drive', 22102, 7530);


-- ----------------------------
-- Records of Hotels
-- ----------------------------
INSERT INTO hotel VALUES ('The Ritz-Carlton','Marriott', 'Toronto', 'Ontario', 'Wellington St W', 2606, 468,4.7, 'RitzCarltonTO@Marriott.com','4165852503');
INSERT INTO hotel VALUES ('Fairfield Inn','Marriott', 'Orillia', 'Ontario', 'Mulcachy Court', 8, 202,4.9, 'FairFieldOR@Marriott.com','2494490368');
INSERT INTO hotel VALUES ('The Dorian','Marriott', 'Calgary', 'Alberta', '5TH AVENUE SW,', 525, 480,4.6, 'DorianCal@Marriott.com','4033006630');
INSERT INTO hotel VALUES ('Residence Inn','Marriott', 'Calgary', 'Alberta', '10th Avenue SW Calgary', 610, 200,4.7, 'ResInnCal@Marriott.com','5878852288');
INSERT INTO hotel VALUES ('Courtyard','Marriott', 'Montreal', 'Quebec', 'Decarie Boulevard Montreal', 6787, 101,3.5, 'CourtyardMO@Marriott.com','416585333');
INSERT INTO hotel VALUES ('Delta Hotels','Marriott', 'Toronto', 'Ontario', 'Kennedy Road', 2035, 106 ,1.1, 'Delta@Marriott.com','4162991500');
INSERT INTO hotel VALUES ('Marriott on the Falls','Marriott', 'Niagara', 'Ontario', 'Fallsview Boulevard', 6755, 800,3.9, 'MarriottFalls@Marriott.com','4165852503');
INSERT INTO hotel VALUES ('JW Marriott The Rosseau','Marriott', 'Muskoka', 'Ontario', 'Paignton House Road', 1050, 468,4.2, 'JWMuskoka@Marriott.com','4165852503');

INSERT INTO hotel VALUES ('The Westin Ottawa', 'Westin', 'Ottawa','Ontario', 'Colonel By Dr', 11,450, 4.5, 'info@westinottawa.com', '6135607000');
INSERT INTO hotel VALUES ('The Westin Harbour Castle', 'Westin', 'Toronto', 'Ontario', 'Harbour Square', 1,977, 4.4, 'harbourcastle@westin.com', '4168691600');
INSERT INTO hotel VALUES ('The Westin Peachtree Plaza', 'Westin', 'Atlanta','Georgia', 'Peachtree St NW', 210,1073, 4.3, 'westinpeachtree@westin.com', '4046591400');
INSERT INTO hotel VALUES ('The Westin St. Francis San Francisco', 'Westin', 'San Francisco','California', 'Powell St', 355,1195, 4.4, 'stfrancis@westin.com', '4153977000');
INSERT INTO hotel VALUES ('The Westin Harbour Island', 'Westin', 'Tampa','Florida', 'Harbour Island Blvd', 725,299, 4.4, 'westintampa@westin.com', '8132295000');
INSERT INTO hotel VALUES ('The Westin Copley Place', 'Westin', 'Boston','Massachusetts', 'Huntington Ave', 10,803, 4.5, 'copleyplace@westin.com', '6172629600');
INSERT INTO hotel VALUES ('The Westin Riverwalk', 'Westin', 'San Antonio','Texas', 'W Market St', 420,473, 4.6, 'riverwalk@westin.com','2102246500');
INSERT INTO hotel VALUES ('The Westin Grand', 'Westin', 'Vancouver','British Columbia', 'Robson St', 433,207, 4.4, 'westingrandvancouver@westin.com', '6046021999');

INSERT INTO hotel VALUES ('Fairmont Château Laurier', 'Fairmont', 'Ottawa', 'Ontario', 'Rideau St', 1,429, 4.6, 'chateaulaurier@fairmont.com', '6132411414');
INSERT INTO hotel VALUES ('Fairmont Royal York', 'Fairmont', 'Toronto', 'Ontario', 'Front St W', 100,1365, 4.4, 'ryh.reservations@fairmont.com', '4163682511');
INSERT INTO hotel VALUES ('Fairmont Olympic Hotel', 'Fairmont', 'Seattle', 'Washington', 'University St', 411,450, 4.6, 'seattle@fairmont.com', '2066211700');
INSERT INTO hotel VALUES ('Fairmont Banff Springs', 'Fairmont', 'Banff', 'Alberta', 'Spray Ave', 405,757, 4.5, 'bsl.reservations@fairmont.com', '4037622211');
INSERT INTO hotel VALUES ('The Plaza', 'Fairmont', 'New York', 'New York', '5th Ave', 768,282, 4.6, 'plaza@fairmont.com', '2127593000');
INSERT INTO hotel VALUES ('Fairmont Empress', 'Fairmont', 'Victoria', 'British Columbia', 'Government St', 721,464, 4.6, 'empress@fairmont.com', '2503848111');
INSERT INTO hotel VALUES ('Fairmont San Francisco', 'Fairmont', 'San Francisco', 'California', 'Mason St', 950,606, 4.5, 'sanfrancisco@fairmont.com', '4157725000');
INSERT INTO hotel VALUES ('Fairmont Le Château Frontenac', 'Fairmont', 'Quebec City', 'Quebec', 'Rue des Carrières', 1,611, 4.7, 'chateaufrontenac@fairmont.com', '4186923861');

INSERT INTO hotel VALUES ('Four Seasons Hotel Toronto', 'Four Seasons', 'Toronto', 'Ontario', 'Yorkville Ave', 60, 259, 4.8, 'info@fourseasons.com', '4169640411');
INSERT INTO hotel VALUES ('Four Seasons Hotel New York', 'Four Seasons', 'New York', 'New York', 'Barclay St', 27, 189, 4.7, 'info@fourseasons.com', '6468801999');
INSERT INTO hotel VALUES ('Four Seasons Hotel Seattle', 'Four Seasons', 'Seattle', 'Washington', '1st Ave', 99, 147, 4.6, 'info@fourseasons.com', '2067497000');
INSERT INTO hotel VALUES ('Four Seasons Resort Whistler', 'Four Seasons', 'Whistler', 'British Columbia', 'Blackcomb Way', 4591, 273, 4.8, 'info@fourseasons.com', '6049353400');
INSERT INTO hotel VALUES ('Four Seasons Resort Maui', 'Four Seasons', 'Maui', 'Hawaii', 'Wailea Alanui Dr', 3900, 383, 4.9, 'info@fourseasons.com', '8088748000');
INSERT INTO hotel VALUES ('Four Seasons Hotel Boston', 'Four Seasons', 'Boston', 'Massachusetts', 'Boylston St', 200, 273, 4.7, 'info@fourseasons.com', '6173384400');
INSERT INTO hotel VALUES ('Four Seasons Hotel at Beverly Hills', 'Four Seasons', 'Los Angeles', 'California', 'S Doheny Dr', 300, 285, 4.7, 'info@fourseasons.com', '3102732222');
INSERT INTO hotel VALUES ('Four Seasons Hotel Chicago', 'Four Seasons', 'Chicago', 'Illinois', 'E Delaware Pl', 120, 345, 4.8, 'info@fourseasons.com', '3122808800');

INSERT INTO hotel VALUES ('Hilton Toronto', 'Hilton', 'Toronto', 'Ontario', 'Richmond St W', 145, 600, 4.4, 'info@hilton.com', '4168693456');
INSERT INTO hotel VALUES ('Hilton New York Midtown', 'Hilton', 'New York', 'New York', 'Avenue of the Americas', 1335, 1969, 4.3, 'info@hilton.com', '2125867000');
INSERT INTO hotel VALUES ('Hilton Seattle', 'Hilton', 'Seattle', 'Washington', '6th Ave', 1301, 239, 4.5, 'info@hilton.com', '2066240500');
INSERT INTO hotel VALUES ('Hilton Whistler Resort', 'Hilton', 'Whistler', 'British Columbia', 'Whistler Way', 4050, 287, 4.6, 'info@hilton.com', '6049321982');
INSERT INTO hotel VALUES ('Hilton Hawaiian Village', 'Hilton', 'Honolulu', 'Hawaii', 'Kalia Rd', 2005, 2860, 4.5, 'info@hilton.com', '8089494321');
INSERT INTO hotel VALUES ('Hilton Boston Back Bay', 'Hilton', 'Boston', 'Massachusetts', 'Dalton St', 40, 390, 4.3, 'info@hilton.com', '6172361100');
INSERT INTO hotel VALUES ('Hilton Los Angeles Airport', 'Hilton', 'Los Angeles', 'California', 'W Century Blvd', 5711, 1234, 4.1, 'info@hilton.com', '3104104000');
INSERT INTO hotel VALUES ('Hilton Chicago', 'Hilton', 'Chicago', 'Illinois', 'S Michigan Ave', 720, 1544, 4.4, 'info@hilton.com', '3129224400');



-- ----------------------------
-- Records of Rooms
-- ----------------------------
-- Rooms for The Ritz-Carlton
INSERT INTO room VALUES (101, 'The Ritz-Carlton', 2606, 2, 170, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Ritz-Carlton', 2606, 2, 180, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Ritz-Carlton', 2606, 3, 190, 'WiFi, Gym Access, Waterfront View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Ritz-Carlton', 2606, 2, 200, 'WiFi, Spa Access, Mountain View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Ritz-Carlton', 2606, 2, 210, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Fairfield Inn
INSERT INTO room VALUES (101, 'Fairfield Inn', 8, 2, 180, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Fairfield Inn', 8, 2, 190, 'WiFi, Breakfast Included, Mountain View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Fairfield Inn', 8, 3, 200, 'WiFi, Gym Access, Forest View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Fairfield Inn', 8, 2, 210, 'WiFi, Spa Access, Village View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Fairfield Inn', 8, 2, 220, 'WiFi, Pet Friendly, Lake View', FALSE, NULL, TRUE);

-- Rooms for The Dorian
INSERT INTO room VALUES (101, 'The Dorian', 525, 2, 200, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Dorian', 525, 2, 210, 'WiFi, Breakfast Included, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Dorian', 525, 3, 220, 'WiFi, Gym Access, Beach View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Dorian', 525, 2, 230, 'WiFi, Spa Access, Garden View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Dorian', 525, 2, 240, 'WiFi, Pet Friendly, Mountain View', FALSE, NULL, TRUE);

-- Rooms for Residence Inn
INSERT INTO room VALUES (101, 'Residence Inn', 610, 2, 160, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Residence Inn', 610, 2, 170, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Residence Inn', 610, 3, 180, 'WiFi, Gym Access, River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Residence Inn', 610, 2, 190, 'WiFi, Spa Access, Skyline View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Residence Inn', 610, 2, 200, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Hilton Courtyard
INSERT INTO room VALUES (101, 'Courtyard', 6787, 2, 140, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Courtyard', 6787, 2, 150, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Courtyard', 6787, 3, 160, 'WiFi, Gym Access, Runway View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Courtyard', 6787, 2, 170, 'WiFi, Spa Access, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Courtyard', 6787, 2, 180, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Delta Hotels
INSERT INTO room VALUES (101, 'Delta Hotels', 2035, 2, 170, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Delta Hotels', 2035, 2, 180, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Delta Hotels', 2035, 3, 190, 'WiFi, Gym Access, Lake View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Delta Hotels', 2035, 2, 200, 'WiFi, Spa Access, Downtown View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Delta Hotels', 2035, 2, 210, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Marriott on the Falls
INSERT INTO room VALUES (106, 'Marriott on the Falls', 6755, 4, 350, 'WiFi, Spa Access, City View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (107, 'Marriott on the Falls', 6755, 2, 280, 'WiFi, Breakfast Included, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (108, 'Marriott on the Falls', 6755, 3, 300, 'WiFi, Gym Access, Mountain View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (109, 'Marriott on the Falls', 6755, 2, 250, 'WiFi, Mini Bar, River View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (110, 'Marriott on the Falls', 6755, 2, 270, 'WiFi, Pet Friendly, Garden View', FALSE, NULL, TRUE);

-- Rooms for JW Marriott The Rossea
INSERT INTO room VALUES (101, 'JW Marriott The Rosseau', 1050, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'JW Marriott The Rosseau', 1050, 2, 230, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'JW Marriott The Rosseau', 1050, 2, 240, 'WiFi, Gym Access, Mountain View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (104, 'JW Marriott The Rosseau', 1050, 2, 250, 'WiFi, Spa Access, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'JW Marriott The Rosseau', 1050, 4, 300, 'WiFi, Suite, Garden View', TRUE, NULL, TRUE);



-- Rooms for Fairmont Château Laurier
INSERT INTO room VALUES (106, 'Fairmont Château Laurier', 1, 4, 300, 'WiFi, Spa Access, City View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (107, 'Fairmont Château Laurier', 1, 2, 250, 'WiFi, Breakfast Included, Mountain View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (108, 'Fairmont Château Laurier', 1, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (109, 'Fairmont Château Laurier', 1, 3, 280, 'WiFi, Gym Access, River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (110, 'Fairmont Château Laurier', 1, 2, 230, 'WiFi, Pet Friendly, Garden View', FALSE, NULL, TRUE);

-- Rooms for Fairmont Royal York
INSERT INTO room VALUES (106, 'Fairmont Royal York', 100, 4, 350, 'WiFi, Spa Access, City View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (107, 'Fairmont Royal York', 100, 2, 280, 'WiFi, Breakfast Included, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (108, 'Fairmont Royal York', 100, 3, 300, 'WiFi, Gym Access, Mountain View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (109, 'Fairmont Royal York', 100, 2, 250, 'WiFi, Mini Bar, River View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (110, 'Fairmont Royal York', 100, 2, 270, 'WiFi, Pet Friendly, Garden View', FALSE, NULL, TRUE);

-- Rooms for Fairmont Olympic Hotel
INSERT INTO room VALUES (101, 'Fairmont Olympic Hotel', 411, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Fairmont Olympic Hotel', 411, 2, 230, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Fairmont Olympic Hotel', 411, 2, 240, 'WiFi, Gym Access, Mountain View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Fairmont Olympic Hotel', 411, 2, 250, 'WiFi, Spa Access, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Fairmont Olympic Hotel', 411, 4, 300, 'WiFi, Suite, Garden View', TRUE, NULL, TRUE);

-- Rooms for Fairmont Banff Springs
INSERT INTO room VALUES (101, 'Fairmont Banff Springs', 405, 2, 250, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Fairmont Banff Springs', 405, 2, 260, 'WiFi, Breakfast Included, Mountain View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Fairmont Banff Springs', 405, 3, 280, 'WiFi, Spa Access, River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Fairmont Banff Springs', 405, 2, 270, 'WiFi, Gym Access, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Fairmont Banff Springs', 405, 2, 280, 'WiFi, Pet Friendly, Forest View', FALSE, NULL, TRUE);

-- Rooms for The Plaza - A Fairmont Managed Hotel
INSERT INTO room VALUES (101, 'The Plaza', 768, 2, 400, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Plaza', 768, 2, 410, 'WiFi, Breakfast Included, Central Park View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Plaza', 768, 3, 450, 'WiFi, Spa Access, City View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Plaza', 768, 2, 420, 'WiFi, Gym Access, Statue of Liberty View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Plaza', 768, 2, 430, 'WiFi, Pet Friendly, Empire State Building View', FALSE, NULL, TRUE);

-- Rooms for Fairmont Empress
INSERT INTO room VALUES (101, 'Fairmont Empress', 721, 2, 230, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Fairmont Empress', 721, 2, 240, 'WiFi, Breakfast Included, Harbor View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Fairmont Empress', 721, 2, 250, 'WiFi, Spa Access, Garden View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Fairmont Empress', 721, 3, 270, 'WiFi, Gym Access, Mountain View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Fairmont Empress', 721, 2, 260, 'WiFi, Pet Friendly, City View', FALSE, NULL, TRUE);

-- Rooms for Fairmont San Francisco
INSERT INTO room VALUES (101, 'Fairmont San Francisco', 950, 2, 300, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Fairmont San Francisco', 950, 2, 310, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Fairmont San Francisco', 950, 2, 320, 'WiFi, Spa Access, Bay View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Fairmont San Francisco', 950, 3, 340, 'WiFi, Gym Access, Golden Gate Bridge View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Fairmont San Francisco', 950, 2, 330, 'WiFi, Pet Friendly, Alcatraz Island View', FALSE, NULL, TRUE);

-- Rooms for Fairmont Le Château Frontenac
INSERT INTO room VALUES (101, 'Fairmont Le Château Frontenac', 1, 2, 270, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Fairmont Le Château Frontenac', 1, 2, 280, 'WiFi, Breakfast Included, Old Quebec View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Fairmont Le Château Frontenac', 1, 3, 300, 'WiFi, Spa Access, St. Lawrence River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Fairmont Le Château Frontenac', 1, 2, 290, 'WiFi, Gym Access, Citadel View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Fairmont Le Château Frontenac', 1, 2, 280, 'WiFi, Pet Friendly, Frontenac Castle View', FALSE, NULL, TRUE);




-- Rooms for The Westin Peachtree Plaza, Atlanta
INSERT INTO room VALUES (101, 'The Westin Peachtree Plaza', 210, 1,220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Westin Peachtree Plaza', 210, 1,230, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Westin Peachtree Plaza', 210, 4,240, 'WiFi, Gym Access, Downtown View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Westin Peachtree Plaza', 210, 3,250, 'WiFi, Spa Access, Park View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Westin Peachtree Plaza', 210, 2,260, 'WiFi, Pet Friendly, Garden View', FALSE, NULL, TRUE);

-- Rooms for The Westin Copley Place, Boston
INSERT INTO room VALUES (101, 'The Westin Copley Place', 10, 2, 280, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Westin Copley Place', 10, 2, 290, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Westin Copley Place', 10, 3, 300, 'WiFi, Gym Access, River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Westin Copley Place', 10, 2, 310, 'WiFi, Spa Access, Harbor View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Westin Copley Place', 10, 2, 320, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for The Westin Ottawa
INSERT INTO room VALUES (101, 'The Westin Ottawa', 11, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Westin Ottawa', 11, 2, 230, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Westin Ottawa', 11, 3, 240, 'WiFi, Gym Access, River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Westin Ottawa', 11, 2, 250, 'WiFi, Spa Access, Harbor View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Westin Ottawa', 11, 2, 260, 'WiFi, Pet Friendly, Garden View', FALSE, NULL, TRUE);

-- Rooms for The Westin Grand
INSERT INTO room VALUES (101, 'The Westin Grand', 433, 2, 280, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Westin Grand',433, 2, 290, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Westin Grand', 433, 3, 300, 'WiFi, Gym Access, River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Westin Grand', 433, 2, 310, 'WiFi, Spa Access, Downtown View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Westin Grand', 433, 2, 320, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for The Westin Harbour Castle, Toronto
INSERT INTO room VALUES (101, 'The Westin Harbour Castle', 1, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Westin Harbour Castle', 1, 2, 230, 'WiFi, Breakfast Included, Lake View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Westin Harbour Castle', 1, 3, 240, 'WiFi, Gym Access, City View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Westin Harbour Castle', 1, 2, 250, 'WiFi, Spa Access, Harbor View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Westin Harbour Castle', 1, 2, 260, 'WiFi, Pet Friendly, Garden View', FALSE, NULL, TRUE);

-- Rooms for The Westin St. Francis San Francisco on Union Square
INSERT INTO room VALUES (101, 'The Westin St. Francis San Francisco', 355, 2, 250, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Westin St. Francis San Francisco', 355, 2, 260, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Westin St. Francis San Francisco', 355, 3, 270, 'WiFi, Gym Access, Union Square View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Westin St. Francis San Francisco', 355, 2, 280, 'WiFi, Spa Access, Bay View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Westin St. Francis San Francisco', 355, 2, 290, 'WiFi, Pet Friendly, Garden View', FALSE, NULL, TRUE);

-- Rooms for The Westin Harbour Island, Tampa
INSERT INTO room VALUES (101, 'The Westin Harbour Island', 725, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Westin Harbour Island', 725, 2, 230, 'WiFi, Breakfast Included, Bay View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Westin Harbour Island', 725, 3, 240, 'WiFi, Gym Access, Marina View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Westin Harbour Island', 725, 2, 250, 'WiFi, Spa Access, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Westin Harbour Island', 725, 2, 260, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for The Westin Riverwalk
INSERT INTO room VALUES (101, 'The Westin Riverwalk', 420, 2, 250, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'The Westin Riverwalk', 420, 2, 260, 'WiFi, Breakfast Included, River View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'The Westin Riverwalk', 420, 3, 270, 'WiFi, Gym Access, City View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'The Westin Riverwalk', 420, 2, 280, 'WiFi, Spa Access, Garden View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'The Westin Riverwalk', 420, 2, 290, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);



-- Rooms for Four Seasons Hotel Toronto
INSERT INTO room VALUES (101, 'Four Seasons Hotel Toronto', 60, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Four Seasons Hotel Toronto', 60, 2, 230, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Four Seasons Hotel Toronto', 60, 3, 240, 'WiFi, Gym Access, Lake View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Four Seasons Hotel Toronto', 60, 2, 250, 'WiFi, Spa Access, Downtown View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Four Seasons Hotel Toronto', 60, 2, 260, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Four Seasons Hotel New York Downtown
INSERT INTO room VALUES (101, 'Four Seasons Hotel New York', 27, 2, 250, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Four Seasons Hotel New York', 27, 2, 260, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Four Seasons Hotel New York', 27, 3, 270, 'WiFi, Gym Access, River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Four Seasons Hotel New York', 27, 2, 280, 'WiFi, Spa Access, Skyline View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Four Seasons Hotel New York', 27, 2, 290, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Four Seasons Hotel Seattle
INSERT INTO room VALUES (101, 'Four Seasons Hotel Seattle', 99, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Four Seasons Hotel Seattle', 99, 2, 230, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Four Seasons Hotel Seattle', 99, 3, 240, 'WiFi, Gym Access, Waterfront View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Four Seasons Hotel Seattle', 99, 2, 250, 'WiFi, Spa Access, Mountain View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Four Seasons Hotel Seattle', 99, 2, 260, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Four Seasons Resort and Residences Whistler
INSERT INTO room VALUES (101, 'Four Seasons Resort Whistler', 4591, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Four Seasons Resort Whistler', 4591, 2, 230, 'WiFi, Breakfast Included, Mountain View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Four Seasons Resort Whistler', 4591, 3, 240, 'WiFi, Gym Access, Forest View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Four Seasons Resort Whistler', 4591, 2, 250, 'WiFi, Spa Access, Village View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Four Seasons Resort Whistler', 4591, 2, 260, 'WiFi, Pet Friendly, Lake View', FALSE, NULL, TRUE);

-- Rooms for Four Seasons Resort Maui at Wailea
INSERT INTO room VALUES (101, 'Four Seasons Resort Maui', 3900, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Four Seasons Resort Maui', 3900, 2, 230, 'WiFi, Breakfast Included, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Four Seasons Resort Maui', 3900, 3, 240, 'WiFi, Gym Access, Garden View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Four Seasons Resort Maui', 3900, 2, 250, 'WiFi, Spa Access, Beach View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Four Seasons Resort Maui', 3900, 2, 260, 'WiFi, Pet Friendly, Mountain View', FALSE, NULL, TRUE);

-- Rooms for Four Seasons Hotel Boston
INSERT INTO room VALUES (101, 'Four Seasons Hotel Boston', 200, 2, 220, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Four Seasons Hotel Boston', 200, 2, 230, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Four Seasons Hotel Boston', 200, 3, 240, 'WiFi, Gym Access, Harbor View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Four Seasons Hotel Boston', 200, 2, 250, 'WiFi, Spa Access, Park View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Four Seasons Hotel Boston', 200, 2, 260, 'WiFi, Pet Friendly, River View', FALSE, NULL, TRUE);

-- Rooms for Four Seasons Hotel Los Angeles at Beverly Hills
INSERT INTO room VALUES (101, 'Four Seasons Hotel at Beverly Hills', 300, 2, 280, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Four Seasons Hotel at Beverly Hills', 300, 2, 290, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Four Seasons Hotel at Beverly Hills', 300, 3, 300, 'WiFi, Gym Access, Mountain View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Four Seasons Hotel at Beverly Hills', 300, 2, 310, 'WiFi, Spa Access, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Four Seasons Hotel at Beverly Hills', 300, 2, 320, 'WiFi, Pet Friendly, City View', FALSE, NULL, TRUE);

-- Rooms for Four Seasons Hotel Chicago
INSERT INTO room VALUES (101, 'Four Seasons Hotel Chicago', 120, 2, 250, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Four Seasons Hotel Chicago',120, 2, 260, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Four Seasons Hotel Chicago', 120, 3, 270, 'WiFi, Gym Access, River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Four Seasons Hotel Chicago', 120, 2, 280, 'WiFi, Spa Access, Skyline View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Four Seasons Hotel Chicago', 120, 2, 290, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);



-- Rooms for Hilton Toronto
INSERT INTO room VALUES (101, 'Hilton Toronto', 145, 2, 150, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Hilton Toronto', 145, 2, 160, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Hilton Toronto', 145, 3, 170, 'WiFi, Gym Access, Lake View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Hilton Toronto', 145, 2, 180, 'WiFi, Spa Access, Downtown View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Hilton Toronto', 145, 2, 190, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Hilton New York Midtown
INSERT INTO room VALUES (101, 'Hilton New York Midtown', 1335, 2, 200, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Hilton New York Midtown', 1335, 2, 210, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Hilton New York Midtown', 1335, 3, 220, 'WiFi, Gym Access, River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Hilton New York Midtown', 1335, 2, 230, 'WiFi, Spa Access, Skyline View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Hilton New York Midtown', 1335, 2, 240, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Hilton Seattle Downtown
INSERT INTO room VALUES (101, 'Hilton Seattle', 1301, 2, 170, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Hilton Seattle', 1301, 2, 180, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Hilton Seattle', 1301, 3, 190, 'WiFi, Gym Access, Waterfront View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Hilton Seattle', 1301, 2, 200, 'WiFi, Spa Access, Mountain View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Hilton Seattle', 1301, 2, 210, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Hilton Whistler Resort & Spa
INSERT INTO room VALUES (101, 'Hilton Whistler Resort', 4050, 2, 180, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Hilton Whistler Resort', 4050, 2, 190, 'WiFi, Breakfast Included, Mountain View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Hilton Whistler Resort', 4050, 3, 200, 'WiFi, Gym Access, Forest View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Hilton Whistler Resort', 4050, 2, 210, 'WiFi, Spa Access, Village View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Hilton Whistler Resort', 4050, 2, 220, 'WiFi, Pet Friendly, Lake View', FALSE, NULL, TRUE);

-- Rooms for Hilton Hawaiian Village Waikiki Beach Resort
INSERT INTO room VALUES (101, 'Hilton Hawaiian Village', 2005, 2, 200, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Hilton Hawaiian Village', 2005, 2, 210, 'WiFi, Breakfast Included, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Hilton Hawaiian Village', 2005, 3, 220, 'WiFi, Gym Access, Beach View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Hilton Hawaiian Village', 2005, 2, 230, 'WiFi, Spa Access, Garden View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Hilton Hawaiian Village', 2005, 2, 240, 'WiFi, Pet Friendly, Mountain View', FALSE, NULL, TRUE);

-- Rooms for Hilton Boston Back Bay
INSERT INTO room VALUES (101, 'Hilton Boston Back Bay', 40, 2, 160, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Hilton Boston Back Bay', 40, 2, 170, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Hilton Boston Back Bay', 40, 3, 180, 'WiFi, Gym Access, River View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Hilton Boston Back Bay', 40, 2, 190, 'WiFi, Spa Access, Skyline View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Hilton Boston Back Bay', 40, 2, 200, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Hilton Los Angeles Airport
INSERT INTO room VALUES (101, 'Hilton Los Angeles Airport', 5711, 2, 140, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Hilton Los Angeles Airport', 5711, 2, 150, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Hilton Los Angeles Airport', 5711, 3, 160, 'WiFi, Gym Access, Runway View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Hilton Los Angeles Airport', 5711, 2, 170, 'WiFi, Spa Access, Ocean View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Hilton Los Angeles Airport', 5711, 2, 180, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);

-- Rooms for Hilton Chicago
INSERT INTO room VALUES (101, 'Hilton Chicago', 720, 2, 170, 'WiFi, TV, Mini Bar', FALSE, NULL, TRUE);
INSERT INTO room VALUES (102, 'Hilton Chicago', 720, 2, 180, 'WiFi, Breakfast Included, City View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (103, 'Hilton Chicago', 720, 3, 190, 'WiFi, Gym Access, Lake View', TRUE, NULL, TRUE);
INSERT INTO room VALUES (104, 'Hilton Chicago', 720, 2, 200, 'WiFi, Spa Access, Downtown View', FALSE, NULL, TRUE);
INSERT INTO room VALUES (105, 'Hilton Chicago', 720, 2, 210, 'WiFi, Pet Friendly, Park View', FALSE, NULL, TRUE);


-- ----------------------------
-- Records of Customers
-- ----------------------------
INSERT INTO customer VALUES ('123456789', 'John', 'Doe', 'Toronto', 'Ontario', 'Main St', 123, '2023-05-10');
INSERT INTO customer VALUES ('987654321', 'Alice', 'Smith', 'New York', 'New York', 'Broadway', 456, '2023-06-15');
INSERT INTO customer VALUES ('456789012', 'Michael', 'Johnson', 'Seattle', 'Washington', 'Pine St', 789, '2023-07-20');
INSERT INTO customer VALUES ('789012345', 'Emily', 'Brown', 'Whistler', 'British Columbia', 'Mountain Ave', 321, '2023-08-25');
INSERT INTO customer VALUES ('234567890', 'David', 'Taylor', 'Maui', 'Hawaii', 'Beach Rd', 654, '2023-09-30');
INSERT INTO customer VALUES ('123456780', 'Jessica', 'Johnson', 'Los Angeles', 'California', 'Sunset Blvd', 987, '2023-10-05');
INSERT INTO customer VALUES ('987654322', 'Matthew', 'Clark', 'Chicago', 'Illinois', 'Lake St', 654, '2023-11-10');
INSERT INTO customer VALUES ('456789013', 'Sophia', 'Martinez', 'Boston', 'Massachusetts', 'Park Ave', 321, '2023-12-15');
INSERT INTO customer VALUES ('789012346', 'Daniel', 'Rodriguez', 'Honolulu', 'Hawaii', 'King St', 852, '2024-01-20');
INSERT INTO customer VALUES ('234567891', 'Olivia', 'Garcia', 'Vancouver', 'British Columbia', 'Granville St', 159, '2024-02-25');
INSERT INTO customer VALUES ('135792468', 'William', 'Martinez', 'Houston', 'Texas', 'Main St', 753, '2024-03-01');
INSERT INTO customer VALUES ('987654323', 'Sophia', 'Davis', 'Philadelphia', 'Pennsylvania', 'Broad St', 159, '2024-03-05');
INSERT INTO customer VALUES ('246801357', 'James', 'Wilson', 'Phoenix', 'Arizona', 'Palm St', 852, '2024-03-10');
INSERT INTO customer VALUES ('369258147', 'Emily', 'Lopez', 'San Diego', 'California', 'Ocean Blvd', 753, '2024-03-15');
INSERT INTO customer VALUES ('951357924', 'Alexander', 'Hernandez', 'Dallas', 'Texas', 'Elm St', 357, '2024-03-20');