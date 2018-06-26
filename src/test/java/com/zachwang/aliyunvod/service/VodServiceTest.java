package com.zachwang.aliyunvod.service;

import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.aliyuncs.vod.model.v20170321.UpdateVideoInfoResponse;

import javax.annotation.Resource;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class VodServiceTest {
  private static final String VIDEO_ID = "28bcbf7167e84795945eb816b175e4be";
  private static final Logger LOG = LoggerFactory.getLogger(VodServiceTest.class);
  @Resource
  private VodService vodService;

  @Test
  public void initVodClient() {
  }

  /**
   * 测试通过.
   */
  @Test
  public void uploadVideo() {
    String title = "这个是视频的标题2";
    String descrption = "这个是该视频的描述信息2";
    String fileName = "C:\\Users\\zachw\\Downloads\\测试视频-床垫广告.mp4";
    Long cateId = 896575296L; // 视频分类"测试"
    String tags = "测试2,测试3";
    String coverUrl = "https://app.youbanban.com/gkiwi/console/oss/imgs/zoom/1/goldenkiwi/poi/cc1ccaf35117a31f2a3cdfca6cd47704.jpg";
    vodService.uploadVideo(title, descrption, fileName, cateId, tags, coverUrl);
  }

  /**
   * 测试通过.
   */
  @Test
  public void uploadUrlStream() {
    String title = "这个是视频的标题1";
    String descrption = "这个是该视频的描述信息1";
    String fileName = "测试视频-空镜03.mp4";
    String url = "http://mp4.vjshi.com/2018-05-25/849ca2426893f351c3a8b9385f73b315.mp4";
    Long cateId = 896575296L; // 视频分类"测试"
    String tags = "测试2,测试3";
    String coverUrl = "";
    vodService.uploadUrlStream(title, descrption, fileName, url, cateId, tags, coverUrl);
  }

  /**
   * 测试通过.
   */
  @Test
  public void uploadFileStream() {
    String title = "这个是视频的标题2";
    String descrption = "这个是该视频的描述信息2";
    String fileName = "C:\\Users\\zachw\\Downloads\\测试视频-床垫广告.mp4";
    Long cateId = 896575296L; // 视频分类"测试"
    String tags = "测试2,测试3";
    String coverUrl = "https://app.youbanban.com/gkiwi/console/oss/imgs/zoom/1/goldenkiwi/poi/cc1ccaf35117a31f2a3cdfca6cd47704.jpg";
    vodService.uploadFileStream(title, descrption, fileName, cateId, tags, coverUrl);
  }

  @Test
  public void showUploadVideoResponse() {
  }

  @Test
  public void createUploadVideo() {
  }

  @Test
  public void refreshUploadVideo() {
  }

  @Test
  public void getPlayInfo() {
    GetPlayInfoResponse playInfo = vodService.getPlayInfo(VIDEO_ID);
    LOG.debug(playInfo.toString());
  }

  @Test
  public void getVideoInfo() {
    GetVideoInfoResponse videoInfo = vodService.getVideoInfo(VIDEO_ID);
    LOG.debug(videoInfo.toString());
  }

  @Test
  public void getVideoPlayAuth() {
    GetVideoPlayAuthResponse response = vodService.getVideoPlayAuth(VIDEO_ID);
    LOG.debug(response.toString());
  }

  @Test
  public void updateVideoInfo() {
    UpdateVideoInfoResponse response = vodService.updateVideoInfo(VIDEO_ID, "这个是视频的标题2",
        "这个是该视频的描述信息2", "测试2,测试3");
    LOG.debug(response.toString());
  }

  @Test
  public void deleteVideo() {
    DeleteVideoResponse response = vodService.deleteVideo("6d6bbb08e5f34d14a3e09985372101d4");
    LOG.debug(response.toString());
  }
}
