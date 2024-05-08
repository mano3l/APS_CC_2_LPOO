package unip.aps.models;

import java.util.Arrays;

public class Programs {
    private  String noemDoPrograma;
    private String niveldoCurso;
    private String duracao;
    private String horario;
    private String descricao;
    private String[] matriculados;

    public String getNoemDoPrograma() {
        return noemDoPrograma;
    }

    public void setNoemDoPrograma(String noemDoPrograma) {
        this.noemDoPrograma = noemDoPrograma;
    }

    public String getNiveldoCurso() {
        return niveldoCurso;
    }

    public void setNiveldoCurso(String niveldoCurso) {
        this.niveldoCurso = niveldoCurso;
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

    public String[] getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(String[] matriculados) {
        this.matriculados = matriculados;
    }

    @Override
    public String toString() {
        return "Programs{" +
                "noemDoPrograma='" + noemDoPrograma + '\'' +
                ", niveldoCurso='" + niveldoCurso + '\'' +
                ", duracao='" + duracao + '\'' +
                ", horario='" + horario + '\'' +
                ", descricao='" + descricao + '\'' +
                ", matriculados=" + Arrays.toString(matriculados) +
                '}';
    }
}
