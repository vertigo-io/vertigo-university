-- ============================================================
--   SGBD      		  :  PostgreSql                     
-- ============================================================




-- ============================================================
--   Sequences                                      
-- ============================================================
create sequence SEQ_TUTO_OBJECT_ETAT
	start with 1000 cache 20; 

create sequence SEQ_TUTO_OBJECT_TYPE
	start with 1000 cache 20; 


-- ============================================================
--   Table : TUTO_OBJECT_ETAT                                        
-- ============================================================
create table TUTO_OBJECT_ETAT
(
    ETA_ID      	 BIGINT      	not null,
    ETAT        	 VARCHAR(30) 	not null,
    LIBELLE     	 VARCHAR(100)	not null,
    constraint PK_TUTO_OBJECT_ETAT primary key (ETA_ID)
);

comment on column TUTO_OBJECT_ETAT.ETA_ID is
'ID etat';

comment on column TUTO_OBJECT_ETAT.ETAT is
'Code Etat';

comment on column TUTO_OBJECT_ETAT.LIBELLE is
'Libell�';

-- ============================================================
--   Table : TUTO_OBJECT_TYPE                                        
-- ============================================================
create table TUTO_OBJECT_TYPE
(
    TYP_ID      	 BIGINT      	not null,
    CODE        	 VARCHAR(30) 	not null,
    LIBELLE     	 VARCHAR(100)	not null,
    constraint PK_TUTO_OBJECT_TYPE primary key (TYP_ID)
);

comment on column TUTO_OBJECT_TYPE.TYP_ID is
'ID Type';

comment on column TUTO_OBJECT_TYPE.CODE is
'Code type';

comment on column TUTO_OBJECT_TYPE.LIBELLE is
'Libell�';



alter table TUTO_OBJECT
	add constraint FK_OBJ_ETA_TUTO_OBJECT_ETAT foreign key (ETA_ID)
	references TUTO_OBJECT_ETAT (ETA_ID);

create index OBJ_ETA_TUTO_OBJECT_ETAT_FK on TUTO_OBJECT (ETA_ID asc);

alter table TUTO_OBJECT
	add constraint FK_OBJ_TYP_TUTO_OBJECT_TYPE foreign key (TYP_ID)
	references TUTO_OBJECT_TYPE (TYP_ID);

create index OBJ_TYP_TUTO_OBJECT_TYPE_FK on TUTO_OBJECT (TYP_ID asc);


