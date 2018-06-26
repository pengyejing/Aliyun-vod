package com.zachwang.aliyunvod.controller;

import com.aliyun.vod.upload.resp.BaseResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zachwang.aliyunvod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VodController {
  private static final String VIDEO_ID = "videoId";
  private static final String PLAY_AUTH = "playAuth";
  @Autowired
  private VodService vodService;

  /**
   * 视频测试首页.
   *
   * @return 视频测试首页
   */
  @RequestMapping(value = {"", "/", "/home"})
  public ModelAndView home() {
    return new ModelAndView("home");
  }

  /**
   * 上传一个测试视频，并返回该视频的ID.
   *
   * @return 上传成功返回视频ID.
   */
  @RequestMapping("/upload")
  @ResponseBody
  public String uploadVideo() {
    String title = "这个是视频的标题";
    String descrption = "这个是该视频的描述信息";
    String fileName = "C:/Users/zachw/Downloads/测试视频-床垫广告.mp4";
    Long cateId = 896575296L; // 视频分类"测试"
    String tags = "测试2,测试3";
    String coverUrl = "https://app.youbanban.com/gkiwi/console/oss/imgs/zoom/1/goldenkiwi"
        + "/poi/cc1ccaf35117a31f2a3cdfca6cd47704.jpg";
    BaseResponse response = vodService.uploadVideo(title, descrption, fileName, cateId,
        tags, coverUrl);
    if (response.isSuccess()) {
      return response.getVideoId();
    } else {
      return "";
    }
  }

  /**
   * 获取视频的播放凭证.
   *
   * @param videoId 视频ID
   * @return 视频播放凭证
   */
  @RequestMapping("/play_auth")
  @ResponseBody
  public String getPlayAuth(@RequestParam(VIDEO_ID) String videoId) {
    GetVideoPlayAuthResponse response = vodService.getVideoPlayAuth(videoId);
    if (response != null) {
      return response.getPlayAuth();
    }
    return "";
  }

  /**
   * 通过上面两个接口返回的视频ID和播放凭证，返回视频播放页面.
   *
   * @param videoId  视频ID
   * @param playAuth 播放凭证
   * @return 返回视频播放页面
   */
  @RequestMapping(value = "/play_video", method = RequestMethod.GET)
  public ModelAndView playVideo(@RequestParam(VIDEO_ID) String videoId,
                                @RequestParam(PLAY_AUTH) String playAuth) {
    ModelAndView modelAndView = new ModelAndView("play_video");
    modelAndView.addObject(VIDEO_ID, videoId);
    modelAndView.addObject(PLAY_AUTH, playAuth);
    return modelAndView;
  }
}
