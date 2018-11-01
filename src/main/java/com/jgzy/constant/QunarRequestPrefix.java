package com.jgzy.constant;

/**
 * @program: ahomet-app
 * @description: 去哪儿接口地址
 * @author: yaobing
 * @create: 2018-07-19 16:11
 */
public class QunarRequestPrefix {
    /*   名称				 编码			类型			必填		示例值			默认值	描述`*/
    /*	`修改时间			updateTime	DateTime	否								修改时间,按大于这个时间返回修改的酒店`*/
    /*	`是否要求有在线报价	havePrice	Boolean		否		false			true	是否要求有在线报价，如果为true则返回当时有报价的酒店列表。默认返回有报价的酒店列表。`*/
    /*	`城市编码			cityCode	String		否		beijing_city			一级城市的cityCode（对应城市接口中code和父code相同的城市编码）`*/
    public static final String hotelInfoList_GET = QunarConstant.APIPrefix + "/api/hotel/queryHotelList.json";

    /*	名称			编码			类型		必填	示例值			默认值	描述*/
    /*	酒店ID		hotelIds	String	是	2536987,2536988			酒店ID（支持传多个ID，使用逗号分隔，建议每次最多传入10个酒店）*/
    /*	是否需要图片	isNeedImage	Boolean	否	true			true	是否需要图片（默认是true，会返回图片信息）*/
    /*	是否需要物理房型	isNeedRooms	Boolean	否	true			false	是否需要物理房型*/
    public static final String hotelDetailList_GET = QunarConstant.APIPrefix + "/api/hotel/queryHotelDetail.json";

    /* 无需要的参数	*/
    public static final String cityInfoList_GET = QunarConstant.APIPrefix + "/api/city/queryCity.json";

    /* 获取房型报价	*/
    /*入住日期			arrivalDate			Date	是	2014-02-18		入住日期*/
    /*离店日期			departureDate		Date	是	2014-02-20		离店日期*/
    /*酒店ID列表		hotelIds			String	是	570526865		酒店Id,每次一个酒店Id*/
    /*是否跳过预订规则校验	isSkipHdsCondition	Boolean	否	true	true	是否跳过预订规则校验,如果是true时,分销商拿回带原始预订规则的报价,自行解析预订规则。*/
    /*{*/
    /*  "head": {*/
    /*   "appKey": "12345678",*/
    /*    "salt": "123",*/
    /*    "sign": "063cae89a00896187f80eecbf922364a",*/
    /*    "version": "3.1.0"*/
    /*  },*/
    /*  "data": {*/
    /*    "arrivalDate": "2015-11-01",*/
    /*    "departureDate": "2015-11-02",*/
    /*    "hotelIds": "1111",*/
    /*    "isSkipHdsCondition": "true"  【need false】  */
    /*  }*/
    /*}*/
    public static final String getRoomPlan_GET = QunarConstant.APIPrefix + "/api/hotel/queryRatePlan.json";

    /* 查询订单报价  */
    /*名称			编码					类型				填	示例值			默认值		描述 */
    /*入住日期		arrivalDate			Date			是	2014-02-18					入住日期 */
    /*离店日期		departureDate		Date			是	2014-02-20					离店日期 */
    /*酒店ID			hotelIds			String			是	570526865					酒店Id,每次一个酒店Id */
    /*产品编码		ratePlanId			Long			是	41665						产品编码 */
    /*预订房间数量	roomNum				Int				否	1				1			预订房间数量 */
    /*最晚到店时间	latestArrivalTime	DateTime		否	2015-01-01 18:00:00			最晚到店时间 */
    /*成人儿童数		occupancyInfos		OccupancyInfo[]	否	[{		成人儿童数。仅对国际报价生效。如果使用该参数时，同时使用了roomNum，则数组长度需要和roomNum一致 */
    public static final String queryOrderPrice_GET = QunarConstant.APIPrefix + "/api/hotel/queryOrderPrice.json";


