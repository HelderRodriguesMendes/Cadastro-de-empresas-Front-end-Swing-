/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroempresas.retrofit.repository_retrofit;

import java.util.List;

import model.State;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * @author helde
 */
public interface StateRepository {
    
    @GET("/state/findAllStateCountry/name?")
    Call<List<State>> getStatesCadastrados(@Query("name") String name);
    
    @POST("/state/cadastrar")
    Call<Boolean> salvar(@Body State state);
    
    @PUT("/state/alterar/{id}")
    Call<Boolean> alterar(@Path("id") Long id, @Body State state);
}
