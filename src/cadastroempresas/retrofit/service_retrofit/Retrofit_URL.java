/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroempresas.retrofit.service_retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author helde
 */
public class Retrofit_URL {
    Retrofit retrofit;
	public Retrofit BaseURL_estado() {
		retrofit = new Retrofit.Builder().baseUrl("https://servicodados.ibge.gov.br/")
                .addConverterFactory(GsonConverterFactory.create()).build();
		return retrofit;
	}
        
        public Retrofit BaseURL() {
		retrofit = new Retrofit.Builder().baseUrl("http:192.168.1.3:8080/")
                .addConverterFactory(GsonConverterFactory.create()).build();
		return retrofit;
	}
}
