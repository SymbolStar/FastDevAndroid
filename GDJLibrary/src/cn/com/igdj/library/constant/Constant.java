package cn.com.igdj.library.constant;

public class Constant {
	
	public static final String KEY_SMS_KEY = "1b79b81fb8d10";
	public static final String KEY_SMS_SECRET = "6ce9b379921f60a538d8abd9c62f2ddf";
	
	public static final String KEY_USER = "key_user";
	public static final String KEY_MAP = "key_map";
	
	public static final String ACTION_UPDATE_USER = "action_update_user";
	public static final String ACTION_LOGOUT = "action_logout";
	public static final String ACTION_LOGOUT_YXT = "action_logout_yxt";
	
	public static final String ACTION_LOGIN_FROM_GDJ = "yxt_login_from_gdj";//云校通从逛大街登录,打开逛大街登录页面
	
	public static final String ACTION_LOGIN_WITH_YXT_USER_ID = "login_with_yxt_user_id"; //云校通登录之后，通过发送该action让逛大街也登录
	
	public static final String ACTION_LOGIN_FROM_YXT = "gdj_login_from_yxt"; //逛大街使用云校通的登录页面登录之后，通过发送该action让逛大街也登录
			
	public static final String ACTION_REGISTER_WITH_YXT_USER = "register_with_yxt_user";
	
	public static final String ACTION_JPUSH_SET_TAGS = "jpush_set_tags";
	public static final String ACTION_JPUSH_SET_ALIAS = "jpush_set_alias";
	
	public static final String ACTION_JPUSH_SET_TAGS_DELAYED = "jpush_set_tags_delayed"; //60秒后重新设置
	public static final String ACTION_JPUSH_SET_ALIAS_DELAYED = "jpush_set_alias_delayed";//60秒后重新设置
}
