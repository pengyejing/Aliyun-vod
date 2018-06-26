# Aliyun-vod
Aliyun vod project.

本项目实现了阿里云视频点播的web端，没有涉及Android和IOS。
   包含的主要功能：
 * 1、上传视频；
 * 2、播放视频；
 * 3、查看视频信息；
 * 4、查看视频播放信息；
 * 5、修改视频信息；
 * 6、删除视频.

项目需要引入第三方的jar包，jar包的位置在项目的根目录，名字是 aliyun-java-vod-upload-1.3.0.jar  maven命令：

mvn install:install-file -Dfile=./aliyun-java-vod-upload-1.3.0.jar -DgroupId=com.aliyun -DartifactId=aliyun-java-vod-upload -Dversion=1.3.0 -Dpackaging=jar

# 引入好第三方jar包后

1、 把项目根目录下的视频文件（./测试视频-床垫广告.mp4）复制到电脑中的某个位置，复制其路径；

2、 进入controller/VodController，把uploadVideo接口下面的fileName变量修改成之前复制的路径；

3、 运行SpringBoot项目，浏览器中打开该链接进入首页：http://localhost:7000/gkiwi/vod

4、 点击【上传视频】按钮，这个时候会上传之前复制的视频；

5、 如果视频ID显示出来了，说明视频已经在上传了，此时视频可能在上传中或转码中等状态，等待一会；

6、 点击【获取上面视频的播放授权】，获取该视频的播放授权；

7、 如果第6步没有效果，继续等待5秒钟，重新获取；

8、 如果视频ID和播放授权均以获取到了，点击【播放该视频】，进入视频播放页面；

9、 以上实现了视频的简单上传和播放。
