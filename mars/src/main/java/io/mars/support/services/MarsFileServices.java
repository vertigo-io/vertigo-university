package io.mars.support.services;

import javax.inject.Inject;

import io.mars.fileinfo.FileInfoTmp;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.file.metamodel.FileInfoDefinition;
import io.vertigo.dynamo.file.model.FileInfo;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.lang.Assertion;

@Transactional
public class MarsFileServices implements Component {

	@Inject
	private StoreManager storeManager;

	public FileInfoURI saveFileTmp(final VFile file) {
		//apply security check
		final FileInfo fileInfo = storeManager.getFileStore().create(new FileInfoTmp(file));
		return fileInfo.getURI();
	}

	public VFile getFileTmp(final FileInfoURI fileTmpUri) {
		final FileInfoDefinition tmpFileInfoDefinition = FileInfoDefinition.findFileInfoDefinition(FileInfoTmp.class);
		Assertion.checkArgument(tmpFileInfoDefinition.equals(fileTmpUri.getDefinition()), "Can't access this file storage."); //not too much infos for security purpose
		return storeManager.getFileStore().read(fileTmpUri).getVFile();
	}

	public void deleteFileTmp(final FileInfoURI fileTmpUri) {
		final FileInfoDefinition tmpFileInfoDefinition = FileInfoDefinition.findFileInfoDefinition(FileInfoTmp.class);
		Assertion.checkArgument(tmpFileInfoDefinition.equals(fileTmpUri.getDefinition()), "Can't access this file storage."); //not too much infos for security purpose
		storeManager.getFileStore().delete(fileTmpUri);
	}

}
