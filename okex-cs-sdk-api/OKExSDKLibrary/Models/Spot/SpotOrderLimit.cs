using System;
using System.Collections.Generic;
using System.Text;

namespace OKExSDK.Models.Spot
{
    public class SpotOrderLimit : SpotOrder
    {
        /// <summary>
        /// 价格
        /// </summary>
        public string price { get; set; }

        /// <summary>
        /// 0:普通委托 1:只做Maker 2:FOK 3:IOC
        /// </summary>
        public string order_type { get; set; }
    }
}
