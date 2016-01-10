package com.example.mirko.proxermeforfiretv;

        import android.os.Environment;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.InputStreamReader;
        import com.loopj.android.http.*;
        import cz.msebera.android.httpclient.Header;
        import org.jsoup.*;
        import org.jsoup.nodes.Document;
        import org.jsoup.select.Elements;

        import org.json.*;

        import org.jsoup.*;
        import org.jsoup.nodes.Document;
        import org.jsoup.select.Elements;



public class ProxerME {
    private static final String[] SUB_AND_DUB = {"gersub","engsub","engdub","engsub"};
    private static int loginID = -1;
    private static String loginFailMessage;
    private static int loginFailID;
    private static SyncHttpClient client = new SyncHttpClient();
    private static ArrayList<ArrayList<ArrayList<String>>> privateAnimes;
    private static ArrayList<ArrayList<ArrayList<String>>> animeEpisodesHtmlTable;
    private static ArrayList<ArrayList<String>> animeEpisodesJson;
    private static int animeStart;
    private static String animeEnd;
    private static ArrayList<String> animeLang = new ArrayList<String>();
    private static String animeKategory;
    private static String jsonStringAnimesEpisode = "";
    private static String[][] streamProvider= {
            /*{"180Upload",""},
            {"Akstream",""},
            {"Allmyvideos",""},
            {"Axavid",""},
            {"Backin",""},
            {"Cloudtime",""},
            {"Cloudvidz",""},
            {"Cloudzilla",""},*/
            //{"crunchyroll","http://www.crunchyroll.com/affiliate_iframeplayer?aff=af-26422-mtqi&video_format=0&video_quality=0&auto_play=0&click_through=1&media_id="},
            /*{"Daclips",""},
            {"Divxstage",""},
            {"Ecostream",""},
            {"Exashare",""},
            {"Faststream",""},
            {"Fastvideo",""},
            {"Filenuke",""},
            {"Flashx",""},
            {"Gamovideo",""},
            {"Flashdrive",""},
            {"Gorillavid",""},
            {"Hellsmedia",""},
            {"Ishared",""},
            {"Junkyvideo",""},
            {"Mega-vids",""},
            {"Mightyupload",""},
            {"Moevideo",""},
            {"Mooshare",""},
            {"Movreel",""},
            {"Movshare",""},
            {"Muchshare",""},
            {"Mystream",""},
            {"Novamov",""},
            {"Nowvideo",""},
            {"Played",""},
            {"Playreplay",""},
            {"Putlocker",""},
            {"Firedrive",""},
            {"Primeshare",""},
            {"Rapidvideo",""},
            {"Shared",""},
            {"Sharerepo",""},
            {"Sharesix",""},
            {"Sharevid",""},
            {"Sharexvid",""},
            {"Slickvid",""},
            {"Sockshare",""},
            {"Speedvideo",""},*/
            {"Streamcloud","http://www.streamcloud.eu/"},
            {"Streamcloud2","http://www.streamcloud.eu/"},
            /*{"Streamin.to",""},
            {"Thevideo",""},
            {"Uploadc",""},
            {"Tumi.tv",""},
            {"Veehd",""},
            {"Vidbull",""},
            {"Videla",""},
            {"Videomega",""},
            {"Videomeh",""},
            {"Videopremium",""},
            {"Videott",""},*/
            {"Videoweed","http://www.videoweed.es/file/"},
            /*{"Videowood",""},
            {"Vidhog",""},
            {"Vidpaid",""},
            {"Vidplay",""},
            {"Vidspot",""},
            {"Vidstream",""},
            {"Vidto",""},
            {"Vidup",""},
            {"Vidzi",""},*/
            {"Viewster","http://www.viewster.com/serie/"},
            /*{"Vivo.sx",""},
            {"Vk",""},
            {"Vodlocker",""},
            {"Vshare",""},
            {"V-vids",""},
            {"Wilibi",""},
            {"Xvidstage",""},
            {"Youwatch",""},
            {"Zinwa",""},*/
    };


    public static ArrayList<ArrayList<ArrayList<String>>> testPrivateAnimes(String html){
        try {
            privateAnimes = null;



            //String html = readFileProxer(Environment.getExternalStorageDirectory().getPath() + "/htmlFilesProxerME/proxerHtmlPRivateAnimes");
            ArrayList<ArrayList<ArrayList<String>>> privateAnimes = parseHTMLPrivateAnimesToList(html);
            /*privateAnimes.add(tempPrivateAnimes.get(1));
            privateAnimes.add(tempPrivateAnimes.get(2));
            privateAnimes.add(tempPrivateAnimes.get(0));*/



            //privateAnimes = getPrivateAnimesHTMLList();
            return privateAnimes;
        }catch (Exception e){
            return privateAnimes;
        }
    }

