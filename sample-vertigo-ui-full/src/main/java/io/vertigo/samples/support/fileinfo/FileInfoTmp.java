package io.vertigo.samples.support.fileinfo;

import io.vertigo.datastore.filestore.definitions.FileInfoDefinition;
import io.vertigo.datastore.filestore.model.VFile;
import io.vertigo.datastore.impl.filestore.model.AbstractFileInfo;

/**
 * Attention cette classe est générée automatiquement !
 * Objet représentant un fichier persistant FileInfoTmp
 */
public final class FileInfoTmp extends AbstractFileInfo {
	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur par défaut.
	 * @param vFile Données du fichier
	 */
	public FileInfoTmp(final VFile vFile) {
		super(FileInfoDefinition.findFileInfoDefinition(FileInfoTmp.class), vFile);
	}
}
