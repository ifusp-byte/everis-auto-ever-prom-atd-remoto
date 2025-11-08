//modelos/aluno.ts
"use strict";
import { Contacto } from "../modelos";
export class Aluno{
    contactos: Contacto[];
    constructor(public nome: string){
        this.contactos = [];
    }
    adicionaContacto(contacto: Contacto){
        if(this.existeContacto(contacto)){
            return;
        }
        this.contactos.push(contacto);
    }
    removeContacto(contacto: Contacto){
        let pos = this.contactos.findIndex(c => c.igual(contacto));
        if(pos < 0){
            return;
        }
        this.contactos.splice(pos, 1);
    }
    obtemContactosParaEdicao(){
        return this.contactos.map(c => c.duplica());
    }
    private existeContacto(contacto: Contacto){
        let pos = this.contactos.findIndex(c => c.igual(contacto));
        return pos >= 0;
    }
}