package com.cgpr.visitApp.repository;

import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.model.Visitor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RelationshipTypeRepositoryCustomImpl implements RelationshipTypeRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public RelationshipTypeRepositoryCustomImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<RelationshipType> findByTimeDayPrisonWithOptionalNames(
            String time, String day, String codeGouvernorat, String codePrison,
            String nom, String prenom, String nomPere ,String idPrisoner, String numeroEcrou) {

//    	
//    	   sql.append("SELECT rt.*, p.TCODRES , p.TANNRES , p.TCODDET, ")
//           .append("p.TNUMIDE AS PRISONER_ID, p.TPNOMA AS PRISONER_FIRSTNAME, ")
//           .append("p.TPGPERA AS PRISONER_FATHERNAME, p.TPPERA AS PRISONER_GRANDFATHER, p.TNOMA AS PRISONER_LASTNAME, ")
//           .append("v.ID_VISITOR AS VISITOR_ID, v.FIRST_NAME AS VISITOR_FIRSTNAME, ")
//           .append("v.FATHER_NAME AS VISITOR_FATHERNAME, v.GRAND_FATHER_NAME AS VISITOR_GRANDFATHER, v.LAST_NAME AS VISITOR_LASTNAME, v.PHONE AS VISITOR_PHONE ")
//           .append("FROM MV_RESIDENCE_ACTIVE p ")
//           .append("left JOIN RELATIONSHIP_TYPE rt ON p.TNUMIDE = rt.PRISONER_ID ")
//           .append("left JOIN VISITOR v ON v.ID_VISITOR = rt.VISITOR_ID ")
//           .append("WHERE rt.CODE_GOUVERNORAT = ? ")
//           .append("AND rt.CODE_PRISON = ? ")
//           .append("AND TRIM(rt.STATUT_RESIDANCE) = 'O' ");
//    	   
    	   
    	   
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT rt.*, p.TCODRES , p.TANNRES , p.TCODDET, ")
           .append("p.TNUMIDE AS PRISONER_ID, p.TPNOMA AS PRISONER_FIRSTNAME, ")
           .append("p.TPGPERA AS PRISONER_FATHERNAME, p.TPPERA AS PRISONER_GRANDFATHER, p.TNOMA AS PRISONER_LASTNAME, ")
           .append("v.ID_VISITOR AS VISITOR_ID, v.FIRST_NAME AS VISITOR_FIRSTNAME, ")
           .append("v.FATHER_NAME AS VISITOR_FATHERNAME, v.GRAND_FATHER_NAME AS VISITOR_GRANDFATHER, v.LAST_NAME AS VISITOR_LASTNAME, v.PHONE AS VISITOR_PHONE ")
           .append("FROM MV_RESIDENCE_ACTIVE p ")
           .append("left JOIN RELATIONSHIP_TYPE rt ON p.TNUMIDE = rt.PRISONER_ID  and TRIM(rt.STATUT_RESIDANCE) = 'O'")
           .append("left JOIN VISITOR v ON v.ID_VISITOR = rt.VISITOR_ID ")
           .append("WHERE rt.CODE_GOUVERNORAT = ? ")
           .append("AND rt.CODE_PRISON = ? ") ;

        List<Object> params = new ArrayList<>();
        params.add(codeGouvernorat);
        params.add(codePrison);

        // Filtres optionnels pour Prisoner
        if (nom != null && !nom.isEmpty()) {
            sql.append(" AND UPPER(TRIM(p.TPNOMA)) LIKE ? ");
            params.add("%" + nom.toUpperCase() + "%");
        }
        if (prenom != null && !prenom.isEmpty()) {
            sql.append(" AND UPPER(TRIM(p.TPGPERA)) LIKE ? ");
            params.add("%" + prenom.toUpperCase() + "%");
        }
        if (nomPere != null && !nomPere.isEmpty()) {
            sql.append(" AND UPPER(TRIM(p.TNOMA)) LIKE ? ");
            params.add("%" + nomPere.toUpperCase() + "%");
        }

       

        if (time != null && !time.isEmpty()) {
            sql.append(" AND rt.TIME = ? ");
            params.add(time);
        }
        if (day != null && !day.isEmpty()) {
            sql.append(" AND rt.DAY = ? ");
            params.add(day);
        }

        sql.append(" ORDER BY rt.DAY, rt.TIME");

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
            RelationshipType rt = new RelationshipType();

            // RelationshipType
            rt.setId(rs.getLong("ID"));
            rt.setCodeGouvernorat(rs.getString("CODE_GOUVERNORAT"));
            rt.setCodePrison(rs.getString("CODE_PRISON"));
            rt.setStatutResidance(rs.getString("STATUT_RESIDANCE"));
            rt.setDay(rs.getString("DAY"));
            rt.setTime(rs.getString("TIME"));
           
           
            rt.setCentre(rs.getString("CENTRE"));
            rt.setSalle(rs.getString("SALLE"));
            rt.setEventDate(rs.getString("EVENT_DATE"));
            rt.setStatutSMS(rs.getString("STATUTSMS"));

            // Prisoner
            Prisoner prisoner = new Prisoner();
            prisoner.setIdPrisoner(rs.getString("PRISONER_ID"));
            prisoner.setFirstName(rs.getString("PRISONER_FIRSTNAME"));
            prisoner.setFatherName(rs.getString("PRISONER_FATHERNAME"));
            prisoner.setGrandFatherName(rs.getString("PRISONER_GRANDFATHER"));
            prisoner.setLastName(rs.getString("PRISONER_LASTNAME"));
          
            rt.setNumDetention(rs.getString("TCODDET"));
            rt.setCodeResidance(rs.getString("TCODRES"));
            rt.setAnneeResidance(rs.getString("TANNRES"));
            
            
            rt.setPrisoner(prisoner);

            // Visitor
            Visitor visitor = new Visitor();
            visitor.setIdVisitor(rs.getLong("VISITOR_ID"));
            visitor.setFirstName(rs.getString("VISITOR_FIRSTNAME"));
            visitor.setFatherName(rs.getString("VISITOR_FATHERNAME"));
            visitor.setGrandFatherName(rs.getString("VISITOR_GRANDFATHER"));
            visitor.setLastName(rs.getString("VISITOR_LASTNAME"));
            visitor.setPhone(rs.getString("VISITOR_PHONE"));
            rt.setVisitor(visitor);

            return rt;
        });
    }


}
