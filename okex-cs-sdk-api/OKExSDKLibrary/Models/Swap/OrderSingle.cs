using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Swap
{
    public class OrderSingle
    {
        /// <summary>
        /// 合约ID，如BTC-USD-180213
        /// </summary>
        public string instrument_id { get; set; }
        /// <summary>
        /// 由您设置的订单ID来识别您的订单
        /// </summary>
        public string client_oid { get; set; }
        /// <summary>
        /// 每张合约的价格
        /// </summary>
        public decimal price { get; set; }
        /// <summary>
        /// 买入或卖出合约的数量（以张计数）
        /// </summary>
        public int size { get; set; }
        /// <summary>
        /// 1:开多2:开空3:平多4:平空
        /// </summary>
        public string type { get; set; }
        /// <summary>
        /// 0:普通委托 1:只做Maker 2:FOK 3:IOC 4:市价委托
        /// </summary>
        public string order_type { get; set; }
        /// <summary>
        /// 是否以对手价下单(0:不是 1:是)，默认为0，当取值为1时。price字段无效
        /// </summary>
        public string match_price { get; set; }
    }
}
