package com.datum.gestao.pedidos.domain.service;

import com.datum.gestao.pedidos.api.dto.cliente.ClienteAtualizaRequestDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteRequestDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResponseDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResumoResponseDTO;
import com.datum.gestao.pedidos.core.mapper.ClienteMapper;
import com.datum.gestao.pedidos.domain.exception.ClienteNaoEncontradoException;
import com.datum.gestao.pedidos.domain.model.Cliente;
import com.datum.gestao.pedidos.domain.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public Page<ClienteResumoResponseDTO> listarClientes(Pageable pageable) {
        Page<Cliente> clientes = clienteRepository.findAll(pageable);
        List<ClienteResumoResponseDTO> dtos = clienteMapper.toClienteResumoDTO(clientes.getContent());
        return new PageImpl<>(dtos, pageable, clientes.getTotalElements());
    }

    public ClienteResponseDTO buscarClientePorId(Long id) {
        Cliente cliente = buscarCliente(id);
        return clienteMapper.toClienteResponseDTO(cliente);
    }

    public ClienteResponseDTO salvarCliente(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteMapper.toCliente(clienteRequestDTO);
        cliente.setAtivo(true);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteMapper.toClienteResponseDTO(clienteSalvo);
    }

    @Transactional
    public ClienteResponseDTO atualizarClientePorId(Long id, ClienteAtualizaRequestDTO clienteAtualizaRequestDTO) {
        Cliente clienteBanco = buscarCliente(id);

        atualizarCliente(clienteAtualizaRequestDTO, clienteBanco);

        return clienteMapper.toClienteResponseDTO(clienteBanco);
    }

    @Transactional
    public void deletarClientePorId(Long id) {
        Cliente cliente = buscarCliente(id);
        cliente.setAtivo(false);
    }

    private void atualizarCliente(ClienteAtualizaRequestDTO clienteAtualizaRequestDTO, Cliente clienteBanco) {
        clienteBanco.setNome(clienteAtualizaRequestDTO.nome());
        clienteBanco.setTelefone(clienteAtualizaRequestDTO.telefone());
        clienteBanco.setLogradouro(clienteAtualizaRequestDTO.logradouro());
        clienteBanco.setNumero(clienteAtualizaRequestDTO.numero());
        clienteBanco.setComplemento(clienteAtualizaRequestDTO.complemento());
        clienteBanco.setBairro(clienteAtualizaRequestDTO.bairro());
        clienteBanco.setCidade(clienteAtualizaRequestDTO.cidade());
        clienteBanco.setEstado(clienteAtualizaRequestDTO.estado());
    }

    private Cliente buscarCliente(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }
}
