package com.example.websitetrivia.storage.data;

// Name of columns in Database.

import android.provider.BaseColumns;

public final class WebsitesPersistenceContract {
    private WebsitesPersistenceContract(){

    }
    public static abstract class WebsiteEntry implements BaseColumns{
        public static final String TABLE_NAME = "websitesTrivia";
        public static final String COLUMN_ENTRY_ID = "entryId";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FOUNDING_YEAR = "foundingYEAR";
        public static final String COLUMN_FOUNDERS = "founders";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_CEO = "CEO";
        public static final String COLUMN_RANK = "rank";
        public static final String COLUMN_TIME_SPENT = "timeSpent";
    }
}
