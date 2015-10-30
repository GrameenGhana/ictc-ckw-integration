package applab.client.search.utils;

import android.util.Log;
import applab.client.search.model.Farmer;
import applab.client.search.model.wrapper.ItemWrapper;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by skwakwa on 10/22/15.
 */
public class FarmerUtil {


    public static Map<String,List<ItemWrapper>> getFarmerDetails(Farmer f){

        Map<String,List<ItemWrapper>> itmWrap = new HashMap<String, List<ItemWrapper>>();


        List<ItemWrapper> wr = new ArrayList<ItemWrapper>();
        wr.add(new ItemWrapper("Surname",f.getLastName()));
        wr.add(new ItemWrapper("Othernames",f.getFirstName()));
        wr.add(new ItemWrapper("Nickname",f.getNickname()));
        wr.add(new ItemWrapper("Region",f.getRegion()));
        wr.add(new ItemWrapper("District",f.getDistrict()));
        wr.add(new ItemWrapper("Community",f.getCommunity()));
        wr.add(new ItemWrapper("Age",f.getAge()));
        wr.add(new ItemWrapper("Gender",f.getGender()));
        itmWrap.put("Biodata",wr);


        List<ItemWrapper> wrPostharvest = new ArrayList<ItemWrapper>();
        String[] pHarvest={"typeofstoragechemical",
                "completionofproducemarketing",
                "applicationrateofstoragechemical" ,
                "mostproducesaledate",
                "typeofmachinewinowing",
                "typeofmachine",
                "mainpointofsaleorcontact",
                "typeofbagusedinbulkingproduct",
                "datetocompletedrying",
                "postharvestlosses",
                "methodofdryinggrain",
                "completionofthreshing",
                "methodofwinnowing",
                "dehuskingdate",
                "priceatfirstsaledate",
                "methodofdryingcobspanicleschipschu",
                "typeofstoragestructure",
                "priceatmostsaledate",
                "dateofcompletingdrying",
                "proportionformarket",
                "marketingoccasions",
                "otherapplicationrate",
                "firstsaledate",
                "otherstoragechemical",
                "methodofthreshing",
                "othersalecontact"
        };
        String[] pHarvestName={
                " Type Of Storage Chemical",
                " Completion Of Produce Marketing",
                " Application Rate Of Storage Chemical",
                " Most Produce Sale Date",
                " Type Of Machine Winowing",
                " Type Of Machine",
                " Main Point Of Sale Or Contact",
                " Type Of Bag Used In Bulking Product",
                " Date To Complete Drying",
                " Post Harvest Losses",
                " Method Of Drying Grain",
                " Completion Of Threshing",
                " Method Of Winnowing",
                " Dehusking Date",
                " Price At First Sale Date",
                " Method Of Drying Cobspanicles Chipschu",
                " Type Of Storage Structure",
                " Price At Most Sale Date",
                " Date Of Completing Drying",
                " Proportion For Market",
                " Marketing Occasions",
                " Other Application Rate",
                " First Sale Date",
                " Other Storage Chemical",
                " Method Of Threshing",
                " Other Sale Contact",
        };



        List<ItemWrapper> wrProduction = new ArrayList<ItemWrapper>();

        JSONObject postHarvest = f.getPostHarvestJSON();

        int i=0;
        for(String str:pHarvest){
            try {
                wrPostharvest.add(new ItemWrapper(pHarvestName[i], postHarvest.getString(str)));
            }catch(Exception e){
                wrPostharvest.add(new ItemWrapper(pHarvestName[i],""));
            }
            i++;

        }


        String  [] production  =  {
                "nameofcropvarietyyam",
                "refillinggapsoccurence",
                "applicationofbasalfertilizer",
                "applicationofbasalfertilizerdate",
                "datefirstmanualweedcontrol",
                "nameofcropvarietyrice",
                "nameofcropvarietycassava",
                "quantityofbasalfertilizerpurchasedapply",
                "methodoflandclearing",
                "quantitypostplantherbicide",
                "typeofherbicidepostplantweed",
                "methodtopdressfertilizerapp",
                "applicationoftopdressfertilizer",
                "applicationmonthofherbicidedate",
                "refillinggapsproportion",
                "seedbedformtype",
                "seedbedpreparationdate",
                "dateofsecondmanualweedcontrol",
                "landclearingdate",
                "otherfertilizer",
                "acresofland",
                "plantingdistancebetweenplantsmaize",
                "targetyieldperacre",
                "ploughingdate",
                "timeofharvest",
                "nameofcropvariety",
                "timeofapplicationtopdressing",
                "methodoflandpreparation",
                "plantingdistancebetweenrowsmaize",
                "postplantherbicideuse",
                "typeofbasalfertilizer",
                "sourceofseedorplantingmaterial",
                "methodofbasalfertilizerapplication",
                "plantingdate"
        };
        String  [] productionHd  =  {
                " Name Of Crop Variety Yam",
                " Refilling Gaps Occurence",
                " Application Of Basal Fertilizer",
                " Application Of Basal Fertilizerdate",
                " Date First Manual Weed Control",
                " Name Of Crop Variety Rice",
                " Name Of Crop Variety Cassava",
                " Quantity Of Basal Fertilizer Purchased Apply",
                " Method Of Land Clearing",
                " Quantity Post Plant Herbicide",
                " Type Of Herbicide Post Plant Weed",
                " Method Top Dress Fertilizer Application",
                " Application Of Top Dress Fertilizer",
                " Application Month Of Herbicide Date",
                " Refilling Gaps Proportion",
                " Seed Bed Form Type",
                " Seedbed Preparation Date",
                " Date Of Second Manual Weed Control",
                " Land Clearing Date",
                " Other Fertilizer",
                " Acres Of Land",
                " Planting Distance Between Plants Maize",
                " Target Yield Per Acre",
                " Ploughing Date",
                " Time Of Harvest",
                " Name Of Crop Variety",
                " Time Of Application Top Dressing",
                " Method Of Land Preparation",
                " Planting Distance Between Rows Maize",
                " Post Plant Herbicide Use",
                " Type Of Basal Fertilizer",
                " Source Of Seed Or Planting Material",
                " Method Of Basal Fertilizer Application",
                " Planting Date"
        };

        JSONObject prod = f.getProductionJSON();
        Log.i(FarmerUtil.class.getName(),"Pdddd : "+prod);

        i=0;
        for(String str: production){
            try {
                wrProduction.add(new ItemWrapper(productionHd[i],prod.getString(str)));
            }catch(Exception e){
                wrProduction.add(new ItemWrapper(productionHd[i],""));
            }
            i++;
        }


        itmWrap.put("Production",wrProduction);
        itmWrap.put("Post Harvest",wrPostharvest);



        String []  baseLinePostHvest= { "typeofstoragechemical",
                "completionofproducemarketing",
                "otherapplicationrateofstoragechemica",
                "applicationrateofstoragechemical",
                "mostproducesaledate",
                "manualthreshing",
                "mainpointofsaleorcontact",
                "ownershiporconditionsforstorage",
                "datetocompletedrying",
                "postharvestlosses",
                "methodofdryinggrain",
                "completionofthreshing",
                "methodofwinnowing",
                "priceatfirstsaledate",
                "methodofdryingcobspanicleschipschu",
                "proportionofproducesold",
                "typeofstoragestructure",
                "priceatmostsaledate",
                "dateofcompletingdrying",
                "marketingoccasions",
                "manualwinnowing",
                "firstsaledate",
                "otherstoragechemical",
                "typeofbagcontainerused",
                "methodofthreshing",
                "othersalecontact"};



        String []  baseLinePostHvestHd= { " Type Of Storage Chemical",
                " Completion Of Produce Marketing",
                " Other Application Rate Of Storage Chemical",
                " Application Rate Of Storage Chemical",
                " Most Produce Sale Date",
                " Manual Threshing",
                " Main Point Of Sale Or Contact",
                " Ownership Or Conditions For Storage",
                " Date To Complete Drying",
                " Post Harvest Losses",
                " Method Of Drying Grain",
                " Completion Of Threshing",
                " Method Of Winnowing",
                " Price At First Sale Date",
                " Method Of Drying Cobspanicles Chips Chu",
                " Proportion Of Produce Sold",
                " Type Of Storage Structure",
                " Price At Most Sale Date",
                " Date Of Completing Drying",
                " Marketing Occasions",
                " Manual Winnowing",
                " First Sale Date",
                " Other Storage Chemical",
                " Type Of Bag Container Used",
                " Method Of Threshing",
                " Other Sale Contact"};


        List<ItemWrapper> wrBaseLinePH = new ArrayList<ItemWrapper>();

        JSONObject basePostHarvest = f.getBaselinePostHarvestJSON();//PostHarvestJSON();
        i=0;
        for(String str:baseLinePostHvest){
            try {
                wrBaseLinePH.add(new ItemWrapper(baseLinePostHvestHd[i], basePostHarvest.getString(str)));
            }catch(Exception e){
                wrBaseLinePH.add(new ItemWrapper(baseLinePostHvestHd[i],""));
            }

            i++;
        }
        itmWrap.put("Baseline Post Harvest",wrBaseLinePH);


        //Technical Needs

        String  []  needs={   "integratedsoilfertilitymanagement",
                "weedcontrol",
                "cropvarietyandseedcassava",
                "farmplanning",
                "crovarietyandseedsyam",
                "cropvarietyandseed",
                "cropestablishment",
        };
        String  []  needsNM={" Integrated Soil Fertility Management",
        " Weed Control",
                " Crop Variety And Seed Cassava",
                " Farm Planning",
                " Crop Variety And Seeds Yam",
                " Crop Variety And Seed",
                " Crop Establishment"};

         JSONObject techNeeds = f.getJSONObject(f.getTechnicalNeeds());//PostHarvestJSON();
        Log.i(FarmerUtil.class.getName(),"TechNeeds : "+techNeeds);
        List<ItemWrapper> wrNeeds = new ArrayList<ItemWrapper>();
        i=0;
        for(String str:needs){
            try {
                wrNeeds.add(new ItemWrapper(needsNM[i], techNeeds.getString(str)));
            }catch(Exception e){
//                wrBaseLinePH.add(new ItemWrapper(baseLinePostHvestHd[i],""));
            }

            i++;
        }
        itmWrap.put("Technical Needs",wrNeeds);
        return itmWrap;
    }

