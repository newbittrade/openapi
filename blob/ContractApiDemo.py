import base64
import hashlib
import hmac
import urllib
import urllib.parse
import urllib.request
import requests
import time
from websocket import create_connection
from datetime import datetime
import json
from aiowebsocket.converses import AioWebSocket
import asyncio

##########################################
# 配置你的KEY
ACCESS_KEY = "ACCESS_KEY"
SECRET_KEY = "SECRET_KEY"
##########################################

API_HOST = 'perpetual-wss.monkey.com'
API_RUL = 'wss://' + API_HOST


def paramsSign(params, paramsPrefix, accessSecret):
    host = "perpetual.monkey.com"
    method = paramsPrefix['method'].upper()
    uri = paramsPrefix['uri']
    tempParams = urllib.parse.urlencode(sorted(params.items(), key=lambda d: d[0], reverse=False))
    payload = '\n'.join([method, host, uri, tempParams]).encode(encoding='UTF-8')
    accessSecret = accessSecret.encode(encoding='UTF-8')
    return base64.b64encode(hmac.new(accessSecret, payload, digestmod=hashlib.sha256).digest())


def wss_request(url, params):
    print(url, params)
    try:
        wss = create_connection(url)
        if wss.status == 101:
            wss.send(params)
            print(wss.recv())
            position_params = '{"event":"subscribe","params":{"biz":"perpetual","type":"position", "base": "usdt","zip":false}}'
            wss.send(position_params)
            while True: 
              print(wss.recv())
    except BaseException as msg:
        print('失败: ' + str(msg))
		

def get_time_stamp():
    return int ( round ( time.time() * 1000 ))

def api_key_wss(params, API_URI):
    method = 'wss'
    time = get_time_stamp()
    params_to_sign = {'AccessKeyId': ACCESS_KEY,
                      'SignatureMethod': 'HmacSHA256',
                      'SignatureVersion': '2',
                      'Timestamp': time}
    paramsPrefix = {"host": "perpetual-wss.monkey.com", 'method': method, 'uri': API_URI}
    params_to_sign.update(params)
    data = {"event":"signin","params":{"apiKey": ACCESS_KEY,"timestamp":time,"signature":paramsSign(params_to_sign, paramsPrefix, SECRET_KEY).decode(encoding='UTF-8')}}
    url = API_RUL
    wss_request(url, json.dumps(data))


def sign_in_send():
    params = {}
    API_RUI = '/wss'
    return api_key_wss(params, API_RUI)
	

if __name__ == "__main__":
    sign_in_send()

     