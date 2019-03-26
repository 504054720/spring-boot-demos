

package com.nyfz.util;

import java.util.HashMap;
import java.util.Map;

public final class Constants {
	public static final String SYS_APP_ID = "ocpcore";
	public static final String SESSION_USER = "account";
	public static final String NAMESPACE_SHARE = "/sh";
	
	public static final String SUPERMAN = "2";	// 超级管理员
	public static final String PRIV_MENU = "MENU";	// 菜单权限
	public static final String PRIV_BUTTON = "BUTTON";	// 按钮权限
	public static final String PRIV_LINK = "LINK";	// 链接权限

	public static final int NORMAL_AGENT = 0; // 普通会员
	public static final int CHANNEL_EMPLOYEE = 1;	// 接包方
	public static final int DELIVER_EMPLOYEE = 2;	// 发包方
	
	//不需要登录访问的路径
	private static final String[] NO_FILTER_LOGIN = {"/css","/js","/lib","/image","/module/public","/gateway","/error","/ftl/headfloor",
		"checkweb.jsp","index.html","/admin_login.html","agent/homeOutBoundBusiness!getAgentOutboundTaskIndexOrderByRecentlyTime",
		"sh/login!","sh/singleton!initToken","/front/account!isSerial","agent/account!isAuthCode",
		"agent/account!insertActhCodeDate","front/agent/user!getUserName",
		"agent/account!loginVerificationCode","agent/account!resetVerificationCode","agent/account!registerVerificationCode","agent/account!addNewAccount",
		"agent/account!insertAgentQuickRegister","agent/account!checkVerificationCode",
		"agent/account!checkFindPwdMobile","agent/account!findPwdSaveAccountMobile",
		"agent/account!getUserAccountId",
		"agent/account!modifyPassword","sh/common!","sh/user!queryCustByLblId"
		,"/fileserver/abouthome", "/fileserver/download", "/fileserver/excel"
		,"/fileserver/gateway", "/fileserver/image", "/fileserver/images"
		,"/fileserver/market", "/fileserver/train", "/fileserver/audio","/fileserver/vedio"
		,"/front/sh/extern!renwuduoLogin"
		,"/front/sh/extern!renwuduoIDCard","/front/agent/account!getRealNameResult"
		,"sh/heartBeat!queryService","agent/agentHelp!getHelpTypeList","agent/agentHelp!getHelpListByPage","agent/agentHelp!getHelpInfoById"
		,"/module/agent/outbound/questionnaireDomain.html","/module/agent/outbound/questionnaireDomainFalse.html"
	};
	
	public static String[] getNoFilterLogin() {
		return NO_FILTER_LOGIN;
	}
	
	//管理端路径
	private static final String[] ADMIN_FILTER_DIR = {"/front/admin","module/admin","fileserver/personalInfo","/fileserver/voice","fileserver/phonerecord","/media/111111/","/data","sh/interface"};
	
	public static String[] getAdminFilterDir() {
		return ADMIN_FILTER_DIR;
	}
	
	//会员端路径
	private static final String[] AGENT_FILTER_DIR ={"/front/agent","/front/sh/singleton!heartBeat","/front/sh/common!preListenRecord","module/agent","sh/interface","/smodule","fileserver/personalInfo","/fileserver/voice","fileserver/phonerecord","/media/111111/","/ftl/component","/data"};
	
	public static String[] getAgentFilterDir() {
		return AGENT_FILTER_DIR;
	}
	
	// 三方公司管理端路径
	private static final String[] CHANNEL_FILTER_DIR = {"module/channel", "/front/channel", "fileserver/personalInfo","/fileserver/voice","/media/111111/","/data"};
	// 发包企业管理端路径 
	private static final String[] DELIVER_FILTER_DIR = {"module/deliver", "/front/deliver", "fileserver/personalInfo","/fileserver/voice", "/fileserver/deliver","/media/111111/","/data","/front/agent/agentMyTrain!findAgentOnlineTrain","sh/interface"};
		
