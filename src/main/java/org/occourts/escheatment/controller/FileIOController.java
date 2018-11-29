package org.occourts.escheatment.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.occourts.escheatment.dao.impl.DocumentDAOImpl;
import org.occourts.escheatment.model.Document;
import org.occourts.escheatment.util.CaseHistoryService;
import org.occourts.escheatment.util.CollageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller implementation for file downloading/uploading $Revision: 4531 $
 * $Author: flabriaga $ $Date: 2018-10-11 12:20:47 -0700 (Wed, 10 Oct 2018) $
 */

@Controller
public class FileIOController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileIOController.class);

	@Autowired
	DocumentDAOImpl documentdao;

	@Autowired
	private CaseHistoryService caseHistoryService;

	@GetMapping(path = "/get-document")
	private void getDocument(@RequestParam(value = "doc_id", required = true) String docId,
			@RequestParam(value = "dms", required = true) String dms, HttpServletResponse response) {
		LOGGER.debug("fetching document [{}] dms [{}]", docId, dms);

		InputStream is = CollageUtil.getInstance().getDocument(docId, dms);
		try {
			IOUtils.copy(is, response.getOutputStream());
		} catch (IOException e) {
			LOGGER.error("Exception occured", e);
		}
	}

	@GetMapping(path = "/get-document-by-document-id")
	private void getDocumentByDocumentId(@RequestParam(value = "documentId", required = true) String documentId,
			HttpServletResponse response) {
		// LOGGER.debug("fetching document [{}] dms [{}]", docId, dms);

		// get doc_id, and dms
		Document doc = documentdao.fetchDocumentInfo(Long.parseLong(documentId));

		InputStream is = CollageUtil.getInstance().getDocument(String.valueOf(doc.getDocId()), doc.getDmsCd());
		try {
			IOUtils.copy(is, response.getOutputStream());
		} catch (IOException e) {
			LOGGER.error("Exception occured", e);
		}
	}

	@RequestMapping(path = "/view-pdf", params = { "trustId", "formName" })
	public ResponseEntity<byte[]> viewpdf(@RequestParam(value = "trustId") long trustId,
			@RequestParam(value = "formName") String filename, Model model) throws IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));

		headers.add("content-disposition", "inline;filename=" + filename + ".pdf");

		File file = new File("c:/temp/" + trustId + "/" + filename + ".pdf");
		Path path = Paths.get(file.getAbsolutePath());
		byte[] fileContent = Files.readAllBytes(path);

		// headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fileContent, headers, HttpStatus.OK);
		return response;
	}
}
