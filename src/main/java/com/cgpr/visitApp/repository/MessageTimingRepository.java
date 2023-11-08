package com.cgpr.visitApp.repository;

 

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cgpr.visitApp.model.MessageTiming;
import com.cgpr.visitApp.model.Visitor;
 
@Repository
public interface  MessageTimingRepository extends JpaRepository<MessageTiming, Long> {
  
	 MessageTiming findByTypeOfMsgAndActivatedIsTrue(String typeOfMsg);
	 
	 List<MessageTiming> findByTypeOfMsg(String typeOfMsg);
	 
	 @Query("SELECT mt FROM MessageTiming mt WHERE mt.typeOfMsg = :typeOfMsg " +
	           "AND mt.id = (SELECT MAX(mt2.id) FROM MessageTiming mt2 WHERE mt2.typeOfMsg = :typeOfMsg)")
	    Optional<MessageTiming> findMaxIdByTypeOfMsg(@Param("typeOfMsg") String typeOfMsg);
}
