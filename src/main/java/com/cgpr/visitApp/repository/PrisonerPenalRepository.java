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

    public Optional<PrisonerDto> findById(String id) {
    //	dt.tclores='O' and 
        String sql = "SELECT DISTINCT  ide.TNUMIDE, ide.TPNOMA, ide.TPPERA, ide.TPGPERA, ide.TNOMA, ide.CODE_NATIONALITE, "
        		+"res.tclores , res.tannres , res.tcodres , res.TCODGOU, res.TCODPR, res.TCODDET,  ja.TETAT, ja.TCODTYP , " 
        		+ "GETLIBELLEPRISON@LINK_PENALE(res.TCODGOU, res.TCODPR) as libellePrison , TNBRMU , res.TDATDR  FROM TIDENTITE@LINK_PENALE ide, tjugearret@LINK_PENALE  ja, "
        		+ "TRESIDENCE@LINK_PENALE res, TDETENTION@LINK_PENALE dt WHERE res.tclores='O' "
        		+ " and ide.TNUMIDE = res.TNUMIDE AND ide.TNUMIDE = ? and dt.TNUMIDE = res.TNUMIDE and dt.TCODDET = res.TCODDET and ide.TNUMIDE = ja.TNUMIDE ";
          
        PrisonerDto prisonerDto = null;

        try {
        	prisonerDto =  jdbcTemplate.queryForObject (sql, new Object[]{id}, (rs, rowNum) -> {
        		PrisonerDto p = new PrisonerDto();
        		p.setIdPrisoner(rs.getString("TNUMIDE"));
        		  p.setFirstName(rs.getString("TPNOMA"));
                  p.setLastName(rs.getString("TNOMA"));
                  p.setFatherName(rs.getString("TPPERA"));
                  p.setGrandFatherName(rs.getString("TPGPERA"));
        		p.setGrandFatherName(rs.getString("TNOMA"));
        		p.setCodeGouvernorat(rs.getString("TCODGOU"));
        		p.setCodePrison(rs.getString("TCODPR"));
        		p.setNamePrison(rs.getString("libellePrison"));
        		p.setCodeResidance(rs.getString("TCODRES"));
        		p.setAnneeResidance(rs.getString("TANNRES"));
        		p.setStatutResidance(rs.getString("tclores"));
        		p.setNumDetention(rs.getString("TCODDET"));
        		p.setCodeNationalite(rs.getString("CODE_NATIONALITE"));
        		String etat=rs.getString("TETAT");
        		if(etat == null ) {
        			etat="L";
        		} 
        		p.setEtat(etat);
        		p.setType(rs.getString("TCODTYP"));
        		 String[] parts = rs.getString("TDATDR").split(" ");

                 
         		p.setEventDate(parts[0]);
         		
         		if(rs.getInt("TNBRMU") == 0  ) {
         			p.setStatutResidance("E");
         		}
         		else {
         			p.setStatutResidance("M");
         		}
                return p;
            
            });
        } catch (EmptyResultDataAccessException ex) {
            // Gérez le cas où aucun détenu n'est trouvé en retournant un Optional vide.
            return Optional.empty();
        }

        // Si le détenu est trouvé, retournez-le dans un Optional.
       
        return Optional.ofNullable(prisonerDto);
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



    
    
    public List<PrisonerDto> findPrisonersEnteringByPeriodandLocation(String startDate, String endDate, String gouvernorat, String prison) {
    	String sql = "SELECT DISTINCT res.TNUMIDE, GETLIBELLEPRISON@LINK_PENALE(res.TCODGOU, res.TCODPR) as libellePrison, ide.TPNOMA, ide.TPPERA, ide.TPGPERA, ide.TNOMA, ide.CODE_NATIONALITE,  " +
                "res.tclores, res.tannres, res.tcodres, res.TCODGOU, res.TCODPR , res.TDATDR " +
                "FROM TRESIDENCE@LINK_PENALE res, TIDENTITE@LINK_PENALE ide " +
                "WHERE res.TCLORES = 'O' " +
                "AND res.TDATDR BETWEEN ? AND ? " +
                "AND res.TCODGOU = ? " +
                "AND res.TCODPR = ? " +
                "AND res.TNUMIDE = ide.TNUMIDE " +
                "AND res.ttypecr = 'R' " +
                "AND res.tdatfr IS NULL " +
                "AND res.tnumide IN (SELECT tnumide FROM tdetention@LINK_PENALE WHERE tnumide = res.tnumide AND tcoddet = res.tcoddet AND tnbrmu = 0)";

        return jdbcTemplate.query(sql, new Object[]{startDate, endDate, gouvernorat, prison}, (rs, rowNum) -> {
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
    		 // Divisez la chaîne en deux parties en utilisant l'espace comme séparateur
            String[] parts = rs.getString("TDATDR").split(" ");

            
    		p.setEventDate(parts[0]);
//            prisoner.setMinAdlt(rs.getString("libellePrison"));
            return p;
        });
    }
    
    public List<PrisonerDto> findPrisonersMutatingByPeriodandLocation(String startDate, String endDate, String gouvernorat, String prison) {
        String sql = "SELECT DISTINCT res.TNUMIDE, GETLIBELLEPRISON@LINK_PENALE(res.TCODGOU, res.TCODPR) as libellePrison, ide.TPNOMA, ide.TPPERA, ide.TPGPERA, ide.TNOMA , ide.CODE_NATIONALITE, " 
        		+" res.tclores , res.tannres , res.tcodres ,res.TCODGOU, res.TCODPR , res.TDATDR  "
                    + "FROM TRESIDENCE@LINK_PENALE res, TIDENTITE@LINK_PENALE ide " 
                    + "WHERE res.TCLORES ='O' and res.TDATDR BETWEEN ? AND ? AND res.TCODGOU = ? AND res.TCODPR = ? AND res.TNUMIDE = ide.TNUMIDE"
                    + " and res.ttypecr='R' and tdatfr is null and res.tnumide in(select tnumide from tdetention@LINK_PENALE where tnumide=res.tnumide and tcoddet=res.tcoddet and tnbrmu>0)";

        return jdbcTemplate.query(sql, new Object[]{startDate, endDate, gouvernorat, prison}, (rs, rowNum) -> {
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
    		
          String[] parts = rs.getString("TDATDR").split(" ");

            
    		p.setEventDate(parts[0]);
//            prisoner.setMinAdlt(rs.getString("libellePrison"));
            return p;
        });
    }
    
    
    public List<PrisonerDto> findPrisonersLeavingByPeriodandLocation(String startDate, String endDate, String gouvernorat, String prison) {
    	String sql = "SELECT DISTINCT res.TNUMIDE, GETLIBELLEPRISON@LINK_PENALE(TCODGOU, TCODPR) as libellePrison, " +
                "res.TDATDR, res.TDATFR, ide.TPNOMA, ide.TPPERA, ide.TPGPERA, ide.TNOMA, ide.CODE_NATIONALITE, res.tclores , res.tannres , res.tcodres , res.TCODGOU, res.TCODPR ,  td.tdatlibe  " +
                "FROM TRESIDENCE@LINK_PENALE res, TIDENTITE@LINK_PENALE ide,TDETENTION@LINK_PENALE td " +
                "WHERE res.tclores = 'F' " +
               "AND res.TDATFR = td.tdatlibe " +
                "AND res.TCODGOU = ? " +
                "AND res.TCODPR = ? " +
                "AND res.TNUMIDE = ide.TNUMIDE" +
                " AND res.TNUMIDE = td.TNUMIDE "+
                " AND res.tcoddet = td.tcoddet "+
                " and td.tdatlibe BETWEEN ? AND ?";

        return jdbcTemplate.query(sql, new Object[]{ gouvernorat, prison, startDate, endDate}, (rs, rowNum) -> {
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
    		
    		 String[] parts = rs.getString("tdatlibe").split(" ");

             
     		p.setEventDate(parts[0]);
         //   prisoner.setMinAdlt(rs.getString("libellePrison"));
            return p;
        });
    }
    
    
    public List<PrisonerDto> findPrisonersExistingByLocationWithOutVisit  (String gouvernorat, String prison) {
//    	 String sql =  "SELECT DISTINCT " +
//                 "ide.TNUMIDE, " +
//                 "ide.TPNOMA, " +
//                 "ide.TPPERA, " +
//                 "ide.TPGPERA, " +
//                 "ide.TNOMA, " +
//                 "dt.tclores, " +
//                 "dt.tannres, " +
//                 "dt.tcodres, " +
//                 "dt.TCODGOU, " +
//                 "dt.TCODPR, " +
//                 "dt.TCODDET, " +
//                 "GETLIBELLEPRISON@LINK_PENALE(dt.TCODGOU, dt.TCODPR) as libellePrison " +
//                 "FROM " +
//                 "TIDENTITE@LINK_PENALE ide " +
//                 "JOIN " +
//                 "TRESIDENCE@LINK_PENALE dt ON ide.TNUMIDE = dt.TNUMIDE " +
//                 "LEFT JOIN " +
//                 "relationship_type rt ON ide.TNUMIDE = rt.prisoner_id " +
//                 "AND rt.code_gouvernorat = ? " +
//                 "AND rt.code_prison = ? " +
//                 "AND rt.statut_residance = 'O'  " +
//                 "WHERE " +
//                 "dt.tclores = 'O' " +
//                 "AND dt.TCODGOU = ? " +
//                 "AND dt.TCODPR = ? " ;

String sql = "SELECT DISTINCT " + 
		"    ide.TNUMIDE, " + 
		"    ide.TPNOMA," + 
		"    ide.TPPERA," + 
		"    ide.TPGPERA," + 
		"    ide.TNOMA," + 
		"    ide.CODE_NATIONALITE, "+
		"    dt.tclores," + 
		"    dt.tannres," + 
		"    dt.tcodres," + 
		"    dt.TCODGOU," + 
		"    dt.TCODPR," + 
		"    dt.TCODDET, " + 
		"    dt.TDATDR,  "+
	     
		"    GETLIBELLEPRISON@LINK_PENALE(dt.TCODGOU, dt.TCODPR) as libellePrison " + 
		"FROM " + 
		"    TIDENTITE@LINK_PENALE ide " + 
		"JOIN " + 
		"    TRESIDENCE@LINK_PENALE dt ON ide.TNUMIDE = dt.TNUMIDE " + 
		"  " + 
		"WHERE " + 
		"    dt.tclores = 'O' " + 
		"    AND dt.TCODGOU = ? " + 
		"    AND dt.TCODPR = ? and ide.TNUMIDE not in (  " + 
		"       select rt.prisoner_id  from relationship_type rt   " + 
		"        where  rt.code_gouvernorat = ? " + 
		"        AND rt.code_prison = ? " + 
		"        AND rt.statut_residance = 'O') "+
		" ORDER BY dt.TDATDR";
		
        return jdbcTemplate.query(sql, new Object[]{  gouvernorat, prison, gouvernorat, prison }, (rs, rowNum) -> {
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
          
         //   prisoner.setMinAdlt(rs.getString("libellePrison"));
            return p;
        });
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
