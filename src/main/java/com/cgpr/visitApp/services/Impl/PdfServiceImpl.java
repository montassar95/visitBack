package com.cgpr.visitApp.services.Impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cgpr.visitApp.services.PdfService;
import com.ibm.icu.text.ArabicShapingException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.cgpr.visitApp.config.*;
import com.cgpr.visitApp.dto.RelationshipTypeDto;
import com.cgpr.visitApp.dto.VisitorDto;

@Service
public class PdfServiceImpl implements PdfService {

    public static final Font FONT = new Font();
    public static final Font BOLD = new Font(FontFamily.HELVETICA, 12, Font.BOLD);

    ConfigShaping boldConf = new ConfigShaping();
    Font boldfontLabelTop = boldConf.getFontForArabicAmiri1(12);

    public final Font boldfontTitle = boldConf.getFontForArabicAmiri(30);
    public final Font boldfontTitleStatique = boldConf.getFontForArabic(30);
    public final Font boldfontLabel20 = boldConf.getFontForArabic(20);
    public final Font boldfontLabel = boldConf.getFontForArabic(16);
    public final Font boldfontFamielle = boldConf.getFontForArabic(14);
    public final Font boldfontFamielle11 = boldConf.getFontForArabic(11);
    public final Font boldfontFamielle13 = boldConf.getFontForArabic(13);
    public final Font boldfontFamielle12 = boldConf.getFontForArabic(12);
    public final Font boldfontLabelAmiri = boldConf.getFontForArabicAmiri(17);
    public final Font boldfontLabelAmiri14 = boldConf.getFontForArabicAmiri(13);
    public final Font boldfontLabelAmiri13 = boldConf.getFontForArabicAmiri(13);
    public final Font boldfontLabelAmiri11 = boldConf.getFontForArabicAmiri(12);
    public final Font boldfontLabelAmiri9 = boldConf.getFontForArabicAmiri(9);
    public final Font boldfontLabel9 = boldConf.getFontForArabic(10);

    public static final URL resource = PdfService.class.getResource("/images/cgpr.png");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private void addCellToTable(PdfPTable table, String cellText, Font font) {
        Phrase phrase = new Phrase(cellText, font);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(50);
        cell.setBorder(0);
        table.addCell(cell);
    }

    private void addCellToTable(PdfPTable table, String content, Font font, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(colspan);
        cell.setFixedHeight(30);
        table.addCell(cell);
    }

    public static String getCurrentArabicMonth(int currentMonth) {
        String[] arabicMonths = { "جانفي", "فيفري", "مارس", "أفريل", "ماي", "جوان", "جويلية", "أوت", "سبتمبر", "أكتوبر",
                "نوفمبر", "ديسمبر" };

        int currentMonthIndex = currentMonth - 1;

        return arabicMonths[currentMonthIndex];
    }

    @Override
    public byte[] exportPdf(List<RelationshipTypeDto> list, String prison, String titre) throws DocumentException, IOException, ArabicShapingException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu mm:HH");
        LocalDateTime localDate = LocalDateTime.now();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 25f, 0f);
        PdfWriter.getInstance(document, out);
        document.open();
        Image image = Image.getInstance(resource);
        image.scaleAbsolute(110f, 100f);

        PdfPTable tableTop = new PdfPTable(3);
        tableTop.setWidthPercentage(100);

        Phrase p1Top;
        PdfPCell c1Top;

        PdfPTable tab = new PdfPTable(1);
        tab.setWidthPercentage(100);

        Phrase ptab;
        PdfPCell ctab;

        ptab = new Phrase(boldConf.format("الجمهورية التونسية "), boldfontLabelTop);
        ctab = new PdfPCell(ptab);
        ctab.setHorizontalAlignment(Element.ALIGN_RIGHT);
        ctab.setBorder(0);
        ctab.setPaddingRight(27f);
        tab.addCell(ctab);

        ptab = new Phrase(boldConf.format("وزارة العدل "), boldfontLabelTop);
        ctab = new PdfPCell(ptab);
        ctab.setHorizontalAlignment(Element.ALIGN_RIGHT);
        ctab.setBorder(0);
        ctab.setPaddingRight(36f);
        tab.addCell(ctab);

        ptab = new Phrase(boldConf.format(  "الهيئة العامة للسجون والإصلاح "), boldfontLabelTop);
        ctab = new PdfPCell(ptab);
        ctab.setHorizontalAlignment(Element.ALIGN_RIGHT);
        ctab.setBorder(0);
        tab.addCell(ctab);
        
        ptab = new Phrase(boldConf.format(  "سجن"+" "+prison), boldfontLabelTop);
        ctab = new PdfPCell(ptab);
        ctab.setHorizontalAlignment(Element.ALIGN_RIGHT);
        ctab.setBorder(0);
        ctab.setPaddingRight(36f);
        tab.addCell(ctab);

        p1Top = new Phrase(boldConf.format(dtf.format(localDate)) + " " + boldConf.format("تونس في"), boldfontLabelTop);

        c1Top = new PdfPCell(p1Top);
        c1Top.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1Top.setBorder(0);
        tableTop.addCell(c1Top);

        c1Top = new PdfPCell(image);
        c1Top.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1Top.setBorder(0);
        tableTop.addCell(c1Top);

        c1Top = new PdfPCell(tab);
        c1Top.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1Top.setBorder(0);
        tableTop.addCell(c1Top);

        PdfPTable table = new PdfPTable(100);
        table.setWidthPercentage(100);

        Phrase p1;
        PdfPCell c1 =null;

        p1 = new Phrase( titre , boldfontLabel20);
        c1 =new PdfPCell(p1);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        c1.setBackgroundColor(new BaseColor(255, 255, 255));
        c1.setColspan(100);
     c1.setFixedHeight(30);
        c1.setBorder(0);
        
        table.addCell(c1);

