-- ============================================================
--   SGBD      		  :  H2                     
-- ============================================================

-- ============================================================
--   Drop                                       
-- ============================================================
drop table IF EXISTS BASE cascade;
drop sequence IF EXISTS SEQ_BASE;
drop table IF EXISTS BASE_TYPE cascade;
drop table IF EXISTS BUSINESS cascade;
drop sequence IF EXISTS SEQ_BUSINESS;
drop table IF EXISTS EQUIPMENT cascade;
drop sequence IF EXISTS SEQ_EQUIPMENT;
drop table IF EXISTS EQUIPMENT_CATEGORY cascade;
drop sequence IF EXISTS SEQ_EQUIPMENT_CATEGORY;
drop table IF EXISTS EQUIPMENT_FEATURE cascade;
drop sequence IF EXISTS SEQ_EQUIPMENT_FEATURE;
drop table IF EXISTS EQUIPMENT_TYPE cascade;
drop sequence IF EXISTS SEQ_EQUIPMENT_TYPE;
drop table IF EXISTS GEOSECTOR cascade;
drop sequence IF EXISTS SEQ_GEOSECTOR;
drop table IF EXISTS GROUPS cascade;
drop sequence IF EXISTS SEQ_GROUPS;
drop table IF EXISTS MEDIA_FILE_INFO cascade;
drop sequence IF EXISTS SEQ_MEDIA_FILE_INFO;
drop table IF EXISTS MISSION cascade;
drop sequence IF EXISTS SEQ_MISSION;
drop table IF EXISTS OPENDATA_SET cascade;
drop sequence IF EXISTS SEQ_OPENDATA_SET;
drop table IF EXISTS OPENDATA_SET_STATUS cascade;
drop table IF EXISTS PERSON cascade;
drop sequence IF EXISTS SEQ_PERSON;
drop table IF EXISTS PICTURE cascade;
drop sequence IF EXISTS SEQ_PICTURE;
drop table IF EXISTS SUPPLIER cascade;
drop table IF EXISTS TAG cascade;
drop sequence IF EXISTS SEQ_TAG;
drop table IF EXISTS TICKET cascade;
drop sequence IF EXISTS SEQ_TICKET;
drop table IF EXISTS TICKET_STATUS cascade;
drop table IF EXISTS WORK_ORDER cascade;
drop sequence IF EXISTS SEQ_WORK_ORDER;
drop table IF EXISTS WORK_ORDER_STATUS cascade;




-- ============================================================
--   Sequences                                      
-- ============================================================
create sequence SEQ_BASE
	start with 1000 cache 20; 


create sequence SEQ_BUSINESS
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

create sequence SEQ_GROUPS
	start with 1000 cache 20; 

create sequence SEQ_MEDIA_FILE_INFO
	start with 1000 cache 20; 

create sequence SEQ_MISSION
	start with 1000 cache 20; 

create sequence SEQ_OPENDATA_SET
	start with 1000 cache 20; 


create sequence SEQ_PERSON
	start with 1000 cache 20; 

create sequence SEQ_PICTURE
	start with 1000 cache 20; 


create sequence SEQ_TAG
	start with 1000 cache 20; 

create sequence SEQ_TICKET
	start with 1000 cache 20; 


create sequence SEQ_WORK_ORDER
	start with 1000 cache 20; 



-- ============================================================
--   Table : BASE                                        
-- ============================================================
create table BASE
(
    BASE_ID     	 NUMERIC     	not null,
    CODE        	 VARCHAR(100)	not null,
    NAME        	 VARCHAR(100)	,
    HEALTH_LEVEL	 NUMERIC     	,
    CREATION_DATE	 DATE        	,
    DESCRIPTION 	 VARCHAR(350)	,
    GEO_LOCATION	 VARCHAR(100)	,
    ASSETS_VALUE	 NUMERIC(12,2)	,
    RENTING_FEE 	 NUMERIC(12,2)	,
    TAGS        	 TEXT        	,
    BASE_TYPE_ID	 VARCHAR(100)	,
    GEOSECTOR_ID	 NUMERIC     	,
    constraint PK_BASE primary key (BASE_ID)
);

comment on column BASE.BASE_ID is
'Id';

comment on column BASE.CODE is
'Code';

comment on column BASE.NAME is
'Name';

comment on column BASE.HEALTH_LEVEL is
'Health Level';

comment on column BASE.CREATION_DATE is
'Creation Date';

