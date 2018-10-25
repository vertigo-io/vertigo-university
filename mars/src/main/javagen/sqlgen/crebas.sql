-- ============================================================
--   SGBD      		  :  H2                     
-- ============================================================




-- ============================================================
--   Sequences                                      
-- ============================================================
create sequence SEQ_BASE
	start with 1000 cache 20; 

create sequence SEQ_BASE_TYPE
	start with 1000 cache 20; 

create sequence SEQ_EQUIPMENT
	start with 1000 cache 20; 

create sequence SEQ_EQUIPMENT_CATEGORY
	start with 1000 cache 20; 

create sequence SEQ_EQUIPMENT_FEATURE
	start with 1000 cache 20; 

create sequence SEQ_EQUIPMENT_TYPE
	start with 1000 cache 20; 

create sequence SEQ_GEOSECTOR
	start with 1000 cache 20; 

create sequence SEQ_JOB
	start with 1000 cache 20; 

create sequence SEQ_JOB_STATUS
	start with 1000 cache 20; 

create sequence SEQ_MISSION
	start with 1000 cache 20; 

create sequence SEQ_PERSON
	start with 1000 cache 20; 

create sequence SEQ_PICTURE
	start with 1000 cache 20; 

create sequence SEQ_TICKET
	start with 1000 cache 20; 

create sequence SEQ_TICKET_STATUS
	start with 1000 cache 20; 

create sequence SEQ_WORK_ORDER
	start with 1000 cache 20; 

create sequence SEQ_WORK_ORDER_STATUS
	start with 1000 cache 20; 


-- ============================================================
--   Table : BASE                                        
-- ============================================================
create table BASE
(
    BAS_ID      	 NUMERIC     	not null,
    CODE        	 VARCHAR(100)	,
    NAME        	 VARCHAR(100)	,
    HEALTH_LEVEL	 NUMERIC     	,
    CREATION_DATE	 DATE        	,
    DESCRIPTION 	 VARCHAR(350)	,
    GEO_LOCATION	 VARCHAR(100)	,
    ASSETS_VALUE	 NUMERIC(12,2)	,
    RENTING_FEE 	 NUMERIC(12,2)	,
    BASE_TYPE_ID	 VARCHAR(100)	,
    GEOSECTOR_ID	 NUMERIC     	,
    constraint PK_BASE primary key (BAS_ID)
);

comment on column BASE.BAS_ID is
'Id';

comment on column BASE.CODE is
'Base Code';

comment on column BASE.NAME is
'Name';

comment on column BASE.HEALTH_LEVEL is
'Health Level';

comment on column BASE.CREATION_DATE is
'Base Creation Date';

comment on column BASE.DESCRIPTION is
'Description';

comment on column BASE.GEO_LOCATION is
'Geographic Location';

comment on column BASE.ASSETS_VALUE is
'Current base assets value';

comment on column BASE.RENTING_FEE is
'Renting Fee';

comment on column BASE.BASE_TYPE_ID is
'Base Type';

comment on column BASE.GEOSECTOR_ID is
'Base Geosector';

-- ============================================================
--   Table : BASE_TYPE                                        
-- ============================================================
create table BASE_TYPE
(
    BASE_TYPE_ID	 VARCHAR(100)	not null,
    LABEL       	 VARCHAR(100)	,
    constraint PK_BASE_TYPE primary key (BASE_TYPE_ID)
);

comment on column BASE_TYPE.BASE_TYPE_ID is
'Id';

comment on column BASE_TYPE.LABEL is
'Base Type Label';

-- ============================================================
--   Table : EQUIPMENT                                        
-- ============================================================
create table EQUIPMENT
(
    EQUIPMENT_ID	 NUMERIC     	not null,
    NAME        	 VARCHAR(100)	,
    CODE        	 VARCHAR(100)	,
    HEALTH_LEVEL	 NUMERIC     	,
    PURCHASE_DATE	 DATE        	,
    DESCRIPTION 	 VARCHAR(350)	,
    TAGS        	 TEXT        	,
    GEO_LOCATION	 VARCHAR(100)	,
    RENTING_FEE 	 NUMERIC(12,2)	,
    EQUIPMENT_VALUE	 NUMERIC(12,2)	,
    BASE_ID     	 NUMERIC     	,
    GEOSECTOR_ID	 NUMERIC     	,
    EQUIPMENT_CATEGORY_ID	 NUMERIC     	,
    EQUIPMENT_TYPE_ID	 NUMERIC     	,
    constraint PK_EQUIPMENT primary key (EQUIPMENT_ID)
);

comment on column EQUIPMENT.EQUIPMENT_ID is
'Id';

comment on column EQUIPMENT.NAME is
'Name';

