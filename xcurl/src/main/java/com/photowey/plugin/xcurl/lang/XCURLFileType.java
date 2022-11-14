package com.photowey.plugin.xcurl.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.photowey.plugin.xcurl.icon.XCurlIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * {@code XCURLFileType}
 *
 * @author photowey
 * @date 2022/11/14
 * @since 1.0.0
 */
public class XCURLFileType extends LanguageFileType {

    public static final XCURLFileType INSTANCE = new XCURLFileType();

    public XCURLFileType() {
        super(XCURLanguage.getInstance());
    }

    public static XCURLFileType getInstance() {
        return INSTANCE;
    }

    @Override
    public @NotNull
    String getName() {
        return "XCURL";
    }

    @Override
    public @NotNull
    String getDescription() {
        return "XCURL language file";
    }

    @Override
    public @NotNull
    String getDefaultExtension() {
        return "xcurl";
    }

    @Override
    public @Nullable
    Icon getIcon() {
        return XCurlIcons.ICON;
    }
}