comment on column BASE.DESCRIPTION is
'Description';

comment on column BASE.GEO_LOCATION is
'Geographic Location';

comment on column BASE.ASSETS_VALUE is
'Current base assets value';

comment on column BASE.RENTING_FEE is
'Renting Fee';

comment on column BASE.TAGS is
'Tags';

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
--   Table : BUSINESS                                        
-- ============================================================
create table BUSINESS
(
    BUSINESS_ID 	 NUMERIC     	not null,
    NAME        	 VARCHAR(100)	,
    ICON        	 VARCHAR(100)	,
    constraint PK_BUSINESS primary key (BUSINESS_ID)
);

comment on column BUSINESS.BUSINESS_ID is
'Id';

comment on column BUSINESS.NAME is
'Name';

comment on column BUSINESS.ICON is
'Icon';

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
    BUSINESS_ID 	 NUMERIC     	,
    EQUIPMENT_TYPE_ID	 NUMERIC     	,
    constraint PK_EQUIPMENT primary key (EQUIPMENT_ID)
);

comment on column EQUIPMENT.EQUIPMENT_ID is
'Id';

comment on column EQUIPMENT.NAME is
'Name';

comment on column EQUIPMENT.CODE is
'Code';

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

comment on column EQUIPMENT.BUSINESS_ID is
'Business';

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
    EQUIPMENT_ID	 NUMERIC     	,
    constraint PK_EQUIPMENT_FEATURE primary key (EQUIPMENT_FEATURE_ID)
);

comment on column EQUIPMENT_FEATURE.EQUIPMENT_FEATURE_ID is
'Id';

comment on column EQUIPMENT_FEATURE.NAME is
'Name';

comment on column EQUIPMENT_FEATURE.EQUIPMENT_ID is
'Equipment';

-- ============================================================
--   Table : EQUIPMENT_TYPE                                        
-- ============================================================
create table EQUIPMENT_TYPE
(
    EQUIPMENT_TYPE_ID	 NUMERIC     	not null,
    LABEL       	 VARCHAR(100)	,
    ACTIVE      	 bool        	,
    EQUIPMENT_CATEGORY_ID	 NUMERIC     	,
    constraint PK_EQUIPMENT_TYPE primary key (EQUIPMENT_TYPE_ID)
);

comment on column EQUIPMENT_TYPE.EQUIPMENT_TYPE_ID is
'Id';

comment on column EQUIPMENT_TYPE.LABEL is
'Equipment Type Label';

comment on column EQUIPMENT_TYPE.ACTIVE is
'Equipment type is active';

comment on column EQUIPMENT_TYPE.EQUIPMENT_CATEGORY_ID is
'Equipment Category';

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
--   Table : GROUPS                                        
-- ============================================================
create table GROUPS
(
    GROUP_ID    	 NUMERIC     	not null,
    NAME        	 VARCHAR(100)	,
    constraint PK_GROUPS primary key (GROUP_ID)
);

comment on column GROUPS.GROUP_ID is
'Id';

comment on column GROUPS.NAME is
'Name';

-- ============================================================
--   Table : MEDIA_FILE_INFO                                        
-- ============================================================
create table MEDIA_FILE_INFO
(
    FIL_ID      	 NUMERIC     	not null,
    FILE_NAME   	 VARCHAR(100)	not null,
    MIME_TYPE   	 VARCHAR(100)	not null,
    LENGTH      	 NUMERIC     	not null,
    LAST_MODIFIED	 TIMESTAMP   	not null,
    FILE_PATH   	 VARCHAR(500)	,
    FILE_DATA   	 bytea       	,
    constraint PK_MEDIA_FILE_INFO primary key (FIL_ID)
);

comment on column MEDIA_FILE_INFO.FIL_ID is
'Id';

comment on column MEDIA_FILE_INFO.FILE_NAME is
'Name';

comment on column MEDIA_FILE_INFO.MIME_TYPE is
'MimeType';

comment on column MEDIA_FILE_INFO.LENGTH is
'Size';

comment on column MEDIA_FILE_INFO.LAST_MODIFIED is
'Modification Date';

comment on column MEDIA_FILE_INFO.FILE_PATH is
'path';

comment on column MEDIA_FILE_INFO.FILE_DATA is
'data';

