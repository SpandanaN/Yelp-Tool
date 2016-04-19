CREATE TABLE Users(
  name VARCHAR(40),
  user_id VARCHAR(25),
  review_count INTEGER,
  avg_stars REAL,
  funny_votes INTEGER,
  useful_votes INTEGER,
  cool_votes INTEGER,
  PRIMARY KEY(user_id)
  );

CREATE TABLE Business(
    business_id VARCHAR(25),
    name VARCHAR(70),
    review_count INTEGER,
    city VARCHAR(30),
    state VARCHAR(5),
    stars REAL,
    zipcode VARCHAR(5),
    PRIMARY KEY(business_id)
    );

CREATE TABLE Reviews(
  review_id VARCHAR(30),
  user_id VARCHAR(25),
  review_date VARCHAR(15),
  text CLOB,
  business_id VARCHAR(30),
  stars REAL,
  funny_votes INTEGER,
  useful_votes INTEGER,
  cool_votes INTEGER,
  PRIMARY KEY(review_id),
  FOREIGN KEY (business_id) REFERENCES Business(business_id),
  FOREIGN KEY (user_id) REFERENCES Users(user_id)
  );

CREATE TABLE BusinessCategory(
  business_id VARCHAR(25),
  main_category VARCHAR(30),
  sub_category VARCHAR(40),
  PRIMARY KEY (business_id,main_category,sub_category),
  FOREIGN KEY (business_id) REFERENCES Business(business_id)
  );

CREATE TABLE BusinessAttributes(
  business_id VARCHAR(25),
  attribute VARCHAR(30),
  PRIMARY KEY(business_id,attribute),
  FOREIGN KEY(business_id) REFERENCES Business(business_id) 
  );

CREATE TABLE BusinessHours(
    business_id VARCHAR(25),
    day VARCHAR(10),
    open INTEGER,
    close INTEGER,
    PRIMARY KEY(business_id,day),
    FOREIGN KEY (business_id) REFERENCES Business(business_id)
    );

CREATE TABLE BusinessCheckIn(
    business_id VARCHAR(25),
    day VARCHAR(10),
    checkin INTEGER,
    counter integer,
    PRIMARY KEY(business_id,day,checkin,counter),
    FOREIGN KEY (business_id) REFERENCES Business(business_id)
    );