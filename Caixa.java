import javax.swing.JOptionPane;
import java.util.Random;  //importa a classe que gera numeros aleatorios
import java.util.HashSet; //importa a classe que faz com que o numero seja unico no conjunto
import java.util.Set;  //importa a classe que cria um conjunto
import java.util.ArrayList; // Importe a classe ArrayList
import java.util.List; // Importe a interface List
import java.util.HashMap; //importa a classe hashmap complementa a map e da funcionalidade de mapeamento chava-valor
import java.util.Map;  //classe que representa uma coleção de pares chave-valor


public class Caixa{
	static double dinheiro = 0;  //representa o valor inicial de dinheiro voce tem no banco
	static int numeroAleatorioConta;
	static Set<Integer> numerosDeConta = new HashSet<>();
	static List<String> listaAcoesExtrato = new ArrayList<>();
	static List<String> listaSenha = new ArrayList<>();

	static Map<Integer, String> contas = new HashMap<>(); //declara mapa chamado contas, q associa num de conta (chave) a senhas (valor)
	static Map<Integer, Double> saldos = new HashMap<>(); //declara mapa chamado saldos, associa num de conta (chave) a saldos iniciais (valor) 


	static {
		criarContaInicial(31, "1234");
		criarContaInicial(23, "1010");
	}
	
	public static void criarContaInicial(int numeroConta, String senha) {
	    dinheiro = 100;
		contas.put(numeroConta, senha);
		saldos.put(numeroConta, dinheiro);
		numerosDeConta.add(numeroConta);
		listaSenha.add(senha);
	}

	public static void criarConta(){               //metodo criar conta
	   String[] opcaoConta={"ABRIR NOVA CONTA" , "ENTRAR EM MINHA CONTA"};  //opcoes a serem escolhidas
	   int escolhaOpcaoConta= JOptionPane.showOptionDialog (
		   null, "DESEJA:",    //menssagem para o usuario
	       "Atenção!", //titulo da caixa de texto
	       JOptionPane.DEFAULT_OPTION,   //tipo de opçao padrao
	       JOptionPane.QUESTION_MESSAGE, //tipo da menssagem(pergunta)
	       null, opcaoConta, //opcoes a serem exibidas
	       opcaoConta[0] //opcao padrao a ser selecionada
	    );  

	   if (opcaoConta[escolhaOpcaoConta].equals("ABRIR NOVA CONTA")){    //se a opçao escolhida for abrir conta faca
	      String nome = JOptionPane.showInputDialog("Digite seu nome:");   //recebe o nome da pessoa
		  String senha = JOptionPane.showInputDialog("Crie uma senha de 4 números: "); //recebe senha da pessoa
	      listaSenha.add(senha);


	      do{   //fazer
	         numeroAleatorioConta = gerarNumeroAleatorio();  //o valor da variavel numeroAleatorioConta é o numero gerado no metodo gerarNumeroAleatorio
	      } while ( !numerosDeConta.add(numeroAleatorioConta));  //verifica se o numero ja esta no conjunto, se ja estiver ele vai gerando numeros aleatorios até achar um que não esteja, quando achar um que não está ele adiciona no conjunto e o loop encerra
	
		  dinheiro = 0.0;
		  contas.put(numeroAleatorioConta, senha);
          saldos.put(numeroAleatorioConta, dinheiro);
	      JOptionPane.showMessageDialog(null, "O número da sua conta é:" + numeroAleatorioConta, "Informação", JOptionPane.INFORMATION_MESSAGE); //caixa de texto mostrando o numero da conta
		   
		}
		  
	 }
	 public static int gerarNumeroAleatorio(){   //metodo para gerar o numero aleatorio
		Random rand = new Random();     //função que gera o numero aleatorio
		return rand.nextInt(100);    //retorna numeros aleatorios inteiros até o numero 100
	 }


