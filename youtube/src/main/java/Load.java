import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.VideoDetails;
import com.github.kiulian.downloader.model.YoutubeVideo;

import java.io.File;
import java.io.IOException;

public class Load {

    public static void load(String  videoId)  {
        YoutubeDownloader downloader = new YoutubeDownloader();

        downloader.setParserRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        downloader.setParserRetryOnFailure(1);

        System.out.println("https://www.youtube.com/watch?v="+videoId);
        System.out.println(System.getProperty("user.dir"));
        try {
            YoutubeVideo video = downloader.getVideo(videoId);





            video.download(video.formats().get(0),
                    new File(System.getProperty("user.dir")+File.separator+"down"));

            VideoDetails details = video.details();
            System.out.println(details.title());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (YoutubeException e) {
            e.printStackTrace();
        }


// video details
    }
}
