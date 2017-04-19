package io.vertigo.demo.domain;

import java.util.Arrays;
import java.util.Iterator;
import io.vertigo.dynamo.domain.metamodel.DtFieldName;

/**
 * Attention cette classe est générée automatiquement !
 */
public final class DtDefinitions implements Iterable<Class<?>> {
	
	/**
	 * Enumération des DtDefinitions.
	 */
	public enum Definitions {
			/** Objet de données ActifChoice. */
			ActifChoice(io.vertigo.demo.domain.referentiel.ActifChoice.class),
			/** Objet de données AdresseSelection. */
			AdresseSelection(io.vertigo.demo.domain.client.AdresseSelection.class),
			/** Objet de données Client. */
			Client(io.vertigo.demo.domain.commande.Client.class),
			/** Objet de données ClientCritere. */
			ClientCritere(io.vertigo.demo.domain.client.ClientCritere.class),
			/** Objet de données CodePostal. */
			CodePostal(io.vertigo.demo.domain.referentiel.CodePostal.class),
			/** Objet de données Commande. */
			Commande(io.vertigo.demo.domain.commande.Commande.class),
			/** Objet de données Commune. */
			Commune(io.vertigo.demo.domain.tutorial.Commune.class),
			/** Objet de données Departement. */
			Departement(io.vertigo.demo.domain.referentiel.Departement.class),
			/** Objet de données Famille. */
			Famille(io.vertigo.demo.domain.referentiel.Famille.class),
			/** Objet de données KxFileInfo. */
			KxFileInfo(io.vertigo.demo.domain.KxFileInfo.class),
			/** Objet de données LigneCommande. */
			LigneCommande(io.vertigo.demo.domain.commande.LigneCommande.class),
			/** Objet de données Login. */
			Login(io.vertigo.demo.domain.administration.utilisateur.Login.class),
			/** Objet de données OuiNonChoice. */
			OuiNonChoice(io.vertigo.demo.domain.referentiel.OuiNonChoice.class),
			/** Objet de données Produit. */
			Produit(io.vertigo.demo.domain.produit.Produit.class),
			/** Objet de données ProduitCritere. */
			ProduitCritere(io.vertigo.demo.domain.produit.ProduitCritere.class),
			/** Objet de données ReferentielChoice. */
			ReferentielChoice(io.vertigo.demo.domain.referentiel.ReferentielChoice.class),
			/** Objet de données ReferentielCritere. */
			ReferentielCritere(io.vertigo.demo.domain.referentiel.ReferentielCritere.class),
			/** Objet de données Region. */
			Region(io.vertigo.demo.domain.referentiel.Region.class),
			/** Objet de données Role. */
			Role(io.vertigo.demo.domain.administration.utilisateur.Role.class),
			/** Objet de données RoleInput. */
			RoleInput(io.vertigo.demo.domain.administration.utilisateur.RoleInput.class),
			/** Objet de données TutoObject. */
			TutoObject(io.vertigo.demo.domain.tutorial.TutoObject.class),
			/** Objet de données TutoObjectEtat. */
			TutoObjectEtat(io.vertigo.demo.domain.tutorial.TutoObjectEtat.class),
			/** Objet de données TutoObjectType. */
			TutoObjectType(io.vertigo.demo.domain.tutorial.TutoObjectType.class),
			/** Objet de données Utilisateur. */
			Utilisateur(io.vertigo.demo.domain.administration.utilisateur.Utilisateur.class),
			/** Objet de données UtilisateurCritere. */
			UtilisateurCritere(io.vertigo.demo.domain.administration.utilisateur.UtilisateurCritere.class),
			/** Objet de données UtilisateurLogin. */
			UtilisateurLogin(io.vertigo.demo.domain.administration.utilisateur.UtilisateurLogin.class),
			/** Objet de données Ville. */
			Ville(io.vertigo.demo.domain.referentiel.Ville.class),
		;
		
		private final Class<?> clazz;
		private Definitions(final Class<?> clazz) {
			this.clazz = clazz;
		}
		
