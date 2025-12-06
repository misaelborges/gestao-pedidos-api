package com.datum.gestao.pedidos.domain.service;

import com.datum.gestao.pedidos.api.dto.cliente.ClienteAtualizaRequestDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteRequestDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResponseDTO;
import com.datum.gestao.pedidos.api.dto.cliente.ClienteResumoResponseDTO;
import com.datum.gestao.pedidos.core.mapper.ClienteMapper;
import com.datum.gestao.pedidos.domain.exception.ClienteNaoEncontradoException;
import com.datum.gestao.pedidos.domain.model.Cliente;
import com.datum.gestao.pedidos.domain.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente joao;
    private Cliente maria;
    private ClienteResumoResponseDTO joaoResumoDTO;
    private ClienteResumoResponseDTO mariaResumoDTO;
    private ClienteResponseDTO joaoResponseDTO;
    private ClienteResponseDTO joaoAtualizadoDTO;
    private ClienteRequestDTO joaoRequestDTO;
    private ClienteAtualizaRequestDTO joaoAtualizaDTO;

    @BeforeEach
    public void setup() {

        joao = new Cliente(1L,"João Silva","joao@email.com","12345678900","11999990000",
                "01001-000","Rua A","100","Apto 101","Centro","São Paulo",
                "SP", LocalDateTime.now(), true
        );

        maria = new Cliente(2L,"Maria Oliveira","maria@email.com","98765432100","21988887777",
                "20040-020","Rua B","200","Casa","Copacabana","Rio de Janeiro",
                "RJ", LocalDateTime.now(),true
        );

        joaoResumoDTO = new ClienteResumoResponseDTO(1L,"João Silva","joao@email.com","11999990000","SP",
                "Rio de Janeiro");

        mariaResumoDTO = new ClienteResumoResponseDTO(2L, "Maria Oliveira", "maria@email.com", "21988887777",
                "RJ", "Rio de Janeiro");

        joaoResponseDTO = new ClienteResponseDTO(1L,"João Silva","joao@email.com",
                "12345678900","11999990000", "01001-000","Rua A","100",
                "Apto 101","Centro","São Paulo", "SP", LocalDateTime.now());

        joaoRequestDTO = new ClienteRequestDTO("João Silva","joao@email.com","12345678900","11999990000",
                "01001-000","Rua A","100","Apto 101","Centro","São Paulo",
                "SP");

        joaoAtualizaDTO = new ClienteAtualizaRequestDTO("João Silva José", "1199999", "Rua A","100",
                "Apto 101","Centro","São Paulo", "SP");

        joaoAtualizadoDTO = new ClienteResponseDTO(1L,"João Silva José","joao@email.com",
                "12345678900","1199999", "01001-000","Rua A","100",
                "Apto 101","Centro","São Paulo", "SP", LocalDateTime.now());
    }

    @Test
    @DisplayName("Dado que existam clientes, listarClientes deve retorná-los")
    void deveListarClientesComSucesso() {

        Pageable pageable = PageRequest.of(0, 10);

        List<Cliente> clientes = List.of(joao, maria);

        Page<Cliente> pageClientes = new PageImpl<>(clientes, pageable, clientes.size());

        List<ClienteResumoResponseDTO> dtos = List.of(joaoResumoDTO, mariaResumoDTO);

        when(clienteRepository.findAllClienteByAtivoTrue(pageable))
                .thenReturn(pageClientes);

        when(clienteMapper.toClienteResumoDTO(clientes))
                .thenReturn(dtos);

        Page<ClienteResumoResponseDTO> resultado = clienteService.listarClientes(pageable);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getContent().get(0).nome());
        assertEquals("Maria Oliveira", resultado.getContent().get(1).nome());

        verify(clienteRepository, times(1)).findAllClienteByAtivoTrue(pageable);
        verify(clienteMapper, times(1)).toClienteResumoDTO(clientes);
    }

    @Test
    @DisplayName("Dado um id válido, buscarClientePorId deve retornar o cliente")
    void deveBuscarClientePorIdComSucesso() {

        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(joao));

        when(clienteMapper.toClienteResponseDTO(joao))
                .thenReturn(joaoResponseDTO);

        ClienteResponseDTO resultado = clienteService.buscarClientePorId(1L);
        assertNotNull(resultado);
        assertEquals(joao.getId(), resultado.id());
        assertEquals(joao.getNome(), resultado.nome());

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteMapper, times(1)).toClienteResponseDTO(joao);
    }

    @Test
    @DisplayName("Dado um id inválido, buscarClientePorId deve lançar ClienteNaoEncontradoException")
    void deveFalharAoBuscarClientePorId() {

        when(clienteRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.buscarClientePorId(10L);
        });

        verify(clienteRepository, times(1)).findById(10L);
    }

    @Test
    @DisplayName("Dado um cliente válido, salvarCliente deve salvar um novo cliente")
    void deveSalvarClienteComSucesso() {

        when(clienteMapper.toCliente(joaoRequestDTO))
                .thenReturn(joao);

        when(clienteRepository.save(joao))
                .thenReturn(joao);

        when(clienteMapper.toClienteResponseDTO(joao))
                .thenReturn(joaoResponseDTO);

        ClienteResponseDTO resultado = clienteService.salvarCliente(joaoRequestDTO);

        assertNotNull(resultado);
        assertEquals(joao.getNome(), resultado.nome());
        assertTrue(joao.getAtivo());

        verify(clienteMapper, times(1)).toCliente(joaoRequestDTO);
        verify(clienteRepository, times(1)).save(joao);
        verify(clienteMapper, times(1)).toClienteResponseDTO(joao);
    }

    @Test
    @DisplayName("Dado um cliente válido, atualizarClientePorId deve atualizar um cliente já existente")
    void deveAtualizarClienteComSucesso() {
        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(joao));

        when(clienteMapper.toClienteResponseDTO(joao))
                .thenReturn(joaoAtualizadoDTO);

        ClienteResponseDTO resultado = clienteService.atualizarClientePorId(1L, joaoAtualizaDTO);

        assertNotNull(resultado);
        assertEquals(joaoAtualizadoDTO.nome(), resultado.nome());

        assertEquals(joaoAtualizadoDTO.nome(), joao.getNome());
        assertEquals(joaoAtualizadoDTO.telefone(), joao.getTelefone());

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteMapper, times(1)).toClienteResponseDTO(joao);
    }

    @Test
    @DisplayName("Dado um id válido, deletarClientePorId deve deletar um cliente")
    void deveDeletarClienteComSucesso() {
        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(joao));

        clienteService.deletarClientePorId(1L);

        assertFalse(joao.getAtivo());

        verify(clienteRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(clienteRepository);
    }
}