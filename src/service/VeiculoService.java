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
		
		repository.salvar(new Veiculo("palio", "fiat", "preto", "fhnfgxh", "carro", 634));
		repository.salvar(new Veiculo("mobi", "fiat", "vermelho", "fgj33ho", "carro", 534));
		repository.salvar(new Veiculo("toro", "fiat", "preto", "v457hlho", "carro", 244));
		repository.salvar(new Veiculo("cg 150", "fiat", "preto", "dfh346o", "moto", 334));
		
	}
	

	public void cadastrarVeiculo() {
		System.out.println("Digite o modelo do veiculo: ");
		String modelo = sc.next();
		
		System.out.println("Digite a marca do veiculo: ");
		String marca = sc.next();
		
		System.out.println("Digite a cor do veiculo: ");
		String cor = sc.next();
		
		System.out.println("Digite a placa do veiculo: ");
		String placa = sc.next();
		
		System.out.println("Digite o segmento do veiculo: ");
		String tipo = sc.next();
		
		System.out.println("Digite o valor de locacao do veiculo: ");
		double valor = sc.nextDouble();
		
		Veiculo veiculo = new Veiculo(modelo, marca, cor, placa, tipo, valor);
		
		this.repository.salvar(veiculo); 
	
		System.out.println("Voce cadastrou o veiculo!");
	}
	
	public void buscarTodosVeiculosLivres(){
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
			throw new SistemaException("Veiculo nao encontrado!");
		}
		
		veiculo.setStatus(Status.ALUGADO);
		this.repository.salvar(veiculo);
		return veiculo;
	}
	
	public Veiculo devolverVeiculo(Cliente cliente, int id) throws SistemaException {
		Veiculo veiculo = this.repository.buscarPorId(id);
		
		if(veiculo == null) {
			throw new SistemaException("Veiculo nao encontrado!");
		}
		
		if(!cliente.getVeiculos().contains(veiculo)) {
			throw new SistemaException("Voce nao possui este veiculo");
		}
		
		veiculo.setStatus(Status.LIVRE);
		this.repository.salvar(veiculo);
		
		return veiculo;  
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