	public static String[] getChannelFilterDir() {
		return CHANNEL_FILTER_DIR;
	}
	public static String[] getDeliverFilterDir() {
		return DELIVER_FILTER_DIR;
	}
	// 不需要csrf post校验的url
	private static final  String[] NO_CHECK_POST ={"singleton!initToken?uid=login006","login!ipLoginNum?uid=login005","login!genRsaPublicKey?uid=login004","report!findPerformanceDerive?uid=10002","adminEmployee!findRightByEmployeeId?uid=u0012","login!userCheckCode?uid=login003","adminTrain!uploadImgTrain?uid=train10007","homeOutBoundBusiness!findAgentOutboundBusiness?uid=home0001",
		"report!findQuestionaireReportDerive?uid=10004","agent/homeOutBoundBusiness!findQuestionByTrainId?uid=home00014","agent/homeOutBoundBusiness!index?uid=home00030","/front/agent/questionaireAction!index",
		"report!findMemberEmolumentDerive?uid=10006","report!findQuestionaireDetailDerive?uid=10008","report!findOrderDetailDerive?uid=10010","member!findMemberDerive?uid=member0011","account!checkLoginAccountPassword?uid=myaccount009","agent/member!testLoad?uid=myaccount019"
		,"/front/sh/extern!renwuduoLogin?uid=extern001","/front/sh/extern!renwuduoIDCard?uid=extern002","adminQuestion!importQuestion?uid=10009","agent/product!getProductSKUQTY?uid=u0003","/front/sh/singleton!heartBeat?uid=p004", 
		"uploadImg","testLoad","uploadImgTrain","uploadAudioTrain","uploadVedioTrain","sh/heartBeat!queryService","/admin/outboundBusiness!exportOutboundTaskDetailByTask?uid=project0029", "/admin/outboundBusiness!exportAgentsBybusinessId?uid=project0030"
		,"/front/admin/report!exportPrdAnalyzeData?uid=10012","/front/admin/outboundBusiness!downRecord?uid=project0038","/front/admin/outboundBusiness!exportPhoneList?uid=project0040","/front/admin/report!exportTouchData?uid=10014","/front/agent/quality!downRecord?uid=q001"
		,"/front/channel/channelMember!findMemberDerive?uid=member002","/front/channel/report!findChannelPerformanceDerive?uid=q005","/front/channel/report!findChannelQuestionaireDetailDerive?uid=q006"
		,"/front/channel/report!findChannelPrdAnalyzeDataDerive?uid=q001","/front/channel/report!findChannelTouchDataDerive?uid=q001",
		"/front/admin/smartreport!downloadFile?uid=download1001","/front/channel/smartReport!downloadFile?uid=download1001","/front/deliver/smartReport!downloadFile?uid=download1001","/front/sh/common!downloadRecord","/front/sh/common!preListenRecord"
		,"/front/deliver/deliverSalary!downloadTemplate?uid=salary001","/front/deliver/deliverSalary!downloadByFileName?uid=salary004","/front/admin/adminSalary!downloadByFileName?uid=adminSalary003","/front/deliver/deliverSalary!exportSalaryPreSettlementData?uid=salary007"
		,"/front/admin/salaryPreSettlement!exportSalaryPreSettlementData?uid=preSalary002","/front/channel/channelSalary!exportSalaryPreSettlementData?uid=channelSalary002","adminLossOrder!exportLoseOrderAudit?uid=aLoss003","deliverLossOrder!exportLossOrder?uid=dlvLoss002"
		,"/front/admin/smartreport!downloadFileByUrl?uid=smart32","/front/sh/common!downloadFileByUrl?uid=u0013"
	};
	
	public static String[] getNoCheckPost() {
		return NO_CHECK_POST;
	}
	private static final  String[] CHECK_RIGHT ={"project_detail.html","job_detail.html","channel/member/view_member.html",
		"/agent/train/view_train.html","agent/train/view_quiz.html","agent/train/online_exam.html"
	};
	public static String[] getCheckRight() {
		return CHECK_RIGHT;
	}
	//需要做token校验的url
	private static final  String[] USE_CHECK_TOKEN ={
		//"/front/admin/","/front/channel/","/front/agent/"
			"homeOutBoundBusiness!getVerificationCode?uid=home0036","/front/agent/homeOutBoundBusiness!checkVerificationCode?uid=home0037"
		};
	
