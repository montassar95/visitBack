//package com.cgpr.visitApp.repository.repositorySanction;
//
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.cgpr.visitApp.model.*;
//
//public interface ChambreRepository extends JpaRepository<Chambre , Long> {
//	
//	
//    // 🔍 Trouver les chambres par type, gouvernorat et prison
//    List<Chambre> findByTypeAndCodeGouvernoratAndCodePrison(
//            String type,
//            String codeGouvernorat,
//            String codePrison
//    );
//}
