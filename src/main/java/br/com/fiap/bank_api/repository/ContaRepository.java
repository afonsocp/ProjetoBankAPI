package br.com.fiap.bank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.bank_api.model.Conta;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByCpfTitular(String cpf);
}
