package service;

import java.util.List;
import java.util.Scanner;

import exception.SistemaException;
import model.Cliente;
import model.Veiculo;
import model.Vendedor;
import repository.VendedorRepository;

public class VendedorService {
	Scanner sc;
	VendedorRepository repository = new VendedorRepository();

	public VendedorService(Scanner sc) {
		this.sc = sc;
		
		repository.salvar(new Vendedor("joao", "joao@vendedor", "poa", "1233", 1500)); 
		repository.salvar(new Vendedor("maria", "maria@vendedora", "poa", "1234", 3000)); 
		repository.salvar(new Vendedor("jose", "jose@vendedor", "poa", "1222", 2000)); 
		
	}

	public Vendedor confereEmail(String email) {
		 List<Vendedor> vendedoresCadastrados = repository.buscarTodos();
		 
		 for(Vendedor vendedor: vendedoresCadastrados) {
			 if(vendedor.getEmail().equals(email)) {
				 return vendedor;
			 }
		 }
		 return null;
	}

	public boolean conferirSenha(Vendedor vendedorParam, String senha) {
		Vendedor vendedor = repository.BuscarPorId(vendedorParam.getId());
		
		return vendedor.getSenha().equals(senha);
		 
	}
	
	public void verSalario(Vendedor vendedor) {
		System.out.println("Seu salario atual é: " + vendedor.getSalario());
	}

	public void retornaTodosVendedores() {
		List<Vendedor> vendedores = repository.buscarTodos();
		for(Vendedor vendedor: vendedores) {
			System.out.println(vendedor.getId()+ " - "+vendedor.getNome());
		}
	}
	
	public void salvarVeiculo(Veiculo veiculo, Integer idVendedor) throws SistemaException {
		Vendedor vendedor = repository.BuscarPorId(idVendedor);
		
		if(vendedor == null) {
			throw new SistemaException("Vendedor não encontrado!");
		}
		
		vendedor.getVeiculosAlugado().add(veiculo);
		
		repository.salvar(vendedor);
	}
	
	public void mostrarAlugueisVeiculos(Vendedor vendedor) {
		List<Veiculo> veiculos = vendedor.getVeiculosAlugado();
		
		for(Veiculo veiculo: veiculos) {
			System.out.println(veiculo);
		}
		
		
	}

	public void verSalarioComComissao(Vendedor vendedor) {
		List<Veiculo> veiculos = vendedor.getVeiculosAlugado();
		double totalVendas = 0;
		
		for(Veiculo veiculo: veiculos) {
			totalVendas += veiculo.getValorLocacao();
		}
		
		double comissao = totalVendas * vendedor.COMISSAO;
		System.out.println("Seu salário atual: "+ vendedor.getSalario());
		System.out.println("Sua comissão: "+ comissao);
		System.out.println("Seu salário + comissão: "+ (vendedor.getSalario() + comissao));
		
	}
	
	public void cadastrarVendedor() {
		
		System.out.println("Digite seu nome: ");		
		String nome = sc.next();
		System.out.println("Digite seu e-mail: ");		
		String email = sc.next();
		System.out.println("Digite sua cidade: ");		
		String cidade = sc.next();
		System.out.println("Digite uma senha: ");		
		String senha = sc.next();
		System.out.println("Digite o salário: ");		
		double salario = sc.nextDouble();

		Vendedor vendedor = new Vendedor(nome, email, cidade, senha, salario);
	
		repository.salvar(vendedor);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