	public static String[] getUseCheckToken() {
		return USE_CHECK_TOKEN;
	}
	
	//不需要xss校验的url
	private static final  String[] NO_CHECK_XSS={"/front/admin/home!btnAuthorize","gateWay!updateAboutHomeById?uid=gate0005","/front/admin/help!insertHelp?uid=help004","/front/admin/help!updateHelpById?uid=help007","adminTrain!insertOperationTrain","adminQuestion!insertSheet","adminQuestion!update","agentTest!updateBatch","uploadImg","testLoad","uploadImgTrain","uploadAudioTrain","uploadVedioTrain","AboutHome","/front/agent/questionaireAction!index","interface!submitOrder?uid=u0001"
		,"/front/sh/extern!renwuduoLogin?uid=extern001","/front/sh/extern!renwuduoIDCard?uid=extern002","sh/heartBeat!queryService","/front/admin/outboundBusiness!saveSpeechCraft","/front/agent/member!submitProfessionTestAnswer?uid=myaccount022","/front/admin/adminEmployee!saveEmployeeRoleBatch?uid=u0008","sh/heartBeat!queryService","/front/admin/outboundBusiness!saveSmsContent?uid=project0043",
		"/front/channel/channelMember!insertJoinGroup?uid=member014","/front/agent/account!getRealNameResult?uid=myaccount041","/front/admin/smartreport!","/front/channel/smartReport!","/front/deliver/smartReport!","/front/admin/adminPerms!insertRightPower","/front/admin/adminPerms!updateRightPower"
		,"/front/deliver/outboundBusiness!saveSpeechCraft","/front/deliver/outboundBusiness!updateBusinessName?uid=project0026","/front/admin/outboundBusinessTrain!saveSelectorTrain?uid=project0023","/front/admin/adminTrain!saveRegisterTrain?uid=train10019","/front/admin/outboundBusiness!delOutboundBusinessTrain","/front/deliver/outboundBusinessTrain!saveSelectorTrain?uid=project0023","/front/deliver/outboundBusiness!deleteOutboundBusinessTrain?uid=project0006","/front/admin/adminTrain!findRegisterTrain?uid=train10018"
        ,"/front/admin/outboundBusiness!findAllOutboundBusinessTrain","/front/deliver/outboundBusiness!findAllOutboundBusinessTrain?uid=project0056","/front/admin/adminInterface!interfaceTest?uid=q001","/front/deliver/outboundBusiness!saveSmsContent?uid=project0043","/front/agent/contact!executeFun?uid=cloud03","/front/deliver/smartReport!findZBMultiBusinessHandle?uid=smartRT24","/front/deliver/smartReport!exportZBMultiBusinessHandle?uid=smartRT25","/front/admin/adminSystem!insertSystemFrequencyBlack?uid=10033"
        ,"/front/admin/adminSystem!delSystemFrequencyBlack?uid=10034"
		,"/front/agent/lossOrder!saveLossData?uid=loss001"
		,"/front/agent/lossOrder!findLossData?uid=loss002"
		,"/front/admin/adminLossOrder!findAdminLossOrder?uid=aLoss001"
        ,"/front/channel/channelLossOrder!findLossOrder?uid=cnlLoss001"
        ,"/front/deliver/deliverLossOrder!findLossOrder?uid=dlvLoss001"
        ,"/front/deliver/deliverSalary!uploadSalaryInfo?uid=salary002" 
        ,"/front/admin/smartReport!findSalaryDataImportReport?uid=smartRT30"
        ,"/front/sh/common!downloadFileByUrl?uid=u0013"
        ,"front/deliver/outboundBusiness!saveCloudQuestionaire?uid=questionaire01"
        ,"front/admin/outboundBusiness!saveCloudQuestionaire?uid=questionaire01"
	};	
	
