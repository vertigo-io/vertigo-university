insert into COUNTRY(COU_ID, NAME) values (10, 'France');
insert into COUNTRY(COU_ID, NAME) values (11, 'USA');

insert into MOVIE(MOV_ID, NAME, COU_ID, YEAR, IMDBID ) values (10, 'Star Wars (1977)',  11, 1977, null);

insert into USER_GROUP(GRP_ID, NAME) values (10, 'TIMEs cover');
insert into USER(USR_ID, LOGIN, NAME, EMAIL, COU_ID, GRP_ID) values (nextval('SEQ_USER'), 'palmer.luckey', 'Palmer Luckey', 'palmer.luckey@yopmail.com',	11, 10);
insert into USER(USR_ID, LOGIN, NAME, EMAIL, COU_ID, GRP_ID) values (nextval('SEQ_USER'), 'bill.clinton', 'Bill Clinton', 'bill.clinton@yopmail.com', 11, 10);
insert into USER(USR_ID, LOGIN, NAME, EMAIL, COU_ID, GRP_ID) values (nextval('SEQ_USER'), 'john.doe', 'John Doe', 'john.doe@yopmail.com', 10, 10);

insert into USER_GROUP(GRP_ID, NAME) values (11, 'Users');
insert into USER(USR_ID, LOGIN, NAME, EMAIL, COU_ID, GRP_ID) values (nextval('SEQ_USER'), 'npi', 'Npi', 'npi@vertigo.io', 10, 11);
insert into USER(USR_ID, LOGIN, NAME, EMAIL, COU_ID, GRP_ID) values (nextval('SEQ_USER'), 'hb', 'Hugo Bertrand', 'hb@yopmail.com', 10, 11);
insert into USER(USR_ID, LOGIN, NAME, EMAIL, COU_ID, GRP_ID) values (nextval('SEQ_USER'), 'admin', 'Super Admin', 'admin@yopmail.com', 10, 11);


insert into USER_GROUP(GRP_ID, NAME) values (12, 'Readers');
insert into USER(USR_ID, LOGIN, NAME, EMAIL, COU_ID, GRP_ID) values (nextval('SEQ_USER'), 'phil.mormon', 'Phil Mormon', 'phil.mormon@yopmail.com', 10, 12);
insert into USER(USR_ID, LOGIN, NAME, EMAIL, COU_ID, GRP_ID) values (nextval('SEQ_USER'), 'bdufour', 'Bernard Dufour', 'bdufour@yopmail.com', 10, 12);
insert into USER(USR_ID, LOGIN, NAME, EMAIL, COU_ID, GRP_ID) values (nextval('SEQ_USER'), 'nicolas.legendre', 'Nicolas Legendre', 'nicolas.legendre@yopmail.com', 10, 12);
insert into USER(USR_ID, LOGIN, NAME, EMAIL, COU_ID, GRP_ID) values (nextval('SEQ_USER'), 'marie.garnier', 'Marie Garnier', 'marie.garnier@yopmail.com', 10, 12);

commit;