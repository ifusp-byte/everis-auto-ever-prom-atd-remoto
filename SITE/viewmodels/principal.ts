/// <reference path="../typings/index.d.ts" />
import { Aluno, Contacto, TipoContacto } from "../modelos/index";
import { ServicoAlunos } from "../servicos/servico-alunos"; 

export class Principal {
    filtro: KnockoutObservable<string>;
    alunosFiltrados: KnockoutComputed<Aluno[]>;
    alunos: KnockoutObservableArray<Aluno>;
    alunoAtual: Aluno;
    nomeAtual: KnockoutObservable<string>;
    contactoAtual: KnockoutObservable<string>;
    tipoContactoAtual: KnockoutObservable<TipoContacto>;
    tipoContactoAtualEdicao: KnockoutComputed<string>;
    contactos: KnockoutObservableArray<Contacto>;
    emEdicao: boolean; 

    constructor(private servico: ServicoAlunos){
        this.preparaBindings();
        this.iniciaAluno();
        this.servico.obtemTodos()
        .then(als => this.alunos(als)); 
    }

    preparaBindings(){
        this.alunos = ko.observableArray([]);
        this.alunosFiltrados = ko.computed({
            read: () => this.alunos()
            .filter(a => 
                a.nome.toUpperCase().startsWith(this.filtro().toUpperCase()))
        })
        this.filtro = ko.observable("");
        this.nomeAtual = ko.observable("").extend({
            required: {
                message: "O nome do aluno é de preenchimento obrigatório."
            }
        });
        this.contactoAtual = ko.observable("").extend({
            required: {
                message: "O campo contacto é de preenchimento obrigatório."
            },
            email: {
                message: "O email não está no formato correto.",
                onlyIf: () => this.tipoContactoAtual() === TipoContacto.Email
            },
            pattern: {
                message: "O formato do número de telefone não está correto.",
                params: "^\\d{9}$",
                onlyIf: () => this.tipoContactoAtual() === TipoContacto.Telefone
            }
        });

        this.tipoContactoAtual = ko.observable(TipoContacto.Telefone);
        this.tipoContactoAtualEdicao = ko.computed({
            read: () => {
                return this.tipoContactoAtual().toString()
            },
            write: (val) => this.tipoContactoAtual(parseInt(val))
        });

        this.contactos = ko.observableArray([]); 
    }

    iniciaAluno(){
        this.alunoAtual = new Aluno ("");
        this.iniciaFormularioEdicao();
    }

    iniciaFormularioEdicao(){
        this.nomeAtual(this.alunoAtual.nome);
        this.iniciaFormularioContactos();
        $("#nome").focus();
    }

    iniciaFormularioContactos(){
        this.limpaCamposContacto();
        this.contactos(this.alunoAtual.obtemContactosParaEdicao());
    }

    limpaCamposContacto(){
        this.tipoContactoAtual(TipoContacto.Telefone);
        this.contactoAtual("");
    }

    selecionaAluno(aluno: Aluno){
        if(this.emEdicao){
            alert("Por favor, termine a edição do outro contacto.");
            return;
        }
        this.alunoAtual = aluno;
        this.alunos.remove(this.alunoAtual);
        this.iniciaFormularioEdicao();
        this.emEdicao = true; 
    }

    adicionaContacto(){
        var ct = new Contacto (this.contactoAtual(), this.tipoContactoAtual());
        this.contactos.push(ct);
        this.limpaCamposContacto();
        $("#registro").focus();
    }

    removeContacto(ct: Contacto){
        if(!ct){
            return; 
        }
        this.contactos.remove(ct);
    }

    cancelaEdicao(){
        if(!this.emEdicao){
            return;
        }
        this.alunos.push(this.alunoAtual);
        this.iniciaAluno();
        this.emEdicao = false;
    }

    gravaEdicao(){
        if(!this.alunoAtual){
            return; 
        }
        this.alunoAtual.nome = this.nomeAtual();
        this.alunoAtual.contactos = this.contactos();
        this.alunos.push(this.alunoAtual);
        this.iniciaAluno();
        this.emEdicao = false;
    }
}