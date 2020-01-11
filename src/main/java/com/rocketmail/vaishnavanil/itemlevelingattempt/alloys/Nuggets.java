package com.rocketmail.vaishnavanil.itemlevelingattempt.alloys;

public enum Nuggets {
    //Naming convention :: ENUM NAME = "N" + ID
    N1(1000001,"Adamantite Nugget"),
    N2(1000002,"Chlorophyte Nugget"),
    N3(1000003,"Cobalt Nugget"),
    N4(1000004,"Copper Nugget"),
    N5(1000005,"Crimtane Nugget"),
    N6(1000006,"Demonite Nugget"),
    N7(1000007,"Impure Gold Nugget"),
    N8(1000008,"Hellstone Nugget"),
    N9(1000009,"Impure Iron Nugget"),
    N10(1000010,"Lead Nugget"),
    N11(1000011,"Luminite Nugget"),
    N12(1000012,"Meteorite Nugget"),
    N13(1000013,"Mythril Nugget"),
    N14(1000014,"Obsidian Nugget"),
    N15(1000015,"Orichalcum Nugget"),
    N16(1000016,"Paladium Nugget"),
    N17(1000017,"Platnium Nugget"),
    N18(1000018,"Silver Nugget"),
    N19(1000019,"Tungsten Nugget"),
    N20(1000020,"Tin Nugget"),
    N21(1000021,"Titanium Nugget")
    ;
    private String name;
    private Nugget nug;
    Nuggets(int id,String name){
        nug = new Nugget(name,id);
        this.name = name;
    }
    public Nugget getNugget(){
        return nug;
    }
    public static Nugget getByID(int id){
        return Nuggets.valueOf("N"+id).nug;
    }
}