    public static Map<String,List<ItemWrapper>> getFarmerSummaryDetails(Farmer f){

        Map<String,List<ItemWrapper>> itmWrap = new HashMap<String, List<ItemWrapper>>();


        List<ItemWrapper> wr = new ArrayList<ItemWrapper>();
        wr.add(new ItemWrapper("Surname",f.getLastName()));
        wr.add(new ItemWrapper("Othernames",f.getFirstName()));
        wr.add(new ItemWrapper("Nickname",f.getNickname()));
        wr.add(new ItemWrapper("Cluster",f.getCluster()));
        wr.add(new ItemWrapper("District",f.getDistrict()));
        wr.add(new ItemWrapper("Community",f.getCommunity()));
        wr.add(new ItemWrapper("Age",f.getAge()));
        wr.add(new ItemWrapper("Gender",f.getGender()));
        wr.add(new ItemWrapper("Land Size",f.getTargetArea()));



JSONObject obj = f.getBaselinePostHarvestJSON();
        JSONObject ph= f.getPostHarvestJSON();

        JSONObject bud= f.getBaselineProductionBudgetJSON();

        JSONObject prod= f.getProductionJSON();



        wr.add(new ItemWrapper("Last Season Yield Per Acre",""));

        wr.add(new ItemWrapper("This Season Target Yield per Acre",f.getJSONValue(prod,"targetyieldperacre")));
        wr.add(new ItemWrapper("This Season Expected Price Per Ton",f.getJSONValue(prod,"targetyieldperacre")));
         wr.add(new ItemWrapper("Planting Date",f.getJSONValue(prod,"plantingdate")));
        wr.add(new ItemWrapper("Weeding Date",f.getJSONValue(prod, "datefirstmanualweedcontrol")));
         wr.add(new ItemWrapper("Main Sales Person",f.getJSONValue(ph,"mainpointofsaleorcontact")));
        wr.add(new ItemWrapper("Proportion of Crops Sold",f.getJSONValue(ph, "proportionformarket")));

        itmWrap.put("Farmer Summary",wr);
        return itmWrap;
    }
}
