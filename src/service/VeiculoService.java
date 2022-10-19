package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.SistemaException;
import model.Cliente;
import model.Veiculo;
import model.Veiculo.Status;
import repository.VeiculoRepository;

public class VeiculoService {
	Scanner sc;
	VeiculoService veiculoService;
	VeiculoRepository repository = new VeiculoRepository();
	
	public VeiculoService(Scanner sc) {
		this.sc = sc;
		
		repository.salvar(new Veiculo("Palio", "Fiat", "Preto", "IQO90MD", "carro", 300));
		repository.salvar(new Veiculo("Mobi", "Fiat", "Vermelho", "DOC12MLS", "carro", 350));
		repository.salvar(new Veiculo("Civic", "Honda", "Preto", "IQKN234", "carro", 400));
		repository.salvar(new Veiculo("CG 150", "Honda", "preto", "IKLA234", "moto", 200));
		
	}
	

	public void cadastrarVeiculo() {
		System.out.println("Digite o modelo do veículo: ");
		String modelo = sc.next();
		
		System.out.println("Digite a marca do veículo: ");
		String marca = sc.next();
		
		System.out.println("Digite a cor do veículo: ");
		String cor = sc.next();
		
		System.out.println("Digite a placa do veículo: ");
		String placa = sc.next();
		
		System.out.println("Digite o segmento do veículo: ");
		String tipo = sc.next();
		
		System.out.println("Digite o valor de locacão do veículo: ");
		double valor = sc.nextDouble();
		
		Veiculo veiculo = new Veiculo(modelo, marca, cor, placa, tipo, valor);
		
		this.repository.salvar(veiculo); 
	
		System.out.println("Você cadastrou o veículo!");
	}
	
	public void buscarTodosVeiculosLivres() {
		List<Veiculo> todosVeiculos = this.repository.buscarTodos();
		
		for(Veiculo veiculo: todosVeiculos) {
			if(veiculo.getStatus() == Status.LIVRE) {
				System.out.println(veiculo);
			}	
		}	
		
	}

	public Veiculo alugarVeiculoPorID(Cliente cliente, int id) throws SistemaException {
		Veiculo veiculo = this.repository.buscarPorId(id);
		
		//tratamento de exception
		if(veiculo == null) {
			throw new SistemaException("Veículo não encontrado!");
		}
		
		veiculo.setStatus(Status.ALUGADO);
		this.repository.salvar(veiculo);
		return veiculo;
	}
	
	public Veiculo devolverVeiculo(Cliente cliente, int id) throws SistemaException {
		Veiculo veiculo = this.repository.buscarPorId(id);
		
		if(veiculo == null) {
			throw new SistemaException("Veículo não encontrado!");
		}
		
		if(!cliente.getVeiculos().contains(veiculo)) {
			throw new SistemaException("Você não possui este veículo");
		}
		
		veiculo.setStatus(Status.LIVRE);
		this.repository.salvar(veiculo);
		
		return veiculo;  
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
