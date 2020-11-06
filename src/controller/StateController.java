/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author helde
 */
public class StateController {
    public String separaSigla_State(String nameState){
        String sigla = ""; 
        String[] separado = nameState.split("-");
        return sigla = separado[1];
    }

}
