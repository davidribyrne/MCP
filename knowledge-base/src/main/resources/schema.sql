create table nodes(
	id CHAR(16) FOR BIT DATA, 
	type CHAR(16) FOR BIT DATA, 
	value CLOB(64K))
	
//////////
	
create table connections(
	id CHAR(16) FOR BIT DATA, 
	nodeID CHAR(16) FOR BIT DATA)

//////////

create table types(
	id CHAR(16) FOR BIT DATA, 
	name VARCHAR(50),
	description VARCHAR(200))
	
//////////

