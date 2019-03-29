-- ============================================================
--   SGBD      		  :  H2                     
-- ============================================================




-- ============================================================
--   Sequences                                      
-- ============================================================
create sequence SEQ_ACTOR_ROLE
	start with 1000 cache 20; 

create sequence SEQ_MOVIE
	start with 1000 cache 20; 

create sequence SEQ_PERSON
	start with 1000 cache 20; 


-- ============================================================
--   Table : ACTOR_ROLE                                        
-- ============================================================
create table ACTOR_ROLE
(
    ARO_ID      	 LONG        	not null,
    ROLE        	 VARCHAR(300)	,
    PER_ID      	 LONG        	,
    MOV_ID      	 LONG        	,
    constraint PK_ACTOR_ROLE primary key (ARO_ID)
);

comment on column ACTOR_ROLE.ARO_ID is
'ARO ID';

comment on column ACTOR_ROLE.ROLE is
'ROLE';

comment on column ACTOR_ROLE.PER_ID is
'Actor';

comment on column ACTOR_ROLE.MOV_ID is
'Movie';

-- ============================================================
--   Table : MOVIE                                        
-- ============================================================
create table MOVIE
(
    MOV_ID      	 LONG        	not null,
    TITLE       	 VARCHAR(300)	,
    ORIGINAL_TITLE	 VARCHAR(300)	,
    SYNOPSIS    	 VARCHAR(10000)	,
    SHORT_SYNOPSIS	 VARCHAR(10000)	,
    KEYWORDS    	 VARCHAR(300)	,
    POSTER      	 VARCHAR(250)	,
    TRAILER_NAME	 VARCHAR(300)	,
    TRAILER_HREF	 VARCHAR(250)	,
    RUNTIME     	 INT         	,
    MOVIE_TYPE  	 VARCHAR(100)	,
    PRODUCTION_YEAR	 INT         	,
    USER_RATING 	 INT         	,
    PRESS_RATING	 INT         	,
    constraint PK_MOVIE primary key (MOV_ID)
);

comment on column MOVIE.MOV_ID is
'MOV ID';

comment on column MOVIE.TITLE is
'TITLE';

comment on column MOVIE.ORIGINAL_TITLE is
'ORIGINAL TITLE';

comment on column MOVIE.SYNOPSIS is
'SYNOPSIS';

comment on column MOVIE.SHORT_SYNOPSIS is
'SHORT SYNOPSIS';

comment on column MOVIE.KEYWORDS is
'KEYWORDS';

comment on column MOVIE.POSTER is
'POSTER';

comment on column MOVIE.TRAILER_NAME is
'TRAILER NAME';

comment on column MOVIE.TRAILER_HREF is
'TRAILER HREF';

comment on column MOVIE.RUNTIME is
'RUNTIME';

comment on column MOVIE.MOVIE_TYPE is
'MOVIE TYPE';

comment on column MOVIE.PRODUCTION_YEAR is
'PRODUCTION YEAR';

comment on column MOVIE.USER_RATING is
'USER RATING';

comment on column MOVIE.PRESS_RATING is
'PRESS RATING';

-- ============================================================
--   Table : PERSON                                        
-- ============================================================
create table PERSON
(
    PER_ID      	 LONG        	not null,
    FULL_NAME   	 VARCHAR(300)	,
    FIRST_NAME  	 VARCHAR(300)	,
    LAST_NAME   	 VARCHAR(300)	,
    BIOGRAPHY   	 VARCHAR(10000)	,
    SHORT_BIOGRAPHY	 VARCHAR(10000)	,
    SEX         	 VARCHAR(5)  	,
    PHOTO_HREF  	 VARCHAR(250)	,
    BIRTH_DATE  	 VARCHAR(20) 	,
    BIRTH_PLACE 	 VARCHAR(300)	,
    ACTIVITY    	 VARCHAR(500)	,
    constraint PK_PERSON primary key (PER_ID)
);