comment on column EQUIPMENT.CODE is
'Base Code';

comment on column EQUIPMENT.HEALTH_LEVEL is
'Health Level';

comment on column EQUIPMENT.PURCHASE_DATE is
'Date of purchase';

comment on column EQUIPMENT.DESCRIPTION is
'Description';

comment on column EQUIPMENT.TAGS is
'Tags';

comment on column EQUIPMENT.GEO_LOCATION is
'Geographic Location';

comment on column EQUIPMENT.RENTING_FEE is
'Renting Fee';

comment on column EQUIPMENT.EQUIPMENT_VALUE is
'Current equipment value';

comment on column EQUIPMENT.BASE_ID is
'Base';

comment on column EQUIPMENT.GEOSECTOR_ID is
'Equipment Geosector';

comment on column EQUIPMENT.EQUIPMENT_CATEGORY_ID is
'Equipment Category';

comment on column EQUIPMENT.EQUIPMENT_TYPE_ID is
'Equipment Type';

-- ============================================================
--   Table : EQUIPMENT_CATEGORY                                        
-- ============================================================
create table EQUIPMENT_CATEGORY
(
    EQUIPMENT_CATEGORY_ID	 NUMERIC     	not null,
    LABEL       	 VARCHAR(100)	,
    ACTIVE      	 bool        	,
    constraint PK_EQUIPMENT_CATEGORY primary key (EQUIPMENT_CATEGORY_ID)
);

comment on column EQUIPMENT_CATEGORY.EQUIPMENT_CATEGORY_ID is
'Id';

comment on column EQUIPMENT_CATEGORY.LABEL is
'Equipment Category Label';

comment on column EQUIPMENT_CATEGORY.ACTIVE is
'Equipment category is active';

-- ============================================================
--   Table : EQUIPMENT_FEATURE                                        
-- ============================================================
create table EQUIPMENT_FEATURE
(
    EQUIPMENT_FEATURE_ID	 NUMERIC     	not null,
    NAME        	 VARCHAR(100)	,
    constraint PK_EQUIPMENT_FEATURE primary key (EQUIPMENT_FEATURE_ID)
);

comment on column EQUIPMENT_FEATURE.EQUIPMENT_FEATURE_ID is
'Id';

comment on column EQUIPMENT_FEATURE.NAME is
'Name';

-- ============================================================
--   Table : EQUIPMENT_TYPE                                        
-- ============================================================
create table EQUIPMENT_TYPE
(
    EQUIPMENT_TYPE_ID	 NUMERIC     	not null,
    LABEL       	 VARCHAR(100)	,
    ACTIVE      	 bool        	,
    constraint PK_EQUIPMENT_TYPE primary key (EQUIPMENT_TYPE_ID)
);

comment on column EQUIPMENT_TYPE.EQUIPMENT_TYPE_ID is
'Id';

comment on column EQUIPMENT_TYPE.LABEL is
'Equipment Type Label';

comment on column EQUIPMENT_TYPE.ACTIVE is
'Equipment type is active';

-- ============================================================
--   Table : GEOSECTOR                                        
-- ============================================================
create table GEOSECTOR
(
    GEOSECTOR_ID	 NUMERIC     	not null,
    SECTOR_LABEL	 VARCHAR(100)	,
    constraint PK_GEOSECTOR primary key (GEOSECTOR_ID)
);

comment on column GEOSECTOR.GEOSECTOR_ID is
'Id';

comment on column GEOSECTOR.SECTOR_LABEL is
'Sector Label';

-- ============================================================
--   Table : JOB                                        
-- ============================================================
create table JOB
(
    JOB_ID      	 NUMERIC     	not null,
    CODE        	 VARCHAR(100)	,
    NAME        	 VARCHAR(100)	,
    DESCRIPTION 	 VARCHAR(350)	,
    DUE_DATE    	 DATE        	,
    constraint PK_JOB primary key (JOB_ID)
);

comment on column JOB.JOB_ID is
'Id';

comment on column JOB.CODE is
'CODE';

comment on column JOB.NAME is
'Job Name';

comment on column JOB.DESCRIPTION is
'Job Description';

comment on column JOB.DUE_DATE is
'Due Date';

-- ============================================================
--   Table : JOB_STATUS                                        
-- ============================================================
create table JOB_STATUS
(
    JOB_STATUS_ID	 VARCHAR(100)	not null,
    LABEL       	 VARCHAR(100)	,
    constraint PK_JOB_STATUS primary key (JOB_STATUS_ID)
);

comment on column JOB_STATUS.JOB_STATUS_ID is
'Id';

comment on column JOB_STATUS.LABEL is
'Status Label';

