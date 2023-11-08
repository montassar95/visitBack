package com.cgpr.visitApp.services;

import java.io.IOException;
import java.util.List;

import com.cgpr.visitApp.dto.RelationshipTypeDto;
import com.ibm.icu.text.ArabicShapingException;
import com.itextpdf.text.DocumentException;

public interface PdfService {
//	 findByPrisonAndStatutOAndStatutSMSReady(String gouvernorat, String prison) 
	byte[] exportPdf(  List<RelationshipTypeDto> list ,String prison,String title)throws DocumentException, IOException, ArabicShapingException; 
	byte[] exportWithOutVisitPdf(  List<RelationshipTypeDto> list ,String prison,String title)throws DocumentException, IOException, ArabicShapingException;
}
