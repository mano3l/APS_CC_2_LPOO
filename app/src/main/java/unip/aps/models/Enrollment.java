package unip.aps.models;

public class Enrollment {
    private String ra;
    private int idade;
    private String nome;
    private String sobrenome;
    private String sexo;
    private String email;
    private String telefone;
    private String endereco;
    private String nomeDoPrograma;
    private String dataMatricula;

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "ra='" + ra + '\'' +
                ", idade=" + idade +
                ", nome='" + nome + '\'' +
                ", sexo='" + sexo + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", nomeDoPrograma='" + nomeDoPrograma + '\'' +
                ", dataMatricula='" + dataMatricula + '\'' +
                '}';
    }
}
