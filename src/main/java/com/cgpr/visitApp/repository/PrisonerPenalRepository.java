package com.cgpr.visitApp.repository;

 
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.cgpr.visitApp.dto.PrisonDto;
import com.cgpr.visitApp.dto.PrisonerDto;
import com.cgpr.visitApp.model.Prisoner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class PrisonerPenalRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public Optional<PrisonerDto> findById(String id) {
//    //	dt.tclores='O' and 
////        String sql = "SELECT DISTINCT  ide.TNUMIDE, ide.TPNOMA, ide.TPPERA, ide.TPGPERA, ide.TNOMA, ide.CODE_NATIONALITE, "
////        		+"res.tclores , res.tannres , res.tcodres , res.TCODGOU, res.TCODPR, res.TCODDET,  ja.TETAT, ja.TCODTYP , " 
////        		+ "GETLIBELLEPRISON@LINK_PENALE(res.TCODGOU, res.TCODPR) as libellePrison , TNBRMU , res.TDATDR ,  ja.TDATDPE as debutPunition , ja.TDATLIB as finPunition  FROM TIDENTITE@LINK_PENALE ide, tjugearret@LINK_PENALE  ja, "
////        		+ "TRESIDENCE@LINK_PENALE res, TDETENTION@LINK_PENALE dt WHERE res.tclores='O' "
////        		+ " and ide.TNUMIDE = res.TNUMIDE AND ide.TNUMIDE = ? and dt.TNUMIDE = res.TNUMIDE and dt.TCODDET = res.TCODDET and ide.TNUMIDE = ja.TNUMIDE ";
//          
//    	
//    	String sql = 
//    		    "SELECT DISTINCT " +
//    		    "ide.TNUMIDE, " +
//    		    "ide.TPNOMA, " +
//    		    "ide.TPPERA, " +
//    		    "ide.TPGPERA, " +
//    		    "ide.TNOMA, " +
//    		    "ide.CODE_NATIONALITE, " +
//    		    "res.TCLORES, " +
//    		    "res.TANNRES, " +
//    		    "res.TCODRES, " +
//    		    "res.TCODGOU, " +
//    		    "res.TCODPR, " +
//    		    "res.TCODDET, " +
//    		    " gettetat@LINK_PENALE(ide.TNUMIDE, res.TCODDET, 0) AS TETAT, " +
//    		    "ide.TCODTYP, " +
//    		    "GETLIBELLEPRISON@LINK_PENALE(res.TCODGOU, res.TCODPR) AS libellePrison, " +
//    		    "TNBRMU, " +
//    		    "res.TDATDR  " +
////    		    ", ja.TDATDPE AS debutPunition, " +
////    		    "ja.TDATLIB AS finPunition " +
//    		    "FROM TIDENTITE@LINK_PENALE ide " +
//    		    "JOIN TRESIDENCE@LINK_PENALE res ON ide.TNUMIDE = res.TNUMIDE " +
//    		    "JOIN TDETENTION@LINK_PENALE dt ON dt.TNUMIDE = res.TNUMIDE AND dt.TCODDET = res.TCODDET " +
////    		    "LEFT JOIN TJUGEARRET@LINK_PENALE ja ON ide.TNUMIDE = ja.TNUMIDE " +
//    		    "WHERE res.TCLORES = 'O' " +
//    		    "AND ide.TNUMIDE = ?";
//
//        PrisonerDto prisonerDto = null;
//
//        try {
//        	prisonerDto =  jdbcTemplate.queryForObject (sql, new Object[]{id}, (rs, rowNum) -> {
//        		PrisonerDto p = new PrisonerDto();
//        		p.setIdPrisoner(rs.getString("TNUMIDE"));
//        		  p.setFirstName(rs.getString("TPNOMA"));
//                  p.setLastName(rs.getString("TNOMA"));
//                  p.setFatherName(rs.getString("TPPERA"));
//                  p.setGrandFatherName(rs.getString("TPGPERA"));
//        		p.setGrandFatherName(rs.getString("TNOMA"));
//        		p.setCodeGouvernorat(rs.getString("TCODGOU"));
//        		p.setCodePrison(rs.getString("TCODPR"));
//        		p.setNamePrison(rs.getString("libellePrison"));
//        		p.setCodeResidance(rs.getString("TCODRES"));
//        		p.setAnneeResidance(rs.getString("TANNRES"));
//        		p.setStatutResidance(rs.getString("tclores"));
//        		p.setNumDetention(rs.getString("TCODDET"));
//        		p.setCodeNationalite(rs.getString("CODE_NATIONALITE"));
//        		p.setDateDebutPunition(null);
//        		p.setDateFinPunition(null);
//        		 
//        		String etat=rs.getString("TETAT");
//        		if(etat == "I" ) {
//        			etat="L";
//        		} 
//        		p.setEtat(etat);
//        		p.setType(rs.getString("TCODTYP"));
//        		 String[] parts = rs.getString("TDATDR").split(" ");
//
//                 
//         		p.setEventDate(parts[0]);
//         		
//         		if(rs.getInt("TNBRMU") == 0  ) {
//         			p.setStatutResidance("E");
//         		}
//         		else {
//         			p.setStatutResidance("M");
//         		}
//                return p;
//            
//            });
//        } catch (EmptyResultDataAccessException ex) {
//            // Gérez le cas où aucun détenu n'est trouvé en retournant un Optional vide.
//            return Optional.empty();
//        }
//
//        // Si le détenu est trouvé, retournez-le dans un Optional.
//       
//        return Optional.ofNullable(prisonerDto);
//    }
    
    public Optional<PrisonerDto> findById(String id) {

        String sql = "SELECT DISTINCT "
                + "iden.TNUMIDE AS prisoner_id, "
                + "nationalite.LIBELLE_NATIONALITE AS nationalite, "
                + "niveauculturel.TLIBNC AS niveau_culturel, "
                + "profession.LIBELLE_PROFESSION AS profession, "
                + "iden.TPNOMA AS firstname, "
                + "iden.TPPERA AS father_name, "
                + "iden.TPGPERA AS grandfather_name, "
                + "iden.TNOMA AS lastname, "
                + "iden.TDATN AS birth_date, "
                + "iden.TPMER AS mother_name, "
                + "iden.TNOMMER AS maternal_grandmother_name, "
                + "iden.TCODSEX AS sex, "
                + "iden.TADR AS adresse, "
                + "iden.TNOMPCJ AS partenaire, "
                + "iden.TNBRNF AS nombre_enfant, "
                + "iden.CODE_NATIONALITE, "
                + "res.TCLORES, "
                + "res.TANNRES, "
                + "res.TCODRES, "
                + "res.TCODGOU, "
                + "res.TCODPR, "
                + "res.TCODDET, "
                + "GETLIBELLEPRISON@LINK_PENALE(res.TCODGOU, res.TCODPR) AS libellePrison, "
                + "dt.TNBRMU, "
                + "res.TDATDR, "
                + "GETTETAT@LINK_PENALE(iden.TNUMIDE, res.TCODDET, 0) AS TETAT, "
                + "iden.TCODTYP "
                + "FROM TIDENTITE@LINK_PENALE iden "
                + "LEFT JOIN PROFESSION@LINK_PENALE profession "
                + "ON iden.CODE_PROFESSION = profession.CODE_PROFESSION "
                + "LEFT JOIN NIVEAUCULTUREL@LINK_PENALE niveauculturel "
                + "ON iden.TCODNC = niveauculturel.TCODNC "
                + "LEFT JOIN NATIONALITE@LINK_PENALE nationalite "
                + "ON iden.CODE_NATIONALITE = nationalite.CODE_NATIONALITE "
                + "JOIN TRESIDENCE@LINK_PENALE res "
                + "ON iden.TNUMIDE = res.TNUMIDE "
                + "JOIN TDETENTION@LINK_PENALE dt "
                + "ON dt.TNUMIDE = res.TNUMIDE AND dt.TCODDET = res.TCODDET "
                + "WHERE res.TCLORES = 'O' "
                + "AND iden.TNUMIDE = ?";

        try {
            PrisonerDto prisoner = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                PrisonerDto p = new PrisonerDto();
                p.setIdPrisoner(rs.getString("prisoner_id"));
                p.setFirstName(rs.getString("firstname"));
                p.setFatherName(rs.getString("father_name"));
                p.setGrandFatherName(rs.getString("grandfather_name"));
                p.setLastName(rs.getString("lastname"));
                    p.setCodeNationalite(rs.getString("CODE_NATIONALITE"));
                p.setNationalite(rs.getString("nationalite"));
               
                p.setCodeGouvernorat(rs.getString("TCODGOU"));
                p.setCodePrison(rs.getString("TCODPR"));
                p.setNamePrison(rs.getString("libellePrison"));
                p.setCodeResidance(rs.getString("TCODRES"));
                p.setAnneeResidance(rs.getString("TANNRES"));
                p.setNumDetention(rs.getString("TCODDET"));
                p.setEventDate(rs.getString("TDATDR"));
                p.setType(rs.getString("TCODTYP"));

                String etat = rs.getString("TETAT");
                if ("I".equals(etat)) {
                    etat = "L";
                }
                p.setEtat(etat);

                if (rs.getInt("TNBRMU") == 0) {
                    p.setStatutResidance("E");
                } else {
                    p.setStatutResidance("M");
                }
                p.setBirthDate(rs.getString("birth_date"));
                p.setMotherName(rs.getString("mother_name"));
                p.setMaternalGrandmotherName(rs.getString("maternal_grandmother_name"));
                p.setSex(rs.getString("sex"));
                p.setAdresse(rs.getString("adresse"));
                p.setPartenaire(rs.getString("partenaire"));
                p.setNombreEnfant(rs.getString("nombre_enfant"));
                p.setProfession(rs.getString("profession"));
                p.setNiveauCulturel(rs.getString("niveau_culturel"));
           
                return p;
            });

            return Optional.ofNullable(prisoner);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Map<String, String> findEtatAndLibelleById(String id) {
        String sql = "SELECT DISTINCT ja.TETAT, na.LIBELLE_NATURE FROM tjugearret@LINK_PENALE ja, natureaffaire@LINK_PENALE na WHERE ja.TCODTAF = na.CODE_NATURE AND ja.TNUMIDE = ?";

        Map<String, String> resultat;

        try {
            resultat = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Map<String, String> map = new HashMap<>();
                map.put("etat", rs.getString("TETAT"));
                map.put("libelleNature", rs.getString("LIBELLE_NATURE"));
                return map;
            });
        } catch (EmptyResultDataAccessException ex) {
            // Gérez le cas où aucun résultat n'est trouvé en retournant null ou un Optional vide.
            return null;
        }

        // Si un résultat est trouvé, retournez le Map contenant les deux valeurs
        return resultat;
    }


    
    public List<PrisonerDto> findResidantPrisonersByLocation(String gouvernorat, String prison) {

        String sql = "SELECT  " + 
        		"    mv.*, " + 
        		"    ac.CHAMBRE_ID, " + 
        		"    c.name AS CHAMBRE_NOM, " + 
        		"    p.name AS PAVILLON_NOM, " + 
        		"    cp.name AS COMPLEXE_NOM, " + 
        		"    ctr.name AS CENTRE_NOM, " + 
        		"    pr.name AS PRISON_NOM " + 
        		"FROM MV_RESIDENCE_ACTIVE mv " + 
        		"LEFT JOIN ASSIGNMENT_CHAMBRE ac " + 
        		"    ON mv.TNUMIDE = ac.PRISONER_ID " + 
        		"   AND ac.CODE_GOUVERNORAT = ? " + 
        		"   AND ac.CODE_PRISON = ? " + 
         
    			"        		   AND SYSDATE >= ac.START_DATE " +
    			"        		   AND (ac.END_DATE IS NULL OR SYSDATE <= ac.END_DATE) "+
        		"LEFT JOIN CHAMBRE c " + 
        		"    ON ac.CHAMBRE_ID = c.ID " + 
        		"LEFT JOIN PAVILLON p " + 
        		"    ON c.PAVILLON_ID = p.ID " + 
        		"LEFT JOIN COMPLEXE cp " + 
        		"    ON p.COMPLEXE_ID = cp.ID " + 
        		"LEFT JOIN CENTRE ctr " + 
        		"    ON cp.CENTRE_ID = ctr.ID " + 
        		"LEFT JOIN PRISON pr " + 
        		"    ON ctr.PRISON_ID = pr.ID " + 
        		"WHERE mv.CODGOU = ? " + 
        		"  AND mv.CODPR = ? " + 
        		"ORDER BY mv.TDATDR DESC, TCODRES DESC" ;

        return jdbcTemplate.query(sql, new Object[]{gouvernorat, prison , gouvernorat, prison}, (rs, rowNum) -> {
            PrisonerDto p = new PrisonerDto();
            p.setIdPrisoner(rs.getString("TNUMIDE"));
            p.setNumDetention(rs.getString("TCODDET"));
            p.setFirstName(rs.getString("TPNOMA"));
            p.setLastName(rs.getString("TNOMA"));
            p.setFatherName(rs.getString("TPGPERA"));
            p.setGrandFatherName(rs.getString("TPPERA"));
            p.setCodeNationalite(rs.getString("CODE_NATIONALITE"));
            p.setNationalite(rs.getString("NATIONALITE"));
            p.setCodeGouvernorat(rs.getString("CODGOU"));
            p.setCodePrison(rs.getString("CODPR"));
            p.setNamePrison(rs.getString("LIBELLEPRISON"));
            p.setCodeResidance(rs.getString("TCODRES"));
            p.setAnneeResidance(rs.getString("TANNRES"));
            
        
           String[] parts = rs.getString("TDATDR").split(" ");
            p.setEventDate(parts[0]);
            
            p.setNombreMutation(rs.getString("TNBRMU"));
            // champs hiérarchiques
            p.setChambreNom(rs.getString("CHAMBRE_NOM"));
            p.setPavillonNom(rs.getString("PAVILLON_NOM"));
            p.setComplexeNom(rs.getString("COMPLEXE_NOM"));
            p.setCentreNom(rs.getString("CENTRE_NOM"));
            p.setPrisonNom(rs.getString("PRISON_NOM"));
            p.setPrisonOrig(rs.getString("LIBELLEPRISON_ORIG"));
           
            return p;
        });
    }


    
    public List<PrisonerDto> findPrisonersEnteringByPeriodAndLocation(
            String startDate, String endDate, String gouvernorat, String prison) {

//        String sql = "SELECT " +
//                "det.TNUMIDE, " +  // ajouté pour éviter l'erreur 'Nom de colonne non valide'
//               "det.TDATLIBE, " +
//                "motifLib.tlibmot as motifLiberation, "+
//
//                
//                "det.TDATDET, " +
//                "    det.TCODDET, " +
//                "det.TDATLIBE, " +
//                "ide.TPNOMA, " +
//                "ide.TPPERA, " +
//                "ide.TPGPERA, " +
//                "ide.TNOMA, " +
//                "ide.CODE_NATIONALITE, " +
//                "GETLIBELLE_NATIONALITE@LINK_PENALE(ide.CODE_NATIONALITE) AS NATIONALITE, " +
//                "COALESCE(res.TCODGOU, mut.TCODGOU) AS CODGOU, " +
//                "COALESCE(res.TCODPR, mut.TCODPR) AS CODPR, " +
//                "COALESCE(GETLIBELLEPRISON@LINK_PENALE(res.TCODGOU, res.TCODPR), " +
//                "         GETLIBELLEPRISON@LINK_PENALE(mut.TCODGOU, mut.TCODPR)) AS LIBELLEPRISON, " +
//                "COALESCE(res.TANNRES, mut.TANNRES) AS TANNRES, " +
//                "COALESCE(res.TCODRES, mut.TCODRES) AS TCODRES, " +
//                "   CASE  " + 
//                "        WHEN det.TDATLIBE IS NOT NULL THEN 'L'   " + 
//                "        ELSE COALESCE(res.TCLORES, 'F')         " + 
//                "    END AS STATUT "+
//                "FROM TDETENTION@LINK_PENALE det " +
//                "LEFT JOIN   tmotif@LINK_PENALE  motifLib on motifLib.tcodmot = det.tcodmot  "+
//                "LEFT JOIN TRESIDENCE@LINK_PENALE res " +
//                "  ON res.TNUMIDE = det.TNUMIDE AND res.TCODDET = det.TCODDET AND res.TDATDR = det.TDATDET " +
//                "LEFT JOIN TMUTATION@LINK_PENALE mut " +
//                "  ON det.TNUMIDE = mut.TNUMIDE AND det.TCODDET = mut.TCODDET AND mut.TCODMUT = '01' " +
//                "JOIN TIDENTITE@LINK_PENALE ide " +
//                "  ON ide.TNUMIDE = det.TNUMIDE " +
//                "WHERE TRUNC(det.TDATDET) BETWEEN TO_DATE(?, 'DD-MM-YYYY') AND TO_DATE(?, 'DD-MM-YYYY') " +
//                "AND COALESCE(res.TCODGOU, mut.TCODGOU) = ? " +
//                "AND COALESCE(res.TCODPR, mut.TCODPR) = ? " +
//                "ORDER BY det.TDATDET DESC";

    	String sql = "SELECT mv.* , " + 
    		 
    			"    c.name AS CHAMBRE_NOM, " + 
    			"    p.name AS PAVILLON_NOM, " + 
    			"    cp.name AS COMPLEXE_NOM, " + 
    			"    ctr.name AS CENTRE_NOM, " + 
    			"    pr.name AS PRISON_NOM " + 
    			"FROM MV_RESIDENCE_LAST_YEAR mv " + 
    			"LEFT JOIN ASSIGNMENT_CHAMBRE ac " + 
    			"    ON mv.TNUMIDE = ac.PRISONER_ID " + 
    			"   AND ac.CODE_GOUVERNORAT = ? " + 
    			"   AND ac.CODE_PRISON = ? " + 
    			"        		   AND mv.TDATDET >= ac.START_DATE " +
    			"        		   AND (ac.END_DATE IS NULL OR mv.TDATDET <= ac.END_DATE) "+
    			"LEFT JOIN CHAMBRE c " + 
    			"    ON ac.CHAMBRE_ID = c.ID " + 
    			"LEFT JOIN PAVILLON p " + 
    			"    ON c.PAVILLON_ID = p.ID " + 
    			"LEFT JOIN COMPLEXE cp " + 
    			"    ON p.COMPLEXE_ID = cp.ID " + 
    			"LEFT JOIN CENTRE ctr " + 
    			"    ON cp.CENTRE_ID = ctr.ID " + 
    			"LEFT JOIN PRISON pr " + 
    			"    ON ctr.PRISON_ID = pr.ID " + 
    			"     " + 
    			"  WHERE TRUNC( TDATDET) BETWEEN TO_DATE( ? , 'DD-MM-YYYY') AND TO_DATE( ? , 'DD-MM-YYYY')   " + 
    			"                AND CODGOU = ?   " + 
    			"                AND CODPR = ?  " + 
    			"                ORDER BY   TDATDET DESC , TCODRES DESC";
    	
        return jdbcTemplate.query(sql, new Object[]{  gouvernorat, prison,startDate, endDate,gouvernorat, prison }, (rs, rowNum) -> {
            PrisonerDto p = new PrisonerDto();
            p.setIdPrisoner(rs.getString("TNUMIDE")); // correspond maintenant au SELECT
            p.setNumDetention(rs.getString("TCODDET"));
            p.setFirstName(rs.getString("TPNOMA"));
            p.setLastName(rs.getString("TNOMA"));
            p.setFatherName(rs.getString("TPGPERA"));
            p.setGrandFatherName(rs.getString("TPPERA"));
            p.setCodeNationalite(rs.getString("CODE_NATIONALITE"));
            p.setNationalite(rs.getString("NATIONALITE"));
            p.setCodeGouvernorat(rs.getString("CODGOU"));
            p.setCodePrison(rs.getString("CODPR"));
            
            p.setNamePrison(rs.getString("LIBELLEPRISON"));
            p.setCodeResidance(rs.getString("TCODRES"));
            p.setAnneeResidance(rs.getString("TANNRES"));
            
            
            p.setDateLiberation(rs.getString("TDATLIBE"));
            p.setMotifLiberation(rs.getString("MOTIF_LIBERATION"));  
            p.setStatutFermer(rs.getString("STATUT"));             
        String[] parts = rs.getString("TDATDET").split(" ");

            
    		p.setEventDate(parts[0]);
     	      p.setChambreNom(rs.getString("CHAMBRE_NOM"));
               p.setPavillonNom(rs.getString("PAVILLON_NOM"));
               p.setComplexeNom(rs.getString("COMPLEXE_NOM"));
               p.setCentreNom(rs.getString("CENTRE_NOM"));
               p.setPrisonNom(rs.getString("PRISON_NOM"));
        
            return p;
        });
    }

    
    

    public List<PrisonerDto> findPrisonersMutatingByPeriodandLocation(
            String startDate, String endDate, String gouvernorat, String prison) {

    	String sql = "select mv.* , " + 
    			"	    c.name AS CHAMBRE_NOM,  " + 
    			"        		   p.name AS PAVILLON_NOM,  " + 
    			"                cp.name AS COMPLEXE_NOM,  " + 
    			"        		   ctr.name AS CENTRE_NOM,  " + 
    			"        		   pr.name AS PRISON_NOM  " + 
    			"from MV_MUT_ENTRANT mv " + 
    			" " + 
    			"LEFT JOIN ASSIGNMENT_CHAMBRE ac  " + 
    			"        		    ON mv.TNUMIDE = ac.PRISONER_ID  " + 
    			"        		   AND ac.CODE_GOUVERNORAT = ?  " + 
    			"        		   AND ac.CODE_PRISON = ?  " + 
      			"        		   AND mv.TDATFMU >= ac.START_DATE " +
    			"        		   AND (ac.END_DATE IS NULL OR mv.TDATFMU <= ac.END_DATE) "+
    			"        		LEFT JOIN CHAMBRE c  " + 
    			"        		    ON ac.CHAMBRE_ID = c.ID  " + 
    			"        		LEFT JOIN PAVILLON p  " + 
    			"        		    ON c.PAVILLON_ID = p.ID  " + 
    			"        		LEFT JOIN COMPLEXE cp   " + 
    			"        		    ON p.COMPLEXE_ID = cp.ID  " + 
    			"        		LEFT JOIN CENTRE ctr  " + 
    			"        		    ON cp.CENTRE_ID = ctr.ID  " + 
    			"        		LEFT JOIN PRISON pr  " + 
    			"        		    ON ctr.PRISON_ID = pr.ID  " + 
    			"                 " + 
    			" WHERE TRUNC(mv.TDATFMU) BETWEEN TO_DATE( ? , 'DD-MM-YYYY') AND TO_DATE( ? , 'DD-MM-YYYY')  " + 
    			"                AND mv.TCODGOUDES = ? " + 
    			"                AND mv.TCODPRDES = ?  " + 
    			"                ORDER BY mv.TDATFMU DESC, mv.TCODRES DESC";

        return jdbcTemplate.query(sql, new Object[]{gouvernorat, prison, startDate, endDate, gouvernorat, prison}, (rs, rowNum) -> {
            PrisonerDto p = new PrisonerDto();
            p.setIdPrisoner(rs.getString("TNUMIDE"));
            p.setNumDetention(rs.getString("TCODDET"));
            p.setFirstName(rs.getString("TPNOMA"));
            p.setLastName(rs.getString("TNOMA"));
            p.setFatherName(rs.getString("TPGPERA"));
            p.setGrandFatherName(rs.getString("TPPERA"));
            p.setCodeNationalite(rs.getString("NATIONALITE"));
            p.setCodeGouvernorat(rs.getString("CODGOU"));
            p.setCodePrison(rs.getString("CODPR"));
            p.setNamePrison(rs.getString("LIBELLEPRISON"));
            p.setCodeResidance(rs.getString("TCODRES"));
            p.setAnneeResidance(rs.getString("TANNRES"));
            p.setSituation(rs.getString("LIBELLEPRISON_ORIG"));
            // Date de mutation (événement principal)
            String dateMutation = rs.getString("TDATFMU");
            
            p.setDateLiberation(rs.getString("TDATLIBE"));
            p.setMotifLiberation(rs.getString("motifLiberation"));
            
            
            if (dateMutation != null && dateMutation.contains(" ")) {
                dateMutation = dateMutation.split(" ")[0];
            }
            p.setEventDate(dateMutation);
            p.setStatutFermer(rs.getString("statut"));
            
            p.setChambreNom(rs.getString("CHAMBRE_NOM"));
            p.setPavillonNom(rs.getString("PAVILLON_NOM"));
            p.setComplexeNom(rs.getString("COMPLEXE_NOM"));
            p.setCentreNom(rs.getString("CENTRE_NOM"));
            p.setPrisonNom(rs.getString("PRISON_NOM"));
     
            // Prison de destination
           

            return p;
        });
    }
    
    
    public List<PrisonerDto> findPrisonersMutatingSortantByPeriodandLocation(
            String startDate, String endDate, String gouvernorat, String prison) {

     	String sql = "select mv.* , " + 
    			"	    c.name AS CHAMBRE_NOM,  " + 
    			"        		   p.name AS PAVILLON_NOM,  " + 
    			"                cp.name AS COMPLEXE_NOM,  " + 
    			"        		   ctr.name AS CENTRE_NOM,  " + 
    			"        		   pr.name AS PRISON_NOM,  " + 
    			"        		   ac.END_DATE AS END_DATE  " + 
    			
               " from MV_MUT_SORTANT mv " +
        		" " + 
    			" LEFT JOIN ASSIGNMENT_CHAMBRE ac  " + 
    			"        		    ON mv.TNUMIDE = ac.PRISONER_ID  " + 
    			"        		   AND ac.CODE_GOUVERNORAT = ?  " + 
    			"        		   AND ac.CODE_PRISON = ?  " + 
    			"        		   AND mv.TDATFMU >= ac.START_DATE " +
    			"        		   AND (ac.END_DATE IS NULL OR mv.TDATFMU <= ac.END_DATE) "+

    			"        		LEFT JOIN CHAMBRE c  " + 
    			"        		    ON ac.CHAMBRE_ID = c.ID  " + 
    			"        		LEFT JOIN PAVILLON p  " + 
    			"        		    ON c.PAVILLON_ID = p.ID  " + 
    			"        		LEFT JOIN COMPLEXE cp   " + 
    			"        		    ON p.COMPLEXE_ID = cp.ID  " + 
    			"        		LEFT JOIN CENTRE ctr  " + 
    			"        		    ON cp.CENTRE_ID = ctr.ID  " + 
    			"        		LEFT JOIN PRISON pr  " + 
    			"        		    ON ctr.PRISON_ID = pr.ID  " + 
    			"                 " + 		
                "WHERE TRUNC(mv.TDATFMU) BETWEEN TO_DATE(?, 'DD-MM-YYYY') AND TO_DATE(?, 'DD-MM-YYYY') " +
                "AND mv.CODGOU = ? " +
                "AND mv.CODPR  = ? " +
                "ORDER BY mv.TDATFMU DESC, mv.TCODRES DESC";

        return jdbcTemplate.query(sql, new Object[]{gouvernorat, prison, startDate, endDate, gouvernorat, prison}, (rs, rowNum) -> {
            PrisonerDto p = new PrisonerDto();
            p.setIdPrisoner(rs.getString("TNUMIDE"));
            p.setNumDetention(rs.getString("TCODDET"));
            p.setFirstName(rs.getString("TPNOMA"));
            p.setLastName(rs.getString("TNOMA"));
            p.setFatherName(rs.getString("TPGPERA"));
            p.setGrandFatherName(rs.getString("TPPERA"));
            p.setCodeNationalite(rs.getString("NATIONALITE"));
            p.setCodeGouvernorat(rs.getString("CODGOU"));
            p.setCodePrison(rs.getString("CODPR"));
            p.setNamePrison(rs.getString("LIBELLEPRISON"));
            p.setCodeResidance(rs.getString("TCODRES"));
            p.setAnneeResidance(rs.getString("TANNRES"));
            p.setSituation(rs.getString("LIBELLEPRISON_DES"));
            // Date de mutation (événement principal)
            String dateMutation = rs.getString("TDATFMU");
            if (dateMutation != null && dateMutation.contains(" ")) {
                dateMutation = dateMutation.split(" ")[0];
            }
            p.setEventDate(dateMutation);
            p.setDateLiberation(rs.getString("TDATLIBE"));
            p.setMotifLiberation(rs.getString("motifLiberation"));
            p.setStatutFermer(rs.getString("statut"));
            // Prison de destination
            p.setChambreNom(rs.getString("CHAMBRE_NOM"));
            p.setPavillonNom(rs.getString("PAVILLON_NOM"));
            p.setComplexeNom(rs.getString("COMPLEXE_NOM"));
            p.setCentreNom(rs.getString("CENTRE_NOM"));
            p.setPrisonNom(rs.getString("PRISON_NOM"));
           p.setEndDate(rs.getString("END_DATE"));
            return p;
        });
    }

