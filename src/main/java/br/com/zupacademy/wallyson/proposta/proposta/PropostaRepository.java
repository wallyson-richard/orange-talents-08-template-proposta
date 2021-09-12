package br.com.zupacademy.wallyson.proposta.proposta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    List<Proposta> findByDocumento(String documento);
}
