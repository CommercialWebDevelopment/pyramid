package com.financial.pyramid.web;

import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.VideoService;
import com.financial.pyramid.settings.Setting;
import com.financial.pyramid.web.form.VideoUploadForm;
import com.financial.pyramid.web.form.VideosForm;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * User: dbudunov
 * Date: 16.06.13
 * Time: 16:09
 */
@Controller
@RequestMapping(value = "/video")
public class VideosController extends AbstractController {

    @Autowired
    VideoService videoService;

    @Autowired
    SettingsService settingsService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(ModelMap model, @ModelAttribute("video") Video video) {
        String thumbnailUrl = settingsService.getProperty(Setting.YOU_TUBE_VIDEO_THUMBNAILS, video.externalId);
        video.setThumbnailUrl(thumbnailUrl);
        videoService.save(video);
        List<Video> videoList = videoService.find();
        VideosForm videosForm = new VideosForm();
        videosForm.setVideos(videoList);
        model.addAttribute("videosForm", videosForm);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "video_settings");
        return "redirect:/pyramid/admin/video_settings";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(ModelMap model, @PathVariable Long id) {
        videoService.remove(id);
        List<Video> videoList = videoService.find();
        VideosForm videosForm = new VideosForm();
        videosForm.setVideos(videoList);
        model.addAttribute("videosForm", videosForm);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "video_settings");
        return "redirect:/pyramid/admin/video_settings";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(ModelMap model, @ModelAttribute("videoUploadForm") VideoUploadForm form) {
        File file = new File(form.getFile().getOriginalFilename());
        try {
            form.getFile().transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Video video = form.getVideo();
        VideoEntry result = uploadVideo(file, video);
        if (result != null) {
            video.setExternalId(result.getId());
            model.addAttribute("uploadSuccess", true);
            return save(model, video);
        }
        List<Video> videoList = videoService.find();
        VideosForm videosForm = new VideosForm();
        videosForm.setVideos(videoList);
        model.addAttribute("uploadSuccess", false);
        model.addAttribute("videosForm", videosForm);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "video_settings");
        return "/tabs/admin/videos";
    }

    private VideoEntry uploadVideo(File file, Video video) {
        YouTubeService service = new YouTubeService("953904755977-i6o899p9n7qt1hs21kia78a1d4tav3lt.apps.googleusercontent.com", "AI39si5hZhL10G6z178tUL-JthEkEL_TA1hEa-up-a0ueQOy_v9cvcnDhGoHCIxfiC2uSRnVKT8MjYcbvKMQz8LoLCdVCn7dkQ");
        String requestUrl = AuthSubUtil.getRequestUrl("http://localhost/RetrieveToken", "http://gdata.youtube.com", false, true);
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            URLConnection urlConnection = null;
            urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
            {
                response.append(line.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String onetimeUseToken = AuthSubUtil.getTokenFromReply(response.toString());
        String sessionToken = null;
        try {
            sessionToken = AuthSubUtil.exchangeForSessionToken(onetimeUseToken, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.setAuthSubToken(sessionToken, null);

        VideoEntry newEntry = new VideoEntry();
        String videoName = video.getName().isEmpty() ? file.getName() : video.getName();

        YouTubeMediaGroup mediaGroup = newEntry.getOrCreateMediaGroup();
        mediaGroup.setTitle(new MediaTitle());
        mediaGroup.getTitle().setPlainTextContent(videoName);
        mediaGroup.setKeywords(new MediaKeywords());
        mediaGroup.getKeywords().addKeyword("mlm business");
        mediaGroup.setDescription(new MediaDescription());
        mediaGroup.getDescription().setPlainTextContent(video.getDescription());
        mediaGroup.setPrivate(false);

        MediaFileSource mediaFileSource = new MediaFileSource(file, "video/quicktime");
        newEntry.setMediaSource(mediaFileSource);
        String uploadUrl = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";
        try {
            return service.insert(new URL(uploadUrl), newEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