	public static String[] getNoCheckXss() {
		return NO_CHECK_XSS;
	}
	
	//半角转全角url
	//有的文本输入框需要输入半角字符，不能直接拒绝，需要将半角替换为全角
	private static final String[] USE_REPLACE_HALF_ANGLE={"/front/agent/contact!executeFun?uid=u019","/front/agent/account!savePersonalInfo?uid=myaccount015","/front/agent/member!editorPersonalInfo?uid=myaccount017","/front/agent/member!editorAgentEducationExperience?uid=myaccount018","/front/agent/member!saveTypingTest?uid=myaccount020",
		"agentMyTrain!submitExamQuestionAnswer","/front/agent/questionaireAction!index?uid=qoro026","/front/sh/interface!submitOrder?uid=u0001","/front/sh/interface!submitFlowOrder?uid=u0004","/front/sh/interface!submitPackageOrder?uid=u0003","/front/agent/member!submitProfessionTestAnswer?uid=myaccount022","/front/admin/adminEmployee!saveEmployeeRoleBatch?uid=u0008","/front/agent/member!addMemberWorkExperience?uid=myaccount033","/front/agent/member!updateMemberWorkExperienceById?uid=myaccount032"
		,"admin/help!insertHelpType","admin/help!updateHelpTypeById","/front/agent/quality!saveReplyReport?uid=q001","/front/admin/outboundBusiness!saveMarketingOptionsInfo?uid=project0046"
		,"/front/agent/outboundBusiness!modifyHangupOption","/front/admin/smartreport!findQuestionaireDetail?uid=query1001","/front/admin/smartreport!exportQuestionaireDetail?uid=export1001","/front/admin/smartreport!findOutboundDetail?uid=query1002","/front/admin/smartreport!exportOutboundDetail?uid=export1002","/front/admin/smartreport!findQualityDataAnalyzeDay?uid=smart01","/front/admin/smartreport!exportQualityDataAnalyzeDay?uid=smart02","/front/admin/smartreport!findPrdDataAnalyzeDay?uid=smart03","/front/admin/smartreport!exportPrdDataAnalyzeDay?uid=smart04"
		,"/front/admin/smartreport!findOrderDetail?uid=smart05","/front/admin/smartreport!exportOrderDetail?uid=smart06","/front/admin/smartreport!findAgentBonus?uid=smart07","/front/admin/smartreport!exportAgentBonus?uid=smart08","/front/admin/smartreport!findAgentPerformance?uid=smart09","/front/admin/smartreport!exportAgentPerformance?uid=smart10","/front/admin/smartreport!findPdnDateDayPartingReport?uid=smart11","/front/admin/smartreport!exportPdnDateDayPartingReport?uid=smart12","/front/admin/smartreport!findSatisfactionSurveyReport?uid=smart13","/front/admin/smartreport!exportSatisfactionSurveyReport?uid=smart14"
		,"/front/channel/smartReport!findQualityDataAnalyzeDay?uid=smart01","/front/channel/smartReport!exportQualityDataAnalyzeDay?uid=smart02","/front/channel/smartReport!findPrdDataAnalyzeDay?uid=smart03","/front/channel/smartReport!exportPrdDataAnalyzeDay?uid=smart04","/front/channel/smartReport!findQuestionaireDetail?uid=smart05","/front/channel/smartReport!exportQuestionaireDetail?uid=smart06","/front/channel/smartReport!findOutboundDetail?uid=smart07","/front/channel/smartReport!exportOutboundDetail?uid=smart08","/front/channel/smartReport!findOrderDetail?uid=smart09","/front/channel/smartReport!exportOrderDetail?uid=smart10"
		,"/front/channel/smartReport!findAgentBonus?uid=smart11","/front/channel/smartReport!exportAgentBonus?uid=smart12","/front/channel/smartReport!findAgentPerformance?uid=smart13","/front/channel/smartReport!exportAgentPerformance?uid=smart14","/front/channel/smartReport!findPdnDateDayPartingReport/uid=smart15","/front/channel/smartReport!exportPdnDateDayPartingReport?uid=smart16","/front/channel/smartReport!findSatisfactionSurveyReport?uid=smart17","/front/channel/smartReport!exportSatisfactionSurveyReport?uid=smart18","/front/admin/outboundBusiness!updateProductFlow?uid=project0028"
		,"/front/admin/adminPerms!insertRightPower?uid=p0003","/front/admin/adminPerms!updateRightPower?uid=p0004","/front/sh/interface!submitOrderPhone?uid=u0001","/front/admin/adminProductManage!saveSProductInfo","/front/deliver/deliverProductManage!saveSProductInfo",
		"/front/admin/adminProductManage!updateSProductInfo","/front/deliver/deliverProductManage!updateSProductInfo","/front/sh/interface!submitActivityOrder?uid=u0004","/front/sh/interface!uniformOrderEntrance?uid=q001","/front/sh/interface!submitProductOrder?uid=se0001"
	};
	
