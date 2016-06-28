package applab.client.search.model;

/**
 * Created by skwakwa on 1/21/16.
 */
public class FarmerBudget {

    private double productionCost;
    private double postHarvestCost;
    private double area;
    private double totalYield;
    private double revenue;
    private double totalCost;
    private double grossMargin;
    private double inputReceivedCost;
    private double averageCostPerAcre;
    private double averageRevenuePerAcre;
    private double costBenefitRatio;




    public  FarmerBudget(double productionCost,double postHarvestCost,double area,double revenue,double inputReceivedCost,double yield){
        this.setProductionCost(productionCost);
        this.setPostHarvestCost(postHarvestCost);
        this.setArea(area);
        this.setRevenue(revenue);
        this.setInputReceivedCost(inputReceivedCost);
        this.setTotalCost(productionCost+postHarvestCost);
        this.setGrossMargin(revenue- getTotalCost());
        this.setAverageCostPerAcre((getTotalCost() /area));
        this.setAverageRevenuePerAcre((revenue/area));
        this.setCostBenefitRatio((getAverageCostPerAcre() / getAverageRevenuePerAcre()));
        this.setTotalYield(yield);

    }

    public double getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(double productionCost) {
        this.productionCost = productionCost;
    }

    public double getPostHarvestCost() {
        return postHarvestCost;
    }

    public void setPostHarvestCost(double postHarvestCost) {
        this.postHarvestCost = postHarvestCost;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getGrossMargin() {
        return grossMargin;
    }

    public void setGrossMargin(double grossMargin) {
        this.grossMargin = grossMargin;
    }

    public double getInputReceivedCost() {
        return inputReceivedCost;
    }

    public void setInputReceivedCost(double inputReceivedCost) {
        this.inputReceivedCost = inputReceivedCost;
    }

    public double getAverageCostPerAcre() {
        return averageCostPerAcre;
    }

    public void setAverageCostPerAcre(double averageCostPerAcre) {
        this.averageCostPerAcre = averageCostPerAcre;
    }

    public double getAverageRevenuePerAcre() {
        return averageRevenuePerAcre;
    }

    public void setAverageRevenuePerAcre(double averageRevenuePerAcre) {
        this.averageRevenuePerAcre = averageRevenuePerAcre;
    }

    public double getCostBenefitRatio() {
        return costBenefitRatio;
    }

    public void setCostBenefitRatio(double costBenefitRatio) {
        this.costBenefitRatio = costBenefitRatio;
    }

    public double getTotalYield() {
        return totalYield;
    }

    public void setTotalYield(double totalYield) {
        this.totalYield = totalYield;
    }
}
