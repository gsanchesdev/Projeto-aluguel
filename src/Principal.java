import java.util.InputMismatchException;
import java.util.Scanner;

import exception.SistemaException;
import menu.Menu;
import model.Administrador;
import model.Cliente;
import model.Veiculo;
import model.Vendedor;
import service.AdminService;
import service.ClienteService;
import service.VeiculoService;
import service.VendedorService;
import util.Normaliza;
import exception.SistemaException;


public class Principal {

	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		
		ClienteService clienteService = new ClienteService(sc);
		VeiculoService veiculoService = new VeiculoService(sc);
		VendedorService vendedorService = new VendedorService(sc);
		AdminService adminService = new AdminService(sc, veiculoService, vendedorService);

		
		boolean continua = true;
		do {
			try {
			Menu.menu1();
			int opcao1 = sc.nextInt();
			sc.nextLine();
			
			switch(opcao1) {
			case 1:
				Menu.menu2();
				String email = sc.nextLine();
				Cliente cliente = clienteService.confereEmail(email);
				
				boolean senhaCorreta = false;
				for(int i = 0; i < 3; i++) {
					System.out.println("Digite sua senha");
					String senha = sc.next();
					senhaCorreta = clienteService.conferirSenha(cliente, senha);
					if(!senhaCorreta){
						System.out.println("Senha incorreta. Tente novamente!");
					} else {
						break;
					}
				}
				if(!senhaCorreta) {
					break;
				}
				
				Menu.menuCliente2();
				int opcao2 = sc.nextInt();
				if(opcao2 == 1) {
					System.out.println("Digite o numero referente ao carro desejado");
					veiculoService.buscarTodosVeiculosLivres();
					int opcaoCarro = sc.nextInt();
					Veiculo veiculo = veiculoService.alugarVeiculoPorID(cliente, opcaoCarro);
					clienteService.alugarVeiculo(cliente, veiculo);

					// ESCOLHER VENDEDOR QUE ATENDEU
					System.out.println("Digite o numero referente ao vendedor que lhe atendeu");
					vendedorService.retornaTodosVendedores();
					int opcaoVendedor = sc.nextInt();
					vendedorService.salvarVeiculo(veiculo, opcaoVendedor);
					
				} else if(opcao2 == 2) {
					if(cliente.getVeiculos().size() <= 0) {
						throw new SistemaException("Voce nao tem veiculos para devolver");
					}
					
					System.out.println("Digite o numero referente ao carro desejado");
					clienteService.buscarVeiculosAlugados(cliente);
					
					int opcaoCarro = sc.nextInt();
					
					Veiculo veiculoDevolvido = veiculoService.devolverVeiculo(cliente, opcaoCarro);
					clienteService.removerVeiculo(cliente, veiculoDevolvido);
					
					
				}
				
				break;
			case 2:
				Menu.menu2();
				email = sc.nextLine();
				Vendedor vendedor = vendedorService.confereEmail(email);
				if(vendedor == null) {
					throw new SistemaException("Vendedor nao encontrado!");
				}
				senhaCorreta = false;
				for(int i = 0; i < 3; i++) {
					System.out.println("Digite sua senha");
					String senha = sc.next();
					senhaCorreta = vendedorService.conferirSenha(vendedor, senha);
					if(!senhaCorreta){
						System.out.println("Senha incorreta. Tente novamente!");
					} else {
						break;
					}
				}
				if(!senhaCorreta) {
					break;
				}
				
				Menu.menuVendedor1();
				opcao2 = sc.nextInt();
				
				if(opcao2 == 1) { 
					vendedorService.mostrarAlugueisVeiculos(vendedor);
				}else if(opcao2 == 2) {
					vendedorService.verSalario(vendedor);
				}else if(opcao2 == 3){
					vendedorService.verSalarioComComissao(vendedor);
				}
				break;
			case 3:
				Menu.menu2();
				email = sc.next();

				Administrador admin = adminService.confereEmail(email);
				if(admin == null) {
					throw new SistemaException("Administrador nao encontrado!");
				}
				
				senhaCorreta = false;
				for(int i = 0; i < 3; i++) {
					System.out.println("Digite sua senha");
					String senha = sc.next();
					senhaCorreta = adminService.conferirSenha(admin, senha);
					if(!senhaCorreta){
						System.out.println("Senha incorreta. Tente novamente!");
					} else {
						break;
					}
				}
				if(!senhaCorreta) {
					break;
				}
				
				Menu.menuAdministrador();
				opcao2 = sc.nextInt();
				adminService.confereEntrada(opcao2);
				
				break;
			case 0: 
				continua = false;
				break;
			default:
				System.out.println("Opção invalida. Tente novamente!");
				break;
			}
			
			
			}catch(SistemaException e) {
				System.out.println(e.getMessage());
			}catch(InputMismatchException e) {
				sc.nextLine();
				System.out.println("Opcao invalida!");
			}finally {
				Thread.sleep(2000l);
			}
				
			
		} while( continua );

			
			
			
			
			
			
			
//			if(opcao1 == 1) {
//				Menu.menuCliente1();
//			}else if(opcao1 == 2) {
//				Menu.menuVendedor();
//			}else if(opcao1 == 3) {
//				Menu.menuAdministrador();
//			}else if(opcao1 == 0) {
//				continua = false;
//				break;
//			}else {
//				System.out.println("Alternativa inválida. Tente novamente");
//			}
			
			
			
			
			
		
		
		
	}

}
