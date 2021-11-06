package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class RouterFactory$Adaptive implements RouterFactory {

    public Router getRouter(com.alibaba.dubbo.common.URL arg0) {
        if (arg0 == null) throw new IllegalArgumentException("url == null");
        com.alibaba.dubbo.common.URL url = arg0;
        String extName = url.getProtocol();
        if (extName == null)
            throw new IllegalStateException("Fail to get extension(com.alibaba.dubbo.rpc.cluster.RouterFactory) name from url(" + url.toString() + ") use keys([protocol])");
        RouterFactory extension = (RouterFactory) ExtensionLoader.getExtensionLoader(RouterFactory.class).getExtension(extName);
        return extension.getRouter(arg0);
    }
}