    public ArrayList<ArrayList<String>> testAnimeEpisodes(String html){
        ArrayList<String> AnimeEpisodes = new ArrayList<>();
        try{
            //String jsonAnimeEpisodes = readFileProxer(Environment.getExternalStorageDirectory().getPath() + "/htmlFilesProxerME/proxerJsonAnimeEpisodes");
            animeEpisodesJson = parseJsonAnimeEpisodesToList(html);
            return animeEpisodesJson;
        }catch (Exception e){
            return animeEpisodesJson;
        }
    }

    private static String readFileProxer(String path){
        String html = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            html = sb.toString();

            return html;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return html;
        } finally {

        }
    }

    public static String getHost(String number,String html){
        for(int i = 0; i<animeEpisodesJson.size();i++){
            if(animeEpisodesJson.get(i).get(0) == number){
                String[] types = animeEpisodesJson.get(i).get(1).split(",");
                for (int index = 0; index < streamProvider.length; index++){
                    for (String viableType: types){
                        if(viableType.toLowerCase().contains(streamProvider[index][0].toLowerCase())){
                            JSONArray arr = new JSONArray();
                            try {
                                String htmlAnimeEpisode = html;
                                jsonStringAnimesEpisode = getJsonStringFromHtml(htmlAnimeEpisode,"streams = ", ";");
                                arr = new JSONArray(jsonStringAnimesEpisode);
                            }catch (Exception e){
                                return "notAvaiable";
                            }
                            try{
                                String test = "";
                                for (int j = 0; j < arr.length(); j++) {

                                    //return arr.getJSONObject(j).get("type").toString().toLowerCase() + "/////" + viableType.toLowerCase();
                                    //streamProverinJson = streamProverinJson+ "/////////////" + arr.getJSONObject(j).get("type").toString().toLowerCase();
                                    test = test + "/////////" + arr.getJSONObject(j).get("type").toString().toLowerCase() + " == " + viableType.toLowerCase();
                                    if (arr.getJSONObject(j).get("type").toString().toLowerCase().contains(viableType.toLowerCase())) {
                                        return streamProvider[index][1] + arr.getJSONObject(j).get("code");
                                    }
                                }
                                return test;
                            }catch (Exception e){
                                return "parseFail";
                            }

                        }
                    }
                }
            }
        }
        return "";
    }


    /*public static ArrayList<ArrayList<ArrayList<String>>> getAnimesEpisodesHTMLList(String Url) throws JSONException{
        ArrayList<String> proxerHttpRequest = new ArrayList<String>();
        String htmlProxer = "";
        ArrayList<ArrayList<ArrayList<String>>> animes = new ArrayList<ArrayList<ArrayList<String>>>();
        if(loginProxer(usernameProxer,passwordProxer)){
            proxerHttpRequest = httpRequestPost("http://proxer.me"+Url+"format=json");
            for(String htmlList:proxerHttpRequest){
                htmlProxer = htmlProxer + htmlList;
            }
            animes = parseHTMLPrivateAnimesToList(htmlProxer);
            return animes;
        }else{
            return animes;
        }
    }*/

    private static String getJsonStringFromHtml(String html, String delimiter,String delimiter2){
        String[] htmlSplit;
        String[] htmlSplit2;
        htmlSplit = html.split(delimiter);
        htmlSplit2 = htmlSplit[1].split(delimiter2);
        return htmlSplit2[0];
    }

    private static ArrayList<ArrayList<ArrayList<String>>> parseHTMLAnimeEpisodesToList(String htmlProxer){
        ArrayList<ArrayList<ArrayList<String>>> animes = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<Elements>> tables = new ArrayList<ArrayList<Elements>>();
        tables = parseTableToList(htmlProxer);
        for (int index = 0;index <tables.size();index++) {
            animes.add(new ArrayList<ArrayList<String>>());
            for (int i = 0; i < tables.get(index).size(); i++) {
                animes.get(index).add(new ArrayList<String>());
                Elements tds = tables.get(index).get(i).select("td");
                for (int j = 0; j < tds.size(); j++) {
                    if(!tds.get(j).text().isEmpty()){
                        animes.get(index).get(i).add(tds.get(j).text());
                        System.out.println(tds.get(j).text());
                    }
                }
            }
        }
        return animes;
    }

    private static ArrayList<ArrayList<String>> parseJsonAnimeEpisodesToList(String json) throws JSONException{
        JSONObject obj = new JSONObject(json);
        ArrayList<ArrayList<String>> jsonList = new ArrayList<ArrayList<String>>();
        System.out.println(obj);
        animeStart = obj.getInt("start");
        animeEnd = obj.getString("end");
        String lang = obj.getJSONArray("lang").toString();
        JSONArray jsonArray = new JSONArray(lang);
        for (int i = 0; i < jsonArray.length(); i++) {
            animeLang.add(jsonArray.getString(i));
        }
        animeKategory = obj.getString("kat");

        JSONArray arr = obj.getJSONArray("data");
        for (int i = 0; i < arr.length(); i++) {
            jsonList.add(new ArrayList<String>());
            jsonList.get(i).add(arr.getJSONObject(i).getString("no"));
            jsonList.get(i).add(arr.getJSONObject(i).getString("types"));
            jsonList.get(i).add(arr.getJSONObject(i).getString("typ"));
        }
        return jsonList;
    }


    /*public static ArrayList<ArrayList<ArrayList<String>>> getPrivateAnimesHTMLList() throws JSONException{
        ArrayList<String> proxerHttpRequest = new ArrayList<String>();
        String htmlProxer = "";
        ArrayList<ArrayList<ArrayList<String>>> animes = new ArrayList<ArrayList<ArrayList<String>>>();
        if(loginProxer(usernameProxer,passwordProxer)){
            proxerHttpRequest = httpRequestPost("http://proxer.me/user/"+loginID+"/anime?format=raw");
            for(String htmlList:proxerHttpRequest){
                htmlProxer = htmlProxer + htmlList;
            }
            animes = parseHTMLPrivateAnimesToList(htmlProxer);
            return animes;
        }else{
            return animes;
        }
    }*/

    private static ArrayList<ArrayList<ArrayList<String>>> parseHTMLPrivateAnimesToList(String htmlProxer){
        ArrayList<ArrayList<ArrayList<String>>> animes = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<Elements>> tables = new ArrayList<ArrayList<Elements>>();
        tables = parseTableToList(htmlProxer);
        for (int index = 0;index <tables.size();index++) {
            animes.add(new ArrayList<ArrayList<String>>());
            for (int i = 0; i < tables.get(index).size(); i++) {
                animes.get(index).add(new ArrayList<String>());
                Elements tds = tables.get(index).get(i).select("td");
                for (int j = 0; j < tds.size(); j++) {
                    if(j%6 == 0){
                        Elements img = tds.get(j).select("img");
                        animes.get(index).get(i).add(img.attr("title"));
                    }else if(j%6 == 1){
                        Elements linkA = tds.get(j).select("a");
                        animes.get(index).get(i).add(linkA.attr("href"));
                        animes.get(index).get(i).add(getAnimeEpisodesBasedOnUrl(linkA.attr("href")));
                        animes.get(index).get(i).add(linkA.text());
                    }else{
                        animes.get(index).get(i).add(tds.get(j).text());
                    }
                }
            }
        }
        return animes;
    }

    private static ArrayList<ArrayList<Elements>> parseTableToList(String htmlProxer){
        ArrayList<ArrayList<Elements>> tablesList = new  ArrayList<ArrayList<Elements>>();
        Document doc = Jsoup.parse(htmlProxer);
        Elements tables = doc.select("table");
        for (int index = 0;index <tables.size();index++) {
            Elements trs = tables.get(index).select("tr");
            tablesList.add(new ArrayList<Elements>());
            for (int i = 0; i < trs.size(); i++) {
                tablesList.get(index).add(trs.get(i).select("td"));
            }
        }
        return tablesList;
    }

    public static String getAnimeEpisodesBasedOnUrl(String Url){
        String[] splitUrl;
        String[] splitUrl2;
        splitUrl = Url.split("#");
        splitUrl2 = splitUrl[0].split("/");
        for(int index = 0; index < splitUrl2.length; index++){
            if(splitUrl2[index].matches("[0-9]+") && splitUrl2[index-1].equals("info")){
                return "/info/"+splitUrl2[index]+"/list";
            }
        }
        return null;
    }

    /*public static boolean loginProxer(String username,String password) throws JSONException{
        if(loginID == -1){
            ArrayList<String> loginCredentials = new ArrayList<String>();
            loginCredentials.add(username);
            loginCredentials.add(password);
            ArrayList<String> loginAnswer = loginRequestPost(loginCredentials);
            if(loginAnswer.isEmpty()){
                return false;
            }else if(loginAnswer.size() == 1){

            }else{
                return false;
            }
        }else{
            return true;
        }
    }

    public static ArrayList<String> httpRequestPost(String url){
        final ArrayList<String> postRequestAnswer = new ArrayList<String>();
        try{
            client.get(url, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String res) {
                            // called when response HTTP status is "200 OK"
                            postRequestAnswer.add(res);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            postRequestAnswer.add(res);
                        }
                    }
            );
            return postRequestAnswer;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return postRequestAnswer;
        }
    }
    private static ArrayList<String> postRequestAnswer = new ArrayList<String>();
    public static ArrayList<String> loginRequestPost(ArrayList<String> postParameters){
        try{
            String url = "https://proxer.me/login?format=json&action=login";
            RequestParams params = new RequestParams();
            params.put("username", usernameProxer);
            params.put("password", passwordProxer);
            client.post(url, params, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String res) {
                            // called when response HTTP status is "200 OK"
                            postRequestAnswer.add(res);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            postRequestAnswer.add(res);
                        }
                    }
            );
            return postRequestAnswer;
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return postRequestAnswer;
        }
    }*/
}
