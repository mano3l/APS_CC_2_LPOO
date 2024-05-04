package unip.aps.models;


public class Student {
    private String ra;
    private String nome;
    private int idade;
    private String sexo;
    private String telefone;

    public String getRa() {
        return ra;
    }

    public void setRa(){
        int rN =  (int) (Math.random() * 9000) + 1000;
        String randomNumber = Integer.toString(rN);
        this.ra = "RA" + randomNumber;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo.toLowerCase();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone.toLowerCase();
    }

    @Override
    public String toString() {
        return "1 - RA: " + getRa() + "\n" +
                "2 - NOME: " + getNome() + "\n" +
                "3 - IDADE: " + getIdade() + "\n" +
                "4 - SEXO: " + getSexo() + "\n" +
                "5 - TELEFONE: " + getTelefone() + "\n";
    }
}
