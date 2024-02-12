package com.flab.goodchoicepoint.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flab.goodchoicepoint.domain.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    @Query(value = "select * from point where status = 'VALID'", nativeQuery = true)
    List<Point> findExpirPoints();

}
