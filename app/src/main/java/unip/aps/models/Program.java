package unip.aps.models;

import java.util.List;

public class Program {
    private String codigoDoPrograma;
    private String nomeDoPrograma;
    private String nivelDoPrograma;
    private String duracao;
    private String horario;
    private String descricao;
    private List<String> matriculados;

    public Program(String codigoDoPrograma, String nomeDoPrograma, String nivelDoPrograma, String duracao, String horario, String descricao, List<String> matriculados) {
        this.codigoDoPrograma = codigoDoPrograma;
        this.nomeDoPrograma = nomeDoPrograma;
        this.nivelDoPrograma = nivelDoPrograma;
        this.duracao = duracao;
        this.horario = horario;
        this.descricao = descricao;
        this.matriculados = matriculados;
    }

    public String getCodigoDoPrograma() {
        return codigoDoPrograma;
    }

    public void setCodigoDoPrograma(String codigoDoPrograma) {
        this.codigoDoPrograma = codigoDoPrograma;
    }

    public String getNomeDoPrograma() {
        return nomeDoPrograma;
    }

    public void setNomeDoPrograma(String nomeDoPrograma) {
        this.nomeDoPrograma = nomeDoPrograma;
    }

    public String getNivelDoPrograma() {
        return nivelDoPrograma;
    }

    public void setNivelDoPrograma(String nivelDoPrograma) {
        this.nivelDoPrograma = nivelDoPrograma;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getMatriculados() {
        return matriculados;
    }

    public void addRA(String RA) {
        this.matriculados.add(RA);
    }

    public void removeRA(String RA) {
        this.matriculados.remove(RA);
    }

    @Override
    public String toString() {
        return "Codigo do Programa: " + codigoDoPrograma + "\n" +
                "Nome do Programa: " + nomeDoPrograma + "\n" +
                "Nivel do Programa: " + nivelDoPrograma + "\n"
                + "Duracao: " + duracao + "\n"
                + "Horario: " + horario + "\n"
                + "Descricao: " + descricao + "\n"
                + "Matriculados: " + matriculados + "\n";
    }
}