	public static String[] getUseReplaceHalfAngle() {
		return USE_REPLACE_HALF_ANGLE;
	}
	
	public interface SYSTEM_PROP_CONFIG {
		String REDIS_ADDR_CFG = "REDIS_ADDR_CFG";// Redis IP地址配置，各ip之间以半角逗号","分隔
		String REDIS_CFG_SPLIT = ",";// 分隔符
		String APP_ARRAY_UNION_URL = "APP_ARRAY_UNION_URL";// ecpcore层地址配置		
		String REDIS_MAX_TOTAL = "REDIS_MAX_TOTAL"; // 最大连接数key值，默认8个
		String REDIS_MAX_IDLE = "REDIS_MAX_IDLE"; // 最大空闲连接数key值，默认8个
		String REDIS_MIN_IDLE = "REDIS_MIN_IDLE"; // 最小空闲连接数key值，默认8个
		String REDIS_CONN_TIMEOUT = "REDIS_CONN_TIMEOUT"; // connection timeout
		String REDIS_SOCK_TIMEOUT = "REDIS_SOCK_TIMEOUT";	// socket timeout
		String REDIS_MAX_REDIRECT = "REDIS_MAX_REDIRECT";	// max redirection
	}
	

	/*
     * 追加号码最大数量限制
     */
    public interface IMPORT_PHONE_LIMIT{
        int MAX_SIZE=10000; // 最大支持导入数
    }
    
    /*
     * 导入会员星级信息数量限制
     */
    public interface IMPORT_STARINFO_LIMIT{
        int MAX_SIZE=10000; // 最大支持导入数
    }
    
    
    /*
     * 导入会员批量资格删除数目限制
     */
    public interface IMPORT_AGENT_TASK_LIMIT{
        int MAX_SIZE=500; // 最大支持导入数
    }
    
    
	public interface CTI_INTERFACE {
		String CTI_TASK_SAVE = "cti_agent_task_save";// 保存任务
		String CTI_TASK_UPDATE = "cti_agent_task_update";// 更新任务
		String CTI_TASK_RESULT_DETAIL = "cti_task_result_detail";// 查询外呼结果详情
		String CTI_AGENT_TASK_PHONE_APPEND = "cti_agent_task_phone_append";//追加目标客户号码
		String CTI_AGENT_TASK_TASKID="cti_agent_task_taskid";
		String CTI_AGENT_TASK_AGENT_MEMBERS_ADD = "cti_agent_task_agent_members_add";//追加坐席到任务执行坐席队列
		String CTI_AGENT_TASK_AGENT_MEMBERS_DELETE = "cti_agent_task_agent_members_delete";//删除坐席从任务执行坐席队列
		String CTI_AGENT_LIST="cti_agent_list"; //查询人工外呼任务的坐席列表
		String CTI_AGENT_LIST_COUNT = "cti_agent_list_count";	// 查询人工外呼的坐席总数
		String CTI_AGENT_LIST_BY_PAGE = "cti_agent_list_by_page"; // 分页查询人工外呼任务的坐席列表
		String CTI_AGENT_FIND="cti_agent_find"; //查询坐席信息
		String CTI_FIND_OUTCALL="cti_find_outcall"; //查询单个人工外呼
		String CTI_MAN_TASK_RST="cti_man_task_rst"; //查询人工外呼任务结果接口
		String CTI_AGENT_RATE_MODIFY="cti_agent_rate_modify"; //修改外拨速率因子
		String CTI_TASK_RUNNING_DETAIL="cti_task_running_detail";//查询外呼运行情况
		String CTI_SCHEDULE_SAVE="cti_schedule_save";//保存日程
		String CTI_FIND_EXTENSION="cti_find_extension";//查询单个分机
		String CTI_SAVE_EXTENSION="cti_save_extension";//增加分机
		String CTI_DELETE_EXTENSION="cti_delete_extension";//删除分机
		String CTI_FIND_AGENT="cti_find_agent";//查询单个坐席
		String CTI_SAVE_AGENT="cti_save_agent";//增加坐席
		String CTI_DELETE_AGENT="cti_delete_agent";//删除坐席
	}
	
