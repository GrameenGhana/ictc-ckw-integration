package applab.client.search.model;

/**
 * Created by skwakwa on 8/4/15.
 */
public class CommunityCounterWrapper {


    private String community;
    private int counter;

    public CommunityCounterWrapper(String community, int counter) {
        this.community = community;
        this.counter = counter;

    }


    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }


    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
