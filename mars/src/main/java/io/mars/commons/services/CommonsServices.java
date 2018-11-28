package io.mars.commons.services;

import javax.inject.Inject;

import io.mars.fileinfo.FileInfoTmp;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.file.model.FileInfo;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.store.StoreManager;

@Transactional
public class CommonsServices implements Component {

	@Inject
	private StoreManager storeManager;

	public FileInfoURI saveFileTmp(final VFile file) {
		//apply security check
		final FileInfo fileInfo = storeManager.getFileStore().create(new FileInfoTmp(file));
		return fileInfo.getURI();
	}

	public VFile getFileTmp(final FileInfoURI fileTmpUri) {
		//apply security check
		return storeManager.getFileStore().read(fileTmpUri).getVFile();
	}

}
