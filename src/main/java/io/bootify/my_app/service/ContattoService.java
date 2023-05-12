package io.bootify.my_app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import io.bootify.my_app.bean.ContattoDTO;
import io.bootify.my_app.domain.Contatto;
import io.bootify.my_app.repos.ContattoRepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class ContattoService {
    
    @Autowired
    private ContattoRepository contattoRepository;

    @Autowired
    EntityManager entityManager ;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

	// thanks Java 8, look the custom RowMapper
    public List<ContattoDTO> findAllJdbc() {

        List<ContattoDTO> result = jdbcTemplate.query(
                "SELECT id, nome, cognome,  FROM Contatto",
                (rs, rowNum) -> new ContattoDTO(rs.getLong("id"),
                        rs.getString("nome"), rs.getString("cognome"))
        );

        return result;
    }    
    
    public List<Contatto> findAll() {
        return contattoRepository.findAll();
    }
    
    public Optional<Contatto> findById(Long id) {
        return contattoRepository.findById(id);
    }
    
    public Contatto save(Contatto contatto) {
        return contattoRepository.save(contatto);
    }

    @Transactional
    public void savEntity(Long id, String name) {

        Contatto contatto = new Contatto();
        contatto.setId(id);
        contatto.setNome(name);
        contatto.setCognome("cogn-"+name);
        entityManager.persist(contatto);

        
    }
    
    public void deleteById(Long id) {
        contattoRepository.deleteById(id);
    }
    
}
