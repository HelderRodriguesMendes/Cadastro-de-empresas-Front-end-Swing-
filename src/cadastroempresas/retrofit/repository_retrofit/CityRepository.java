/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroempresas.retrofit.repository_retrofit;

import java.util.List;
import model.City;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *
 * @author helde
 */
public interface CityRepository {
    @GET("/city/findAllCityState/name?")
    Call<List<City>> getCitysCadastrados(@Query("name") String name);
    
    @POST("/city/cadastrar")
    Call<Boolean> salvar(@Body City city);
}