		/** 
		  * Classe associée.
		  * @return Class d'implémentation de l'objet 
		  */
		public Class<?> getDtClass() {
			return clazz;
		}
    }

	/**
	 * Enumération des champs de ActifChoice.
	 */
	public enum ActifChoiceFields implements DtFieldName {
		/** Propriété 'Valeur'. */
		KEY,
		/** Propriété 'Libell�'. */
		LIBELLE,
	}

	/**
	 * Enumération des champs de AdresseSelection.
	 */
	public enum AdresseSelectionFields implements DtFieldName {
		/** Propriété 'Region'. */
		REG_ID,
		/** Propriété 'D�partement'. */
		DEP_ID,
		/** Propriété 'Code postal'. */
		CPO_ID,
	}

	/**
	 * Enumération des champs de Client.
	 */
	public enum ClientFields implements DtFieldName {
		/** Propriété 'CLI ID'. */
		CLI_ID,
		/** Propriété 'NOM'. */
		NOM,
		/** Propriété 'PRENOM'. */
		PRENOM,
		/** Propriété 'ADDRESS'. */
		ADDRESS,
		/** Propriété 'CodePostal'. */
		CPO_ID,
	}

	/**
	 * Enumération des champs de ClientCritere.
	 */
	public enum ClientCritereFields implements DtFieldName {
		/** Propriété 'Nom'. */
		NOM,
	}

	/**
	 * Enumération des champs de CodePostal.
	 */
	public enum CodePostalFields implements DtFieldName {
		/** Propriété 'CPO_ID'. */
		CPO_ID,
		/** Propriété 'CODE POSTAL'. */
		CODE_POSTAL,
		/** Propriété 'Ville'. */
		VIL_ID,
	}

	/**
	 * Enumération des champs de Commande.
	 */
	public enum CommandeFields implements DtFieldName {
		/** Propriété 'COM ID'. */
		COM_ID,
		/** Propriété 'CODE'. */
		CODE,
		/** Propriété 'DATE CREATION'. */
		DATE_CREATION,
		/** Propriété 'DATE VALIDATION'. */
		DATE_VALIDATION,
		/** Propriété 'MONTANT TOTAL'. */
		MONTANT_TOTAL,
		/** Propriété 'Client'. */
		CLI_ID,
	}

	/**
	 * Enumération des champs de Commune.
	 */
	public enum CommuneFields implements DtFieldName {
		/** Propriété 'ID INSEE'. */
		ID_INSEE,
		/** Propriété 'Code postal'. */
		CODE_POSTAL,
		/** Propriété 'Commune'. */
		COMMUNE,
		/** Propriété 'D�partement'. */
		DEPARTEMENT,
	}

	/**
	 * Enumération des champs de Departement.
	 */
	public enum DepartementFields implements DtFieldName {
		/** Propriété 'DEP_ID'. */
		DEP_ID,
		/** Propriété 'LIBELLE'. */
		LIBELLE,
		/** Propriété 'Region'. */
		REG_ID,
	}

	/**
	 * Enumération des champs de Famille.
	 */
	public enum FamilleFields implements DtFieldName {
		/** Propriété 'FAM_ID'. */
		FAM_ID,
		/** Propriété 'LIBELLE'. */
		LIBELLE,
	}

	/**
	 * Enumération des champs de KxFileInfo.
	 */
	public enum KxFileInfoFields implements DtFieldName {
		/** Propriété 'Identifiant'. */
		FIL_ID,
		/** Propriété 'Nom'. */
		FILE_NAME,
		/** Propriété 'Type mime'. */
		MIME_TYPE,
		/** Propriété 'Taille'. */
		LENGTH,
		/** Propriété 'Date de derni�re modification'. */
		LAST_MODIFIED,
		/** Propriété 'data'. */
		FILE_DATA,
	}

