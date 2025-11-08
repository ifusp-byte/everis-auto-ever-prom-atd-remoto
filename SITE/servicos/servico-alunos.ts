//servicos/servico-alunos
"use strcit";
import { Aluno } from "../modelos";
import { Contacto } from "../modelos";
import { TipoContacto } from "../modelos";

export class ServicoAlunos{
    //simular web service com promise

    obtemTodos():Promise<Aluno[]>{
        let alunos: Aluno[] = [];

        let aluno1 = new Aluno("Felipe Lucas Gewers");
        aluno1.adicionaContacto(Contacto.criaEmail("felipe@usp.com"));
        aluno1.adicionaContacto(Contacto.criaTelefone("111111111"));
        alunos.push(aluno1);

        let aluno2 = new Aluno ("Mariana");
        aluno1.adicionaContacto(Contacto.criaEmail("mariana@usp.com"));
        aluno1.adicionaContacto(Contacto.criaTelefone("222222222"));
        alunos.push(aluno2);
        
        return Promise.resolve(alunos); 
    }
}