package unip.aps.models;

import java.util.ArrayList;
import java.util.List;

public class Programs {
    private String nomeDoPrograma;
    private String nivelDoPrograma;
    private String duracao;
    private String horario;
    private String descricao;
    private List<String> matriculados;

    public  Programs() {
        this.matriculados = new ArrayList<>();
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
        return "Programs{" +
                "nomeDoPrograma='" + nomeDoPrograma + '\'' +
                ", nivelDoPrograma='" + nivelDoPrograma + '\'' +
                ", duracao='" + duracao + '\'' +
                ", horario='" + horario + '\'' +
                ", descricao='" + descricao + '\'' +
                ", matriculados=" + matriculados +
                '}';
    }
}
