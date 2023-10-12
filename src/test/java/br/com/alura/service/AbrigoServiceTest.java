package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Abrigo;
import br.com.alura.util.EntradaSaidaSistemUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.http.HttpResponse;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class AbrigoServiceTest {

    private final ClientHttpConfiguration client = mock(ClientHttpConfiguration.class);

    @SuppressWarnings("Unchecked")
    private final HttpResponse response = mock(HttpResponse.class);

    private final AbrigoService service = new AbrigoService(client);

    private Scanner scanner = mock(Scanner.class);

    private final Abrigo abrigo = new Abrigo("Teste", "61981880392", "abrigo_alura@gmail.com");

    private final String entradasMetodoCadastraAbrigo = "entrada1\nentrada2\nentrada3";

    @AfterEach
    private void afterEach() {
        EntradaSaidaSistemUtil.restauraSaidaOriginal();
    }

    @Test
    public void testListarAbrigoDeveriaRetornarMensagemIgualAsMensagensDoTeste() throws IOException, InterruptedException {
        ByteArrayOutputStream baos = EntradaSaidaSistemUtil.pegaSaidaSystemOut();

        abrigo.setId(0L);

        when(client.dispararRequisicaoGet(anyString())).thenReturn(response);
        when(response.body()).thenReturn("[{"+abrigo.toString()+"}]");

        service.listarAbrigo();

        String[] lines = baos.toString().split(System.lineSeparator());

        Assertions.assertEquals("Abrigos cadastrados:", lines[0]);
        Assertions.assertEquals("0 - Teste", lines[1]);
    }

    @Test
    public void testCadastrarAbrigoDeveriaRetornarMensagemIgualAsMensagensDoTesteCodigo200() throws IOException, InterruptedException {
        ByteArrayOutputStream baos = EntradaSaidaSistemUtil.pegaSaidaSystemOut();

        EntradaSaidaSistemUtil.simulaEntradaSystemIn(entradasMetodoCadastraAbrigo);

        when(client.dispararRequisicaoPost(anyString(), any())).thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("[{"+abrigo.toString()+"}]");

        service.cadastrarAbrigo();

        EntradaSaidaSistemUtil.restauraEntradaOriginal();

        String[] lines = baos.toString().split(System.lineSeparator());

        Assertions.assertEquals("Abrigo cadastrado com sucesso!", lines[3]);
    }

    @Test
    public void testCadastrarAbrigoDeveriaRetornarMensagemIgualAsMensagensDoTesteCodigo400() throws IOException, InterruptedException {
        ByteArrayOutputStream baos = EntradaSaidaSistemUtil.pegaSaidaSystemOut();

        EntradaSaidaSistemUtil.simulaEntradaSystemIn(entradasMetodoCadastraAbrigo);

        when(client.dispararRequisicaoPost(anyString(), any())).thenReturn(response);
        when(response.statusCode()).thenReturn(400);
        when(response.body()).thenReturn("[{" + abrigo.toString() + "}]");

        service.cadastrarAbrigo();

        EntradaSaidaSistemUtil.restauraEntradaOriginal();

        String[] lines = baos.toString().split(System.lineSeparator());

        Assertions.assertEquals("Erro ao cadastrar o abrigo:", lines[3]);
    }

}
