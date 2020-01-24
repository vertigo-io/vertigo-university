-- ============================================================
--   SGBD      		  :  H2                     
-- ============================================================




-- ============================================================
--   Sequences                                      
-- ============================================================
create sequence SEQ_MY_ACTOR
	start with 1000 cache 20; 

create sequence SEQ_MY_COUNTRY
	start with 1000 cache 20; 

create sequence SEQ_MY_MOVIE
	start with 1000 cache 20; 

create sequence SEQ_MY_ROLE
	start with 1000 cache 20; 


-- ============================================================
--   Table : MY_ACTOR                                        
-- ============================================================
create table MY_ACTOR
(
    ACT_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(500)	not null,
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
    NAME        	 VARCHAR(500)	not null,
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
    AS_CHARACTER	 VARCHAR(1000)	not null,
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


alter table MY_MOVIE
	add constraint FK__A_MMOV_MCOU_MY_COUNTRY foreign key (COU_ID)
	references MY_COUNTRY (COU_ID);

create index _A_MMOV_MCOU_MY_COUNTRY_FK on MY_MOVIE (COU_ID asc);

alter table MY_ROLE
	add constraint FK__A_MROL_MACT_MY_ACTOR foreign key (ACT_ID)
	references MY_ACTOR (ACT_ID);

create index _A_MROL_MACT_MY_ACTOR_FK on MY_ROLE (ACT_ID asc);

alter table MY_ROLE
	add constraint FK__A_MROL_MMOV_MY_MOVIE foreign key (MOV_ID)
	references MY_MOVIE (MOV_ID);

create index _A_MROL_MMOV_MY_MOVIE_FK on MY_ROLE (MOV_ID asc);


