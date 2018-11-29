package org.occourts.escheatment.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.occourts.escheatment.dao.PublicationDAO;
import org.occourts.escheatment.model.Publication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PdfBoxGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfBoxGenerator.class);

	@Autowired
	private PublicationDAO publicationDAOImpl;

	public void generatePublication(String headerText) {
		PDPageContentStream contentStream = null;
		try {
			PDDocument document = new PDDocument();
			PDPage page = new PDPage();
			document.addPage(page);
			contentStream = new PDPageContentStream(document, page);
			contentStream.beginText();

			contentStream.setLeading(20f);
			contentStream.newLineAtOffset(50, 780);
			contentStream.setFont(PDType1Font.COURIER_BOLD, 20);
			contentStream.showText(headerText);
			contentStream.newLine();

			contentStream.setLeading(12.1f);
			contentStream.setFont(PDType1Font.COURIER, 12);
			contentStream.showText("The quick brown fox jump over the lazy dog");
			contentStream.newLine();
			contentStream.showText("Near the riverbank");
			contentStream.setLeading(6f);
			contentStream.newLine();
			contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 5);
			contentStream.showText("A test for italic");
			contentStream.newLine();

			contentStream.endText();
			contentStream.close();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			document.save(bos);
			testSavePublication(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (contentStream != null) {
				try {
					contentStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void testSavePublication(final byte[] pdf) {
		Publication publication = new Publication();
		publication.setOutputPdf(pdf);
		publication.setBatchId(BigDecimal.ONE);
		publication.setCreateUserId("ESCHEATMENT");
		publication.setFinalized("Y");
		publication.setPublicationDt(Calendar.getInstance().getTime());
		publication.setUpdateUserId("ESCHEATMENT");
		publication.setOutputPdf(pdf);
		long newId = publicationDAOImpl.addPublication(publication);

		// Test retrieve
		Publication newPublication = publicationDAOImpl.findPublication(newId);
		OutputStream os = null;
		try {
			os = new FileOutputStream("C:/_Workspace/eclipse_workspace/projects/theNewPdf123.pdf");
			InputStream io = new ByteArrayInputStream(newPublication.getOutputPdf());
			IOUtils.copy(io, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
