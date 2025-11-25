CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_pedido VARCHAR(20) NOT NULL UNIQUE,
    cliente_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    observacoes TEXT,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    INDEX idx_numero_pedido (numero_pedido),
    INDEX idx_cliente (cliente_id),
    INDEX idx_status (status),
    INDEX idx_data_pedido (data_pedido)
);