package org.hillel.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="cities")
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

}
