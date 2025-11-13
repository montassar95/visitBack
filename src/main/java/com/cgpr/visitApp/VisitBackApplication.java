package com.cgpr.visitApp;
 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VisitBackApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VisitBackApplication.class, args);
		
		 
	}

}
//update relationship_type set statutsms = null where statutsms='FAILED'
//commit
//confirmation 
//run app


//
//SELECT 
////det.TCODDET,
//det.TDATDET,
//det.TDATLIBE,
//ide.TPNOMA,
//ide.TPPERA,
//ide.TPGPERA,
//ide.TNOMA,
//GETLIBELLE_NATIONALITE(ide.CODE_NATIONALITE) AS nationalite,
//
//-- Codes prison et gouvernorat selon disponibilité
//COALESCE(res.TCODGOU, mut.TCODGOU) AS codGou,
//COALESCE(res.TCODPR, mut.TCODPR) AS codPr,
//
//-- Libellé prison
//COALESCE(
//    GETLIBELLEPRISON(res.TCODGOU, res.TCODPR),
//    GETLIBELLEPRISON(mut.TCODGOU, mut.TCODPR)
//) AS libellePrison,
//
//-- Année et code résidence
//COALESCE(res.TANNRES, mut.TANNRES) AS TANNRES,
//COALESCE(res.TCODRES, mut.TCODRES) AS TCODRES
//
//FROM TDETENTION det
//LEFT JOIN TRESIDENCE res 
//ON res.TNUMIDE = det.TNUMIDE
//AND res.TCODDET = det.TCODDET
//AND    res.TDATDR  = det.TDATDET
//--  AND ABS(TRUNC(res.TDATDR) - TRUNC(det.TDATDET)) <= 1  -- tolérance de ±1 jour
//LEFT JOIN TMUTATION mut 
//ON det.TNUMIDE = mut.TNUMIDE 
//AND det.TCODDET = mut.TCODDET
//AND mut.TCODMUT = '01'
//JOIN TIDENTITE ide 
//ON ide.TNUMIDE = det.TNUMIDE
//
//WHERE TRUNC(det.TDATDET) BETWEEN TO_DATE('08-10-2025', 'DD-MM-YYYY') 
//                         AND TO_DATE('08-10-2025', 'DD-MM-YYYY')
//AND COALESCE(res.TCODGOU, mut.TCODGOU) = '24'  -- ton code gouvernorat
//AND COALESCE(res.TCODPR, mut.TCODPR) = '03' -- ton code prison
//
//ORDER BY det.TCODDET;ide.TNUMIDE,


