package applab.client.search.model;

import android.util.Log;
import org.json.JSONObject;

/**
 * represent a farmer record
 * <p/>
 * Copyright (c) 2014 AppLab, Grameen Foundation
 * Created by: David
 */
public class Farmer extends ListObject {
    //private String farmerId;
    private String firstName;
    private String lastName;
    private String creationDate;
    private String subcounty;
    private String village;
    private String nickname;
    private String community;
    private String district;
    private String region;
    private String age;
    private String gender;
    private String maritalStatus;
    private String numberOfChildren;
    private String numberOfDependants;
    private String education;
    private String cluster;
    private String farmID;

    private String phoneNumber;
    private String sizePlot;
    private String labour;
    private String dateOfLandIdentification;
    private String locationOfLand;
    private String targetArea;
    private String expectedPriceInTon;
    private String variety;
    private String targetNextSeason;
    private String techNeeds1;
    private String techNeeds2;
    private String farmerBasedOrg;
    //    String REGION,
    private String plantingDate;
    private String landArea;
    private String dateManualWeeding;
    private String posContact;
    private String monthSellingStarts;
    private String monthFinalProductSold;
    private String mainCrop;



    private String production;
    private String baselineProductionBudget;
    private String baselineProduction;
    private String baselinepostharvest;
    private String baselinepostharvestBudget;
    private String postharvest;
    private String technicalNeeds;
    private String profiling;
    private String fmpproductionbudget;

    /*public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }*/
    public Farmer() {

    }

