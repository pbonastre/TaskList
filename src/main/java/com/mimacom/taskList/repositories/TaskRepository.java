package com.mimacom.taskList.repositories;

import com.mimacom.taskList.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {


  @Modifying
  @Query("Update Task t SET t.finish=:finish WHERE t.id=:id")
  public void updateFinish(@Param("id") Long id, @Param("finish") Date finish);



}
