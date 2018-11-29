package org.occourts.escheatment.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.occourts.escheatment.Constants;
import org.occourts.escheatment.model.FormData;
import org.occourts.escheatment.model.LawfulOwner;

import com.DocPath.hpc.v41.docgen.DocGen;
import com.DocPath.hpc.v41.docgen.DocGenJobCompletedEvent;
import com.DocPath.hpc.v41.docgen.DocGenListener;

public class PdfUtil implements DocGenListener {

	public String populateNoticeOfUnclaimedFunds(List<LawfulOwner> lawfulOwners, FormData formData) throws InvalidPasswordException, IOException {
		try {
			
			BigDecimal trustId = lawfulOwners.get(0).getTrustId();
			int counter = 0;			
			int numPages = 0;
			FileInputStream cert = null;
			PDFMergerUtility merger = new PDFMergerUtility(); //all notices merged						
			//get output path from properties file
			final ResourceBundle outputPath = ResourceBundle.getBundle("escheatment");
			String path = outputPath.getString("pdf.outputPath");
			String fullPath = path + trustId  + "/NoticeOfUnclaimedFundsFinal.pdf";
			new File(path + trustId).mkdir();			
			
			for (LawfulOwner owner : lawfulOwners) {
				
				PDFMergerUtility mergeSingleNotice = new PDFMergerUtility();
				counter = counter + 1;
				InputStream input = PdfUtil.class.getResourceAsStream("/pdf/templates/Notice_of_Unclaimed_Funds_Template.pdf");

				PDDocument doc = new PDDocument().load(input);
				PDDocumentCatalog docCatalog = doc.getDocumentCatalog();
				PDAcroForm acroForm = docCatalog.getAcroForm();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date today = new Date();
				PDField date = acroForm.getField("Date");	
				PDField nameLine1 = acroForm.getField("nameLine1");		
				PDField nameLine2 = acroForm.getField("nameLine2");
				PDField addressLine1 = acroForm.getField("addressLine1");
				PDField addressLine2 = acroForm.getField("addressLine2");
				PDField addressLine3 = acroForm.getField("addressLine3");
				PDField caseNumber = acroForm.getField("caseNumber");
				PDField caseNumber2 = acroForm.getField("caseNumber2");
				PDField caseTitle = acroForm.getField("caseTitle");
				PDField caseTitle2 = acroForm.getField("caseTitle2");
				PDField trustNumber = acroForm.getField("trustNumber");
				PDField trustNumber2 = acroForm.getField("trustNumber2");
				PDField trustType = acroForm.getField("trustType");
				PDField trustType2 = acroForm.getField("trustType2");
				PDField trustDate = acroForm.getField("trustDate");
				PDField trustAmount = acroForm.getField("trustAmount");
				PDField deputyClerk = acroForm.getField("deputyClerk");
				
				if (date != null) {
					date.setValue(dateFormat.format(today));
				}				
				if (nameLine1 != null) {
					nameLine1.setValue(owner.getNameLine1());
				}
				if (nameLine2 != null) {
					nameLine2.setValue(owner.getNameLine2());
				}	
				if (addressLine1 != null) {
					addressLine1.setValue(owner.getAddressLine1());
				}	
				if (addressLine2 != null) {
					addressLine2.setValue(owner.getAddressLine2());
				}	
				if (addressLine3 != null) {
					addressLine3.setValue(owner.getAddressLine3());
				}		
				if (caseNumber != null) {
					caseNumber.setValue(formData.getCaseNumber());
				}
				if (caseNumber2 != null) {
					caseNumber2.setValue(formData.getCaseNumber());
				}				
				if (caseTitle != null) {
					caseTitle.setValue(formData.getCaseTitle());
				}	
				if (caseTitle2 != null) {
					caseTitle2.setValue(formData.getCaseTitle());
				}					
				if (trustNumber != null) {
					trustNumber.setValue(formData.getTrustNumber());
				}	
				if (trustNumber2 != null) {
					trustNumber2.setValue(formData.getTrustNumber());
				}					
				if (trustType != null) {
					trustType.setValue(formData.getTrustType());
				}	
				if (trustType2 != null) {
					trustType2.setValue(formData.getTrustType());
				}					
				if (trustDate != null) {
					trustDate.setValue(formData.getTrustDate());
				}		
				if (trustAmount != null) {
					//String trustAmountString = Long.toString(formData.getTrustAmount().longValue());
					NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
					trustAmount.setValue(n.format(formData.getTrustAmount()));
				}		
				if (deputyClerk != null) {
					deputyClerk.setValue(formData.getClerkName());
				}					
				
				acroForm.flatten();
				
				
				//generate Clerk's Certificate of Mailing
				formData.setFormDisplayName("Notice of Unclaimed Funds");
				String certPath = generateClerksCertOfMailing(owner,formData,counter);
				File file = new File(certPath);
				cert = new FileInputStream(file); 		
				
				//get number of pages from cert
				PDDocument generatedCert = PDDocument.load(cert);				
				numPages = generatedCert.getNumberOfPages();
				generatedCert.close();
				
				//add number of pages from form
				numPages += doc.getNumberOfPages();
				
				//save to output stream, for merge, then save to disk for upload of individual form and cert
				doc.save(baos);
				
				merger.addSource(new ByteArrayInputStream(baos.toByteArray()));			
				merger.addSource(certPath);			
				
				mergeSingleNotice.addSource(new ByteArrayInputStream(baos.toByteArray()));
				mergeSingleNotice.addSource(certPath);
				mergeSingleNotice.setDestinationFileName(path + trustId + "/NoticeOfUnclaimedFunds" + counter + ".pdf");
				mergeSingleNotice.mergeDocuments(null);
				
				doc.close();
				cert.close();
				
			}
		            	
            merger.setDestinationFileName(fullPath);
            merger.mergeDocuments(null);	        
			addBookmarks(lawfulOwners, fullPath, numPages); 		            		         	
            
			return "success";

		} catch (InvalidPasswordException e) {
			e.printStackTrace();
			return "fail";
		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}
		
	}

