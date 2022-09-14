package service;

import java.util.List;
import java.util.Scanner;

import exception.SistemaException;
import model.Cliente;
import model.Veiculo;
import repository.ClienteRepository;
import util.Normaliza;

public class ClienteService {
	
	Scanner sc;
	ClienteRepository repository = new ClienteRepository();	

	public ClienteService(Scanner sc) {
		this.sc = sc;
		repository.salvar(new Cliente("joao", "joao@", "poa", "123"));
	}

	public Cliente confereEmail(String email) {
		 List<Cliente> clientesCadastrados = repository.buscarTodos();
		 
		 for(Cliente cliente: clientesCadastrados) {
			 if(cliente.getEmail().equals(Normaliza.normalizaEmail(email))) {
				 return cliente;
			 }
		 }
	
		 return this.cadastrarCliente(email);
	}
	
	public boolean conferirSenha(Cliente clienteParam, String senha) {
		Cliente cliente = repository.buscarPorId(clienteParam.getId());
		
		return cliente.getSenha().equals(senha);
	}
	
	
	private Cliente cadastrarCliente(String email) {
		System.out.println("digite seu nome: ");		
		String nome = sc.nextLine();
		
		System.out.println("digite sua cidade: ");		
		String cidade = sc.nextLine();
		System.out.println("digite uma senha: ");		
		String senha = sc.nextLine();
	
		Cliente cliente = new Cliente(nome, email, cidade, senha);
		
		this.repository.salvar(cliente);
		
		return cliente;
	}

	public void alugarVeiculo(Cliente cliente, Veiculo veiculo) {
		cliente.getVeiculos().add(veiculo);
		this.repository.salvar(cliente);
	}

	public void buscarVeiculosAlugados(Cliente cliente) {
		List<Veiculo> veiculosAlugados = cliente.getVeiculos();
		
		for(Veiculo veiculo: veiculosAlugados) {
			System.out.println(veiculo);
		}
		
	}
	
	public void removerVeiculo(Cliente clienteParam, Veiculo veiculoParam) throws SistemaException {
		Cliente cliente = this.repository.buscarPorId(clienteParam.getId());
		
		//tratamento de exception
		if(cliente == null) {
			throw new SistemaException("Cliente nao encontrado!");
		}
	
		
		cliente.getVeiculos().remove(veiculoParam);
		
		this.repository.salvar(cliente);
	}



}
