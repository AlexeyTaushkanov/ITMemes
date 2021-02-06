package com.hfad.picasso.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hfad.picasso.Example;
import com.hfad.picasso.Result;
import com.hfad.picasso.api.Client;
import com.hfad.picasso.api.ApiService;
import com.hfad.picasso.model.Data;
import com.hfad.picasso.util.InternetConnection;
import com.hfad.picasso.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class MainActivity extends AppCompatActivity {

    private ImageView _imageViewGif;
    private TextView _textViewDescription;
    private Button _buttonBack;
    private ArrayList<String> _descriptionDataRandom;
    private ArrayList<String> _getGifURLDataRandom;
    private ArrayList<String> _descriptionDataLatest;
    private ArrayList<String> _getGifURLDataLatest;
    private ArrayList<String> _descriptionDataTop;
    private ArrayList<String> _getGifURLDataTop;
    private ApiService _api;
    private Spinner _spinner;
    private List<Result> resultListLatest;
    private List<Result> resultListHot;
    private String _spinnerSelected;
    private int _counterLatest1;
    private int _counterLatest2;
    private int _pageLatest;
    private int _counterTop1;
    private int _counterTop2;
    private int _pageTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        _api = Client.getApiService(getString(R.string.baseURL));
        _descriptionDataRandom = new ArrayList();
        _getGifURLDataRandom = new ArrayList();
        _descriptionDataLatest = new ArrayList();
        _getGifURLDataLatest = new ArrayList();
        _descriptionDataTop = new ArrayList<>();
        _getGifURLDataTop = new ArrayList<>();

        _imageViewGif = findViewById(R.id.imageView);
        _textViewDescription = findViewById(R.id.textView);
        _buttonBack = findViewById(R.id.back_bottom);
        _spinner = findViewById(R.id.spinner);


        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.categories);

                switch (selectedItemPosition){
                    case 0: _spinnerSelected = choose[selectedItemPosition];
                    if (_descriptionDataRandom.size() == 1) {
                        _textViewDescription.setText(_descriptionDataRandom.get(_descriptionDataRandom.size()-1));
                        setGif(_getGifURLDataRandom.get(_getGifURLDataRandom.size()-1));
                        _buttonBack.setVisibility(View.INVISIBLE);
                    } else if (_descriptionDataRandom.size() >= 2){
                        _textViewDescription.setText(_descriptionDataRandom.get(_descriptionDataRandom.size()-1));
                        setGif(_getGifURLDataRandom.get(_getGifURLDataRandom.size()-1));
                        _buttonBack.setVisibility(View.VISIBLE);
                    } else {
                        showGifRandom();
                    }
                        break;
                    case 1: _spinnerSelected = choose[selectedItemPosition];
                        if (_counterTop1 == 1) {
                            setGif(_getGifURLDataTop.get(_counterTop2));
                            _textViewDescription.setText(_descriptionDataTop.get(_counterTop2));
                            _buttonBack.setVisibility(View.INVISIBLE);
                        } else if (_counterTop1 >= 2){
                            setGif(_getGifURLDataTop.get(_counterTop2));
                            _textViewDescription.setText(_descriptionDataTop.get(_counterTop2));
                            _buttonBack.setVisibility(View.VISIBLE);
                        } else {
                            showGifTop();
                            _buttonBack.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case 2: _spinnerSelected = choose[selectedItemPosition];
                        if (_counterLatest1 == 1) {
                            setGif(_getGifURLDataLatest.get(_counterLatest2));
                            _textViewDescription.setText(_descriptionDataLatest.get(_counterLatest2));
                            _buttonBack.setVisibility(View.INVISIBLE);
                        } else if (_counterLatest1 >= 2){
                            setGif(_getGifURLDataLatest.get(_counterLatest2));
                            _textViewDescription.setText(_descriptionDataLatest.get(_counterLatest2));
                            _buttonBack.setVisibility(View.VISIBLE);
                        } else {
                            showGifLatest();
                            _buttonBack.setVisibility(View.INVISIBLE);
                        }
                        break;
                    default: Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClickNext(View view) {
        switch (_spinnerSelected) {
            case "Random": showGifRandom();
                /*Toast.makeText(getApplicationContext(), spinnerSelected, Toast.LENGTH_SHORT).show();*/
            break;
            case "Hot": Toast.makeText(getApplicationContext(), _spinnerSelected, Toast.LENGTH_SHORT).show();
            break;
            case "Top":

                showGifTop();

                _counterTop2 +=1;

            break;
            case "Latest":

                showGifLatest();

                _counterLatest2 += 1;

                break;
            default: Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            break;
        }
    }

    private void showGifTop() {

        if (_descriptionDataTop.size() == _counterTop1) {

            Call<Example> callTop = _api.getTop(_pageTop);

            callTop.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {

                    Example exampleTop = response.body();

                    resultListHot = new ArrayList<>(Arrays.asList(exampleTop.getResult()));

                    for (Result resultTop : resultListHot) {
                        _descriptionDataTop.add(resultTop.getDescription());
                        _getGifURLDataTop.add(resultTop.getGifURL());
                    }

                    _pageTop += 1;

                    setGif(_getGifURLDataTop.get(_counterTop1));
                    _textViewDescription.setText(_descriptionDataTop.get(_counterTop1));

                    _counterTop1 += 1;
                }
                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getString(R.string.loadError), Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        } else {
            setGif(_getGifURLDataTop.get(_counterTop1));
            _textViewDescription.setText(_descriptionDataTop.get(_counterTop1));

            _counterTop1 += 1;
        }
        if (_counterTop1 > 0); {
            _buttonBack.setVisibility(View.VISIBLE);
        }
    }

    private void showGifLatest() {

        if (_descriptionDataLatest.size() == _counterLatest1) {

                Call<Example> callLatest = _api.getExample(_pageLatest);

                callLatest.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {


                        Example exampleLatest = response.body();
                        resultListLatest = new ArrayList<>(Arrays.asList(exampleLatest.getResult()));

                        for (Result resultLatest : resultListLatest) {
                                _descriptionDataLatest.add(resultLatest.getDescription());
                                _getGifURLDataLatest.add(resultLatest.getGifURL());
                            }
                            _pageLatest += 1;

                        setGif(_getGifURLDataLatest.get(_counterLatest1));
                        _textViewDescription.setText(_descriptionDataLatest.get(_counterLatest1));

                        _counterLatest1 += 1;
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                getString(R.string.loadError), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            } else {
            setGif(_getGifURLDataLatest.get(_counterLatest1));
            _textViewDescription.setText(_descriptionDataLatest.get(_counterLatest1));

            _counterLatest1 += 1;

                    }
        if (_counterLatest1 > 0); {
            _buttonBack.setVisibility(View.VISIBLE);
        }
    }

    private void showGifRandom() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle(getString(R.string.wait));
            dialog.setMessage(getString(R.string.gettingData));
            dialog.show();

            Call<Data> callRand = _api.getRandomGifDescription();

            callRand.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    dialog.dismiss();
                    String gifDescription = response.body().getDescription();;
                    String gifURL = response.body().getGifURL();
                    if ( gifDescription != null && gifURL != null ) {
                        _textViewDescription.setText(gifDescription);

                        _descriptionDataRandom.add(gifDescription);
                        _getGifURLDataRandom.add(gifURL);

                        setGif(gifURL);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                getString(R.string.loadError), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    dialog.dismiss();
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getString(R.string.loadError), Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.connectionError), Toast.LENGTH_SHORT);
            toast.show();
        }
        if (_descriptionDataRandom.size() > 0) {
            _buttonBack.setVisibility(View.VISIBLE);
        }
    }

    public void BackRandom() {
        _textViewDescription.setText(_descriptionDataRandom.get(_descriptionDataRandom.size()-2));
        setGif(_getGifURLDataRandom.get(_getGifURLDataRandom.size()-2));

        _descriptionDataRandom.remove(_descriptionDataRandom.size() - 1);
        _getGifURLDataRandom.remove(_getGifURLDataRandom.size() - 1);

        if (_descriptionDataRandom.size() <= 1) {
            _buttonBack.setVisibility(View.INVISIBLE);
        }
    }

    private void BackTop() {

        _counterTop1 -= 1;

        _counterTop2 -= 1;

        setGif(_getGifURLDataTop.get(_counterTop2));
        _textViewDescription.setText(_descriptionDataTop.get(_counterTop2));

        if (_counterTop2 < 1) {
            _buttonBack.setVisibility(View.INVISIBLE);
        }
    }

    private void BackLatest () {

        _counterLatest2 -= 1;

        _counterLatest1 -= 1;

        setGif(_getGifURLDataLatest.get(_counterLatest2));
        _textViewDescription.setText(_descriptionDataLatest.get(_counterLatest2));
        if (_counterLatest2 < 1) {
            _buttonBack.setVisibility(View.INVISIBLE);
        }
    }

    public void onClickBack(View view) {
        switch (_spinnerSelected) {
            case "Random": BackRandom();
                break;
            case "Top": BackTop();
                break;
            case "Latest": BackLatest();
                break;
            default: Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setGif(String gifURL) {

        String URL = new StringBuilder(gifURL).insert(4, "s").toString();

        if ( gifURL != null) {
            Glide.with(this)
                    .load(URL)
                    .apply(centerCropTransform()
                            .placeholder(R.drawable.progress_bar)
                            .error(R.drawable.error))
                    .into(_imageViewGif);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.loadError), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}