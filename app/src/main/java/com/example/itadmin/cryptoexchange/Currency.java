package com.example.itadmin.cryptoexchange;

/**
 * Created by ITAdmin on 11/10/2017.
 */

public class Currency  {

    private String mCurr;

    private final double mBtc;

    private final double mEth;

    public Currency(String curr, double btc, double eth) {
        mCurr = curr;
        mBtc = btc;
        mEth = eth;
    }

    public double getmBtc() { return mBtc; }

    public double getmEth() { return mEth; }

    public String getmCurr() { return mCurr; }

}
