package com.datum.gestao.pedidos.domain.service;

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
        Cliente cliente = buscadorClinte(id);
        return clienteMapper.toClienteResponseDTO(cliente);
    }

    public ClienteResponseDTO salvarCliente(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteMapper.toCliente(clienteRequestDTO);
        cliente.setAtivo(true);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteMapper.toClienteResponseDTO(clienteSalvo);
    }

    private Cliente buscadorClinte(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }
}
