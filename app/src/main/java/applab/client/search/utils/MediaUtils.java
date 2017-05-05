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
import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Contains methods for managing media files on the file system.
 */
public class MediaUtils {

    public static final String TAG = MediaUtils.class.getSimpleName();
    public static final String MEDIA_ROOT = Environment.getExternalStorageDirectory() + "/gfsearch/";
    public static final String SMART_EXT_FOLDER = Environment.getExternalStorageDirectory()+"/SmartEx";
    private static String[] SUPPORTED_FORMATS = {".jpg", ".jpeg", ".mp4"};

    public static boolean storageReady() {
        String cardStatus = Environment.getExternalStorageState();
        if (cardStatus.equals(Environment.MEDIA_REMOVED)
                || cardStatus.equals(Environment.MEDIA_UNMOUNTABLE)
                || cardStatus.equals(Environment.MEDIA_UNMOUNTED)
                || cardStatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean createRootFolder(String dirName) {
        if (storageReady()) {
            File dir = new File(dirName);
            if (!dir.exists()) {
                return dir.mkdirs();
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean createRootFolder() {
        return createRootFolder(MEDIA_ROOT);
    }

    public static boolean deleteFile(File file) {
        if (storageReady()) {
            return file.delete();
        } else {
            return false;
        }
    }

    public static void writeFile(String fileName, InputStream inputStream) throws IOException {
       writeFile(fileName,"ckw",inputStream);
    }

    public static void writeFile(String fileName, String type, InputStream inputStream) throws IOException {
        if (storageReady() && createRootFolder()) {
            try {
                // replace spaces with underscores
                fileName = fileName.replace(" ", "_");

                // change to lowercase
                fileName = fileName.toLowerCase();
                FileOutputStream fileOutputStream = new FileOutputStream(new File(MEDIA_ROOT, fileName));

                byte[] buffer = new byte[1024];

                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }

                fileOutputStream.close();
            } catch (Exception ex) {
                Log.d(TAG, "Writing file of type " + type);
            }
        }
    }

    public static ArrayList<String> getFilesAsArrayList() {
        ArrayList<String> fileList = new ArrayList<String>();
        File rootDirectory = new File(MEDIA_ROOT);
        if (!storageReady()) {
            return null;
        }
        // If directory does not exist, create it.
        if (!rootDirectory.exists()) {
            if (!createRootFolder()) {
                return null;
            }
        }
        File[] children = rootDirectory.listFiles();
        for (File child : children) {
            fileList.add(child.getAbsolutePath());
        }

        return fileList;
    }

    public static boolean fileExists(String fileName) {
        if (!storageReady()) {
            return false;
        }

        return getFullPath(fileName) == null ? false : true;
    }

    public static boolean fileExists(String fileName, boolean isPartialName) {
        if (!storageReady()) {
            return false;
        } else if (isPartialName) {
            return getFullPath(fileName, true) == null ? false : true;
        } else {
            return fileExists(fileName);
        }
    }

    public static String getFullPath(String fileName){
            return getFullPath(fileName, SUPPORTED_FORMATS);
    }

    public static String getFullPath(String fileName, String[] formats) {
        for (String format : formats) {
            String path = MEDIA_ROOT + fileName + format;
            File file = new File(path);

            if (file.exists()) {
                return path;
            }
        }
        return null;
    }

    public static String getFullPath(String fileName, boolean isPartialName) {
            return getFullPath(fileName, SUPPORTED_FORMATS, isPartialName);
    }

    public static String getFullPath(String fileName, String[] formats, boolean isPartialName) {
        if (!isPartialName) {
            return getFullPath(fileName, formats);
        }

        File dir = new File(MEDIA_ROOT);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (fileName != null && file.getName().toLowerCase().contains(fileName.toLowerCase())) {
                    return file.getAbsolutePath();
                }
            }
        }

        return null;
    }

    public static String getSHA1Hash(File file) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] messageDigest = md.digest(getFileAsBytes(file));
            BigInteger number = new BigInteger(1, messageDigest);
            String sha1 = number.toString(16);
            while (sha1.length() < 32)
                sha1 = "0" + sha1;
            return sha1;
        } catch (NoSuchAlgorithmException e) {
            Log.e("SHA1", e.getMessage());
            return null;
        }
    }

    public static byte[] getFileAsBytes(File file) {
        byte[] bytes = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            // Get the size of the file
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                Log.e("", "File " + file.getName() + "is too large");
                return null;
            }
            // Create the byte array to hold the data
            bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int read = 0;
            try {
                while (offset < bytes.length && read >= 0) {
                    read = is.read(bytes, offset, bytes.length - offset);
                    offset += read;
                }
            } catch (IOException e) {
                Log.e(TAG, "Cannot read " + file.getName());
                e.printStackTrace();
                return null;
            }
            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                Log.e(TAG, "Could not completely read file " + file.getName());
                return null;
            }
            return bytes;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Cannot find " + file.getName());
            e.printStackTrace();
            return null;
        } finally {
            // Close the input stream
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "Cannot close input stream for " + file.getName());
                e.printStackTrace();
                return null;
            }
        }
    }

    public static File createCacheFolderIfNotExists(Context context, String folderName) {
        String cachePath = context.getCacheDir() + "/"+folderName+"/";
        File folder = new File(cachePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
}