	/**
	 * Enumération des champs de LigneCommande.
	 */
	public enum LigneCommandeFields implements DtFieldName {
		/** Propriété 'LCO ID'. */
		LCO_ID,
		/** Propriété 'QUANTITE'. */
		QUANTITE,
		/** Propriété 'Commande'. */
		COM_ID,
		/** Propriété 'Produit'. */
		PRD_ID,
	}

	/**
	 * Enumération des champs de Login.
	 */
	public enum LoginFields implements DtFieldName {
		/** Propriété 'LOG ID'. */
		LOG_ID,
		/** Propriété 'Login'. */
		LOGIN,
		/** Propriété 'Mot de passe'. */
		PASSWORD,
		/** Propriété 'Utilisateur'. */
		UTI_ID,
	}

	/**
	 * Enumération des champs de OuiNonChoice.
	 */
	public enum OuiNonChoiceFields implements DtFieldName {
		/** Propriété 'Valeur'. */
		KEY,
		/** Propriété 'Libell�'. */
		LIBELLE,
	}

	/**
	 * Enumération des champs de Produit.
	 */
	public enum ProduitFields implements DtFieldName {
		/** Propriété 'identifiant'. */
		PRD_ID,
		/** Propriété 'Code produit'. */
		CODE,
		/** Propriété 'libellé'. */
		LIBELLE,
		/** Propriété 'description'. */
		DESCRIPTION,
		/** Propriété 'prix unitaire'. */
		PRIX,
		/** Propriété 'en stock'. */
		SI_STOCK,
		/** Propriété 'poids unitaire'. */
		POIDS,
		/** Propriété 'commentaire'. */
		COMMENTAIRE,
		/** Propriété 'Famille'. */
		FAM_ID,
	}

	/**
	 * Enumération des champs de ProduitCritere.
	 */
	public enum ProduitCritereFields implements DtFieldName {
		/** Propriété 'Code'. */
		CODE,
		/** Propriété 'Libelle'. */
		LIBELLE,
		/** Propriété 'Prix min'. */
		PRIX_MIN,
		/** Propriété 'Prix max'. */
		PRIX_MAX,
		/** Propriété 'Poids min'. */
		POIDS_MIN,
		/** Propriété 'Poids max'. */
		POIDS_MAX,
		/** Propriété 'Description'. */
		DESCRIPTION,
		/** Propriété 'Commentaire'. */
		COMMENTAIRE,
		/** Propriété 'En stock'. */
		SI_STOCK,
		/** Propriété 'Famille'. */
		FAM_ID,
	}

	/**
	 * Enumération des champs de ReferentielChoice.
	 */
	public enum ReferentielChoiceFields implements DtFieldName {
		/** Propriété 'R�f�rentiel'. */
		REFERENTIEL_NAME,
		/** Propriété 'R�f�rentiel'. */
		LIBELLE,
	}

	/**
	 * Enumération des champs de ReferentielCritere.
	 */
	public enum ReferentielCritereFields implements DtFieldName {
		/** Propriété 'R�f�rentiel'. */
		REFERENTIEL_NAME,
	}

	/**
	 * Enumération des champs de Region.
	 */
	public enum RegionFields implements DtFieldName {
		/** Propriété 'REG_ID'. */
		REG_ID,
		/** Propriété 'LIBELLE'. */
		LIBELLE,
	}

	/**
	 * Enumération des champs de Role.
	 */
	public enum RoleFields implements DtFieldName {
		/** Propriété 'ROL_CODE'. */
		ROL_CODE,
		/** Propriété 'Libellé'. */
		LIBELLE,
	}

	/**
	 * Enumération des champs de RoleInput.
	 */
	public enum RoleInputFields implements DtFieldName {
		/** Propriété 'Role'. */
		ROL_CODE,
	}

