package com.projeto.zikazero.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Hiago on 26/05/2016.
 */
public class TransformarFotoBits {

    public static byte[] fotoByte(ImageView foto){
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, saida);
        byte[] img = saida.toByteArray();

        return img;
    }
}
