    package unip.aps.models;


    public class Student {
        private String cpf;
        private String nome;
        private String sobrenome;
        private String endereco;
        private int idade;
        private String sexo;
        private String telefone;

        public Student(String cpf, String nome, String sobrenome, String endereco, int idade, String sexo, String telefone) {
            this.cpf = cpf;
            this.nome = nome;
            this.sobrenome = sobrenome;
            this.endereco = endereco;
            this.idade = idade;
            this.sexo = sexo;
            this.telefone = telefone;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getSobrenome() {
            return sobrenome;
        }

        public void setSobrenome(String sobrenome) {
            this.sobrenome = sobrenome;
        }

        public String getEndereco() {
            return endereco;
        }

        public void setEndereco(String endereco) {
            this.endereco = endereco;
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
            this.sexo = sexo;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        @Override
        public String toString() {
            return  "CPF: " + cpf + '\n' +
                    "Nome: " + nome + '\n' +
                    "Sobrenome: " + sobrenome + '\n' +
                    "Idade: " + idade + '\n' +
                    "Endereco: " + endereco + '\n' +
                    "Sexo: " + sexo + '\n' +
                    "Telefone: " + telefone + '\n';
        }
    }
