package io.mars.support.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.support.services.MarsFileServices;
import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.vega.webservice.stereotype.QueryParam;

@Controller
@RequestMapping("/commons/")
public class FileUploadController {

	@Inject
	private MarsFileServices commonsServices;

	@PostMapping("/upload")
	public FileInfoURI uploadFile(@QueryParam("file") final VFile file) {
		return commonsServices.saveFileTmp(file);
	}

	@DeleteMapping("/upload")
	public FileInfoURI removeFile(@QueryParam("file") final FileInfoURI file) {
		commonsServices.deleteFileTmp(file);
		return file; //if no return, you must get the response. Prefer to return old uri.
	}
}
