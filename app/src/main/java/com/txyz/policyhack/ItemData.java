package com.txyz.policyhack;

/**
 * Created by naman on 18/04/15.
 */
public class ItemData {


    private String title;
    private int imageUrl;
    private String block;
    private String village;

    public ItemData(String title,int imageUrl,String block,String village){

        this.title = title;
        this.imageUrl = imageUrl;
        this.block=block;
        this.village=village;
    }
    // getters & setters

    public String getTitle(){
        return title;
    }
    public int getImageUrl(){
        return imageUrl;
    }
    public String getBlock(){
        return block;
    }
    public String getVillage(){
        return village;
    }
}