	private void addBookmarks(List<LawfulOwner> lawfulOwners, String fullPath, int numPages)
			throws FileNotFoundException, InvalidPasswordException, IOException {
		
		int bookmarkPage = 0; //zero index, so first page of PDF is zero
		File initialFile = new File(fullPath);
		InputStream finalInput = new FileInputStream(initialFile);						
		PDDocument finalDoc = new PDDocument().load(finalInput);
		
		//add bookmarks
		PDDocumentOutline outline =  new PDDocumentOutline();
		finalDoc.getDocumentCatalog().setDocumentOutline( outline );
		PDOutlineItem pagesOutline = new PDOutlineItem();
		pagesOutline.setTitle("Lawful Owners");
		outline.addLast( pagesOutline );					
		       
		for (LawfulOwner owner : lawfulOwners) {
				final PDPageFitWidthDestination dest = new PDPageFitWidthDestination();
				dest.setPageNumber(bookmarkPage);
				final PDOutlineItem bookmark = new PDOutlineItem();
				bookmark.setDestination(dest);
				bookmark.setTitle(owner.getNameLine1());
				pagesOutline.addLast(bookmark);
				bookmarkPage = bookmarkPage + numPages;    			
		}
		pagesOutline.openNode();
		outline.openNode();

		finalDoc.save(fullPath);
		finalDoc.close();
	}
	
