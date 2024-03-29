package com.example.moviematchbackend.models.entity;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pereche")
@Data
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
//AM FOLOSIT LOMBOK PENTRU A NU MAI FOLOSI GETTERI SI SETTERI SI CONSTRUCTORI IN CLASA
public class Pereche implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPereche;

    @ManyToOne
    @JoinColumn(name = "id_film")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "id_utilizator")
    private Utilizator utilizator;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "status_vizionare")
    private StatusVizionare statusVizionare;
    public Pereche(Utilizator utilizator, Film film, StatusVizionare statusVizionare) {
        this.utilizator = utilizator;
        this.film = film;
        this.statusVizionare = statusVizionare;
    }
}
