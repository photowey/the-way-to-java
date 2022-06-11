/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class Cluster$Adaptive implements Cluster {

    public com.alibaba.dubbo.rpc.Invoker join(Directory arg0) throws com.alibaba.dubbo.rpc.RpcException {
        if (arg0 == null)
            throw new IllegalArgumentException("com.alibaba.dubbo.rpc.cluster.Directory argument == null");
        if (arg0.getUrl() == null)
            throw new IllegalArgumentException("com.alibaba.dubbo.rpc.cluster.Directory argument getUrl() == null");
        com.alibaba.dubbo.common.URL url = arg0.getUrl();
        String extName = url.getParameter("cluster", "failover");
        if (extName == null)
            throw new IllegalStateException("Fail to get extension(com.alibaba.dubbo.rpc.cluster.Cluster) name from url(" + url.toString() + ") use keys([cluster])");
        Cluster extension = ExtensionLoader.getExtensionLoader(Cluster.class).getExtension(extName);
        return extension.join(arg0);
    }
}