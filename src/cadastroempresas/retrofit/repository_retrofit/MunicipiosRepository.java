/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastroempresas.retrofit.repository_retrofit;

import cadastroempresas.retrofit.model_retrofit.Cidade_retrofit;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 *
 * @author helde
 */

public interface MunicipiosRepository {
    
    @GET("api/v1/localidades/estados/{UF}/municipios")
    Call<List<Cidade_retrofit>> getCidades(@Path("UF") String UF);
}
