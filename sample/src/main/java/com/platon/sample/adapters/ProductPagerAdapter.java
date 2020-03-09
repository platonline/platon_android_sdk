package com.platon.sample.adapters;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.platon.sample.R;
import com.platon.sdk.model.request.order.product.PlatonProductSale;
import com.slmyldz.random.Randoms;

import java.util.ArrayList;
import java.util.List;

import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MAX_AMOUNT;
import static com.platon.sdk.constant.api.PlatonApiConstants.Formats.Amount.MIN_AMOUNT;

public class ProductPagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    private final List<PlatonProductSale> mProductSales = new ArrayList<>();

    private OnItemCountChangeListener mOnItemCountChangeListener;

    public ProductPagerAdapter(final Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemCountChangeListener(final OnItemCountChangeListener onItemCountChangeListener) {
        mOnItemCountChangeListener = onItemCountChangeListener;
    }

    public List<PlatonProductSale> getProductSales() {
        return mProductSales;
    }

    public void setProductSales(final List<PlatonProductSale> productSales) {
        mProductSales.clear();
        mProductSales.addAll(productSales);
        notifyDataSetChanged();
    }

    public void addProduct(final PlatonProductSale productSale) {
        mProductSales.add(productSale);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (mOnItemCountChangeListener != null) mOnItemCountChangeListener.onItemCountChanged();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view = mLayoutInflater.inflate(R.layout.item_product, null);
        final PlatonProductSale productSale = mProductSales.get(position);

        final EditText etxtAmount = view.findViewById(R.id.etxt_item_product_amount);
        etxtAmount.setText(String.valueOf(productSale.getAmount()));
        etxtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                try {
                    productSale.setAmount(Float.parseFloat(String.valueOf(editable)));
                } catch (final Exception e) {
                    productSale.setAmount(Randoms.Float(MIN_AMOUNT, MAX_AMOUNT * 2.0F));
                }
            }
        });

        final EditText etxtDescription = view.findViewById(R.id.etxt_item_product_description);
        etxtDescription.setText(productSale.getDescription());
        etxtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                productSale.setDescription(String.valueOf(editable));
            }
        });

        final EditText etxtCurrency = view.findViewById(R.id.etxt_item_product_currency);
        etxtCurrency.setText(productSale.getCurrencyCode());
        etxtCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                productSale.setCurrencyCode(String.valueOf(editable));
            }
        });

        final CheckBox cbSelected = view.findViewById(R.id.cb_item_product_selected);
        cbSelected.setChecked(productSale.isSelected());
        cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                productSale.setSelected(b);
            }
        });

        final CheckBox cbRecurring = view.findViewById(R.id.cb_item_product_recurring);
        cbRecurring.setChecked(productSale.isRecurring());
        cbRecurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                productSale.setRecurring(b);
            }
        });

        view.findViewById(R.id.btn_item_product_remove).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        mProductSales.remove(position);
                        notifyDataSetChanged();
                    }
                }
        );

        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mProductSales.size();
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    public interface OnItemCountChangeListener {
        void onItemCountChanged();
    }

}
