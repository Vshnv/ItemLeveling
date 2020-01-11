package com.rocketmail.vaishnavanil.itemlevelingattempt.alloys;

public enum Alloys {
    //Naming convention :: ENUM NAME = "A" + ID
    A1(1000001,"Adamantite Ingot"),
    A2(1000002,"Chlorophyte Ingot"),
    A3(1000003,"Cobalt Ingot"),
    A4(1000004,"Copper Ingot"),
    A5(1000005,"Crimtane Ingot"),
    A6(1000006,"Demonite Ingot"),
    A7(1000007,"Tin Ingot"),
    A8(1000008,"Hellstone Ingot"),
    A9(1000009,"Impure Iron Ingot"),
    A10(1000010,"Lead Ingot"),
    A11(1000011,"Luminite Ingot"),
    A12(1000012,"Meteorite Ingot"),
    A13(1000013,"Mythril Ingot"),
    A14(1000014,"Orichalcum Ingot"),
    A15(1000015,"Palladium Ingot"),
    A16(1000016,"Platinum Ingot"),
    A17(1000017,"Silver Ingot"),
    A18(1000018,"Spectre Iron Ingot"),
    A19(1000019,"Shroomite Ingot"),
    A20(1000020,"Hallowed Ingot"),
    A21(1000021,"Titanium Ingot"),
    A22(1000022,"Tungsten Ingot")
    ;
    private String name;
    private Alloy alloy;
    Alloys(int id,String name){
        alloy = new Alloy(name,id);
        this.name = name;
    }
    public Alloy getAlloy(){
        return alloy;
    }
    public static Alloy getByID(int id){
        return Alloys.valueOf("A"+id).alloy;
    }
}
