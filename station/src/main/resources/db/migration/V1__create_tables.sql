CREATE TABLE STATION (
  id bigint NOT NULL AUTO_INCREMENT,
  station_id varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  hd_enabled boolean NOT NULL,
  callsign varchar(4) NOT NULL,
  PRIMARY KEY (id)
);