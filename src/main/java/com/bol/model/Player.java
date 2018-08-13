package com.bol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String name;
    private PlayerTurn turn;

    @OneToMany(targetEntity = Pit.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "pits", referencedColumnName = "id")
    @OrderColumn
    private List<Pit> pits;

    @OneToOne
    @JoinColumn(name="store_id", referencedColumnName = "id")
    private Store store;
}
