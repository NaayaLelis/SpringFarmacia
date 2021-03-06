package com.generation.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.model.Produto;
import com.generation.repository.CategoriaRepository;
import com.generation.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins= "*", allowedHeaders ="*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto>getById(@PathVariable Long id){
	    return produtoRepository.findById(id)
		.map(resposta -> ResponseEntity.ok(resposta))
		.orElse(ResponseEntity.notFound(). build());
	}
	
	@GetMapping ("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
				
	}
	
	@GetMapping ("/descricao/{descricao}")
	public ResponseEntity<List<Produto>> getByDescricao(@PathVariable String descricao){
		return ResponseEntity.ok(produtoRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@GetMapping ("precomaior/{preco}")
	public ResponseEntity<List<Produto>> getByPrecoMaior(@PathVariable BigDecimal preco){
		return ResponseEntity.ok(produtoRepository.findAllByPrecoGreaterThan(preco));
	}
	
	@GetMapping ("precomenor/{preco}")
	public ResponseEntity<List<Produto>> getByPrecoMenor(@PathVariable BigDecimal preco){
		return ResponseEntity.ok(produtoRepository.findAllByPrecoLessThan(preco));
	}
	
	@GetMapping ("{nome}/{descricao}")
	public ResponseEntity<List<Produto>> getByOr(@PathVariable String nome ,String descricao){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCase(nome, descricao));}
	
	
	@GetMapping ("/and/{descricao}/{preco}")
	public ResponseEntity<List<Produto>> getByAnd(@PathVariable String descricao,BigDecimal preco){
		return ResponseEntity.ok(produtoRepository.findAllByDescricaoContainingIgnoreCaseAndPrecoLessThan(descricao, preco));
	}

	@PostMapping
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto){
		
		if(categoriaRepository.existsById(produto.getCategoria().getId()))
				return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping ("{preco1}/{preco2}")
	public ResponseEntity<List<Produto>> getByBetween(@PathVariable BigDecimal preco1, BigDecimal preco2 ){
		return ResponseEntity.ok(produtoRepository.findByPrecoBetween(preco1, preco2));}
	
	@PutMapping
	public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto){
		
		if (produtoRepository.existsById(produto.getId())) {
			if(categoriaRepository.existsById(produto.getCategoria().getId()))
					return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));}
	
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		
		return produtoRepository.findById(id)
				.map(resposta -> {
					produtoRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}

		
		
	

