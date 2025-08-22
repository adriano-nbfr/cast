package br.mp.mpf.cast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.mp.mpf.cast.model.ArquivoAndamento;

@Repository
public interface ArquivoAndamentoRepository extends JpaRepository<ArquivoAndamento, Long> {

}
