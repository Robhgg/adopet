package br.com.alura;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.service.*;

import java.util.Scanner;

public class AdopetConsoleApplication {

    public static void main(String[] args) {
        CommandExecuter command = new CommandExecuter();

        System.out.println("##### BOAS VINDAS AO SISTEMA ADOPET CONSOLE #####");
        try {
            int opcaoEscolhida = 0;
            while (opcaoEscolhida != 5) {
                System.out.println("\nDIGITE O NÚMERO DA OPERAÇÃO DESEJADA:");
                System.out.println("1 -> Listar abrigos cadastrados");
                System.out.println("2 -> Cadastrar novo abrigo");
                System.out.println("3 -> Listar pets do abrigo");
                System.out.println("4 -> Importar pets do abrigo");
                System.out.println("5 -> Sair");

                String textoDigitado = new Scanner(System.in).nextLine();
                opcaoEscolhida = Integer.parseInt(textoDigitado);

                switch (opcaoEscolhida) {
                    case 1 -> command.execute(new ListarAbrigoCommand());
                    case 2 -> command.execute(new CadastrarAbrigoCommand());
                    case 3 -> command.execute(new ListarPetsCommand());
                    case 4 -> command.execute(new ImportarPetsCommand());
                    case 5 -> {
                        return;
                    }
                    default -> {
                        System.out.println("NÚMERO INVÁLIDO!");
                        opcaoEscolhida = 0;
                    }
                }
            }

            System.out.println("Finalizando o programa...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}