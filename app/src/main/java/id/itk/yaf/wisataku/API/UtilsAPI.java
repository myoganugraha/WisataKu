package id.itk.yaf.wisataku.API;

public class UtilsAPI {
    public static final String BASE_URL_API = "http://sicentang.xyz/ppb/";

    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}
