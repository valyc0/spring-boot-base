package io.bootify.my_app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import io.bootify.my_app.bean.ContattoDTO;
import io.bootify.my_app.domain.Contatto;
import io.bootify.my_app.repos.ContattoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContattoService {
    
    @Autowired
    private ContattoRepository contattoRepository;
    
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
    
    public void deleteById(Long id) {
        contattoRepository.deleteById(id);
    }
    
}
