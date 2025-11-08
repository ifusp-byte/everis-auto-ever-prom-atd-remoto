/// <reference path="typings/index.d.ts" />

(function(global){
    SystemJS.config({
        map: {
            app: "/js"
        },
        packages: {
            app: {
                main: "./main.js",
                defaultExtension: "js"
            }
        }
    });
})(this); 