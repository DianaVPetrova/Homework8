package bg.softuni.diana.foodfacts.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    public static final String strURL = "https://world.openfoodfacts.org/";

    @GET("api/v0/product/{param}")
    Call<Model> getCurrentWeather(@Path("param") String code);

}
