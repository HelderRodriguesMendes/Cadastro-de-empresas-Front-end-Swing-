/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroempresas.retrofit.model_retrofit;

import java.io.Serializable;

/**
 *
 * @author helde
 */
public class Mesorregiao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
        private String nome;
        private Estado_retrofit UF;
        
        public Mesorregiao(){
            
        }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the UF
     */
    public Estado_retrofit getUF() {
        return UF;
    }

    /**
     * @param UF the UF to set
     */
    public void setUF(Estado_retrofit UF) {
        this.UF = UF;
    }
        
        
}
