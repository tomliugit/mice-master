package util.email;

import constant.GlobalConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Description
 * 封装Email相关的操作
 * @author sunwell
 */
public class EmailUtil {
    private static Logger log = LoggerFactory.getLogger(EmailUtil.class);
    private MimeMessage message;
    private Session session;

    private Transport transport;
    private String mailHost;
    private int port;
    private String sender_username;
    private String sender_password;


    private static class LazyHolder {
        private static final EmailUtil instance = new EmailUtil();
    }

    public static EmailUtil getInstance() {
        return LazyHolder.instance;
    }

    private EmailUtil() {
        Properties properties = new Properties();
        this.mailHost = GlobalConstants.Mail.HOST;
        this.port = GlobalConstants.Mail.PORT;
        this.sender_username = GlobalConstants.Mail.USERNAME;
        this.sender_password = GlobalConstants.Mail.PASSWORD;
        session = Session.getInstance(properties);
        session.setDebug(false);//开启后有调试信息
        message = new MimeMessage(session);
    }

    /**
     * 发送邮件
     *
     * @param subject     邮件主题
     * @param sendHtml    邮件内容
     * @param receiveUser 收件人地址
     */
    public void sendEmail(String subject, String sendHtml, String receiveUser) {
        try {
            //发件人
            //InternetAddress from = new InternetAddress(sender_username);
            //下面这个是设置发送人的Nick name
            InternetAddress from = new InternetAddress(MimeUtility.encodeWord(GlobalConstants.Mail.SENDER_NAME) + " <" + sender_username + ">");
            message.setFrom(from);
            //收件人
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);//还可以有CC、BCC
            //邮件主题
            message.setSubject(subject);
            //邮件内容,也可以使纯文本"text/plain"
            message.setContent(sendHtml, "text/html;charset=UTF-8");
            //保存邮件
            message.saveChanges();
            message.setSentDate(new Date());
            transport = session.getTransport("smtp");
            //smtp验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(mailHost, port, sender_username, sender_password);
            //发送
            transport.sendMessage(message, message.getAllRecipients());
            log.info("邮件发送成功：subject=" + subject + ",email=" + receiveUser);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 根据入参，判断是否是企业邮箱后缀
     * @param emailStr
     * @return
     */
    public static boolean isOfficeMail(String emailStr){

        boolean flag = false;

        String[] suffixArray = {"163.com","sina.com","sina.cn","outlook.com","tom.com","189.cn",
                "netease.com","128.com","2980.com","qip.ru","qq.com","21cn.com","wo.cn","aliyun.com",
                "tianya.cn","hainan.net","sunmail.cn","mail.com","sohu.com","139.com","126.com",
                "zoho.com.cn","xinhuanet.com","yahoo.com","aol.com","yeah.net","cntv.cn","ymail.cn",
                "eyou.com"};

        String[] strArray = emailStr.split("@");

        String[] strsLowercase = getLowerCaseArray(strArray);

        for(String suffix : suffixArray){
            if(strsLowercase[1].contains(suffix)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static String[] getLowerCaseArray(String[] strs){
        List<String> list = new ArrayList<String>();
        for (String str : strs){
            list.add(str.toLowerCase());
        }
        String[] strings=new String[list.size()];
        for(int i=0,j=list.size();i<j;i++){
            strings[i]=list.get(i);
        }
        return strings;
    }

    public static void main(String[] args) {
        getInstance().sendEmail("test","<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/><meta name=\"viewport\" content=\"width=device-width,minimum-scale=1.0,maximum-scale=1.0\"><meta name=\"renderer\" content=\"webkit\"><title>梅花网每日资讯</title></head> <style type=\"text/css\">*{font-family: '微软雅黑',Arial,Helvetica,sans-serif;}.table-width{width: 100%!important;max-width: 720px!important;}@media (max-width: 480px) {.top-placeholder{display: none!important;}.logo{padding-left: 10px!important;}.table-width{width: 100%!important;margin: 0 auto!important;}.table-width-pad{padding: 8px 10px!important;}.padding-1{padding: 0px 10px!important;}.td-line-3{font-size: 10pt!important;font-size: 14px !important;line-height: 22px !important;overflow: hidden !important;text-overflow: ellipsis !important;display: -moz-box !important;display: -webkit-box !important;-webkit-box-orient: vertical !important;-webkit-line-clamp: 1 !important;}.td-article-w{height: inherit!important;width: calc(100% - 10px)!important;}.bottom-link{display: -webkit-box!important;display: -moz-box !important;text-overflow: ellipsis!important;-webkit-box-orient: vertical !important;-webkit-line-clamp: 3;overflow: hidden !important;font-size: 16px!important;}.none-phone{width: 100% !important;height: 22px!important;position:relative!important;overflow:hidden!important;display: none !important;}.none-phone:after {content:\"...\";position:absolute;bottom:0;right:0;background:#FFF;padding-left:0.2em;}.article-img{width: 110px!important;height: 73px!important;}}</style> <body style=\"font-size:12px; padding:0; margin:0; font-family: '微软雅黑',Arial,Helvetica,sans-serif;\"><table class=\"table-width\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"table-width\" style=\"width: 100%;max-width: 720px; border:0px;background-color:White;margin:0 auto;padding:10px 0px 0px 0px;\"><tr><td class=\"table-width\" style=\"width: 100%;max-width: 720px; height:40px;\"><table class=\"table-width\" style=\"width: 100%;max-width: 720px; height:40px;\"><tr><td class=\"logo\" style=\"width:130px;padding-left:20px;vertical-align:top;\"><a href=\"http://www.meihua.info\" target=\"_blank\"><img src=\"http://www.meihua.info/icons/logo2.png\" border=\"0\" width=\"120\" height=\"40\"/></a></td><td class=\"top-placeholder\" style=\"width:220px;\"></td><td style=\"width:70px;text-align:center;\"><a href=\"http://www.meihua.info/\" target=\"_blank\" style=\"text-decoration:none;color:#333333;font-size:12pt;\">资讯</a></td><td style=\"width:70px;text-align:center;\"><a href=\"http://event.meihua.info/\" target=\"_blank\" style=\"text-decoration:none;color:#333333;font-size:12pt;\">活动 </a></td><td style=\"width:70px;text-align:center;\"><a href=\"http://resource.meihua.info/\" target=\"_blank\" style=\"text-decoration:none;color:#333333;font-size:12pt;\">资源 </a></td><td style=\"width:70px;text-align:center;\"><a href=\"http://www.meihua.info/p\" target=\"_blank\" style=\"text-decoration:none;color:#333333;font-size:12pt;\"> 产品 </a></td><td style=\"width:70px;text-align:center;\"><a href=\"http://vip.meihua.info/\" target=\"_blank\" style=\"text-decoration:none;color:#333333;font-size:12pt;\">人脉 </a></td></tr></table></td></tr><tr><td class=\"table-width padding-1\" style=\"width: 100%;max-width: 720px; height:100px;padding:30px 0px 30px 0px;border-bottom-color: #ccc;border-bottom-width: 1px;border-bottom-style: dotted;\"><table class=\"table-width\" style=\"width: 100%;max-width: 720px; \"><tr><td class=\"article-img\" rowspan=\"2\" style=\"width:150px;\"><a href=\"http://www.meihua.info/a/71177?source=todayemail\" target=\"_blank\"><img class=\"article-img \" src=\"http://image.meihua.info:808/upload/2018/03/02/151996943650834.jpg\" border=\"0\" width=\"150\" height=\"100\"/></a></td><td class=\"td-article-w\" style=\"width:570px;height:20px;padding-left:10px;\"><a class=\"bottom-link\" href=\"http://www.meihua.info/a/71177?source=todayemail\" target=\"_blank\" style=\"text-decoration:none;color:black;font-size:13pt;\"> 《红海行动》PK《唐人街探案2》，谁的营销更胜一筹？ </a></td></tr><tr><td class=\"td-line-3 td-article-w none-phone\" style=\"width:570px;height:80px;padding-left:10px;line-height:25px;font-size:11pt;color:#333333;\"> 春节档多部影片上映，你和家人去看了哪一部？据悉，今年的春节档刷新了多项票房纪录，《红海行动》《唐人街探案2》成了新年最火影片。从这两部爆火影片来看，其实电影市场早已发生了改变… </td></tr></table></td></tr><tr><td class=\"table-width padding-1\" style=\"width: 100%;max-width: 720px; height:100px;padding:30px 0px 30px 0px;border-bottom-color: #ccc;border-bottom-width: 1px;border-bottom-style: dotted;\"><table class=\"table-width\" style=\"width: 100%;max-width: 720px; \"><tr><td class=\"article-img\" rowspan=\"2\" style=\"width:150px;\"><a href=\"http://www.meihua.info/a/71174?source=todayemail\" target=\"_blank\"><img class=\"article-img \" src=\"http://image.meihua.info:808/upload/2018/03/02/151995718752231.jpg\" border=\"0\" width=\"150\" height=\"100\"/></a></td><td class=\"td-article-w\" style=\"width:570px;height:20px;padding-left:10px;\"><a class=\"bottom-link\" href=\"http://www.meihua.info/a/71174?source=todayemail\" target=\"_blank\" style=\"text-decoration:none;color:black;font-size:13pt;\"> 围绕用户需要，在B站打广告的“三步走”策略 </a></td></tr><tr><td class=\"td-line-3 td-article-w none-phone\" style=\"width:570px;height:80px;padding-left:10px;line-height:25px;font-size:11pt;color:#333333;\"> 为什么我在B站投放的广告总是石沉大海？到底该如何赢得B站这群年轻人的关注？不了解用户，你的广告只是自嗨。 </td></tr></table></td></tr><tr><td class=\"table-width padding-1\" style=\"width: 100%;max-width: 720px; height:100px;padding:30px 0px 30px 0px;border-bottom-color: #ccc;border-bottom-width: 1px;border-bottom-style: dotted;\"><table class=\"table-width\" style=\"width: 100%;max-width: 720px; \"><tr><td class=\"article-img\" rowspan=\"2\" style=\"width:150px;\"><a href=\"http://www.meihua.info/a/71175?source=todayemail\" target=\"_blank\"><img class=\"article-img \" src=\"http://image.meihua.info:808/upload/2018/03/02/151996829303632.jpg\" border=\"0\" width=\"150\" height=\"100\"/></a></td><td class=\"td-article-w\" style=\"width:570px;height:20px;padding-left:10px;\"><a class=\"bottom-link\" href=\"http://www.meihua.info/a/71175?source=todayemail\" target=\"_blank\" style=\"text-decoration:none;color:black;font-size:13pt;\"> 网易传媒抢签法国队德国队，世界杯战略资源全面升级 </a></td></tr><tr><td class=\"td-line-3 td-article-w none-phone\" style=\"width:570px;height:80px;padding-left:10px;line-height:25px;font-size:11pt;color:#333333;\"> 3月1日，2018俄罗斯世界杯网易战略发布会暨网易与德国足协独家签约仪式在京举行。继签约法国队之后，网易传媒又与目前世界排名第一的超级劲旅德国队达成合作，成为其世界杯的中国独家网络媒体合作伙伴。 </td></tr></table></td></tr><tr><td class=\"table-width padding-1\" style=\"width: 100%;max-width: 720px; height:100px;padding:30px 0px 30px 0px;border-bottom-color: #ccc;border-bottom-width: 1px;border-bottom-style: dotted;\"><table class=\"table-width\" style=\"width: 100%;max-width: 720px; \"><tr><td class=\"article-img\" rowspan=\"2\" style=\"width:150px;\"><a href=\"http://www.meihua.info/a/71172?source=todayemail\" target=\"_blank\"><img class=\"article-img \" src=\"http://image.meihua.info:808/upload/2018/03/02/151995498022129.jpg\" border=\"0\" width=\"150\" height=\"100\"/></a></td><td class=\"td-article-w\" style=\"width:570px;height:20px;padding-left:10px;\"><a class=\"bottom-link\" href=\"http://www.meihua.info/a/71172?source=todayemail\" target=\"_blank\" style=\"text-decoration:none;color:black;font-size:13pt;\"> 在kol运营过程中，我曾踩过的8个坑儿 </a></td></tr><tr><td class=\"td-line-3 td-article-w none-phone\" style=\"width:570px;height:80px;padding-left:10px;line-height:25px;font-size:11pt;color:#333333;\"> 现在运营这个行业分工越来越细，要求越来越高，如果你想成为一个优秀的运营人员，想必kol这个环节你是逃不掉的。 </td></tr></table></td></tr><tr><td><table><tr><td style=\"width:360px;height:20px;text-align:center;padding:0px 0px 0px 0px;font-size:9pt;\"><table><tr><td style=\"padding: 30px;text-align: center;\"><a href=\"http://www.m1world.com?ref=mhdaily\"><img src=\"http://www.meihua.info/icons/logo_m1.png\" border=\"0\"/></a></td></tr><tr><td style=\"font-size: 14px;\">M1云端市场部，营销人的云端工具集平台</td></tr><tr><td style=\"padding: 30px 30px 35px 0px;text-align: center;\"><a href=\"http://www.m1world.com?ref=mhdaily\" style=\"color: #0066cc;font-size: 14px;\">进一步了解>></a></td></tr></table></td><td style=\"width:360px;height:20px;text-align:center;padding:0px 0px 0px 0px;font-size:9pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"><table><tr><td style=\"padding: 30px;text-align: center;\"><a href=\"http://www.mingdao.com?s=mhdaily\"><img src=\"http://www.meihua.info/icons/logo_md.png\" border=\"0\"/></a></td></tr><tr><td style=\"font-size: 14px;\">梅花网兄弟产品“明道”，开放沟通&nbsp;&nbsp;扁平协作</td></tr><tr><td style=\"padding: 30px 30px 35px 0px;text-align: center;\"><a href=\"http://www.mingdao.com?s=mhdaily\" style=\"color: #0066cc;font-size: 14px;\">进一步了解>></a></td></tr></table></td></tr></table></td></tr><tr><td class=\"table-width\" style=\" width: 100%;max-width: 720px; height:20px;text-align:center;padding:0px 0px 10px 0px;font-size:9pt;\"> 如果你不想再收到梅花网的推荐邮件，请点击这里 <a href=\"#TUIDINGLINK\" target=\"_blank\" style=\"color:#cccccc;text-decoration:none;\">退订</a></td></tr><tr><td class=\"table-width\" style=\" width: 100%;max-width: 720px; height:23px;text-align:center;padding:0px 0px 10px 0px;font-size:9pt;\"><img src=\"http://www.meihua.info/icons/logo_text_1.png\" border=\"0\"/></td></tr><tr><td class=\"table-width\" style=\" width: 100%;max-width: 720px; height:20px;text-align:center;padding:0px 0px 30px 0px;font-size:9pt;\"> Copyright 2018 Meihua Information </td></tr></table><a title=\"shopify visitor statistics\" href=\"http://statcounter.com/shopify/\" target=\"_blank\"><img src=\"http://c.statcounter.com/5635157/0/9b474211/1/\" alt=\"shopify visitor statistics\" style=\"border:none;\"></a><img src=\"http://meihua.adsame.com/s?z=meihua&c=5&op=1\" border=\"0\" width=\"1\" height=\"1\"/><img src=\"#TONGJILINK\" border=\"0\" width=\"1\" height=\"1\"/></body></html>","tao.jie@live.com");
//        getInstance().sendEmail("test","<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/><title>梅花网每日资讯</title></head><body style=\"font-family:'微软雅黑',Arial,Helvetica,sans-serif;font-size:12px; padding:0; margin:0; \"><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:720px;border:0px;background-color:White;margin:0 auto;padding:10px 0px 0px 0px;\"><tr><td style=\"width:720px;height:40px;\"><table style=\"width:720px;height:40px;\"><tr><td style=\"width:130px;padding-left:20px;vertical-align:top;\"><a href=\"http://www.meihua.info\" target=\"_blank\"><img src=\"http://www.meihua.info/icons/logo2.png\" border=\"0\" width=\"120\" height=\"40\"/></a></td><td style=\"width:220px;\"></td><td style=\"width:70px;text-align:center;\"><a href=\"http://www.meihua.info/\" target=\"_blank\" style=\"text-decoration:none;color:#333333;font-size:12pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif\">资讯</a></td><td style=\"width:70px;text-align:center;\"><a href=\"http://event.meihua.info/\" target=\"_blank\" style=\"text-decoration:none;color:#333333;font-size:12pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif\">活动 </a></td><td style=\"width:70px;text-align:center;\"><a href=\"http://resource.meihua.info/\" target=\"_blank\" style=\"text-decoration:none;color:#333333;font-size:12pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif\">资源 </a></td><td style=\"width:70px;text-align:center;\"><a href=\"http://www.meihua.info/p\" target=\"_blank\" style=\"text-decoration:none;color:#333333;font-size:12pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif\"> 产品 </a></td><td style=\"width:70px;text-align:center;\"><a href=\"http://vip.meihua.info/\" target=\"_blank\" style=\"text-decoration:none;color:#333333;font-size:12pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif\">人脉 </a></td></tr></table></td></tr><tr><td style=\"width:720px;height:100px;padding:30px 0px 30px 0px;border-bottom-color: #ccc;border-bottom-width: 1px;border-bottom-style: dotted;\"><table style=\"width:720px;\"><tr><td rowspan=\"2\" style=\"width:150px;\"><a href=\"http://www.meihua.info/a/67831?source=todayemail\" target=\"_blank\"><img src=\"http://image.meihua.info:808/upload/2016/10/10/147608461182227.jpg\" border=\"0\" width=\"150\" height=\"100\"/></a></td><td style=\"width:570px;height:20px;padding-left:10px;\"><a href=\"http://www.meihua.info/a/67831?source=todayemail\" target=\"_blank\" style=\"text-decoration:none;color:black;font-size:13pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"> 带符号的文案是什么样的？ </a></td></tr><tr><td style=\"width:570px;height:80px;padding-left:10px;line-height:25px;font-size:11pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;color:#333333;\"> 人的心理活动具有选择性，特定的符号会把人的心理活动有选择地指向某一目标。所以当文字为我们提供一个有印象有记忆有指向的目标时，人的大脑会产生一种可理解易联想的场景画面。 </td></tr></table></td></tr><tr><td style=\"width:720px;height:100px;padding:30px 0px 30px 0px;border-bottom-color: #ccc;border-bottom-width: 1px;border-bottom-style: dotted;\"><table style=\"width:720px;\"><tr><td rowspan=\"2\" style=\"width:150px;\"><a href=\"http://www.meihua.info/a/67830?source=todayemail\" target=\"_blank\"><img src=\"http://image.meihua.info:808/upload/2016/10/10/147608099230722.jpg\" border=\"0\" width=\"150\" height=\"100\"/></a></td><td style=\"width:570px;height:20px;padding-left:10px;\"><a href=\"http://www.meihua.info/a/67830?source=todayemail\" target=\"_blank\" style=\"text-decoration:none;color:black;font-size:13pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"> 小马宋：所谓做营销，平淡又无奇 </a></td></tr><tr><td style=\"width:570px;height:80px;padding-left:10px;line-height:25px;font-size:11pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;color:#333333;\"> 怎么才能成为一个营销高手？尽管我自己也算不上高手，可是我观察很多高手的行为，还是有所收获。关于营销这件事，我的结论是，你只要做到三个很简单但大多数人却无法做到的事情，就能成为营销高手。 </td></tr></table></td></tr><tr><td style=\"width:720px;height:100px;padding:30px 0px 30px 0px;border-bottom-color: #ccc;border-bottom-width: 1px;border-bottom-style: dotted;\"><table style=\"width:720px;\"><tr><td rowspan=\"2\" style=\"width:150px;\"><a href=\"http://www.meihua.info/a/67827?source=todayemail\" target=\"_blank\"><img src=\"http://image.meihua.info:808/upload/2016/10/10/147607762813513.jpg\" border=\"0\" width=\"150\" height=\"100\"/></a></td><td style=\"width:570px;height:20px;padding-left:10px;\"><a href=\"http://www.meihua.info/a/67827?source=todayemail\" target=\"_blank\" style=\"text-decoration:none;color:black;font-size:13pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"> 为什么文案一定要场景化？ </a></td></tr><tr><td style=\"width:570px;height:80px;padding-left:10px;line-height:25px;font-size:11pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;color:#333333;\"> 什么是场景化文案？就是设计一个产品使用场景，让顾客通过场景进一步认识产品。当顾客遇到同样的场景，脑海里能想到你家的产品。 </td></tr></table></td></tr><tr><td style=\"width:720px;height:100px;padding:30px 0px 30px 0px;border-bottom-color: #ccc;border-bottom-width: 1px;border-bottom-style: dotted;\"><table style=\"width:720px;\"><tr><td rowspan=\"2\" style=\"width:150px;\"><a href=\"http://www.meihua.info/a/67828?source=todayemail\" target=\"_blank\"><img src=\"http://image.meihua.info:808/upload/2016/10/10/147607833822214.jpg\" border=\"0\" width=\"150\" height=\"100\"/></a></td><td style=\"width:570px;height:20px;padding-left:10px;\"><a href=\"http://www.meihua.info/a/67828?source=todayemail\" target=\"_blank\" style=\"text-decoration:none;color:black;font-size:13pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"> 一周热点观察：房地产 “恐慌式营销”；26家药商被撤GSP </a></td></tr><tr><td style=\"width:570px;height:80px;padding-left:10px;line-height:25px;font-size:11pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;color:#333333;\"> 一、二、三线的20个城市先后发布力度不一的楼市调控政策，多地重启限购限贷；房地产开发商“恐慌式营销”乱象调查，你是否也遇到过？梅花网MICE小编带你盘点上周各行业的热点资讯… … </td></tr></table></td></tr><tr><td style=\"width:720px;padding:8px 10px 8px 0px;font-size:12pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;border-bottom-color: #ccc;border-bottom-width: 1px;border-bottom-style: dotted;\"><table style=\"width:700px;\"><tr><td style=\"width:700px;height:20px;\"><a href=\"http://www.meihua.info/a/67826?source=todayemail\" target=\"_blank\" style=\"text-decoration:none;color:black;font-size:13pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"> 长假结束该好好学习了，大众汽车80年经典广告合集 </a></td></tr></table></td></tr><tr><td style=\"width:720px;padding:8px 10px 8px 0px;font-size:12pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;border-bottom-color: #ccc;border-bottom-width: 1px;border-bottom-style: dotted;\"><table style=\"width:700px;\"><tr><td style=\"width:700px;height:20px;\"><a href=\"http://www.meihua.info/a/67823?source=todayemail\" target=\"_blank\" style=\"text-decoration:none;color:black;font-size:13pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"> 说好一本正经的博物杂志，是如何“软萌贱”的走红新媒体营销 </a></td></tr></table></td></tr><tr><td><table><tr><td style=\"width:360px;height:20px;text-align:center;padding:0px 0px 0px 0px;font-size:9pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"><table><tr><td style=\"padding: 30px;text-align: center;\"><a href=\"http://www.m1world.com\"><img src=\"http://www.meihua.info/icons/logo_m1.png\" border=\"0\"/></a></td></tr><tr><td style=\"font-size: 14px;\">M1云端市场部，面向营销人的云端工具集成平台</td></tr><tr><td style=\"padding: 30px 35px;text-align: center;\"><a href=\"http://www.m1world.com\" style=\"color: #0066cc;font-size: 14px;\">进一步了解>></a></td></tr></table></td><td style=\"width:360px;height:20px;text-align:center;padding:0px 0px 0px 0px;font-size:9pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"><table><tr><td style=\"padding: 30px;text-align: center;\"><a href=\"http://www.mingdao.com\"><img src=\"http://www.meihua.info/icons/logo_md.png\" border=\"0\"/></a></td></tr><tr><td style=\"font-size: 14px;\">梅花网兄弟产品“明道”，超自由的团队协作平台</td></tr><tr><td style=\"padding: 30px 35px;text-align: center;\"><a href=\"http://www.mingdao.com\" style=\"color: #0066cc;font-size: 14px;\">进一步了解>></a></td></tr></table></td></tr></table></td></tr><tr><td style=\"width:720px;height:20px;text-align:center;padding:0px 0px 10px 0px;font-size:9pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"> 如果你不想再收到梅花网的推荐邮件，请点击这里 <a href=\"#TUIDINGLINK\" target=\"_blank\" style=\"color:#cccccc;text-decoration:none;\">退订</a></td></tr><tr><td style=\"width:720px;height:23px;text-align:center;padding:0px 0px 10px 0px;font-size:9pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"><img src=\"http://www.meihua.info/icons/logo_text_1.png\" border=\"0\"/></td></tr><tr><td style=\"width:720px;height:20px;text-align:center;padding:0px 0px 30px 0px;font-size:9pt;font-family:'微软雅黑',Arial,Helvetica,sans-serif;\"> Copyright 2016 Meihua Information </td></tr></table><a title=\"shopify visitor statistics\" href=\"http://statcounter.com/shopify/\" target=\"_blank\"><img src=\"http://c.statcounter.com/5635157/0/9b474211/1/\" alt=\"shopify visitor statistics\" style=\"border:none;\"></a><img src=\"http://meihua.adsame.com/s?z=meihua&c=5&op=1\" border=\"0\" width=\"1\" height=\"1\"/><img src=\"#TONGJILINK\" border=\"0\" width=\"1\" height=\"1\"/></body></html>","jason.tao@meihua.info");
//        System.out.println(isOfficeMail("andy.fu@meihua.info"));
//        if (!EmailUtil.isOfficeMail("andy.fu@meihua.info")) {
//            int type = 1;
//            String detail = "绑定企业邮箱赠送";
//            int pro = 2;
//            String code = "1yrhen";
//            String scope = MhUtil.simpleMbScope(String.valueOf(324288), String.valueOf(pro), String.valueOf(type), detail);
//            //生成对应的token
//            String m1_token = TokenUtil.genToken(String.valueOf(324288), "392bd7e26953134942c03f73d1eb0f49");
//            MhUtil.mbtaskMark(type, detail, pro, code, 0, scope, m1_token);
//        }

//        System.out.println(isOfficeMail("lihuan@qq.com"));
//        System.out.println(isOfficeMail("lihuan@163.COM"));
//        System.out.println(isOfficeMail("lihuan@abc.com"));
    }


    /**
     * 发送推荐邮件
     *
     * @param receiveUser 收件人地址(多的用逗号分开)
     */
  /*  public  void send_recommendEmail(String receiveUser) {

        //邮件主题
        String subject = "推荐邮件";
        //邮件内容
        String sendHtml = "<html><body><h1>推荐邮件<h1><body><html>";
        try {
            //发件人
            //InternetAddress from = new InternetAddress(sender_username);
            //下面这个是设置发送人的Nick name
            InternetAddress from = new InternetAddress(MimeUtility.encodeWord(R.Mail.SENDER_NAME) + " <" + sender_username + ">");
            message.setFrom(from);
            //收件人用逗号隔开字符串处理
            String[] receiversArray = receiveUser.split(",");
            //收件人
            //循环收件人数组发送邮件
            for (String receive : receiversArray) {
                InternetAddress to = new InternetAddress(receive);
                message.setRecipient(Message.RecipientType.TO, to);//还可以有CC、BCC
                //邮件主题
                message.setSubject(subject);
                //邮件内容,也可以使纯文本"text/plain"
                message.setContent(sendHtml, "text/html;charset=UTF-8");
                //保存邮件
                message.saveChanges();
                message.setSentDate(new Date());
                transport = session.getTransport("smtp");
                //smtp验证，就是你用来发邮件的邮箱用户名密码
                transport.connect(mailHost, port, sender_username, sender_password);
                //发送
                transport.sendMessage(message, message.getAllRecipients());
                log.info("邮件发送成功：subject=" + subject + ",email=" + receiveUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}