	public String generateClerksCertOfMailing(LawfulOwner lawfulOwner, FormData formData, int index) throws IOException {
		
		StringBuffer datText = new StringBuffer();
		OutputStream outStream = null;	
		final ResourceBundle outputPath = ResourceBundle.getBundle("escheatment");
		BigDecimal trustId = lawfulOwner.getTrustId();
		String path = outputPath.getString("pdf.outputPath");
		String fullPath = path + trustId  + "/CertOfMailing" + index + ".pdf";
		new File(path + trustId).mkdir();				
		
		
		datText.append( "^Job GenerateOutput -qfsnameClerks_Certificate_of_Service_By_Mail_Op.qfs\n");
		datText.append("^field PAGE_NUMBER\n" + 
				"123\n" + 
				"^field TO_ADDR_MARKER_START\n" + 
				"^global Footer_R_NAME\n" + 
				"^global Footer_FIRM_NAME\n" + 
				"^global Footer_R_ADDR_LINE1\n" + 
				"^global Footer_R_ADDR_LINE2\n" + 
				"^global Footer_R_CITY\n" + 
				"^global Footer_R_STATE\n" + 
				"^global Footer_R_ZIP_CODE\n" + 
				"^field TO_ADDR_MARKER_END\n" + 
				"^global RECIPIENT_ADDRESS\n" + 
				"^field PART_NAME_ID\n" +				
				"^global R_NAME\n" + 
				lawfulOwner.getNameLine1() + "\n" + 
				"^global FIRM_NAME\n" +
				lawfulOwner.getNameLine2() + "\n" +
				"^global R_ADDR_LINE1\n" + 
				lawfulOwner.getAddressLine1() + "\n" + 
				"^global R_ADDR_LINE2\n" + 
				lawfulOwner.getAddressLine2() + "\n" +
				"^global R_CITY\n" + 
				lawfulOwner.getAddressLine3() + "\n" + 
				"^global R_STATE\n" +
				"^global PROVINCE\n" + 
				"^global R_ZIP_CODE\n" + 				
				"^global COUNTRY\n" + 
				"US\n" + 
				"^field SERIALNUMBER\n" + 
				"1\n" + 				 
				"^global ALL_CHEK\n" + 
				"X\n" + 
				"^global ADDRESS\n" + 
				"STOLO\n" + 
				"^global TO_ADDRESS\n" + 
				"STOLO\n" + 
				"^global COUNTY_NAME\n" + 
				"Orange\n" + 
				"^global COURT_ADDRESS\n" + 
				formData.getCourtAddrLine1() + "\n" + 
				"^global C_C_S_Z\n" + 
				formData.getCourtAddrLine2() + "\n" + 
				"^global COURT_LOC_DESC\n" + 
				formData.getCourtLocation() + "\n" + 
				"^global CITY\n" + 
				"^global MAILDATE\n" + 
				"10/18/2018\n" + 
				"^global SHORT_TITLE\n" + 
				formData.getCaseTitle() + "\n" + 
				"^global DISPLAY_CASE_NUM\n" + 
				formData.getCaseNumber() + "\n" +
				"^global PRINT_OPTION\n" + 
				"NPR\n" + 
				"^global SYSTEM_DATE\n" + 
				"10/17/2018\n" + 
				"^global LOCATION\n" + 
				"1600 Pennsylvania Ave. Washington DC 10204\n" + 
				"^global CLERK_CERT_INFO\n" + 
				"stolo\n" + 
				"^global OUTPUT_FORM_NAME\n" + 
				formData.getFormDisplayName() + "\n" + 
				"^field CLERK_SIGNATURE\n" + 
				//TODO address clerk signature
				"^field SYSDATE_1\n" + 
				"10/17/2018\n" + 
				"^field MAILING_LOCATION\n" + 
				"1600 Pennsylvania Ave. Washington DC 10204\n" + 
				"^field SYSDATE_2\n" + 
				"10/18/2018\n" + 
				"^field copy_text \n" + 
				" ORIGINAL");
		
		DocGen job = new DocGen();	
		DocGen.addDocGenListener(this);
		job.setDatByteArray(String.valueOf(datText).getBytes());
		outStream = new FileOutputStream(fullPath);
		job.setDocOutputStream(outStream);					
		
		String termID = "formGen";//The process is given a name that will allow to identify it in the server log
		job.setTermId(termID);
		
		if (DocGen.isInitialized() == false) {
			docPathInit();		
		}	
		
		int validation = job.start();//The document is created			
		if (validation != DocGen.OK) {
			System.out.println("Error in DocPath form creation (error code=" + validation + ")");
		} else {
			validation = job.waitForRc(30000);//Wait 30 seconds for the job to finish
			
			if (validation == DocGen.TIMEOUT) {
				System.out.println("DocPath timeout error (error code=" + validation + ")");
			} else if (validation == DocGen.OK) {
				System.out.println( "Form generation successful." );
			} else {
				System.out.println("Error in DocPath form creation (error code=" + validation + ")");
			}
		}					
		
		job.finish(); //This will release all resources associated to this job
		outStream.close();	 
		DocGen.removeDocGenListener(this);
		//DocGen.close();		
		
		return fullPath;
	}

	private void docPathInit() {
				 
		String iniFile = getClass().getClassLoader().getResource("hpc.properties").getPath();
		int rc = DocGen.init(iniFile,"Escheatment","Escheatment");
				 
		if (rc == 0) {
			System.out.println("DocPath HPC initialized OK");				 
		} else {
			System.out.println("ERROR initializing DocPath HPC (error code=" + rc +")");
		}
	}


