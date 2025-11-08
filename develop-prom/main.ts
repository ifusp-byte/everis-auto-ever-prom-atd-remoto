/// <reference path="typings/index.d.ts" />
"use strict";
import { Principal } from "./viewmodels/principal";
import { ServicoAlunos } from "./servicos/servico-alunos";
import { TipoContacto } from "./modelos/index";

let servico = new ServicoAlunos();
let principal = new Principal(servico);

(<any>ko.bindingHandlers).confirmClick = {
    init: function(element, valueAccessor, allBindings, viewModel) {
        var value = valueAccessor();
        var message = ko.unwrap(value.message);
        var click = value.click;
        ko.applyBindingsToNode(element, {click: function () {
            if(confirm(message))
            return click.apply(this, 
                Array.prototype.slice.apply(arguments));
            }}, allBindings);
        },
        update: function(element, valueAccessor){

        }
    }

    ko.validation.init({
        registerExtenders: true, 
        messagesOnModified: true, 
        errorMessageClass: "infoErro"
    });

    ko.applyBindings(principal); 

