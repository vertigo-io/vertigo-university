package io.mars.commons.controllers;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.commons.services.CommonsServices;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.file.model.VFile;

@Controller
@RequestMapping("/commons/")
public class FileUploadController {

	@Inject
	private CommonsServices commonsServices;

	@PostMapping("/upload")
	public FileInfoURI uploadFile(@Named("file") final VFile file) {
		return commonsServices.saveFileTmp(file);
	}
}
