package br.com.alura.service;

import br.com.alura.Command;
import br.com.alura.client.ClientHttpConfiguration;

import java.io.IOException;

public class CadastrarAbrigoCommand implements Command {
    @Override
    public void execute() {
        ClientHttpConfiguration client = new ClientHttpConfiguration();
        AbrigoService abrigoService = new AbrigoService(client);

        try {
            abrigoService.cadastrarAbrigo();

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
}
