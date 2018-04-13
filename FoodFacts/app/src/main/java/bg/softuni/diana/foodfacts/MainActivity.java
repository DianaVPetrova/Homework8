package bg.softuni.diana.foodfacts;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import bg.softuni.diana.foodfacts.api.APIService;
import bg.softuni.diana.foodfacts.api.Model;
import bg.softuni.diana.foodfacts.api.Product;
import bg.softuni.diana.foodfacts.data.AppDatabase;
import bg.softuni.diana.foodfacts.data.ProductItem;
import bg.softuni.diana.foodfacts.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private AppDatabase db;
    private APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //hide description layout
        binding.llProductInfo.setVisibility(View.INVISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.strURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIService.class);

        AsyncTask.execute(() ->
                db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "products").build());


        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llProductInfo.setVisibility(View.INVISIBLE);
                String code = getCode();
                searchForProduct(code);
            }
        });
    }


    private String getCode() {
        return binding.etCode.getText().toString();
    }

    private void searchForProduct(String code) {

        if (code.isEmpty()) {
            showWrongCodeError();
            return;
        }

        new SearchProducts().execute(code);

    }


    private void searchOnline(String code) {

        service.getCurrentWeather(code).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model model = response.body();
                if(model.getProduct()!=null){
                ProductItem productItem = new ProductItem(model.getProduct().getCode(),
                        model.getProduct().getProduct_name(),
                        model.getProduct().getIngredients_text());
                new SaveProduct().execute(productItem);}
                else {
                    showWrongCodeError();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }

    private void showWrongCodeError(){
        Toast.makeText(this, R.string.enter_valid_code, Toast.LENGTH_SHORT).show();

    }

    private void showProductInfo(ProductItem productItem) {

        binding.llProductInfo.setVisibility(View.VISIBLE);
        binding.txtProductName.setText(productItem.getProduct_name());
        binding.txtProductDescription.setText(productItem.getIngredients_text());
    }


    private class SearchProducts extends AsyncTask<String, Integer, ProductItem> {
        String code = "";

        protected ProductItem doInBackground(String... codes) {

            code = codes[0];
            try {
                ProductItem productItem = db.productDao().findByCode(code);
                return productItem;
            } catch (Exception e) {
                return null;
            }
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(ProductItem productItem) {
            if (productItem != null) {
                    showProductInfo(productItem);
            } else {

                searchOnline(code);
                Log.d("Err", "ee");
            }

        }
    }




    private class SaveProduct extends AsyncTask<ProductItem,Integer,Boolean>

    {
        ProductItem productItem;
        protected Boolean doInBackground (ProductItem...products){

           try {
                db.productDao().insertProducts(products);
                productItem = products[0];
                return true;
            } catch (Exception e) {
                return false;
            }
    }

        protected void onProgressUpdate (Integer...progress){
    }

        protected void onPostExecute (Boolean isOK){
        if (isOK) {
            // TODO: show info to the user?
            showProductInfo(productItem);

        } else {
            Log.d("Err", "ee");
        }

    }

    }
}
