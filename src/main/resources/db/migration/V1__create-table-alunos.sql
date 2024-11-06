create table alunos (
    id int primary key not null auto_increment,
    nome varchar(100),
    email varchar(100),
    matricula varchar(20),
    data_nascimento date
)