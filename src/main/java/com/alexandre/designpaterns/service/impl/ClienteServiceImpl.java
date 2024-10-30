package com.alexandre.designpaterns.service.impl;

import com.alexandre.designpaterns.model.ClientRepository;
import com.alexandre.designpaterns.model.Cliente;
import com.alexandre.designpaterns.model.Endereco;
import com.alexandre.designpaterns.model.EnderecoRepository;
import com.alexandre.designpaterns.service.ClienteService;
import com.alexandre.designpaterns.service.ViaCepService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 *
 * @author alexandrebarbosa
 */

@Service
public class ClienteServiceImpl implements ClienteService {

    // Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        // Buscar todos os clientes
        return clientRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        //Buscar cliente por ID.
        Optional<Cliente> cliente = clientRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        //Inserir Cliente, vinculando o Endereço (novo ou existente)
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteExistente = clientRepository.findById(id);
        if (clienteExistente.isPresent()) {
            //Alterar Cliente, vinculando o Endereço (novo ou existente)
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        // Deletar Cliente por ID.
        clientRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        //Verificar se o Endereço do Cliente já existe (pelo Cep)
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        // Inserir Cliente, vinculando o Endereço (novo ou existente)
        clientRepository.save(cliente);
    }


}
