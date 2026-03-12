create database frota_veiculos;
use  frota_veiculos;
cREATE TABLE tb_veiculos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    placa VARCHAR(10) UNIQUE NOT NULL, -- UNIQUE evita placas duplicadas
    ano INT NOT NULL,
    quilometragem INT,
    cor VARCHAR(50),
    tipo VARCHAR(50),
    preco DECIMAL(10, 2) NOT NULL,
    status VARCHAR(30) NOT NULL
    );
    
insert into tb_veiculos (modelo, marca, placa, ano, quilometragem, cor, tipo, preco, status)
values ("Fiesta", "Ford", "DGX1Z28", "2004", 12000, "prata", "Hatch", 168000, "Disponivel"),
("C3", "Citroen", "ELF3I84", "2012", "24000", "preto", "hatch", "12000", "Oficina");

select * from tb_veiculos;

delete from tb_veiculos where id = 1; 