//    public List<PrisonerDto> findPrisonersMutatingByPeriodandLocation(String startDate, String endDate, String gouvernorat, String prison) {
//        String sql = "SELECT DISTINCT res.TNUMIDE, GETLIBELLEPRISON@LINK_PENALE(res.TCODGOU, res.TCODPR) as libellePrison, ide.TPNOMA, ide.TPPERA, ide.TPGPERA, ide.TNOMA , ide.CODE_NATIONALITE, " 
//        		+" res.tclores , res.tannres , res.tcodres ,res.TCODGOU, res.TCODPR , res.TDATDR  "
//                    + "FROM TRESIDENCE@LINK_PENALE res, TIDENTITE@LINK_PENALE ide " 
//                    + "WHERE res.TCLORES ='O' and res.TDATDR BETWEEN ? AND ? AND res.TCODGOU = ? AND res.TCODPR = ? AND res.TNUMIDE = ide.TNUMIDE"
//                    + " and res.ttypecr='R' and tdatfr is null and res.tnumide in(select tnumide from tdetention@LINK_PENALE where tnumide=res.tnumide and tcoddet=res.tcoddet and tnbrmu>0)";
//
//        return jdbcTemplate.query(sql, new Object[]{startDate, endDate, gouvernorat, prison}, (rs, rowNum) -> {
//        	PrisonerDto p = new PrisonerDto();
//            p.setIdPrisoner(rs.getString("TNUMIDE"));
//            p.setFirstName(rs.getString("TPNOMA"));
//            p.setLastName(rs.getString("TNOMA"));
//            p.setFatherName(rs.getString("TPGPERA"));
//            p.setGrandFatherName(rs.getString("TPPERA"));
//            p.setCodeNationalite(rs.getString("CODE_NATIONALITE"));
//            
//        	p.setCodeGouvernorat(rs.getString("TCODGOU"));
//    		p.setCodePrison(rs.getString("TCODPR"));
//    		p.setNamePrison(rs.getString("libellePrison"));
//    		p.setCodeResidance(rs.getString("TCODRES"));
//    		p.setAnneeResidance(rs.getString("TANNRES"));
//    		p.setStatutResidance(rs.getString("tclores"));
//    		
//          String[] parts = rs.getString("TDATDR").split(" ");
//
//            
//    		p.setEventDate(parts[0]);
// 
//            return p;
//        });
//    }
    
    
    public List<PrisonerDto> findPrisonersLeavingByPeriodandLocation(String startDate, String endDate, String gouvernorat, String prison) {

        String sql = "SELECT " +
                "det.TNUMIDE, " +  // ajouté pour éviter l'erreur 'Nom de colonne non valide'
                "det.TDATDET, " +
                "    det.TCODDET, " +
                "det.TDATLIBE, " +
                "ide.TPNOMA, " +
                "ide.TPPERA, " +
                "ide.TPGPERA, " +
                "ide.TNOMA, " +
                "ide.CODE_NATIONALITE, " +
                "GETLIBELLE_NATIONALITE@LINK_PENALE(ide.CODE_NATIONALITE) AS NATIONALITE, " +
                "COALESCE(res.TCODGOU, mut.TCODGOU) AS CODGOU, " +
                "COALESCE(res.TCODPR, mut.TCODPR) AS CODPR, " +
                "COALESCE(GETLIBELLEPRISON@LINK_PENALE(res.TCODGOU, res.TCODPR), " +
                "         GETLIBELLEPRISON@LINK_PENALE(mut.TCODGOU, mut.TCODPR)) AS LIBELLEPRISON, " +
                "COALESCE(res.TANNRES, mut.TANNRES) AS TANNRES, " +
                "COALESCE(res.TCODRES, mut.TCODRES) AS TCODRES, " +
                "  motifLib.tlibmot as motifLiberation, "+
                "   CASE  " + 
                "        WHEN det.TDATLIBE IS NOT NULL THEN 'L'   " + 
                "        ELSE COALESCE(res.TCLORES, 'F')         " + 
                "    END AS STATUT "+
                "FROM TDETENTION@LINK_PENALE det " +
                "LEFT JOIN TRESIDENCE@LINK_PENALE res " +
                "  ON res.TNUMIDE = det.TNUMIDE AND res.TCODDET = det.TCODDET AND res.TDATDR = det.TDATDET " +
                "LEFT JOIN TMUTATION@LINK_PENALE mut " +
                "  ON det.TNUMIDE = mut.TNUMIDE AND det.TCODDET = mut.TCODDET AND mut.TCODMUT = '01' " +
                "JOIN TIDENTITE@LINK_PENALE ide " +
                "  ON ide.TNUMIDE = det.TNUMIDE " +
                "LEFT JOIN   tmotif@LINK_PENALE motifLib on motifLib.tcodmot = det.tcodmot  "+
                "WHERE TRUNC(det.TDATLIBE) BETWEEN TO_DATE(?, 'DD-MM-YYYY') AND TO_DATE(?, 'DD-MM-YYYY') " +
                "AND COALESCE(res.TCODGOU, mut.TCODGOU) = ? " +
                "AND COALESCE(res.TCODPR, mut.TCODPR) = ? " +
                "ORDER BY det.TDATDET DESC";

        return jdbcTemplate.query(sql, new Object[]{startDate, endDate, gouvernorat, prison}, (rs, rowNum) -> {
            PrisonerDto p = new PrisonerDto();
            p.setIdPrisoner(rs.getString("TNUMIDE")); // correspond maintenant au SELECT
            p.setNumDetention(rs.getString("TCODDET"));
            p.setFirstName(rs.getString("TPNOMA"));
            p.setLastName(rs.getString("TNOMA"));
            p.setFatherName(rs.getString("TPGPERA"));
            p.setGrandFatherName(rs.getString("TPPERA"));
            p.setCodeNationalite(rs.getString("CODE_NATIONALITE"));
            p.setNationalite(rs.getString("NATIONALITE"));
            p.setCodeGouvernorat(rs.getString("CODGOU"));
            p.setCodePrison(rs.getString("CODPR"));
            
            p.setNamePrison(rs.getString("LIBELLEPRISON"));
            p.setCodeResidance(rs.getString("TCODRES"));
            p.setAnneeResidance(rs.getString("TANNRES"));
            
            p.setSituation(rs.getString("motifLiberation"));
        String[] parts = rs.getString("TDATLIBE").split(" ");

            
    		p.setEventDate(parts[0]);
    		
    		 p.setDateLiberation(rs.getString("TDATLIBE"));
             p.setMotifLiberation(rs.getString("motifLiberation"));
    		   p.setStatutFermer(rs.getString("statut"));
            return p;
        });
    }
    
    
    public List<PrisonerDto> findPrisonersExistingByLocationWithOutVisit(
            String gouvernorat,
            String prison,
            String nom,
            String prenom,
            String nomPere 
    ) {
//        String sql = "SELECT " +
//                "    ide.TNUMIDE, " +
//                "    ide.TPNOMA, " +
//                "    ide.TPPERA, " +
//                "    ide.TPGPERA, " +
//                "    ide.TNOMA, " +
//                              "    ide.CODE_NATIONALITE, " +
//              "    dt.tclores, " +
//              "    dt.tannres, " +
//               "    dt.tcodres, " +
//              "    dt.TCODGOU, " +
//               "    dt.TCODPR, " +
//              "    dt.TCODDET, " +
//                "    dt.TDATDR, " +
//                "    GETLIBELLEPRISON@LINK_PENALE(dt.TCODGOU, dt.TCODPR) AS libellePrison " +
//                "FROM TIDENTITE@LINK_PENALE ide " +
//                "JOIN TRESIDENCE@LINK_PENALE dt ON ide.TNUMIDE = dt.TNUMIDE " +
//                "WHERE dt.tclores = 'O' " +
//                "  AND dt.TCODGOU = ? " +
//                "  AND dt.TCODPR = ? " +
//                "  AND NOT EXISTS ( " +
//                "       SELECT 1 " +
//                "       FROM relationship_type rt " +
//                "       WHERE rt.prisoner_id = ide.TNUMIDE " +
//                "         AND rt.code_gouvernorat = ? " +
//                "         AND rt.code_prison = ? " +
//                "         AND rt.statut_residance = 'O' " +
//                "  ) " +
//                "  AND ide.TNUMIDE IN ( " +
//                "       SELECT simp.TNUMIDE FROM TIDENSIMP@LINK_PENALE simp " +
//                "       WHERE ( ? IS NULL OR simp.TNOMSA LIKE '%' || ? || '%' ) " +
//                "         AND ( ? IS NULL OR simp.TPNOMSA LIKE '%' || ? || '%') " +
//                "         AND ( ? IS NULL OR simp.TPPERSA LIKE '%' || ? || '%' ) " +
// 
//                "  ) " +
//                "ORDER BY dt.TDATDR";
String sql ="                     SELECT     mv.*   " + 
 
		"        		FROM MV_RESIDENCE_ACTIVE mv  " + 
 
		"          " + 
 
 
		"        		WHERE mv.CODGOU = ?  " + 
		"                         AND mv.CODPR = ?  " + 
		"AND NOT EXISTS (  " + 
		"                      SELECT 1  " + 
		"                     FROM relationship_type rt  " + 
		"                    WHERE rt.prisoner_id = mv.TNUMIDE  " + 
		"                       AND rt.code_gouvernorat = ?  " + 
		"                       AND rt.code_prison = ?  " + 
		"                       AND rt.statut_residance = 'O'  " + 
		"                 )  " + 
		"        		ORDER BY mv.TDATDR DESC, TCODRES DESC";
        return jdbcTemplate.query(
                sql,
                new Object[]{
                        gouvernorat,
                        prison,
                        gouvernorat,
                        prison,
//                        prenom, prenom,
//                        nom, nom,
//                      
//                        nomPere, nomPere 
 
                },
                (rs, rowNum) -> {
                    PrisonerDto p = new PrisonerDto();
                    p.setIdPrisoner(rs.getString("TNUMIDE"));
                    p.setFirstName(rs.getString("TPNOMA"));
                    p.setLastName(rs.getString("TNOMA"));
                    p.setFatherName(rs.getString("TPGPERA"));
                    p.setGrandFatherName(rs.getString("TPPERA"));
                   p.setCodeNationalite(rs.getString("CODE_NATIONALITE"));
                 	p.setCodeGouvernorat(rs.getString("CODGOU"));
             		p.setCodePrison(rs.getString("CODPR"));
             		p.setNamePrison(rs.getString("libellePrison"));
             		p.setCodeResidance(rs.getString("TCODRES"));
             		p.setAnneeResidance(rs.getString("TANNRES"));
//             		p.setStatutResidance(rs.getString("tclores"));
             		p.setNumDetention(rs.getString("TCODDET"));
                    String[] parts = rs.getString("TDATDR").split(" ");
                    p.setEventDate(parts[0]);

                    return p;
                }
        );
    }
    public List<PrisonerDto> findAllPrisonersExisting(
            String gouvernorat,
            String prison,
            String nom,
            String prenom,
            String nomPere,
            String idPrisoner , 
            String numeroEcrou
    ) {
        String sql = "SELECT " +
                "    ide.TNUMIDE, " +
                "    ide.TPNOMA, " +
                "    ide.TPPERA, " +
                "    ide.TPGPERA, " +
                "    ide.TNOMA, " +
                              "    ide.CODE_NATIONALITE, " +
              "    dt.tclores, " +
              "    dt.tannres, " +
               "    dt.tcodres, " +
              "    dt.TCODGOU, " +
               "    dt.TCODPR, " +
              "    dt.TCODDET, " +
                "    dt.TDATDR, " +
                "    GETLIBELLEPRISON@LINK_PENALE(dt.TCODGOU, dt.TCODPR) AS libellePrison " +
                "FROM TIDENTITE@LINK_PENALE ide " +
                "JOIN TRESIDENCE@LINK_PENALE dt ON ide.TNUMIDE = dt.TNUMIDE " +
                "WHERE dt.tclores = 'O' " +
                "  AND dt.TCODGOU = ? " +
                "  AND dt.TCODPR = ? " +
                "  AND NOT EXISTS ( " +
                "       SELECT 1 " +
                "       FROM relationship_type rt " +
                "       WHERE rt.prisoner_id = ide.TNUMIDE " +
                "         AND rt.code_gouvernorat = ? " +
                "         AND rt.code_prison = ? " +
                "         AND rt.statut_residance = 'O' " +
                "  ) " +
                "  AND ide.TNUMIDE IN ( " +
                "       SELECT simp.TNUMIDE FROM TIDENSIMP@LINK_PENALE simp " +
                "       WHERE ( ? IS NULL OR simp.TNOMSA LIKE '%' || ? || '%' ) " +
                "         AND ( ? IS NULL OR simp.TPNOMSA LIKE '%' || ? || '%') " +
                "         AND ( ? IS NULL OR simp.TPPERSA LIKE '%' || ? || '%' ) " +
 
                "  ) "
                + " AND (  ? IS NULL OR ide.TNUMIDE = ? ) "
                + " AND (  ? IS NULL OR dt.tcodres = ? ) " +
                "ORDER BY dt.TDATDR";

        return jdbcTemplate.query(
                sql,
                new Object[]{
                        gouvernorat,
                        prison,
                        gouvernorat,
                        prison,
                        prenom, prenom,
                        nom, nom,
                      
                        nomPere, nomPere ,
                         idPrisoner ,  idPrisoner,
                         numeroEcrou ,  numeroEcrou
 
                },
                (rs, rowNum) -> {
                    PrisonerDto p = new PrisonerDto();
                    p.setIdPrisoner(rs.getString("TNUMIDE"));
                    p.setFirstName(rs.getString("TPNOMA"));
                    p.setLastName(rs.getString("TNOMA"));
                    p.setFatherName(rs.getString("TPGPERA"));
                    p.setGrandFatherName(rs.getString("TPPERA"));
                   p.setCodeNationalite(rs.getString("CODE_NATIONALITE"));
                 	p.setCodeGouvernorat(rs.getString("TCODGOU"));
             		p.setCodePrison(rs.getString("TCODPR"));
             		p.setNamePrison(rs.getString("libellePrison"));
             		p.setCodeResidance(rs.getString("TCODRES"));
             		p.setAnneeResidance(rs.getString("TANNRES"));
             		p.setStatutResidance(rs.getString("tclores"));
             		p.setNumDetention(rs.getString("TCODDET"));
                    String[] parts = rs.getString("TDATDR").split(" ");
                    p.setEventDate(parts[0]);

                    return p;
                }
        );
    }
 
    
    public List<PrisonerDto> findAllPrisonersExistingFromPenal(
            String gouvernorat,
            String prison,
            String nom,
            String prenom,
            String nomPere,
            String idPrisoner , 
            String numeroEcrou
    ) {
        String sql = "SELECT " +
                "    ide.TNUMIDE, " +
                "    ide.TPNOMA, " +
                "    ide.TPPERA, " +
                "    ide.TPGPERA, " +
                "    ide.TNOMA, " +
                              "    ide.CODE_NATIONALITE, " +
              "    dt.tclores, " +
              "    dt.tannres, " +
               "    dt.tcodres, " +
              "    dt.TCODGOU, " +
               "    dt.TCODPR, " +
              "    dt.TCODDET, " +
                "    dt.TDATDR, " +
                "    GETLIBELLEPRISON@LINK_PENALE(dt.TCODGOU, dt.TCODPR) AS libellePrison " +
                "FROM TIDENTITE@LINK_PENALE ide " +
                "JOIN TRESIDENCE@LINK_PENALE dt ON ide.TNUMIDE = dt.TNUMIDE " +
                "WHERE dt.tclores = 'O' " +
                "  AND dt.TCODGOU = ? " +
                "  AND dt.TCODPR = ? " +
     
                "  AND ide.TNUMIDE IN ( " +
                "       SELECT simp.TNUMIDE FROM TIDENSIMP@LINK_PENALE simp " +
                "       WHERE ( ? IS NULL OR simp.TNOMSA LIKE '%' || ? || '%' ) " +
                "         AND ( ? IS NULL OR simp.TPNOMSA LIKE '%' || ? || '%') " +
                "         AND ( ? IS NULL OR simp.TPPERSA LIKE '%' || ? || '%' ) " +
 
                "  ) "
                + " AND (  ? IS NULL OR ide.TNUMIDE = ? ) "
                + " AND (  ? IS NULL OR dt.tcodres = ? ) " +
                "ORDER BY dt.TDATDR";

        return jdbcTemplate.query(
                sql,
                new Object[]{
                        gouvernorat,
                        prison,
                        
                        prenom, prenom,
                        nom, nom,
                      
                        nomPere, nomPere ,
                         idPrisoner ,  idPrisoner,
                         numeroEcrou ,  numeroEcrou
 
                },
                (rs, rowNum) -> {
                    PrisonerDto p = new PrisonerDto();
                    p.setIdPrisoner(rs.getString("TNUMIDE"));
                    p.setFirstName(rs.getString("TPNOMA"));
                    p.setLastName(rs.getString("TNOMA"));
                    p.setFatherName(rs.getString("TPGPERA"));
                    p.setGrandFatherName(rs.getString("TPPERA"));
                   p.setCodeNationalite(rs.getString("CODE_NATIONALITE"));
                 	p.setCodeGouvernorat(rs.getString("TCODGOU"));
             		p.setCodePrison(rs.getString("TCODPR"));
             		p.setNamePrison(rs.getString("libellePrison"));
             		p.setCodeResidance(rs.getString("TCODRES"));
             		p.setAnneeResidance(rs.getString("TANNRES"));
             		p.setStatutResidance(rs.getString("tclores"));
             		p.setNumDetention(rs.getString("TCODDET"));
                    String[] parts = rs.getString("TDATDR").split(" ");
                    p.setEventDate(parts[0]);

                    return p;
                }
        );
    }
    
    
    public List<PrisonDto> findAllPrisons ( ) {
        String sql = "select CODE_GOUVERNORAT, CODE_PRISON,LIBELLE_PRISON  from prison@LINK_PENALE";

        return jdbcTemplate.query(sql, new Object[]{ }, (rs, rowNum) -> {
        	PrisonDto prison = new PrisonDto();
        	prison.setCodeGouvernorat(rs.getString("CODE_GOUVERNORAT")); 
        	prison.setCodePrison(rs.getString("CODE_PRISON"));
        	prison.setName(rs.getString("LIBELLE_PRISON"));
            
            return prison;
        });
    }
    public PrisonDto findPrisonByCode(String codeGouvernorat, String codePrison) {
        String sql = "SELECT CODE_GOUVERNORAT, CODE_PRISON, LIBELLE_PRISON FROM prison@LINK_PENALE " +
                     "WHERE CODE_GOUVERNORAT = ? AND CODE_PRISON = ?";

        List<PrisonDto> prisons = jdbcTemplate.query(sql, new Object[]{codeGouvernorat, codePrison}, (rs, rowNum) -> {
            PrisonDto prison = new PrisonDto();
            prison.setCodeGouvernorat(rs.getString("CODE_GOUVERNORAT"));
            prison.setCodePrison(rs.getString("CODE_PRISON"));
            prison.setName(rs.getString("LIBELLE_PRISON"));

            return prison;
        });

        if (prisons.isEmpty()) {
            // Aucune prison trouvée
            return null;
        } else {
            // Retourne le premier élément de la liste (la première prison trouvée)
            return prisons.get(0);
        }
    }

    
