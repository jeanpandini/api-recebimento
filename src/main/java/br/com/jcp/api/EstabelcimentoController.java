package br.com.jcp.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.jcp.model.estabelecimento.Estabelecimento;
import br.com.jcp.model.estabelecimento.EstabelecimentoRepository;

@Controller
@RequestMapping(path = "/api/estabelecimento")
public class EstabelcimentoController {

	@Autowired
	private EstabelecimentoRepository estbRepo;

	@PostMapping
	public ResponseEntity<Estabelecimento> cria(@RequestBody Estabelecimento estabelecimento) {
		return ResponseEntity.ok(estbRepo.save(estabelecimento));
	}

	@PutMapping
	public ResponseEntity<Estabelecimento> atualiza(@RequestBody Estabelecimento estabelecimento) {
		if (!estbRepo.existsById(estabelecimento.getId())) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(estbRepo.save(estabelecimento));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		if (!estbRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		estbRepo.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<Estabelecimento> buscaPorId(@PathVariable Long id) {
		if (estbRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(estbRepo.findById(id).get());
	}

	@GetMapping
	public ResponseEntity<List<Estabelecimento>> getTodos() {
		return ResponseEntity.ok(estbRepo.findAll());
	}

	@GetMapping(path = "/findByName")
	public ResponseEntity<Estabelecimento> buscaPorNome(@RequestParam(name = "nome") String nome) {
		Estabelecimento estbSearch = new Estabelecimento();
		estbSearch.setNome(nome);
		Example<Estabelecimento> exampleEstb = Example.of(estbSearch);
		if (!estbRepo.exists(exampleEstb)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(estbRepo.findOne(exampleEstb).get());
	}

}
