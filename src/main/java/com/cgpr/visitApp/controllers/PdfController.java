//package com.cgpr.visitApp.controllers;
//
//import static com.cgpr.visitApp.utils.Constants.APP_ROOT;
//
//import java.io.IOException;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cgpr.visitApp.services.PdfService;
//import com.cgpr.visitApp.services.RelationshipTypeService;
//import com.ibm.icu.text.ArabicShapingException;
//import com.itextpdf.text.DocumentException;
//
//@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
// 
//public class PdfController {
//
//	private PdfService pdfService;
//	 
//	private RelationshipTypeService relationshipTypeService;
//	@Autowired
//	public PdfController(PdfService pdfService,RelationshipTypeService relationshipTypeService ) { 
//
//		this.pdfService = pdfService;
//		this.relationshipTypeService=relationshipTypeService;
//		 
//	}
//
//	 
//
//	@GetMapping(path = APP_ROOT + "PDF/generate/{gouvernorat}/{prison}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public void generatePdf(HttpServletResponse response,@PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
//	    byte[] pdfData = null;
//
//	    try {
//	        pdfData = pdfService.exportPdf(relationshipTypeService.findByPrisonAndStatutOAndStatutSMSReady(  gouvernorat ,prison),"hhhhh" );
//	    } catch (DocumentException | IOException | ArabicShapingException e) {
//	        // Gérez les exceptions ici
//	        e.printStackTrace();
//	    }
//
//	    // Définissez le type de contenu de la réponse en tant que PDF
//	    response.setContentType("application/pdf");
//
//	    // Ajoutez l'en-tête Content-Disposition pour indiquer au navigateur d'ouvrir le PDF en ligne
//	    response.setHeader("Content-Disposition", "inline; filename=enfnat.pdf");
//
//	    try {
//	        // Écrivez le contenu du PDF dans le flux de réponse
//	        ServletOutputStream outputStream = response.getOutputStream();
//	        outputStream.write(pdfData);
//	        outputStream.close();
//	    } catch (IOException e) {
//	        // Gérez les exceptions d'E/S ici
//	        e.printStackTrace();
//	    }
//	}
//
// 
// 
//}
