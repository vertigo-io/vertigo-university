-- ============================================================
--   SGBD      		  :  PostgreSql                     
-- ============================================================




-- ============================================================
--   Sequences                                      
-- ============================================================
create sequence SEQ_CLIENT
	start with 1000 cache 20; 

create sequence SEQ_CODE_POSTAL
	start with 1000 cache 20; 

create sequence SEQ_COMMANDE
	start with 1000 cache 20; 

create sequence SEQ_DEPARTEMENT
	start with 1000 cache 20; 

create sequence SEQ_FAMILLE
	start with 1000 cache 20; 

create sequence SEQ_KX_FILE_INFO
	start with 1000 cache 20; 

create sequence SEQ_LIGNE_COMMANDE
	start with 1000 cache 20; 

create sequence SEQ_LOGIN
	start with 1000 cache 20; 

create sequence SEQ_PRODUIT
	start with 1000 cache 20; 

create sequence SEQ_REFERENTIEL_CHOICE
	start with 1000 cache 20; 

create sequence SEQ_REGION
	start with 1000 cache 20; 

create sequence SEQ_ROLE
	start with 1000 cache 20; 

create sequence SEQ_TUTO_OBJECT
	start with 1000 cache 20; 

create sequence SEQ_UTILISATEUR
	start with 1000 cache 20; 

create sequence SEQ_VILLE
	start with 1000 cache 20; 


-- ============================================================
--   Table : CLIENT                                        
-- ============================================================
create table CLIENT
(
    CLI_ID      	 BIGINT      	not null,
    NOM         	 VARCHAR(100)	not null,
    PRENOM      	 VARCHAR(100)	not null,
    ADDRESS     	 TEXT        	not null,
    CPO_ID      	 BIGINT      	not null,
    constraint PK_CLIENT primary key (CLI_ID)
);

comment on column CLIENT.CLI_ID is
'CLI ID';

comment on column CLIENT.NOM is
'NOM';

comment on column CLIENT.PRENOM is
'PRENOM';

comment on column CLIENT.ADDRESS is
'ADDRESS';

comment on column CLIENT.CPO_ID is
'CodePostal';

-- ============================================================
--   Table : CODE_POSTAL                                        
-- ============================================================
create table CODE_POSTAL
(
    CPO_ID      	 BIGINT      	not null,
    CODE_POSTAL 	 VARCHAR(5)  	not null,
    VIL_ID      	 BIGINT      	not null,
    constraint PK_CODE_POSTAL primary key (CPO_ID)
);

comment on column CODE_POSTAL.CPO_ID is
'CPO_ID';

comment on column CODE_POSTAL.CODE_POSTAL is
'CODE POSTAL';

comment on column CODE_POSTAL.VIL_ID is
'Ville';

-- ============================================================
--   Table : COMMANDE                                        
-- ============================================================
create table COMMANDE
(
    COM_ID      	 BIGINT      	not null,
    CODE        	 VARCHAR(30) 	not null,
    DATE_CREATION	 DATE        	not null,
    DATE_VALIDATION	 DATE        	,
    MONTANT_TOTAL	 NUMERIC(12,2)	,
    CLI_ID      	 BIGINT      	not null,
    constraint PK_COMMANDE primary key (COM_ID)
);

comment on column COMMANDE.COM_ID is
'COM ID';

comment on column COMMANDE.CODE is
'CODE';

comment on column COMMANDE.DATE_CREATION is
'DATE CREATION';

comment on column COMMANDE.DATE_VALIDATION is
'DATE VALIDATION';

comment on column COMMANDE.MONTANT_TOTAL is
'MONTANT TOTAL';

comment on column COMMANDE.CLI_ID is
'Client';

-- ============================================================
--   Table : DEPARTEMENT                                        
-- ============================================================
create table DEPARTEMENT
(
    DEP_ID      	 BIGINT      	not null,
    LIBELLE     	 VARCHAR(100)	not null,
    REG_ID      	 BIGINT      	not null,
    constraint PK_DEPARTEMENT primary key (DEP_ID)
);

comment on column DEPARTEMENT.DEP_ID is
'DEP_ID';

