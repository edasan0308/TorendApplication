/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.ait.oop2.k17017;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import jp.ne.docomo.smt.dev.common.exception.SdkException;
import jp.ne.docomo.smt.dev.common.exception.ServerException;
import jp.ne.docomo.smt.dev.common.http.AuthApiKey;
import jp.ne.docomo.smt.dev.webcuration.CurationContents;
import jp.ne.docomo.smt.dev.webcuration.constants.Lang;
import jp.ne.docomo.smt.dev.webcuration.data.CurationArticleContentsData;
import jp.ne.docomo.smt.dev.webcuration.data.CurationContentData;
import jp.ne.docomo.smt.dev.webcuration.data.CurationContentsResultData;
import jp.ne.docomo.smt.dev.webcuration.param.CurationContentsRequestParam;

/**
 *
 * @author edasan0308
 */
public class TorendDataMining {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, InterruptedException {

        //ページにできるだけのニュースデータがあるのは以下のジャンル
        int[] genreId = {2, 3, 5, 13, 14};
        int size = 20;

        try {
            // APIKEY の設定
            AuthApiKey.initializeAuth("52772f6773364434306e69665536585448396e7773594e596e6443474935525531507a505055613378382e");

            // プロキシの設定
            // プロキシを使用しない場合はコメントとする
            //ProxyInfo.initializeProxy("proxyhost.co.jp", 80);
            // 記事取得リクエスト用データクラスを生成して、パラメータを設定する
            for (int genre : genreId) {

                CurationContentsRequestParam requestParam = new CurationContentsRequestParam();
                requestParam.setGenreId(genre);
                requestParam.setLang(Lang.JP);
                requestParam.setNumber(size);

                // 記事取得クラスの生成して、リクエストを実行する
                CurationContents contents = new CurationContents();
                CurationContentsResultData resultData = contents.request(requestParam);

                ArrayList<CurationArticleContentsData> articleList = resultData.getCurationArticleContentsDataList();
                if (articleList == null) {
                    System.out.println("記事データ : なし");
                    return;
                }

                for (CurationArticleContentsData articleData : articleList) {
                    CurationContentData contentData = articleData.getCurationContentData();
                    News news;

                    //image系のデータはNULLの場合があるのでその場合は空文字でコンンストラクタへ
                    if (contentData.getImageUrl() != null) {
                        news = new News(LocalDate.parse(resultData.getIssueDate().split("T")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")), articleData.getContentId(), articleData.getGenreId(), contentData.getTitle(), contentData.getBody(), contentData.getLinkUrl(), contentData.getImageUrl(), String.valueOf(contentData.getCurationImageSizeData().getHeight()), String.valueOf(contentData.getCurationImageSizeData().getWidth()), LocalDate.parse(contentData.getCreatedDate().split("T")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")), contentData.getSourceDomain(), contentData.getSourceName(), 1);
                    } else {
                        news = new News(LocalDate.parse(resultData.getIssueDate().split("T")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")), articleData.getContentId(), articleData.getGenreId(), contentData.getTitle(), contentData.getBody(), contentData.getLinkUrl(), "", "", "", LocalDate.parse(contentData.getCreatedDate().split("T")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")), contentData.getSourceDomain(), contentData.getSourceName(), 1);
                    }

                    Database.insertNewsData(news);
                    Thread.sleep(100);//サーバー負荷軽減のため次の記事取得まで0.1秒待つ
                }

            }

            System.out.println("END"+LocalDate.now());

        } catch (SdkException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

}