	public String populateRequestForAddlInfo(LawfulOwner owner, FormData formData) throws InvalidPasswordException, IOException {
		try {
			
			BigDecimal trustId = owner.getTrustId();						
			FileInputStream cert = null;
			PDFMergerUtility merger = new PDFMergerUtility();			
			//get output path from properties file
			final ResourceBundle outputPath = ResourceBundle.getBundle("escheatment");
			String path = outputPath.getString("pdf.outputPath");
			String fullPath = path + trustId  + "/RequestForAddlInfo.pdf";
			new File(path + trustId).mkdir();			
			
			InputStream input = PdfUtil.class.getResourceAsStream("/pdf/templates/Request_For_Additional_Information_Template.pdf");

			PDDocument doc = new PDDocument().load(input);
			PDDocumentCatalog docCatalog = doc.getDocumentCatalog();
			PDAcroForm acroForm = docCatalog.getAcroForm();
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date today = new Date();
			PDField date = acroForm.getField("Date");	
			PDField nameLine1 = acroForm.getField("nameLine1");		
			PDField nameLine2 = acroForm.getField("nameLine2");
			PDField addressLine1 = acroForm.getField("addressLine1");
			PDField addressLine2 = acroForm.getField("addressLine2");
			PDField addressLine3 = acroForm.getField("addressLine3");
			PDField caseNumber = acroForm.getField("caseNumber");			
			PDField caseTitle = acroForm.getField("caseTitle");		
			PDField trustAmount = acroForm.getField("trustAmount");
			PDField trustNumber = acroForm.getField("trustNumber");			
			PDField nameLine1_2 = acroForm.getField("nameLine1_2");
			PDField submitDate = acroForm.getField("submitDate");			
			PDField sigMissing = acroForm.getField("signatureMissing");
			PDField notarizationMissing = acroForm.getField("notarizationMissing");
			PDField photocopy = acroForm.getField("photocopy");
			PDField insufficientDocumentation = acroForm.getField("insufficientDocumentation");
			PDField other = acroForm.getField("Other");
			PDField otherReasonLine1 = acroForm.getField("otherReason1");	
			PDField otherReasonLine2 = acroForm.getField("otherReason2");
			PDField deputyClerk = acroForm.getField("deputyClerk");
			
			if (date != null) {
				date.setValue(dateFormat.format(today));
			}				
			if (nameLine1 != null) {
				nameLine1.setValue(owner.getNameLine1());
			}
			if (nameLine1_2 != null) {
				nameLine1_2.setValue(owner.getNameLine1() + ",");
			}			
			if (nameLine2 != null) {
				nameLine2.setValue(owner.getNameLine2());
			}	
			if (addressLine1 != null) {
				addressLine1.setValue(owner.getAddressLine1());
			}	
			if (addressLine2 != null) {
				addressLine2.setValue(owner.getAddressLine2());
			}	
			if (addressLine3 != null) {
				addressLine3.setValue(owner.getAddressLine3());
			}		
			if (caseNumber != null) {
				caseNumber.setValue(formData.getCaseNumber());
			}			
			if (caseTitle != null) {
				caseTitle.setValue(formData.getCaseTitle());
			}					
			if (trustNumber != null) {
				trustNumber.setValue(formData.getTrustNumber());
			}	
			if (submitDate != null) {
				submitDate.setValue(formData.getClaimSubmitDate());
			}			
			switch (formData.getRejectReason()) {
				case Constants.AFFIRMATION_SIG_MISSING: 
					if (sigMissing != null ) sigMissing.setValue("X");
					break;
					
				case Constants.NOTARIZATION_MISSING: 
					if (notarizationMissing != null ) notarizationMissing.setValue("X");
					break;	
					
				case Constants.PHOTO_ID_MISSING: 
					if (photocopy != null ) photocopy.setValue("X");
					break;	
					
				case Constants.INSUFFICIENT_DOCUMENTATION: 
					if (insufficientDocumentation != null ) insufficientDocumentation.setValue("X");
					break;		
					
				case Constants.OTHER_REJECT_REASON: 
					if (other != null ) other.setValue("X");
					
					if (otherReasonLine1 != null ) {
						otherReasonLine1.setValue(formData.getOtherRejectReason().substring(1,Math.min(140, formData.getOtherRejectReason().length())));	
					}
					
					if (otherReasonLine2 != null && formData.getOtherRejectReason().length() > 140) {
						otherReasonLine2.setValue(formData.getOtherRejectReason().substring(141,formData.getOtherRejectReason().length()));	
					}										
					break;						
			}
			
			if (trustAmount != null) {				
				NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
				trustAmount.setValue(n.format(formData.getTrustAmount()));				
			}		
			if (deputyClerk != null) {
				deputyClerk.setValue(formData.getClerkName());
			}					
			
			acroForm.flatten();
			
			
			//generate Clerk's Certificate of Mailing
			formData.setFormDisplayName("Request For Additional Information");
			String certPath = generateClerksCertOfMailing(owner,formData,1);
			File file = new File(certPath);
			cert = new FileInputStream(file); 		
			
			//get number of pages from cert
			PDDocument generatedCert = PDDocument.load(cert);				
			generatedCert.close();
			
			doc.save(fullPath);
			merger.addSource(fullPath);
			merger.addSource(certPath);			
			doc.close();
			cert.close();
			
            	
            merger.setDestinationFileName(fullPath);
            merger.mergeDocuments(null);	

            
			return "success";

		} catch (InvalidPasswordException e) {
			e.printStackTrace();
			return "fail";
		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}
		
	}
	
	public void JobCompleted(DocGenJobCompletedEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
