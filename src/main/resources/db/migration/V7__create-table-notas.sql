create table notas(
    id int not null primary key auto_increment,
    matricula_id int,
    disciplina_id int,
    nota numeric(5, 2),
    data_lancamento date,
    foreign key (matricula_id) references matriculas(id),
    foreign key (disciplina_id) references disciplinas(id)
)