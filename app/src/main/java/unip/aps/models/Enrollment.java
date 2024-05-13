package unip.aps.models;

public class Enrollment {
    private String ra;
    private String cpf;
    private String email;
    private String nomeDoPrograma;
    private String dataMatricula;

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

    public String getNomeDoPrograma() {
        return nomeDoPrograma;
    }

    public void setNomeDoPrograma(String nomeDoPrograma) {
        this.nomeDoPrograma = nomeDoPrograma;
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
        return "Enrollment{" +
                "ra='" + ra + '\'' +
                ", cpf='" + cpf + '\'' +
                ", nomeDoPrograma='" + nomeDoPrograma + '\'' +
                ", dataMatricula='" + dataMatricula + '\'' +
                '}';
    }
}
