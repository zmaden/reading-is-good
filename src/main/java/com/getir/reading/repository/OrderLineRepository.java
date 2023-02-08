package com.getir.reading.repository;

import com.getir.reading.model.OrderLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, String> {

    List<OrderLine> getOrderLineByOrderNumber(long orderNumber);

    Page<OrderLine> getOrderLineByEmail(String email, Pageable pageable);

    List<OrderLine> getOrderLineByCreatedDateBetween(Date startDate, Date endDate);

    @Query("select new map(to_char(o.createdDate, 'Month') as monthName, count(*) as orderCount, sum(o.quantity) as totalQuantity, sum(o.amount) as totalAmount) from OrderLine o where o.email = :email group by to_char(o.createdDate, 'Month')")
    List<Map<String, Object>> getMonthlyReport(String email);
}
