package unip.aps.models;

public class Enrollment {
    private String ra;
    private String cpf;
    private String email;
    private String codigoDoPrograma;
    private String dataMatricula;

    public Enrollment(String ra, String cpf, String email, String codigoDoPrograma, String dataMatricula) {
        this.ra = ra;
        this.cpf = cpf;
        this.email = email;
        this.codigoDoPrograma = codigoDoPrograma;
        this.dataMatricula = dataMatricula;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigoDoPrograma() {
        return codigoDoPrograma;
    }

    public void setCodigoDoPrograma(String codigoDoPrograma) {
        this.codigoDoPrograma = codigoDoPrograma;
    }

    public String getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "RA: " + ra + "\n" +
                "CPF: " + cpf + "\n" +
                "Nome do Programa: " + codigoDoPrograma + "\n" +
                "Data da Matricula: " + dataMatricula + "\n" +
                "Email: " + email + "\n";
    }
}
