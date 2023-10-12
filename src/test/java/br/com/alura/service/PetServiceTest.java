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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PetServiceTest {

    private final ClientHttpConfiguration client = mock(ClientHttpConfiguration.class);

    private final HttpResponse response = mock(HttpResponse.class);

    private final PetService service = new PetService(client);

    private final Pet pet = new Pet("Cachorro", "Teste", "Teste",
            10, "vermelho", 5F);

    @AfterEach
    public void afterEach() {
        EntradaSaidaSistemUtil.restauraSaidaOriginal();
    }

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
}