-- ============================================================
--   Table : MISSION                                        
-- ============================================================
create table MISSION
(
    MISSION_ID  	 NUMERIC     	not null,
    ROLE        	 VARCHAR(100)	,
    PERSON_ID   	 NUMERIC     	,
    BASE_ID     	 NUMERIC     	,
    BUSINESS_ID 	 NUMERIC     	,
    constraint PK_MISSION primary key (MISSION_ID)
);

comment on column MISSION.MISSION_ID is
'Id';

comment on column MISSION.ROLE is
'Role';

comment on column MISSION.PERSON_ID is
'Person';

comment on column MISSION.BASE_ID is
'Base';

comment on column MISSION.BUSINESS_ID is
'Business';

-- ============================================================
--   Table : OPENDATA_SET                                        
-- ============================================================
create table OPENDATA_SET
(
    ODS_ID      	 NUMERIC     	not null,
    CODE        	 VARCHAR(100)	,
    TITLE       	 VARCHAR(100)	,
    DESCRIPTION 	 VARCHAR(350)	,
    END_POINT_URL	 TEXT        	,
    PICTUREFILE_ID	 NUMERIC     	,
    TAGS        	 TEXT        	,
    OPENDATA_SET_STATUS_ID	 VARCHAR(100)	,
    constraint PK_OPENDATA_SET primary key (ODS_ID)
);

comment on column OPENDATA_SET.ODS_ID is
'Id';

comment on column OPENDATA_SET.CODE is
'Code';

comment on column OPENDATA_SET.TITLE is
'Title';

comment on column OPENDATA_SET.DESCRIPTION is
'Description';

comment on column OPENDATA_SET.END_POINT_URL is
'Service Endpoint URL';

comment on column OPENDATA_SET.PICTUREFILE_ID is
'Picture';

comment on column OPENDATA_SET.TAGS is
'Tags';

comment on column OPENDATA_SET.OPENDATA_SET_STATUS_ID is
'Opendata Set Status';

-- ============================================================
--   Table : OPENDATA_SET_STATUS                                        
-- ============================================================
create table OPENDATA_SET_STATUS
(
    OPENDATA_SET_STATUS_ID	 VARCHAR(100)	not null,
    LABEL       	 VARCHAR(100)	,
    constraint PK_OPENDATA_SET_STATUS primary key (OPENDATA_SET_STATUS_ID)
);

comment on column OPENDATA_SET_STATUS.OPENDATA_SET_STATUS_ID is
'Id';

comment on column OPENDATA_SET_STATUS.LABEL is
'Status Label';

-- ============================================================
--   Table : PERSON                                        
-- ============================================================
create table PERSON
(
    PERSON_ID   	 NUMERIC     	not null,
    FIRST_NAME  	 VARCHAR(100)	,
    LAST_NAME   	 VARCHAR(100)	,
    EMAIL       	 VARCHAR(150)	,
    PICTUREFILE_ID	 NUMERIC     	,
    TAGS        	 TEXT        	,
    DATE_HIRED  	 DATE        	,
    GROUP_ID    	 NUMERIC     	,
    constraint PK_PERSON primary key (PERSON_ID)
);

comment on column PERSON.PERSON_ID is
'Id';

comment on column PERSON.FIRST_NAME is
'First Name';

comment on column PERSON.LAST_NAME is
'Last Name';

comment on column PERSON.EMAIL is
'E-mail';

comment on column PERSON.PICTUREFILE_ID is
'Picture';

comment on column PERSON.TAGS is
'Tags';

comment on column PERSON.DATE_HIRED is
'Date hired';

comment on column PERSON.GROUP_ID is
'Group';

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
--   Table : SUPPLIER                                        
-- ============================================================
create table SUPPLIER
(
    SIREN       	 VARCHAR(100)	not null,
    STATUT_DIFFUSION	 VARCHAR(100)	,
    DATE_CREATION	 DATE        	,
    SEXE        	 VARCHAR(100)	,
    PRENOM_USUEL	 VARCHAR(100)	,
    TRANCHE_EFFECTIFS	 VARCHAR(100)	,
    DATE_DERNIER_TRAITEMENT	 TIMESTAMP   	,
    NOMBRE_PERIODES	 NUMERIC     	,
    CATEGORIE_ENTREPRISE	 VARCHAR(100)	,
    DATE_DEBUT  	 DATE        	,
    ETAT_ADMINISTRATIF	 VARCHAR(100)	,
    NOM         	 VARCHAR(100)	,
    NOM_USAGE   	 VARCHAR(100)	,
    DENOMINATION	 VARCHAR(100)	,
    CATEGORIE_JURIDIQUE	 VARCHAR(100)	,
    ACTIVITE_PRINCIPALE	 VARCHAR(100)	,
    NOMENCLATURE_ACTIVITE	 VARCHAR(100)	,
    NIC_SIEGE   	 NUMERIC     	,
    CARACTERE_EMPLOYEUR	 bool        	,
    constraint PK_SUPPLIER primary key (SIREN)
);

