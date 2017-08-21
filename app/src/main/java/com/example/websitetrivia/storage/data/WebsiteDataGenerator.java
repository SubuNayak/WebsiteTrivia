package com.example.websitetrivia.storage.data;

// Just to assemble data, initial data presentation.
// Dummy data generator
// Helper class, Utility Class - Convert a list strings to object websites - A machine takes strings and converts into website.

import com.example.websitetrivia.domain.model.Website;

import java.util.ArrayList;
import java.util.List;

public class WebsiteDataGenerator {

    private static Website assembleWebsiteFromArray(String[] websites){
        String id =  Long.toString(System.currentTimeMillis())+websites[0];
        Website website = new Website(id, websites[0], Integer.parseInt(websites[1]), websites[2], websites[3], websites[4],
                Integer.parseInt(websites[5]), websites[6]);
        return website;
    }

    public static Website createWebsiteFromData(String name, String foundingYear, String founders,
                                                String location, String CEO, String rank, String timeSpent) {
        if(foundingYear==""||foundingYear==null){
            foundingYear = "0";
        }
        if(rank==""||rank==null){
            rank = "0";
        }
        String id =  Long.toString(System.currentTimeMillis())+name;
        return new Website(id, name, Integer.parseInt(foundingYear), founders, location, CEO,
                Integer.parseInt(rank), timeSpent);
    }

    public static List<Website> getListOfWebsites(List<String[]> data){
        List<Website> websites = new ArrayList<Website>(data.size());
        for(String[] detail: data){
            websites.add(assembleWebsiteFromArray(detail));
        }
        return websites;
    }
}
