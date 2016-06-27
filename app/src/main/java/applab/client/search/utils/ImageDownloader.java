package applab.client.search.utils;

/**
 * Created by skwakwa on 2/4/16.
 */


        import java.io.BufferedInputStream;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.net.URL;
        import java.net.URLConnection;
        import java.util.ArrayList;
        import java.util.List;

        import applab.client.search.interactivecontent.ContentUtils;
        import applab.client.search.model.Farmer;
        import applab.client.search.synchronization.IctcCkwIntegrationSync;
        import org.apache.http.util.ByteArrayBuffer;

        import android.util.Log;

public class ImageDownloader {

    private final String PATH = ImageUtils.FULL_URL_PROFILE_PIX;

    public void downloadimage(String img){
        URL url = null; //you can write here any link
        File file = null;
        URLConnection ucon = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        ByteArrayBuffer baf = null;
        FileOutputStream fos = null;
        downloadImage(img,url,file,ucon,is,bis,baf,fos);
    }
    public void downloadImage(final String imgUrl ,  URL url,
            File file ,
            URLConnection ucon,
            InputStream is ,
            BufferedInputStream bis ,
            ByteArrayBuffer baf ,
            FileOutputStream fos ){
        final String fileUrl =ImageUtils.FULL_URL_PROFILE_PIX+"/"+imgUrl;
        System.out.println("File Url : "+fileUrl);
        file = new File(fileUrl);
        System.out.println("Image URl : "+imgUrl);
        if(!file.exists()) {

            System.out.println("Image url does not exist");
            Thread background = new Thread(new Runnable() {

                @Override
                public void run() {

                    try {
                        System.out.println("Image RUn : "+fileUrl);
                        URL url=null;
                        File file =null;
                        URLConnection ucon=null;
                        InputStream is =null;
                        BufferedInputStream bis=null;
                        ByteArrayBuffer baf =null;
                        FileOutputStream fos = null;

                        String iUrl = IctcCkwIntegrationSync.IMAGE_URL + imgUrl;
                        System.out.println("image url to download : " + iUrl);
                        url = new URL(iUrl);
                        ucon = url.openConnection();
                        System.out.println("image url after conn to download : " + iUrl);

                        long startTime = System.currentTimeMillis();
                        Log.d("ImageManager", "download begining");
                        Log.d("ImageManager", "download url:" + url);
                        Log.d("ImageManager", "downloaded file name:" + imgUrl);
                            /* Open a connection to that URL. */
                        System.out.println("image url to download start : " + iUrl);

                            /*
                             * Define InputStreams to read from the URLConnection.
                             */
                        is = ucon.getInputStream();
                        bis = new BufferedInputStream(is);
                        System.out.println("image url to download start 1: " + iUrl);
                            /*
                             * Read bytes to the Buffer until there is nothing more to read(-1).
                             */
                        baf = new ByteArrayBuffer(50);
                        int current = 0;
                        while ((current = bis.read()) != -1) {
                            baf.append((byte) current);
                        }
                        System.out.println("image url to download start 2: " + iUrl);
                            /* Convert the Bytes read to a String. */
                        fos = new FileOutputStream(file);
                        fos.write(baf.toByteArray());
                        fos.close();
                        Log.d("ImageManager", "download ready in"
                                + ((System.currentTimeMillis() - startTime) / 1000)
                                + " sec");

                        System.out.println("image url to download ened : " + iUrl + ((System.currentTimeMillis() - startTime) / 1000)
                                + " sec");

                    } catch (Exception e) {
                        Log.d("ImageManager", "Errork: " + e);
                        e.printStackTrace();
                        System.out.println("Image Download Error ended : " + e.getLocalizedMessage());
                    }
                }
            });
            }

        }

    public void downloadFromUrl(List<String> imgs ,String fileName) {  //this is the downloader method
        try {
            URL url = null; //you can write here any link
            File file = null;
            URLConnection ucon = null;
            InputStream is = null;
            BufferedInputStream bis = null;
            ByteArrayBuffer baf = null;
            FileOutputStream fos = null;

            for(String imgUrl: imgs){
                downloadImage(imgUrl,url,file,ucon,is,bis,baf,fos);
            }




        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e);
        }

    }
    public void downloadFarmerImages(List<Farmer> farmers){
        List<String> imgs = new ArrayList<String>();
        for (Farmer farmer: farmers){
            imgs.add(farmer.getFarmID()+".jpg");
        }
        downloadFromUrl(imgs,"");

    }
}