 	//问卷模式：85同步问卷、云问卷
 	public static final String QUESTIONAIRE_MODEL = "ywj";//使用云问卷提供问卷模式
 	
 	/**
 	 * 集团商城客户信息接口，客户级别转换
 	 */
 	@SuppressWarnings("serial")
	public final static Map<String, String> jtscUserLevelMap = new HashMap<String, String>() {
        {
            put("000", "保留");
            put("100", "普通客户");
            put("300", "普通大客户");
            put("301", "钻石卡大客户");
            put("302", "金卡大客户");
            put("303", "银卡大客户");
            put("304", "贵宾卡大客户");
            put("305", "白玉兰大客户");
        }
    };
    
    /**
 	 * 一级客服六期客户信息接口，客户级别转换
 	 */
 	@SuppressWarnings("serial")
	public final static Map<String, String> csvc6UserLevelMap = new HashMap<String, String>() {
        {
        	put("05", "五星（钻）");
        	put("06", "五星（金）");
        	put("07", "五星");
        	put("08", "四星");
        	put("09", "三星");
        	put("10", "二星");
        	put("11", "一星");
        	put("12", "准星");
        	put("13", "未评级");
        }
    };
    
    /*
     * 有效状态
     */
    public static final String STATE_VALID = "1"; 	// 有效
    public static final String STATE_INVALID = "0";	// 无效
    
    /*
     * 接口渠道编码对应
     */
    @SuppressWarnings("serial")
	public final static Map<String, String> channelCodeMap = new HashMap<String, String>() {
        {
        	put("2", "beijing"); //北京 bj
        	put("3", "tianjin"); //天津 tj
        	put("4", "hebei"); //河北 hb
        	put("5", "shanxi"); //山西 sx
        	put("6", "neimenggu"); //内蒙古 nmg
        	put("7", "liaoning"); //辽宁 ln
        	put("8", "jilin"); //吉林 jl
        	put("9", "heilongjiang"); //黑龙江 hlj
        	put("10", "shanghai"); //上海 sh
        	put("11", "jiangsu"); //江苏 js
        	put("12", "zhejiang"); //浙江 zj
        	put("13", "anhui"); //安徽 ah
        	put("14", "fujian"); //福建 fj
        	put("15", "jiangxi"); //江西 jx
        	put("16", "shandong"); //山东 sd
        	put("17", "henan"); //河南 hn
        	put("18", "hubei"); //湖北 hub
        	put("19", "hunan"); //湖南 hun
        	put("20", "guangdong"); //广东 gd
        	put("21", "guangxi"); //广西 gx
        	put("22", "hainan"); //海南 hain
        	put("23", "chongqing"); //重庆 cq
        	put("24", "sichuan"); //四川 sc
        	put("25", "guizhou"); //贵州 gz
        	put("26", "yunnan"); //云南 yn
        	put("27", "xizang"); //西藏 xz
        	put("28", "shanxisheng"); //陕西 shanx
        	put("29", "gansu"); //甘肃 gs
        	put("30", "qinghai"); //青海 qh
        	put("31", "ningxia"); //宁夏 nx
        	put("32", "xinjiang"); //新疆 xj
        	put("33", "xianggang"); //香港 xg
        	put("34", "aomen"); //澳门 am
        	put("35", "taiwan"); //台湾 tw
        	put("36", "quanguo"); //全国qg
        }
    };
    
