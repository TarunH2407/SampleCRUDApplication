package com.example.crudapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column
    private Long itemId;

    @Column
    private String status;

    @Column
    private Long userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
