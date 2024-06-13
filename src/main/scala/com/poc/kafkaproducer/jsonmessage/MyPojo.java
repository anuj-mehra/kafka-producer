package com.poc.kafkaproducer.jsonmessage;

public class MyPojo {

    private String odsname;
    private String hbasename;

    public String getOdsname() {
        return odsname;
    }

    public void setOdsname(String odsname) {
        this.odsname = odsname;
    }

    public String getHbasename() {
        return hbasename;
    }

    public void setHbasename(String hbasename) {
        this.hbasename = hbasename;
    }

    public boolean equals(Object o){
        boolean areEqual = false;
        if(this == o){
            areEqual = true;
        }else{

            if(o instanceof MyPojo){
                MyPojo obj = (MyPojo)o;

                if(this.hbasename.equals(obj.hbasename)
                && this.odsname.equals((obj.odsname))){
                    areEqual = true;
                }
            }
        }

        return  areEqual;
    }
}