-- ============================================================
--   Table : MISSION                                        
-- ============================================================
create table MISSION
(
    MISSION_ID  	 NUMERIC     	not null,
    ROLE        	 VARCHAR(100)	,
    BASE_ID     	 NUMERIC     	,
    constraint PK_MISSION primary key (MISSION_ID)
);

comment on column MISSION.MISSION_ID is
'Id';

comment on column MISSION.ROLE is
'Role';

comment on column MISSION.BASE_ID is
'Base';

-- ============================================================
--   Table : PERSON                                        
-- ============================================================
create table PERSON
(
    PERSON_ID   	 NUMERIC     	not null,
    FIRST_NAME  	 VARCHAR(100)	,
    LAST_NAME   	 VARCHAR(100)	,
    E_MAIL      	 VARCHAR(150)	,
    MISSION_ID  	 NUMERIC     	,
    constraint PK_PERSON primary key (PERSON_ID)
);

comment on column PERSON.PERSON_ID is
'Id';

comment on column PERSON.FIRST_NAME is
'Name';

comment on column PERSON.LAST_NAME is
'Name';

comment on column PERSON.E_MAIL is
'E-mail';

comment on column PERSON.MISSION_ID is
'Mission';

-- ============================================================
--   Table : PICTURE                                        
-- ============================================================
create table PICTURE
(
    PICTURE_ID  	 NUMERIC     	not null,
    PICTUREFILE_ID	 NUMERIC     	,
    BASE_ID     	 NUMERIC     	not null,
    constraint PK_PICTURE primary key (PICTURE_ID)
);

comment on column PICTURE.PICTURE_ID is
'Id';

comment on column PICTURE.PICTUREFILE_ID is
'Id';

comment on column PICTURE.BASE_ID is
'Base';

-- ============================================================
--   Table : TICKET                                        
-- ============================================================
create table TICKET
(
    TICKET_ID   	 NUMERIC     	not null,
    CODE        	 VARCHAR(100)	,
    TITLE       	 VARCHAR(100)	,
    DESCRIPTION 	 VARCHAR(350)	,
    DATE_CREATED	 DATE        	,
    WORK_ORDER_STATUS_ID	 VARCHAR(100)	,
    EQUIPMENT_ID	 NUMERIC     	,
    constraint PK_TICKET primary key (TICKET_ID)
);

comment on column TICKET.TICKET_ID is
'Id';

comment on column TICKET.CODE is
'Ticket Number';

comment on column TICKET.TITLE is
'Ticket title';

comment on column TICKET.DESCRIPTION is
'Ticket Descrption';

comment on column TICKET.DATE_CREATED is
'Ticket Creation Date';

comment on column TICKET.WORK_ORDER_STATUS_ID is
'Ticket Status';

comment on column TICKET.EQUIPMENT_ID is
'Equipment';

-- ============================================================
--   Table : TICKET_STATUS                                        
-- ============================================================
create table TICKET_STATUS
(
    TICKET_STATUS_ID	 VARCHAR(100)	not null,
    LABEL       	 VARCHAR(100)	,
    constraint PK_TICKET_STATUS primary key (TICKET_STATUS_ID)
);

comment on column TICKET_STATUS.TICKET_STATUS_ID is
'Id';

comment on column TICKET_STATUS.LABEL is
'Status Label';

-- ============================================================
--   Table : WORK_ORDER                                        
-- ============================================================
create table WORK_ORDER
(
    MO_ID       	 NUMERIC     	not null,
    TICKET_CODE 	 VARCHAR(100)	,
    NAME        	 VARCHAR(100)	,
    DESCRIPTION 	 VARCHAR(350)	,
    DUE_DATE    	 DATE        	,
    TICKET_ID   	 NUMERIC     	,
    WORK_ORDER_STATUS_ID	 VARCHAR(100)	,
    constraint PK_WORK_ORDER primary key (MO_ID)
);

comment on column WORK_ORDER.MO_ID is
'Id';

comment on column WORK_ORDER.TICKET_CODE is
'Ticket Number';

comment on column WORK_ORDER.NAME is
'Mainenance Operation';

comment on column WORK_ORDER.DESCRIPTION is
'Maintenance Operation Descrption';

comment on column WORK_ORDER.DUE_DATE is
'Due Date';

comment on column WORK_ORDER.TICKET_ID is
'Ticket';

comment on column WORK_ORDER.WORK_ORDER_STATUS_ID is
'Work Order Status';

-- ============================================================
--   Table : WORK_ORDER_STATUS                                        
-- ============================================================
create table WORK_ORDER_STATUS
(
    WORK_ORDER_STATUS_ID	 VARCHAR(100)	not null,
    LABEL       	 VARCHAR(100)	,
    constraint PK_WORK_ORDER_STATUS primary key (WORK_ORDER_STATUS_ID)
);

