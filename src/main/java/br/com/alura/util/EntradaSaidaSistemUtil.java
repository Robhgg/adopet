package br.com.alura.util;

import java.io.*;

public class EntradaSaidaSistemUtil {

    private EntradaSaidaSistemUtil() {
        throw new IllegalStateException("Classe composta de metodos estaticos, "
                + "nao deve ser instanciada");
    }

    public static ByteArrayOutputStream pegaSaidaSystemOut(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PrintStream printStream = new PrintStream(baos);

        System.setOut(printStream);

        return baos;
    }

    public static void simulaEntradaSystemIn(String entradaSimulada){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(entradaSimulada.getBytes());

        System.setIn(inputStream);
    }

    public static void restauraEntradaOriginal(){
        InputStream inputStream = System.in;

        System.setIn(inputStream);
    }

    public static void restauraSaidaOriginal(){
        PrintStream printStream = System.out;

        System.setOut(printStream);
    }
}
