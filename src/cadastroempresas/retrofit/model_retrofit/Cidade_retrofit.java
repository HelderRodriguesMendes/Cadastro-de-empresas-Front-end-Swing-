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
public class Cidade_retrofit implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
        private String nome;
        private Microrregiao microrregiao;
        
        public Cidade_retrofit(){
            
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
     * @return the microrregiao
     */
    public Microrregiao getMicrorregiao() {
        return microrregiao;
    }

    /**
     * @param microrregiao the microrregiao to set
     */
    public void setMicrorregiao(Microrregiao microrregiao) {
        this.microrregiao = microrregiao;
    }
        
        
}
