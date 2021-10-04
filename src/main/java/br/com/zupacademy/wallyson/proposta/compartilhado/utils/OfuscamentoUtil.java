package br.com.zupacademy.wallyson.proposta.compartilhado.utils;

public class OfuscamentoUtil {

    public static String documento(String documento) {
        documento = documento.replaceAll("[^0-9]", "");
        var isCpf = documento.length() == 11;
        if (isCpf) {
            return String.format("%s######%s", documento.substring(0, 3), documento.substring(9));
        }

        return String.format("%s#########%s", documento.substring(0, 3), documento.substring(12));
    }

    public static String cartao(String cartao) {
        return String.format("############%s", cartao.replaceAll("[^0-9]", "").substring(12));
    }
}
