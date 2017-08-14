create table nodes(
	id VARCHAR(36), 
	type VARCHAR(36), 
	time TIMESTAMP,
	value VARCHAR(100))
	
//////////
	
create table connections(
	nodeAID VARCHAR(36), 
	nodeBID VARCHAR(36), 
	nodeAType VARCHAR(36), 
	nodeBType VARCHAR(36))

//////////

create table types(
	id VARCHAR(36), 
	name VARCHAR(50),
	description VARCHAR(200))
	
//////////

