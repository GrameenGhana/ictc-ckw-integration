package applab.client.search.utils;

import android.util.Log;
import applab.client.search.model.Farmer;
import applab.client.search.model.FarmerBudget;
import applab.client.search.model.FarmerInputs;
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
        wr.add(new ItemWrapper("Cluster",f.getCluster()));
        wr.add(new ItemWrapper("District",f.getDistrict()));
        wr.add(new ItemWrapper("Community",f.getCommunity()));
        wr.add(new ItemWrapper("Age",f.getAge()));
        wr.add(new ItemWrapper("Gender",f.getGender()));




        JSONObject obj = f.getBaselinePostHarvestJSON();
        JSONObject ph= f.getPostHarvestJSON();

        JSONObject bud= f.getBaselineProductionBudgetJSON();

        JSONObject prod= f.getProductionJSON();


      /**  wr.add(new ItemWrapper("Land Size",f.getJSONValue(prod,"acresofland")));
        wr.add(new ItemWrapper("Last Season Yield Per Acre",""));

        wr.add(new ItemWrapper("This Season Target Yield per Acre",f.getJSONValue(prod,"targetyieldperacre")));
        wr.add(new ItemWrapper("This Season Expected Price Per Ton",f.getJSONValue(prod,"targetyieldperacre")));
        wr.add(new ItemWrapper("Planting Date",(f.getJSONValue(prod, "plantingdate"))));
        wr.add(new ItemWrapper("Weeding Date",(f.getJSONValue(prod, "datefirstmanualweedcontrol"))));
        wr.add(new ItemWrapper("Main Sales Person",f.getJSONValue(ph,"mainpointofsaleorcontact")));
        wr.add(new ItemWrapper("Proportion of Crops Sold",f.getJSONValue(ph, "proportionformarket")));
         **/
        itmWrap.put("Farmer Summary",wr);

        wr = new ArrayList<ItemWrapper>();
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
                if(str.endsWith("date")){
                    wrPostharvest.add(new ItemWrapper(pHarvestName[i],IctcCKwUtil.getReadableDate(postHarvest.getString(str))));
                } else
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

        prod = f.getProductionJSON();
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
        String [] profilingsf = {
                "Second_constraint_after_farm_planning",
                "Second_constraint_after_marketing",
                "Second_constraint_after_post_harvest",
                "Second_constraint_after_field_production",
                "main_constraint_farmplanning",
                "main_constraint_field_production",
                "main_constraint_general",
                "main_constraint_marketing",
                "main_constraint_post_harvest",
                "other_constraint_farm_planning",
                "other_constraint_field_production",
                "other_constraint_marketing",
                "other_constraint_post_harvest"

        };


        String[] profiling ={
                    "second constraint farm planning",
                    "second constraint after marketing",
                    "second constraint post harvest",
                    "second constraint field production",
                    "main constraint general",
                    "main constraint field production",
                    "main constraint marketing",
                    "main constraint post market",
                    "other constraint farm planning",
                    "other constraint field production",
                    "other constraint marketing",
                    "other constraint post harvest"
        };


         JSONObject techNeeds = f.getJSONObject(f.getTechnicalNeeds());//PostHarvestJSON();
        Log.i(FarmerUtil.class.getName(),"TechNeeds in code : "+techNeeds);
        List<ItemWrapper> wrNeeds = new ArrayList<ItemWrapper>();
        i=0;
        for(String str:profilingsf){
            try {
               // wrNeeds.add(new ItemWrapper(needsNM[i], techNeeds.getString(str)));
                wrNeeds.add(new ItemWrapper(profiling[i],techNeeds.getString(str)));
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
        wr.add(new ItemWrapper("Other names",f.getFirstName()));
        wr.add(new ItemWrapper("Nickname",f.getNickname()));
        wr.add(new ItemWrapper("Cluster",f.getCluster()));
        wr.add(new ItemWrapper("District",f.getDistrict()));
        wr.add(new ItemWrapper("Community",f.getCommunity()));
        wr.add(new ItemWrapper("Age",f.getAge()));
        wr.add(new ItemWrapper("Gender",f.getGender()));
//        wr.add(new ItemWrapper("Land Size",IctcCKwUtil.formatDouble(f.getTargetArea())));



        JSONObject obj = f.getBaselinePostHarvestJSON();
        JSONObject ph= f.getPostHarvestJSON();

        JSONObject bud= f.getBaselineProductionBudgetJSON();

        JSONObject prod= f.getProductionJSON();



        wr.add(new ItemWrapper("Last Season Yield Per Acre",""));


        System.out.println("Production JAson : "+prod.toString());
        wr.add(new ItemWrapper("Land Size",f.getJSONValue(prod,"acresofland")));
        wr.add(new ItemWrapper("This Season Target Yield per Acre",f.getJSONValue(prod,"targetyieldperacre")));
        wr.add(new ItemWrapper("This Season Expected Price Per Ton",f.getJSONValue(prod,"targetyieldperacre")));
         wr.add(new ItemWrapper("Planting Date",(f.getJSONValue(prod, "plantingdate"))));
        wr.add(new ItemWrapper("Weeding Date",(f.getJSONValue(prod, "datefirstmanualweedcontrol"))));
         wr.add(new ItemWrapper("Main Sales Person",f.getJSONValue(ph,"mainpointofsaleorcontact")));
        wr.add(new ItemWrapper("Proportion of Crops Sold",f.getJSONValue(ph, "proportionformarket")));

        itmWrap.put("Farmer Summary",wr);
        return itmWrap;
    }



    public static  List<ItemWrapper>  getFarmManagementPlan(Farmer f,List<FarmerInputs> myInputs ) {



        JSONObject productionJSOn  = f.getProductionJSON();
        JSONObject postHarvest  = f.getPostHarvestJSON();

        System.out.println("Production : "+f.getProduction());

        JSONObject jOb =  f.getBaselinePostHarvestBudgetJSON();
        JSONObject j = f.getBaselinePostHarvestBudgetJSON();

        List<ItemWrapper> itemAgric  = new ArrayList<ItemWrapper>();

        itemAgric.add(new ItemWrapper("","Production"));
        itemAgric.add(new ItemWrapper(" Key Dates","",true));
        itemAgric.add(new ItemWrapper("  Field preparation",String.valueOf(getStringFromJSON(productionJSOn, "ploughingdate"))));
        itemAgric.add(new ItemWrapper("  Planting",String.valueOf(getStringFromJSON(productionJSOn, "plantingdate"))));
        itemAgric.add(new ItemWrapper("  First Fertilizer application",String.valueOf(getStringFromJSON(productionJSOn, "applicationofbasalfertilizerdate"))));
        itemAgric.add(new ItemWrapper("  First post-plant herbicide application",String.valueOf(getStringFromJSON(productionJSOn, "applicationmonthofherbicidedate"))));
        itemAgric.add(new ItemWrapper("  First manual weed control",String.valueOf(getStringFromJSON(productionJSOn, "datefirstmanualweedcontrol"))));
        itemAgric.add(new ItemWrapper("  Second Fertilizer application",String.valueOf(getStringFromJSON(productionJSOn, ""))));
        itemAgric.add(new ItemWrapper("  Harvest",String.valueOf(getStringFromJSON(productionJSOn, "timeofharvest"))));



        itemAgric.add(new ItemWrapper(" Key Inputs","",true));

        itemAgric.add(new ItemWrapper("  Crop variety",String.valueOf(getStringFromJSON(productionJSOn, "nameofcropvariety"))));
        itemAgric.add(new ItemWrapper("  Quantity of pre-plant herbicide",String.valueOf(getStringFromJSON(productionJSOn, "quantity_of_preplant_herbicide_base"))));
        itemAgric.add(new ItemWrapper("  Quantity of post plant herbicide",String.valueOf(getStringFromJSON(productionJSOn, "quantitypostplantherbicide"))));
        itemAgric.add(new ItemWrapper("  Quantity of fertilizer: first application",String.valueOf(getStringFromJSON(productionJSOn, "basal_fertilizer_appl_lab_base"))));
        itemAgric.add(new ItemWrapper("  Quantity of fertilizer: second application",String.valueOf(getStringFromJSON(productionJSOn, "quantityoftopdresserfertilizerpurchased"))));

        itemAgric.add(new ItemWrapper(" Key Practices","",true));
        itemAgric.add(new ItemWrapper("  Method of Land clearing",String.valueOf(getStringFromJSON(productionJSOn, "methodoflandclearing"))));
        itemAgric.add(new ItemWrapper("  Distance between rows",String.valueOf(getStringFromJSON(productionJSOn, "plantingdistancebetweenrow"+f.getMainCrop().toLowerCase()))));
        itemAgric.add(new ItemWrapper("  Method of first fertilizer application",String.valueOf(getStringFromJSON(productionJSOn, "methodofbasalfertilizerapplication"))));
        itemAgric.add(new ItemWrapper("  No of occasions of post-plant herbicide application",String.valueOf(getStringFromJSON(productionJSOn, ""))));
        itemAgric.add(new ItemWrapper("  No. of occasions of manual weed control using hoe",String.valueOf(getStringFromJSON(productionJSOn, ""))));




        itemAgric.add(new ItemWrapper("","Post Harvest"));
        itemAgric.add(new ItemWrapper(" Key Dates","",true));
        itemAgric.add(new ItemWrapper("  Complete threshing",String.valueOf(getStringFromJSON(postHarvest, "completionofthreshing"))));
        itemAgric.add(new ItemWrapper("  Complete drying of grain",String.valueOf(getStringFromJSON(postHarvest, "datetocompletedrying"))));
        itemAgric.add(new ItemWrapper("  First sale date",String.valueOf(getStringFromJSON(postHarvest, "firstsaledate"))));
        itemAgric.add(new ItemWrapper("  Most sale date",String.valueOf(getStringFromJSON(postHarvest, "mostproducesaledate"))));


        itemAgric.add(new ItemWrapper(" Key Inputs","",true));



        itemAgric.add(new ItemWrapper(" Type of buyer",String.valueOf(getStringFromJSON(postHarvest, "buyernumber1"))));
        itemAgric.add(new ItemWrapper("  Ownership of storage structure",String.valueOf(getStringFromJSON(postHarvest, "typeofstoragestructure"))));
        itemAgric.add(new ItemWrapper("  Type of storage chemical",String.valueOf(getStringFromJSON(postHarvest, "typeofstoragechemical"))));

        itemAgric.add(new ItemWrapper(" Key Practices","",true));
        itemAgric.add(new ItemWrapper("  Method winnowing",String.valueOf(getStringFromJSON(postHarvest, "methodofwinnowing"))));
        itemAgric.add(new ItemWrapper("  Method of threshing",String.valueOf(getStringFromJSON(postHarvest, "methodofthreshing"))));
        itemAgric.add(new ItemWrapper("  Method of drying",String.valueOf(getStringFromJSON(postHarvest, "methodofdryinggrain"))));
        itemAgric.add(new ItemWrapper("  Proportion sold",String.valueOf(getStringFromJSON(postHarvest, "proportionformarket"))));


        itemAgric.add(new ItemWrapper("","Farm inputs credit needs"));

        int cnt=0;
        for(FarmerInputs fi : myInputs){
            String title="";
            String xt="";String act=String.valueOf(fi.getQty());
            if(fi.getQty()<1.0){
                xt=" Not Given ";
                act="NA";
            }else{
                act= IctcCKwUtil.formatDoubleNoDecimal(fi.getQty());
            }
            itemAgric.add(new ItemWrapper("  "+fi.getName().substring(0,1).toUpperCase()+fi.getName().substring(1),act));
            cnt++;
        }


        itemAgric.add(new ItemWrapper("  Farm inputs credit received(Seeds)",String.valueOf(getStringFromJSON(jOb, ""))));
        itemAgric.add(new ItemWrapper("  Bags of fertilizer",String.valueOf(getStringFromJSON(jOb, ""))));
        itemAgric.add(new ItemWrapper("  Liters of Herbicide 1",String.valueOf(getStringFromJSON(jOb, ""))));
        itemAgric.add(new ItemWrapper("  Liters of Herbicide 2",String.valueOf(getStringFromJSON(jOb, ""))));
        itemAgric.add(new ItemWrapper("  Ploughing services",String.valueOf(getStringFromJSON(jOb, ""))));
        itemAgric.add(new ItemWrapper("  Transport service",String.valueOf(getStringFromJSON(jOb, ""))));


        itemAgric.add(new ItemWrapper("","Crop budget summary"));
        itemAgric.add(new ItemWrapper(" Overall","",true));

        FarmerBudget budget = getFarmerBudget(f);
        itemAgric.add(new ItemWrapper("  Area",String.valueOf(budget.getArea())));
        itemAgric.add(new ItemWrapper("  Total yield",String.valueOf(budget.getTotalYield())));
        itemAgric.add(new ItemWrapper("  Total revenue",String.valueOf(budget.getRevenue())));
        itemAgric.add(new ItemWrapper("  Total costs",String.valueOf(budget.getTotalCost())));
        itemAgric.add(new ItemWrapper("  Gross margin",String.valueOf(budget.getGrossMargin())));



        itemAgric.add(new ItemWrapper(" Total Production Costs",String.valueOf(budget.getProductionCost())));
        itemAgric.add(new ItemWrapper(" Total Post-harvest Costs",String.valueOf(budget.getPostHarvestCost())));
        itemAgric.add(new ItemWrapper(" Total cost of inputs expected to be received",String.valueOf(budget.getInputReceivedCost())));
        itemAgric.add(new ItemWrapper(" Average cost per acre",String.valueOf(budget.getAverageCostPerAcre())));
        itemAgric.add(new ItemWrapper(" Average revenue/acre",String.valueOf(budget.getAverageRevenuePerAcre())));
        itemAgric.add(new ItemWrapper(" Benefit:Cost ratio",String.valueOf(budget.getCostBenefitRatio())));


        return itemAgric;
    }


    public static  List<ItemWrapper>  getFarmerBaseline(Farmer f,List<FarmerInputs> myInputs ) {



        JSONObject productionJSOn  = f.getBaselineProductionJSON();
        JSONObject postHarvest  = f.getBaselinePostHarvestJSON();

        System.out.println("Production : "+f.getProduction());

        JSONObject jOb =  f.getBaselinePostHarvestBudgetJSON();
        JSONObject j = f.getBaselinePostHarvestBudgetJSON();

        List<ItemWrapper> itemAgric  = new ArrayList<ItemWrapper>();

        itemAgric.add(new ItemWrapper("","Production"));
        itemAgric.add(new ItemWrapper(" Key Dates","",true));
        itemAgric.add(new ItemWrapper("  Field preparation",String.valueOf(getStringFromJSON(productionJSOn, "ploughing_date_base"))));
        itemAgric.add(new ItemWrapper("  Planting",String.valueOf(getStringFromJSON(productionJSOn, "planting_date_base"))));
        itemAgric.add(new ItemWrapper("  First Fertilizer application",String.valueOf(getStringFromJSON(productionJSOn, "time_basal_fert_appl_base"))));//
        itemAgric.add(new ItemWrapper("  First post-plant herbicide application",String.valueOf(getStringFromJSON(productionJSOn, "applic_postplant_herb_date_base"))));//
        itemAgric.add(new ItemWrapper("  First manual weed control",String.valueOf(getStringFromJSON(productionJSOn, "datefirstmanualweedcontrol"))));
        itemAgric.add(new ItemWrapper("  Second Fertilizer application",String.valueOf(getStringFromJSON(productionJSOn, ""))));
        itemAgric.add(new ItemWrapper("  Harvest",String.valueOf(getStringFromJSON(productionJSOn, "date_of_harvest_base"))));



        itemAgric.add(new ItemWrapper(" Key Inputs","",true));

        itemAgric.add(new ItemWrapper("  Crop variety",String.valueOf(getStringFromJSON(productionJSOn, "name_of_other_variety_base"))));
        itemAgric.add(new ItemWrapper("  Quantity of pre-plant herbicide",String.valueOf(getStringFromJSON(productionJSOn, "quantity_of_preplant_herbicide_base"))));
        itemAgric.add(new ItemWrapper("  Quantity of post plant herbicide",String.valueOf(getStringFromJSON(productionJSOn, "postplant_herbicide_use_base"))));
        itemAgric.add(new ItemWrapper("  Quantity of fertilizer: first application",String.valueOf(getStringFromJSON(productionJSOn, "basal_fertilizer_appl_lab_base"))));
        itemAgric.add(new ItemWrapper("  Quantity of fertilizer: second application",String.valueOf(getStringFromJSON(productionJSOn, "quantityoftopdresserfertilizerpurchased"))));

        itemAgric.add(new ItemWrapper(" Key Practices","",true));
        itemAgric.add(new ItemWrapper("  Method of Land clearing",String.valueOf(getStringFromJSON(productionJSOn, "method_of_land_clearing_base"))));
        itemAgric.add(new ItemWrapper("  Distance between rows",String.valueOf(getStringFromJSON(productionJSOn, "distance_between_plants_base"))));
        itemAgric.add(new ItemWrapper("  Method of first fertilizer application",String.valueOf(getStringFromJSON(productionJSOn, "method_bfert_applic_base"))));
        itemAgric.add(new ItemWrapper("  No of occasions of post-plant herbicide application",String.valueOf(getStringFromJSON(productionJSOn, "no_occasions_manual_weed_base"))));
        itemAgric.add(new ItemWrapper("  No. of occasions of manual weed control using hoe",String.valueOf(getStringFromJSON(productionJSOn, "no_occasions_manual_weed_base"))));




        itemAgric.add(new ItemWrapper("","Post Harvest"));
        itemAgric.add(new ItemWrapper(" Key Dates","",true));
        itemAgric.add(new ItemWrapper("  Complete threshing",String.valueOf(getStringFromJSON(postHarvest, "completionofthreshing"))));
        itemAgric.add(new ItemWrapper("  Complete drying of grain",String.valueOf(getStringFromJSON(postHarvest, "datetocompletedrying"))));
        itemAgric.add(new ItemWrapper("  First sale date",String.valueOf(getStringFromJSON(postHarvest, "firstsaledate"))));
        itemAgric.add(new ItemWrapper("  Most sale date",String.valueOf(getStringFromJSON(postHarvest, "mostproducesaledate"))));


        itemAgric.add(new ItemWrapper(" Key Inputs","",true));



        itemAgric.add(new ItemWrapper(" Type of buyer",String.valueOf(getStringFromJSON(postHarvest, "buyernumber1"))));
        itemAgric.add(new ItemWrapper("  Ownership of storage structure",String.valueOf(getStringFromJSON(postHarvest, "typeofstoragestructure"))));
        itemAgric.add(new ItemWrapper("  Type of storage chemical",String.valueOf(getStringFromJSON(postHarvest, "typeofstoragechemical"))));

        itemAgric.add(new ItemWrapper(" Key Practices","",true));
        itemAgric.add(new ItemWrapper("  Method winnowing",String.valueOf(getStringFromJSON(postHarvest, "methodofwinnowing"))));
        itemAgric.add(new ItemWrapper("  Method of threshing",String.valueOf(getStringFromJSON(postHarvest, "methodofthreshing"))));
        itemAgric.add(new ItemWrapper("  Method of drying",String.valueOf(getStringFromJSON(postHarvest, "methodofdryinggrain"))));
        itemAgric.add(new ItemWrapper("  Proportion sold",String.valueOf(getStringFromJSON(postHarvest, "proportionformarket"))));


        itemAgric.add(new ItemWrapper(" Farm inputs credit received","",true));

        int cnt=0;
        for(FarmerInputs fi : myInputs){
            String title="";
            String xt="";String act=String.valueOf(fi.getQty());
            if(fi.getQty()<1.0){
                xt=" Not Given ";
                act="NA";
            }else{
                act= IctcCKwUtil.formatDoubleNoDecimal(fi.getQty());
            }
            itemAgric.add(new ItemWrapper("  "+fi.getName().substring(0,1).toUpperCase()+fi.getName().substring(1),act));
            cnt++;
        }


        itemAgric.add(new ItemWrapper("  Farm inputs credit received",String.valueOf(getStringFromJSON(jOb, ""))));
        itemAgric.add(new ItemWrapper("  Bags of fertilizer",String.valueOf(getStringFromJSON(jOb, ""))));
        itemAgric.add(new ItemWrapper("  Liters of Herbicide 1",String.valueOf(getStringFromJSON(jOb, ""))));
        itemAgric.add(new ItemWrapper("  Liters of Herbicide 2",String.valueOf(getStringFromJSON(jOb, ""))));
        itemAgric.add(new ItemWrapper("  Ploughing services",String.valueOf(getStringFromJSON(jOb, ""))));
        itemAgric.add(new ItemWrapper("  Transport service",String.valueOf(getStringFromJSON(jOb, ""))));


        itemAgric.add(new ItemWrapper("","Crop budget summary"));
        itemAgric.add(new ItemWrapper(" Overall","",true));

        FarmerBudget budget = getFarmerBudget(f);
        itemAgric.add(new ItemWrapper("  Area",String.valueOf(budget.getArea())));
        itemAgric.add(new ItemWrapper("  Total yield",String.valueOf(budget.getTotalYield())));
        itemAgric.add(new ItemWrapper("  Total revenue",String.valueOf(budget.getRevenue())));
        itemAgric.add(new ItemWrapper("  Total costs",String.valueOf(budget.getTotalCost())));
        itemAgric.add(new ItemWrapper("  Gross margin",String.valueOf(budget.getGrossMargin())));



        itemAgric.add(new ItemWrapper(" Total Production Costs",String.valueOf(budget.getProductionCost())));
        itemAgric.add(new ItemWrapper(" Total Post-harvest Costs",String.valueOf(budget.getPostHarvestCost())));
        itemAgric.add(new ItemWrapper(" Total cost of inputs expected to be received",String.valueOf(budget.getInputReceivedCost())));
        itemAgric.add(new ItemWrapper(" Average cost per acre",String.valueOf(budget.getAverageCostPerAcre())));
        itemAgric.add(new ItemWrapper(" Average revenue/acre",String.valueOf(budget.getAverageRevenuePerAcre())));
        itemAgric.add(new ItemWrapper(" Benefit:Cost ratio",String.valueOf(budget.getCostBenefitRatio())));


        return itemAgric;
    }


    public static  List<ItemWrapper>  getFarmerSummaryBudget(Farmer f){


        Map<String,List<ItemWrapper>> itmWrap = new HashMap<String, List<ItemWrapper>>();


        /**
         *





         */

        String []  production= {
                " Target area",
                " Target total yield",
                " Season  ",
                " Target /Expected revenue ",
                " Total Expected costs ",
                " Expected Gross margin ",
               };

        double price=120;

        JSONObject productionJSOn  = f.getProductionJSON();
       String areaOfLand = f.getJSONValue(productionJSOn,"acresofland");
        double acreOfland=0.0;
        try{
            if(areaOfLand.equalsIgnoreCase(""))
                acreOfland=0.0;
            else
                acreOfland = Double.parseDouble(areaOfLand);
        }catch(Exception e){
            acreOfland=0.0;

        }


//        acreOfland =1.3;
        String yieldPerAcre  = f.getJSONValue(productionJSOn,"targetyieldperacre");

        double yield=0.0;

        try{
            if(yieldPerAcre.equalsIgnoreCase(""))
                yield=0.0;
            else
                yield = Double.parseDouble(yieldPerAcre);
        }catch(Exception e){
            yield=0.0;

        }
        //test data
//        yield=15;
        double revenue = price*yield;

        List<ItemWrapper> itemSum = new ArrayList<ItemWrapper>();
        itemSum.add(new ItemWrapper(production[0],String.valueOf(acreOfland)));
        itemSum.add(new ItemWrapper(production[1],String.valueOf(yield)));
        itemSum.add(new ItemWrapper(production[2],"2015/1"));
        itemSum.add(new ItemWrapper(production[3], String.valueOf(revenue)));



        String []  agricInputs= { " Rent",
                "Seeds",
                " Pre-plant Planting material   ",
                " herbicide ",
                " Ploughing",
                " Harrowing",
                " First Post-Plant Herbicide Application",
                " Second Post-Herbicide Application",
                " Post-Plant Herbicide Application ",
                " Fertilizer - First Application  ",
        };

//        String []  agricInputs= { " Rent",
//                " Planting Material",
//                " Pre-plant Planting material   ",
//                " herbicide ",
//                " Ploughing",
//                " Harrowing",
//                " First Post-Plant Herbicide Application",
//                " Second Post-Herbicide Application",
//                " Post-Plant Herbicide Application ",
//                " Fertilizer - First Application  ",
//        };


        String []  agricInputsItem= { "land_rent_base",
                "seedplanting_material_cost_base",
                "quantity_of_preplant_herbicide_base",
                "price_of_herbicide_base",
                "ploughing_cost_per_acre_base",
                "harrowing_cost_per_acre_base",

                "herbicide_application_cost_base",
                "herbicide_application_cost_base",
                "post_plant_herbicide_cost_base",
                "price_of_basal_fertilizer_base",
        };

        JSONObject jOb = f.getBaselineProductionBudgetJSON();

        List<ItemWrapper> itemAgric  = new ArrayList<ItemWrapper>();

        itemAgric.add(new ItemWrapper(" Rent",String.valueOf(getDoubleFromJSON(jOb,agricInputsItem[0])*acreOfland)));
        itemAgric.add(new ItemWrapper(" Seed",String.valueOf(getDoubleFromJSON(jOb,agricInputsItem[1])*acreOfland)));
        itemAgric.add(new ItemWrapper("","Land Clearing"));


        double cst = getDoubleFromJSON(jOb,"quantity_of_preplant_herbicide_liters") * (getDoubleFromJSON(jOb,"cost_of_herbicide_ghc")* acreOfland);
        itemAgric.add(new ItemWrapper(" Pre-Plant Herbicide",String.valueOf(cst)));

        cst = getDoubleFromJSON(jOb,"herbicide_application") * acreOfland;
        itemAgric.add(new ItemWrapper(" Pre-plant herbicide application",String.valueOf(cst)));


//        itemAgric.add(new ItemWrapper(" Land preparation",
//                String.valueOf(
//                        (getDoubleFromJSON(jOb,"ploughing_cost_per_acre_base")*acreOfland)
//
//
//                )));

        itemAgric.add(new ItemWrapper("","Ploughing"));


        itemAgric.add(new ItemWrapper(" Hoe",String.valueOf(getDoubleFromJSON(jOb,"total_cost_hoe_ploughing"))));

        itemAgric.add(new ItemWrapper(" Tractor plough",String.valueOf(getDoubleFromJSON(jOb,"ploughing_cost_total")*acreOfland)));

        itemAgric.add(new ItemWrapper(" Tractor harrow",String.valueOf(getDoubleFromJSON(jOb,"harrowing_cost_total"))));
        itemAgric.add(new ItemWrapper("",""));
        itemAgric.add(new ItemWrapper(" Seedbed preparation",String.valueOf(getDoubleFromJSON(jOb,"seedbed_labor_cost")*acreOfland)));

        itemAgric.add(new ItemWrapper("  Planting and refilling",
                String.valueOf((
                        getDoubleFromJSON(jOb,"planting_labor_cost")*acreOfland)
                        +getDoubleFromJSON(jOb,"refilling_labour_cost"))));



        itemAgric.add(new ItemWrapper(" Post-plant herbicide",
                String.valueOf(
                          (getDoubleFromJSON(jOb,"postplantherbide2price")*getDoubleFromJSON(jOb,"postplantherbicide2qty") * acreOfland)+
                                (getDoubleFromJSON(jOb,"postplantherbicide1qty")*getDoubleFromJSON(jOb,"postplantherbicide1price") * acreOfland)
                )));

        /**
         * (+)
         */
        itemAgric.add(new ItemWrapper(" Post-plant herbicide application",
                String.valueOf(
                        getDoubleFromJSON(jOb,"postplantherbicide1apply")+ (getDoubleFromJSON(jOb,"postplantherbicide2apply"))* acreOfland

                )));

        itemAgric.add(new ItemWrapper(" Organic fertilizer quantity used",String.valueOf(getDoubleFromJSON(jOb,"organicfertilizerqtycurrent"))));

        itemAgric.add(new ItemWrapper(" Organic fertilizer cost",String.valueOf(getDoubleFromJSON(jOb,"organicfertilizercostcurrent"))));

        itemAgric.add(new ItemWrapper(" Organic fertilizer application cost",String.valueOf(getDoubleFromJSON(jOb,"organicfertilizerappcostcurrent"))));

        itemAgric.add(new ItemWrapper(" Quantity of Inorganic fertilizer used (First/basal)",String.valueOf(getDoubleFromJSON(jOb,"fertilizerbasalnobags")*acreOfland)));
        itemAgric.add(new ItemWrapper(" Inorganic fertilizer used (First/basal)",String.valueOf(getDoubleFromJSON(jOb,"fertilizerbasalnobags")*acreOfland*getDoubleFromJSON(jOb,"cost_of_basal_fertilizer"))));



        itemAgric.add(new ItemWrapper(" Inorganic fertilizer application(first/basal)",String.valueOf(getDoubleFromJSON(jOb,"basal_fertilizer_labour_appl_cost"))));

        itemAgric.add(new ItemWrapper(" Quantity of Inorganic fertilizer used (second/topdress)",String.valueOf(getDoubleFromJSON(jOb,"fetilizertopdressnobags")*acreOfland)));



        itemAgric.add(new ItemWrapper(" Cost of Inorganic fertilizer used (second/topdress) ",
                String.valueOf(
                        getDoubleFromJSON(jOb,"fertilizertopdressnobaggs")*(getDoubleFromJSON(jOb,"cost_of_topdress_fertilizer"))* acreOfland

                )));


        itemAgric.add(new ItemWrapper(" Inorganic fertilizer application(second/topdress)",  String.valueOf(
                getDoubleFromJSON(jOb,"cost_of_applicationtopdress")

        )));

        itemAgric.add(new ItemWrapper(" Manual weed control",  String.valueOf(
                getDoubleFromJSON(jOb,"total_cost_of_labor_first_manual_weed")+
                        getDoubleFromJSON(jOb,"total_cost_of_labor_second_manual_weed")+
                        getDoubleFromJSON(jOb,"total_cost_of_labor_third_manual_weed")
        )));

        itemAgric.add(new ItemWrapper(" Harvesting",  String.valueOf(
                getDoubleFromJSON(jOb,"harvest_labor_costs")*acreOfland

        )));





        double productionCost=0.0;
        for(ItemWrapper w : itemAgric){
            try {
                productionCost+=(Double.parseDouble(w.getValue()));
            }catch(Exception e){

            }


        }


        itemAgric.add(new ItemWrapper("Production Cost ",String.valueOf(productionCost)));
        double cost=0.0;

        int k=0;
//        for(String agric:agricInputsItem){
//           double val=0.0;
//            try{
//                String aItem  = f.getJSONItem(jOb,agric);
//
//                if(!aItem.isEmpty())
//                {
//                    try{
//                        val = Double.parseDouble(aItem);
//                    }catch(Exception e){
//                        val=0.0;
//                    }
//                }
//
//
//            }catch(Exception e){
//                    val=0.0;
//            }
//            System.out.println("agricInput:::"+agricInputs[k]+" <> "+val+"<>"+acreOfland);
//            double unitCost = val* acreOfland;
//
//            cost+=unitCost;
//            itemAgric.add(new ItemWrapper(agricInputs[k],String.valueOf(unitCost)));
//            k++;
//
//        }

        String []  labours= { " Planting",
                " Ridge mound preparation",
                " Gap filling",
                " First Post-Plant Herbicide Application ",
                " Second Post-Herbicide Application",
                " Fertilizer Application",
        };
        String []  labourItems= { "labor_for_planting_number_base",
                "seedbed_labor_cost_base",
                "refilling_labor_period_base",
                "herbicide_app_labor_period_base",
                "post_herbicide_application_cost_base",
                "fertilizer_app_labour_cost_base",
        };



        String []  labourItemsCnt= {
                "labor_for_planting_number_base",
                "seedbed_labor_cost_base",
                "refilling_labor_period_base",
                "herbicide_app_labor_period_base",
                "post_herbicide_application_cost_base",
                "fertilizer_app_labour_cost_base",
        };

       List<ItemWrapper> itemLabour = new ArrayList<ItemWrapper>();
        double labourCost=0;
        k=0;
        for(String labour:labourItems){
            k++;
            try{
                String aItem  = f.getJSONItem(jOb,labour);
                double val=0.0;
                if(!aItem.isEmpty())
                {
                    try{
                        val = Double.parseDouble(aItem);
                    }catch(Exception e){

                    }
                }
                double unitCost = val* acreOfland;
                cost+=unitCost;
                itemLabour.add(new ItemWrapper(labours[k],String.valueOf(unitCost)));
            }catch(Exception e){
            }
        }

        JSONObject postharvest = f.getBaselinePostHarvestBudgetJSON();

        System.out.println("Post harvest budget json : "+postharvest);

        /**
         *     "labor_period_drying_cobs_base": "2",
         "time_completion_of_bagging_base": "2",
         "cost_of_storage_chemical_base": "3.0",
         "unit_cost_of_storage_bags_base": "2",
         "labor_hands_dryingcobs_base": "2",
         "labour_winnowing_base": "6",
         "": "5",
         "grain_drying_cost_base": "6.0",
         "": "1.0",
         "labor_cost_dehuskingpeeling_base": "20.0",
         "dehuskingpeeling_labor_base": "1",
         "labor_cost_drying_of_cobs_base": "5.0",
         "unit_cost_machine_threshing_base": "15.0",
         "period_to_complete_dehusking_base": "3.0",
         "time_interval_winnowing_base": "2"
         */

        itemLabour = new ArrayList<ItemWrapper>();
        itemLabour.add(new ItemWrapper(" Processing costs (machine  , shelling and winnowing)",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"processingcosts1")

                )));

        itemLabour.add(new ItemWrapper(" Transportation from farm to homestead",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"transportcostshome1")

                )));


        itemLabour.add(new ItemWrapper(" Dehusking",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"labor_cost_dehuskingpeeling")

                )));


        itemLabour.add(new ItemWrapper(" Drying of cobs",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"unit_cost_drying_of_cobs")

                )));



        itemLabour.add(new ItemWrapper(" Threshing",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"unit_cost_manual_threshing")

                )));


        itemLabour.add(new ItemWrapper(" Drying grain",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"grain_drying_cost")
//                                *getDoubleFromJSON(postharvest,"unit_cost_of_storage_bags_base")

                )));

        itemLabour.add(new ItemWrapper(" Winnowing",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"unit_labour_cost_winnowing")

                )));

        itemLabour.add(new ItemWrapper("Storage bags",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"bags_for_storage")

                )));



        itemLabour.add(new ItemWrapper(" Storage chemical",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"cost_of_storage_chemical")

                )));

        itemLabour.add(new ItemWrapper(" Bagging",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"unit_labor_cost_bagging")

                )));



        itemLabour.add(new ItemWrapper(" Commercial storage",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"cost_of_warehouse")

                )));

        itemLabour.add(new ItemWrapper(" Cost of transportation to market",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"transportcostsmarket")

                )));

        double postHarvesCost=0.0;
        for(ItemWrapper w : itemLabour){

            postHarvesCost+=(Double.parseDouble(w.getValue()));
        }
        itemLabour.add((new ItemWrapper("Sub-total Post-harvest costs",String.valueOf(postHarvesCost))));


        cost=productionCost+postHarvesCost;
        double grossMargin = revenue - (cost);

        List<ItemWrapper> itemGM = new ArrayList<ItemWrapper>();

        itemGM.add(new ItemWrapper("Total Expected Costs",String.valueOf(cost)));
        itemGM.add(new ItemWrapper("Gross Margin ",String.valueOf((yield * price)-cost)));

        itemSum.add(new ItemWrapper(production[4], String.valueOf(cost)));
        itemSum.add(new ItemWrapper(production[5], String.valueOf(grossMargin)));
        List<ItemWrapper> allItems = new ArrayList<ItemWrapper>();
        allItems.add(new ItemWrapper("","Summary"));
        allItems.addAll(itemSum);

