package org.hillel.demo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="streets")
@Data
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

}
