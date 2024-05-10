package unip.aps.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataFormatter {
    public String formatPhoneNumber(String phoneNumber) {
        String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");
        if (cleanedNumber.length() != 11) {
            return "Número de telefone inválido";
        }

        // Formatando o número de telefone
        String formattedNumber = "(" + cleanedNumber.substring(0, 2) + ") " +
                cleanedNumber.substring(2, 7) + "-" +
                cleanedNumber.substring(7);

        return formattedNumber;
    }

    public String formatCpf(String cpf) {
        String cleanedNumber = cpf.replaceAll("[^0-9]", "");

        if (cleanedNumber.length() != 11) {
            return "CPF inválido";
        }
        return cleanedNumber.substring(0, 3) + "." +
                cleanedNumber.substring(3, 6) + "." +
                cleanedNumber.substring(6, 9) + "-" +
                cleanedNumber.substring(9);
    }

    public String setDate() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = now.format(formatter);
        return dataFormatada;
    }



}
