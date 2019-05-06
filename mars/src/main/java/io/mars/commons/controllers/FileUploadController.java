package io.mars.commons.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.commons.services.CommonsServices;
import io.vertigo.core.param.ParamValue;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.file.model.VFile;

@Controller
@RequestMapping("/commons/")
public class FileUploadController {

	@Inject
	private CommonsServices commonsServices;

	@PostMapping("/upload")
	public FileInfoURI uploadFile(@ParamValue("file") final VFile file) {
		return commonsServices.saveFileTmp(file);
	}

	@DeleteMapping("/upload")
	public FileInfoURI removeFile(@ParamValue("file") final FileInfoURI file) {
		commonsServices.deleteFileTmp(file);
		return file; //if no return, you must get the response. Prefer to return old uri.
	}
}
