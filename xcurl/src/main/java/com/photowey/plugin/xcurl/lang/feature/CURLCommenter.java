package com.photowey.plugin.xcurl.lang.feature;

import com.intellij.lang.Commenter;
import org.jetbrains.annotations.Nullable;

/**
 * {@code CURLCommenter}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class CURLCommenter implements Commenter {

    @Override
    public @Nullable
    String getLineCommentPrefix() {
        return "//";
    }

    @Override
    public @Nullable
    String getBlockCommentPrefix() {
        return "/**";
    }

    @Override
    public @Nullable
    String getBlockCommentSuffix() {
        return "*/";
    }

    @Override
    public @Nullable
    String getCommentedBlockCommentPrefix() {
        return "/**";
    }

    @Override
    public @Nullable
    String getCommentedBlockCommentSuffix() {
        return "*/";
    }
}