//        itmWrap.put("Summary",item);
//        itmWrap.put("Agric Inputs",itemAgric);
        allItems.add(new ItemWrapper("","Production Costs"));
        allItems.addAll(itemAgric);

//        itmWrap.put("Labour Cost",itemLabour);

        allItems.add(new ItemWrapper("","Post Harvest Cost"));


        allItems.addAll(itemLabour);


        allItems.add(new ItemWrapper("","Gross Margin"));

        allItems.addAll(itemGM);


        return allItems;
    }


    public static FarmerBudget getFarmerBudget(Farmer f){


        Map<String,List<ItemWrapper>> itmWrap = new HashMap<String, List<ItemWrapper>>();


        String []  production= {
                " Target area",
                " Target total yield",
                " Season  ",
                " Target /Expected revenue ",
                " Total Expected costs ",
                " Expected Gross margin ",
        };

        double price=120;

        JSONObject productionJSOn  = f.getProductionJSON();
        String areaOfLand = f.getJSONValue(productionJSOn,"acresofland");
        double acreOfland=0.0;
        try{
            if(areaOfLand.equalsIgnoreCase(""))
                acreOfland=0.0;
            else
                acreOfland = Double.parseDouble(areaOfLand);
        }catch(Exception e){
            acreOfland=0.0;

        }


//        acreOfland =1.3;
        String yieldPerAcre  = f.getJSONValue(productionJSOn,"targetyieldperacre");

        double yield=0.0;

        try{
            if(yieldPerAcre.equalsIgnoreCase(""))
                yield=0.0;
            else
                yield = Double.parseDouble(yieldPerAcre);
        }catch(Exception e){
            yield=0.0;

        }
        //test data
//        yield=15;
        double revenue = price*yield;

        List<ItemWrapper> itemSum = new ArrayList<ItemWrapper>();
        itemSum.add(new ItemWrapper(production[0],String.valueOf(acreOfland)));
        itemSum.add(new ItemWrapper(production[1],String.valueOf(yield)));
        itemSum.add(new ItemWrapper(production[2],"2015/1"));
        itemSum.add(new ItemWrapper(production[3], String.valueOf(revenue)));



        String []  agricInputs= { " Rent",
                "Seeds",
                " Pre-plant Planting material   ",
                " herbicide ",
                " Ploughing",
                " Harrowing",
                " First Post-Plant Herbicide Application",
                " Second Post-Herbicide Application",
                " Post-Plant Herbicide Application ",
                " Fertilizer - First Application  ",
        };



        String []  agricInputsItem= { "land_rent_base",
                "seedplanting_material_cost_base",
                "quantity_of_preplant_herbicide_base",
                "price_of_herbicide_base",
                "ploughing_cost_per_acre_base",
                "harrowing_cost_per_acre_base",

                "herbicide_application_cost_base",
                "herbicide_application_cost_base",
                "post_plant_herbicide_cost_base",
                "price_of_basal_fertilizer_base",
        };

        JSONObject jOb = f.getBaselineProductionBudgetJSON();

        List<ItemWrapper> itemAgric  = new ArrayList<ItemWrapper>();

        itemAgric.add(new ItemWrapper(" Rent",String.valueOf(getDoubleFromJSON(jOb,agricInputsItem[0])*acreOfland)));
        itemAgric.add(new ItemWrapper(" Seed",String.valueOf(getDoubleFromJSON(jOb,agricInputsItem[1])*acreOfland)));
        itemAgric.add(new ItemWrapper("","Land Clearing"));


        double cst = getDoubleFromJSON(jOb,"quantity_of_preplant_herbicide_liters") * (getDoubleFromJSON(jOb,"cost_of_herbicide_ghc")* acreOfland);
        itemAgric.add(new ItemWrapper(" Pre-Plant Herbicide",String.valueOf(cst)));

        cst = getDoubleFromJSON(jOb,"herbicide_application") * acreOfland;
        itemAgric.add(new ItemWrapper(" Pre-plant herbicide application",String.valueOf(cst)));


//        itemAgric.add(new ItemWrapper(" Land preparation",
//                String.valueOf(
//                        (getDoubleFromJSON(jOb,"ploughing_cost_per_acre_base")*acreOfland)
//
//
//                )));

        itemAgric.add(new ItemWrapper("","Ploughing"));


        itemAgric.add(new ItemWrapper(" Hoe",String.valueOf(getDoubleFromJSON(jOb,"total_cost_hoe_ploughing"))));

        itemAgric.add(new ItemWrapper(" Tractor plough",String.valueOf(getDoubleFromJSON(jOb,"ploughing_cost_total")*acreOfland)));

        itemAgric.add(new ItemWrapper(" Tractor harrow",String.valueOf(getDoubleFromJSON(jOb,"harrowing_cost_total"))));
        itemAgric.add(new ItemWrapper("",""));
        itemAgric.add(new ItemWrapper(" Seedbed preparation",String.valueOf(getDoubleFromJSON(jOb,"seedbed_labor_cost")*acreOfland)));

        itemAgric.add(new ItemWrapper("  Planting and refilling",
                String.valueOf((
                        getDoubleFromJSON(jOb,"planting_labor_cost")*acreOfland)
                        +getDoubleFromJSON(jOb,"refilling_labour_cost"))));



        itemAgric.add(new ItemWrapper(" Post-plant herbicide",
                String.valueOf(
                        (getDoubleFromJSON(jOb,"postplantherbide2price")*getDoubleFromJSON(jOb,"postplantherbicide2qty") * acreOfland)+
                                (getDoubleFromJSON(jOb,"postplantherbicide1qty")*getDoubleFromJSON(jOb,"postplantherbicide1price") * acreOfland)
                )));

        /**
         * (+)
         */
        itemAgric.add(new ItemWrapper(" Post-plant herbicide application",
                String.valueOf(
                        getDoubleFromJSON(jOb,"postplantherbicide1apply")+ (getDoubleFromJSON(jOb,"postplantherbicide2apply"))* acreOfland

                )));

        itemAgric.add(new ItemWrapper(" Organic fertilizer quantity used",String.valueOf(getDoubleFromJSON(jOb,"organicfertilizerqtycurrent"))));

        itemAgric.add(new ItemWrapper(" Organic fertilizer cost",String.valueOf(getDoubleFromJSON(jOb,"organicfertilizercostcurrent"))));

        itemAgric.add(new ItemWrapper(" Organic fertilizer application cost",String.valueOf(getDoubleFromJSON(jOb,"organicfertilizerappcostcurrent"))));

        itemAgric.add(new ItemWrapper(" Quantity of Inorganic fertilizer used (First/basal)",String.valueOf(getDoubleFromJSON(jOb,"fertilizerbasalnobags")*acreOfland)));
        itemAgric.add(new ItemWrapper(" Inorganic fertilizer used (First/basal)",String.valueOf(getDoubleFromJSON(jOb,"fertilizerbasalnobags")*acreOfland*getDoubleFromJSON(jOb,"cost_of_basal_fertilizer"))));



        itemAgric.add(new ItemWrapper(" Inorganic fertilizer application(first/basal)",String.valueOf(getDoubleFromJSON(jOb,"basal_fertilizer_labour_appl_cost"))));

        itemAgric.add(new ItemWrapper(" Quantity of Inorganic fertilizer used (second/topdress)",String.valueOf(getDoubleFromJSON(jOb,"fetilizertopdressnobags")*acreOfland)));



        itemAgric.add(new ItemWrapper(" Cost of Inorganic fertilizer used (second/topdress) ",
                String.valueOf(
                        getDoubleFromJSON(jOb,"fertilizertopdressnobaggs")*(getDoubleFromJSON(jOb,"cost_of_topdress_fertilizer"))* acreOfland

                )));


        itemAgric.add(new ItemWrapper(" Inorganic fertilizer application(second/topdress)",  String.valueOf(
                getDoubleFromJSON(jOb,"cost_of_applicationtopdress")

        )));

        itemAgric.add(new ItemWrapper(" Manual weed control",  String.valueOf(
                getDoubleFromJSON(jOb,"total_cost_of_labor_first_manual_weed")+
                        getDoubleFromJSON(jOb,"total_cost_of_labor_second_manual_weed")+
                        getDoubleFromJSON(jOb,"total_cost_of_labor_third_manual_weed")
        )));

        itemAgric.add(new ItemWrapper(" Harvesting",  String.valueOf(
                getDoubleFromJSON(jOb,"harvest_labor_costs")*acreOfland

        )));





        double productionCost=0.0;
        for(ItemWrapper w : itemAgric){
            try {
                productionCost+=(Double.parseDouble(w.getValue()));
            }catch(Exception e){

            }


        }


        itemAgric.add(new ItemWrapper("Production Cost ",String.valueOf(productionCost)));
        double cost=0.0;

        int k=0;
//        for(String agric:agricInputsItem){
//           double val=0.0;
//            try{
//                String aItem  = f.getJSONItem(jOb,agric);
//
//                if(!aItem.isEmpty())
//                {
//                    try{
//                        val = Double.parseDouble(aItem);
//                    }catch(Exception e){
//                        val=0.0;
//                    }
//                }
//
//
//            }catch(Exception e){
//                    val=0.0;
//            }
//            System.out.println("agricInput:::"+agricInputs[k]+" <> "+val+"<>"+acreOfland);
//            double unitCost = val* acreOfland;
//
//            cost+=unitCost;
//            itemAgric.add(new ItemWrapper(agricInputs[k],String.valueOf(unitCost)));
//            k++;
//
//        }

        String []  labours= { " Planting",
                " Ridge mound preparation",
                " Gap filling",
                " First Post-Plant Herbicide Application ",
                " Second Post-Herbicide Application",
                " Fertilizer Application",
        };
        String []  labourItems= { "labor_for_planting_number_base",
                "seedbed_labor_cost_base",
                "refilling_labor_period_base",
                "herbicide_app_labor_period_base",
                "post_herbicide_application_cost_base",
                "fertilizer_app_labour_cost_base",
        };



        String []  labourItemsCnt= {
                "labor_for_planting_number_base",
                "seedbed_labor_cost_base",
                "refilling_labor_period_base",
                "herbicide_app_labor_period_base",
                "post_herbicide_application_cost_base",
                "fertilizer_app_labour_cost_base",
        };

        List<ItemWrapper> itemLabour = new ArrayList<ItemWrapper>();
        double labourCost=0;
        k=0;
        for(String labour:labourItems){
            k++;
            try{
                String aItem  = f.getJSONItem(jOb,labour);
                double val=0.0;
                if(!aItem.isEmpty())
                {
                    try{
                        val = Double.parseDouble(aItem);
                    }catch(Exception e){

                    }
                }
                double unitCost = val* acreOfland;
                cost+=unitCost;
                itemLabour.add(new ItemWrapper(labours[k],String.valueOf(unitCost)));
            }catch(Exception e){
            }
        }

        JSONObject postharvest = f.getBaselinePostHarvestBudgetJSON();

        System.out.println("Post harvest budget json : "+postharvest);

        /**
         *     "labor_period_drying_cobs_base": "2",
         "time_completion_of_bagging_base": "2",
         "cost_of_storage_chemical_base": "3.0",
         "unit_cost_of_storage_bags_base": "2",
         "labor_hands_dryingcobs_base": "2",
         "labour_winnowing_base": "6",
         "": "5",
         "grain_drying_cost_base": "6.0",
         "": "1.0",
         "labor_cost_dehuskingpeeling_base": "20.0",
         "dehuskingpeeling_labor_base": "1",
         "labor_cost_drying_of_cobs_base": "5.0",
         "unit_cost_machine_threshing_base": "15.0",
         "period_to_complete_dehusking_base": "3.0",
         "time_interval_winnowing_base": "2"
         */

        itemLabour = new ArrayList<ItemWrapper>();
        itemLabour.add(new ItemWrapper(" Processing costs (machine  , shelling and winnowing)",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"processingcosts1")

                )));

        itemLabour.add(new ItemWrapper(" Transportation from farm to homestead",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"transportcostshome1")

                )));


        itemLabour.add(new ItemWrapper(" Dehusking",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"labor_cost_dehuskingpeeling")

                )));


        itemLabour.add(new ItemWrapper(" Drying of cobs",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"unit_cost_drying_of_cobs")

                )));



        itemLabour.add(new ItemWrapper(" Threshing",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"unit_cost_manual_threshing")

                )));


        itemLabour.add(new ItemWrapper(" Drying grain",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"grain_drying_cost")
//                                *getDoubleFromJSON(postharvest,"unit_cost_of_storage_bags_base")

                )));

        itemLabour.add(new ItemWrapper(" Winnowing",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"unit_labour_cost_winnowing")

                )));

        itemLabour.add(new ItemWrapper("Storage bags",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"bags_for_storage")

                )));



        itemLabour.add(new ItemWrapper(" Storage chemical",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"cost_of_storage_chemical")

                )));

        itemLabour.add(new ItemWrapper(" Bagging",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"unit_labor_cost_bagging")

                )));



        itemLabour.add(new ItemWrapper(" Commercial storage",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"cost_of_warehouse")

                )));

        itemLabour.add(new ItemWrapper(" Cost of transportation to market",
                String.valueOf(
                        getDoubleFromJSON(postharvest,"transportcostsmarket")

                )));

        double postHarvesCost=0.0;
        for(ItemWrapper w : itemLabour){

            postHarvesCost+=(Double.parseDouble(w.getValue()));
        }
        itemLabour.add((new ItemWrapper("Sub-total Post-harvest costs",String.valueOf(postHarvesCost))));


        cost=productionCost+postHarvesCost;
        double grossMargin = revenue - (cost);

        List<ItemWrapper> itemGM = new ArrayList<ItemWrapper>();

        itemGM.add(new ItemWrapper("Total Expected Costs",String.valueOf(cost)));
        itemGM.add(new ItemWrapper("Gross Margin ",String.valueOf((yield * price)-cost)));

        itemSum.add(new ItemWrapper(production[4], String.valueOf(cost)));
        itemSum.add(new ItemWrapper(production[5], String.valueOf(grossMargin)));
       //FarmerBudget(double productionCost,double postHarvestCost,double area,double revenue,double inputReceivedCost){
       return new FarmerBudget(productionCost,postHarvesCost,acreOfland,(yield * price),0.0,yield);



    }
    public static Double getDoubleFromJSON(JSONObject json, String fieldName){
        try {
            String aItem = json.getString(fieldName);
            if(!aItem.isEmpty())
            {
                try{
                    return Double.parseDouble(aItem);
                }catch(Exception e){

                }
            }
        }catch(Exception e ){
        }
        return 0.0;
    }


    public static String getStringFromJSON(JSONObject json, String fieldName){
        try {
            String aItem = json.getString(fieldName);
            if(!aItem.isEmpty())
            {
                try{
                    return (aItem);
                }catch(Exception e){

                }
            }
        }catch(Exception e ){
        }
        return "";
    }

    /**
     *
     public static  List<ItemWrapper>  getFarmerSummaryBudget(Farmer f){


     Map<String,List<ItemWrapper>> itmWrap = new HashMap<String, List<ItemWrapper>>();



     String []  production= { " Land cultivated",
     " Yield (bags)",
     " Season  ",
     " Revenue ",
     " Gross Margin ",
     };

     double price=120;

     JSONObject productionJSOn  = f.getProductionJSON();
     String areaOfLand = f.getJSONValue(productionJSOn,"acresofland");
     double acreOfland=0.0;
     try{
     if(areaOfLand.equalsIgnoreCase(""))
     acreOfland=0.0;
     else
     acreOfland = Double.parseDouble(areaOfLand);
     }catch(Exception e){
     acreOfland=0.0;

     }


     //        acreOfland =1.3;
     String yieldPerAcre  = f.getJSONValue(productionJSOn,"targetyieldperacre");

     double yield=0.0;

     try{
     if(yieldPerAcre.equalsIgnoreCase(""))
     yield=0.0;
     else
     yield = Double.parseDouble(yieldPerAcre);
     }catch(Exception e){
     yield=0.0;

     }
     //test data
     //        yield=15;

     List<ItemWrapper> itemSum = new ArrayList<ItemWrapper>();
     itemSum.add(new ItemWrapper(production[0],String.valueOf(acreOfland)));
     itemSum.add(new ItemWrapper(production[1],String.valueOf(yield)));
     itemSum.add(new ItemWrapper(production[2],"2015/1"));
     itemSum.add(new ItemWrapper(production[3], String.valueOf(price*yield)));

     String []  agricInputs= { " Land",
     " Planting Material",
     " Pre-plant Planting material   ",
     " herbicide ",
     " Ploughing",
     " Harrowing",
     " First Post-Plant Herbicide Application",
     " Second Post-Herbicide Application",
     " Post-Plant Herbicide Application ",
     " Fertilizer - First Application  ",
     };


     String []  agricInputsItem= { "land_rent_base",
     "seedplanting_material_cost_base",
     "quantity_of_preplant_herbicide_base",
     "price_of_herbicide_base",
     "ploughing_cost_per_acre_base",
     "harrowing_cost_per_acre_base",

     "herbicide_application_cost_base",
     "herbicide_application_cost_base",
     "post_plant_herbicide_cost_base",
     "price_of_basal_fertilizer_base",
     };
     List<ItemWrapper> itemAgric  = new ArrayList<ItemWrapper>();

     double cost=0.0;
     JSONObject jOb = f.getBaselineProductionBudgetJSON();
     int k=0;
     for(String agric:agricInputsItem){
     double val=0.0;
     try{
     String aItem  = f.getJSONItem(jOb,agric);

     if(!aItem.isEmpty())
     {
     try{
     val = Double.parseDouble(aItem);
     }catch(Exception e){
     val=0.0;
     }
     }


     }catch(Exception e){
     val=0.0;
     }
     System.out.println("agricInput:::"+agricInputs[k]+" <> "+val+"<>"+acreOfland);
     double unitCost = val* acreOfland;

     cost+=unitCost;
     itemAgric.add(new ItemWrapper(agricInputs[k],String.valueOf(unitCost)));
     k++;

     }

     String []  labours= { " Planting",
     " Ridge mound preparation",
     " Gap filling",
     " First Post-Plant Herbicide Application ",
     " Second Post-Herbicide Application",
     " Fertilizer Application",
     };
     String []  labourItems= { "labor_for_planting_number_base",
     "seedbed_labor_cost_base",
     "refilling_labor_period_base",
     "herbicide_app_labor_period_base",
     "post_herbicide_application_cost_base",
     "fertilizer_app_labour_cost_base",
     };



     String []  labourItemsCnt= { "labor_for_planting_number_base",
     "seedbed_labor_cost_base",
     "refilling_labor_period_base",
     "herbicide_app_labor_period_base",
     "post_herbicide_application_cost_base",
     "fertilizer_app_labour_cost_base",
     };

     List<ItemWrapper> itemLabour = new ArrayList<ItemWrapper>();
     double labourCost=0;
     k=0;
     for(String labour:labourItems){
     k++;
     try{
     String aItem  = f.getJSONItem(jOb,labour);
     double val=0.0;
     if(!aItem.isEmpty())
     {
     try{
     val = Double.parseDouble(aItem);
     }catch(Exception e){}
     }
     double unitCost = val* acreOfland;
     cost+=unitCost;
     itemLabour.add(new ItemWrapper(labours[k],String.valueOf(unitCost)));
     }catch(Exception e){
     }
     }

     List<ItemWrapper> itemGM = new ArrayList<ItemWrapper>();
     itemGM.add(new ItemWrapper("Gross Margin ",String.valueOf((yield * price)-cost)));

     itemSum.add(new ItemWrapper(production[4], String.valueOf(price*yield -cost)));
     List<ItemWrapper> allItems = new ArrayList<ItemWrapper>();
     allItems.add(new ItemWrapper("","Summary"));
     allItems.addAll(itemSum);

     //        itmWrap.put("Summary",item);
     //        itmWrap.put("Agric Inputs",itemAgric);
     allItems.add(new ItemWrapper("","Agric Inputs"));
     allItems.addAll(itemAgric);

     itmWrap.put("Labour Cost",itemLabour);

     allItems.add(new ItemWrapper("","Labour Cost"));
     allItems.addAll(itemLabour);


     allItems.add(new ItemWrapper("","Gross Margin"));
     allItems.addAll(itemGM);


     return allItems;
     }
     */
}
