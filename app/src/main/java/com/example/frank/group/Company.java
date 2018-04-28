package com.example.frank.group;

/**
 * Created by JiachengYe on 4/25/2018.
 */

public class Company {
    String symbol;
    String companyName;
    String exchange;
    String industry;
    String website;
    String description;
    String CEO;
    String issueType;
    String sector;
    double price;

    public Company(String symbol, String companyName, String exchange,
                   String industry, String website, String description,
                   String CEO, String issueType, String sector){
        this.symbol = symbol;
        this.companyName = companyName;
        this.exchange = exchange;
        this.industry = industry;
        this.website = website;
        this.description = description;
        this.CEO = CEO;
        this.issueType = issueType;
        this.sector = sector;
    }

    public Company(){

    }
}
