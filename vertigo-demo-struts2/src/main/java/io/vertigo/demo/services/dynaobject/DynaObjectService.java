package io.vertigo.demo.services.dynaobject;

import java.io.Serializable;

import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.Entity;

/**
 * Service g�n�rique pour la gestion de la persistance des DtObject.
 *
 * @author prahmoune
 * @version $Id: DynaObjectService.java,v 1.2 2014/02/07 16:48:27 npiedeloup Exp $
 *
 */
public interface DynaObjectService {
	//Todo mettre le type de la PK en param�tre

	/**
	 * Charge un DtObject par son id technique.
	 *
	 * @param dtDefinition dtDefinition du DtObject
	 * @param id id technique
	 * @return dtObject
	 */
	Entity load(final DtDefinition dtDefinition, final Serializable id);

	/**
	 * Retourne la liste des DtObjects.
	 * @param dtDefinition dtDefinition du DtObject
	 * @return liste des DtObjects.
	 */
	DtList<? extends Entity> getList(final DtDefinition dtDefinition);

	/**
	 * Sauvegarde un dtObject en base de donn�es.
	 *
	 * @param dtObject � persister en base de donn�es
	 */
	void save(final Entity dtObject);

	/**
	 * Supprime un dtObject en base de donn�es.
	 *
	 * @param dtDefinition dtDefinition du DtObject
	 * @param id Id du DtObject � supprimer en base
	 */
	void delete(final DtDefinition dtDefinition, final Serializable id);

}
