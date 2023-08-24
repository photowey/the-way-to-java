/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.plugin.xcurl.lang.feature;

import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@code XCURLCommandDocumentProvider}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class XCURLCommandDocumentProvider implements DocumentationProvider {

    @Override
    public @Nullable
    String generateHoverDoc(@NotNull PsiElement element, @Nullable PsiElement originalElement) {
        switch (element.getText().toLowerCase()) {
            case "curl":
                return "curl is a tool to transfer data from or to a server, using one of the supported protocols \n" +
                        "(DICT, FILE, FTP, FTPS, GOPHER, HTTP, HTTPS, IMAP, IMAPS, LDAP, LDAPS, POP3, POP3S, RTMP, RTSP, SCP, SFTP, SMTP, SMTPS, TELNET and TFTP).\n" +
                        "The command is designed to work without user interaction.";
            case "-a":
            case "--append":
                return this.getAppendDoc();
            default:
                return this.syntax();
        }
    }

    @Override
    public @Nullable
    PsiElement getCustomDocumentationElement(@NotNull Editor editor, @NotNull PsiFile file, @Nullable PsiElement contextElement, int targetOffset) {
        return contextElement;
    }

    /**
     * $ man curl
     *
     * @return
     */
    private String syntax() {
        return "The URL syntax is protocol-dependent. You'll find a detailed description in RFC 3986.\n" +
                "\n" +
                "You can specify multiple URLs or parts of URLs by writing part sets within braces as in:\n" +
                "\n" +
                " http://site.{one,two,three}.com\n" +
                "\n" +
                "or you can get sequences of alphanumeric series by using [] as in:\n" +
                "\n" +
                " ftp://ftp.numericals.com/file[1-100].txt\n" +
                " ftp://ftp.numericals.com/file[001-100].txt    (with leading zeros)\n" +
                " ftp://ftp.letters.com/file[a-z].txt\n" +
                "\n" +
                "Nested sequences are not supported, but you can use several ones next to each other:\n" +
                "\n" +
                " http://any.org/archive[1996-1999]/vol[1-4]/part{a,b,c}.html\n" +
                "\n" +
                "You can specify any amount of URLs on the command line. They will be fetched in a sequential manner in the specified order.\n" +
                "\n" +
                "You can specify a step counter for the ranges to get every Nth number or letter:\n" +
                "\n" +
                " http://www.numericals.com/file[1-100:10].txt\n" +
                " http://www.letters.com/file[a-z:2].txt\n" +
                "\n" +
                "If you specify URL without protocol:// prefix, curl will attempt to guess what protocol you might want. It will then default to HTTP but try other protocols based on often-used host name prefixes. For example, for host names starting with \"ftp.\" curl  will\n" +
                "assume you want to speak FTP.\n" +
                "\n" +
                "curl will do its best to use what you pass to it as a URL. It is not trying to validate it as a syntactically correct URL by any means but is instead very liberal with what it accepts.\n" +
                "\n" +
                "curl  will  attempt  to re-use connections for multiple file transfers, so that getting many files from the same server will not do multiple connects / handshakes. This improves speed. Of course this is only done on files specified on a single command line\n" +
                "and cannot be used between separate curl invokes.";
    }

    private String getAppendDoc() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <style>\n" +
                "    .article-main { display: inline; float: left; }\n" +
                "    .metadata { border-left: 3px solid #2d8d2d8d; color: #ffaa00ff; font-size: 1em; margin: 0 0 1.5em 0; padding: 5px 15px; }\n" +
                "    pre {\n" +
                "      white-space: pre-wrap;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1><a href=\"https://curl.se/docs/manpage.html\">append</a></h1>\n<div class=\"article - main\">\n" +
                " <p>(FTP SFTP) When used in an upload, this makes curl append to the target file instead of overwriting it.</p>" +
                " <p>If the remote file does not exist, it will be created. Note that this flag is ignored by some SFTP servers (including OpenSSH).</p>\n" +
                " <br>" +
                " <p>Providing --append multiple times has no extra effect. Disable it again with --no-append.</p>" +
                " <br>" +
                " <h2><a href=\"#options\" class=\" anchor - link\"></a>Example:</h2> \n" +
                " <br>" +
                " <p>  curl --upload-file local --append ftp://example.com/\n</p> \n" +
                " <br>" +
                " <p>See also <a href=\"https://curl.se/docs/manpage.html#-r\">-r, --range</a>  and <a href=\"https://curl.se/docs/manpage.html#-C\">-C, --continue-at.</a></p>" +
                "</body>\n" +
                "</html>\n";
    }
}