    /*
     * 全网宽带业务类型对应
     */
    @SuppressWarnings("serial")
	public final static Map<String, String> busiTypeMap = new HashMap<String, String>() {
        {
        	put("010601", "RENEW");//宽带续约
        	put("010602", "NEW");//宽带新装
        	put("010603", "ADD");//增值产品办理
        	put("010604", "SPEED_UP");//宽带提速
        	put("010605", "APPOINT");//预约登记
        }
    };
    
    /*
     * 全网家宽对应渠道码对应
     */
    @SuppressWarnings("serial")
	public final static Map<String, String> channelNgwlanCodeMap = new HashMap<String, String>() {
        {
        	put("2", "00030001"); //北京 bj
        	put("3", "00030002"); //天津 tj
        	put("4", "00030004"); //河北 hb
        	put("5", "00030010"); //山西 sx
        	put("6", "00030003"); //内蒙古 nmg
        	put("7", "00030006"); //辽宁 ln
        	put("8", "00030007"); //吉林 jl
        	put("9", "00030005"); //黑龙江 hlj
        	put("10", "00030028"); //上海 sh
        	put("11", "00030021"); //江苏 js
        	put("12", "00030023"); //浙江 zj
        	put("13", "00030030"); //安徽 ah
        	put("14", "00030024"); //福建 fj
        	put("15", "00030022"); //江西 jx
        	put("16", "00030008"); //山东 sd
        	put("17", "00030009"); //河南 hn
        	put("18", "00030019"); //湖北 hub
        	put("19", "00030020"); //湖南 hun
        	put("20", "00030025"); //广东 gd
        	put("21", "00030026"); //广西 gx
        	put("22", "00030029"); //海南 hain
        	put("23", "00030018"); //重庆 cq
        	put("24", "00030017"); //四川 sc
        	put("25", "00030027"); //贵州 gz
        	put("26", "00030016"); //云南 yn
        	put("27", "00030015"); //西藏 xz
        	put("28", "00030011"); //陕西 shanx
        	put("29", "00030012"); //甘肃 gs
        	put("30", "00030031"); //青海 qh
        	put("31", "00030013"); //宁夏 nx
        	put("32", "00030014"); //新疆 xj
        }
    };
    
    /*
     * 二级业务分类ID对应
     */
    public interface SUB_OPERATION_ID {
		String SUB_OPERATION_WHYX = "11"; //外呼营销
		String SUB_OPERATION_DDRW = "12"; //订单任务
		String SUB_OPERATION_4GLLYX = "010102"; //4G流量营销
		String SUB_OPERATION_4GTCYX = "010201"; //4G套餐营销
		String SUB_OPERATION_YXHD = "010201"; //营销活动
		String SUB_OPERATION_4GZDYX = "010701"; //4G终端营销
		String SUB_OPERATION_JKYX = "010602"; //家宽营销
	}
    
    /**
     * 系统错误编码
     */
    public interface SYS_ERROR_CODE {
		String XSS_ERROR = "2000"; //xss注入，输入非法字符
	}
    public static final int STATE_FREQUENCY_OVER_MAX= -9998; 	// 会员端请求频次好过超过最大频次
    public static final String UPLOAD_TEMP_DIR="/tmp/upload/";//上传文件的临时目录。
    public interface UPLOAD_SALARY_TYPE{//上传薪酬文件类型 
    	int UPLOAD=0;//追加
    	int REPLACE=1;//替换
    }
}
