package br.com.alura.service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Pet;
import br.com.alura.util.EntradaSaidaSistemUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PetServiceTest {

    private final ClientHttpConfiguration client = mock(ClientHttpConfiguration.class);

    @SuppressWarnings("rawtypes")
    private final HttpResponse response = mock(HttpResponse.class);

    private final PetService service = new PetService(client);

    private final String testeSimulaImportarPetsAbrigo = "1\nsrc/test/resources/petsTest.csv";

    private final Pet pet = new Pet("Cachorro", "Teste", "Teste",
            10, "vermelho", 5F);

    @AfterEach
    public void afterEach() {
        EntradaSaidaSistemUtil.restauraSaidaOriginal();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testMensagensDeveriamSerIguaisMetodolistarPetsDoAbrigo() throws IOException, InterruptedException {
        ByteArrayOutputStream boas = EntradaSaidaSistemUtil.pegaSaidaSystemOut();

        pet.setId(0L);

        when(client.dispararRequisicaoGet(anyString())).thenReturn(response);
        when(response.body()).thenReturn("[{"+pet.toString()+"}]");
        when(response.statusCode()).thenReturn(200);

        EntradaSaidaSistemUtil.simulaEntradaSystemIn("1");

        service.listarPetsDoAbrigo();

        EntradaSaidaSistemUtil.restauraEntradaOriginal();

        String[] lines = boas.toString().split(System.lineSeparator());

        Assertions.assertEquals("Digite o id ou nome do abrigo:", lines[0]);
        Assertions.assertEquals("Pets cadastrados:", lines[1]);
        Assertions.assertEquals(lines[2], pet.getId() +" - " +pet.getTipo()
                +" - " +pet.getNome() +" - " +pet.getRaca() +" - " +pet.getIdade() +" ano(s)");

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testMensagensDeveriamSerIguaisMetodolistarPetsDoAbrigoCodigo400() throws IOException, InterruptedException {
        ByteArrayOutputStream boas = EntradaSaidaSistemUtil.pegaSaidaSystemOut();

        when(client.dispararRequisicaoGet(anyString())).thenReturn(response);
        when(response.statusCode()).thenReturn(404);

        EntradaSaidaSistemUtil.simulaEntradaSystemIn("1");

        service.listarPetsDoAbrigo();

        EntradaSaidaSistemUtil.restauraEntradaOriginal();

        String[] lines = boas.toString().split(System.lineSeparator());

        Assertions.assertEquals("ID ou nome não cadastrado!", lines[1]);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testimportarPetsDoAbrigoCodigo200() throws IOException, InterruptedException {
        ByteArrayOutputStream boas = EntradaSaidaSistemUtil.pegaSaidaSystemOut();
        EntradaSaidaSistemUtil.simulaEntradaSystemIn(testeSimulaImportarPetsAbrigo);

        when(client.dispararRequisicaoPost(anyString(), any())).thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("[{"+pet.toString()+"}]");

        service.importarPetsDoAbrigo();

        String[] lines = boas.toString().split(System.lineSeparator());

        Assertions.assertEquals("Pet cadastrado com sucesso: " + pet.getNome(), lines[2]);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testimportarPetsDoAbrigoCodigo400() throws IOException, InterruptedException {
        ByteArrayOutputStream boas = EntradaSaidaSistemUtil.pegaSaidaSystemOut();
        EntradaSaidaSistemUtil.simulaEntradaSystemIn(testeSimulaImportarPetsAbrigo);

        when(client.dispararRequisicaoPost(anyString(), any())).thenReturn(response);
        when(response.statusCode()).thenReturn(400);
        when(response.body()).thenReturn("[{"+pet.toString()+"}]");

        service.importarPetsDoAbrigo();

        String[] lines = boas.toString().split(System.lineSeparator());

        Assertions.assertEquals("Erro ao cadastrar o pet: " + pet.getNome(), lines[2]);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testimportarPetsDoAbrigoCodigo404() throws IOException, InterruptedException {
        ByteArrayOutputStream boas = EntradaSaidaSistemUtil.pegaSaidaSystemOut();
        EntradaSaidaSistemUtil.simulaEntradaSystemIn(testeSimulaImportarPetsAbrigo);

        when(client.dispararRequisicaoPost(anyString(), any())).thenReturn(response);
        when(response.statusCode()).thenReturn(404);
        when(response.body()).thenReturn("[{"+pet.toString()+"}]");

        service.importarPetsDoAbrigo();

        String[] lines = boas.toString().split(System.lineSeparator());

        Assertions.assertEquals("Id ou nome do abrigo não encontado!", lines[2]);
    }

}
