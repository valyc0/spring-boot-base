package io.bootify.my_app.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.bootify.my_app.domain.Contatto;

import java.util.List;

public interface ContattoRepository extends JpaRepository<Contatto, Long> {

    @Query("SELECT c FROM Contatto c WHERE c.nome LIKE %?1% OR c.cognome LIKE %?1%")
    List<Contatto> findByNomeOrCognomeContaining(String keyword);

}
