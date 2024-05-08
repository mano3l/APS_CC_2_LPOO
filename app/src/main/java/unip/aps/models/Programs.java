package unip.aps.models;

import java.util.Arrays;

public class Programs {
    private String nomeDoPrograma;
    private String nivelDoCurso;
    private String horario;
    private String descricao;
    private String[] matriculados;

    public String getNomeDoPrograma() {
        return nomeDoPrograma;
    }

    public void setNomeDoPrograma(String nomeDoPrograma) {
        this.nomeDoPrograma = nomeDoPrograma;
    }

    public String getNivelDoCurso() {
        return nivelDoCurso;
    }

    public void setNivelDoCurso(String nivelDoCurso) {
        this.nivelDoCurso = nivelDoCurso;
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

    public String[] getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(String[] matriculados) {
        this.matriculados = matriculados;
    }

    @Override
    public String toString() {
        return "Programs{" +
                "nomeDoPrograma='" + nomeDoPrograma + '\'' +
                ", nivelDoCurso='" + nivelDoCurso + '\'' +
                ", horario='" + horario + '\'' +
                ", descricao='" + descricao + '\'' +
                ", matriculados=" + Arrays.toString(matriculados) +
                '}';
    }
}
