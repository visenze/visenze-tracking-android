package com.visenze.datatracking;

public class Constants {
    public static final String BASE_URL = "https://staging-analytics.data.visenze.com/v3/";
    public static final String PLATFORM = "Mobile";
    public static final String OS = "Android";
    public static final String SDK_NAME = "Android SDK";


    public static class Action {
        public static final String LOAD = "load";
        public static final String SEARCH = "search";
        public static final String CLICK = "product_click";
        public static final String VIEW = "product_view";
        public static final String INSTALL = "app_install";
        public static final String UNINSTALL = "app_uninstall";
        public static final String TRANSACTION = "transaction";
        public static final String ADD_TO_CART = "add_to_cart";
    }


}
