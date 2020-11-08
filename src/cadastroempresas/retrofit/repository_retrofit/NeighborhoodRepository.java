/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroempresas.retrofit.repository_retrofit;

import java.util.List;
import model.City;
import model.Neighborhood;
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
public interface NeighborhoodRepository {

    @POST("/neighborhood/cadastrar")
    Call<Boolean> salvar(@Body Neighborhood neighborhood);

    @GET("/neighborhood/findAllNeighborhoodNameCity/name?")
    Call<List<Neighborhood>> getNeighborhoodCadastradosFK(@Query("name") String name);

    @PUT("/neighborhood/alterar/{id}")
    Call<Boolean> alterar(@Path("id") Long id, @Body Neighborhood neighborhood);

    @GET("/neighborhood/findAllNeighborhood")
    Call<List<Neighborhood>> getNeighborhoodCadastrados();
    
    @GET("/neighborhood/findAllNeighborhood/name")
    Call<List<Neighborhood>> getNeighborhoodCadastrados_name(@Query("name") String name);
}