    public Farmer(String firstName, String lastName, String nickname, String community, String village, String district, String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants, String education) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.community = community;
        this.village = village;
        this.district = district;
        this.region = region;
        this.age = age;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.numberOfChildren = numberOfChildren;
        this.numberOfDependants = numberOfDependants;
        this.education = education;
    }

    public Farmer(String firstName, String lastName, String nickname, String community, String village, String district, String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants, String education, String cluster, String farmID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.community = community;
        this.village = village;
        this.district = district;
        this.region = region;
        this.age = age;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.numberOfChildren = numberOfChildren;
        this.numberOfDependants = numberOfDependants;
        this.education = education;
        this.cluster = cluster;
        this.farmID = farmID;
    }

    /**
     * @param firstName
     * @param lastName
     * @param village
     * @param nickname
     * @param community
     * @param district
     * @param region
     * @param age
     * @param gender
     * @param maritalStatus
     * @param numberOfChildren
     * @param numberOfDependants
     * @param education
     * @param cluster
     * @param farmID
     * @param sizePlot
     * @param labour
     * @param dateOfLandIdentification
     * @param locationOfLand
     * @param targetArea
     * @param expectedPriceInTon
     * @param variety
     * @param targetNextSeason
     * @param techNeeds1
     * @param techNeeds2
     * @param farmerBasedOrg
     * @param plantingDate
     * @param landArea
     * @param dateManualWeeding
     * @param posContact
     * @param monthSellingStarts
     * @param monthFinalProductSold    firstName, lastName, nickname, community, village, district, region, age, gender, maritalStatus, numberOfChildren, numberOfDependants, education, cluster, farmID,SIZE_PLOT,
     *                                 LABOUR,
     *                                 DATE_OF_LAND_IDENTIFICATION ,
     *                                 LOCATION_LAND,
     *                                 TARGET_AREA,
     *                                 EXPECTED_PRICE_TON ,
     *                                 VARIETY,
     *                                 EDUCATION ,
     *                                 TARGET_NEXT_SEASON ,
     *                                 TECH_NEEDS_I,
     *                                 TECH_NEEDS_II ,
     *                                 FARMER_BASE_ORG,
     *                                 PLANTING_DATE,
     *                                 LAND_AREA ,
     *                                 DATE_MANUAL_WEEDING ,
     *                                 POS_CONTACT ,
     *                                 MONTH_SELLING_STARTS ,
     *                                 MONTH_FINAL_PRODUCT_SOLD
     */

    public Farmer(String firstName, String lastName, String nickname, String community, String village, String district, String region, String age, String gender, String maritalStatus, String numberOfChildren, String numberOfDependants, String education, String cluster, String farmID,
                  String sizePlot, String labour, String dateOfLandIdentification, String locationOfLand, String targetArea,
                  String expectedPriceInTon, String variety, String targetNextSeason, String techNeeds1, String techNeeds2,
                  String farmerBasedOrg, String plantingDate, String landArea, String dateManualWeeding, String posContact,
                  String monthSellingStarts, String monthFinalProductSold, String mainCrop) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mainCrop = mainCrop;


        System.out.println("Main crop  : " + firstName + " \t:\t" + mainCrop);

        this.village = village;
        System.out.println("Village  : " + firstName + " \t:\t" +village);
        this.nickname = nickname;
        this.community = community;
        this.district = district;
        this.region = region;
        this.age = age;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.numberOfChildren = numberOfChildren;
        this.numberOfDependants = numberOfDependants;
        this.education = education;
        this.cluster = cluster;
        this.farmID = farmID;
        this.sizePlot = sizePlot;
        this.labour = labour;
        this.dateOfLandIdentification = dateOfLandIdentification;
        this.locationOfLand = locationOfLand;
        this.targetArea = targetArea;
        this.expectedPriceInTon = expectedPriceInTon;
        this.variety = variety;
        this.targetNextSeason = targetNextSeason;
        this.techNeeds1 = techNeeds1;
        this.techNeeds2 = techNeeds2;
        this.farmerBasedOrg = farmerBasedOrg;
        this.plantingDate = plantingDate;
        this.landArea = landArea;
        this.dateManualWeeding = dateManualWeeding;
        this.posContact = posContact;
        this.monthSellingStarts = monthSellingStarts;
        this.monthFinalProductSold = monthFinalProductSold;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getSubcounty() {
        return subcounty;
    }

    public void setSubcounty(String subcounty) {
        this.subcounty = subcounty;
    }

    public String getVillage() {
        System.out.println("villagio--------------- " + village);
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(String numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public String getNumberOfDependants() {
        return numberOfDependants;
    }

    public void setNumberOfDependants(String numberOfDependants) {
        this.numberOfDependants = numberOfDependants;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * @return the cluster
     */
    public String getCluster() {
        return cluster;
    }

    /**
     * @param cluster the cluster to set
     */
    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    /**
     * @return the farmID
     */
    public String getFarmID() {
        return farmID;
    }

    /**
     * @param farmID the farmID to set
     */
    public void setFarmID(String farmID) {
        this.farmID = farmID;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }

    public String getSizePlot() {
        return sizePlot;
    }

    public void setSizePlot(String sizePlot) {
        this.sizePlot = sizePlot;
    }

    public String getLabour() {
        return labour;
    }

    public void setLabour(String labour) {
        this.labour = labour;
    }

    public String getDateOfLandIdentification() {
        return dateOfLandIdentification;
    }

    public void setDateOfLandIdentification(String dateOfLandIdentification) {
        this.dateOfLandIdentification = dateOfLandIdentification;
    }

    public String getLocationOfLand() {
        return locationOfLand;
    }

    public void setLocationOfLand(String locationOfLand) {
        this.locationOfLand = locationOfLand;
    }

    public String getTargetArea() {
        return targetArea;
    }

    public void setTargetArea(String targetArea) {
        this.targetArea = targetArea;
    }

    public String getExpectedPriceInTon() {
        return expectedPriceInTon;
    }

    public void setExpectedPriceInTon(String expectedPriceInTon) {
        this.expectedPriceInTon = expectedPriceInTon;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getTargetNextSeason() {
        return targetNextSeason;
    }

    public void setTargetNextSeason(String targetNextSeason) {
        this.targetNextSeason = targetNextSeason;
    }

    public String getTechNeeds1() {
        return techNeeds1;
    }

    public void setTechNeeds1(String techNeeds1) {
        this.techNeeds1 = techNeeds1;
    }

    public String getTechNeeds2() {
        return techNeeds2;
    }

    public void setTechNeeds2(String techNeeds2) {
        this.techNeeds2 = techNeeds2;
    }

    public String getFarmerBasedOrg() {
        return farmerBasedOrg;
    }

    public void setFarmerBasedOrg(String farmerBasedOrg) {
        this.farmerBasedOrg = farmerBasedOrg;
    }

    public String getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(String plantingDate) {
        this.plantingDate = plantingDate;
    }

    public String getLandArea() {
        return landArea;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    public String getDateManualWeeding() {
        return dateManualWeeding;
    }

    public void setDateManualWeeding(String dateManualWeeding) {
        this.dateManualWeeding = dateManualWeeding;
    }

    public String getPosContact() {
        return posContact;
    }

    public void setPosContact(String posContact) {
        this.posContact = posContact;
    }

    public String getMonthSellingStarts() {
        return monthSellingStarts;
    }

    public void setMonthSellingStarts(String monthSellingStarts) {
        this.monthSellingStarts = monthSellingStarts;
    }

    public String getMonthFinalProductSold() {
        return monthFinalProductSold;
    }

    public void setMonthFinalProductSold(String monthFinalProductSold) {
        this.monthFinalProductSold = monthFinalProductSold;
    }

    public String getMainCrop() {
        return mainCrop;
    }

    public void setMainCrop(String mainCrop) {
        this.mainCrop = mainCrop;
    }

    public String getFullname()
    {
        return firstName+"  "+lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getBaselineProductionBudget() {
        return baselineProductionBudget;
    }

    public void setBaselineProductionBudget(String baselineProductionBudget) {
        this.baselineProductionBudget = baselineProductionBudget;
    }

    public String getBaselineProduction() {
        return baselineProduction;
    }

    public void setBaselineProduction(String baselineProduction) {
        this.baselineProduction = baselineProduction;
    }

    public String getBaselinepostharvest() {
        return baselinepostharvest;
    }

    public void setBaselinepostharvest(String baselinepostharvest) {
        this.baselinepostharvest = baselinepostharvest;
    }

    public String getPostharvest() {
        return postharvest;
    }

    public void setPostharvest(String postharvest) {
        this.postharvest = postharvest;
    }





    public String getgetBaselineProductionBudgetItem(String name){
        return  getJSONItem(getBaselineProductionBudgetJSON(),name);
    }


    public JSONObject getBaselineProductionBudgetJSON(){
        try {
           return new JSONObject(this.baselineProductionBudget);
        }catch (Exception e ){

        }
        return null;
    }


    public String getBaselineProductionItem(String name){
        return  getJSONItem(getBaselineProductionJSON(),name);
    }


    public JSONObject getBaselineProductionJSON(){
        try {
            System.out.println("Baseline Production: "+getBaselineProduction());
         return   new JSONObject(getBaselineProduction());
        }catch (Exception e ){

        }
        return null;
    }

    public JSONObject getProductionJSON(){
        Log.i(this.getClass().getName(),"Production "+getProduction());
        try {
         return   new JSONObject(getProduction());
        }catch (Exception e ){
            System.out.println("Production E : "+e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }
    public String getPostHarvestItem(String name){
        return  getJSONItem(getProductionJSON(),name);
    }


    public JSONObject getPostHarvestJSON(){
        try {
            return new JSONObject(getPostharvest());
        }catch (Exception e ){

        }
        return null;
    }
    public JSONObject getBaselinePostHarvestBudgetJSON(){
        try {
            return new JSONObject(getBaselinepostharvestBudget());
        }catch (Exception e ){

        }
        return null;
    }

    public JSONObject getProductionBudgetJSON(){
        try {
            return new JSONObject(getFmpproductionbudget());
        }catch (Exception e ){

        }
        return null;
    }

    public String getBaselinePostHarvesBudgetItem(String name){
        return  getJSONItem(getBaselinePostHarvestBudgetJSON(),name);
    }


    public String getProductionItem(String name){
        return  getJSONItem(getProductionJSON(),name);
    }



    public JSONObject getBaselinePostHarvestJSON(){
        try {
          return  new JSONObject(getBaselinepostharvest());
        }catch (Exception e ){

        }
        return null;


    }
     public String getBaselinePostHarvestItem(String name){
         System.out.println("Baseline Post Harvest " + getBaselinePostHarvestJSON());
        return  getJSONItem(getBaselinePostHarvestJSON(),name);
     }

    public String getJSONItem(JSONObject jobj,String item){
        if(null != jobj) {
            try {
                return jobj.getString(item);
            } catch (Exception e) {

            }
        }
        return  "";

    }
    public JSONObject getJSONObject(String f){
        try {
            return  new JSONObject(f);
        }catch (Exception e){

        }
        return null;
    }

    public String getJSONValue(JSONObject obj,String key){

        try {
            return String.valueOf(obj.get(key));
        }catch (Exception e){

        }
        return  "";
    }

    public String getTechnicalNeeds() {
        return technicalNeeds;
    }

    public void setTechnicalNeeds(String technicalNeeds) {
        this.technicalNeeds = technicalNeeds;
    }

    public String getFmpproductionbudget() {
        return fmpproductionbudget;
    }

    public void setFmpproductionbudget(String fmpproductionbudget) {
        this.fmpproductionbudget = fmpproductionbudget;
    }

    public String getBaselinepostharvestBudget() {
        return baselinepostharvestBudget;
    }

    public void setBaselinepostharvestBudget(String baselinepostharvestBudget) {
        this.baselinepostharvestBudget = baselinepostharvestBudget;
    }
}
