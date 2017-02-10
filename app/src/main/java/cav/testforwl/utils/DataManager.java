package cav.testforwl.utils;

public class DataManager {
    private static DataManager INSTANCE = null;
    private PreferensManager mPreferensManager;

    public DataManager() {
        this.mPreferensManager = new PreferensManager();
    }

    public static DataManager getInstance() {
        if (INSTANCE==null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public PreferensManager getPreferensManager() {
        return mPreferensManager;
    }
}
