/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroempresas.retrofit.repository_retrofit;

import model.Company;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 *
 * @author helde
 */
public interface CompanhiaRepository {
    
    @POST("/company/cadastrar")
    Call<Boolean> salvar(@Body Company company);
    
    @PUT("/company/alterar/{id}")
    Call<Boolean> alterar(@Path("id") Long id, @Body Company company);
}
