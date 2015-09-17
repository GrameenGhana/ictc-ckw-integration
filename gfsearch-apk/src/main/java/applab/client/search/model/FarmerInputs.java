package applab.client.search.model;

import java.util.Date;

/**
 * Created by skwakwa on 9/17/15.
 */
public class FarmerInputs {


    private int id;
    private String name;
    private Date dateReceived;
    private String status;
    private double qty;
    private double qtyReceived;
    private String farmer;

    public FarmerInputs() {
    }


    public FarmerInputs(String name, Date dateReceived, String status, double qty) {
        this.name = name;
        this.dateReceived = dateReceived;
        this.status = status;
        this.qty = qty;
    }

    public FarmerInputs(String name, Date dateReceived, String status, double qty, String farmer) {
        this.name = name;
        this.dateReceived = dateReceived;
        this.status = status;
        this.qty = qty;
        this.farmer = farmer;
    }public FarmerInputs(int id,String name, Date dateReceived, String status, double qty, String farmer) {
        this.setId(id);
        this.name = name;
        this.dateReceived = dateReceived;
        this.status = status;
        this.qty = qty;
        this.farmer = farmer;
    }



    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dateReceived
     */
    public Date getDateReceived() {
        return dateReceived;
    }

    /**
     * @param dateReceived the dateReceived to set
     */
    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(double qty) {
        this.qty = qty;
    }

    /**
     * @return the farmer
     */
    public String getFarmer() {
        return farmer;
    }

    /**
     * @param farmer the farmer to set
     */
    public void setFarmer(String farmer) {
        this.farmer = farmer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(double qtyReceived) {
        this.qtyReceived = qtyReceived;
    }
}
