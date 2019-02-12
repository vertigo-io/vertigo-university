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

create sequence SEQ_ROLE
	start with 1000 cache 20; 


-- ============================================================
--   Table : ACTOR                                        
-- ============================================================
create table ACTOR
(
    ACT_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(500)	not null,
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
    NAME        	 VARCHAR(500)	not null,
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
--   Table : ROLE                                        
-- ============================================================
create table ROLE
(
    ROL_ID      	 NUMERIC     	not null,
    AS_CHARACTER	 VARCHAR(1000)	not null,
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



alter table MOVIE
	add constraint FK_MOV_COU_COUNTRY foreign key (COU_ID)
	references COUNTRY (COU_ID);

create index MOV_COU_COUNTRY_FK on MOVIE (COU_ID asc);

alter table ROLE
	add constraint FK_ROL_ACT_ACTOR foreign key (ACT_ID)
	references ACTOR (ACT_ID);

create index ROL_ACT_ACTOR_FK on ROLE (ACT_ID asc);

alter table ROLE
	add constraint FK_ROL_MOV_MOVIE foreign key (MOV_ID)
	references MOVIE (MOV_ID);

create index ROL_MOV_MOVIE_FK on ROLE (MOV_ID asc);