comment on column WORK_ORDER_STATUS.WORK_ORDER_STATUS_ID is
'Id';

comment on column WORK_ORDER_STATUS.LABEL is
'Status Label';



alter table BASE
	add constraint FK_BASE_BASETYPE_BASE_TYPE foreign key (BASE_TYPE_ID)
	references BASE_TYPE (BASE_TYPE_ID);

create index BASE_BASETYPE_BASE_TYPE_FK on BASE (BASE_TYPE_ID asc);

alter table EQUIPMENT
	add constraint FK_BASE_EQUIPMENT_BASE foreign key (BASE_ID)
	references BASE (BAS_ID);

create index BASE_EQUIPMENT_BASE_FK on EQUIPMENT (BASE_ID asc);

alter table BASE
	add constraint FK_BASE_GEOSECTOR_GEOSECTOR foreign key (GEOSECTOR_ID)
	references GEOSECTOR (GEOSECTOR_ID);

create index BASE_GEOSECTOR_GEOSECTOR_FK on BASE (GEOSECTOR_ID asc);

alter table PICTURE
	add constraint FK_BASE_PICTURE_BASE foreign key (BASE_ID)
	references BASE (BAS_ID);

create index BASE_PICTURE_BASE_FK on PICTURE (BASE_ID asc);

alter table EQUIPMENT
	add constraint FK_EQUIPMENT_EQUIPMENT_TYPE_EQUIPMENT_TYPE foreign key (EQUIPMENT_TYPE_ID)
	references EQUIPMENT_TYPE (EQUIPMENT_TYPE_ID);

create index EQUIPMENT_EQUIPMENT_TYPE_EQUIPMENT_TYPE_FK on EQUIPMENT (EQUIPMENT_TYPE_ID asc);

alter table EQUIPMENT
	add constraint FK_EQUIPMENT_GEOSECTOR_GEOSECTOR foreign key (GEOSECTOR_ID)
	references GEOSECTOR (GEOSECTOR_ID);

create index EQUIPMENT_GEOSECTOR_GEOSECTOR_FK on EQUIPMENT (GEOSECTOR_ID asc);

alter table TICKET
	add constraint FK_EQUIPMENT_TICKET_EQUIPMENT foreign key (EQUIPMENT_ID)
	references EQUIPMENT (EQUIPMENT_ID);

create index EQUIPMENT_TICKET_EQUIPMENT_FK on TICKET (EQUIPMENT_ID asc);

alter table EQUIPMENT
	add constraint FK_EQUIPMENT_TYPE_EQUIPMENT_CATEGORY_EQUIPMENT_CATEGORY foreign key (EQUIPMENT_CATEGORY_ID)
	references EQUIPMENT_CATEGORY (EQUIPMENT_CATEGORY_ID);

create index EQUIPMENT_TYPE_EQUIPMENT_CATEGORY_EQUIPMENT_CATEGORY_FK on EQUIPMENT (EQUIPMENT_CATEGORY_ID asc);

alter table MISSION
	add constraint FK_MISSION_BASE_BASE foreign key (BASE_ID)
	references BASE (BAS_ID);

create index MISSION_BASE_BASE_FK on MISSION (BASE_ID asc);

alter table PERSON
	add constraint FK_PERSON_MISSION_MISSION foreign key (MISSION_ID)
	references MISSION (MISSION_ID);

create index PERSON_MISSION_MISSION_FK on PERSON (MISSION_ID asc);

alter table TICKET
	add constraint FK_TICKET_TICKET_STATUS_TICKET_STATUS foreign key (WORK_ORDER_STATUS_ID)
	references TICKET_STATUS (TICKET_STATUS_ID);

create index TICKET_TICKET_STATUS_TICKET_STATUS_FK on TICKET (WORK_ORDER_STATUS_ID asc);

alter table WORK_ORDER
	add constraint FK_TICKET_WORK_ORDER_TICKET foreign key (TICKET_ID)
	references TICKET (TICKET_ID);

create index TICKET_WORK_ORDER_TICKET_FK on WORK_ORDER (TICKET_ID asc);

alter table WORK_ORDER
	add constraint FK_WORK_ORDER_WORK_ORDER_STATUS_WORK_ORDER_STATUS foreign key (WORK_ORDER_STATUS_ID)
	references WORK_ORDER_STATUS (WORK_ORDER_STATUS_ID);

create index WORK_ORDER_WORK_ORDER_STATUS_WORK_ORDER_STATUS_FK on WORK_ORDER (WORK_ORDER_STATUS_ID asc);


