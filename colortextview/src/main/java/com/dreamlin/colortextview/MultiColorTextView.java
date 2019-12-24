package com.dreamlin.colortextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;



import java.util.regex.Pattern;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class MultiColorTextView extends AppCompatTextView {

    private int defColor = Color.BLACK;
    private String spilt;

    public MultiColorTextView(Context context) {
        this(context, null);
    }

    public MultiColorTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initResources(context, attrs);
    }

    private void initResources(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiColorTextView);
        String texts = typedArray.getString(R.styleable.MultiColorTextView_texts);
        String colors = typedArray.getString(R.styleable.MultiColorTextView_colors);
        defColor = typedArray.getColor(R.styleable.MultiColorTextView_defColor, Color.BLACK);
        setTextColor(defColor);
        spilt = typedArray.getString(R.styleable.MultiColorTextView_spilt);
        if (TextUtils.isEmpty(spilt))
            spilt = "\\|";
        typedArray.recycle();
        parse(texts, colors, defColor);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void parse(String texts, String colors, @ColorInt int defColor) {
        if (TextUtils.isEmpty(texts) || TextUtils.isEmpty(colors))
            return;
        String[] textArray = texts.split(spilt);
        String[] colorArray = colors.split(spilt);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < textArray.length; i++) {
            String color = (colorArray.length > i && !TextUtils.isEmpty(colorArray[i]))
                    ? colorArray[i] : String.valueOf(defColor);
            String format = String.format("<font color=%s>%s</font>", color, textArray[i]);
            builder.append(format);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setText(Html.fromHtml(builder.toString(), FROM_HTML_MODE_LEGACY));
        } else {
            setText(Html.fromHtml(builder.toString()));
        }
    }

    public void setDefColor(int defColor) {
        this.defColor = defColor;
        setTextColor(defColor);
    }

    public void setColorTexts(String texts, String colors) {
        parse(texts, colors, defColor);
    }

    /**
     * 只能修改text的颜色，每次修改会使其他文字颜色失效
     *
     * @param text
     * @param color
     */
    public void applyColorForText(String text, String color) {
        String getText = getText().toString();
        String regex = String.format("^<font color=.*?>%s</font>$", text);
        String applyText;
        boolean matches = Pattern.matches(regex, text);
        if (matches) {
            applyText = getText.replaceAll(String.format("<font color=.*?>%s</font>", text),
                    String.format("<font color=%s>%s</font>", color, text));
        } else {
            applyText = getText.replaceAll(text, String.format("<font color=%s>%s</font>", color, text));
        }
        setText(Html.fromHtml(applyText));

    }

    /**
     * @param colors
     * @param texts
     */
    public void applyColorForTexts(String[] colors, String... texts) {
        String getText = getText().toString();
        String applyText = getText;
        for (int i = 0; i < texts.length; i++) {
            int color = colors.length > i ? Color.parseColor(colors[i]) : defColor;
            applyText = applyText.replaceAll(texts[i], String.format("<font color=%s>%s</font>", color, texts[i]));
        }
        if (TextUtils.isEmpty(applyText))
            applyText = getText;
        setText(Html.fromHtml(applyText));
    }

}
