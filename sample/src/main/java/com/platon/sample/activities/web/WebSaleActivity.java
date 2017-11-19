package com.platon.sample.activities.web;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.platon.sample.R;
import com.platon.sample.activities.BaseActivity;
import com.platon.sample.adapters.ProductPagerAdapter;
import com.platon.sdk.callback.PlatonWebCallback;
import com.platon.sdk.core.PlatonSdk;
import com.platon.sdk.model.request.option.web.PlatonWebSaleOptions;
import com.platon.sdk.model.request.order.product.PlatonProductSale;
import com.platon.sdk.model.request.payer.PlatonPayerWebSale;
import com.slmyldz.random.Randoms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import io.kimo.lib.faker.Faker;
import retrofit2.Call;
import retrofit2.Response;

import static com.platon.sample.app.PlatonApp.isValidURL;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;

public class WebSaleActivity extends BaseActivity implements
        ProductPagerAdapter.OnItemCountChangeListener,
        View.OnClickListener,
        PlatonWebCallback {

    private Button mBtnRandomize;
    private Button mBtnAddProduct;
    private ViewPager mVpProducts;
    private EditText mEtxtSuccessUrl;
    private EditText mEtxtOrderId;
    private EditText mEtxtPayerFirstName;
    private EditText mEtxtPayerLastName;
    private EditText mEtxtPayerAddress;
    private EditText mEtxtPayerCountryCode;
    private EditText mEtxtPayerState;
    private EditText mEtxtPayerCity;
    private EditText mEtxtPayerZip;
    private EditText mEtxtPayerEmail;
    private EditText mEtxtPayerPhone;
    private EditText mEtxtLanguage;
    private EditText mEtxtErrorUrl;
    private EditText mEtxtFormId;
    private EditText mEtxtExt1;
    private EditText mEtxtExt2;
    private EditText mEtxtExt3;
    private EditText mEtxtExt4;
    private Button mBtnSale;

    private ProductPagerAdapter mProductPagerAdapter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_sale);

        assignViews();
        configureViews();
    }

    private void assignViews() {
        mBtnRandomize = findViewById(R.id.btn_randomize);
        mBtnAddProduct = findViewById(R.id.btn_add_product);
        mVpProducts = findViewById(R.id.vp_products);
        mEtxtSuccessUrl = findViewById(R.id.etxt_success_url);
        mEtxtOrderId = findViewById(R.id.etxt_order_id);
        mEtxtPayerFirstName = findViewById(R.id.etxt_payer_first_name);
        mEtxtPayerLastName = findViewById(R.id.etxt_payer_last_name);
        mEtxtPayerAddress = findViewById(R.id.etxt_payer_address);
        mEtxtPayerCountryCode = findViewById(R.id.etxt_payer_country_code);
        mEtxtPayerState = findViewById(R.id.etxt_payer_state);
        mEtxtPayerCity = findViewById(R.id.etxt_payer_city);
        mEtxtPayerZip = findViewById(R.id.etxt_payer_zip);
        mEtxtPayerEmail = findViewById(R.id.etxt_payer_email);
        mEtxtPayerPhone = findViewById(R.id.etxt_payer_phone);
        mEtxtLanguage = findViewById(R.id.etxt_language);
        mEtxtErrorUrl = findViewById(R.id.etxt_error_url);
        mEtxtFormId = findViewById(R.id.etxt_form_id);
        mEtxtExt1 = findViewById(R.id.etxt_ext_1);
        mEtxtExt2 = findViewById(R.id.etxt_ext_2);
        mEtxtExt3 = findViewById(R.id.etxt_ext_3);
        mEtxtExt4 = findViewById(R.id.etxt_ext_4);
        mBtnSale = findViewById(R.id.btn_sale);
    }

    private void configureViews() {
        mBtnRandomize.setOnClickListener(this);
        mBtnAddProduct.setOnClickListener(this);
        mBtnSale.setOnClickListener(this);

        mProductPagerAdapter = new ProductPagerAdapter(this);
        mProductPagerAdapter.setOnItemCountChangeListener(this);
        mVpProducts.setAdapter(mProductPagerAdapter);

        randomize();
    }

    private void randomize() {
        final Random random = new Random();

        final List<PlatonProductSale> productSales = new ArrayList<>();
        final int size = random.nextInt(5) + 1;
        for (int i = 0; i < size; i++) {
            final PlatonProductSale productSale = new PlatonProductSale(
                    Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F),
                    Faker.with(this).Lorem.sentences()
            );

            productSale.setCurrencyCode("UAH");
            productSale.setSelected(false);
            productSale.setRecurring(random.nextBoolean());

            productSales.add(productSale);
        }
        productSales.get(random.nextInt(productSales.size())).setSelected(true);
        mProductPagerAdapter.setProductSales(productSales);

        mEtxtSuccessUrl.setText(Faker.with(this).Internet.url());
        mEtxtOrderId.setText(String.valueOf(UUID.randomUUID()));

        mEtxtPayerFirstName.setText(Faker.with(this).Name.firstName());
        mEtxtPayerLastName.setText(Faker.with(this).Name.lastName());
        mEtxtPayerAddress.setText(Faker.with(this).Address.secondaryAddress());
        mEtxtPayerCountryCode.setText(Faker.with(this).Address.countryAbbreviation());
        mEtxtPayerState.setText(Faker.with(this).Address.state());
        mEtxtPayerCity.setText(Faker.with(this).Address.city());
        mEtxtPayerZip.setText(Faker.with(this).Address.zipCode());
        mEtxtPayerEmail.setText(Faker.with(this).Internet.email());
        mEtxtPayerPhone.setText(Faker.with(this).Phone.phoneWithAreaCode());

        final int randomLanguage = random.nextInt(3);
        final String language;
        switch (randomLanguage) {
            case 0:
                language = "RU";
                break;
            case 1:
                language = "EN";
                break;
            case 2:
            default:
                language = "UK";
                break;
        }
        mEtxtLanguage.setText(language);
        mEtxtErrorUrl.setText(Faker.with(this).Internet.url());
