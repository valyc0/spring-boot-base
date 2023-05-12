package io.bootify.my_app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import io.bootify.my_app.bean.ContattoDTO;
import io.bootify.my_app.domain.Contatto;
import io.bootify.my_app.service.ContattoService;


@Controller
public class HomeController {
	
	@Autowired
	ContattoService contattoService;

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello World!";
    }
    
    @GetMapping("/ins")
    @ResponseBody
    public String ins() {
    	
    	Contatto contatto = new Contatto();
    	contatto.setId(1L);
    	contatto.setNome("pippo1");
    	contatto.setCognome("pluto1");
    	contattoService.save(contatto);
    	
    	contatto.setId(2L);
    	contatto.setNome("pippo2");
    	contatto.setCognome("pluto2");
    	contattoService.save(contatto);
		
		return "done";
        
    }
    
    @GetMapping("/get")
    @ResponseBody
    public List<Contatto> get() {
        return contattoService.findAll();
    }
    
    @GetMapping("/getJ")
    @ResponseBody
    public List<ContattoDTO> getJ() {
        return contattoService.findAllJdbc();
    }

    @GetMapping("/save/{id}/{nome}")
    public ResponseEntity<String> saveEntity(@PathVariable Long id,@PathVariable String nome) {
       
        contattoService.savEntity(id,nome);
        
        return ResponseEntity.ok("Entità salvata con successo");
    }
    

}
