package com.financial.pyramid.web;

import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.VideoService;
import com.financial.pyramid.web.form.VideoUploadForm;
import com.financial.pyramid.web.form.VideosForm;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.geo.impl.GeoRssWhere;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.util.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * User: dbudunov
 * Date: 16.06.13
 * Time: 16:09
 */
@Controller
@RequestMapping(value = "/video")
public class VideosController {

    @Autowired
    VideoService videoService;

    @Autowired
    SettingsService settingsService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(ModelMap model, @ModelAttribute("video") Video video) {
        String thumbnailUrl = settingsService.getProperty("youTubeVideoThumbnailsUrl", video.externalId);
        video.setThumbnailUrl(thumbnailUrl);
        videoService.save(video);
        List<Video> videoList = videoService.find();
        VideosForm videosForm = new VideosForm();
        videosForm.setVideos(videoList);
        model.addAttribute("videosForm", videosForm);
        model.addAttribute("page-name", "admin");
        model.addAttribute("admin-page-name", "video_settings");
        return "/tabs/admin/videos";
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
        return "/tabs/admin/videos";
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
        YouTubeService service = new YouTubeService("953904755977", "d30mtcoHSY1eo9UpPAMP4g_O");
        try {
            service.setUserCredentials("commercial.web.development@gmail.com", "support247");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return null;
        }
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

//        newEntry.setGeoCoordinates(new GeoRssWhere(37.0, -122.0));
//        newEntry.setLocation("Mountain View, CA");

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