//        mEtxtFormId.setText(UUID.randomUUID().toString());
        mEtxtExt1.setText(Faker.with(this).Url.avatar());
        mEtxtExt2.setText(Faker.with(this).Url.avatar());
        mEtxtExt3.setText(Faker.with(this).Url.avatar());
        mEtxtExt4.setText(Faker.with(this).Url.avatar());
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btn_randomize:
                randomize();
                break;
            case R.id.btn_add_product:
                mProductPagerAdapter.addProduct(new PlatonProductSale(0.00F, ""));
                mVpProducts.setCurrentItem(mProductPagerAdapter.getCount() - 1);
                break;
            case R.id.btn_sale:
                final String successUrl = String.valueOf(mEtxtSuccessUrl.getText());
                if (!isValidURL(successUrl)) {
                    Toast.makeText(this, "Invalid success url", Toast.LENGTH_SHORT).show();
                    return;
                }

                final List<PlatonProductSale> productSales = mProductPagerAdapter.getProductSales();
                int selectedProducts = 0;
                for (int i = productSales.size() - 1; i >= 0; i--) {
                    if (selectedProducts > 1) {
                        Toast.makeText(this, "Only 1 selected product allowed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (productSales.get(i).isSelected()) selectedProducts++;
                }

                final PlatonPayerWebSale payer = new PlatonPayerWebSale.Builder()
                        .firstName(String.valueOf(mEtxtPayerFirstName.getText()))
                        .lastName(String.valueOf(mEtxtPayerLastName.getText()))
                        .address(String.valueOf(mEtxtPayerAddress.getText()))
                        .countryCode(String.valueOf(mEtxtPayerCountryCode.getText()))
                        .state(String.valueOf(mEtxtPayerState.getText()))
                        .city(String.valueOf(mEtxtPayerCity.getText()))
                        .zip(String.valueOf(mEtxtPayerZip.getText()))
                        .email(String.valueOf(mEtxtPayerEmail.getText()))
                        .phone(String.valueOf(mEtxtPayerPhone.getText()))
                        .build();

                final PlatonWebSaleOptions webSaleOptions = new PlatonWebSaleOptions.Builder()
                        .language(String.valueOf(mEtxtLanguage.getText()))
                        .errorUrl(String.valueOf(mEtxtErrorUrl.getText()))
                        .formId(String.valueOf(mEtxtFormId.getText()))
                        .ext1(String.valueOf(mEtxtExt1.getText()))
                        .ext2(String.valueOf(mEtxtExt2.getText()))
                        .ext3(String.valueOf(mEtxtExt3.getText()))
                        .ext4(String.valueOf(mEtxtExt4.getText()))
                        .build();

                showProgress();
                PlatonSdk.WebPayments.getSaleAdapter().sale(
                        productSales,
                        successUrl,
                        String.valueOf(mEtxtOrderId.getText()),
                        payer,
                        webSaleOptions,
                        this
                );
                break;
            default:
                break;
        }
    }

    @Override
    public void onResponse(final Call<String> call, final Response<String> response) {
        hideProgress();
        Log.d("Response: ", response.toString());

        final CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        final CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(response.raw().request().url().toString()));
    }

    @Override
    public void onFailure(final Call<String> call, final Throwable t) {
        hideProgress();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onItemCountChanged() {
        mBtnAddProduct.setText(String.format("ADD_PRODUCT(%d)", mProductPagerAdapter.getCount()));
    }
}
