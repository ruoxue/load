import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Loads {

    public static void main(String[] args) {

        String url="https://www.youtube.com/playlist?list=PLsw2iU9xmpfYbK8TqsB7wHHiuvpjLDp4f";

        try {
            Document document = Jsoup.connect(url).get();
            Elements script =   document.getElementsByTag("script");


            for (Element element : script) {
                if (element.html().contains("videoId")){

                    String html = element.html();


                   html = html.substring(html.indexOf("{"),html.lastIndexOf(";"));


                    JSONObject jsonObject = JSON.parseObject(html);


                    JSONObject contents = jsonObject.getJSONObject("contents")
                            .getJSONObject("twoColumnBrowseResultsRenderer")
                            .getJSONArray("tabs").getJSONObject(0)
                            .getJSONObject("tabRenderer").getJSONObject("content")
                            .getJSONObject("sectionListRenderer")
                            .getJSONArray("contents").getJSONObject(0)
                            .getJSONObject("itemSectionRenderer")
                            .getJSONArray("contents").getJSONObject(0)
                            .getJSONObject("playlistVideoListRenderer")  ;

                    JSONArray contents1 = contents.getJSONArray("contents");


                    for (int i =0 ; i <contents1.size() ; i++) {

                        if (i<31){
                            continue;
                        }
//	enPwd	"	enPwd	"11f60a58d2d63dd9255333672ebb3ffc"	"
                        JSONObject jsonObject1 = contents1.getJSONObject(i);

                        String videoId = jsonObject1.getJSONObject("playlistVideoRenderer").getString("videoId");

                        System.out.println("jsonObject1 = " + videoId);
                        Load.load(videoId);
                    }


                }
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
