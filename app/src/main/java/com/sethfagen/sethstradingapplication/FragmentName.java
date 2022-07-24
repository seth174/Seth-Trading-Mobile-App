package com.sethfagen.sethstradingapplication;

import androidx.fragment.app.Fragment;

public enum FragmentName {
    PURCHASE_FRAGMENT,
    SELL_FRAGMENT,
    ACCOUNT_HOME_FRAGMENT;

    public static int getInteger(FragmentName fragmentName){
        switch (fragmentName){
            case SELL_FRAGMENT:return 1;
            case PURCHASE_FRAGMENT: return 2;
            case ACCOUNT_HOME_FRAGMENT: return 3;
            default:return -1;
        }
    }

    public static Fragment getFragment(int fragment){
        switch (fragment){
            case 1 : return new SellStockFragment();
            case 2 : return new PurchaseStockFragment();
            case 3 : return new AccountHomeFragment();
            default: return null;
        }
    }

    public static FragmentName getFragmentName(Fragment fragment){
        if(fragment instanceof PurchaseStockFragment){
            return PURCHASE_FRAGMENT;
        }
        else if(fragment instanceof SellStockFragment){
            return SELL_FRAGMENT;
        }
        else if(fragment instanceof AccountHomeFragment){
            return ACCOUNT_HOME_FRAGMENT;
        }
        else{
            return null;
        }
    }
}
