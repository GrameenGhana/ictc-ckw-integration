package applab.client.search.interactivecontent;

import android.os.Environment;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class ContentUtils {
    private static final String CONTENT_ROOT = Environment.getExternalStorageDirectory() + "/gfinteractive";
    private static final String VIDEO_LOCATION = CONTENT_ROOT + "/video/";
    private static final String AUDIO_LOCATION = CONTENT_ROOT + "/audio/";
    public static final String VIDEO_PLACEHOLDER = "\\{video:(.*?)\\}";
    public static final String AUDIO_PLACEHOLDER = "\\{audio:(.*?)\\}";

    /**
     * default constructor made private to avoid instantiating
     * this class
     */
    private ContentUtils() {

    }

    private static boolean storageReady() {
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

    private static boolean createContentRootIfNotExists() {
        if (storageReady()) {
            File dir = new File(CONTENT_ROOT);
            if (!dir.exists()) {
                return dir.mkdirs();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * get the interactive content folder names
     *
     * @return
     */
    public static String[] getContentListing() {
        String[] items = new String[]{};

        File dir = new File(CONTENT_ROOT);
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
//        String regPattern="\\{((V|v)ideo:[^]]+)\\}";
        Pattern pattern = Pattern.compile(VIDEO_PLACEHOLDER);
        Matcher matcher = pattern.matcher(desc);
        return matcher.find();
    }

    public static boolean containsAudio(String desc) {
        Pattern pattern = Pattern.compile(AUDIO_PLACEHOLDER);
        Matcher matcher = pattern.matcher(desc);
        return matcher.find();
    }

    public static String replaceMultimediaPlaceholder(String content) {

        return content.replaceAll(ContentUtils.VIDEO_PLACEHOLDER, "").replaceAll(ContentUtils.AUDIO_PLACEHOLDER, "");
    }
}
