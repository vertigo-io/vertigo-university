-- ==========================================================================
--   Copyright � Klee 2016. All Rights Reserved.
--   Nom du script		:	init_data.sql
--   Date de création	:	04/08/2009
--   Action				:	Script d'initialisation des données
-- ==========================================================================

SET AUTOCOMMIT OFF;

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
INSERT INTO ROLE ( ROL_CODE, LIBELLE ) VALUES ( 'R_REF_PAY_CONS', 'Consultation Pays'); 
INSERT INTO ROLE ( ROL_CODE, LIBELLE ) VALUES ( 'R_REF_PAY_CREA', 'Creation Pays'); 
INSERT INTO ROLE ( ROL_CODE, LIBELLE ) VALUES ( 'R_REF_PAY_MODIF', 'Modification Pays'); 
INSERT INTO ROLE ( ROL_CODE, LIBELLE ) VALUES ( 'R_REF_PRF_CONS', 'Consultation Profils'); 
INSERT INTO ROLE ( ROL_CODE, LIBELLE ) VALUES ( 'R_REF_PRF_CREA', 'Creation Profils'); 
INSERT INTO ROLE ( ROL_CODE, LIBELLE ) VALUES ( 'R_REF_PRF_MODIF', 'Modification Profils'); 
INSERT INTO ROLE ( ROL_CODE, LIBELLE ) VALUES ( 'R_REF_UTI_CONS', 'Consultation Utilisateurs'); 
INSERT INTO ROLE ( ROL_CODE, LIBELLE ) VALUES ( 'R_REF_UTI_CREA', 'Creation Utilisateurs'); 
INSERT INTO ROLE ( ROL_CODE, LIBELLE ) VALUES ( 'R_REF_UTI_MODIF', 'Modification Utilisateurs'); 

/*==============================================================*/
/* Table: UTILISATEUR                                                  */
/*==============================================================*/

INSERT INTO UTILISATEUR ( 
	UTI_ID, NOM, PRENOM,
	MAIL, TELEPHONE, FAX, SI_ACTIF, DATE_CREATION,
	DATE_DERNIERE_MODIF, AUTEUR_DERNIERE_MODIF ) 
VALUES ( 
	1, 'Administrateur', '--', 'npiedeloup@kleegroup.com', '0102030405', '0102030405', 1, sysdate, NULL, 'SYSTEM');
	
INSERT INTO UTILISATEUR ( 
	UTI_ID, NOM, PRENOM,
	MAIL, TELEPHONE, FAX, SI_ACTIF, DATE_CREATION,
	DATE_DERNIERE_MODIF, AUTEUR_DERNIERE_MODIF ) 
VALUES ( 
	2, 'Demo', 'Test', 'demo@yopmail.com', null, null, 1, sysdate, NULL, 'SYSTEM');

INSERT INTO LOGIN (LOG_ID, LOGIN, PASSWORD, UTI_ID) 
VALUES (1,'admin', 'zfy8fVkqX3jlJ1bcUVDb8qKBPHvLJom1zhymv2WAEvf10diXSno=', 1);

INSERT INTO LOGIN (LOG_ID, LOGIN, PASSWORD, UTI_ID) 
VALUES (2,'demo', 'xGpISBnNcvxztvov8ci9lhkW630SCiHneuniAn5Ub2e4vtqi4FA=', 2);



/*==============================================================*/
/* Table: UTI_ROL                                                  */
/*==============================================================*/
INSERT INTO UTI_ROL (UTI_ID, ROL_CODE) VALUES (1, 'R_REF_PAY_CONS');
INSERT INTO UTI_ROL (UTI_ID, ROL_CODE) VALUES (1, 'R_REF_PAY_CREA');
INSERT INTO UTI_ROL (UTI_ID, ROL_CODE) VALUES (1, 'R_REF_PAY_MODIF');
INSERT INTO UTI_ROL (UTI_ID, ROL_CODE) VALUES (1, 'R_REF_PRF_CONS');
INSERT INTO UTI_ROL (UTI_ID, ROL_CODE) VALUES (1, 'R_REF_PRF_CREA');
INSERT INTO UTI_ROL (UTI_ID, ROL_CODE) VALUES (1, 'R_REF_PRF_MODIF');
INSERT INTO UTI_ROL (UTI_ID, ROL_CODE) VALUES (1, 'R_REF_UTI_CONS');
INSERT INTO UTI_ROL (UTI_ID, ROL_CODE) VALUES (1, 'R_REF_UTI_CREA');
INSERT INTO UTI_ROL (UTI_ID, ROL_CODE) VALUES (1, 'R_REF_UTI_MODIF');
	
	
COMMIT;