comment on column DEPARTEMENT.LIBELLE is
'LIBELLE';

comment on column DEPARTEMENT.REG_ID is
'Region';

-- ============================================================
--   Table : FAMILLE                                        
-- ============================================================
create table FAMILLE
(
    FAM_ID      	 BIGINT      	not null,
    LIBELLE     	 VARCHAR(100)	not null,
    constraint PK_FAMILLE primary key (FAM_ID)
);

comment on column FAMILLE.FAM_ID is
'FAM_ID';

comment on column FAMILLE.LIBELLE is
'LIBELLE';

-- ============================================================
--   Table : KX_FILE_INFO                                        
-- ============================================================
create table KX_FILE_INFO
(
    FIL_ID      	 BIGINT      	not null,
    FILE_NAME   	 VARCHAR(30) 	not null,
    MIME_TYPE   	 VARCHAR(30) 	not null,
    LENGTH      	 BIGINT      	not null,
    LAST_MODIFIED	 DATE        	not null,
    FILE_DATA   	 VARCHAR(30) 	,
    constraint PK_KX_FILE_INFO primary key (FIL_ID)
);

comment on column KX_FILE_INFO.FIL_ID is
'Identifiant';

comment on column KX_FILE_INFO.FILE_NAME is
'Nom';

comment on column KX_FILE_INFO.MIME_TYPE is
'Type mime';

comment on column KX_FILE_INFO.LENGTH is
'Taille';

comment on column KX_FILE_INFO.LAST_MODIFIED is
'Date de derni�re modification';

comment on column KX_FILE_INFO.FILE_DATA is
'data';

-- ============================================================
--   Table : LIGNE_COMMANDE                                        
-- ============================================================
create table LIGNE_COMMANDE
(
    LCO_ID      	 BIGINT      	not null,
    QUANTITE    	 BIGINT      	not null,
    COM_ID      	 BIGINT      	not null,
    PRD_ID      	 BIGINT      	not null,
    constraint PK_LIGNE_COMMANDE primary key (LCO_ID)
);

comment on column LIGNE_COMMANDE.LCO_ID is
'LCO ID';

comment on column LIGNE_COMMANDE.QUANTITE is
'QUANTITE';

comment on column LIGNE_COMMANDE.COM_ID is
'Commande';

comment on column LIGNE_COMMANDE.PRD_ID is
'Produit';

-- ============================================================
--   Table : LOGIN                                        
-- ============================================================
create table LOGIN
(
    LOG_ID      	 BIGINT      	not null,
    LOGIN       	 VARCHAR(30) 	not null,
    PASSWORD    	 VARCHAR(250)	not null,
    UTI_ID      	 BIGINT      	not null,
    constraint PK_LOGIN primary key (LOG_ID)
);

comment on column LOGIN.LOG_ID is
'LOG ID';

comment on column LOGIN.LOGIN is
'Login';

comment on column LOGIN.PASSWORD is
'Mot de passe';

comment on column LOGIN.UTI_ID is
'Utilisateur';

-- ============================================================
--   Table : PRODUIT                                        
-- ============================================================
create table PRODUIT
(
    PRD_ID      	 BIGINT      	not null,
    CODE        	 VARCHAR(30) 	not null,
    LIBELLE     	 VARCHAR(40) 	,
    DESCRIPTION 	 TEXT        	,
    PRIX        	 NUMERIC(12,2)	,
    SI_STOCK    	 BOOLEAN     	,
    POIDS       	 NUMERIC(4,1)	,
    COMMENTAIRE 	 TEXT        	,
    FAM_ID      	 BIGINT      	not null,
    constraint PK_PRODUIT primary key (PRD_ID)
);

comment on column PRODUIT.PRD_ID is
'identifiant';

comment on column PRODUIT.CODE is
'Code produit';

comment on column PRODUIT.LIBELLE is
'libellé';

comment on column PRODUIT.DESCRIPTION is
'description';

comment on column PRODUIT.PRIX is
'prix unitaire';

comment on column PRODUIT.SI_STOCK is
'en stock';

comment on column PRODUIT.POIDS is
'poids unitaire';