//    , rela.libelle_statut_residance = 'Mutation'
			public void updateRelationshipTypeStatutResidance() {
            
				String updateSqlMutation = "UPDATE relationship_type rela "
		                + "SET rela.statut_residance = 'F' "
		                + "WHERE rela.prisoner_id IN ("
		                + "    SELECT DISTINCT res.TNUMIDE as prisoner_id "
		                + "    FROM TRESIDENCE@LINK_PENALE res, relationship_type rela "
		                + "    WHERE res.TCLORES = 'O' "
		                + "    AND (rela.statut_residance = 'O' OR rela.statut_residance = 'E' OR rela.statut_residance = 'M' ) "
		                + "    AND rela.prisoner_id = res.TNUMIDE "
		                + "    AND res.TCODRES <> rela.code_residance"
		                + "    AND ( res.TCODGOU <> rela.code_gouvernorat "
		                + "    OR res.TCODPR <> rela.code_prison ) "
		                + ")"
		                + "AND (rela.statut_residance = 'O' OR rela.statut_residance = 'E' OR rela.statut_residance = 'M' )";
			                 	     
	          	jdbcTemplate.update(updateSqlMutation);
	          	
	          	String reqLibiration = "UPDATE relationship_type rela "
		                + "SET rela.statut_residance = 'F' "
		                + "WHERE rela.prisoner_id IN ( " 
	          			
                        + "    SELECT DISTINCT res.TNUMIDE as prisoner_id "
	                    + "FROM TRESIDENCE@LINK_PENALE res, TIDENTITE@LINK_PENALE ide, TDETENTION@LINK_PENALE td, relationship_type r "
	                    + "WHERE res.tclores = 'F' "
	                    + "AND res.TDATFR = td.tdatlibe "
	                    + "AND res.TNUMIDE = ide.TNUMIDE "
	                    + "AND res.TNUMIDE = td.TNUMIDE "
	                    + "AND res.tcoddet = td.tcoddet "
	                    + "AND r.prisoner_id = ide.TNUMIDE "
	                    + "AND r.statut_residance='O' "
	                    + "AND r.num_detention = td.TCODDET "
	                    + "AND td.tdatlibe is not null "
	                    + ")"
		                + "AND (rela.statut_residance = 'O' OR rela.statut_residance = 'E' OR rela.statut_residance = 'M' )";;
	          	
	         	jdbcTemplate.update(reqLibiration);


               }
         }
