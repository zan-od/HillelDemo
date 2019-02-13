package org.hillel.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="addresses")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="country_id")
    @NotNull
    private Country country;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="city_id")
    @NotNull
    private City city;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="street_id")
    @NotNull
    private Street street;

}
