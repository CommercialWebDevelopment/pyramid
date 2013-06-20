package com.financial.pyramid.web.form;

import com.financial.pyramid.domain.Video;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: dbudunov
 * Date: 20.06.13
 * Time: 18:02
 */
public class VideoUploadForm {

    public MultipartFile file;

    public Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
