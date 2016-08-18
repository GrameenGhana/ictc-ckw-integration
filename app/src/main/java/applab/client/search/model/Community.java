package applab.client.search.model;

/**
 * Created by Software Developer on 18-Jul-16.
 */
public class Community {
    private String name;
    private String memberCount;

    public Community(){

    }
    public Community(String name, String memberCount) {
        this.name = name;
        this.memberCount = memberCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }
}
