package unip.aps.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataFormatter {
    public String formatPhoneNumber(String phoneNumber) {
        // Removendo caracteres não numéricos do número de telefone
        String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");

        // Verificando se o número é válido (possui 11 dígitos)
        if (cleanedNumber.length() != 11) {
            // Aqui você pode tratar caso o número não tenha a quantidade correta de dígitos
            return "Número de telefone inválido";
        }

        // Formatando o número de telefone
        String formattedNumber = "(" + cleanedNumber.substring(0, 2) + ") " +
                cleanedNumber.substring(2, 7) + "-" +
                cleanedNumber.substring(7);

        return formattedNumber;
    }

    public String setDate() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = now.format(formatter);
        return dataFormatada;
    }



}
