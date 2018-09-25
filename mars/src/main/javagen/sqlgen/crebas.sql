-- ============================================================
--   SGBD      		  :  H2                     
-- ============================================================




-- ============================================================
--   Sequences                                      
-- ============================================================
create sequence SEQ_APPLICATION_USER
	start with 1000 cache 20; 

create sequence SEQ_CASTING
	start with 1000 cache 20; 

create sequence SEQ_MOVIE
	start with 1000 cache 20; 

create sequence SEQ_PEOPLE
	start with 1000 cache 20; 

create sequence SEQ_PROFIL
	start with 1000 cache 20; 

create sequence SEQ_SECURITY_ROLE
	start with 1000 cache 20; 

create sequence SEQ_USER_AUTHENTIFICATION
	start with 1000 cache 20; 


-- ============================================================
--   Table : APPLICATION_USER                                        
-- ============================================================
create table APPLICATION_USER
(
    USR_ID      	 NUMERIC     	not null,
    LAST_NAME   	 VARCHAR(50) 	,
    FIRST_NAME  	 VARCHAR(50) 	,
    EMAIL       	 VARCHAR(150)	,
    PRO_ID      	 NUMERIC     	,
    constraint PK_APPLICATION_USER primary key (USR_ID)
);

comment on column APPLICATION_USER.USR_ID is
'USR_ID';

comment on column APPLICATION_USER.LAST_NAME is
'Last Name';

comment on column APPLICATION_USER.FIRST_NAME is
'First Name';

comment on column APPLICATION_USER.EMAIL is
'email';

comment on column APPLICATION_USER.PRO_ID is
'Profil';

-- ============================================================
--   Table : CASTING                                        
-- ============================================================
create table CASTING
(
    CAST_ID     	 NUMERIC     	not null,
    CHARACTER_NAME	 VARCHAR(250)	,
    PEO_ID      	 NUMERIC     	not null,
    MOV_ID      	 NUMERIC     	not null,
    constraint PK_CASTING primary key (CAST_ID)
);

comment on column CASTING.CAST_ID is
'Cast_id';

comment on column CASTING.CHARACTER_NAME is
'Character name';

comment on column CASTING.PEO_ID is
'People';

comment on column CASTING.MOV_ID is
'Movie';

-- ============================================================
--   Table : MOVIE                                        
-- ============================================================
create table MOVIE
(
    MOV_ID      	 NUMERIC     	not null,
    TITLE       	 VARCHAR(250)	,
    RELEASED    	 DATE        	,
    YEAR        	 NUMERIC     	,
    RUNTIME     	 NUMERIC     	,
    DESCRIPTION 	 VARCHAR(3000)	,
    POSTER      	 VARCHAR(250)	,
    RATED       	 VARCHAR(250)	,
    constraint PK_MOVIE primary key (MOV_ID)
);

comment on column MOVIE.MOV_ID is
'MOV_ID';

comment on column MOVIE.TITLE is
'TITLE';

comment on column MOVIE.RELEASED is
'Released';

comment on column MOVIE.YEAR is
'Year';

comment on column MOVIE.RUNTIME is
'Runtime';

comment on column MOVIE.DESCRIPTION is
'Description';

comment on column MOVIE.POSTER is
'Poster';

comment on column MOVIE.RATED is
'rated';

-- ============================================================
--   Table : PEOPLE                                        
-- ============================================================
create table PEOPLE
(
    PEO_ID      	 NUMERIC     	not null,
    LAST_NAME   	 VARCHAR(50) 	,
    FIRST_NAME  	 VARCHAR(50) 	,
    PEO_NAME    	 VARCHAR(250)	,
    IMDBID      	 VARCHAR(100)	,
    constraint PK_PEOPLE primary key (PEO_ID)
);

comment on column PEOPLE.PEO_ID is
'PEO_ID';

comment on column PEOPLE.LAST_NAME is
'Last Name';

comment on column PEOPLE.FIRST_NAME is
'First Name';

comment on column PEOPLE.PEO_NAME is
'Peo Name';

comment on column PEOPLE.IMDBID is
'imdbID';

-- ============================================================
--   Table : PROFIL                                        
-- ============================================================
create table PROFIL
(
    PRO_ID      	 NUMERIC     	not null,
    LABEL       	 VARCHAR(100)	,
    constraint PK_PROFIL primary key (PRO_ID)
);

comment on column PROFIL.PRO_ID is
'PRO_ID';

comment on column PROFIL.LABEL is
'Label';

-- ============================================================
--   Table : SECURITY_ROLE                                        
-- ============================================================
create table SECURITY_ROLE
(
    SRO_CD      	 VARCHAR(100)	not null,
    LABEL       	 VARCHAR(100)	,
    constraint PK_SECURITY_ROLE primary key (SRO_CD)
);

comment on column SECURITY_ROLE.SRO_CD is
'SRO_CD';

comment on column SECURITY_ROLE.LABEL is
'Label';

-- ============================================================
--   Table : USER_AUTHENTIFICATION                                        
-- ============================================================
create table USER_AUTHENTIFICATION
(
    AUTH_ID     	 NUMERIC     	not null,
    LOGIN       	 VARCHAR(50) 	,
    PASSWORD    	 VARCHAR(32) 	,
    USR_ID      	 NUMERIC     	not null,
    constraint PK_USER_AUTHENTIFICATION primary key (AUTH_ID)
);

comment on column USER_AUTHENTIFICATION.AUTH_ID is
'AUTH_ID';

comment on column USER_AUTHENTIFICATION.LOGIN is
'Login';

comment on column USER_AUTHENTIFICATION.PASSWORD is
'Password';

comment on column USER_AUTHENTIFICATION.USR_ID is
'Application user';



alter table USER_AUTHENTIFICATION
	add constraint FK_AUTH_USR_APPLICATION_USER foreign key (USR_ID)
	references APPLICATION_USER (USR_ID);

create index AUTH_USR_APPLICATION_USER_FK on USER_AUTHENTIFICATION (USR_ID asc);

alter table CASTING
	add constraint FK_CAST_MOV_MOVIE foreign key (MOV_ID)
	references MOVIE (MOV_ID);

create index CAST_MOV_MOVIE_FK on CASTING (MOV_ID asc);

alter table CASTING
	add constraint FK_CAST_PEO_PEOPLE foreign key (PEO_ID)
	references PEOPLE (PEO_ID);

create index CAST_PEO_PEOPLE_FK on CASTING (PEO_ID asc);

alter table APPLICATION_USER
	add constraint FK_USR_PRO_PROFIL foreign key (PRO_ID)
	references PROFIL (PRO_ID);

create index USR_PRO_PROFIL_FK on APPLICATION_USER (PRO_ID asc);


create table PRO_SRO
(
	PRO_ID      	 NUMERIC     	 not null,
	SRO_CD      	 VARCHAR(100)	 not null,
	constraint PK_PRO_SRO primary key (PRO_ID, SRO_CD),
	constraint FK_PRO_SRO_PROFIL 
		foreign key(PRO_ID)
		references PROFIL (PRO_ID),
	constraint FK_PRO_SRO_SECURITY_ROLE 
		foreign key(SRO_CD)
		references SECURITY_ROLE (SRO_CD)
);

create index PRO_SRO_PROFIL_FK on PRO_SRO (PRO_ID asc);

create index PRO_SRO_SECURITY_ROLE_FK on PRO_SRO (SRO_CD asc);

