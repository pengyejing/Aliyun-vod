package com.zachwang.aliyunvod.service;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadFileStreamRequest;
import com.aliyun.vod.upload.req.UploadURLStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.BaseResponse;
import com.aliyun.vod.upload.resp.UploadFileStreamResponse;
import com.aliyun.vod.upload.resp.UploadURLStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.youbanban.goldenkiwi.utility.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VodService {
  private static final Logger LOG = LoggerFactory.getLogger(VodService.class);
  @Value("${aliyun.vod.regionId}")
  private String regionId;
  @Value("${aliyun.vod.endpoint}")
  private String endpoint;
  @Value("${aliyun.vod.accessKeyId}")
  private String accessKeyId;
  @Value("${aliyun.vod.accessKeySecret}")
  private String accessKeySecret;

  /**
   * 初始化客户端.
   *
   * @return 客户端
   */
  public DefaultAcsClient initVodClient() {
    DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
    return new DefaultAcsClient(profile);
  }
  //---------------- 上传视频 ----------------//

  /**
   * 上传本地视频到 vod，可续传.
   *
   * @param title       视频标题
   * @param description 视频描述
   * @param fileName    视频文件路径
   * @param cateId      视频分类ID
   * @param tags        视频的标签信息
   * @param coverUrl    视频的封面url
   * @return 上传视频操作结果
   */
  public UploadVideoResponse uploadVideo(String title, String description, String fileName,
                                         Long cateId, String tags, String coverUrl) {
    UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret,
        title, fileName);
    request.setDescription(description);
    request.setCateId(cateId);
    request.setTags(tags);
    request.setCoverURL(coverUrl);
    request.setPartSize(1024 * 1024L);
    request.setTaskNum(1);
    request.setEnableCheckpoint(false);
    UploadVideoImpl uploader = new UploadVideoImpl();
    UploadVideoResponse response = uploader.uploadVideo(request);
    showUploadVideoResponse(response);
    return response;
  }

  /**
   * 上传网络视频到 vod，不可续传.
   *
   * @param title       视频标题
   * @param description 视频描述
   * @param fileName    视频文件路径
   * @param url         网络视频文件链接
   * @param cateId      视频分类ID
   * @param tags        视频的标签信息
   * @param coverUrl    视频的封面url
   * @return 上传视频操作结果
   */
  public UploadURLStreamResponse uploadUrlStream(String title, String description, String fileName,
                                                 String url, Long cateId, String tags, String coverUrl) {
    UploadURLStreamRequest request = new UploadURLStreamRequest(accessKeyId, accessKeySecret,
        title, fileName, url);
    request.setDescription(description);
    request.setCateId(cateId);
    request.setTags(tags);
    request.setCoverURL(coverUrl);
    UploadVideoImpl uploader = new UploadVideoImpl();
    UploadURLStreamResponse response = uploader.uploadURLStream(request);
    showUploadVideoResponse(response);
    return response;
  }

  /**
   * 上传本地视频流到 vod，不可续传.
   *
   * @param title       视频标题
   * @param description 视频描述
   * @param fileName    视频文件路径
   * @param cateId      视频分类ID
   * @param tags        视频的标签信息
   * @param coverUrl    视频的封面url
   * @return 上传视频操作结果
   */
  public UploadFileStreamResponse uploadFileStream(String title, String description, String fileName,
                                                   Long cateId, String tags, String coverUrl) {
    UploadFileStreamRequest request = new UploadFileStreamRequest(accessKeyId, accessKeySecret,
        title, fileName);
    request.setDescription(description);
    request.setCateId(cateId);
    request.setTags(tags);
    request.setCoverURL(coverUrl);
    UploadVideoImpl uploader = new UploadVideoImpl();
    UploadFileStreamResponse response = uploader.uploadFileStream(request);
    showUploadVideoResponse(response);
    return response;
  }

  /**
   * 打印出上传视频返回的结果.
   *
   * @param response 上传视频返回的结果
   */
  public void showUploadVideoResponse(BaseResponse response) {
    LOG.info("RequestId=" + response.getRequestId()); //请求视频点播服务的请求ID
    if (response.isSuccess()) {
      LOG.info("VideoId=" + response.getVideoId());
    } else {
      LOG.info("VideoId=" + response.getVideoId());
      LOG.info("ErrorCode=" + response.getCode());
      LOG.info("ErrorMessage=" + response.getMessage());
    }
  }

  /**
   * 获取视频上传地址和凭证.
   *
   * @return 视频上传地址和凭证
   * @throws ClientException 异常
   */
  public CreateUploadVideoResponse createUploadVideoResponse(
      String title, String description, String fileName, String tags, String coverUrl)
      throws ClientException {
    CreateUploadVideoRequest request = new CreateUploadVideoRequest();
    request.setTitle(title);
    request.setDescription(description);
    request.setFileName(fileName);
    request.setTags(tags);
    request.setCoverURL(coverUrl);
    return initVodClient().getAcsResponse(request);
  }

  /**
   * 刷新视频上传凭证.
   *
   * @return 刷新视频上传凭证
   * @throws ClientException 异常
   */
  public RefreshUploadVideoResponse refreshUploadVideoResponse(String videoId)
      throws ClientException {
    RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
    request.setVideoId(videoId);
    return initVodClient().getAcsResponse(request);
  }

  //---------------- 播放视频 ----------------//

  /**
   * 获取视频播放凭证.
   *
   * @param videoId 视频ID
   * @return 视频播放凭证
   */
  public GetVideoPlayAuthResponse getVideoPlayAuth(String videoId) {
    GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
    request.setVideoId(videoId);
    try {
      GetVideoPlayAuthResponse response = initVodClient().getAcsResponse(request);
      LOG.info(response.getPlayAuth());
      String videoMeta = JsonConverter.toPrettyJson(response.getVideoMeta());
      LOG.info(videoMeta);
      return response;
    } catch (Exception e) {
      LOG.debug(e.getMessage(), e);
    }
    return null;
  }
}