    /* 创建订单 */
    /*名称			编码							类型			必填		示例值			默认值			描述*/
    /*分销商订单编号	affiliateConfirmationId		String		是		oottii11111111					分销商订单编号，编号相同会视为重复下单(每次请求使用不同的分销商订单编号)*/
    /*酒店编号		hotelId						String		是		457								酒店编号*/
    /*离店时间		departureDate				Date		是		2014-02-09						离店时间*/
    /*房型编号		roomTypeId					String		是		11035368						房型编号*/
    /*产品编号		ratePlanId					String		是		41663							产品编号*/
    /*入住时间		arrivalDate					Date		是		2014-02-08						入住时间*/
    /*房间数量		numberOfRooms				Int			是		1								有几个房间数量，对应几个OrderRooms节点数组。*/
    /*顾客数量		numberOfCustomers			Int			否		1								顾客数量*/
    /*最晚到店时间	latestArrivalTime			DateTime	是		2014-02-08 17:00:00				预付房间整晚保留。对于现付，不能早于报价接口的arrivalStartTime，不能晚于报价接口的arrivalEndTime。对于钟点房，该值为开始入住的时间点，选择不同的rateplanId则表示使用该rateplan中sellTimeQuantity的值作为入住的时长。*/
    /*货币类型		currencyCode				Enum		是		RMB								RMB人民币 HKD港币 MOP澳门币 USD美币 SGD新加坡币*/
    /*给酒店备注		noteToHotel					String		否										客人给酒店的备注，尽量不填写，以免影响房间确认速度，并可减少投诉；若必须要填，尽量对要求进行规范，便于沟通和处理，长度不能超过120个字符。因特殊要求无法满足导致的拒单不享受赔付。*/
    /*联系人			contact						Contact		是										联系人*/
	/*客人信息集		orderRooms					OrderRoom[]	是										客人信息集（每间房的入住人信息，预定几间就需要传入几个）
		/*入住人信息数组		customers	Customer[]	是			入住人信息数组，每个房间传入一个入住人信息。（所填姓名需与入住时所持证件一致）*/
    /*姓名	name		String	是	张大力		中文或者英文,不能带数字以及特殊符号。长度至少2个字符且不能超过20位。非大陆酒店请传英文。（国际报价时只填入不带姓氏的名字拼音）*/
    /*姓氏	lastName	String	否	Zhang		姓氏（国内报价不要填写，国际报价时必填，填入姓氏拼音）*/
    /*成人数				adults		Int			否	2		成人数。对于国际报价，成人数必填。*/
    /*儿童数				children	Int			否			儿童数*/
    /*儿童年龄			childAges	String		否	“3|2|4”		儿童年龄（多个时用 | 分割）。年龄的个数需要和儿童数量相等，否则会下单失败*/
    /*是否团单		groupBuyFlag				Enum		否						notGroupBuy		团单-isGroupBuy；非团单-notGroupBuy。团单允许一个订单多个房间，但只输入一个入住人姓名。*/
    /*是否担保		needGuarantee				Boolean		否		false			false			是否担保（这个字段只针对现付业务，现付必填）*/
    /*担保金额		guaranteePrice				Decimal		否		100				0				如果是现付担保订单，则必填*/
    /*支付类型		paymentType					Enum		是		SelfPay							订单支付类型，如果是预付填Prepay，如果是现付填SelfPay*/
    /*结算总价		totalCost					Decimal		否		100								预付时必填，为和分销商的结算总价。*/
    /*总卖价			totalSalePrice				Decimal		否										现付时必填，为酒店总卖价。*/
    /*宾客类型		customerType				Enum		否		DOMESTIC						客人类型（DOMESTIC：内宾；FOREIGNER：外宾）*/
    /*分销商对外销售总价	distributorSalePrice	Decimal		否		300								分销商对外销售总价*/
    public static final String createOrder_POST = "https://hdspci.qunar.com" + "/api/order/createOrder.json";

    /**获取订单详情*/
    /*去哪儿订单编号	orderId	String	否	123456781401101123114122		去哪儿订单编号和分销商订单编号至少需要一个*/
    /*分销商订单编号	affiliateConfirmationId	String	否	xyz178292710xy282		去哪儿订单编号和分销商订单编号至少需要一个*/
    public static final String getOrderDetail_GET = QunarConstant.APIPrefix +"/api/order/queryOrderDetail.json";

    /*去哪儿订单编号	orderId	String	是	100557575560		去哪儿订单编号*/
    public static final String cancelOrder_GET = QunarConstant.APIPrefix+"/api/order/cancelOrder.json";

    /*名称			编码			类型		必填		示例值			默认值	描述*/
    /*去哪儿订单编号	orderId		String	是		120389782384			去哪儿订单编号*/
    /*支付或担保金额	payAmount	Decimal	是		550.0					支付或担保金额*/
    public static final String payForOrder_GET = "https://hdspci.qunar.com"+"/api/order/payOrder.json";
}