comment on column PRODUIT.COMMENTAIRE is
'commentaire';

comment on column PRODUIT.FAM_ID is
'Famille';

-- ============================================================
--   Table : REFERENTIEL_CHOICE                                        
-- ============================================================
create table REFERENTIEL_CHOICE
(
    REFERENTIEL_NAME	 VARCHAR(100)	not null,
    LIBELLE     	 VARCHAR(100)	not null,
    constraint PK_REFERENTIEL_CHOICE primary key (REFERENTIEL_NAME)
);

comment on column REFERENTIEL_CHOICE.REFERENTIEL_NAME is
'R�f�rentiel';

comment on column REFERENTIEL_CHOICE.LIBELLE is
'R�f�rentiel';

-- ============================================================
--   Table : REGION                                        
-- ============================================================
create table REGION
(
    REG_ID      	 BIGINT      	not null,
    LIBELLE     	 VARCHAR(100)	not null,
    constraint PK_REGION primary key (REG_ID)
);

comment on column REGION.REG_ID is
'REG_ID';

comment on column REGION.LIBELLE is
'LIBELLE';

-- ============================================================
--   Table : ROLE                                        
-- ============================================================
create table ROLE
(
    ROL_CODE    	 VARCHAR(30) 	not null,
    LIBELLE     	 VARCHAR(100)	not null,
    constraint PK_ROLE primary key (ROL_CODE)
);

comment on column ROLE.ROL_CODE is
'ROL_CODE';

comment on column ROLE.LIBELLE is
'Libellé';

-- ============================================================
--   Table : TUTO_OBJECT                                        
-- ============================================================
create table TUTO_OBJECT
(
    OBJ_ID      	 BIGINT      	not null,
    CODE        	 VARCHAR(30) 	not null,
    LIBELLE     	 VARCHAR(100)	not null,
    DESCRIPTION 	 TEXT        	,
    PRIX        	 NUMERIC(12,2)	,
    SI_STOCK    	 BOOLEAN     	not null,
    POIDS       	 NUMERIC(4,1)	not null,
    DATE_CREATION	 DATE        	not null,
    DATE_MODIFICATION	 DATE        	,
    TYP_ID      	 BIGINT      	,
    ETA_ID      	 BIGINT      	,
    constraint PK_TUTO_OBJECT primary key (OBJ_ID)
);

comment on column TUTO_OBJECT.OBJ_ID is
'ID Objet';

comment on column TUTO_OBJECT.CODE is
'Code produit';

comment on column TUTO_OBJECT.LIBELLE is
'Libell�';

comment on column TUTO_OBJECT.DESCRIPTION is
'Description';

comment on column TUTO_OBJECT.PRIX is
'Prix unitaire';

comment on column TUTO_OBJECT.SI_STOCK is
'En stock';

comment on column TUTO_OBJECT.POIDS is
'Poids unitaire';

comment on column TUTO_OBJECT.DATE_CREATION is
'Date de cr�ation';

comment on column TUTO_OBJECT.DATE_MODIFICATION is
'Date de modification';

comment on column TUTO_OBJECT.TYP_ID is
'type';

comment on column TUTO_OBJECT.ETA_ID is
'etat';

-- ============================================================
--   Table : UTILISATEUR                                        
-- ============================================================
create table UTILISATEUR
(
    UTI_ID      	 BIGINT      	not null,
    NOM         	 VARCHAR(100)	not null,
    PRENOM      	 VARCHAR(100)	not null,
    MAIL        	 VARCHAR(255)	not null,
    TELEPHONE   	 VARCHAR(20) 	,
    FAX         	 VARCHAR(20) 	,
    SI_ACTIF    	 BOOLEAN     	not null,
    DATE_CREATION	 DATE        	not null,
    DATE_DERNIERE_MODIF	 DATE        	,
    AUTEUR_DERNIERE_MODIF	 VARCHAR(250)	not null,
    constraint PK_UTILISATEUR primary key (UTI_ID)
);

comment on column UTILISATEUR.UTI_ID is
'UTI_ID';

comment on column UTILISATEUR.NOM is
'Nom';

comment on column UTILISATEUR.PRENOM is
'Prénom';

