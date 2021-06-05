package com.example.keepup_v1.http;

public class Constants {
    public static final String SHARE_PREFERENCE_NAME = "myNovel";
    public static final String BASE_URL = "http://172.20.10.3:3000";

    public static final String SEND_CODE_URL = BASE_URL + "/sendCode";//发送验证码
    public static final String REGISTER_URL = BASE_URL + "register?email=%s&pwd=%s&verifyCode=%s";//注册
    public static final String FORGET_PWD_URL = BASE_URL + "forgetPWD?email=%s&pwd=%s&verifyCode=%s";//忘记密码

    public static final String LOGIN_AUTO_URL = BASE_URL + "/sign-in/auto";
    public static final String LOGIN_URL = BASE_URL + "/sign-in/submit";//登录
    public static final String SIGN_UP_EMAIL_URL = BASE_URL + "/sign-up/email";
    public static final String SIGN_UP_CODE_URL = BASE_URL + "/sign-up/code";
    public static final String SIGN_UP_PWD_URL = BASE_URL + "/sign-up/pwd";
    public static final String FORGET_PWD_EMAIL_URL = BASE_URL + "/forgetpwd/email";
    public static final String FORGET_PWD_SEND_URL = BASE_URL + "/forgetpwd/login";

    public static final String MEDIA_CONTENT_URL = BASE_URL + "/media/showposts_";
    public static final String MEDIA_COMMENT_URL = BASE_URL + "/media/comment";
    public static final String MEDIA_LIKE_URL = BASE_URL + "/media/thumb_up";

    public static final String MESSAGE_GETINFO_URL = BASE_URL + "/media/one_blog?msg_id=";
    //public static final String MESSAGE_GETCOMMENT_URL = BASE_URL + "/media/message/comment?msg_id";
    public static final String MESSAGE_SENDCOMMENT_URL = BASE_URL + "/media/comment";

    public static final String ADD_MSG_URL = BASE_URL + "/media/post";

    public static final String FOLLOW_URL = BASE_URL + "/follow";
}
