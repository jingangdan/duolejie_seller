package com.lss.duolejie_seller.utils;

import java.io.File;

import com.loopj.android.http.AsyncHttpClient;

public class HttpUtilsx {
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
//    public static final String BASE_URL = "http://192.168.1.245/pengkeyoutui/index.php?r=api/";
    public static final String BASE_URL = "http://39.107.14.118/";
    
    
	/**
	 * (2)会员登录
	 */
	public static String url_denglu(String json) {
		String url = null;
		url = BASE_URL+"http://39.107.14.118/appInterface/brandLogin.jhtml&json=" + json;
		return url;
	}

	/**
	 * (3)会员重置密码
	 */
	public static String url_chonghzimima(String json) {
		String url = null;
		url = BASE_URL+"site/resetPassword&json=" + json;
		return url;
	}

	/**
	 * (4)会员资料修改
	 */
	public static String url_ziliaoxiugai(String json) {
		String url = null;
		url = BASE_URL+"site/update&json=" + json;
		return url;
	}

	/**
	 * 	(5)会员头像保存
	 */
	public static String url_touxiangbaocun(String json) {
		String url = null;
		url = BASE_URL+"site/update&json=" + json;
		return url;
	}

	/**
	 * (6)会员头像修改(需改成post)
	 */
	public static String url_touxiangxiugai(String member_id,File f) {
		String url = null;
		url = BASE_URL+"site/UploadHeadPic&member_id=" + member_id;
		return url;
	}

	/**
	 * (7)会员密码修改
	 */
	public static String url_mimaxiugai(String json) {
		String url = null;
		url = BASE_URL+"/site/updatePassword&json=" + json;
		return url;
	}

