package br.com.fiap.bank_api.controller;

import jakarta.validation.Valid; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fiap.bank_api.model.Conta;
import br.com.fiap.bank_api.repository.ContaRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaRepository repository;

    public ContaController(ContaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Conta> criarConta(@Valid @RequestBody Conta conta) {
        conta.setAtiva(true);
        return ResponseEntity.ok(repository.save(conta));
    }

    @GetMapping
    public List<Conta> listarContas() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Conta> buscarPorCpf(@PathVariable String cpf) {
        return repository.findByCpfTitular(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/deposito")
    public ResponseEntity<Conta> depositar(@PathVariable Long id, @RequestParam double valor) {
        Optional<Conta> contaOpt = repository.findById(id);
        if (contaOpt.isEmpty() || valor <= 0) return ResponseEntity.badRequest().build();

        Conta conta = contaOpt.get();
        conta.setSaldo(conta.getSaldo() + valor);
        return ResponseEntity.ok(repository.save(conta));
    }

    @PutMapping("/{id}/saque")
    public ResponseEntity<Conta> sacar(@PathVariable Long id, @RequestParam double valor) {
        Optional<Conta> contaOpt = repository.findById(id);
        if (contaOpt.isEmpty()) return ResponseEntity.notFound().build();

        Conta conta = contaOpt.get();
        if (valor <= 0 || valor > conta.getSaldo()) return ResponseEntity.badRequest().build();

        conta.setSaldo(conta.getSaldo() - valor);
        return ResponseEntity.ok(repository.save(conta));
    }

    @PostMapping("/pix")
    public ResponseEntity<Conta> realizarPix(@RequestParam Long origemId, @RequestParam Long destinoId, @RequestParam double valor) {
        Optional<Conta> origemOpt = repository.findById(origemId);
        Optional<Conta> destinoOpt = repository.findById(destinoId);

        if (origemOpt.isEmpty() || destinoOpt.isEmpty()) return ResponseEntity.notFound().build();
        if (valor <= 0 || valor > origemOpt.get().getSaldo()) return ResponseEntity.badRequest().build();

        Conta origem = origemOpt.get();
        Conta destino = destinoOpt.get();

        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);

        repository.save(origem);
        repository.save(destino);

        return ResponseEntity.ok(origem);
    }

    @PutMapping("/{id}/encerrar")
    public ResponseEntity<Conta> encerrarConta(@PathVariable Long id) {
        return repository.findById(id).map(conta -> {
            conta.setAtiva(false);
            return ResponseEntity.ok(repository.save(conta));
        }).orElse(ResponseEntity.notFound().build());
    }
}
