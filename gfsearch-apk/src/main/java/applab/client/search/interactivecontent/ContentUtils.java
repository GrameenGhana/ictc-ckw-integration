package applab.client.search.interactivecontent;

import android.os.Environment;
import applab.client.search.utils.MediaUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class ContentUtils {
    private static final String CONTENT_ROOT = Environment.getExternalStorageDirectory() + "/gfinteractive";
    public static final String VIDEO_LOCATION = CONTENT_ROOT + "/video/";
    public static final String AUDIO_LOCATION = CONTENT_ROOT + "/audio/";
    public static final String IMAGE_LOCATION = CONTENT_ROOT + "/images/";
    public static final String VIDEO_PLACEHOLDER = "\\{video:(.*?)\\}";
    public static final String AUDIO_PLACEHOLDER = "\\{audio:(.*?)\\}";

    /**
     * default constructor made private to avoid instantiating
     * this class
     */
    private ContentUtils() {

    }

    private static boolean storageReady() {
        return MediaUtils.storageReady();
    }

    private static boolean createContentRootIfNotExists() {
        return MediaUtils.createRootFolder(CONTENT_ROOT);
    }

    /**
     * get the interactive content folder names
     *
     * @return
     */
    public static String[] getContentListing() {
        String[] items = new String[]{};

        File dir = new File(CONTENT_ROOT);
        System.out.println("CONTENT_ROOT : "+CONTENT_ROOT);
        if (dir.exists()) {
            items = dir.list();
            if (items == null)
                return new String[]{};
        } else {
            createContentRootIfNotExists();
        }

        return items;
    }

    /**
     * gets the content folder for the given content item.
     *
     * @param contentItem
     * @return
     */
    public static String getContentFolder(String contentItem) {
        return CONTENT_ROOT + "/" + contentItem;
    }


    public static String getVideoLocation(String files) {
        Pattern pattern = Pattern.compile(VIDEO_PLACEHOLDER);
        Matcher matcher = pattern.matcher(files);
        if (matcher.find()) {
            String grp1 = matcher.group(1);
            return VIDEO_LOCATION + grp1;
        }
        return "";
    }

    public static String getAudioLocation(String files) {
        Pattern pattern = Pattern.compile(AUDIO_PLACEHOLDER);
        Matcher matcher = pattern.matcher(files);
        if (matcher.find()) {
            String grp1 = matcher.group(1);

            return AUDIO_LOCATION + grp1;
        }
        return "";
    }

    public static boolean containsVideo(String desc) {
        if(null == desc || desc.isEmpty()) return false;
        Pattern pattern = Pattern.compile(VIDEO_PLACEHOLDER);
        Matcher matcher = pattern.matcher(desc);
        return matcher.find();
    }

    public static boolean containsAudio(String desc) {
        if(null == desc || desc.isEmpty()) return false;
        Pattern pattern = Pattern.compile(AUDIO_PLACEHOLDER);
        Matcher matcher = pattern.matcher(desc);
        return matcher.find();
    }

    public static String replaceMultimediaPlaceholder(String content) {
        if(null == content || content.isEmpty() )
            return "";
        return content.replaceAll(ContentUtils.VIDEO_PLACEHOLDER, "").replaceAll(ContentUtils.AUDIO_PLACEHOLDER, "");
    }
}
