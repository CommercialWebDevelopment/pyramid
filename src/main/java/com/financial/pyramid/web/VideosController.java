package com.financial.pyramid.web;

import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.VideoService;
import com.financial.pyramid.web.form.VideosForm;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.geo.impl.GeoRssWhere;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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
    public String upload(ModelMap model, @ModelAttribute("video") Video video, @RequestParam(value = "file", required = false) org.springframework.web.multipart.MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        File file = new File(originalFilename);
        boolean result = uploadVideoToYouTubeServer(video, file);
        if (result) {
            return save(model, video);
        }
        return "/tabs/admin/videos";
    }

    private boolean uploadVideoToYouTubeServer(Video video, File file) {
        YouTubeService service = new YouTubeService("866717645001", "FwfeXvGAAzFWsGD9aNdcXaP5");
        VideoEntry newEntry = new VideoEntry();

        YouTubeMediaGroup mediaGroup = newEntry.getOrCreateMediaGroup();
        mediaGroup.setTitle(new MediaTitle());
        mediaGroup.getTitle().setPlainTextContent(video.getName());
        mediaGroup.setKeywords(new MediaKeywords());
        mediaGroup.setDescription(new MediaDescription());
        mediaGroup.getDescription().setPlainTextContent(video.getDescription());
        mediaGroup.setPrivate(false);

        newEntry.setGeoCoordinates(new GeoRssWhere(37.0, -122.0));

        MediaFileSource mediaFileSource = new MediaFileSource(file, "video/quicktime");
        newEntry.setMediaSource(mediaFileSource);

        String uploadUrl = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";

        try {
            VideoEntry createdEntry = service.insert(new URL(uploadUrl), newEntry);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
