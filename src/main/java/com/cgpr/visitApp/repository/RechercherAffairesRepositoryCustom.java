package com.cgpr.visitApp.repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.cgpr.visitApp.config.SQLLoader;
import com.cgpr.visitApp.dto.PenalSyntheseDto;



@Repository
public class RechercherAffairesRepositoryCustom {
	  @PersistenceContext
	    private EntityManager entityManager;
	
    public PenalSyntheseDto rechercherPenalSyntheseDetenu(String tnumide, String tcoddet) {
    	
        try {
            // --- 1. Charger les données principales de synthèse ---
            String sql1 = SQLLoader.loadSQL("sql/penalSyntheseDetenu.sql");
            Query query1 = entityManager.createNativeQuery(sql1);
            query1.setParameter("tnumide", tnumide);
            query1.setParameter("tcoddet", tcoddet);

            @SuppressWarnings("unchecked")
            List<Object[]> results1 = query1.getResultList();

            PenalSyntheseDto penalSyntheseDto = new PenalSyntheseDto();

            for (Object[] row : results1) {
                String tnumseqaffStr = row[0] != null ? row[0].toString() : null;
                penalSyntheseDto.setTnumseqaff(tnumseqaffStr);
                penalSyntheseDto.setTribunal((String) row[1]);
                penalSyntheseDto.setNumAffaire((String) row[2]);
                penalSyntheseDto.setAccusation((String) row[3]);
                penalSyntheseDto.setDateJugement((String) row[4]);
                penalSyntheseDto.setTypeAffaire((String) row[5]);
            }

            // --- 2. Charger le total des affaires ---
            String sql2 = SQLLoader.loadSQL("sql/get_total_affaire.sql");
            Query query2 = entityManager.createNativeQuery(sql2);
            query2.setParameter("tnumide", tnumide);
            query2.setParameter("tcoddet", tcoddet);

            Object result = query2.getSingleResult();
            if (result != null) {
                int totalCount = ((BigDecimal) result).intValue();
                penalSyntheseDto.setTotalCount(totalCount);
                System.err.println("Total count: " + totalCount);
            }
            System.err.println("penalSyntheseDto  : " + penalSyntheseDto.toString());
             return penalSyntheseDto;

        } catch (IOException e) {
            throw new RuntimeException("Erreur de chargement du fichier SQL", e);
        }
    }
}
