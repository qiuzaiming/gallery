# gallery

1.去除Navigation的依赖和代码  -- 完成
2.加入Hilt作为repository的实例化  -- 完成
3.主界面的adapter进行优化
4.加入过渡动画
5.使用windowInsets对导航栏和状态栏进行优化 -- 完成
6.加入crash后台管理代码
        {
            设计思路: 1.通过设置Timber.Tree的方式获取到打印的日志,也可以在这边进行日志等级的控制
                     2.获取到日志,直接写入到一个cache的文件中（需要对这个文件夹进行合理的管理）
                     3.在发生crash的时候,在AppCenter的getErrorAttachments回调中读取文件获取到日志,然后进行上传即可。
      }