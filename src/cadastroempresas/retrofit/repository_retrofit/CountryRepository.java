/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroempresas.retrofit.repository_retrofit;

import java.util.List;
import model.Country;
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
public interface CountryRepository {
    
    @GET("/country/findAllCountry")
    Call<List<Country>> getCountrysCadastrados();
    
    @GET("/country/findAllCountry/name?")
    Call<List<Country>> getCountrysCadastrados_Nome(@Query("name") String name);
    
    @POST("/country/cadastrar")
    Call<Boolean> salvar(@Body Country country);
    
    @PUT("/country/alterar/{id}")
    Call<Boolean> alterar(@Path("id") Long id, @Body Country country);
}
