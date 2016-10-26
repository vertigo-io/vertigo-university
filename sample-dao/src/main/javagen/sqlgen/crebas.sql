-- ============================================================
--   SGBD      		  :  PostgreSql                     
-- ============================================================




-- ============================================================
--   Sequences                                      
-- ============================================================
create sequence SEQ_ACTOR
	start with 1000 cache 20; 

create sequence SEQ_COUNTRY
	start with 1000 cache 20; 

create sequence SEQ_MOVIE
	start with 1000 cache 20; 

create sequence SEQ_MY_ACTOR
	start with 1000 cache 20; 

create sequence SEQ_MY_COUNTRY
	start with 1000 cache 20; 

create sequence SEQ_MY_MOVIE
	start with 1000 cache 20; 

create sequence SEQ_MY_ROLE
	start with 1000 cache 20; 

create sequence SEQ_ROLE
	start with 1000 cache 20; 


-- ============================================================
--   Table : ACTOR                                        
-- ============================================================
create table ACTOR
(
    ACT_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(500)	,
    SEXE        	 VARCHAR(100)	,
    constraint PK_ACTOR primary key (ACT_ID)
);

comment on column ACTOR.ACT_ID is
'Id';

comment on column ACTOR.NAME is
'Nom';

comment on column ACTOR.SEXE is
'Sexe';

-- ============================================================
--   Table : COUNTRY                                        
-- ============================================================
create table COUNTRY
(
    COU_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(100)	,
    constraint PK_COUNTRY primary key (COU_ID)
);

comment on column COUNTRY.COU_ID is
'Id';

comment on column COUNTRY.NAME is
'Code du pays';

-- ============================================================
--   Table : MOVIE                                        
-- ============================================================
create table MOVIE
(
    MOV_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(500)	,
    YEAR        	 NUMERIC     	,
    IMDBID      	 VARCHAR(100)	,
    COU_ID      	 NUMERIC     	,
    constraint PK_MOVIE primary key (MOV_ID)
);

comment on column MOVIE.MOV_ID is
'Id';

comment on column MOVIE.NAME is
'Code du pays';

comment on column MOVIE.YEAR is
'Année';

comment on column MOVIE.IMDBID is
'Id Imdb';

comment on column MOVIE.COU_ID is
'Country';

-- ============================================================
--   Table : MY_ACTOR                                        
-- ============================================================
create table MY_ACTOR
(
    ACT_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(500)	,
    SEXE        	 VARCHAR(100)	,
    constraint PK_MY_ACTOR primary key (ACT_ID)
);

comment on column MY_ACTOR.ACT_ID is
'Id';

comment on column MY_ACTOR.NAME is
'Nom';

comment on column MY_ACTOR.SEXE is
'Sexe';

-- ============================================================
--   Table : MY_COUNTRY                                        
-- ============================================================
create table MY_COUNTRY
(
    COU_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(100)	,
    constraint PK_MY_COUNTRY primary key (COU_ID)
);

comment on column MY_COUNTRY.COU_ID is
'Id';

comment on column MY_COUNTRY.NAME is
'Code du pays';

-- ============================================================
--   Table : MY_MOVIE                                        
-- ============================================================
create table MY_MOVIE
(
    MOV_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(500)	,
    YEAR        	 NUMERIC     	,
    IMDBID      	 VARCHAR(100)	,
    COU_ID      	 NUMERIC     	,
    constraint PK_MY_MOVIE primary key (MOV_ID)
);

comment on column MY_MOVIE.MOV_ID is
'Id';

comment on column MY_MOVIE.NAME is
'Code du pays';

comment on column MY_MOVIE.YEAR is
'Année';

comment on column MY_MOVIE.IMDBID is
'Id Imdb';

comment on column MY_MOVIE.COU_ID is
'Country';

-- ============================================================
--   Table : MY_ROLE                                        
-- ============================================================
create table MY_ROLE
(
    ROL_ID      	 NUMERIC     	not null,
    AS_CHARACTER	 VARCHAR(1000)	,
    MOV_ID      	 NUMERIC     	,
    ACT_ID      	 NUMERIC     	,
    constraint PK_MY_ROLE primary key (ROL_ID)
);

comment on column MY_ROLE.ROL_ID is
'Id';

comment on column MY_ROLE.AS_CHARACTER is
'Dans le role de';

comment on column MY_ROLE.MOV_ID is
'Movie';

comment on column MY_ROLE.ACT_ID is
'Actor';

-- ============================================================
--   Table : ROLE                                        
-- ============================================================
create table ROLE
(
    ROL_ID      	 NUMERIC     	not null,
    AS_CHARACTER	 VARCHAR(1000)	,
    MOV_ID      	 NUMERIC     	,
    ACT_ID      	 NUMERIC     	,
    constraint PK_ROLE primary key (ROL_ID)
);

comment on column ROLE.ROL_ID is
'Id';

comment on column ROLE.AS_CHARACTER is
'Dans le role de';

comment on column ROLE.MOV_ID is
'Movie';

comment on column ROLE.ACT_ID is
'Actor';



alter table MY_MOVIE
	add constraint FK_MMOV_MCOU_MY_COUNTRY foreign key (COU_ID)
	references MY_COUNTRY (COU_ID);

create index MMOV_MCOU_MY_COUNTRY_FK on MY_MOVIE (COU_ID asc);

alter table MOVIE
	add constraint FK_MOV_COU_COUNTRY foreign key (COU_ID)
	references COUNTRY (COU_ID);

create index MOV_COU_COUNTRY_FK on MOVIE (COU_ID asc);

alter table MY_ROLE
	add constraint FK_MROL_MACT_MY_ACTOR foreign key (ACT_ID)
	references MY_ACTOR (ACT_ID);

create index MROL_MACT_MY_ACTOR_FK on MY_ROLE (ACT_ID asc);

alter table MY_ROLE
	add constraint FK_MROL_MMOV_MY_MOVIE foreign key (MOV_ID)
	references MY_MOVIE (MOV_ID);

create index MROL_MMOV_MY_MOVIE_FK on MY_ROLE (MOV_ID asc);

alter table ROLE
	add constraint FK_ROL_ACT_ACTOR foreign key (ACT_ID)
	references ACTOR (ACT_ID);

create index ROL_ACT_ACTOR_FK on ROLE (ACT_ID asc);

alter table ROLE
	add constraint FK_ROL_MOV_MOVIE foreign key (MOV_ID)
	references MOVIE (MOV_ID);

create index ROL_MOV_MOVIE_FK on ROLE (MOV_ID asc);