comment on column SUPPLIER.SIREN is
'Siren';

comment on column SUPPLIER.STATUT_DIFFUSION is
'Broadcast status';

comment on column SUPPLIER.DATE_CREATION is
'Creation date';

comment on column SUPPLIER.SEXE is
'Sex';

comment on column SUPPLIER.PRENOM_USUEL is
'Firstname';

comment on column SUPPLIER.TRANCHE_EFFECTIFS is
'Workforce range';

comment on column SUPPLIER.DATE_DERNIER_TRAITEMENT is
'Last treatment';

comment on column SUPPLIER.NOMBRE_PERIODES is
'Number of periods';

comment on column SUPPLIER.CATEGORIE_ENTREPRISE is
'Business category';

comment on column SUPPLIER.DATE_DEBUT is
'Start date';

comment on column SUPPLIER.ETAT_ADMINISTRATIF is
'Administrative State';

comment on column SUPPLIER.NOM is
'Name';

comment on column SUPPLIER.NOM_USAGE is
'Use name';

comment on column SUPPLIER.DENOMINATION is
'Denomination';

comment on column SUPPLIER.CATEGORIE_JURIDIQUE is
'Legal category';

comment on column SUPPLIER.ACTIVITE_PRINCIPALE is
'Core business';

comment on column SUPPLIER.NOMENCLATURE_ACTIVITE is
'Business nomenclature';

comment on column SUPPLIER.NIC_SIEGE is
'Nic Siege';

comment on column SUPPLIER.CARACTERE_EMPLOYEUR is
'Employer';

-- ============================================================
--   Table : TAG                                        
-- ============================================================
create table TAG
(
    TAG_ID      	 NUMERIC     	not null,
    LABEL       	 VARCHAR(100)	,
    constraint PK_TAG primary key (TAG_ID)
);

comment on column TAG.TAG_ID is
'Id';

comment on column TAG.LABEL is
'Label';

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
    DATE_CLOSED 	 DATE        	,
    TICKET_STATUS_ID	 VARCHAR(100)	,
    EQUIPMENT_ID	 NUMERIC     	,
    constraint PK_TICKET primary key (TICKET_ID)
);

comment on column TICKET.TICKET_ID is
'Id';

comment on column TICKET.CODE is
'Number';

comment on column TICKET.TITLE is
'Title';

comment on column TICKET.DESCRIPTION is
'Description';

comment on column TICKET.DATE_CREATED is
'Creation Date';

comment on column TICKET.DATE_CLOSED is
'Closing Date';

comment on column TICKET.TICKET_STATUS_ID is
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
    WO_ID       	 NUMERIC     	not null,
    CODE        	 VARCHAR(100)	,
    TICKET_CODE 	 VARCHAR(100)	,
    NAME        	 VARCHAR(100)	,
    DESCRIPTION 	 VARCHAR(350)	,
    DATE_CREATED	 DATE        	,
    DATE_CLOSED 	 DATE        	,
    DUE_DATE    	 DATE        	,
    TICKET_ID   	 NUMERIC     	,
    WORK_ORDER_STATUS_ID	 VARCHAR(100)	,
    constraint PK_WORK_ORDER primary key (WO_ID)
);

comment on column WORK_ORDER.WO_ID is
'Id';

comment on column WORK_ORDER.CODE is
'Code';

comment on column WORK_ORDER.TICKET_CODE is
'Number';

comment on column WORK_ORDER.NAME is
'Name';

comment on column WORK_ORDER.DESCRIPTION is
'Description';

comment on column WORK_ORDER.DATE_CREATED is
'Creation Date';

comment on column WORK_ORDER.DATE_CLOSED is
'Closing Date';

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
	references BASE (BASE_ID);

create index BASE_EQUIPMENT_BASE_FK on EQUIPMENT (BASE_ID asc);

