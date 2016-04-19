package applab.client.search.model;

public class DashboardListItem {

    private String mTag;
    private String mTitle;
    private String mSubTitle;
    private int mImage;

    public DashboardListItem() {}

    public String getTag() { return mTag; }
    public void setTag(String t) { mTag = t; }

    public String getTitle() { return mTitle; }
    public void setTitle(String t) { mTitle = t; }

    public String getSubTitle() { return mSubTitle; }
    public void setSubTitle(String t) { mSubTitle = t; }

    public int getImage() { return mImage; }
    public void setImage(int t) { mImage = t; }
}
