/**
 * Copyright (C) 2010 Grameen Foundation
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy of
 the License at http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package applab.client.search.utils;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Environment;
import android.util.Log;
import applab.client.search.R;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Contains methods for managing image files on the file system.
 */
public class ImageUtils extends MediaUtils {

    public static final String IMAGE_ROOT = MEDIA_ROOT;
    private static String[] SUPPORTED_FORMATS = {".jpg", ".jpeg"};

    public static String ProfilePix="FarmerPix";
    public static String FULL_URL_PROFILE_PIX=SMART_EXT_FOLDER+"/"+ProfilePix;

    public static void writeFile(String fileName, String type, InputStream inputStream) throws IOException {

        if (storageReady() && createRootFolder() && verifyDirectory()) {
            // replace spaces with underscores
            fileName = fileName.replace(" ", "_");
            // change to lowercase
            fileName = fileName.toLowerCase();
            FileOutputStream fileOutputStream = null;
            //pp for profile pix
            if(!type.equalsIgnoreCase("pp")){
                fileOutputStream= new FileOutputStream(new File(IMAGE_ROOT, fileName));
            }else{
                fileOutputStream= new FileOutputStream(new File(FULL_URL_PROFILE_PIX, fileName));
            }
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.close();
        }
    }

    public static Drawable getImageAsDrawable(Context context, String fileName) {
        if (!storageReady()) {
            return null;
        }
        fileName = getFullPath(fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Drawable getImageAsDrawable(Context context, String fileName, boolean isPartialName) {
        if (!storageReady()) {
            return null;
        }
        fileName = getFullPath(fileName, true);
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Drawable getICTCImageAsDrawable(Context context, String fileName) {

//        fileName = getFullPath(fileName, true);
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static boolean imageExists(String fileName) {
            return fileExists(fileName);
    }

    public static boolean imageExists(String fileName, boolean isPartialName) {
        return fileExists(fileName, isPartialName);
    }

    public static String getFullPath(String fileName) {
        return getFullPath(fileName, SUPPORTED_FORMATS);
    }

    public static String getFullPath(String fileName, boolean isPartialName) {
        return getFullPath(fileName, SUPPORTED_FORMATS, isPartialName);
    }

    public static Drawable drawSelectedImage(Context context, int width, int height) {
        Bitmap canvasBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);

        ShapeDrawable drawable = new ShapeDrawable(new RectShape());
        drawable.setBounds(0, 0, width, height);
        drawable.getPaint().setColor(0xff5E5C5C);

        Canvas canvas = new Canvas(canvasBitmap);
        drawable.draw(canvas);

        Drawable resourceDrawable = context.getResources().getDrawable(R.mipmap.ic_action_accept);
        resourceDrawable.draw(canvas);


        return new BitmapDrawable(context.getResources(), canvasBitmap);
    }

    public static Drawable drawRandomColorImageWithText(Context context, String substring, int width, int height) {
        int[] colors = new int[]{0xff67BF74, 0xffE4C62E, 0xff2093CD, 0xff59A2BE, 0xffF9A43A};

        int randomIndex = new Random().nextInt(colors.length);
        Bitmap canvasBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);

        ShapeDrawable drawable = new ShapeDrawable(new RectShape());
        drawable.setBounds(0, 0, width, height);
        drawable.getPaint().setColor(colors[randomIndex]);

        Canvas canvas = new Canvas(canvasBitmap);
        drawable.draw(canvas);

        // Set up the paint for use with our Canvas
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(35f);
        textPaint.setAntiAlias(true);
        //textPaint.setStyle(Paint.Style.FILL);
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto/Roboto-Light.ttf");
        textPaint.setTypeface(myTypeface);
        //textPaint.setStrokeWidth(0.01f);
        textPaint.setColor(0xffFFFFFF);

        canvas.drawText(substring, width / 2, (height / 1.4f), textPaint);
        return new BitmapDrawable(context.getResources(), canvasBitmap);
    }

    public static File createImageCacheFolderIfNotExists(Context context) {
            return createCacheFolderIfNotExists(context, "gfimages");
    }

    public static Drawable scaleAndCacheImage(Context context, String sourceImageFile, String destinationImageFile,
                                              int scaleWidth, int scaleHeight) {
        try {
            int inWidth = 0;
            int inHeight = 0;

            InputStream in = new FileInputStream(sourceImageFile);

            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;

            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;

            // decode full image pre-resized
            in = new FileInputStream(sourceImageFile);
            options = new BitmapFactory.Options();
            // calc rought re-size (this is no exact resize)
            options.inSampleSize = Math.max(inWidth / scaleWidth, inHeight / scaleHeight);
            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            // calc exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, scaleWidth, scaleHeight);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            // resize bitmap
            Bitmap resizedBitmap =
                    Bitmap.createScaledBitmap(roughBitmap,
                            (int) (roughBitmap.getWidth() * values[0]),
                            (int) (roughBitmap.getHeight() * values[4]), true);

            // save image
            try {
                FileOutputStream out = new FileOutputStream(destinationImageFile);
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

                return new BitmapDrawable(context.getResources(), resizedBitmap);
            } catch (Exception e) {
                Log.e("Image", e.getMessage(), e);
            }
        } catch (IOException e) {
            Log.e("Image", e.getMessage(), e);
        }

        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static Drawable loadBitmapDrawableIfExists(Context context, String cacheImageFile) {
        if (new File(cacheImageFile).exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(cacheImageFile);
            return new BitmapDrawable(context.getResources(), bitmap);
        } else {
            return null;
        }
    }

    public static boolean verifyDirectory(){
        System.out.println("Imagev Verifying Directory");
        boolean result=false;
        File direct = new File(SMART_EXT_FOLDER);
        if(!direct.exists()) {
            System.out.println("Not  Existing Creating");
            result = (direct.mkdir()); //directory is created;

        }else{
            result=true;
        }
        System.out.println("Search Imagev Foilder");
        direct = new File(FULL_URL_PROFILE_PIX);
        if(!direct.exists()) {

            System.out.println("Imagev Creating");
            result=(direct.mkdir()) ;
        }else{
            result =true;
        }
        return result;
    }

    public static boolean profilePixExist(String img){
        return (new File(FULL_URL_PROFILE_PIX+"/"+img+"jpg").exists());
    }
}