	/**
	 * (8)获取会员详细信息
	 */
	public static String url_huoquhuiyuanxinxin(String json) {
		String url = null;
		url = BASE_URL+"site/GetMembers&json=" + json;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   4、产品分类接口
     *************************************************************************************************************************
     */

	/**
	 * （1）获取所有产品分类 
	 */
	public static String url_huoquchanpinfenlei() {
		String url = null;
		url = BASE_URL+"category/getCategories";
		return url;
	}

	/**
	 * （2）获取某产品分类的子类 
	 */
	public static String url_huoquchanpinzifenlei(String json) {
		String url = null;
		url = BASE_URL+"category/getCategories&json=" + json;
		return url;
	}

	/**
	 * （2）获取某分类的子类和其产品
	 */
	public static String url_getcategorieswithproducts(String json) {
		String url = null;
		url = BASE_URL+"category/GetCategoriesWithProducts&json=" + json;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   5、产品接口
     *************************************************************************************************************************
     */

	/**
	 * (1)获取某分类的产品并分页
	 */
	public static String url_huoqumoufenleichanpin(String json,String fenye) {
		String url = null;
		url = BASE_URL+"product/getProductList&json=" + json+"&page="+fenye;
		return url;
	}

	/**
	 * (2)获取某产品的详细信息
	 */
	public static String url_huoquchanpininfo(String json) {
		String url = null;
		url = BASE_URL+"product/getProducts&json=" + json;
		return url;
	}

	/**
	 * (3)产品名称搜索并分页
	 */
	public static String url_chanpinmingsousuo(String json,String fenye) {
		String url = null;
		url = BASE_URL+"product/getProductList&json=" + json+"&page="+fenye;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   6、产品收藏接口
     *************************************************************************************************************************
     */

	/**
	 * （1）获取某会员的收藏列表并分页
	 */
	public static String url_shoucangliebiao(String json,String fenye) {
		String url = null;
		url = BASE_URL+"ProductionFavorite/getProductionFavoriteList&json=" + json+"&page="+fenye;
		return url;
	}

	/**
	 * (2)加入收藏/取消收藏
	 */
	public static String url_jiaruquxiaoshoucang(String json) {
		String url = null;
		url = BASE_URL+"ProductionFavorite/create&json=" + json;
		return url;
	}

	/**
	 * (3)删除收藏（支持群删除）
	 */
	public static String url_shanchushoucang(String json) {
		String url = null;
		url = BASE_URL+"ProductionFavorite/delete&json==" + json;
		return url;
	}

	/**
	 * (4)判断产品是否被收藏
	 */
	public static String url_panduanshifoushoucang(String json) {
		String url = null;
		url = BASE_URL+"ProductionFavorite/isFavorite&json=" + json;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   7、地区接口
     *************************************************************************************************************************
     */

	/**
	 * (1)、获取所有城市
	 */
	public static String url_huoquchengshi() {
		String url = null;
		url = BASE_URL+"area/getCities";
		return url;
	}

	/**
	 * (2)、获取全国所有省、市、区
	 */
	public static String url_huoqushengshiqu() {
		String url = null;
		url = BASE_URL+"api/area/getAreas";
		return url;
	}

	/**
	 * (3)、获取所有省份
	 */
	public static String url_huoqusheng(String json) {
		String url = null;
		url = BASE_URL+"area/getAreas&json=" + json;
		return url;
	}

	/**
	 * (4)、获取某省下的所有城市
	 */
	public static String url_huoqushengxiashi(String json) {
		String url = null;
		url = BASE_URL+"area/getAreas&json=" + json;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   8、收货地址接口
     *************************************************************************************************************************
     */

	/**
	 * (1)获取某会员的所有收货地址
	 */
	public static String url_huoquhuiyuanaddress(String json) {
		String url = null;
		url = BASE_URL+"address/getAddresses&json=" + json;
		return url;
	}

	/**
	 * （2）获取某会员的默认收货地址
	 */
	public static String url_huoquhuiyuanmorendizhi(String json) {
		String url = null;
		url = BASE_URL+"address/getAddresses&json=" + json;
		return url;
	}

	/**
	 * （3）添加收货地址
	 */
	public static String url_addaddress(String json) {
		String url = null;
		url = BASE_URL+"address/create&json=" + json;
		return url;
	}

	/**
	 * （4）修改收货地址
	 */
	public static String url_updateaddress(String json) {
		String url = null;
		url = BASE_URL+"address/update&json=" + json;
		return url;
	}

	/**
	 *  （5）设为默认收货地址
	 */
	public static String url_setmorenaddress(String json) {
		String url = null;
		url = BASE_URL+"api/address/update&json=" + json;
		return url;
	}

	/**
	 * (6）删除收货地址
	 */
	public static String url_deladdress(String json) {
		String url = null;
		url = BASE_URL+"address/delete&json=" + json;
		return url;
	}
	
	/*
	 *************************************************************************************************************************
     *  9、栏目接口
     *************************************************************************************************************************
     */
	
	/**
	 * 栏目接口
	 */
	public static String url_lanmujiekou(String json) {
		String url = null;
		url = BASE_URL+"menu/getMenu&json=" + json;
		return url;
	}
	
	/*
	 *************************************************************************************************************************
     *  16、 活动接口
     *************************************************************************************************************************
     */

	/**
	 * （1）获取某店铺的活动列表并分页
	 */
	public static String url_huodongfenye(String json,String fenye) {
		String url = null;
		url = BASE_URL+"activity/getActivityList&json=" + json+"&page="+fenye;
		return url;
	}

	/**
	 * （2）获取活动详情
	 */
	public static String url_huodonginfo(String json) {
		String url = null;
		url = BASE_URL+"activity/getActivityDetail&json=" + json;
		return url;
	}

	/**
	 * （3）报名活动
	 */
	public static String url_baominghuodong(String json) {
		String url = null;
		url = BASE_URL+"Signup/create&json=" + json;
		return url;
	}

	/**
	 * （4）判断活动是否报名
	 */
	public static String url_panduanhuodongshifoubaoming(String json) {
		String url = null;
		url = BASE_URL+"Signup/isSignup&json=" + json;
		return url;
	}

	/**
	 * （5）获取某人已报名的进行中的活动
	 */
	public static String url_yibaominghuodong(String json,String fenye) {
		String url = null;
		url = BASE_URL+"Signup/GetSignUpList&json=" + json+"&page="+fenye;
		return url;
	}
	
	/*
	 *************************************************************************************************************************
     *  17、活动收藏接口
     *************************************************************************************************************************
     */

	/**
	 * （1）获取某会员的收藏列表并分页
	 */
	public static String url_huodongshoucanglist(String json,String fenye) {
		String url = null;
		url = BASE_URL+"ActivityFavorite/getActivityFavoriteList&json=" + json+"&page="+fenye;
		return url;
	}

	/**
	 * (2)加入收藏/取消收藏
	 */
	public static String url_huodongjrqxshoucang(String json) {
		String url = null;
		url = BASE_URL+"ActivityFavorite/create&json=" + json;
		return url;
	}

	/**
	 * (3)删除收藏（支持群删除）
	 */
	public static String url_delhuodongshoucang(String json) {
		String url = null;
		url = BASE_URL+"api/ActivityFavorite/delete&json=" + json;
		return url;
	}

	/**
	 * (4)判断某活动是否被收藏
	 */
	public static String url_panduanhdsfsc(String json) {
		String url = null;
		url = BASE_URL+"ActivityFavorite/isFavorite&json=" + json;
		return url;
	}
	
	/*
	 *************************************************************************************************************************
     *  21、产品评论
     *************************************************************************************************************************
     */

	/**
	 * （1）获取某产品的所有评论列表并分页
	 */
	public static String url_huoqupinglunlist(String json,String fenye) {
		String url = null;
		url = BASE_URL+"productComment/getCommentList&json=" + json+"&page="+fenye;
		return url;
	}

	/**
	 * （2）添加评论
	 */
	public static String url_addpinglun(String json) {
		String url = null;
		url = BASE_URL+"productComment/create&json=" + json;
		return url;
	}
	
	/*
	 *************************************************************************************************************************
     *  24、 购物车接口
     *************************************************************************************************************************
     */

	/**
	 * (1)获取购物车列表界面（点击购物车界面）
	 */
	public static String url_huoqugouwuche(String json,String fenye) {
		String url = null;
		url = BASE_URL+"shopcart/shopcart&json=" + json+"&page="+fenye;
		return url;
	}

	/**
	 * (2)获取某用户的所有订单并分页
	 */
	public static String url_alldingdan(String json) {
		String url = null;
		url = BASE_URL+"shopcart/shopcart&json=" + json;
		return url;
	}

	/**
	 *  (3)加入购物车
	 */
	public static String url_addcart(String json) {
		String url = null;
		url = BASE_URL+"shopcart/create&json=" + json;
		return url;
	}

	/**
	 * (4)编辑购物车(修改数量)
	 */
	public static String url_editcart(String json) {
		String url = null;
		url = BASE_URL+"shopcart/update&json=" + json;
		return url;
	}

	/**
	 * (5)删除购物车已存产品
	 */
	public static String url_delcart(String json) {
		String url = null;
		url = BASE_URL+"shopcart/delete&json=" + json;
		return url;
	}
	
	/*
	 *************************************************************************************************************************
     *  25、 订单接口说明：
     *  	order_status: 1  未付款  
     *  	order_status: 2  待发货 
     *  	order_status: 3  已发货
     *  	order_status: 4  已收货
     *  	order_status: 5  已取消
     *		pay_method:   1 钱包
     *  	pay_method:   1 支付宝
     *  	pay_method:   1 微信
     *************************************************************************************************************************
     */

	/**
	 * (1)获取某用户的所有订单并分页
	 */
	public static String url_alldingdanfenye(String json,String fenye) {
		String url = null;
		url = BASE_URL+"order/getOrderList&json=" + json+"&page="+fenye;
		return url;
	}

	/**
	 * (2)获取某用户的所有“未付款”订单
	 */
	public static String url_weifukuandingdan(String json) {
		String url = null;
		url = BASE_URL+"order/getOrderList&json=" + json;
		return url;
	}

	/**
	 * (3)获取某用户的所有“未付款”订单并分页
	 */
	public static String url_weifukuanfenye(String json,String fenye) {
		String url = null;
		url = BASE_URL+"order/getOrderList&json=" + json+"&page="+fenye;
		return url;
	}

	/**
	 * (4)订单群提交(POST提交)
	 */
  /**  http://localhost/weiwa/index.php?r=api/order/create
        
        
   参数名：json

   参数值：{ "city_id":183,
             "member_id":2,
             "pay_method":3,
             "pay_status":0,
             "orderlist":[
                {  
                   "shop_id":1,
                   "shipping_method":1,
                   "product_info":[{"product_id":1,"specs_id":20,"product_count":15},

{"product_id":13,"specs_id":19,"product_count":19}], 
                   "address_id":2,
                   "leave_word":"订单留言1"
             
                },

                {  
                   "shop_id":2,
                   "shipping_method":1,
                   "product_info":[{"product_id":1,"specs_id":20,"product_count":15},

{"product_id":13,"specs_id":19,"product_count":19}], 
                   "address_id":2,
                   "leave_word":"订单留言2"
             
                }

             ]

           }**/

	/**
	 * (5) 确认订单
	 */
	public static String url_querendingdan(String json) {
		String url = null;
		url = BASE_URL+"ShopCart/getConfirmOrder&json=" + json;
		return url;
	}

	/**
	 * (6)取消订单
	 */
	public static String url_quxiaodingdan(String json) {
		String url = null;
		url = BASE_URL+"order/update&json=" + json;
		return url;
	}

	/**
	 * (7)删除订单
	 */
	public static String url_deldingdan(String json) {
		String url = null;
		url = BASE_URL+"order/delete&json=" + json;
		return url;
	}

	/**
	 * (8)订单支付接口
	 */
	public static String url_dingdanzhifu(String json) {
		String url = null;
		url = BASE_URL+"order/payOrder&json=" + json;
		return url;
	}

	/**
	 * (9)订单详情接口
	 */
	public static String url_dingdaninfo(String json) {
		String url = null;
		url = BASE_URL+"order/getOrderDetail&json=" + json;
		return url;
	}
	
	/*
	 *************************************************************************************************************************
     *   26、钱包
     *************************************************************************************************************************
     */

	/**
	 * （1）获取某会员"钱包"流水记录
	 */
	public static String url_qianbaoliushui(String json) {
		String url = null;
		url = BASE_URL+"cashRecord/getCashRecordList&json=" + json;
		return url;
	}

	/**
	 * （2）钱包申请提现
	 */
	public static String url_shenqingtixian(String json) {
		String url = null;
		url = BASE_URL+"cashRecord/create&json=" + json;
		return url;
	}

	/**
	 * （3）钱包充值
	 */
	public static String url_huochongzhi(String json) {
		String url = null;
		url = BASE_URL+"rechargeRecord/create&json=" + json;
		return url;
	}
	
	/*
	 *************************************************************************************************************************
     *   28、摇一摇抽奖
     *************************************************************************************************************************
     */
	public static String url_choujiang(String json) {
		String url = null;
		url = BASE_URL+"Game/Create&json=" + json;
		return url;
	}
	
	/*
	 *************************************************************************************************************************
     *   29、 QQ、微信登录
     *************************************************************************************************************************
     */
	public static String url_qqweixinlongin(String json) {
		String url = null;
		url = BASE_URL+"site/QQLogin&json=" + json;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *  30、金币库
     *************************************************************************************************************************
     */
	
	/**
	 * 1）获取兑换产品列表
	 */
	public static String url_duihuanchanpin(String json) {
		String url = null;
		url = BASE_URL+"ExchangeProduct/GetProductList&json=" + json;
		return url;
	}

	/**
	 * (2)获取订单列表
	 */
	public static String url_dingdanlist(String json,String fenye) {
		String url = null;
		url = BASE_URL+"ExchangeOrder/GetOrderList&json=" + json+"&page="+fenye;
		return url;
	}

	/**
	 * (3)获取订单详情
	 */
	public static String url_scdingdaninfo(String json) {
		String url = null;
		url = BASE_URL+"ExchangeOrder/GetOrderDetail&json=" + json;
		return url;
	}

	/**
	 * (4)创建订单
	 */
	public static String url_scchuangjiandingdan(String json) {
		String url = null;
		url = BASE_URL+"ExchangeOrder/Create&json=" + json;
		return url;
	}

	/**
	 * (4)创建订单
	 */
	public static String url_schuangjiandingdan(String json) {
		String url = null;
		String ajson = json.replaceAll("\\\\", "");
		url = BASE_URL+"order/createOrder&json=" + ajson;
		return url;
	}

	/**
	 * (5)获取某会员金币记录
	 */
	public static String url_jinbihuoqujilu(String json) {
		String url = null;
		url = BASE_URL+"GoldcoinRecord/GetGoldcoinRecordList&json=" + json;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   30、发表意见
     *************************************************************************************************************************
     */
	public static String url_fabiaoyijian(String json) {
		String url = null;
		url = BASE_URL+"suggest/create&json=" + json;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   31、获取消息列表 
     *************************************************************************************************************************
     */
	public static String url_huoquxiaoxi(String json,String fenye) {
		String url = null;
		url = BASE_URL+"message/getMessageList&json=" + json+"&page="+fenye;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   16、分享接口
     *************************************************************************************************************************
     */
	public static String url_fenxiangjifen(String json) {
		String url = null;
		url = BASE_URL+"share/create&json=" + json;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   16、版本更新
     *************************************************************************************************************************
     */
	public static String url_banbengengxin() {
		String url = null;
		url = BASE_URL+"site/getVersion";
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   （1）获取所有省份
     *************************************************************************************************************************
     */
	public static String url_huoqushengfen() {
		String url = null;
		url = BASE_URL+"Province/GetProvinceList";
		return url;
	}

	/*
	 *************************************************************************************************************************
     *   （2）获取某省的所有城市
     *************************************************************************************************************************
     */
	public static String url_huoqushi(String json) {
		String url = null;
		url = BASE_URL+"City/GetCityList&json=" + json;
		return url;
	}

	/*
	 *************************************************************************************************************************
     *    （3）获取某城市的粉丝手机号并分页
     *************************************************************************************************************************
     */
	public static String url_huoqushoujihao(String json,String fenye) {
		String url = null;
		url = BASE_URL+"FansMobile/GetFansMobileList&json=" + json+"&page="+fenye;
		return url;
	}
}
