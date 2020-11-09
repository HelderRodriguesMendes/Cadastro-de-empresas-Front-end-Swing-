/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroempresas.retrofit.repository_retrofit;

import java.util.List;
import model.Company;
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
public interface CompanhiaRepository {
    
    @POST("/company/cadastrar")
    Call<Boolean> salvar(@Body Company company);
    
    @PUT("/company/alterar/{id}")
    Call<Boolean> alterar(@Path("id") Long id, @Body Company company);
    
    @GET("/company/findAllCompany")
    Call<List<Company>> getCompanys();
    
    @GET("/company/findAllCompany/name")
    Call<List<Company>> getCompanys_name(@Query("name") String name);
    
    @GET("/company/findAllCompany/{id}")
    Call<Company> getCompany_detalhada(@Path("id") Long id);
}
