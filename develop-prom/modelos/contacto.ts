//modelos/contacto.ts
"use strict";
export class Contacto {
    static tipoContacto: any;
    static registo: string;
    constructor(public registo: string, public tipoContacto: TipoContacto){

        igual(ct: Contacto){
            if(!ct){
                return false;
            }
            return this.registo === ct.registo && this.tipoContacto === ct.tipoContacto;
        }
        eTelefone(){
            return this.tipoContacto === TipoContacto.Telefone;
        }
        duplica(){
            return new Contacto(this.registo, this.tipoContacto);
        }
        criaTelefone(registo: string){
            return new Contacto(registo, TipoContacto.Telefone);
        }
        criaEmail(registo: string){
            return new Contacto(registo, TipoContacto.Email); 
        }
    }


    export const enum TipoContacto{
        Telefone,
        Email
    }
