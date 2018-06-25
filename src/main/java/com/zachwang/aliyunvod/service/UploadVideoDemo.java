package com.zachwang.aliyunvod.service;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadFileStreamRequest;
import com.aliyun.vod.upload.req.UploadURLStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadFileStreamResponse;
import com.aliyun.vod.upload.resp.UploadURLStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;

/**
 * 以下Java示例代码演示了如何在服务端上传文件至视频点播。
 * 目前支持两种方式上传：
 * 1.上传本地文件，使用分片上传，并支持断点续传，参见testUploadVideo函数。
 * 1.1 当断点续传关闭时，最大支持上传任务执行时间为3000秒，具体可上传文件大小与您的网络带宽及磁盘读写能力有关。
 * 1.2 当断点续传开启时，最大支持48.8TB的单个文件，注意，断点续传开启后，上传任务执行过程中，同时会将当前上传位置写入本地磁盘文件，影响您上传文件的速度，请您根据文件大小选择是否开启
 * 2.上传网络流，可指定文件URL进行上传，不支持断点续传，最大支持5GB的单个文件。参见testUploadURLStream函数。
 * 3.上传文件流，可指定本地文件进行上传，不支持断点续传，最大支持5GB的单个文件。参见testUploadFileStream函数。
 * 请替换示例中的必选参数，示例中的可选参数如果您不需要设置，请将其删除，以免设置无效参数值与您的预期不符。
 */
public class UploadVideoDemo {
  //账号AK信息请填写(必选)
  private static final String accessKeyId = "";
  //账号AK信息请填写(必选)
  private static final String accessKeySecret = "";

  public static void main(String[] args) {
    //视频标题(必选)
    String title = "测试标题";
    //1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选) 2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。任何上传方式文件名必须包含扩展名
    String fileName = "测试文件名称.mp4";
    //本地文件上传
    testUploadVideo(accessKeyId, accessKeySecret, title, fileName);
    //待上传视频的网络流地址
    String url = "http://video.sample.com/sample.mp4";
    //2.网络流上传
    testUploadURLStream(accessKeyId, accessKeySecret, title, fileName, url);
    //3.文件流上传
    testUploadFileStream(accessKeyId, accessKeySecret, title, fileName);
  }

  /**
   * 本地文件上传接口
   *
   * @param accessKeyId
   * @param accessKeySecret
   * @param title
   * @param fileName
   */
  private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
    UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
    /* 可指定分片上传时每个分片的大小，默认为1M字节 */
    request.setPartSize(1 * 1024 * 1024L);
    /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
    request.setTaskNum(1);
        /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
    request.setEnableCheckpoint(false);
    /* OSS慢请求日志打印超时时间，是指每个分片上传时间超过该阈值时会打印debug日志，如果想屏蔽此日志，请调整该阈值。单位: 毫秒，默认为300000毫秒*/
    //request.setSlowRequestsThreshold(300000L);
    /* 可指定每个分片慢请求时打印日志的时间阈值，默认为300s*/
    //request.setSlowRequestsThreshold(300000L);
    /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
    //request.setIsShowWaterMark(true);
    /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
    //request.setCallback("http://callback.sample.com");
    /* 视频分类ID(可选) */
    //request.setCateId(0);
    /* 视频标签,多个用逗号分隔(可选) */
    //request.setTags("标签1,标签2");
    /* 视频描述(可选) */
    //request.setDescription("视频描述");
    /* 封面图片(可选) */
    //request.setCoverURL("http://cover.sample.com/sample.jpg");
    /* 模板组ID(可选) */
    //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
    /* 存储区域(可选) */
    //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
    UploadVideoImpl uploader = new UploadVideoImpl();
    UploadVideoResponse response = uploader.uploadVideo(request);
    System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
    if (response.isSuccess()) {
      System.out.print("VideoId=" + response.getVideoId() + "\n");
    } else {
      /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
      System.out.print("VideoId=" + response.getVideoId() + "\n");
      System.out.print("ErrorCode=" + response.getCode() + "\n");
      System.out.print("ErrorMessage=" + response.getMessage() + "\n");
    }
  }

  /**
   * 网络流上传接口
   *
   * @param accessKeyId
   * @param accessKeySecret
   * @param title
   * @param fileName
   * @param url
   */
  private static void testUploadURLStream(String accessKeyId, String accessKeySecret, String title, String fileName, String url) {
    UploadURLStreamRequest request = new UploadURLStreamRequest(accessKeyId, accessKeySecret, title, fileName, url);
    /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
    //request.setShowWaterMark(true);
    /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
    //request.setCallback("http://callback.sample.com");
    /* 视频分类ID(可选) */
    //request.setCateId(0);
    /* 视频标签,多个用逗号分隔(可选) */
    //request.setTags("标签1,标签2");
    /* 视频描述(可选) */
    //request.setDescription("视频描述");
    /* 封面图片(可选) */
    //request.setCoverURL("http://cover.sample.com/sample.jpg");
    /* 模板组ID(可选) */
    //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
    /* 存储区域(可选) */
    //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
    UploadVideoImpl uploader = new UploadVideoImpl();
    UploadURLStreamResponse response = uploader.uploadURLStream(request);
    System.out.print("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
    if (response.isSuccess()) {
      System.out.print("VideoId=" + response.getVideoId() + "\n");
    } else {
      /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
      System.out.print("VideoId=" + response.getVideoId() + "\n");
      System.out.print("ErrorCode=" + response.getCode() + "\n");
      System.out.print("ErrorMessage=" + response.getMessage() + "\n");
    }
  }

  /**
   * 文件流上传接口
   *
   * @param accessKeyId
   * @param accessKeySecret
   * @param title
   * @param fileName
   */
  private static void testUploadFileStream(String accessKeyId, String accessKeySecret, String title, String fileName) {
    UploadFileStreamRequest request = new UploadFileStreamRequest(accessKeyId, accessKeySecret, title, fileName);
    /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
    //request.setShowWaterMark(true);
    /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
    //request.setCallback("http://callback.sample.com");
    /* 视频分类ID(可选) */
    //request.setCateId(0);
    /* 视频标签,多个用逗号分隔(可选) */
    //request.setTags("标签1,标签2");
    /* 视频描述(可选) */
    //request.setDescription("视频描述");
    /* 封面图片(可选) */
    //request.setCoverURL("http://cover.sample.com/sample.jpg");
    /* 模板组ID(可选) */
    //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
    /* 存储区域(可选) */
    //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
    UploadVideoImpl uploader = new UploadVideoImpl();
    UploadFileStreamResponse response = uploader.uploadFileStream(request);
    System.out.print("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
    if (response.isSuccess()) {
      System.out.print("VideoId=" + response.getVideoId() + "\n");
    } else {
      /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
      System.out.print("VideoId=" + response.getVideoId() + "\n");
      System.out.print("ErrorCode=" + response.getCode() + "\n");
      System.out.print("ErrorMessage=" + response.getMessage() + "\n");
    }
  }
}