	public static void verificarConta(){
		boolean contaCorreta = false; //inicia a varivel como falsa
		while(!contaCorreta){   //enquanto a conta não estiver correta continue pedendo
			String contaStr = JOptionPane.showInputDialog("Digite o número da sua conta:");
            int conta = Integer.parseInt(contaStr);
            if (contas.containsKey(conta)) {
                contaCorreta = true;
                JOptionPane.showMessageDialog(null, "Conta verificada com sucesso!", "Sucesso ao entrar", JOptionPane.INFORMATION_MESSAGE);
				String senha = contas.get(conta);
				verificarSenha(conta);
            } else {
                JOptionPane.showMessageDialog(null, "Conta incorreta! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
		}	
	} 

	public static void verificarSenha(int numeroConta){
		boolean senhaCorreta = false;
		do{
			String senha = JOptionPane.showInputDialog("Digite a senha:");
			if (contas.containsKey(numeroConta) && contas.get(numeroConta).equals(senha)) { // Verifica se a senha digitada corresponde à senha associada à conta
				senhaCorreta = true;
				JOptionPane.showMessageDialog(null, "Senha verificada com sucesso!", "Sucesso ao entrar", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Senha incorreta! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		} while(!senhaCorreta);
	}
	
	public static void sacar(double valorSaque){    //metodo sacar
	   if (dinheiro < valorSaque){   //se dinheiro for menor que valor
	     JOptionPane.showMessageDialog(null,"Saldo insuficiente para realizar o saque.", "ERRO", JOptionPane.ERROR_MESSAGE); //imprime caixa de texto
	   } else{   //senao
	        double resultado = dinheiro - valorSaque; //calcula, faz o dinheiro que vc tem no banco menos o valor do saque
	        System.out.println("O saque é de: " + valorSaque + " reais.");//printa o valor do saque
	        dinheiro = resultado; //atualiza a variavel dinheiro
	     }
	}


	
	public static void transferir(double valorTransferencia, int contaDestino){  //metodo transferir
	   if (dinheiro < valorTransferencia){    //se dinheiro for menor que valor
	     JOptionPane.showMessageDialog(null,"Saldo insuficiente para realizar a transferencia.", "ERRO", JOptionPane.ERROR_MESSAGE);
	   } else{

	       if (!numerosDeConta.contains(contaDestino)){
			   JOptionPane.showMessageDialog(null, "Está conta não existe. Não será realizado a transferência", "ERRO", JOptionPane.ERROR_MESSAGE);
		   } else{
			   System.out.println("E está transferindo para a conta: " + contaDestino);  //printa para qual conta vai transferir 
			   dinheiro = dinheiro - valorTransferencia; //calcula, faz o dinheiro que vc tem no banco menos o valor da transferencia
			   double saldoDestino = saldos.get(contaDestino); //obtem o saldo da conta destino
			   saldoDestino = saldoDestino + valorTransferencia; //soma o valor da transferencia na conta destino
			   saldos.put(contaDestino, saldoDestino); //atualiza o saldo da conta destino no mapa
	           System.out.println("O valor da transferencia é de: " + valorTransferencia); //printa volor da transferencia
		   }
	        
	      }
	}
    
	public static void depositar(double valorDeposito){    //metodo depositar
	   dinheiro = dinheiro + valorDeposito;   //soma o valor do deposito com o dinheiro do banco
	   System.out.println("O valor do deposito é de: " + valorDeposito);  //printa o valor do deposito
	   
	}

	public static void verSaldo(double dinheiro){  //metodo ver saldo 
	   JOptionPane.showMessageDialog(null, "Você tem: " + dinheiro + " na conta.", "Saldo em conta:", JOptionPane.INFORMATION_MESSAGE);
	   System.out.println("Você tem: " + dinheiro + " na conta.");  //printa quanto de dinheiro você tem na conta
	}

	public static void mostrarMenu(){
	   Object[] options= {"SACAR", "TRANSFERIR", "DEPOSITAR", "VER SALDO"};//opções a serem escolhidas

	   int selectedOption = JOptionPane.showOptionDialog(null, "O que deseja fazer?",//menssagem para o usuario 
	      "Escolha uma opção:",//título da caixa de texto 
	      JOptionPane.DEFAULT_OPTION,//tipo de opçoes(padrão) 
	      JOptionPane.QUESTION_MESSAGE,//tipo da menssagem(pergunta) 
	      null, options,//opcoes a serem exibidas 
	      options[0]);//opção padrão a ser selecionada

	   if (selectedOption != JOptionPane.CLOSED_OPTION){
	      System.out.println("Opção selecionada: " + options[selectedOption]);
	   }

	   switch (options[selectedOption].toString()){
		case "SACAR":
		   double valorSaque = Double.parseDouble(JOptionPane.showInputDialog("Digite quanto deseja sacar: "));  //recebe o valor do usuario e transforma esse input que é do tipo string em double
		   sacar(valorSaque); //executa o metodo sacar
		   listaAcoesExtrato.add("Saque de: " + valorSaque);
		   break;

	   case "TRANSFERIR":
	      int contaDestino = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da conta para qual vai transferir: "));  //entra o numero da conta para qual vai transferir o dinheiro
	      double valorTransferencia = Double.parseDouble(JOptionPane.showInputDialog("Digite quanto deseja transferir: "));  //recebe o valor do usuario e transforma esse input que é do tipo string em double
	      transferir(valorTransferencia, contaDestino);	//executa o metodo transferir
		  listaAcoesExtrato.add("Tranferência de: " + valorTransferencia + " para a conta: " + contaDestino);
		  break;

		case "DEPOSITAR":
		   double valorDeposito = Double.parseDouble(JOptionPane.showInputDialog("Digite quanto deseja depositar: "));  //recebe o valor do usuario e transforma esse input que é do tipo string em double
		   depositar(valorDeposito);  //executa o metodo depositar
		   listaAcoesExtrato.add("Depósito de: " + valorDeposito);
		   break;

		case "VER SALDO":
		   verSaldo(dinheiro); //executa metodo ver saldo
		   listaAcoesExtrato.add("Ver saldo");
		   break;
	    }

	   Object[] escolher= {"SIM", "NÃO"};

	   int escolherOpcoes = JOptionPane.showOptionDialog(null, "Deseja fazer mais alguma coisa?",//menssagem para o usuario 
	     "Confirmação:",//título da caixa de texto 
	      JOptionPane.DEFAULT_OPTION,//tipo de opçoes(padrão) 
	      JOptionPane.QUESTION_MESSAGE,//tipo da menssagem(pergunta) 
	      null, escolher,//opcoes a serem exibidas 
	      escolher[0]);//opção padrão a ser selecionada

	   if (escolher[escolherOpcoes].equals("SIM")){
	     mostrarMenu();
	   } else{
	     JOptionPane.showMessageDialog(null, "Processo finalizado!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
	     }

	    Object[] imprimir= {"SIM", "NÂO"};
		int imprimirExtrato = JOptionPane.showOptionDialog(null, "Deseja imprimir extrato?",//menssagem para o usuario 
		"Confirmação:",//título da caixa de texto 
		 JOptionPane.DEFAULT_OPTION,//tipo de opçoes(padrão) 
		 JOptionPane.QUESTION_MESSAGE,//tipo da menssagem(pergunta) 
		 null, imprimir,//opcoes a serem exibidas 
		 imprimir[0]);//opção padrão a ser selecionada

	   if (imprimir[imprimirExtrato].equals("SIM")){
		String[] arrayExtrato = listaAcoesExtrato.toArray(new String[0]); //converte a lista para um array para usar no joptionpane
		JOptionPane.showOptionDialog(
          null, arrayExtrato, //chama o arrayExtrato para exibir a lista 
          "EXTRATO", // título da janela
          JOptionPane.DEFAULT_OPTION, // tipo de opção
          JOptionPane.INFORMATION_MESSAGE, // tipo de mensagem
          null, // ícone
          new Object[]{"OK"}, // opções dos botões
          "OK"); // opção inicial
		System.exit(0); //encerra o programa
	   } else{
		System.exit(0); //encerra o programa
	   }

	}

	public static void main(String[] args){
	   criarConta(); 
	   verificarConta();
	   mostrarMenu();
	}

}
