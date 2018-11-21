create table nodes(
	id VARCHAR(36), 
	type VARCHAR(36), 
	time TIMESTAMP,
	value VARCHAR(200))
	
//////////
	
create table connections(
	id VARCHAR(36), 
	nodeID VARCHAR(36))

//////////

create table types(
	id VARCHAR(36), 
	name VARCHAR(50),
	description VARCHAR(200))
	
//////////

