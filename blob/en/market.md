
## API List

- [Get Realtime ticker data /v1/market/ticker](#ticker)
- [Get kline data /v1/ticker](#kline)
- [Get depth data /v1/depth](#depth)
- [Get trade data /v1/trade](#trades)

## API Details

### ticker 
GET /v1/market/ticker

 parameter：

return：

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
status|y|string|Status ||ok,error
timestamp|y|long|Current millisseconds||
ticker|y|list|data||

ticker:

Parameter Name|Mandatory|Type|Description|Default|ValueRange
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
symbol|y|string|Pairs symbol||Sell Crypto Abbrev_Buy Crypto Abbrev，eg：btc_usdt
last|y|number|Last Price||
buy|y|number|bid||
sell|y|number|ask||
high|y|number|24H High||
low|y|number|24H Low||
vol|y|number|24H Vol.||
change|y|number|24H Change||


return json

```json
{
   "status": "ok",
   "timestamp": 1567045034,
   "ticker":[
			{
				"symbol":"btc_usdt",
				"last":10000.00000000,
				"buy":9999.00000000,
				"sell":10001.00000000,
				"high":11000.00000000,
				"low":9000.00000000,
				"vol":10000000.0000,
				"change":10.10 
			}
		]
}
```
### kline
GET /v1/ticker
 parameter：

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
step|y|int|Time：sec||60（1 min）,300（5mins）,900（15mins）,1800（30mins）,3600（1h）,86400（1d）,604800（1w）,2592000（1mon）
symbol|y|string|Pairs||Example：btc_gavc

 return : 

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
code|y|int|Status
msg|n|string|return message 
time|y|long|Current millisseconds
data|y|array(array(number))|Klinedata 

data<br>

[[ <br>
   1527820200000,   //int Time
   54598.5,         //number  Open
   54598.5,         //number  High
   54598.5,         //number  Low
   54598.5,         //number  Close
   0.0000          //number  Volumn
   ],
   ......
]


returnjson

```json
{
   "code": 200,
   "msg": "True",
   "time": 1527838104874,
   "data": [
	 [
         1527820200000,
         54598.5,
         54598.5,
         54598.5,
         54598.5,
         0               
     ],
     [
         1527820200000,
         54598.5,
         54598.5,
         54598.5,
         54598.5,
         0
     ]
	]
}
```

### depth
GET /v1/depth
Parameter：

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
symbol|y|string|Pairs||Example：btc_gavc
step|n|int|Market depth aggregation level, details below. unit second||60,3*60,5*60,15*60,30*60,60*60（1h）,24*60*60（1d）,7*24*60*60（1w）,30*24*60*60（1mon）


return :

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
code|y|int|Status
msg|n|string|returnmessage
time|y|long|Current millisseconds
data|y|object|Transaction Depth data

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
depth|y|object
period|n|object|Values only display when uploading step field

depth

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
bids|y|array(array(long))|Sell,[price(Transaction price), amount(Transaction Vol.)]
asks|y|array(array(long))|Sell,[price(Transaction price), amount(Vol.)]
date|y|long|Timestamp
lastPrice|y|number|Last Price

period

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
marketFrom|y|string|input parameters symbol||
coinVol|y|string|input parameters symbol||
type|y|long|input parameters step,Time||
data|y|array（array）|The last Kline has the same format as above,with the sole||



returnjson

```json
{
   "code": 200,
   "msg": "true",
   "time": 1527837164605,
   "data":{
      "period":{
         "data": [[
            1527837120000,
            54598.5,
            54598.5,
            54598.5,
            54598.5,
            0
         ]],
         "marketFrom": "btc_gavc",
         "type": 60,
         "coinVol": "btc_gavc"
      },
      "depth":{
         "date": 1527837163,
         "asks": [
                        [
               57373.8,
               0.0387
            ],
                        [
               57751.26,
               0.0128
            ],
             [
               57751.26,
               0.0128
            ]
         ],
	 "bids": [
                        [
               57373.8,
               0.0387
            ],
                        [
               57751.26,
               0.0128
            ],
             [
               57751.26,
               0.0128
            ]
         ],
         "lastPrice": 54598.5
      }
   }
}
```
### trades
GET /v1/trade

Parameter：

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
count|y|int|Tradesitems||0
symbol|y|string|Pairs||Example：btc_gavc

return : 

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
code|y|int|Status 
msg|n|string|return info 
time|y|long|Current millisseconds
data|y|object| real time transaction 

data

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
trades|y|array(object)|tradesdata
sellSymbol|y|string|sellSymbol
buySymbol|y|string|buySymbol

trades

Parameter Name|Mandatory|Type|Description|Default|Value Range
------------- | ------------- |  ------------- | ------------- |  ------------- | -------------
price|y|long|Transaction price 
amount|y|string|Transaction Vol. 
id|y|string|Transactionid
time|y|string|Transactiontime
en_type|y|string|direction||"bid"(buy),"ask"(sell)
type|y|string|TransactionType||"Buy","sell"

return json

```json
{
 "code": 200,
 "msg": "true",
   "time": 1536315868962,
   "data":    {
     "sellSymbol": "BTC",
     "buySymbol": "GAVC",
     "trades": [
       {
          "price": 0.007,
          "amount": 66491.04,
          "id": 1,
          "time": "02:45:08",
          "en_type": "ask",
          "type": "Sell"
       }, 
	   {
          "price": 0.007,
          "amount": 66491.04,
          "id": 1,
          "time": "02:45:08",
          "en_type": "ask",
          "type": "Sell"
       }

	]
   }
}
```