alter table BASE
	add constraint FK_BASE_GEOSECTOR_GEOSECTOR foreign key (GEOSECTOR_ID)
	references GEOSECTOR (GEOSECTOR_ID);

create index BASE_GEOSECTOR_GEOSECTOR_FK on BASE (GEOSECTOR_ID asc);

alter table PICTURE
	add constraint FK_BASE_PICTURE_BASE foreign key (BASE_ID)
	references BASE (BASE_ID);

create index BASE_PICTURE_BASE_FK on PICTURE (BASE_ID asc);

alter table EQUIPMENT
	add constraint FK_BUSINESS_EQUIPMENT_BUSINESS foreign key (BUSINESS_ID)
	references BUSINESS (BUSINESS_ID);

create index BUSINESS_EQUIPMENT_BUSINESS_FK on EQUIPMENT (BUSINESS_ID asc);

alter table MISSION
	add constraint FK_BUSINESS_MISSION_BUSINESS foreign key (BUSINESS_ID)
	references BUSINESS (BUSINESS_ID);

create index BUSINESS_MISSION_BUSINESS_FK on MISSION (BUSINESS_ID asc);

alter table EQUIPMENT_FEATURE
	add constraint FK_EQUIPMENT_EQUIPMENT_FEATURE_EQUIPMENT foreign key (EQUIPMENT_ID)
	references EQUIPMENT (EQUIPMENT_ID);

create index EQUIPMENT_EQUIPMENT_FEATURE_EQUIPMENT_FK on EQUIPMENT_FEATURE (EQUIPMENT_ID asc);

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

alter table EQUIPMENT_TYPE
	add constraint FK_EQUIPMENT_TYPE_EQUIPMENT_CATEGORY_EQUIPMENT_CATEGORY foreign key (EQUIPMENT_CATEGORY_ID)
	references EQUIPMENT_CATEGORY (EQUIPMENT_CATEGORY_ID);

create index EQUIPMENT_TYPE_EQUIPMENT_CATEGORY_EQUIPMENT_CATEGORY_FK on EQUIPMENT_TYPE (EQUIPMENT_CATEGORY_ID asc);

alter table MISSION
	add constraint FK_MISSION_BASE_BASE foreign key (BASE_ID)
	references BASE (BASE_ID);

create index MISSION_BASE_BASE_FK on MISSION (BASE_ID asc);

alter table OPENDATA_SET
	add constraint FK_OPENDATA_SET_OPENDATA_SET_STATUS_OPENDATA_SET_STATUS foreign key (OPENDATA_SET_STATUS_ID)
	references OPENDATA_SET_STATUS (OPENDATA_SET_STATUS_ID);

create index OPENDATA_SET_OPENDATA_SET_STATUS_OPENDATA_SET_STATUS_FK on OPENDATA_SET (OPENDATA_SET_STATUS_ID asc);

alter table PERSON
	add constraint FK_PERSON_GROUPS_GROUPS foreign key (GROUP_ID)
	references GROUPS (GROUP_ID);

create index PERSON_GROUPS_GROUPS_FK on PERSON (GROUP_ID asc);

alter table MISSION
	add constraint FK_PERSON_MISSION_PERSON foreign key (PERSON_ID)
	references PERSON (PERSON_ID);

create index PERSON_MISSION_PERSON_FK on MISSION (PERSON_ID asc);

alter table TICKET
	add constraint FK_TICKET_TICKET_STATUS_TICKET_STATUS foreign key (TICKET_STATUS_ID)
	references TICKET_STATUS (TICKET_STATUS_ID);

create index TICKET_TICKET_STATUS_TICKET_STATUS_FK on TICKET (TICKET_STATUS_ID asc);

alter table WORK_ORDER
	add constraint FK_TICKET_WORK_ORDER_TICKET foreign key (TICKET_ID)
	references TICKET (TICKET_ID);

create index TICKET_WORK_ORDER_TICKET_FK on WORK_ORDER (TICKET_ID asc);

alter table WORK_ORDER
	add constraint FK_WORK_ORDER_WORK_ORDER_STATUS_WORK_ORDER_STATUS foreign key (WORK_ORDER_STATUS_ID)
	references WORK_ORDER_STATUS (WORK_ORDER_STATUS_ID);

create index WORK_ORDER_WORK_ORDER_STATUS_WORK_ORDER_STATUS_FK on WORK_ORDER (WORK_ORDER_STATUS_ID asc);


