package com.getir.reading.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class Orders implements Serializable {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, unique = true, updatable = false, length = 36)
    private String id;

    @Column(name = "order_number", nullable = false, length = 8)
    private long orderNumber;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    private Date createdDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }
}
