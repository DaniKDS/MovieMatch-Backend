package com.example.moviematchbackend.services.pereche_service;

import com.example.moviematchbackend.models.entity.Film;
import com.example.moviematchbackend.models.entity.Pereche;
import com.example.moviematchbackend.models.entity.StatusVizionare;
import com.example.moviematchbackend.models.entity.Utilizator;
import com.example.moviematchbackend.repositories.PerecheRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//acea clasa care imi face legatura dintre frontend si backend
@Service

public class PerecheService implements PerecheServiceInterface{

    //@Autowired
    private PerecheRepository perecheRepository;
    //acest constructor este folosit pentru a crea un obiect de tip PerecheService
    public PerecheService(PerecheRepository perecheRepository) {
        this.perecheRepository = perecheRepository;
    }

    //acesta metoda imi returneaza o pereche dupa id in format json
    @Override
    public Pereche getPerecheById(Long id) {
        return this.perecheRepository.getPerecheByIdPereche(id);
    }
    //acesta metoda imi returneaza o pereche dupa id in format json
    @Override
    public List<Pereche> getAllPereche() {
        List<Pereche> listOfPereche = new ArrayList<>();
        this.perecheRepository.findAll().forEach(pereche -> listOfPereche.add(pereche));
        return listOfPereche;

    }
    //acesta metoda imi returneaza toate perechile din baza de date in format json

    @Override
    public void deletePereche(Pereche pereche) {
        this.perecheRepository.delete(pereche);
    }
    //acesta metoda imi sterge o pereche din baza de date
    @Override
    public void savePereche(Utilizator utilizator, Film film, StatusVizionare statusVizionare) {
        Pereche pereche = new Pereche(utilizator, film, statusVizionare);
        this.perecheRepository.save(pereche);
    }
    //aceasta metoda imi adauga o pereche in baza de date

    public List<Film> getMoviesToSee(Utilizator user){
        List<Pereche> perechi = getAllPereche();
        List<Film> filme = new ArrayList<>();
        for(Pereche pr: perechi){
            if(pr.getUtilizator() == user && pr.getStatusVizionare() == StatusVizionare.IN_ASTEPTARE){
                filme.add(pr.getFilm());
            }
        }
        return filme;
    }
    //aceasta metoda imi returneaza toate filmele pe care un utilizator trebuie sa le vada
    //adica toate filmele cu statusul IN_ASTEPTARE pentru un utilizator

    public List<Film> getSeenMovies(Utilizator user){
        List<Pereche> perechi = getAllPereche();
        List<Film> filme = new ArrayList<>();
        for(Pereche pr: perechi){
            if(pr.getUtilizator() == user && pr.getStatusVizionare() == StatusVizionare.VAZUT){
                filme.add(pr.getFilm());
            }
        }
        return filme;
    }
    //aceasta metoda imi returneaza toate filmele pe care un utilizator le-a vazut
    //adica toate filmele cu statusul VAZUT pentru un utilizator

    public List<Film> getMoviesWithFriend(Utilizator utilizator, Utilizator friend){
        List<Film> my_movies = getMoviesToSee(utilizator);
        List<Film> his_movies = getMoviesToSee(friend);
        his_movies.retainAll(my_movies);
        return his_movies;
    }
    //aceasta metoda imi returneaza toate filmele pe care un utilizator si un prieten comun trebuie sa le vada
    //adica toate filmele cu statusul IN_ASTEPTARE pentru un utilizator si un prieten comun

    public Pereche getPerecheByUserAndMovie(Utilizator user, Film film){
        List<Pereche> perechi = getAllPereche();
        for(Pereche p1:perechi){
            if(p1.getUtilizator() == user && p1.getFilm() == film){
                return p1;
            }
        }
        return new Pereche();
    }
    //aceasta metoda imi returneaza o pereche dupa utilizator si film (daca exista)
    //daca nu exista, imi returneaza o pereche goala

}