//        p1 = new Phrase(" رقم الهاتف", boldfontLabel);
//        c1 = new PdfPCell(p1);
//        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        c1.setBackgroundColor(new BaseColor(210, 210, 210));
//        c1.setColspan(1);
//        table.addCell(c1);
//
//        p1 = new Phrase(" هوية الزائر ", boldfontLabel);
//        c1 = new PdfPCell(p1);
//        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        c1.setBackgroundColor(new BaseColor(210, 210, 210));
//        c1.setColspan(1);
//        table.addCell(c1);
//
        
        p1 = new Phrase("    هــــــــــــوية الزائــــــــــــر", boldfontLabel);
        c1 = new PdfPCell(p1);
        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new BaseColor(210, 210, 210));
        c1.setColspan(40);
        table.addCell(c1);

        
        
        p1 = new Phrase(" توقيـــت الزيـــارة", boldfontLabel);
        c1 = new PdfPCell(p1);
        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new BaseColor(210, 210, 210));
        c1.setColspan(20);
        table.addCell(c1);

        p1 = new Phrase("هوية السجين", boldfontLabel);
        c1 = new PdfPCell(p1);
        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new BaseColor(210, 210, 210));
        c1.setColspan(20);
        table.addCell(c1);

        p1 = new Phrase("عدد المذكرة", boldfontLabel);
        c1 = new PdfPCell(p1);
        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new BaseColor(210, 210, 210));
        c1.setColspan(15);
        table.addCell(c1);
        
        p1 = new Phrase("ع/ر", boldfontLabel);
        c1 = new PdfPCell(p1);
        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(new BaseColor(210, 210, 210));
        c1.setColspan(5);
        table.addCell(c1);
        
        

        Font cellFont = new Font(Font.FontFamily.HELVETICA, 10);
        PdfPTable tableVisitors = new PdfPTable(40);
        tableVisitors.setWidthPercentage(100);
        int i=1;
        String jourPrecedent = null;
        for (RelationshipTypeDto r : list) {
        	 
	        
        	 if (jourPrecedent != null && !jourPrecedent.equals(r.getDay())) {
        	        document.newPage(); // Saut de page lorsque le jour change
        	      
        	      
        	        
        	        p1 = new Phrase(" ** ", boldfontLabel);
        	        c1 = new PdfPCell(p1);
        	        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        	        c1.setBackgroundColor(new BaseColor(255, 255, 255));
        	        c1.setColspan(100);
        	        c1.setBorder(0);
        	        table.addCell(c1);
        	        
        	         
        	        
        	    }
        	 
        	List<VisitorDto> visitors = r.getVisitorsDto();

            if (visitors.isEmpty()) {
                // Vous pouvez ajouter un message pour gérer le cas où il n'y a pas de visiteurs.
                continue;
            }
            int visitorsCount = visitors.size();
            for (VisitorDto visitorDto :visitors) {
           	 p1 = new Phrase(visitorDto.getPhone().toString(), boldfontLabelAmiri13);
                c1 = new PdfPCell(p1);
                c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c1.setBorder(0);
                c1.setColspan(10);
                tableVisitors.addCell(c1);
                
                
                
                
                String fullNameVisitor =visitorDto.getFirstName();
              //Vérifier et traiter les champs lastName, fatherName et grandFatherName
              if (visitorDto.getLastName() != null) {
            	  
            	  fullNameVisitor =fullNameVisitor +" "+visitorDto.getLastName();
              } else {
            	  
            	  fullNameVisitor =fullNameVisitor +" ";
              }

              if (visitorDto.getFatherName() != null) {
            	  
            	  fullNameVisitor =fullNameVisitor +" "+visitorDto.getFatherName();
              } else {
            	   
            	  fullNameVisitor =fullNameVisitor +" ";
              }

              if (visitorDto.getGrandFatherName() != null) {
            	   
            	  fullNameVisitor =fullNameVisitor +" "+visitorDto.getGrandFatherName();
              } else {
            	  
            	  fullNameVisitor =fullNameVisitor +" ";
              } 
              
              
              
                p1 = new Phrase(fullNameVisitor, boldfontLabelAmiri13);
                c1 = new PdfPCell(p1);
                c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c1.setBorder(0);
                c1.setColspan(20);
                tableVisitors.addCell(c1);

                p1 = new Phrase(visitorDto.getRelationship(), boldfontLabelAmiri13);
                c1 = new PdfPCell(p1);
                c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
                c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                c1.setColspan(10);
                c1.setBorder(0);
                tableVisitors.addCell(c1);
                
                
           }
            
            c1 = new PdfPCell(tableVisitors);
            c1.setColspan(40);
//            c1.setFixedHeight(25);
            table.addCell(c1);
            jourPrecedent = r.getDay();
            tableVisitors = new PdfPTable(40);
            
            
            p1 = new Phrase(r.getDay() + " على ساعة " + r.getTime()  ,
                    boldfontLabelAmiri13);
            c1 = new PdfPCell(p1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            c1.setRowspan(visitorsCount); // rowspan pour la cellule de détenu
            c1.setColspan(20);
//            c1.setFixedHeight(25);
            table.addCell(c1);
            
            
            
            p1 = new Phrase(r.getFirstName() + " " + r.getFatherName() + " " + r.getGrandFatherName() + " " + r.getLastName(),
                    boldfontLabelAmiri13);
            c1 = new PdfPCell(p1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            c1.setRowspan(visitorsCount); // rowspan pour la cellule de détenu
            c1.setColspan(20);
//            c1.setFixedHeight(25);
            table.addCell(c1);

            p1 = new Phrase(r.getIdPrisoner().toString(), boldfontLabelAmiri13);
            c1 = new PdfPCell(p1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
          c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
          c1.setColspan(15);
//          c1.setFixedHeight(25);
        //    c1.setRowspan(visitorsCount); // rowspan pour la cellule de détenu
            table.addCell(c1);
          
            
            p1 = new Phrase((i++)+"", boldfontLabelAmiri13);
            c1 = new PdfPCell(p1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
          c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
          c1.setColspan(5);
        //    c1.setRowspan(visitorsCount); // rowspan pour la cellule de détenu
            table.addCell(c1);
            
           
            jourPrecedent = r.getDay();
        }

        document.add(tableTop);
        document.add(table);

        document.close();
        return out.toByteArray();
    }

	@Override
	public byte[] exportWithOutVisitPdf(List<RelationshipTypeDto> list, String prison, String title)
			throws DocumentException, IOException, ArabicShapingException {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu mm:HH");
	        LocalDateTime localDate = LocalDateTime.now();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 25f, 0f);
	        PdfWriter.getInstance(document, out);
	        document.open();
	        Image image = Image.getInstance(resource);
	        image.scaleAbsolute(110f, 100f);

	        PdfPTable tableTop = new PdfPTable(3);
	        tableTop.setWidthPercentage(100);

	        Phrase p1Top;
	        PdfPCell c1Top;

	        PdfPTable tab = new PdfPTable(1);
	        tab.setWidthPercentage(100);

	        Phrase ptab;
	        PdfPCell ctab;

	        ptab = new Phrase(boldConf.format("الجمهورية التونسية "), boldfontLabelTop);
	        ctab = new PdfPCell(ptab);
	        ctab.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        ctab.setBorder(0);
	        ctab.setPaddingRight(27f);
	        tab.addCell(ctab);

	        ptab = new Phrase(boldConf.format("وزارة العدل "), boldfontLabelTop);
	        ctab = new PdfPCell(ptab);
	        ctab.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        ctab.setBorder(0);
	        ctab.setPaddingRight(36f);
	        tab.addCell(ctab);

	        ptab = new Phrase(boldConf.format(  "الهيئة العامة للسجون والإصلاح "), boldfontLabelTop);
	        ctab = new PdfPCell(ptab);
	        ctab.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        ctab.setBorder(0);
	        tab.addCell(ctab);
	        
	        ptab = new Phrase(boldConf.format(  "سجن"+" "+prison), boldfontLabelTop);
	        ctab = new PdfPCell(ptab);
	        ctab.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        ctab.setBorder(0);
	        ctab.setPaddingRight(36f);
	        tab.addCell(ctab);

	        p1Top = new Phrase(boldConf.format(dtf.format(localDate)) + " " + boldConf.format("تونس في"), boldfontLabelTop);

	        c1Top = new PdfPCell(p1Top);
	        c1Top.setHorizontalAlignment(Element.ALIGN_LEFT);
	        c1Top.setBorder(0);
	        tableTop.addCell(c1Top);

	        c1Top = new PdfPCell(image);
	        c1Top.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1Top.setBorder(0);
	        tableTop.addCell(c1Top);

	        c1Top = new PdfPCell(tab);
	        c1Top.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        c1Top.setBorder(0);
	        tableTop.addCell(c1Top);

	        PdfPTable table = new PdfPTable(100);
	        table.setWidthPercentage(100);

	        Phrase p1;
	        PdfPCell c1 =null;

	        p1 = new Phrase(boldConf.format(title), boldfontLabel20);
	        c1 =new PdfPCell(p1);
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setBackgroundColor(new BaseColor(255, 255, 255));
	        c1.setColspan(100);
	         c1.setFixedHeight(30);
	        c1.setBorder(0);
	        
	        table.addCell(c1);

 
	        p1 = new Phrase("   تـــاريخ الدخـــول ", boldfontLabel);
	        c1 = new PdfPCell(p1);
	        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setBackgroundColor(new BaseColor(210, 210, 210));
	        c1.setColspan(25);
	        table.addCell(c1);
	        
	        p1 = new Phrase("  عـــدد الإيقـــاف ", boldfontLabel);
	        c1 = new PdfPCell(p1);
	        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setBackgroundColor(new BaseColor(210, 210, 210));
	        c1.setColspan(25);
	        table.addCell(c1);

	        p1 = new Phrase("هوية السجين", boldfontLabel);
	        c1 = new PdfPCell(p1);
	        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setBackgroundColor(new BaseColor(210, 210, 210));
	        c1.setColspan(25);
	        table.addCell(c1);

	        p1 = new Phrase("عدد المذكرة", boldfontLabel);
	        c1 = new PdfPCell(p1);
	        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setBackgroundColor(new BaseColor(210, 210, 210));
	        c1.setColspan(20);
	        table.addCell(c1);
	        
	        p1 = new Phrase("ع/ر", boldfontLabel);
	        c1 = new PdfPCell(p1);
	        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setBackgroundColor(new BaseColor(210, 210, 210));
	        c1.setColspan(5);
	        table.addCell(c1);
	        
	        

	        Font cellFont = new Font(Font.FontFamily.HELVETICA, 10);
	       

	         
	      
	      
	        int i=1;
	        for (RelationshipTypeDto r : list) {
	        	 
	        	   p1 = new Phrase(r.getEventDate()  , boldfontLabelAmiri13);
		            c1 = new PdfPCell(p1);
		            c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
		            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//		            c1.setRowspan(visitorsCount); // rowspan pour la cellule de détenu
		            c1.setColspan(25);
//		            c1.setFixedHeight(25);
		            table.addCell(c1);
		           
		            
		            String residance =r.getAnneeResidance()+ " - " + r.getCodeResidance();
	           if(!r.getCodeNationalite().toString().equals("001")) {
	        	   residance =residance+" "+"أجنبـــي ";
	           }
	           
	            p1 = new Phrase(r.getAnneeResidance()+ " - " + r.getCodeResidance() , boldfontLabelAmiri13);
	            c1 = new PdfPCell(p1);
	            c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
	            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	            c1.setRowspan(visitorsCount); // rowspan pour la cellule de détenu
	            c1.setColspan(25);
//	            c1.setFixedHeight(25);
	            table.addCell(c1);
	           
	            
	            
	            
	            
	            p1 = new Phrase(r.getFirstName() + " " + r.getFatherName() + " " + r.getGrandFatherName() + " " + r.getLastName(),
	                    boldfontLabelAmiri13);
	            c1 = new PdfPCell(p1);
	            c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
	            c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	            c1.setRowspan(visitorsCount); // rowspan pour la cellule de détenu
	            c1.setColspan(25);
//	            c1.setFixedHeight(25);
	            table.addCell(c1);

	            p1 = new Phrase(r.getIdPrisoner().toString(), boldfontLabelAmiri13);
	            c1 = new PdfPCell(p1);
	            c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
	          c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          c1.setColspan(20);
//	          c1.setFixedHeight(25);
	        //    c1.setRowspan(visitorsCount); // rowspan pour la cellule de détenu
	            table.addCell(c1);
	          
	            
	            p1 = new Phrase((i++)+"", boldfontLabelAmiri13);
	            c1 = new PdfPCell(p1);
	            c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
	          c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          c1.setColspan(5);
	        //    c1.setRowspan(visitorsCount); // rowspan pour la cellule de détenu
	            table.addCell(c1);
	            
	      
	        }

	        document.add(tableTop);
	        document.add(table);

	        document.close();
	        return out.toByteArray();
	}
}