comment on column PERSON.PER_ID is
'PER ID';

comment on column PERSON.FULL_NAME is
'FULL NAME';

comment on column PERSON.FIRST_NAME is
'FIRST NAME';

comment on column PERSON.LAST_NAME is
'LAST NAME';

comment on column PERSON.BIOGRAPHY is
'BIOGRAPHY';

comment on column PERSON.SHORT_BIOGRAPHY is
'SHORT BIOGRAPHY';

comment on column PERSON.SEX is
'SEX';

comment on column PERSON.PHOTO_HREF is
'PHOTO HREF';

comment on column PERSON.BIRTH_DATE is
'BIRTH DATE';

comment on column PERSON.BIRTH_PLACE is
'BIRTH PLACE';

comment on column PERSON.ACTIVITY is
'ACTIVITY';


alter table ACTOR_ROLE
	add constraint FK_MOV_MRO_MOVIE foreign key (MOV_ID)
	references MOVIE (MOV_ID);

create index MOV_MRO_MOVIE_FK on ACTOR_ROLE (MOV_ID asc);

alter table ACTOR_ROLE
	add constraint FK_MRO_PER_PERSON foreign key (PER_ID)
	references PERSON (PER_ID);

create index MRO_PER_PERSON_FK on ACTOR_ROLE (PER_ID asc);


create table CAMERA
(
	MOV_ID      	 LONG        	 not null,
	PER_ID      	 LONG        	 not null,
	constraint PK_CAMERA primary key (MOV_ID, PER_ID),
	constraint FK_CAMERA_MOVIE 
		foreign key(MOV_ID)
		references MOVIE (MOV_ID),
	constraint FK_CAMERA_PERSON 
		foreign key(PER_ID)
		references PERSON (PER_ID)
);

create index CAMERA_MOVIE_FK on CAMERA (MOV_ID asc);

create index CAMERA_PERSON_FK on CAMERA (PER_ID asc);

create table DIRECTORS
(
	MOV_ID      	 LONG        	 not null,
	PER_ID      	 LONG        	 not null,
	constraint PK_DIRECTORS primary key (MOV_ID, PER_ID),
	constraint FK_DIRECTORS_MOVIE 
		foreign key(MOV_ID)
		references MOVIE (MOV_ID),
	constraint FK_DIRECTORS_PERSON 
		foreign key(PER_ID)
		references PERSON (PER_ID)
);

create index DIRECTORS_MOVIE_FK on DIRECTORS (MOV_ID asc);

create index DIRECTORS_PERSON_FK on DIRECTORS (PER_ID asc);

create table PRODUCERS
(
	MOV_ID      	 LONG        	 not null,
	PER_ID      	 LONG        	 not null,
	constraint PK_PRODUCERS primary key (MOV_ID, PER_ID),
	constraint FK_PRODUCERS_MOVIE 
		foreign key(MOV_ID)
		references MOVIE (MOV_ID),
	constraint FK_PRODUCERS_PERSON 
		foreign key(PER_ID)
		references PERSON (PER_ID)
);

create index PRODUCERS_MOVIE_FK on PRODUCERS (MOV_ID asc);

create index PRODUCERS_PERSON_FK on PRODUCERS (PER_ID asc);

create table WRITERS
(
	MOV_ID      	 LONG        	 not null,
	PER_ID      	 LONG        	 not null,
	constraint PK_WRITERS primary key (MOV_ID, PER_ID),
	constraint FK_WRITERS_MOVIE 
		foreign key(MOV_ID)
		references MOVIE (MOV_ID),
	constraint FK_WRITERS_PERSON 
		foreign key(PER_ID)
		references PERSON (PER_ID)
);

create index WRITERS_MOVIE_FK on WRITERS (MOV_ID asc);

create index WRITERS_PERSON_FK on WRITERS (PER_ID asc);

