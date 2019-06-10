<%-- 
    Document   : index
    Created on : 2019/01/17, 14:15:37
    Author     : edasan0308
--%>

<%@page import="java.util.List"%>
<%@page import="jp.ac.ait.oop2.k17017.Database.News"%>
<%@page import="jp.ac.ait.oop2.k17017.Database.Database"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.data.CurationImageSizeData"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.data.CurationContentData"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.data.CurationArticleContentsData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.data.CurationContentsResultData"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.CurationContents"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.param.CurationContentsRequestParam"%>
<%@page import="jp.ne.docomo.smt.dev.common.exception.SdkException"%>
<%@page import="jp.ne.docomo.smt.dev.common.exception.ServerException"%>
<%@page import="jp.ne.docomo.smt.dev.common.http.AuthApiKey"%>
<%@page import="jp.ne.docomo.smt.dev.common.http.ProxyInfo"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.CurationGenre"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.constants.Lang"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.data.CurationGenreData"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.data.CurationGenreResultData"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.data.CurationSubGenreData"%>
<%@page import="jp.ne.docomo.smt.dev.webcuration.param.CurationGenreRequestParam"%>
<%@page contentType="text/html" pageEncoding="UTF-8"













        %>
<!DOCTYPE HTML>
<!--
        Phantom by HTML5 UP
        html5up.net | @ajlkn
        Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
    <head>
        <title>Torend.com</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link rel="stylesheet" href="assets/css/main.css" />
        <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
    </head>
    <body class="is-preload">
        <!-- Wrapper -->
        <div id="wrapper">

            <!-- Header -->
            <header id="header">
                <div class="inner">

                    <!-- Logo -->
                    <a href="index.jsp" class="logo">
                        <span class="symbol"><img src="images/icon.svg" alt="" /></span><span class="title">E#LABO</span>
                    </a>

                    <!-- Nav -->
                    <nav>
                        <ul>
                            <li><a href="#menu">Menu</a></li>
                        </ul>
                    </nav>

                </div>
            </header>

            <!-- Menu -->
            <nav id="menu">
                <h2>Menu</h2>
                <ul>
                    <li><a href="index.jsp?genreId=5">テクノロジー</a></li>
                    <li><a href="index.jsp?genreId=2">エンタメ</a></li>
                    <li><a href="index.jsp?genreId=3">スポーツ</a></li>
                    <li><a href="index.jsp?genreId=13">ゲーム</a></li>
                </ul>
            </nav>

            <!-- Main -->
            <div id="main">
                <div class="inner">
                    <header>
                        <h1>Trend.com</h1>
                        <p>docomo Developer supportで提供されているトレンド記事抽出APIを用いてリアルタイムにトレンド記事を確認できるサービスです。</p>
                        <%
                            int genreId = 5;
                            //レイアウト等でいい感じにできなかったため保留
                            //String[] genreTitle = {"Technology", "Entertainment", "Sport", "Game"};

                            //ジャンルIDが指定されていればその値を設定　初期値は5(テクノロジー)
                            if (request.getParameter("genreId") != null) {
                                genreId = Integer.parseInt(request.getParameter("genreId"));
                            }

                            /*
                            switch (genreId) {
                                case 5:
                                    out.println("<h2>"+ genreTitle[0] + "</h2>");
                                    break;
                                case 2:
                                    out.println("<h2>" + genreTitle[1] + "</h2>");
                                    break;
                                case 3:
                                    out.println("<h2>" + genreTitle[2] + "</h2>");
                                    break;
                                case 13:
                                    out.println("<h2>" + genreTitle[3] + "</h2>");
                                    break;
                            }
                             */
                        %>
                    </header>
                    <section class="tiles">

                        <%                            
                            int i = 0;
                            //ニュースデータは12個を基本とする。
                            List<News> newsdata = Database.getNewsByGenreId(genreId, 12);
                            for (News news : newsdata) {

                                //Tile背景色設定用
                                i = i + 1;
                                if (i == 7) {
                                    i = 1;
                                }
                                
                                //ループによって受け取った記事の量だけTileを生成
                                out.println("<article class=\"style" + i % 7 + "\">");
                                out.println("<span class=\"image\">");
                                out.println("<img src=\"images/pic" + i + ".jpg\" alt=\"\" />");
                                out.println("</span>");
                                out.println("<a href=\"" + news.getLinkUrl() + "\">");
                                out.println("<h2>" + news.getTitle() + "</h2>");
                                out.println("<div class=\"content\">");
                                out.println("<p>" + news.getBody() + "</p>");
                                out.println("</div>");
                                out.println("</a>");
                                out.println("</article>");
                            }

                        %>
                    </section>
                </div>
            </div>

            <!-- Footer -->
            <footer id="footer">
                <div class="inner">
                    <section>
                        <h2>Get in touch</h2>
                        <form method="post" action="#">
                            <div class="fields">
                                <div class="field half">
                                    <input type="text" name="name" id="name" placeholder="Name" />
                                </div>
                                <div class="field half">
                                    <input type="email" name="email" id="email" placeholder="Email" />
                                </div>
                                <div class="field">
                                    <textarea name="message" id="message" placeholder="Message"></textarea>
                                </div>
                            </div>
                            <ul class="actions">
                                <li><input type="submit" value="Send" class="primary" /></li>
                            </ul>
                        </form>
                    </section>
                    <section>
                        <h2>Follow</h2>
                        <ul class="icons">
                            <li><a href="https://twitter.com/Edasan0308" class="icon style2 fa-twitter"><span class="label">Twitter</span></a></li>
                            <li><a href="https://www.facebook.com/profile.php?id=100005851631793" class="icon style2 fa-facebook"><span class="label">Facebook</span></a></li>
                            <li><a href="https://www.instagram.com/edasan.0308/" class="icon style2 fa-instagram"><span class="label">Instagram</span></a></li>
                            <li><a href="#" class="icon style2 fa-dribbble"><span class="label">Dribbble</span></a></li>
                            <li><a href="#" class="icon style2 fa-github"><span class="label">GitHub</span></a></li>
                            <li><a href="#" class="icon style2 fa-500px"><span class="label">500px</span></a></li>
                            <li><a href="#" class="icon style2 fa-phone"><span class="label">Phone</span></a></li>
                            <li><a href="#" class="icon style2 fa-envelope-o"><span class="label">Email</span></a></li>
                        </ul>
                    </section>
                    <ul class="copyright">
                        <li>&copy; Untitled. All rights reserved</li><li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
                    </ul>
                </div>
            </footer>

        </div>

        <!-- Scripts -->
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/browser.min.js"></script>
        <script src="assets/js/breakpoints.min.js"></script>
        <script src="assets/js/util.js"></script>
        <script src="assets/js/main.js"></script>

    </body>
</html>