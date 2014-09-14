package com.hrzafer.fatihparser;
/**
 * @author Harun Reşit Zafer
 */
public class NonTerminal {
    private String category;

    private NonTerminal(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category;
    }

    public static final NonTerminal S = new NonTerminal("S");
    public static final NonTerminal SS = new NonTerminal("SS");
    public static final NonTerminal Pre = new NonTerminal("Pre");
    public static final NonTerminal Que = new NonTerminal("Que");
    public static final NonTerminal Sub = new NonTerminal("Sub");
    public static final NonTerminal DObj = new NonTerminal("DObj");
    public static final NonTerminal Comp = new NonTerminal("Comp");
    public static final NonTerminal Adv = new NonTerminal("Adv");

    public static final NonTerminal NP = new NonTerminal("NP");
    public static final NonTerminal JP = new NonTerminal("JP");
    public static final NonTerminal VP = new NonTerminal("VP");
    public static final NonTerminal VJ = new NonTerminal("VJ");
    public static final NonTerminal VN = new NonTerminal("VN");
    public static final NonTerminal VA = new NonTerminal("VA");
    public static final NonTerminal AP = new NonTerminal("AP");
    public static final NonTerminal IP = new NonTerminal("IP");

    public static final NonTerminal NC = new NonTerminal("NC");
    public static final NonTerminal JC = new NonTerminal("JC");
    public static final NonTerminal VJG = new NonTerminal("VJG");
    public static final NonTerminal VNG = new NonTerminal("VNG");
    public static final NonTerminal VAG = new NonTerminal("VAG");
    public static final NonTerminal RG = new NonTerminal("RG");
    public static final NonTerminal PG = new NonTerminal("PG");
    public static final NonTerminal CG = new NonTerminal("CG");
    public static final NonTerminal TG = new NonTerminal("TG");
    public static final NonTerminal PNG = new NonTerminal("PNG");
    public static final NonTerminal IG = new NonTerminal("IG");
    public static final NonTerminal NG = new NonTerminal("NG");
    public static final NonTerminal CV = new NonTerminal("CV");
    



}
