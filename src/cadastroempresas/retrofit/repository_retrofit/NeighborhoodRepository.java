/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroempresas.retrofit.repository_retrofit;

import java.util.List;
import model.Neighborhood;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *
 * @author helde
 */
public interface NeighborhoodRepository {
    @GET("/neighborhood/findAllCityState/name?")
    Call<List<Neighborhood>> getNeighborhoodCadastrados(@Query("name") String name);
    
    @POST("/neighborhood/cadastrar")
    Call<Boolean> salvar(@Body Neighborhood neighborhood);
}