comment on column UTILISATEUR.MAIL is
'Courriel';

comment on column UTILISATEUR.TELEPHONE is
'Téléphone';

comment on column UTILISATEUR.FAX is
'Fax';

comment on column UTILISATEUR.SI_ACTIF is
'Si actif';

comment on column UTILISATEUR.DATE_CREATION is
'Date de création';

comment on column UTILISATEUR.DATE_DERNIERE_MODIF is
'Date de dernière modification';

comment on column UTILISATEUR.AUTEUR_DERNIERE_MODIF is
'Auteur de dernière modification';

-- ============================================================
--   Table : VILLE                                        
-- ============================================================
create table VILLE
(
    VIL_ID      	 BIGINT      	not null,
    LIBELLE     	 VARCHAR(100)	not null,
    DEP_ID      	 BIGINT      	not null,
    constraint PK_VILLE primary key (VIL_ID)
);

comment on column VILLE.VIL_ID is
'VIL_ID';

comment on column VILLE.LIBELLE is
'LIBELLE';

comment on column VILLE.DEP_ID is
'Departement';



alter table COMMANDE
	add constraint FK_CLI_COM_CLIENT foreign key (CLI_ID)
	references CLIENT (CLI_ID);

create index CLI_COM_CLIENT_FK on COMMANDE (CLI_ID asc);

alter table CLIENT
	add constraint FK_CLI_CPO_CODE_POSTAL foreign key (CPO_ID)
	references CODE_POSTAL (CPO_ID);

create index CLI_CPO_CODE_POSTAL_FK on CLIENT (CPO_ID asc);

alter table CODE_POSTAL
	add constraint FK_CPO_VIL_VILLE foreign key (VIL_ID)
	references VILLE (VIL_ID);

create index CPO_VIL_VILLE_FK on CODE_POSTAL (VIL_ID asc);

alter table VILLE
	add constraint FK_DEP_VIL_DEPARTEMENT foreign key (DEP_ID)
	references DEPARTEMENT (DEP_ID);

create index DEP_VIL_DEPARTEMENT_FK on VILLE (DEP_ID asc);

alter table LIGNE_COMMANDE
	add constraint FK_LCO_CMD_COMMANDE foreign key (COM_ID)
	references COMMANDE (COM_ID);

create index LCO_CMD_COMMANDE_FK on LIGNE_COMMANDE (COM_ID asc);

alter table LIGNE_COMMANDE
	add constraint FK_LCO_PRD_PRODUIT foreign key (PRD_ID)
	references PRODUIT (PRD_ID);

create index LCO_PRD_PRODUIT_FK on LIGNE_COMMANDE (PRD_ID asc);

alter table PRODUIT
	add constraint FK_PRD_FAM_FAMILLE foreign key (FAM_ID)
	references FAMILLE (FAM_ID);

create index PRD_FAM_FAMILLE_FK on PRODUIT (FAM_ID asc);

alter table DEPARTEMENT
	add constraint FK_REG_DEP_REGION foreign key (REG_ID)
	references REGION (REG_ID);

create index REG_DEP_REGION_FK on DEPARTEMENT (REG_ID asc);

alter table LOGIN
	add constraint FK_UTI_LOG_UTILISATEUR foreign key (UTI_ID)
	references UTILISATEUR (UTI_ID);

create index UTI_LOG_UTILISATEUR_FK on LOGIN (UTI_ID asc);


create table UTI_ROL
(
	UTI_ID      	 BIGINT      	 not null,
	ROL_CODE    	 VARCHAR(30) 	 not null,
	constraint PK_UTI_ROL primary key (UTI_ID, ROL_CODE),
	constraint FK_UTI_ROL_UTILISATEUR 
		foreign key(UTI_ID)
		references UTILISATEUR (UTI_ID),
	constraint FK_UTI_ROL_ROLE 
		foreign key(ROL_CODE)
		references ROLE (ROL_CODE)
);

create index UTI_ROL_UTILISATEUR_FK on UTI_ROL (UTI_ID asc);

create index UTI_ROL_ROLE_FK on UTI_ROL (ROL_CODE asc);

