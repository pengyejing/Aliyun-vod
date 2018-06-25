package com.zachwang.aliyunvod.controller;

import com.aliyun.vod.upload.resp.BaseResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zachwang.aliyunvod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class VodController {
  @Autowired
  private VodService vodService;

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
  public String getPlayAuth(@RequestParam("videoId") String videoId) {
    GetVideoPlayAuthResponse response = vodService.getVideoPlayAuth(videoId);
    if (response != null) {
      return response.getPlayAuth();
    }
    return "";
  }

  /**
   * 播放视频页面.
   *
   * @param map 参数
   * @return 播放视频页面
   */
  @RequestMapping(value = "/play_video", method = RequestMethod.POST)
  public ModelAndView playVideo(@RequestBody Map<String, String> map) {
    String videoId = map.getOrDefault("videoId", "");
    String playAuth = map.getOrDefault("playAuth", "");
    ModelAndView modelAndView = new ModelAndView("play_video");
    modelAndView.addObject("videoId", videoId);
    modelAndView.addObject("playAuth", playAuth);
    return modelAndView;
  }

  @RequestMapping(value = {"", "/", "/home"})
  public ModelAndView home() {
    return new ModelAndView("home");
  }
}