	/**
	 * Enumération des champs de TutoObject.
	 */
	public enum TutoObjectFields implements DtFieldName {
		/** Propriété 'ID Objet'. */
		OBJ_ID,
		/** Propriété 'Code produit'. */
		CODE,
		/** Propriété 'Libell�'. */
		LIBELLE,
		/** Propriété 'Description'. */
		DESCRIPTION,
		/** Propriété 'Prix unitaire'. */
		PRIX,
		/** Propriété 'En stock'. */
		SI_STOCK,
		/** Propriété 'Poids unitaire'. */
		POIDS,
		/** Propriété 'Date de cr�ation'. */
		DATE_CREATION,
		/** Propriété 'Date de modification'. */
		DATE_MODIFICATION,
		/** Propriété 'type'. */
		TYP_ID,
		/** Propriété 'etat'. */
		ETA_ID,
	}

	/**
	 * Enumération des champs de TutoObjectEtat.
	 */
	public enum TutoObjectEtatFields implements DtFieldName {
		/** Propriété 'ID etat'. */
		ETA_ID,
		/** Propriété 'Code Etat'. */
		ETAT,
		/** Propriété 'Libell�'. */
		LIBELLE,
	}

	/**
	 * Enumération des champs de TutoObjectType.
	 */
	public enum TutoObjectTypeFields implements DtFieldName {
		/** Propriété 'ID Type'. */
		TYP_ID,
		/** Propriété 'Code type'. */
		CODE,
		/** Propriété 'Libell�'. */
		LIBELLE,
	}

	/**
	 * Enumération des champs de Utilisateur.
	 */
	public enum UtilisateurFields implements DtFieldName {
		/** Propriété 'UTI_ID'. */
		UTI_ID,
		/** Propriété 'Nom'. */
		NOM,
		/** Propriété 'Prénom'. */
		PRENOM,
		/** Propriété 'Courriel'. */
		MAIL,
		/** Propriété 'Téléphone'. */
		TELEPHONE,
		/** Propriété 'Fax'. */
		FAX,
		/** Propriété 'Si actif'. */
		SI_ACTIF,
		/** Propriété 'Date de création'. */
		DATE_CREATION,
		/** Propriété 'Date de dernière modification'. */
		DATE_DERNIERE_MODIF,
		/** Propriété 'Auteur de dernière modification'. */
		AUTEUR_DERNIERE_MODIF,
		/** Propriété 'Prénom Nom'. */
		PRENOM_NOM,
	}

	/**
	 * Enumération des champs de UtilisateurCritere.
	 */
	public enum UtilisateurCritereFields implements DtFieldName {
		/** Propriété 'Nom'. */
		NOM,
		/** Propriété 'Indentifiant'. */
		LOGIN,
		/** Propriété 'Rôle'. */
		ROLE,
		/** Propriété 'Seulement les utilisateurs actifs'. */
		IS_ACTIF,
	}

	/**
	 * Enumération des champs de UtilisateurLogin.
	 */
	public enum UtilisateurLoginFields implements DtFieldName {
		/** Propriété 'Identifiant utilisateur'. */
		LOGIN,
		/** Propriété 'Mot de passe'. */
		PASSWORD,
		/** Propriété 'Nouveau mot de passe'. */
		NEW_PASSWORD,
		/** Propriété 'Resaisir le mot de passe'. */
		NEW_PASSWORD_CHECK,
	}

	/**
	 * Enumération des champs de Ville.
	 */
	public enum VilleFields implements DtFieldName {
		/** Propriété 'VIL_ID'. */
		VIL_ID,
		/** Propriété 'LIBELLE'. */
		LIBELLE,
		/** Propriété 'Departement'. */
		DEP_ID,
	}

	    
    /** {@inheritDoc} */
    @Override
    public Iterator<Class<?>> iterator() {
        return new Iterator<Class<?>>() {
            private Iterator<Definitions> it = Arrays.asList(Definitions.values()).iterator();

            /** {@inheritDoc} */
            @Override
            public boolean hasNext() {
				return it.hasNext();
            }

            /** {@inheritDoc} */
            @Override
            public Class<?> next() {
            	return it.next().getDtClass();
            }

            /** {@inheritDoc} */
            @Override
            public void remove() {
            	//unsupported
            }
        };
    }                      
}
