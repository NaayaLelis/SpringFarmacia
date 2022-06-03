package com.generation.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import com.generation.model.Produto;


public interface ProdutoRepository extends JpaRepository< Produto, Long>{
	
	public List <Produto> findAllByNomeContainingIgnoreCase(@PathVariable("nome") String nome);
	public List <Produto> findAllByDescricaoContainingIgnoreCase(@PathVariable("descricao") String descricao);
	public List <Produto> findById(@PathVariable("Id") long id);
	public List <Produto> findAllByPrecoGreaterThan(@PathVariable("preco") BigDecimal preco);
	public List <Produto> findAllByPrecoLessThan(@PathVariable("preco") BigDecimal preco);
	public List <Produto> findAllByDescricaoContainingIgnoreCaseAndPrecoLessThan(String descricao, BigDecimal preco);
	public List <Produto> findAllByNomeContainingIgnoreCaseOrDescricaoContainingIgnoreCase (String nome, String descricao);
	public List <Produto> findByPrecoBetween (BigDecimal preco1, BigDecimal preco2);
	

}
