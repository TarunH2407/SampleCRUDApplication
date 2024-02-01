package com.example.crudapp.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "Items")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private int qty;

    @Column
    private String type;

}
