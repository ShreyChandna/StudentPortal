package com.student.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.student.entities.Attendance;
import com.student.entities.AttendanceDetails;

public interface AttendanceDetailsRepository extends JpaRepository<AttendanceDetails,Integer> {
	@Query("from AttendanceDetails as e where e.attendance.subid= :subId")
	public List<AttendanceDetails> findAttendanceBysubId(@Param("subId") Integer subid);
}
